import java.util.Scanner;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Set;

public class ATM {
  Scanner scan = new Scanner(System.in);

  private int userInput;
  private String name;
  private int number_of_accounts = 1;
  private int indexOfElement;

  private boolean bankAdministrator = false;
  private static int max_amount = 500; 
  private int withdrawalAmount;
  private int depositAmount;
  private int invalidAttempts = 0;
  

  ArrayList<Integer> checkingAccount = new ArrayList<Integer>();
  ArrayList<Integer> savingsAccount = new ArrayList<Integer>();
  ArrayList<Integer> pin = new ArrayList<Integer>();
  ArrayList<String> acct_name = new ArrayList<String>();
  

  public void login() {
    bankAdministrator = false;
    boolean isValid = false;
    String input;

    System.out.println("**************************************************************");
    System.out.println("Welcome to the Mars branch of the Polish National Bank!");
    Art.polishFlag();
    System.out.println("Press [1] to create a new account ");
    System.out.println("Press [2] to login to an existing account");
    System.out.println("Press [3] to leave\n");
    if (invalidAttempts == 2) {
      System.out.println("You have entered [2] false entries. You have [2] attempts remaining.\n");
    } else if (invalidAttempts == 3) {
      System.out.println("You have entered [3] false entries. You have [1] attemp remaining.\n");
    } else if (invalidAttempts == 4) {
      Main.clearScreen();
      System.out.println("Your session has been terminated");
      System.exit(0);
    }
    System.out.println("**************************************************************");

    input = scan.nextLine();

    while (isValid == false) {
      if (input.equalsIgnoreCase("1")) {
        Main.clearScreen();
        isValid = true;
        try {
          accountCreation();
        } catch (InterruptedException e) {
          System.out.println("Something happened");
        }
      } else if (input.equalsIgnoreCase("2")) {
        Main.clearScreen();
        isValid = true;
        try {
          verification();
        } catch (InterruptedException e) {
          System.out.println("Something happened");
        }
      } else if (input.equalsIgnoreCase("3")){
        Main.clearScreen();
        System.out.println("Goodbye!");
        System.exit(0);
      } else {
        System.out.println("Please enter only [1] or [2]");
        input = scan.nextLine();
      }
    }

  }

  public void home(){
    String choice;
    boolean isValid = false;
    int total = checkingAccount.get(indexOfElement) + savingsAccount.get(indexOfElement);
    if (bankAdministrator == true) {
      invalidAttempts = 0;
      administratorHome();
    } else {
      invalidAttempts = 0;
      

      System.out.println("************************************************************************************************");
      System.out.println("\n\t\t\t\t\t\t\t\tWelcome to your account, " + acct_name.get(indexOfElement) + "\n");
      System.out.println("\t\t\t\t\t\t\tCheckings : $" + checkingAccount.get(indexOfElement) + "\t Savings : $" + savingsAccount.get(indexOfElement) + "\n");
      System.out.println("\t\t\t\t\t\t\t\tTotal Account Balance : $" + total + "\n");
      System.out.println("[1] for Deposit\t\t\t\t\t\t[2] for Withdrawal \t\t\t\t\t\t[3] for FastCash\n");
      System.out.println("[4] to exit\t\t\t\t\t\t\t[5] to logout and return\n \t\t\t\t\t\t\t\t\t\tto the login screen\n");
      System.out.println("************************************************************************************************");
    }
    choice = scan.nextLine();

    while(!isValid) {
      if (choice.equalsIgnoreCase("1")) {
        isValid = true;
        try {
          deposit();
        } catch (InterruptedException e) {
          System.out.println("Something happened");
        }
      } else if (choice.equalsIgnoreCase("2")) {
        isValid = true;
        try {
          withdrawal();
        } catch (InterruptedException e) {
          System.out.println("Something happened");
        }
      } else if (choice.equalsIgnoreCase("3")) {
        isValid = true;
        try {
          fastCash();
        } catch (InterruptedException e) {
          System.out.println("Something happened");
        }
      } else if (choice.equalsIgnoreCase("4")) {
        isValid = true;
        Main.clearScreen();
        System.out.println("Goodbye!");
        System.exit(0);
      } else if (choice.equalsIgnoreCase("5")) {
        isValid = true;
        Main.clearScreen();
        login();
      } else if (choice.equalsIgnoreCase("6") && bankAdministrator == true){
        isValid = true;
        Main.clearScreen();
        settings();
      } else {
        System.out.println("Only choices [1] - [5] are available");
        choice = scan.nextLine();
      }
    }

  }

