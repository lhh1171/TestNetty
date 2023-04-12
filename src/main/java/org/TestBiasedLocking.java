package org;

import io.netty.handler.codec.DecoderException;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

public class TestBiasedLocking {

    public static void main(String[] args) throws DecoderException, InterruptedException {
        // 首先我们创建一个list，来存放锁对象
        List<TestBiasedLocking> list = new LinkedList<>();

        // 线程1
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                TestBiasedLocking testBiasedLocking = new TestBiasedLocking();
                list.add(testBiasedLocking); // 新建锁对象
                synchronized (testBiasedLocking) {
                    System.out.println("第" + (i + 1) + "次加锁-线程1"); // 50个妹子第一次结婚
//                    System.out.println(ClassLayout.parseInstance(testBiasedLocking).toPrintable());
                }

            }
            LockSupport.park();
        }, "线程1").start();

        // 让线程1跑一会儿
        Thread.sleep(2000);

        // 线程2
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                TestBiasedLocking testBiasedLocking = list.get(i);
                synchronized (testBiasedLocking) {
                    System.out.println("第" + (i + 1) + "次加锁-线程2"); // 前40个妹子依次被40个老王看上，前边20个直接离婚了，后边zf有新规定了，20-40的妹子直接把自己的老公都换成了对应的老王
//                    System.out.println(ClassLayout.parseInstance(testBiasedLocking).toPrintable());

                }
            }
            LockSupport.park();
        }, "线程2").start();

        // 让线程2跑一会儿
        Thread.sleep(2000);

        // 线程3
        new Thread(() -> {
            for (int i = 20; i < 40; i++) {
                TestBiasedLocking testBiasedLocking = list.get(i);
                synchronized (testBiasedLocking) {
                    System.out.println("第" + (i + 1) + "次加锁-线程3"); // 20-40的妹子觉得很刺激，换过老王了，还想接着换王中王？玩砸了，这个地区的女的以后都不准结婚了，结婚的也都给我离婚
//                    System.out.println(ClassLayout.parseInstance(testBiasedLocking).toPrintable());
                }
            }
            LockSupport.park();
        }, "线程3").start();

        // 让线程3跑一会儿
        Thread.sleep(2000);
        System.out.println("刚出生的妹子"); // 生不逢时，政策不一样了，该地区的妹子以后都不让结婚了
//        System.out.println(ClassLayout.parseInstance(new TestBiasedLocking()).toPrintable());
        LockSupport.park();
    }
}