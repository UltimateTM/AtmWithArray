import java.util.Scanner;

class Main {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    String userInput;
    Atm atm = new Atm();

    clearScreen();
    

    atm.login();
    
    
  }

  public static void clearScreen() { // clear console of any text   
  System.out.print("\033[H\033[2J");  
  System.out.flush();  
  } 
}