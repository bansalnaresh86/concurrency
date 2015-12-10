import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTest {

	private static Future taskTwo = null;
	private static Future taskThree = null;
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
		ScheduledFuture future = executorService.schedule(new MyThread("TaskFour", 2, 100), 10, TimeUnit.SECONDS);
	   
		// execute the Runnable
	    Runnable taskOne = new MyThread("TaskOne", 2, 100);
	    executor.execute(taskOne);
	    for(int i = 0; i < 2; i++) {
	    	// if this task is not created or is canceled or is completed
			if ((taskTwo == null) || (taskTwo.isDone()) || (taskTwo.isCancelled())) {
				// submit a task and return a Future
				taskTwo = executor.submit(new MyThread("TaskTwo", 4, 200));
			}
	
			if ((taskThree == null) || (taskThree.isDone()) || (taskThree.isCancelled())) {
				taskThree = executor.submit(new MyThread("TaskThree", 5, 100));
			}
			// if null the task has finished
			if(taskTwo.get() == null) {
				System.out.println(i+1 + ") TaskTwo terminated successfully");
			} else {
				// if it doesn't finished, cancel it
				taskTwo.cancel(true);
			}
			if(taskThree.get() == null) {
				System.out.println(i+1 + ") TaskThree terminated successfully");
			} else {
				taskThree.cancel(true);
			}
	    }
	    executor.shutdown();
	    System.out.println("-----------------------");
	    // wait until all tasks are finished
	    executor.awaitTermination(1, TimeUnit.SECONDS);
	    
	    while(!future.isDone())
	    {
	    	Thread.sleep(2000);
	    	System.out.println("ScheduledTask is pending!");
	    }
	    executorService.shutdown();
	    System.out.println("All tasks are finished!");
	}

}

class MyThread implements Runnable {
	
	private String myName;
	private int count;
	private final long timeSleep;

	MyThread(String name, int newcount, long newtimeSleep) {
		this.myName = name;
	    this.count = newcount;
	    this.timeSleep = newtimeSleep;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

		int sum = 0;
	    for (int i = 1; i <= this.count; i++) {
	    	sum = sum + i;
	    }
	    System.out.println(myName + " thread has sum = " + sum + 
	    		" and is going to sleep for " + timeSleep);
	    try {
			Thread.sleep(this.timeSleep);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}