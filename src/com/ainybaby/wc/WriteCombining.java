package com.ainybaby.wc;

/**
 *  cpu往缓存里写数据,存在合并写的操作.
 *  write combining buffer:4个字节数据
 */
public class WriteCombining {
    private static final int ITERATIONS=Integer.MAX_VALUE;
    private static final int ITEMS=1<<24;
    private static final int MASK=ITEMS-1;

    private static final byte[] array1=new byte[ITEMS];
    private static final byte[] array2=new byte[ITEMS];
    private static final byte[] array3=new byte[ITEMS];
    private static final byte[] array4=new byte[ITEMS];
    private static final byte[] array5=new byte[ITEMS];
    private static final byte[] array6=new byte[ITEMS];

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            System.out.println(i+"_singleLoop(ns):"+runCaseOne());
            System.out.println(i+"_splitLoop(ns):"+runCaseTwo());
        }
    }

    private static long runCaseOne() {
//        long start = System.nanoTime();//纳秒
        long start = System.currentTimeMillis();//毫秒
        int i=ITERATIONS;
        while (--i!=0){//一个循环,有7次写操作
            int slot=i & MASK;
            byte b= (byte) i;
            array1[slot]=b;
            array2[slot]=b;
            array3[slot]=b;
            array4[slot]=b;
            array5[slot]=b;
            array6[slot]=b;
        }
        return System.currentTimeMillis()-start;
    }

    private static long runCaseTwo() {
        long start = System.currentTimeMillis();
        int i=ITERATIONS;
        //有两个循环,每次循环有刚好有4次写操作
        while (--i!=0){
            int slot=i & MASK;
            byte b=(byte)i;
            array1[slot]=b;
            array2[slot]=b;
            array3[slot]=b;
        }
        i=ITERATIONS;
        while (--i!=0){
            int slot=i & MASK;
            byte b=(byte)i;
            array4[slot]=b;
            array5[slot]=b;
            array6[slot]=b;
        }
        return System.currentTimeMillis()-start;
    }
}
