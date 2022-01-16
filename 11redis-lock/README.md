# 基于Redis封装分布式数据操作
***
## 作业要求
- 必做）基于Redis封装分布式数据操作：
 -1）在Java中实现一个简单的分布式锁；
 -2）在Java中实现一个分布式计数器，模拟减库存。
 



### Redis分布式锁
- 加锁：使用 set key value [NX] [EX] 命令，达到lua脚本的原子性操作
  这个命令仅在不存在key的时候才能被执行成功（NX选项），并且这个key有一
  个自动失效时间expire（PX属性）。这个key值在所有的客户端必须是唯一的，
  所有同一key的获取者（竞争者）这个值都不能一样。
- 释放锁：使用lua脚本进行解锁。通过lua脚本告诉Redis只有key存在并且存储
  的值和我指定的值一样才能告诉我删除成功。

```
lock test:: start sleep 10
获取锁失败
库存减一 amount: 99
lock test:: end
lock test:: start sleep 10
库存减一 amount: 98
```