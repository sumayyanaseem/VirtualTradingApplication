package stocks.view;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

/**
 * This class implements the methods of PortfolioView.
 */
public class PortfolioViewImpl implements PortfolioView {
  private final PrintStream out;

  /**
   * Constructs PortfolioViewImpl object with given output stream object.
   *
   * @param out the output stream.
   */
  public PortfolioViewImpl(PrintStream out) {
    this.out = out;
  }

  @Override
  public void callToViewToChooseCreateOrView() {
    out.println("Enter 1: To create a Portfolio "
            + "2: To query the portfolio details 3: To load a Portfolio  4: To update a portfolio");
  }


  @Override
  public void askUserOnHowToCreatePortfolio() {
    out.println("Enter 1: To create Portfolio using external file  2: To create manually");
  }


  @Override
  public void getFilePath() {
    out.println("Enter the path of File to load Portfolio");
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
    out.println("Enter the name of the company");
  }

  @Override
  public void askUserIfHeWantsToContinueTradingInCurrentPortfolio() {
    out.println("Enter 1: To continue trading in current portfolio.  "
            + "2: To exit from current Portfolio.");
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
    out.println("Enter 1: To view composition  2: To get TotalValue of portfolio  3: To get Total CostBasis  4:To display performance of Portfolio");
  }

  @Override
  public void displayTotalValue(String date, String val, String portfolioName) {
    out.println("Total Valuation of Portfolio " + portfolioName + " on " + date + " is :" + val);
  }

  @Override
  public void getDate() {
    out.println("Enter date( YYYY-MM-DD format only)");
  }

  @Override
  public void askUserIfheWantsTOContinueViewing() {
    out.println("Enter 1: To continue querying loaded portfolio  "
            + "2: To perform actions on other portfolios");
  }

  @Override
  public void displayMessageToBuyOrSell() {
    out.println("Enter 1: To buy stocks in this portfolio  "
            + "2: To sell stocks in this portfolio ");
  }

  @Override
  public void checkIfUserWantsToContinueUpdatingPortfolio() {
    out.println("Enter 1: To continue updating this portfolio  "
            + "2: To exit from this portfolio");
  }

  @Override
  public void createFlexibleOrInFlexiblePortfolio() {
    out.println("Enter 1: To create flexible portfolio  "
            + "2: To create inflexible  portfolio");
  }

  @Override
  public void displayTheTotalCost(double totalCost, String date, String portfolioName) {
    out.println("This portfolio's commission is $10 per transaction.");
    out.println("Total cost basis from the portfolioName " + portfolioName + " on " + date + " is " + totalCost);
  }

  @Override
  public void displayPortfolioPerformance(Map<String, Double> result, String startDate, String endDate, String portfolioName) {
    //determine scale from the first entry of the result map
  }


  @Override
  public void displayComposition(List<List<String>> records) {
    if(records.isEmpty()){
      displayErrorMessage("Composition is not available for given date");
    }
    if (records != null && !records.isEmpty()) {
      for (int i = 0; i < records.size(); i++) {
        for (int j = 0; j < records.get(i).size(); j++) {
          out.print(records.get(i).get(j));
          int len1 = records.get(0).get(j).length();
          int len2 = records.get(i).get(j).length();
          int len = len1 - len2;
          for (int k = 0; k < len; k++) {
            out.print(" ");
          }
          out.print("  ");
        }
        out.println(" ");
      }
    }
  }

  @Override
  public void displayErrorMessage(String message) {
    out.println(message);
  }

  @Override
  public void callExitFromLoad() {
    out.println("Enter 1: To view details of this portfolio. "
            + " 2: To exit and continue further trading."
    + " 3: To update loaded portfolio.");
  }

}