  public void displayInformation() {
    int total = checkingAccount.get(indexOfElement) + savingsAccount.get(indexOfElement);
    System.out.println("**************************************************************");
    System.out.println("\nCurrent Total Account Balance : $" + total + "\n");
    System.out.println("\nCheckings : $" + checkingAccount.get(indexOfElement) + "\t Savings : $" + savingsAccount.get(indexOfElement) + "\n");
    System.out.println("**************************************************************");
  }


  public void accountCreation() throws InterruptedException {
    String input;
    boolean isValid = false;

    // slowPrint("============================\n \t Account Creation \n============================\n");
    slowPrint("=================================\n");
    System.out.println("|\t\tAccount Creation\t\t|");
    slowPrint("=================================\n");
    pause(1);
    int i = number_of_accounts;

    slowPrint("Enter a username :\n");
    duplicateCheck();
    System.out.println();

    slowPrint("Enter a PIN :\n");
    pin.add(errorCatch());
    System.out.println();

    slowPrint("Enter your Checking balance :\n");
    userInput = errorCatch();
    while (userInput < 1)  {
      slowPrint("Please enter a value greater than [1]\n");
      userInput = errorCatch();
    }
    checkingAccount.add(userInput);
    System.out.println();

    slowPrint("Enter your Savings balance :\n");
    userInput = errorCatch();
    while (userInput < 1)  {
      slowPrint("Please enter a value greater than [1]\n");
      userInput = errorCatch();
    }
    savingsAccount.add(userInput);
    System.out.println();

    Main.clearScreen();

    number_of_accounts++;

    slowPrint("Creating account . . .\n");
    for (int j = 0; j < (int) (Math.random() * 10) + 3; j++) {
      try {
        Thread.sleep(1000);
        System.out.println(".");
      } catch (InterruptedException e) {
        System.out.println("Something happened");
      }
      
    }

    for (int k = 0; k < 2; k++) {
      try {
        Thread.sleep(1000);
        System.out.println("Account created!");
      } catch (InterruptedException e) {
        System.out.println("Something happened");
      }
    }

    Main.clearScreen();

    login();
    
  }

  public void verification() throws InterruptedException {
    boolean found = false;
    boolean isVerified = false;
    slowPrint("=================================\n");
    System.out.println("|\t\tUser Verification\t\t|");
    slowPrint("=================================\n");
    pause(1);
    slowPrint("Please enter your username :\n");
    name = scan.nextLine();
    System.out.println();
    
    for (int i = 0; i < acct_name.size(); i++) {
      if (acct_name.get(i).equalsIgnoreCase(name)) {
        indexOfElement = i;
        found = true;
        if (i == 0) {
          bankAdministrator = true;
        }
        break;

      } 
    }

    
    if (found) {
      pinVerification(); 
    } else {
      invalidAttempts++;
      Main.clearScreen();
      System.out.println("Sorry, we do not recognize this account\nReturning you to the login screen . . .");
      pause(3);
      Main.clearScreen();
      login();
    }
    
    
    
  }

  public void pinVerification() throws InterruptedException {
    boolean isVerified = false;
    boolean found = false;
    int invalidAttempts = 0;
    slowPrint("Please enter your PIN :\n");
    userInput = errorCatch();
    System.out.println();

    for (int i = 0; i < pin.size(); i++) {
      if (userInput == pin.get(i) && indexOfElement == i) {
        found = true;
        break;
      } 
    } 
      
    if (found == true) {
      slowPrint("Identity verified, sending to home screen");
      pause(2);
      Main.clearScreen();
      home();
    } else {
      invalidAttempts++;
      Main.clearScreen();
      System.out.println("Sorry, you have entered an incorrect PIN\nReturning you to the login screen . . .");
      pause(3);
      Main.clearScreen();
      login();
    }
    
    
    
  }

