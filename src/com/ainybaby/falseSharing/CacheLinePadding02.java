package com.ainybaby.falseSharing;
public class CacheLinePadding02 {
    //成员变量不在同一个缓存行
    private static volatile long[] arr=new long[16];

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10_0000_0000; i++) {
                arr[0]=i;
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10_0000_0000; i++) {
                arr[8]=i;
            }
        });
        long start= System.nanoTime();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(System.nanoTime()-start);
    }
}
