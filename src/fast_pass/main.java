package fast_pass;

import java.util.Scanner;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class user {
	private String name;
	private String password;
	private String key;
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
	public void setPassword(String password) {
		this.password = password;
	}
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
		String username, userpass, correctPass = null, correctUser = null;
		String DB_URL = "jdbc:mysql://localhost:3306/fastpass_database";
		
		System.out.println("Please enter your username");
		username = input.next();
		System.out.println("Please enter your password");
		userpass = input.next();
		
		try {
    		Connection connect = DriverManager.getConnection(DB_URL, "root","usbw");
    		String sql = "SELECT username, password FROM user_details WHERE username = ?";
    		PreparedStatement stmt = connect.prepareStatement(sql);
    		stmt.setString(1, username);
    		ResultSet rs = stmt.executeQuery();
    		while(rs.next()) {
    			correctUser = rs.getString("username");
    			correctPass = rs.getString("password");
    		}			
    	}catch(SQLException sqlEx){
    		sqlEx.printStackTrace();
 			System.err.println("Error working with Database");
    	}
		if(username.equals(correctUser) && userpass.equals(correctPass)) {
	    	System.out.println("Success!");
    	}else {
    		System.out.println("login failed!");
    	}
		
	}
	public void createAccount() {
		String DB_URL = "jdbc:mysql://localhost:3306/fastpass_database";		
		String fname, lname, username, userpass;
		System.out.println("Please enter your first name:");
		fname = input.next();
		System.out.println("Please enter your last name:");
		lname = input.next();
		System.out.println("What would you like your Username to be?");
		username= input.next();
		System.out.println("Ok, lastly lets create the password that will protect all others");
		userpass= input.next();
		
		try {
   		 Connection connect = DriverManager.getConnection(DB_URL, "root", "usbw");
   		
   		
   		String sql = "INSERT INTO user_details VALUES(?,?,?,?,?)";
   		PreparedStatement stmt = connect.prepareStatement(sql);
   		
   		stmt.setString (1, "0");
   		stmt.setString (2, fname);
   		stmt.setString (3, lname);
   		stmt.setString (4, username);
   		stmt.setString (5, userpass);
   		
   		stmt.execute();

   		connect.close();
   		
   	 }catch(SQLException sqlEx){
   		sqlEx.printStackTrace();
			System.err.println("Error working with Database");
   	 }
		
	}
	
}

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		user obj = new user();
		
		obj.welcome();
	}

}
