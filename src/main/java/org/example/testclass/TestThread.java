package org.example.testclass;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class TestThread extends Thread{

    AtomicBoolean isFinished;
    int idThread;

    ConcurrentLinkedQueue<Integer> queueIdOfThread;

    public TestThread(int idThread, ConcurrentLinkedQueue<Integer>  atomicQueue){
        queueIdOfThread = atomicQueue;
        this.idThread = idThread;
        queueIdOfThread.offer(idThread);
    }


    @Override
    public void run() {

        while(true){

            System.out.println("I'm waiting "+idThread);

            if(queueIdOfThread.peek() == idThread){

                System.out.println("I begin to work "+idThread);
                for(int i = 0; i < 20; i++){
                    System.out.println("Step is "+i+" "+idThread);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("I finished to work "+idThread);

                queueIdOfThread.poll();

                System.out.println("Thread "+idThread +"is exited");
                break;


            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }
}
