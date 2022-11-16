package stocks.customapi;

/**
 * This interface represents helper functions to get fetch stock prices.
 */
public interface APICustomInterface {

  /**
   * gives the latest available stock price for the given company ticker.
   *
   * @param companyTickerSymbol company tickerSymbol.
   * @return the stock price of company.
   */
  Double fetchLatestStockPriceOfThisCompany(String companyTickerSymbol);


  /**
   * gives the stock price for the given company ticker as of a certain given date.
   *
   * @param companyTickerSymbol name of the company.
   * @param qty                 quantity of the stocks.
   * @param date                date given.
   * @return the stock price of company for the given date.
   */
  double getStockPriceAsOfCertainDate(String companyTickerSymbol, double qty, String date);
}
