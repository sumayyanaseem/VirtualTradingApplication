package stocks.controller;

import java.util.List;
import java.util.Map;

/**
 * This interface represents features to be implemented for GUI actions.
 */
public interface Features {
  /**
   * creates the portfolio with given name and type.
   *
   * @param pName the name of portfolio.
   * @param pType the type of portfolio to be created.
   */
  void createPortfolio(String pName, String pType);

  /**
   * buys the stocks in a portfolio.
   *
   * @param ticker the company name.
   * @param date   the date on which stocks should be bought.
   * @param qty    the quantity of stocks to be bought.
   * @param comm   the commission to be paid for this transaction.
   * @param pName  the name of portfolio to be created.
   */
  void buyStock(String ticker, String date, String qty, String comm, String pName);

  /**
   * method to invest in fixed amount strategy.
   *
   * @param portfolioName    the company name.
   * @param stockAndPercent  the map which contains stock names and respective percentages.
   * @param investmentAmount the amount to be invested using this strategy.
   * @param commissionFee    the commission to be paid for this transaction.
   * @param date             the name of portfolio to be created.
   */
  void investFixedAmountStrategy(String portfolioName, Map<String, String> stockAndPercent,
                                 double investmentAmount, double commissionFee, String date);

  /**
   * method to invest in dollar cost strategy.
   *
   * @param portfolioName    the company name.
   * @param stockAndPercent  the map which contains stock names and respective percentages.
   * @param investmentAmount the amount to be invested using this strategy.
   * @param commissionFee    the commission to be paid for this transaction.
   * @param dateStart        the start date of investment.
   * @param dateEnd          the end date of investment.
   */
  void dollarCostStrategy(String portfolioName, Map<String, String> stockAndPercent,
                          double investmentAmount, double commissionFee, int investmentInterval, String dateStart, String dateEnd);

  /**
   * method to sellStocks in a portfolio.
   *
   * @param ticker the company name.
   * @param date   the date on which stocks need to be sold.
   * @param qty    the quantity to be sold.
   * @param comm   the commission to be paid for this transaction.
   * @param pName  the portfolio name on which this sell transaction is to be applied.
   */
  void sellStock(String ticker, String date, String qty, String comm, String pName);

  /**
   * method to get total value of a portfolio.
   *
   * @param pName the portfolio name.
   * @param date  the date as of which total value of portfolio is needed.
   */
  double getTotalValue(String pName, String date);

  /**
   * method to get total cost basis of a portfolio.
   *
   * @param pName the portfolio name.
   * @param date  the date as of which total cost basis of portfolio is needed.
   */
  double getCostBasis(String pName, String date);

  /**
   * method to get composition of a portfolio.
   *
   * @param pName the portfolio name.
   * @param date  the date as of which composition of portfolio is needed.
   */
  List<List<String>> viewComposition(String pName, String date);

  /**
   * method to exit the program.
   */
  void exitTheProgram();
}
