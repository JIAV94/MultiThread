/*
 	Password Cracker 1.0
 	The purpose of this project is to prove that the use of threads can optimize 
 	the performance of an application.
 	
    Copyright (C) 2019 Javier Iñaqui Aicinena Vargas

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/
    	
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
		
		// Ascii values range
		int min = 32, max = 127;
		
		// We create 5 string generators with the parameters of the Ascii code divided into 5 blocks
		MultiThread mt1 = new MultiThread(password, 32, 52);
		MultiThread mt2 = new MultiThread(password, 52, 71);
		MultiThread mt3 = new MultiThread(password, 71, 90);
		MultiThread mt4 = new MultiThread(password, 90, 109);
		MultiThread mt5 = new MultiThread(password, 109, 127);
		
		// We create the corresponding threads
		Thread mt1_th = new Thread(mt1);
		Thread mt2_th = new Thread(mt2);
		Thread mt3_th = new Thread(mt3);
		Thread mt4_th = new Thread(mt4);
		Thread mt5_th = new Thread(mt5);
		
		// Cracking Password with single thread (wcs 96 seconds aprox.)
		long start = System.currentTimeMillis();
		SingleThread st = new SingleThread(password, min, max, start);
		st.loop(0, 32, 127);
		
		// We set each list to its respective thread and the start time
		start = System.currentTimeMillis();
		mt1.set_threads_and_start(mt2_th, mt3_th, mt4_th, mt5_th, start);
		mt2.set_threads_and_start(mt1_th, mt3_th, mt4_th, mt5_th, start);
		mt3.set_threads_and_start(mt1_th, mt2_th, mt4_th, mt5_th, start);
		mt4.set_threads_and_start(mt1_th, mt2_th, mt3_th, mt5_th, start);
		mt5.set_threads_and_start(mt1_th, mt2_th, mt3_th, mt4_th, start);
		
		// Cracking Password with multi-thread (wcs 28 seconds aprox.)
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
