package stocks.model;

import java.util.List;
import java.util.Map;

/**
 * This interface acts as model for virtual-trading application.
 * It has implemented all trivial operations of trading like creating a portfolio by
 * buying stocks/loading file ,getting total value of portfolio, view composition.
 */
public interface Portfolio {

  /**
   * Buy stocks of a company and add to the portfolio.
   *
   * @param quantity      number of stocks to be bought.
   * @param companyName   company of which stocks need to be bought.
   * @param portfolioName portfolio to which bought stocks need to be added.
   */
  void addStocks(String quantity, String companyName, String portfolioName);

  /**
   * Gives the valuation of the given portfolio as of a certain given date.
   *
   * @param date          the date as of which total value needs to be calculated.
   * @param portfolioName the portfolio for which total value is needed.
   * @return the total value calculated for given date.
   */
  double getTotalValueOfPortfolioOnCertainDate(String date, String portfolioName);

  /**
   * creates a portfolio using the data given by user in a file format.
   *
   * @param filePath the path of the file where the stocks data is entered by the user.
   */
  void loadPortfolioUsingFilePath(String filePath);

  /**
   * gives the information about the given portfolio's composition.
   *
   * @param portfolioName the name of the portfolio for which composition is needed.
   * @return the composition of portfolio in a list format.
   */
  List<List<String>> viewCompositionOfCurrentPortfolio(String portfolioName);

  /**
   * creates and persists a csv file with portfolio info created using manual inputs.
   *
   * @param portfolioName the name of the portfolio for which file needs to be written.
   */
  void createPortfolioIfCreatedManually(String portfolioName);

  /**
   * validate if the given company name is one among the stocks supported by this application.
   *
   * @param companyName the stock name to be validated.
   */
  void validateIfCompanyExists(String companyName);

  /**
   * validate if the given portfolio is already persisted.
   *
   * @param portfolioName the portfolio to be validated.
   */
  void validateIfPortfolioAlreadyExists(String portfolioName);

  /**
   * validate if the given portfolio is not already present.
   *
   * @param name the name of the portfolio to be validated.
   */
  void validateIfPortfolioDoesntExists(String name);

  /**
   * gives the instance of Model object.
   *
   * @return the model object.
   */
  Portfolio getInstance();


  void buyStocks(String companyName,String quantity,String date,String portfolioName);

  void sellStocks(String companyName,String quantity,String date,String portfolioName);

  void updatePortfolio(String companyName,String quantity,String date,String portfolioName,String action);

  double getTotalMoneyInvestedOnCertainDate(String date,String portfolioName);

  Map<String, Double> getPortfolioPerformanceOvertime(String startTime, String endTime, String portfolioName);

}
