package com.ainybaby.falseSharing;
//伪共享:多线程访问数据在同一个缓存行,造成互相影响,效率低下
public class CacheLinePadding01 {
    //访问的成员变量在同一个缓存行(intel一个缓存行64字节)
    private static volatile long[] arr=new long[2];

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10_0000_0000; i++) {
                arr[0] = i;
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10_0000_0000; i++) {
                arr[1] = i;
            }
        });
        long start = System.nanoTime();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        long end = System.nanoTime();
        System.out.println(end-start);
    }
}
