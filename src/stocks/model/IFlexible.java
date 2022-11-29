package stocks.model;

import java.util.List;
import java.util.Map;

public interface IFlexible extends Portfolio{


  /**
   * Get total cost basis of the given portfolio.
   *
   * @param date          date of cost basis req.
   * @param portfolioName name of portfolio.
   * @return returns total amount invested till this date along with commission.
   */

  double getTotalMoneyInvestedOnCertainDate(String date, String portfolioName);


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
   * creates and persists a csv file with portfolio info created using manual inputs.
   *
   * @param portfolioName the name of the portfolio for which file needs to be written.
   */
  void createEmptyPortfolio(String portfolioName, String portfolioType);

  List<String> getListOfPortfolioNames();

   void dollarCostStrategy(String portfolioName, Map<String, Double> stockAndPercent, double investmentAmount, double commissionFee, int investmentInterval,String dateStart, String dateEnd) ;
   void fixedAmountStrategy(String portfolioName, Map<String, Double> stockAndPercent,double investmentAmount,double commissionFee,String date);

  List<String> getStocksInPortfolio(String portfolioName);
  }
