import java.util.Scanner;

public class Main {

	private static Scanner in;

	public static void main(String[] args) {
		in = new Scanner(System.in);
		
		System.out.println("Type in a pasword of length 5: ");
		String password = in.next().toString();
		
		if (password.length()!=5) {
			System.out.println("Password length invalid!");
			return;
		}
		
		// Rango de valores Ascii
		int min = 32, max = 127;
		
		// Creamos 5 generadores de strings con los parametros del codigo ascii divididos en 5 bloques
		MultiThread mt1 = new MultiThread(password, 32, 52);
		MultiThread mt2 = new MultiThread(password, 52, 71);
		MultiThread mt3 = new MultiThread(password, 71, 90);
		MultiThread mt4 = new MultiThread(password, 90, 109);
		MultiThread mt5 = new MultiThread(password, 109, 127);
		
		// Creamos las threads correspondientes
		Thread mt1_th = new Thread(mt1);
		Thread mt2_th = new Thread(mt2);
		Thread mt3_th = new Thread(mt3);
		Thread mt4_th = new Thread(mt4);
		Thread mt5_th = new Thread(mt5);
		
		// Cracking Password with single thread (wcs 96 seconds aprox)
		long start = System.currentTimeMillis();
		SingleThread st = new SingleThread(password, min, max, start);
		st.loop(0, 32, 127);
		
		// Setteamos cada lista a su respectiva thread y el inicio
		start = System.currentTimeMillis();
		mt1.set_threads_and_start(mt2_th, mt3_th, mt4_th, mt5_th, start);
		mt2.set_threads_and_start(mt1_th, mt3_th, mt4_th, mt5_th, start);
		mt3.set_threads_and_start(mt1_th, mt2_th, mt4_th, mt5_th, start);
		mt4.set_threads_and_start(mt1_th, mt2_th, mt3_th, mt5_th, start);
		mt5.set_threads_and_start(mt1_th, mt2_th, mt3_th, mt4_th, start);
		
		// Cracking Password with multi-thread (wcs 28 seconds aprox)
		mt1_th.start();
		mt2_th.start();
		mt3_th.start();
		mt4_th.start();
		mt5_th.start();
		try {
			// Wait for the threads to finish
			mt1_th.join();
			mt2_th.join();
			mt3_th.join();
			mt4_th.join();
			mt5_th.join();
		} catch (InterruptedException ex) {
			System.out.println(" :( ");
		}
	}

}
