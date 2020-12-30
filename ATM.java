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
  private int indexOfAccounts;

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
    defaultAccount();
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
        } catch (InterruptedException e){
          System.out.println("No");
        }
      } else if (input.equalsIgnoreCase("2")) {
        Main.clearScreen();
        isValid = true;
        try {
          verification();
        } catch (InterruptedException e) {
          System.out.println("no");
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
      System.out.println("\nWelcome to your account, " + acct_name.get(indexOfElement) + "\n");
      System.out.println("Current Total Account Balance : $" + total);
      System.out.println("Checkings : $" + checkingAccount.get(indexOfElement) + "\t Savings : $" + savingsAccount.get(indexOfElement) + "\n");
      System.out.println("[1] for Deposit \t[2] for Withdrawal \t\t\t[3] for FastCash\n");
      System.out.println("[4] to exit \t \t[5] to logout and return\n \t \t \t \t \t \tto the login screen\n");
      System.out.println("************************************************************************************************");
    }
    choice = scan.nextLine();

    while(!isValid) {
      if (choice.equalsIgnoreCase("1")) {
        isValid = true;
        try {
          deposit();
        } catch (InterruptedException e) {
          System.out.println("no");
        }
      } else if (choice.equalsIgnoreCase("2")) {
        isValid = true;
        try {
          withdrawal();
        } catch (InterruptedException e) {
          System.out.println("no");
        }
      } else if (choice.equalsIgnoreCase("3")) {
        isValid = true;
        try {
          fastCash();
        } catch (InterruptedException e) {
          System.out.println("no");
        }
      } else if (choice.equalsIgnoreCase("4")) {
        isValid = true;
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

    System.out.println("============================");
    System.out.println("\t Account Creation");
    System.out.println("============================");
    int i = number_of_accounts;

    System.out.println("Enter a Username :");
    try {
      duplicateCheck();
    } catch (InterruptedException e) {
      System.out.println("No");
    }

    System.out.println("\nEnter a PIN :");
    pin.add(errorCatch());

    System.out.println("\nChecking Account Balance : ");
    checkingAccount.add(errorCatch());

    System.out.println("\nSavings Accounts Balance :");
    savingsAccount.add(errorCatch());

    Main.clearScreen();

    number_of_accounts++;

    System.out.println("Creating account....");
    for (int j = 0; j < (int) (Math.random() * 10) + 1; j++) {
      Thread.sleep(1000);
      System.out.println(".");
    }

    for (int k = 0; k < 2; k++) {
      Thread.sleep(1000);
      System.out.println("Account created!");
    }

    Main.clearScreen();

    login();
    
  }

  public void verification() throws InterruptedException {
    boolean found = false;
    boolean isVerified = false;
    System.out.println("============================");
    System.out.println("\t User Verification");
    System.out.println("============================");
    System.out.println("Please enter your user name");
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
      try {
       pinVerification(); 
      } catch (InterruptedException e) {
        System.out.println("No");
      }
    } else {
      invalidAttempts++;
      Main.clearScreen();
      System.out.println("Sorry, we do not recognize this account\nReturning you to the login screen");
      for (int i = 0; i < (int) (Math.random() * 10) + 1; i++) {
      Thread.sleep(1000);
      System.out.println(".");
      }
      Main.clearScreen();
      login();
    }
    
    
    
  }

  public void pinVerification() throws InterruptedException {
    boolean isVerified = false;
    boolean found = false;
    int invalidAttempts = 0;
    System.out.println("Please enter your PIN");
    userInput = scan.nextInt();
    scan.nextLine();
    System.out.println();

    for (int i = 0; i < pin.size(); i++) {
      if (userInput == pin.get(i) && indexOfElement == i) {
        found = true;
        break;
      } 
    } 
      
    if (found == true) {
      Main.clearScreen();
      home();
    } else {
      invalidAttempts++;
      Main.clearScreen();
      System.out.println("Sorry, you have entered an incorrect PIN\nReturning you to the login screen");
      for (int i = 0; i < (int) (Math.random() * 10) + 1; i++) {
      Thread.sleep(1000);
      System.out.println(".");
      }
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
      System.out.println("Please choose an account:\nCheckings [C] || Savings [S]");
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
        System.out.println("Please enter either [C] or [S]");
        input = scan.next();
      }
    }

    System.out.println("Please enter the amount you wish to deposit into your " + choice + " account.");
    depositAmount = errorCatch();

    while (depositAmount > max_amount) {
      System.out.println("You cannot deposit an amount greater than $" + max_amount + " in one session.");
      System.out.println("Please enter a new deposit amount.");
      depositAmount = errorCatch();
    }

    if (isCheckings) {
      checkingAccount.set(indexOfElement, checkingAccount.get(indexOfElement) + depositAmount);
    } else if (isSavings) {
      savingsAccount.set(indexOfElement, savingsAccount.get(indexOfElement) + depositAmount);
    }

    System.out.println("Please wait while we process this change.");

    for (int i = 0; i < (int) (Math.random() * 10) + 1; i++) {
      Thread.sleep(1000);
      System.out.println(".");
    }
       
    System.out.println("Transaction complete!");
    System.out.println("Updated Checking account balance: $" + checkingAccount.get(indexOfElement));
    System.out.println("Updated Savings account balance: $" + savingsAccount.get(indexOfElement));
    System.out.println("Enter any key to continue.");

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
      System.out.println("Please choose an account:\nCheckings [C] || Savings [S]");
      input = scan.next();

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
        System.out.println("Please enter either [C] or [S]");
        input = scan.next();
      }
    }

    System.out.println("How many times would you like to withdraw from your " + choice + " account?\n(In $20 increments)");
    withdrawalAmount = errorCatch() * 20;

    while (withdrawalAmount > currentBalance) {
      System.out.println("You attempted to withdraw [$" + withdrawalAmount + "] \n You currently do not have enought money in your " + choice + " account");
      System.out.println("Please enter a new number.");
      withdrawalAmount = errorCatch() * 20;
    }

    while (withdrawalAmount > max_amount) { // User can only withdrawal 500 per session
      System.out.println("Sorry, you can only withdraw $" + max_amount + " per session.");
      System.out.println("You attempted to withdrawal $" + withdrawalAmount);
      System.out.println("Please enter a new amount :");
      withdrawalAmount = errorCatch() * 20;
    } 

    if (withdrawalAmount <= currentBalance && isCheckings){
      checkingAccount.set(indexOfElement, checkingAccount.get(indexOfElement) - withdrawalAmount);
    }                                                               // determines which account to remove money from
    else if (withdrawalAmount <= currentBalance && isSavings){
      savingsAccount.set(indexOfElement, savingsAccount.get(indexOfElement) - withdrawalAmount);
    }
    System.out.println("You have withdrawn [$" + withdrawalAmount + "]");
    System.out.println("Please wait while we process this change.");

    for (int i = 0; i < (int) (Math.random() * 10) + 1; i++) {
      Thread.sleep(1000); // 1 second                         // depending on the number generated, the for loop goes to that number and prints out a period every second
      System.out.println(".");
    }

    System.out.println("Transaction complete!");
    System.out.println("Updated Checking account balance: $" + checkingAccount.get(indexOfElement));
    System.out.println("Updated Savings account balance: $" + savingsAccount.get(indexOfElement)); // Updates the account balance
    System.out.println("Enter any key to continue.");

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
      System.out.println("Please choose an account:\nCheckings [C] || Savings [S]");
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
        System.out.println("Please enter either [C] or [S]");
        input = scan.next();
      }
    }

    System.out.println("Please input a number for the amount of cash you would like to withdrawal from your " + choice + " account");
    System.out.println("1 = $20");
    System.out.println("2 = $40");
    System.out.println("3 = $60"); 
    System.out.println("4 = $80");
    System.out.println("5 = $100");
    userInput = scan.nextInt();

    while(scan.hasNextInt() == false) {
      System.out.println("Please enter a number only");
      userInput = scan.nextInt();
    }

    while (userInput > 5) { 
      System.out.println("Sorry, there are no fast cash amounts greater than \"5\". ");
      userInput = scan.nextInt();
    }

    if (isCheckings) { 
      switch (userInput) {
        case 1:
        checkingAccount.set(indexOfElement, checkingAccount.get(indexOfElement) - 20);
        break;
        case 2:
        checkingAccount.set(indexOfElement, checkingAccount.get(indexOfElement) - 40);
        break;
        case 3:
        checkingAccount.set(indexOfElement, checkingAccount.get(indexOfElement) - 60);
        break;
        case 4:
        checkingAccount.set(indexOfElement, checkingAccount.get(indexOfElement) - 80);
        break;
        case 5:
        checkingAccount.set(indexOfElement, checkingAccount.get(indexOfElement) - 100);
        break;
      }
    } else if (isSavings) {                          
      switch (userInput) {
        case 1:
        savingsAccount.set(indexOfElement, savingsAccount.get(indexOfElement) - 20);
        break;
        case 2:
        savingsAccount.set(indexOfElement, savingsAccount.get(indexOfElement) - 40);
        break;
        case 3:
        savingsAccount.set(indexOfElement, savingsAccount.get(indexOfElement) - 60);
        break;
        case 4:
        savingsAccount.set(indexOfElement, savingsAccount.get(indexOfElement) - 80);
        break;
        case 5:
        savingsAccount.set(indexOfElement, savingsAccount.get(indexOfElement) - 100);
        break;
      }
    }

    System.out.println("Please wait while we process this change.");

    for (int i = 0; i < (int) (Math.random() * 10) + 1; i++) {
      Thread.sleep(1000);
      System.out.println(".");
    }

    System.out.println("Transaction complete!");
    System.out.println("Updated Checking account balance: $" + checkingAccount.get(indexOfElement));
    System.out.println("Updated Savings account balance: $" + savingsAccount.get(indexOfElement));
    System.out.println("Enter any key to continue.");

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
    System.out.println("Current Total Account Balance : $" + total);
    System.out.println("Checkings : $" + checkingAccount.get(indexOfElement) + "\t Savings : $" + savingsAccount.get(indexOfElement) + "\n");
    System.out.println("[1] for Deposit \t[2] for Withdrawal \t\t\t\t\t\t\t\t\t[3] for FastCash\n");
    System.out.println("[4] to Exit \t \t[5] to Logout and Return to the Login Screen \t \t[6] for Bank Settings \n");
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
        System.out.println("Press any key to continue");
        scan.nextLine();
        Main.clearScreen();
        settings();
        isVerified = true;
      } else if (input.equalsIgnoreCase("2")) {
        System.out.println("The current maximum deposit/withdraw amount is [" + max_amount + "]");
        System.out.println("What would you like to change this amount to?");
        max_amount = errorCatch();
        scan.nextLine();
        System.out.println("The max amount is now [" + max_amount + "] \nEnter any key to continue");
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
        System.out.println("Please enter only integers with no decimals");
        scan.next();
        continue;
      }
      isValid = true;
    }

    return userInput;

  }

  public void duplicateCheck() throws InterruptedException {
    boolean isValid = false;
    boolean isDuplicate = false;
    int duplicateIndex = 0; 

    name = scan.nextLine();
    acct_name.add(name);

    for (int i = 0; i < acct_name.size(); i++ ) {
      for (int j = i + 1; j < acct_name.size(); j++) {
        if (j != i && acct_name.get(i).equalsIgnoreCase(acct_name.get(j))) {
          isDuplicate = true;
          duplicateIndex = j;
        }
      }
    }

    if (isDuplicate == true) {
      System.out.println("Username already taken. Returning you to the login screen......");
      for (int k = 0; k < 4; k++) {
        Thread.sleep(1000);
      }
      acct_name.remove(duplicateIndex);
      Main.clearScreen();
      login();
    } else {
      isValid = true;
      isDuplicate = false;
    } 
    
  }

}