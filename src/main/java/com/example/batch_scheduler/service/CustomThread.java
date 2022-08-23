package com.example.batch_scheduler.service;
public class CustomThread implements Runnable {

  // to stop the thread
  private boolean exit;

  private String name;
  Thread t;

//  CustomThread(String threadname) {
//    name = threadname;
//    t = new Thread(this, name);
//    System.out.println("New thread: " + t);
//    exit = false;
//    t.start(); // Starting the thread
//  }

  // execution of thread starts from run() method
  @Override
  public void run() {
    int i = 0;
    while (!exit) {
      // to calculate the how many time take to complete the task in thread
//      System.out.println(name + " Counter i : " + i);
      i++;
      try {
        Thread.sleep(1000);
        //        Thread.interrupted();

      } catch (InterruptedException e) {
        System.out.println("Caught: " + e);
      }
    }
    System.out.println(name + " Stopped.");

    //    System.out.println("Number of active threads from the given thread: " + "Thread.activeCount()" + java.lang.Thread.activeCount());
  }

  // for stopping the thread
  public void stop() {
    exit = true;
  }
}
