import java.util.Scanner;
import java.lang.Thread;


public class ATM {
  Scanner scan = new Scanner(System.in);

  private final int array_length = 30;
  private int userInput;
  private String name;
  private int number_of_accounts = 1;
  private int indexOfElement;

  private boolean bankAdministrator = false;
  private static int max_amount = 500; 
  private int withdrawalAmount;
  private int depositAmount;

  int[] checkingAccount = new int[array_length];
  int[] savingsAccount = new int[array_length];
  int[] pin = new int[array_length];
  String[] acct_name = new String[array_length];

  public void login() {
    defaultAccount();
    boolean isValid = false;
    String input;
    System.out.println("**************************************************************");
    System.out.println("Welcome to the Mars branch of the Polish National Bank!");
    Art.polishFlag();
    System.out.println("Press [1] to create a new account ");
    System.out.println("Press [2] to login to an existing account \n");
    System.out.println("**************************************************************");

    input = scan.next();
    scan.nextLine();


    while (isValid = true) {
      if (input.equalsIgnoreCase("1")) {
        Main.clearScreen();
        isValid = true;
        accountCreation();
      } else if (input.equalsIgnoreCase("2")) {
        Main.clearScreen();
        isValid = true;
        verification();
      } else {
        System.out.println("Please enter only [1] or [2]");
        input = scan.next();
      }
    }

  }

