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
  List<List<String>> viewCompositionOfCurrentPortfolio(String portfolioName, String date);

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
   * Buy stocks of a company and add to the portfolio.
   *
   * @param quantity      number of stocks to be bought.
   * @param companyName   company of which stocks need to be bought.
   * @param portfolioName portfolio to which bought stocks need to be added.
   */
  void buyStocks(String companyName, String quantity,
                 String date, String com, String portfolioName)
          throws IllegalArgumentException;

  /**
   * sell stocks of a company and add to the portfolio.
   *
   * @param companyName   name of the company.
   * @param quantity      quantity of stocks.
   * @param date          date of transaction.
   * @param com           commission in this transaction.
   * @param portfolioName name of portfolio.
   * @throws IllegalArgumentException throws IllegalArgument
   *                                  exception when invalid input is provided.
   */
  void sellStocks(String companyName, String quantity,
                  String date, String com, String portfolioName)
          throws IllegalArgumentException;

  /**
   * Update the given portfolio.
   *
   * @param companyName   name of the company.
   * @param quantity      quantity of stocks.
   * @param date          date of transaction.
   * @param portfolioName name of portfolio.
   * @param action        action buy/sell
   * @param com           commission in this transaction.
   * @throws IllegalArgumentException throws IllegalArgument
   *                                  exception when invalid input is provided.
   */
  void updatePortfolio(String companyName, String quantity,
                       String date, String portfolioName,
                       String action, String com)
          throws IllegalArgumentException;

  /**
   * Update the portfolio when path is provided.
   *
   * @param path          path of the portfolio file.
   * @param companyName   name of the company.
   * @param quantity      quantity of stocks.
   * @param date          date of transaction.
   * @param portfolioName name of portfolio.
   * @param action        action buy/sell
   * @param com           commission in this transaction.
   * @throws IllegalArgumentException throws IllegalArgument
   *                                  exception when invalid input is provided.
   */
  void updatePortfolioUsingFilePath(String path, String companyName,
                                    String quantity, String date, String portfolioName,
                                    String action, String com)
          throws IllegalArgumentException;

  /**
   * Get total cost basis of the given portfolio.
   *
   * @param date          date of cost basis req.
   * @param portfolioName name of portfolio.
   * @return returns total amount invested till this date along with commission.
   */

  double getTotalMoneyInvestedOnCertainDate(String date, String portfolioName);

  /**
   * get portfolioPerformance over the range of time.
   *
   * @param startTime     start point of the range.
   * @param endTime       end point of the range.
   * @param portfolioName name of the portfolio.
   * @return returns the performance of portfolio over the time.
   */
  Map<String, Double> getPortfolioPerformanceOvertime(String startTime,
                                                      String endTime,
                                                      String portfolioName);

}
