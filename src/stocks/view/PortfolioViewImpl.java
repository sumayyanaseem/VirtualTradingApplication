package stocks.view;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class PortfolioViewImpl implements PortfolioView {

  private InputStream input;

  public PortfolioViewImpl() {
    input = System.in;
  }

  @Override
  public String callToViewToChooseCreateOrView() {
    System.out.println("Enter 1: To trade stocks 2: To view the portfolio composition");
    Scanner myObj = new Scanner(input);
    return myObj.nextLine();
  }

  @Override
  public String callToViewOnHowToCreatePortfolio() {
    System.out.println("Enter 1: To create Portfolio using external file  2: To create manually");
    Scanner myObj = new Scanner(input);
    return myObj.nextLine();
  }

  @Override
  public String callToViewToGetFilePath() {
    System.out.println("Enter the path of File which is used to create Portfolio");
    Scanner myObj = new Scanner(input);
    return myObj.nextLine();
  }

  @Override
  public String callToViewToAskPortfolioName() {
    System.out.println("Enter the name of the Portfolio");
    Scanner myObj = new Scanner(input);
    return myObj.nextLine();
  }

  @Override
  public String callToViewToChoiceOption() {
    System.out.println("Enter 1: To buy stocks 2: To sell stocks");
    Scanner myObj = new Scanner(input);
    return myObj.nextLine();
  }

  @Override
  public String callToViewToAskQuantity() {
    System.out.println("Enter the quantity of the stocks.");
    Scanner myObj = new Scanner(input);
    String quantity = myObj.nextLine();
    validateQuantity(quantity);
    return quantity;
  }

  private void validateQuantity(String quantity) {
    try {
      Integer q = Integer.parseInt(quantity);
      if (q <= 0) {
        throw new IllegalArgumentException("Invalid quantity provided");
      }
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Quantity should be always a whole number");
    }
  }

  @Override
  public String callToViewToAskCompanyTicker() {
    System.out.println("Enter the name of the company");
    Scanner myObj = new Scanner(input);
    String companyName = myObj.nextLine();
    //validateCompanyName(companyName);
    return companyName;
  }

  @Override
  public String askUserIfHeWantsToContinueTradingInCurrentPortfolio() {
    System.out.println("Enter 1: To continue trading in current portfolio.  2: To exit from current Portfolio.");
    Scanner myObj = new Scanner(input);
    return myObj.nextLine();
  }

  @Override
  public String checkIfUserWantsToExitCompletely() {
    System.out.println("Enter 1: To continue trading further. 2: To exit from this session.");
    Scanner myObj = new Scanner(input);
    return myObj.nextLine();
  }

  @Override
  public String createOrUpdateExistingPortfolio() {
    System.out.println("Enter 1: To create a portfolio. 2: To update a existing portfolio.");
    Scanner myObj = new Scanner(input);
    return myObj.nextLine();
  }

  @Override
  public String checkIfUserWantsToViewCompositionOrTotalValue() {
    System.out.println("Enter 1: To view composition of existing portfolio . 2: To get TotalValue of a portfolio");
    Scanner myObj = new Scanner(input);
    return myObj.nextLine();
  }

  @Override
  public String getPortfolioNameToView() {
    System.out.println("Enter the name of the portfolio you wanna view");
    Scanner myObj = new Scanner(input);
    //validate if this portfolio exists.
    return myObj.nextLine();
  }

  @Override
  public void displayTotalValue(String date, String val, String portfolioName) {
    System.out.println("Total Valuation of Portfolio " + portfolioName + " is :" + val);
  }

  @Override
  public String getDateForValuation() {
    System.out.println("Enter date as of which you need portfolio valuation( YYYY-MM-DD format only)");
    Scanner myObj = new Scanner(System.in);
    return myObj.nextLine();
  }


  @Override
  public void displayComposition(List<List<String>> records) {
    for (int i = 0; i < records.size(); i++) {

      for (int j = 0; j < records.get(i).size(); j++) {
        System.out.print(records.get(i).get(j));
        int len1 = records.get(0).get(j).length();
        int len2 = records.get(i).get(j).length();
        int len = len1 - len2;
        for (int k = 0; k < len; k++) {
          System.out.print(" ");
        }
        System.out.print(" ");
      }
      System.out.println("");
    }
  }


}