  public void home(){
    String choice;
    boolean isValid = false;

    System.out.println("**************************************************************");
    System.out.println("\nWelcome to your account, " + acct_name[indexOfElement] + "\n");
    System.out.println("Current Total Account Balance : $" + checkingAccount[indexOfElement] + savingsAccount[indexOfElement]);
    System.out.println("Checkings : $" + checkingAccount[indexOfElement] + "\t Savings : $" + savingsAccount[indexOfElement]);
    System.out.println("[1] for Deposit \t [2] for Withdrawal \t [3] for FastCash\n");
    if (bankAdministrator = true) {
      System.out.println("[4] to exit \t [5] to logout and return to the logic screen \t [6] for bank settings \n");
    } else {
      System.out.println("[4] to exit \t [5] to logout and return to the logic screen");
    }
    System.out.println("**************************************************************");

    choice = scan.next();

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
      } else if (bankAdministrator = true && choice.equalsIgnoreCase("6")){
        isValid = true;
        Main.clearScreen();
        settings();
      } else {
        System.out.println("Only choices [1] - [5] are available");
        choice = scan.next();
      }
    }

  }

  public void displayInformation() {
    System.out.println("**************************************************************");
    System.out.println("Current Total Account Balance : $" + checkingAccount[indexOfElement] + savingsAccount[indexOfElement]);
    System.out.println("Checkings : $" + checkingAccount[indexOfElement] + "\t Savings : $" + savingsAccount[indexOfElement]);
    System.out.println("**************************************************************");
  }


  public void accountCreation() {
    String input;

    if (number_of_accounts == array_length) {
      System.out.println("The maximum amount of accounts has been created");
      System.out.println("Press any key to return to the login screen");
      input = scan.next();
      verification();
    } else {
      int i = number_of_accounts;

      System.out.println("Enter a name for this account ");
      name = scan.nextLine();
      acct_name[i] = name;
     

      System.out.println("\nEnter a 4 digit pin for this account ");
      userInput = scan.nextInt();
      scan.nextLine();
      pin[i] = userInput;
      

      System.out.println("\nChecking Account Balance : ");
      userInput = scan.nextInt();
      scan.nextLine();
      checkingAccount[i] = userInput;
      

      System.out.println("\nSavings Accounts Balance :");
      userInput = scan.nextInt();
      scan.nextLine();
      savingsAccount[i] = userInput;

      Main.clearScreen();

      number_of_accounts++;
    }

    login();
    
  }

  public void verification() {
    boolean found = false;
    System.out.println("Please enter your user name");
    name = scan.nextLine();
    System.out.println();
    
    while(found = true) {
      for (int i = 0; i < array_length; i++) {
        if (acct_name[i].equals(name)) {
          found = true; 
          break;
        } else {
          System.out.println("\nSorry, we do not recognize that account, please enter another name");
          name = scan.nextLine();
          scan.nextLine();
        }
      }
      break;
    }

    if (found) {
      pinVerification();
    } 

  }

  public void pinVerification() {
    boolean isVerified = false;
    System.out.println("Please enter your 4 digit pin for this account");
    userInput = scan.nextInt();

    while(!isVerified){
      for (int i = 0; i < array_length; i++) {
        int invalidAttempts = 0;
        
        if (userInput == pin[i] && i == indexOfElement) {
          System.out.println("Pin verified");
          isVerified = true;
          Main.clearScreen();
          home();
          break;
        } else {
          invalidAttempts++;

          switch (invalidAttempts) { 
            case 1:
            System.out.println("You have input the incorrect pin, please input the correct 4 digit pin number for this account");
            System.out.println("WARNING: You have 3 attempts left.");
            break;
            case 2:
            System.out.println("WARNING: You have 2 attempts left.");
            break;
            case 3:
            System.out.println("WARNING: You have 1 attempt left.");
            System.out.println("Failure to enter the correct pin will result in session termination.");
            case 4:
            System.out.println("Session terminated.");
            System.exit(0);
          }

        }
      }
    }
    
  }

  public void deposit() throws InterruptedException {
    boolean isValid = false;
    boolean isSavings = false;
    boolean isCheckings = false;
    String input;
    String choice = "";

    while (!isValid) {
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
    depositAmount = scan.nextInt();

    while (depositAmount > max_amount) {
      System.out.println("You cannot deposit an amount greater than $" + max_amount + " in one session.");
      System.out.println("Please enter a new deposit amount.");
      depositAmount = scan.nextInt();
    }

    if (isCheckings) {
      checkingAccount[indexOfElement] += depositAmount;
    } else if (isSavings) {
      savingsAccount[indexOfElement] += depositAmount;
    }

    System.out.println("Please wait while we process this change.");

    for (int i = 0; i < (int) (Math.random() * 10) + 1; i++) {
      Thread.sleep(1000);
      System.out.println(".");
    }
       
    System.out.println("Transaction complete!");
    System.out.println("Updated Checking account balance: $" + checkingAccount[indexOfElement]);
    System.out.println("Updated Savings account balance: $" + savingsAccount[indexOfElement]);
    System.out.println("Enter any key to continue.");

    scan.next();
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
      System.out.println("Please choose an account:\nCheckings [C] || Savings [S]");
      input = scan.next();

      if (input.equalsIgnoreCase("c") || input.equalsIgnoreCase("Checkings")) {
      choice = "CHECKINGS";
      isCheckings = true;
      currentBalance = checkingAccount[indexOfElement];
      isValid = true;
      } else if (input.equalsIgnoreCase("s") || input.equalsIgnoreCase("Savings")) {
      choice = "SAVINGS";
      isSavings = true;
      currentBalance = savingsAccount[indexOfElement];
      isValid = true;
      } else {
        System.out.println("Please enter either [C] or [S]");
        input = scan.next();
      }
    }

    System.out.println("How many times would you like to withdraw from your " + choice + " account?\n(In $20 increments)");
    withdrawalAmount = scan.nextInt() * 20;

    while (withdrawalAmount > currentBalance) {
      System.out.println("You attempted to withdraw [$" + withdrawalAmount + "] \n You currently do not have enought money in your " + choice + " account");
      System.out.println("Please enter a new number.");
      withdrawalAmount = scan.nextInt() * 20;
    }

    while (withdrawalAmount > max_amount) { // User can only withdrawal 500 per session
      System.out.println("Sorry, you can only withdraw $" + max_amount + " per session.");
      System.out.println("You attempted to withdrawal $" + withdrawalAmount);
      System.out.println("Please enter a new amount :");
      withdrawalAmount = scan.nextInt() * 20;
    } 

    if (withdrawalAmount <= currentBalance && isCheckings){
      checkingAccount[indexOfElement] -= withdrawalAmount;
    }                                                               // determines which account to remove money from
    else if (withdrawalAmount <= currentBalance && isSavings){
      savingsAccount[indexOfElement] -= withdrawalAmount;
    }

    System.out.println("Please wait while we process this change.");

    for (int i = 0; i < (int) (Math.random() * 10) + 1; i++) {
      Thread.sleep(1000); // 1 second                         // depending on the number generated, the for loop goes to that number and prints out a period every second
      System.out.println(".");
    }

    System.out.println("Transaction complete!");
    System.out.println("Updated Checking account balance: $" + checkingAccount[indexOfElement]);
    System.out.println("Updated Savings account balance: $" + savingsAccount[indexOfElement]); // Updates the account balance
    System.out.println("Enter any key to continue.");

    home();
  }


  public void fastCash() throws InterruptedException {
    boolean isValid = false;
    boolean isCheckings = false;
    boolean isSavings = false;
    String choice = "";
    String input;

    while (!isValid) {
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
        checkingAccount[indexOfElement] -= 20;
        break;
        case 2:
        checkingAccount[indexOfElement] -= 40;
        break;
        case 3:
        checkingAccount[indexOfElement] -= 60;
        break;
        case 4:
        checkingAccount[indexOfElement] -= 80;
        break;
        case 5:
        checkingAccount[indexOfElement] -= 100;
        break;
      }
    } else if (isSavings) {                           
      switch (userInput) {
        case 1:
        savingsAccount[indexOfElement] -= 20;
        break;
        case 2:
        savingsAccount[indexOfElement] -= 40;
        break;
        case 3:
        savingsAccount[indexOfElement] -= 60;
        break;
        case 4:
        savingsAccount[indexOfElement] -= 80;
        break;
        case 5:
        savingsAccount[indexOfElement] -= 100;
        break;
      }
    }

    System.out.println("Please wait while we process this change.");

    for (int i = 0; i < (int) (Math.random() * 10) + 1; i++) {
      Thread.sleep(1000);
      System.out.println(".");
    }

    System.out.println("Transaction complete!");
    System.out.println("Updated Checking account balance: $" + checkingAccount[indexOfElement]);
    System.out.println("Updated Savings account balance: $" + savingsAccount[indexOfElement]);
    System.out.println("Enter any key to continue.");

    scan.next();
    Main.clearScreen();

    home();

  }

  public void defaultAccount() {
    pin[0] = 1234;
    acct_name[0] = "Bank Administrator";
    checkingAccount[0] = 500000;
    savingsAccount[0] = 500000;
  }

  public void settings() {
    System.out.println("**************************************************************");
    System.out.println("Welcome Administrator");
    System.out.println("**************************************************************");
  }
}