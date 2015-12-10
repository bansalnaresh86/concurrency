import java.util.Random;

public class ThreadlocalExample {
	
	private static Random random = new Random();
	
	private static ThreadLocal<ThreadlocalExample> threadLocal =
            new ThreadLocal<ThreadlocalExample>(){
		 protected ThreadlocalExample initialValue()
         {
             return new ThreadlocalExample(random.nextInt());
         }
     };
     
     private Integer randomNumber;
     
     private ThreadlocalExample(int ran)
     {
    	 randomNumber = ran;
     }

    public Integer getRandomNumber() {
		return randomNumber;
	}

	public static class MyRunnable implements Runnable {
        
        @Override
        public void run() {
    
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
    
            System.out.println(threadLocal.get().getRandomNumber());
        }
    }


    public static void main(String[] args) throws InterruptedException {
        MyRunnable sharedRunnableInstance = new MyRunnable();

        Thread thread1 = new Thread(new MyRunnable());
        Thread thread2 = new Thread(new MyRunnable());

        thread1.start();
        thread2.start();

        thread1.join(); //wait for thread 1 to terminate
        thread2.join(); //wait for thread 2 to terminate
    }

}