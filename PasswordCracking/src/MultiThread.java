
public class MultiThread implements Runnable {
	String password;
	StringBuilder string = new StringBuilder("*****");
	int min, max;
	boolean flag=false;
	long start;
	Thread t1, t2, t3, t4;
	
	// Constructor
	public MultiThread(String password, int min, int max) {
		this.password = password;
		this.min = min;
		this.max = max;
	}
	
	void set_threads_and_start(Thread t1, Thread t2, Thread t3, Thread t4, long start) {
		this.t1 = t1;
		this.t2 = t2;
		this.t3 = t3;
		this.t4 = t4;
		this.start = start;
	}
	
	public void loop (int index, int min, int max) {
		for (int i=min; i<max; i++) {
			string.setCharAt(index, (char) i);
			if(index < string.length() - 1)
				loop(index + 1, 32, 127);
			//System.out.println(string);
			if(flag)
				break;
			if(string.toString().equals(password)) {
				System.out.println("Password found with multi-thread: " + string);
				System.out.println("It took: " + (System.currentTimeMillis() - start) + " milliseconds.");
				t1.interrupt();
				t2.interrupt();
				t3.interrupt();
				t4.interrupt();
				flag = true;
				break;
			}
		}
	}
	
	// Here is where the magic happens
	public void run() {
		try {
			start = System.currentTimeMillis();
			for (int i=min; i<max; i++) {
				Thread.sleep(0);
				string.setCharAt(0, (char) i);
				loop(1, 32, 127);
			}
		}
		catch (InterruptedException e) {
			// Message from the rabbit when the turtle wins and interrupt him.
			System.out.println("Stopped");
		}
	}
}
