package Online.Bankimg.System;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class BankingApp {
 
	private static final String url="jdbc:mysql://localhost:3306/banking_system";
	private static final String userName="root";
	private static final String password="Admin@123";

	public static void main(String args[]) {
		try {
		Class.forName("com.mysql.cj.jdnc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			Connection connection=DriverManager.getConnection(url, userName, password);
			Scanner scanner=new Scanner(System.in);
			User user=new User(connection, scanner);
			Accounts accounts=new Accounts(connection, scanner);
            AccountManager accountManager = new AccountManager(connection, scanner);
			
			String email;
			Long accountNumber;
			
			 while(true){
	                System.out.println("*** WELCOME TO BANKING SYSTEM ***");
	                System.out.println();
	                System.out.println("1. Register");
	                System.out.println("2. Login");
	                System.out.println("3. Exit");
	                System.out.println("Enter your choice: ");
	                int choice1 = scanner.nextInt();
	                switch (choice1){
	                    case 1:
	                        user.register();
	                        break;
	                    case 2:
	                        email = user.login();
	                        if(email!=null){
	                            System.out.println();
	                            System.out.println("User Logged In!");
	                            if(!accounts.accountExist(email)){
	                                System.out.println();
	                                System.out.println("1. Open a new Bank Account");
	                                System.out.println("2. Exit");
	                                if(scanner.nextInt() == 1) {
	                                	accountNumber = accounts.openAccount(email);
	                                    System.out.println("Account Created Successfully");
	                                    System.out.println("Your Account Number is: " + accountNumber);
	                                }else{
	                                    break;
	                                }

	                            }
	                            accountNumber = accounts.getAccountNumber(email);
	                            int choice2 = 0;
	                            while (choice2 != 5) {
	                                System.out.println();
	                                System.out.println("1. Debit Money");
	                                System.out.println("2. Credit Money");
	                                System.out.println("3. Transfer Money");
	                                System.out.println("4. Check Balance");
	                                System.out.println("5. Log Out");
	                                System.out.println("Enter your choice: ");
	                                choice2 = scanner.nextInt();
	                                switch (choice2) {
	                                    case 1:
	                                        accountManager.debitMoney(accountNumber);
	                                        break;
	                                    case 2:
	                                        accountManager.creditMoney(accountNumber);
	                                        break;
	                                    case 3:
	                                        accountManager.transferMoney(accountNumber);
	                                        break;
	                                    case 4:
	                                        accountManager.getBalance(accountNumber);
	                                        break;
	                                    case 5:
	                                        break;
	                                    default:
	                                        System.out.println("Enter Valid Choice!");
	                                        break;
	                                }
	                            }

	                        }
	                        else{
	                            System.out.println("Incorrect Email or Password!");
	                        }
	                    case 3:
	                        System.out.println("THANK YOU FOR USING BANKING SYSTEM!!!");
	                        System.out.println("Exiting System!");
	                        return;
	                    default:
	                        System.out.println("Enter Valid Choice");
	                        break;
	                }
	            }
	        }catch (SQLException e){
	            e.printStackTrace();
	        }
	    }
}
