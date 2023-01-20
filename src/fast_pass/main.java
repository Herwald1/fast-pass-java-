package fast_pass;

import java.util.Scanner;

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
		String username, userpass, correctPass, correctUser;
		
		System.out.println("Please enter your username");
	}
	public void createAccount() {
		
	}
	
}

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		user obj = new user();
		
		obj.welcome();
	}

}
