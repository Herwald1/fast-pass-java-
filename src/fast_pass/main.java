package fast_pass;

import java.util.ArrayList;
import java.util.Scanner;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class user {
	private String name;
	private String key;
	String DB_URL = "jdbc:mysql://localhost:3306/fastpass_database";
	Scanner input = new Scanner(System.in);
	
	public String getName() {
		return name;
	}
	public String getKey() {
		return key;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * public void setPassword(String password) { this.password = password; }
	 */
	public void setKey(String key) {
		this.key = key;
	}
	
	public void welcome() {
		char choice;
		System.out.println("Welcome to fast-pass");
		System.out.println("Do you have a account?(y or n)");
		choice  = input.next().charAt(0);
		
		switch(choice) {
		case 'y':
			login();
			break;
		case 'n':
			createAccount();
			break;
		}
		
	}
	public void login() {
		String username, userpass, correctPass = null, correctUser = null, rankey = null;
		
		System.out.println("Please enter your username");
		username = input.next();
		System.out.println("Please enter your password");
		userpass = input.next();
		
		try {
    		Connection connect = DriverManager.getConnection(DB_URL, "root","usbw");
    		String sql = "SELECT userkey, username, password FROM user_details WHERE username = ?";
    		PreparedStatement stmt = connect.prepareStatement(sql);
    		stmt.setString(1, username);
    		ResultSet rs = stmt.executeQuery();
    		while(rs.next()) {
    			rankey = rs.getString("userkey");
    			correctUser = rs.getString("username");
    			correctPass = rs.getString("password");
    		}			
    	}catch(SQLException sqlEx){
    		sqlEx.printStackTrace();
 			System.err.println("Error working with Database");
    	}
		if(username.equals(correctUser) && userpass.equals(correctPass)) {
	    	System.out.println("Success!");
	    	setKey(rankey);
	    	setName(correctUser);
	    	uservault();
    	}else {
    		System.out.println("login failed!");
    	}
		
		
	}
	public void createAccount() {
	
		String fname, lname, username, userpass;
		int min = 11;
		int max = 99;
		int rankey = (int)(Math.random()*(max-min+1)+min); 
		System.out.println("Please enter your first name:");
		fname = input.next();
		System.out.println("Please enter your last name:");
		lname = input.next();
		System.out.println("What would you like your Username to be?");
		username= input.next();
		System.out.println("Ok, lastly lets create the password that will protect all others");
		userpass= input.next();
		
		setName(username);
		setKey(Integer.toString(rankey));
		try {
	   		Connection connect = DriverManager.getConnection(DB_URL, "root", "usbw");
	   		String sql = "INSERT INTO user_details VALUES(?,?,?,?,?)";
	   		PreparedStatement stmt = connect.prepareStatement(sql);	
	   		stmt.setInt (1, rankey);
	   		stmt.setString (2, fname);
	   		stmt.setString (3, lname);
	   		stmt.setString (4, username);
	   		stmt.setString (5, userpass);
	   		stmt.execute();
	   		
	   		String sql2 = "CREATE TABLE "+ name +" (service varchar(255), emailOrUsername varchar(255), password varchar(255))";
	   		PreparedStatement stmt2 = connect.prepareStatement(sql2);	
	   		stmt2.execute();
	   		connect.close();
   		
   	 	}catch(SQLException sqlEx){
	   		sqlEx.printStackTrace();
			System.err.println("Error working with Database");
   	 	}
		
		login();
		
	}
	
	public void uservault() {
		/*
		 * String[] service = new String[] {}; String[] emailOrUsername = new String[]
		 * {}; String[] password = new String[] {};
		 */
		ArrayList<String> service = new ArrayList<String>();
		ArrayList<String> emailOrUsername = new ArrayList<String>();
		ArrayList<String> password = new ArrayList<String>();

		System.out.println("Here are The list of all your Passwords saved "+ name+" Key:"+key);
		try {
			Connection connect = DriverManager.getConnection(DB_URL, "root", "usbw");

    		String sql = "SELECT * FROM "+name;
    		PreparedStatement stmt = connect.prepareStatement(sql);
    		ResultSet rs = stmt.executeQuery();
    		while(rs.next()) {
				/*
				 * service[count] = rs.getString("service"); emailOrUsername[count] =
				 * rs.getString("emailOrUsername"); password[count] = rs.getString("password");
				 */
    			service.add(rs.getString("service"));
    			emailOrUsername.add(rs.getString("emailOrUsername"));
    			password.add(rs.getString("password"));
    		}
		}catch(SQLException sqlEx) {
			sqlEx.printStackTrace();
			System.err.println("Error working with Database");
		}
		
		for(int i =0;i<service.size();i++) {
			System.out.print("Service: "+service.get(i));
			System.out.print(" Email or username: "+emailOrUsername.get(i));
			System.out.println(" Passord: "+password.get(i));
			System.out.println("-----------------------------------------------------------------------------------");
		}
		System.out.println(encrypt("hello"));
	}
	
	public String encrypt(String password) {
		String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
		String cipherText = "";
		
		char firstChar = key.charAt(0);
		int keyuse = firstChar;
        for (int i = 0; i < password.length(); i++) {
            int charPosition = ALPHABET.indexOf(password.charAt(i));
            int keyVal = (keyuse + charPosition) % 26;
            char replaceVal = ALPHABET.charAt(keyVal);
            cipherText += replaceVal;
        }
		
		return cipherText;
	}
	
}

/*
 * class userVault extends user{
 * 
 * }
 */

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		user obj = new user();
		
		obj.welcome();
	}

}
