package com.yh.test;

import org.csource.fastdfs.*;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*@SpringBootTest
@RunWith(SpringRunner.class)*/
public class FastFDSTest {


    @Test
    public void fastDFSTest() throws Exception {
        //this.getClass().getResource("")
        ClientGlobal.initByProperties("fastDFS.properties");
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();

        StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
        StorageClient1 storageClient1 = new StorageClient1(trackerServer,storageServer);
        String fileId = storageClient1.upload_file1(this.getClass().getResource("/imgs").getPath() + "/1.jpg", "jpg", null);
        System.out.println(fileId);
        //class com.yh.test.FastFDSTest
        //System.out.println(this.getClass());

        //file:/D:/JavaProjects/edu/eduService/fastDFSTest/target/classes/imgs
        //System.out.println(this.getClass().getResource("/imgs"));

        ///D:/JavaProjects/edu/eduService/fastDFSTest/target/classes/imgs/1.jpg
        //System.out.println(this.getClass().getResource("/imgs/1.jpg").getPath());

    }

    @Test
    public void testExecutors(){
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        MyRunnable myRunnable = new MyRunnable();
        executorService.submit(myRunnable);
        executorService.submit(myRunnable);
        executorService.submit(myRunnable);
    }

    public class MyRunnable implements Runnable{

        @Override
        public void run() {
            System.out.println("测试线程池");
            System.out.println(Thread.currentThread().getName());
            try{
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
