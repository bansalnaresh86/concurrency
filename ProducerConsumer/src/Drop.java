public class Drop {
    // Message sent from producer
    // to consumer.
    private String message;
    
    public synchronized String take() {
        
    	return message;
    }

    public synchronized void put(String message) {
        this.message = message;
    }
}