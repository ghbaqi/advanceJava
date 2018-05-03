package com.example.thread.volatil;

/**
 * volatile关键字有两层语义：
 * 1、立即将缓存中数据写会到内存中
 * 2、其他处理器通过嗅探总线上传播过来了数据监测自己缓存的值是不是过期了，
 * 如果过期了，就会对应的缓存中的数据置为无效。而当处理器对这个数据进行修改时，
 * 会重新从内存中把数据读取到缓存中进行处理。
 * <p>
 * 在这种情况下，不同的CPU之间就可以感知其他CPU对变量的修改，并重新从内存中加载更新后的值，
 * 因此可以解决可见性问题。
 */
public class VolatileDemo {

//    private volatile static Boolean flag = true;
    private  static Boolean flag = true;

    public static void main(String[] args) {

        //该线程每隔1毫秒，修改一次flag的值
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1);
                    flag = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        //主线程通过死循环不断根据flag进行判断是否要执行某段代码
        while (flag) {
            System.out.println("do some thing");
        }
        System.out.println("end");


    }
}