  public void deposit() throws InterruptedException {
    boolean isValid = false;
    boolean isSavings = false;
    boolean isCheckings = false;
    String input;
    String choice = "";

    while (!isValid) {
      Main.clearScreen();
      displayInformation();
      pause(1);
      slowPrint("Please choose an account:\nCheckings [C] || Savings [S]\n");
      input = scan.next();

      if (input.equalsIgnoreCase("c") || input.equalsIgnoreCase("Checkings")) {
      choice = "CHECKINGS";
      isCheckings = true;
      isValid = true;
      } else if (input.equalsIgnoreCase("s") || input.equalsIgnoreCase("Savings")) {
      choice = "SAVINGS";
      isSavings = true;
      isValid = true;
      } else {
        slowPrint("Please enter either [C] or [S]\n");
        input = scan.next();
      }
    }

    slowPrint("\nPlease enter the amount you wish to deposit into your " + choice + " account.\n");
    depositAmount = errorCatch();

    while (depositAmount > max_amount) {
      slowPrint("\nYou cannot deposit an amount greater than $" + max_amount + " in one session.\n");
      slowPrint("Please enter a new deposit amount.\n");
      depositAmount = errorCatch();
    }

    while (depositAmount < 0) {
      slowPrint("\nPlease enter a number greater than or equal to [0]\n");
      withdrawalAmount = errorCatch() * 20;
    }

    if (isCheckings) {
      checkingAccount.set(indexOfElement, checkingAccount.get(indexOfElement) + depositAmount);
    } else if (isSavings) {
      savingsAccount.set(indexOfElement, savingsAccount.get(indexOfElement) + depositAmount);
    }

    slowPrint("\nYou have deposited [$" + depositAmount + "]");
    slowPrint("\nPlease wait while we process this change\n");

    for (int i = 0; i < (int) (Math.random() * 10) + 3; i++) {
      try {
        Thread.sleep(1000);
        System.out.println(".");
      } catch (InterruptedException e){
        System.out.println("Something happened");
      }
    }
      
    slowPrint("Transaction complete!\n");
    slowPrint("Updated Checking account balance: $" + checkingAccount.get(indexOfElement) + "\n");
    slowPrint("Updated Savings account balance: $" + savingsAccount.get(indexOfElement) + "\n");
    slowPrint("Press [Enter] to continue\n");

    scan.nextLine();
    Main.clearScreen();

    home();
  }


  public void withdrawal() throws InterruptedException {
    boolean isValid = false;
    boolean isCheckings = false;
    boolean isSavings = false;
    String input;
    String choice = "";
    int currentBalance = 0;

    while (!isValid) {
      Main.clearScreen();
      displayInformation();
      pause(1);
      slowPrint("Please choose an account:\nCheckings [C] || Savings [S]\n");
      input = scan.nextLine();

      if (input.equalsIgnoreCase("c") || input.equalsIgnoreCase("Checkings")) {
      choice = "CHECKINGS";
      isCheckings = true;
      currentBalance = checkingAccount.get(indexOfElement);
      isValid = true;
      } else if (input.equalsIgnoreCase("s") || input.equalsIgnoreCase("Savings")) {
      choice = "SAVINGS";
      isSavings = true;
      currentBalance = savingsAccount.get(indexOfElement);
      isValid = true;
      } else {
        slowPrint("Please enter either [C] or [S]\n");
        input = scan.nextLine();
      }
    }

    slowPrint("\nHow many times would you like to withdraw from your " + choice + " account?\n(In $20 increments)\n");
    withdrawalAmount = errorCatch() * 20;

    while (withdrawalAmount > currentBalance) {
      slowPrint("\nYou attempted to withdraw [$" + withdrawalAmount + "] \n You currently do not have enought money in your " + choice + " account\n");
      slowPrint("Please enter a new number\n");
      withdrawalAmount = errorCatch() * 20;
    }

    while (withdrawalAmount < 0) {
      slowPrint("\nPlease enter a number greater than or equal to [0]\n");
      withdrawalAmount = errorCatch() * 20;
    }

    while (withdrawalAmount > max_amount) { // User can only withdrawal 500 per session
      slowPrint("\nSorry, you can only withdraw $" + max_amount + " per session\n");
      slowPrint("You attempted to withdrawal $" + withdrawalAmount + "\n");
      slowPrint("Please enter a new amount\n");
      withdrawalAmount = errorCatch() * 20;
    } 

    if (withdrawalAmount <= currentBalance && isCheckings){
      checkingAccount.set(indexOfElement, checkingAccount.get(indexOfElement) - withdrawalAmount);
    }                                                               // determines which account to remove money from
    else if (withdrawalAmount <= currentBalance && isSavings){
      savingsAccount.set(indexOfElement, savingsAccount.get(indexOfElement) - withdrawalAmount);
    }
    slowPrint("\nYou have withdrawn [$" + withdrawalAmount + "]\n");
    slowPrint("Please wait while we process this change\n");

    for (int i = 0; i < (int) (Math.random() * 10) + 3; i++) {
      try {
        Thread.sleep(1000); // 1 second                         
        System.out.println(".");
      } catch (InterruptedException e) {
        System.out.println("Something happened");
      }
      
    }

    slowPrint("Transaction complete!\n");
    slowPrint("Updated Checking account balance: $" + checkingAccount.get(indexOfElement) + "\n");
    slowPrint("Updated Savings account balance: $" + savingsAccount.get(indexOfElement) + "\n"); // Updates the account balance
    slowPrint("Press [Enter] to continue\n");

    scan.nextLine();
    Main.clearScreen();

    home();
  }


