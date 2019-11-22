
public class SingleThread {
	String password;
	StringBuilder string = new StringBuilder("*****");
	boolean flag = false;
	int min, max;
	long start;
	
	SingleThread(String password, int min, int max, long start){
		this.password = password;
		this.min = min;
		this.max = max;
		this.start = start;
	}
	
	public void loop (int index, int min, int max) {
		for (int i=min; i<max; i++) {
			// set the char value where index is the position in the string
			string.setCharAt(index, (char) i);
			if(index < string.length() - 1)
				loop(index + 1, 32, 127);
			//System.out.println(string);
			
			// when the password is found it should exit 
			if(flag)
				break;
			// Verifies if the password was found and exit the loop
			if(string.toString().equals(password)) {
				System.out.println("Password found with single thread: " + string);
				System.out.println("It took: " + (System.currentTimeMillis() - start) + " milliseconds.");
				flag = true;
				break;
			}
		}
	}
}
