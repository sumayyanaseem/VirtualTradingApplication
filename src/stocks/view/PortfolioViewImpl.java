package stocks.view;

import java.io.InputStream;
import java.util.Scanner;

public class PortfolioViewImpl implements PortfolioView {

  private InputStream input;

  public PortfolioViewImpl(){
    input= System.in;
  }
  @Override
  public String callToViewToChooseCreateOrView() {
    System.out.println("Enter 1: To trade stocks 2: To view the portfolio composition");
    Scanner myObj = new Scanner(input);
    return myObj.nextLine();
  }

 /* @Override
  public void callToViewTODisplay(Json Json) {
  }*/

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

  private void validateQuantity(String quantity){
    try {
      Integer q = Integer.parseInt(quantity);
      if(q<=0){
        throw new IllegalArgumentException("Invalid quantity provided");
      }
    } catch(IllegalArgumentException e){
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
    System.out.println("Enter 1: To continue trading in current portfolio.  2: To exit");
    Scanner myObj = new Scanner(input);
    return myObj.nextLine();
  }

  @Override
  public String checkIfUserWantsToExitCompletely() {
    System.out.println("Enter 1: To continue trading . 2: To exit");
    Scanner myObj = new Scanner(input);
    return myObj.nextLine();
  }

  @Override
  public String createOrUpdateExistingPortfolio() {
    System.out.println("Enter 1: To create a portfolio. 2: To update a existing portfolio.");
    Scanner myObj = new Scanner(input);
    return myObj.nextLine();
  }

}