  public void fastCash() throws InterruptedException {
    boolean isValid = false;
    boolean isCheckings = false;
    boolean isSavings = false;
    String choice = "";
    String input;

    while (!isValid) {
      Main.clearScreen();
      displayInformation();
      pause(1);
      slowPrint("Please choose an account:\nCheckings [C] || Savings [S]\n");
      input = scan.nextLine();

      if (input.equalsIgnoreCase("c") || input.equalsIgnoreCase("Checkings")) {
      choice = "CHECKINGS";
      isCheckings = true;
      isValid = true;
      } else if (input.equalsIgnoreCase("s") || input.equalsIgnoreCase("Savings")) {
      choice = "SAVINGS";
      isSavings = true;
      isValid = true;
      } else {
        slowPrint("\nPlease enter either [C] or [S]\n");
        input = scan.nextLine();
      }
    }

    slowPrint("\nPlease input a number for the amount of cash you would like to withdrawal from your " + choice + " account\n");
    slowPrint("[1] = $20\n");
    slowPrint("[2] = $40\n");
    slowPrint("[3] = $60\n"); 
    slowPrint("[4] = $80\n");
    slowPrint("[5] = $100\n");
    userInput = errorCatch();

    while (userInput > 5 || userInput < 1) { 
      slowPrint("\nSorry, there are no fast cash amounts greater than [5] and less than [1]\n");
      userInput = errorCatch();
    }

    if (isCheckings) { 
      switch (userInput) {
        case 1:
        slowPrint("\nYou have withdrawn $20\n");
        checkingAccount.set(indexOfElement, checkingAccount.get(indexOfElement) - 20);
        break;
        case 2:
        slowPrint("\nYou have withdrawn $40\n");
        checkingAccount.set(indexOfElement, checkingAccount.get(indexOfElement) - 40);
        break;
        case 3:
        slowPrint("\nYou have withdrawn $60\n");
        checkingAccount.set(indexOfElement, checkingAccount.get(indexOfElement) - 60);
        break;
        case 4:
        slowPrint("\nYou have withdrawn $80\n");
        checkingAccount.set(indexOfElement, checkingAccount.get(indexOfElement) - 80);
        break;
        case 5:
        slowPrint("\nYou have withdrawn $100\n");
        checkingAccount.set(indexOfElement, checkingAccount.get(indexOfElement) - 100);
        break;
      }
    } else if (isSavings) {                          
      switch (userInput) {
        case 1:
        slowPrint("\nYou have withdrawn $20\n");
        savingsAccount.set(indexOfElement, savingsAccount.get(indexOfElement) - 20);
        break;
        case 2:
        slowPrint("\nYou have withdrawn $40\n");
        savingsAccount.set(indexOfElement, savingsAccount.get(indexOfElement) - 40);
        break;
        case 3:
        slowPrint("\nYou have withdrawn $60\n");
        savingsAccount.set(indexOfElement, savingsAccount.get(indexOfElement) - 60);
        break;
        case 4:
        slowPrint("\nYou have withdrawn $80\n");
        savingsAccount.set(indexOfElement, savingsAccount.get(indexOfElement) - 80);
        break;
        case 5:
        slowPrint("\nYou have withdrawn $100\n");
        savingsAccount.set(indexOfElement, savingsAccount.get(indexOfElement) - 100);
        break;
      }
    }

    slowPrint("\nPlease wait while we process this change\n");

    for (int i = 0; i < (int) (Math.random() * 10) + 3; i++) {
      try {
        Thread.sleep(1000);
        System.out.println(".");
      } catch (InterruptedException e) {
        System.out.println("Something happened");
      }
      
    }

    slowPrint("Transaction complete!\n");
    slowPrint("Updated Checking account balance: $" + checkingAccount.get(indexOfElement) + "\n");
    slowPrint("Updated Savings account balance: $" + savingsAccount.get(indexOfElement) + "\n");
    slowPrint("Press [Enter] to continue\n");

    scan.nextLine();
    Main.clearScreen();

    home();

  }

