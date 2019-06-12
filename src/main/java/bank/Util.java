package bank;

import java.util.Scanner;

public class Util {

	private static Scanner input = new Scanner(System.in);
	
	public Util() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * 
	 * @return empty sting Builder
	 * */
	public static StringBuilder ClearStringBuilder(StringBuilder sb) {
		sb.delete(0, sb.length());
		return sb;
	}
	
	//cleans a string of unwanted charachters before insertion
	public static StringBuilder cleanInput(StringBuilder sb) {
		char[] notInluded = {'!', '@', '#', '$', '%', '^', '&', '*', '_', '+', '=', ';', '\"', '?', ',', '.', 
		                     '<', '>', '/', '|', '\\', '\n', '~', '{', '}', '[', ']'};
		char subc;
		for (char c : notInluded) {
			for(int i = 0; i < sb.length(); i++) {
				subc = sb.charAt(i);
				if (subc == c) {
					sb.deleteCharAt(i);
					i--;
				}
			}
		}
		return sb;
	}
	public static Scanner getInput() {
		return input;
	}

	public static void setInput(Scanner input) {
		Util.input = input;
	}

}
