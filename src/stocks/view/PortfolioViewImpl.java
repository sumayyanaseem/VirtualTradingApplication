package stocks.view;

import java.util.Scanner;

public class PortfolioViewImpl implements PortfolioView {
  @Override
  public String callToViewToChooseCreateOrView() {
    System.out.println("Enter 1: To view the portfolio composition 2: To trade a stock");
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
    System.out.println("Enter the name of the Portfolio, you wanna create");
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
}