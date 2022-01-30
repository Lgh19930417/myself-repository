package com.itfxp;

public class Singleton {
    private volatile Singleton singleton;
    /*
    由于要保证单例模式所创建的singleton对象唯一，所以需要对getSingleton()方法进行加锁，

     */
    public Singleton getSingleton(){
        if(singleton==null){
            singleton= new Singleton();
        }
        return  singleton;
    }
    /*
    但是当我们加锁后会,假设有两个线程A和B同时调用此方法，当A先拿到锁进入if语句中创建singleton对象时，
    线程B又获得了CPU执行资格，此时线程A会进入阻塞状态，但是线程B虽然有CPU资源，却拿不到锁，也处于阻塞状态，
    就会导致线程A和线程B处于大眼瞪小眼，谁也干不了活的状态，造成资源的浪费。总结来说就是线程A有锁无CPU资源，线程B有CPU资源却无锁！
     */
   /* public synchronized Singleton getSingleton(){
        if(singleton==null){
            singleton= new Singleton();
        }
        return  singleton;
    }*/
    /*
    下面我们对其进行改造,首先在Singleton属性位置加volatile改造完成后线程A拿到锁会先进行创建singleton对象，由于volatile关键字会保证
    线程之间资源的可见性，当线程A创建了singleton对象后，其他线程可以同时看到对象已经创建完成，就不会再去获取锁创建对象了，这就解决了效率问题，
    也保证了单例模式对象的唯一性
     */
   /* public synchronized Singleton getSingleton(){
        if(singleton==null){
            synchronized(Singleton.class) {
                singleton = new Singleton();
            }
        }
        return  singleton;
    }*/

}
