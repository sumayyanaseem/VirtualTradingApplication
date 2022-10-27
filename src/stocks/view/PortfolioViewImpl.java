package stocks.view;

import java.util.Scanner;

public class PortfolioViewImpl implements PortfolioView {
  @Override
  public String callToViewToChooseCreateOrView() {
    System.out.println("Enter 1: To trade stocks 2: To view the portfolio composition");
    Scanner myObj = new Scanner(System.in);
    return myObj.nextLine();
  }

 /* @Override
  public void callToViewTODisplay(Json Json) {
  }*/

  @Override
  public String callToViewOnHowToCreatePortfolio() {
    System.out.println("Enter 1: To create Portfolio using external file  2: To create manually");
    Scanner myObj = new Scanner(System.in);
    return myObj.nextLine();
  }

  @Override
  public String callToViewToGetFilePath() {
    System.out.println("Enter the path of File which is used to create Portfolio");
    Scanner myObj = new Scanner(System.in);
    return myObj.nextLine();
  }

  @Override
  public String callToViewToAskPortfolioName() {
    System.out.println("Enter the name of the Portfolio");
    Scanner myObj = new Scanner(System.in);
    return myObj.nextLine();
  }

  @Override
  public String callToViewToChoiceOption() {
    System.out.println("Enter 1: To buy stocks 2: To sell stocks");
    Scanner myObj = new Scanner(System.in);
    return myObj.nextLine();
  }

  @Override
  public String callToViewToAskQuantity() {
    System.out.println("Enter the quantity of the stocks.");
    Scanner myObj = new Scanner(System.in);
    return myObj.nextLine();
  }

  @Override
  public String callToViewToAskCompanyTicker() {
    System.out.println("Enter the name of the company");
    Scanner myObj = new Scanner(System.in);
    return myObj.nextLine();
  }

  @Override
  public String askUserIfHeWantsToContinue() {
    System.out.println("Enter yes: To continue trading.  No: To exit");
    Scanner myObj = new Scanner(System.in);
    return myObj.nextLine();
  }

  @Override
  public String checkIfUserWantsToExit() {
    System.out.println("Enter yes: To continue trading  with portfolio. No: To exit");
    Scanner myObj = new Scanner(System.in);
    return myObj.nextLine();
  }

  @Override
  public String createOrUpdateExistingPortfolio() {
    System.out.println("Enter create: To create a portfolio. update: To update a existing portfolio.");
    Scanner myObj = new Scanner(System.in);
    return myObj.nextLine();
  }

}