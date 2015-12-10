import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
 
public class LockingMechanism {
	public static void main(String[] args) {
		final Company oracle = new Company("Oracle");
		final Company google = new Company("Google");
		new Thread(new OracleLoop(oracle, google)).start();
		new Thread(new OracleLoop(google, oracle)).start();
	}
 
	static class OracleLoop implements Runnable {
		private Company companyName1;
		private Company companyName2;
 
		public OracleLoop(Company companyName1, Company companyName2) {
			this.companyName1 = companyName1;
			this.companyName2 = companyName2;
		}
 
		public void run() {
			Random random = new Random();
			// Loop 10
			for (int counter = 0; counter <= 10; counter++) {
				try {
					Thread.sleep(random.nextInt(5));
				} catch (InterruptedException e) {
				}
				companyName2.oracleTalking(companyName1);
			}
		}
	}
 
	// Class Company
	static class Company {
		private final String companyName;
 
		// ReentrantLock: Creates an instance of ReentrantLock. This is equivalent to using ReentrantLock(false)
		private final Lock lock = new ReentrantLock();
 
		// Constructor
		public Company(String name) {
			this.companyName = name;
		}
 
		public String getName() {
			return this.companyName;
		}
 
		public boolean isTalking(Company companyName) {
			boolean oracleLock = false;
			boolean googleLock = false;
			try {
				// tryLock: Acquires the lock only if it is free at the time of invocation.
				oracleLock = lock.tryLock();
				googleLock = companyName.lock.tryLock();
			} finally {
				if (!(oracleLock && googleLock)) {
					if (oracleLock) {
						// unlock: Releases the lock.
						lock.unlock();
					}
					if (googleLock) {
						companyName.lock.unlock();
					}
				}
			}
			return oracleLock && googleLock;
		}
 
		public void oracleTalking(Company companyName) {
			// Check if Lock is already exist?
			if (isTalking(companyName)) {
				try {
					System.out.println("I'm "+this.companyName+": talking to " + companyName.getName());
				} finally {
					lock.unlock();
					companyName.lock.unlock();
				}
			} else {
				System.out.println("\tLock Situation ==> I'm "+this.companyName+": talking to "+companyName.getName()+", but it seems"
						+ " we are already talking. Conflicting.");
			}
		}
	}
}