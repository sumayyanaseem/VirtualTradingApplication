package stocks.view;

import java.io.PrintStream;
import java.util.List;

public class PortfolioViewImpl implements PortfolioView {
  private final PrintStream out;

  public PortfolioViewImpl(PrintStream out) {
    this.out = out;
  }

  @Override
  public void callToViewToChooseCreateOrView()  {
    out.println("Enter 1: To trade stocks 2: To view the portfolio composition");
  }


  @Override
  public void askUserOnHowToCreatePortfolio() {
    out.println("Enter 1: To create Portfolio using external file  2: To create manually");
  }


  @Override
  public void getFilePath() {
    out.println("Enter the path of File which is used to create Portfolio");
  }

  @Override
  public void getPortfolioName() {
    out.println("Enter the name of the Portfolio");
  }

  @Override
  public void getBuyOrSellChoiceFromUser() {
    out.println("Enter 1: To buy stocks 2: To sell stocks");
  }

  @Override
  public void getQuantity() {
    out.println("Enter the quantity of the stocks");
  }

  @Override
  public void getCompanyTicker() {
    out.println("Enter the name of the company to be added to portfolio");
  }

  @Override
  public void askUserIfHeWantsToContinueTradingInCurrentPortfolio() {
    out.println("Enter 1: To continue trading in current portfolio.  2: To exit from current Portfolio.");
  }

  @Override
  public void checkIfUserWantsToExitCompletely() {
    out.println("Enter 1: To continue trading further. 2: To exit from this session.");
  }

  @Override
  public void createOrUpdateExistingPortfolio() {
    out.println("Enter 1: To create a portfolio. 2: To update a existing portfolio.");
  }

  @Override
  public void checkIfUserWantsToViewCompositionOrTotalValue() {
    out.println("Enter 1: To view composition of existing portfolio . 2: To get TotalValue of a portfolio");
  }

  @Override
  public void displayTotalValue(String date, String val, String portfolioName) {
    out.println("Total Valuation of Portfolio " + portfolioName + "  on "+date+ " is :" + val);
  }

  @Override
  public void getDateForValuation() {
    out.println("Enter date as of which you need portfolio valuation( YYYY-MM-DD format only)");
  }


  @Override
  public void displayComposition(List<List<String>> records) {
    for (int i = 0; i < records.size(); i++) {

      for (int j = 0; j < records.get(i).size(); j++) {
        out.print(records.get(i).get(j));
        int len1 = records.get(0).get(j).length();
        int len2 = records.get(i).get(j).length();
        int len = len1 - len2;
        for (int k = 0; k < len; k++) {
          out.print(" ");
        }
        out.print(" ");
      }
      out.println();
    }
  }

}
