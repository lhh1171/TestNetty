package org;

import sun.misc.Unsafe;
import java.lang.reflect.Field;

public class UnSafeTest {
    static final Unsafe unsafe;
    static final long stateOffset;
    private volatile long state = 0;

    static {
        try {
            //利用反射获取Unsafe的成员变量theUnsafe
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            //设置为可访问
            field.setAccessible(true);
            //获取该变量的值
            unsafe = (Unsafe) field.get(null);
            //获取state在UnsafeTest实例中的偏移量
            stateOffset = unsafe.objectFieldOffset(UnSafeTest.class.getDeclaredField("state"));
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            throw new Error(e);
        }
    }

    public static void main(String[] args) {
        UnSafeTest test = new UnSafeTest();
        boolean compareAndSwapInt = unsafe.compareAndSwapInt(test, stateOffset, 0, 1);
        System.out.println(compareAndSwapInt);
    }

}
