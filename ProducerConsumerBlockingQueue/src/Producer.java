import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable{

    protected BlockingQueue<Integer> queue = null;

    public Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
        	for(int i = 0; i<100;i++)
        	{
        		queue.put(i);
        	}
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}