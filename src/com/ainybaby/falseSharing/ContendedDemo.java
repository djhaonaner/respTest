package com.ainybaby.falseSharing;

import sun.misc.Contended;

/**
 * jdk8提供了@Contended注解,使成员变量不与其它成员变量在同一个缓存行,
 * 解决伪共享问题的.
 */
public class ContendedDemo {
    @Contended
    volatile long x;
    @Contended
    volatile long y;

    public static void main(String[] args) throws InterruptedException {
        ContendedDemo demo=new ContendedDemo();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10_0000_0000; i++) {
                demo.x = i;
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10_0000_0000; i++) {
                demo.y = i;
            }
        });
        long start = System.nanoTime();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(System.nanoTime()-start);
    }
}
