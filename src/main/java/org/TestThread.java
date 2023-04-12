package org;

import java.util.concurrent.*;

public class TestThread {

    private static ThreadPoolExecutor EXECUTOR =
            new ThreadPoolExecutor(5, 10, 3L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>());
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long beginTime = System.currentTimeMillis();
//        EXECUTOR.shutdown();
        //	商品，库存----》订单-----》订单明细
//        返回一个新的 CompletableFuture，
//        它由在ForkJoinPool.commonPool()中运行的任务异步完成，其值是通过调用给定的 Supplier 获得的
//        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            String ret = "任务名称：获取商品信息，执行时间：2000ms，线程名：" + Thread.currentThread().getName();
//            System.out.println(ret);
//            return ret;
//        });
//
//        //库存
//        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
//            try {
//                Thread.sleep(1500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            String ret = "任务名称：获取库存信息，执行时间：1500ms，线程名：" + Thread.currentThread().getName();
//            System.out.println(ret);
//            return ret;
//        });
//
//        //订单依赖商品和库存
//        cf1.thenCombineAsync(cf2, (s1, s2) -> {
//            System.out.println(">>>>>>>>>>>>>截止库存和商品执行完花了：" + (System.currentTimeMillis() - beginTime) + "ms");
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            String ret = "任务名称：订单信息，执行时间：500ms，线程名：" + Thread.currentThread().getName();
//            System.out.println(ret);
//            return ret;
//        }).thenApplyAsync(s -> {
//            System.out.println(">>>>>>>>>>>>>>截止订单执行完毕共花了：" + (System.currentTimeMillis() - beginTime) + "ms");
//            try {
//                Thread.sleep(600);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            String ret = "任务名称：订单明细，执行时间：600ms，线程名：" + Thread.currentThread().getName();
//            System.out.println(ret);
//            return ret;
//        }).thenAccept(s -> {
//            System.out.println(">>>>>>>>>>>>>截止订单明细执行完共花了：" + (System.currentTimeMillis() - beginTime) + "ms");
//        }).whenComplete((aVoid, throwable) -> {
//            EXECUTOR.shutdown();
//        });
    }
}