  public void defaultAccount() {
    pin.add(1234);
    acct_name.add("Bank");
    checkingAccount.add(500000);
    savingsAccount.add(500000);
  }

  public void administratorHome() {
    int total = checkingAccount.get(indexOfElement) + savingsAccount.get(indexOfElement);
    System.out.println("************************************************************************************************");
    System.out.println("\n\t\t\t\t\t\t\t\t\tWelcome Administrator\n");
    System.out.println("\t\t\t\t\t\t\tCheckings : $" + checkingAccount.get(indexOfElement) + "\t Savings : $" + savingsAccount.get(indexOfElement) + "\n");
    System.out.println("\t\t\t\t\t\t\t\tTotal Account Balance : $" + total + "\n");
    System.out.println("[1] for Deposit \t\t\t\t\t[2] for Withdrawal \t\t\t\t\t[3] for FastCash\n");
    System.out.println("[4] to Exit \t\t\t\t\t\t[5] to Logout and Return \t\t\t[6] for Bank Settings");
    System.out.println("\t\t\t\t\t\t\t\t\t\tto the Login Screen\n");
    System.out.println("************************************************************************************************");
  }

  public void settings() {
    boolean isVerified = false;
    String input;
    System.out.println("***************************************************************************************************************");
    System.out.println("\n\t\t\t\t\t\t\t\t\tWelcome Administrator\n");
    System.out.println("[1] to view list of users \t\t\t [2] to change the max deposit/withdraw amount \t\t\t [3] to return home\n");
    System.out.println("***************************************************************************************************************");
    input = scan.nextLine();

    while (isVerified = true) {
      if (input.equalsIgnoreCase("1")) {
        System.out.println("User name :");
        for (String x : acct_name) {
          System.out.println(x);
        }
        System.out.println();
        System.out.println("PIN numbers :");
        for (int x : pin) {
          System.out.println(x);
        }
        System.out.println();
        System.out.println("Checking Account Balances :");
        for (int x : checkingAccount) {
          System.out.println(x);
        }
        System.out.println();
        System.out.println("Savings Account Balances :");
        for (int x : savingsAccount) {
          System.out.println(x);
        }
        System.out.println();
        System.out.println("Press [Enter] to continue");
        scan.nextLine();
        Main.clearScreen();
        settings();
        isVerified = true;
      } else if (input.equalsIgnoreCase("2")) {
        System.out.println("The current maximum deposit/withdraw amount is [" + max_amount + "]");
        System.out.println("What would you like to change this amount to?");
        max_amount = errorCatch();
        scan.nextLine();
        System.out.println("The max amount is now [" + max_amount + "] \nPress [Enter] to continue");
        scan.nextLine();
        Main.clearScreen();
        settings();
        isVerified = true;
      } else if (input.equalsIgnoreCase("3")) {
        Main.clearScreen();
        isVerified = true;
        home();
      } else {
        System.out.println("Only options [1] - [3] are available");
        input = scan.nextLine();
      }
    }
   
  }


  public int errorCatch() {
    boolean isValid = false;

    while (isValid == false) {
      if (scan.hasNextInt()) {
        userInput = scan.nextInt();
        scan.nextLine();
      } else {
        System.out.println("\nPlease enter only integers with no decimals");
        scan.next();
        continue;
      }
      isValid = true;
    }

    return userInput;

  }

  public void duplicateCheck() throws InterruptedException { 
    int duplicateIndex = 0;
    boolean isDuplicate = false;

    name = scan.nextLine();
    acct_name.add(name);

    for (int i = 0; i < acct_name.size(); i++ ) {
      for (int j = i + 1; j < acct_name.size(); j++) {
        if (acct_name.get(i).equalsIgnoreCase(acct_name.get(j)) && acct_name.get(j).equalsIgnoreCase(name)) {
          isDuplicate = true;
          duplicateIndex = j;
        }
      }
    }

    if (isDuplicate == true) {
      slowPrint("Username already taken. Returning you to the login screen . . .");
      for (int k = 0; k < 4; k++) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          System.out.println("Something happened");
        }
      }
      acct_name.remove(duplicateIndex);
      Main.clearScreen();
      login();
    }
    
  }

  public static void slowPrint(String text) throws InterruptedException {
    char[] chars = text.toCharArray();

    for (int i = 0; i < chars.length; i++) {
      System.out.print(chars[i]);
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        System.out.println("Something happened");
      }
    
    }

    chars = null;
  }

  public void pause(int amount) {
    for (int i = 0; i < amount; i++) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        System.out.println("Something happened");
      }
    }
  }
}