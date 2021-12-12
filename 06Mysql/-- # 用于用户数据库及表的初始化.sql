-- # 用于用户数据库及表的初始化

CREATE DATABASE if not exists `learntest`;

USE `learntest`;

--用户信息表 guid，总金额，已使用金额  (余额=总金额-已使用金额)
CREATE TABLE `tb_user_balance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_guid` bigint(20) NOT NULL DEFAULT '0' COMMENT 'guid',
  `total_amount` bigint(20) NOT NULL DEFAULT '0' COMMENT '总金额',
  `used_amount` bigint(20) NOT NULL DEFAULT '0' COMMENT '已使用金额',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始日期',
  `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后消费时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户表'


--商品信息表goodsId，名称价格状态
CREATE TABLE `tb_goods_conf` (
  `goods_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '商品id',
  `goods_name` varchar(100) NOT NULL DEFAULT '' COMMENT '商品名称',
  `memo` varchar(200) NOT NULL DEFAULT '' COMMENT '说明',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态0：未启用，1：启用',
  `price` int(11) NOT NULL DEFAULT '0' COMMENT '价格',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品信息表';


--订单信息表 订单id,总金额，用户guid,订单状态，创建时间，更新时间
CREATE TABLE `tb_pay_order_log`(
  `pay_order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '扣费订单id',
  `total_amount` int(11) NOT NULL DEFAULT '0' COMMENT '订单总金额',
  `user_guid` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态0：待付款，1：付款完成，2：配送中，3：已完成',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_guid`,`pay_order_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表'

--订单明细表，明细id，订单id，金额，商品数量，创建时间
CREATE TABLE `tb_pay_order_amount_log` (
  `log_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '明细订单id',
  `pay_order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '扣费订单id',
  `user_guid` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户Id',
  `amount` int(11) NOT NULL DEFAULT '0' COMMENT '总价',
  `count` int(11) NOT NULL DEFAULT '0' COMMENT '商品数量',
  `goods_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '商品Id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
  PRIMARY KEY (`user_guid`,`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单明细表';


