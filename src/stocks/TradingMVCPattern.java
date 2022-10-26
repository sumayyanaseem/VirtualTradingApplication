package stocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import stocks.view.TradingView;

public class TradingMVCPattern {
  public static void main(String[] args) {

    //fetch student record based on his roll no from the database
    TradingModel model  = new TradingModel();

    //Create a view : to write student details on console
    TradingView view = new TradingView();

    TradingController controller = new TradingController(model, view);

    Scanner myObj = new Scanner(System.in);  // Create a Scanner object
    System.out.println("Enter 1: to view the portfolio composition, 2: to trade a stock");
    String initialAction = myObj.nextLine();
    List<String> availablePortfolios = new ArrayList<>();
    availablePortfolios = view.getAvailablePortfolios();
    if(initialAction.equals("1")){
      printAvailablePortfolios(availablePortfolios);
      String selectedPortfolioName = getSelectedPortfolio(availablePortfolios, myObj);
      System.out.println("Enter date for which you want to view the composition (mm/dd/yyyy):");
      String dateStr = myObj.nextLine();
      System.out.println(view.viewPortfolioComposition(selectedPortfolioName,dateStr));
    }
    else {
      System.out.println("Enter 1: if you want to add to one of your existing portfolios, 2: if you want to create a new portfolio");
      String portfolioSelection = myObj.nextLine();
      if(portfolioSelection.equals("1")){
        printAvailablePortfolios(availablePortfolios);
        String selectedPortfolioName = getSelectedPortfolio(availablePortfolios, myObj);
        System.out.println("Enter the id of the company stock that you want to trade");
        String companyStr = myObj.nextLine();
        System.out.println("What action do you want to perform on the stock? Enter 1. for buy, 2. for sell");
        String action = myObj.nextLine();
        if(action.equals("1")){
          System.out.println("Enter the quantity you want to buy:");
          String buyQty = myObj.nextLine();
          int buyQtyNumber=Integer.valueOf(buyQty);
          model.buyStock(selectedPortfolioName,companyStr,buyQtyNumber);

        }
        else {
          System.out.println("Your selected portfolio contains below stocks. Choose the number below to sell");
          printStocksAvailableInPortfolio(selectedPortfolioName);
          String selectedStockNumber = myObj.nextLine();
          getSelectedStockFromPortfolio();
          model.sellStock(selectedPortfolioName,companyStr,buyQtyNumber);

        }

      }

    }


    String userName = myObj.nextLine();  // Read user input
    System.out.println("Username is: " + userName);  // Output user input

    //controller.updateView();

    //update model data
    //controller.setStudentName("John");

    //controller.updateView();
  }

  private static void printStocksAvailableInPortfolio(String selectedPortfolioName) {

  }

  private static String getSelectedPortfolio(List<String> availablePortfolios, Scanner myObj) {
    String selectedPortfolioNumberStr = myObj.nextLine();
    int portfolioNumber = Integer.valueOf(selectedPortfolioNumberStr);
    // if portfolioNumber<0 || portfolioNumber>size then throw exception
    return availablePortfolios.get(portfolioNumber-1);
  }

  private static void printAvailablePortfolios(List<String> availablePortfolios){
    System.out.println("Select portfolio number from your available portfolios displayed below");
    for(int i=0;i<availablePortfolios.size();i++)
    {
      System.out.println(i+1+". "+ availablePortfolios.get(0));
    }
  }
}
