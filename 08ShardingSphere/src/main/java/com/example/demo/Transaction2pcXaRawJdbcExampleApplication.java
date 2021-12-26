package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;

/**
 * @author zhaobaozhi
 */
public class Transaction2pcXaRawJdbcExampleApplication {

    /**
     * 第一次插入数据正常运行成功 插入id小于10的数据
     * 第二次插入数据由于主键冲突，导致回滚 插入id为6~15的数据
     * 如果数据库中没有id
     */
    public static void main(String[] args) throws IOException, SQLException {
        DataSource dataSource = getShardingDatasource();
        cleanData(dataSource);

        TransactionTypeHolder.set(TransactionType.XA);

        Connection conn = dataSource.getConnection();
        String sql = "insert into t_order (user_id, order_id) VALUES (?, ?);";

        System.out.println("First XA Start insert data");
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            for (int i = 1; i < 11; i++) {
                statement.setLong(1, i);
                statement.setLong(2, i);
                statement.executeUpdate();
            }
            conn.commit();
        }

        System.out.println("First XA insert successful");

        // 设置id+5，如果设置XA事务成功，则所有的数据都不会插入
        // 设置id+5，如果设置XA事务不成功，则id大于10的数据就会插入到数据库
        // 程序运行完毕后，查看数据库，没有id大于10的数据，所以XA设置成功
        System.out.println("Second XA Start insert data");
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            for (int i = 1; i < 11; i++) {
                statement.setLong(1, i+5);
                statement.setLong(2, i+5);
                statement.executeUpdate();
            }
            conn.commit();
        } catch (Exception e) {
            System.out.println("Second XA insert failed");
            conn.rollback();
        } finally {
            conn.close();
        }
    }

    private static void cleanData(DataSource dataSource) {
        System.out.println("Delete all Data");
        try (Connection conn = dataSource.getConnection(); Statement statement = conn.createStatement()) {
            statement.execute("delete from t_order;");
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Delete all Data successful");
    }

    /**
     * 生成DataSource，文件路径自行替换
     * @return
     * @throws IOException
     * @throws SQLException
     */
    static private DataSource getShardingDatasource() throws IOException, SQLException {
        String fileName = "/Users/zhaobaozhi/develop/learn_git/08ShardingSphere/src/main/resources/sharding-databases-tables.yaml";

        File yamlFile = new File(fileName);
        return YamlShardingSphereDataSourceFactory.createDataSource(yamlFile);
    }

}
