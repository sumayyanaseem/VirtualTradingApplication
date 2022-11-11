package stocks.model;

import java.util.List;

public class AbstractPortfolio implements Portfolio{
  @Override
  public void addStocks(String quantity, String companyName, String portfolioName) {

  }

  @Override
  public double getTotalValueOfPortfolioOnCertainDate(String date, String portfolioName) {
    return 0;
  }

  @Override
  public void loadPortfolioUsingFilePath(String filePath) {

  }

  @Override
  public List<List<String>> viewCompositionOfCurrentPortfolio(String portfolioName) {
    return null;
  }

  @Override
  public void createPortfolioIfCreatedManually(String portfolioName) {

  }

  @Override
  public void validateIfCompanyExists(String companyName) {

  }

  @Override
  public void validateIfPortfolioAlreadyExists(String portfolioName) {

  }

  @Override
  public void validateIfPortfolioDoesntExists(String name) {

  }

  @Override
  public Portfolio getInstance() {
    return null;
  }

  @Override
  public void buyStocks(String companyName, String quantity, String date, String portfolioName) {

  }

  @Override
  public void sellStocks(String companyName, String quantity, String date, String portfolioName) {

  }

  @Override
  public void getTotalMoneyInvestedOnCertainDate(String date, String portfolioName) {

  }

  @Override
  public void getPortfolioPerformanceOvertime(String startTime, String endTime, String portfolioName) {

  }
}
