package bank;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;


public class Util {

	private static Scanner input = new Scanner(System.in);
	private static Connection server = null;
	private static final Logger log = (Logger) LogManager.getLogger(Util.class); 
	
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
	
	//cleans a string of unwanted characters before insertion
	public static StringBuilder cleanInput(StringBuilder sb) {
		char[] notInluded = {'!', '#', '$', '%', '^', '&', '*', '_', '+', '=', ';', '\"', '?', ',', '.', 
		                     '<', '>', '/', '|', '\\', '\n', '~', '{', '}', '[', ']', '(', ')'};
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
	
	public static Connection getServer() throws Exception {
		if (Util.server != null) {
			return Util.server;
		}
		Connection dbConnection = null;
		Properties server = new Properties();
		try {
			InputStream config = Util.class.getResourceAsStream("/server.properties");
			server.load(config);
			
			String endpoint = server.getProperty("db.url");
			String user = server.getProperty("db.user");
			String password = server.getProperty("db.password");
			dbConnection = DriverManager.getConnection(endpoint, user, password);
			
			log.debug("Connected");;
			Util.server = dbConnection;
			return Util.server;
		} catch (IOException e) {
			log.error("Failed to Open File");
			throw new Exception("Unable to open file");
		} catch (SQLException e){
			log.error("Failed to connect to Database");
			throw new Exception("Unable to connect to database");
		}
	}
}
