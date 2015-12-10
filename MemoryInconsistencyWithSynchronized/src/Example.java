public class Example {

    public static void main(String[] args) throws InterruptedException{
      Counter counter = new Counter();
      System.out.println("Counter object created, Counter value is : " + counter.count);
      Thread  threadA = new CounterThread(counter);
      Thread  threadB = new CounterThread(counter);

      threadA.start();
      threadB.start(); 
      threadA.join();
      threadB.join();
      System.out.println("Counter object created, Counter value is : " + counter.count);
    }
  }