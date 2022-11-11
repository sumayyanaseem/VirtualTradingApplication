package stocks.model;

import java.util.List;

public class ModelImpl implements IModel {


  @Override
  public void addStocks(String quantity, String companyName, String portfolioName, Portfolio portfolio) {
    portfolio.addStocks(quantity, companyName, portfolioName);
  }

  @Override
  public double getTotalValueOfPortfolioOnCertainDate(String date, String portfolioName, Portfolio portfolio) {
    return portfolio.getTotalValueOfPortfolioOnCertainDate(date, portfolioName);
  }

  @Override
  public void getPortfolioPerformanceOvertime(String startTime, String endTime, String portfolioName, Portfolio portfolio) {
    portfolio.getPortfolioPerformanceOvertime(startTime, endTime, portfolioName);
  }

  @Override
  public void loadPortfolioUsingFilePath(String filePath, Portfolio portfolio) {
    portfolio.loadPortfolioUsingFilePath(filePath);
  }

  @Override
  public List<List<String>> viewCompositionOfCurrentPortfolio(String portfolioName, Portfolio portfolio) {
    return portfolio.viewCompositionOfCurrentPortfolio(portfolioName);
  }

  @Override
  public void createPortfolioIfCreatedManually(String portfolioName, Portfolio portfolio) {
    portfolio.createPortfolioIfCreatedManually(portfolioName);
  }

  @Override
  public void validateIfCompanyExists(String companyName) {

    portfolio.validateIfCompanyExists(companyName);
  }

  @Override
  public void validateIfPortfolioAlreadyExists(String portfolioName) {
    portfolio.validateIfPortfolioAlreadyExists(portfolioName);
  }

  @Override
  public void validateIfPortfolioDoesntExists(String name) {
    portfolio.validateIfPortfolioDoesntExists(name);
  }

  @Override
  public Portfolio getInstance(Portfolio portfolio) {
    return portfolio.getInstance();
  }

  @Override
  public void buyStocks(String companyName,String quantity, String date, String portfolioName, Portfolio portfolio) {
    portfolio.buyStocks(companyName, quantity, date, portfolioName);
  }

  @Override
  public void sellStocks(String companyName,String quantity, String date, String portfolioName, Portfolio portfolio) {
    portfolio.sellStocks(companyName, quantity, date, portfolioName);
  }

  @Override
  public void getTotalMoneyInvestedOnCertainDate(String date, String portfolioName, Portfolio portfolio) {
    portfolio.getTotalMoneyInvestedOnCertainDate(date, portfolioName);
  }

}
