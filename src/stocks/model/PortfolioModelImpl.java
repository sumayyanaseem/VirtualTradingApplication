package stocks.model;

import java.util.List;
import java.util.Map;

/**
 * This class represents implementation of PortfolioModel Interface.
 */
public class PortfolioModelImpl implements PortfolioModel {

  @Override
  public Map<String, Double> getPortfolioPerformanceOvertime(
          String startTime, String endTime, String portfolioName,
          IFlexible portfolio) {
    return portfolio.getPortfolioPerformanceOvertime(startTime, endTime, portfolioName);
  }



  @Override
  public double getTotalValueOfPortfolioOnCertainDate(String date, String portfolioName, IFlexible portfolio) {
    return 0;
  }

  @Override
  public void loadPortfolioUsingFilePath(String filePath, IFlexible portfolio) {
    portfolio.loadPortfolioUsingFilePath(filePath);
  }

  @Override
  public List<List<String>> viewCompositionOfCurrentPortfolio(String portfolioName, String date, IFlexible portfolio) {
    return null;
  }





  @Override
  public void createPortfolioIfCreatedManually(String portfolioName, IFlexible portfolio) {
    portfolio.createPortfolioIfCreatedManually(portfolioName);
  }

  @Override
  public void validateIfCompanyExists(String companyName, IFlexible portfolio) {
    portfolio.validateIfCompanyExists(companyName);
  }

  @Override
  public void validateIfPortfolioAlreadyExists(String portfolioName, IFlexible portfolio) {
    portfolio.validateIfPortfolioAlreadyExists(portfolioName);
  }

  @Override
  public void validateIfPortfolioDoesntExists(String name, IFlexible portfolio) {
    portfolio.validateIfPortfolioDoesntExists(name);
  }

  @Override
  public void buyStocks(String companyName, String quantity, String date, String portfolioName, String com, Portfolio portfolio) throws IllegalArgumentException {

  }



  @Override
  public void sellStocks(String companyName, String quantity,
                         String date, String portfolioName,
                         String com, IFlexible portfolio) {
    portfolio.sellStocks(companyName, quantity, date, com, portfolioName);
  }

  @Override
  public void updatePortfolio(String companyName, String quantity,
                              String date, String portfolioName,
                              IFlexible portfolio, String action, String com) {
    portfolio.updatePortfolio(companyName, quantity, date, portfolioName, action, com);
  }

  @Override
  public void updatePortfolioUsingFilePath(String path, String companyName,
                                           String quantity, String date, String portfolioName,
                                           IFlexible portfolio, String action, String com)
          throws IllegalArgumentException {
    portfolio.updatePortfolioUsingFilePath(path, companyName,
            quantity, date, portfolioName, action, com);
  }

  @Override
  public double getTotalMoneyInvestedOnCertainDate(String date,
                                                   String portfolioName,
                                                   IFlexible portfolio) {
    return portfolio.getTotalMoneyInvestedOnCertainDate(date, portfolioName);
  }

}
