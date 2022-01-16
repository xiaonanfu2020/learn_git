# 基于Redis的PubSub实现订单异步处理
***
## 作业要求
基于Redis的PubSub实现订单异步处理

## 作业思路
使用jedis的publis函数发布订单
然后用JedisPubSub::onMessage函数模拟订单处理


### 结果

```shell 
start subscriber order
Start publisher order
Receive message from ORDER :: order sleep 10
Receive message from ORDER :: order sleep 2
Receive message from ORDER :: order sleep 8
Receive message from ORDER :: order sleep 3
Receive message from ORDER :: order sleep 2
Receive message from ORDER :: order sleep 3
Receive message from ORDER :: order sleep 4
Receive message from ORDER :: order sleep 10
Receive message from ORDER :: order sleep 7
SubPub End

Process finished with exit code 0
```