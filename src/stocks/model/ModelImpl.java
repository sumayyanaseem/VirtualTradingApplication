package stocks.model;

import java.util.List;
import java.util.Map;

public class ModelImpl implements IModel {

  @Override
  public double getTotalValueOfPortfolioOnCertainDate(String date, String portfolioName, Portfolio portfolio) {
    return portfolio.getTotalValueOfPortfolioOnCertainDate(date, portfolioName);
  }

  @Override
  public Map<String, Double> getPortfolioPerformanceOvertime(String startTime, String endTime, String portfolioName, Portfolio portfolio) {
    return portfolio.getPortfolioPerformanceOvertime(startTime, endTime, portfolioName);
  }

  @Override
  public void loadPortfolioUsingFilePath(String filePath, Portfolio portfolio) {
    portfolio.loadPortfolioUsingFilePath(filePath);
  }

  @Override
  public List<List<String>> viewCompositionOfCurrentPortfolio(String portfolioName, String date,Portfolio portfolio) {
    return portfolio.viewCompositionOfCurrentPortfolio(portfolioName,date);
  }

  @Override
  public void createPortfolioIfCreatedManually(String portfolioName, Portfolio portfolio) {
    portfolio.createPortfolioIfCreatedManually(portfolioName);
  }

  @Override
  public void validateIfCompanyExists(String companyName,Portfolio portfolio) {
    portfolio.validateIfCompanyExists(companyName);
  }

  @Override
  public void validateIfPortfolioAlreadyExists(String portfolioName,Portfolio portfolio) {
    portfolio.validateIfPortfolioAlreadyExists(portfolioName);
  }

  @Override
  public void validateIfPortfolioDoesntExists(String name,Portfolio portfolio) {
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
  public void updatePortfolio(String companyName, String quantity, String date, String portfolioName, Portfolio portfolio, String action) {
    portfolio.updatePortfolio(companyName,quantity,date,portfolioName,action);
  }

  @Override
  public double getTotalMoneyInvestedOnCertainDate(String date, String portfolioName, Portfolio portfolio) {
    return portfolio.getTotalMoneyInvestedOnCertainDate(date, portfolioName);
  }

}
