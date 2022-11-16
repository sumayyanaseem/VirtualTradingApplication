package stocks.customAPI;

/**
 * This Interface represents helper functions to get fetch stock prices.
 */
public interface APICustomInterface {

  Double fetchLatestStockPriceOfThisCompany(String companyTickerSymbol);

  double getStockPriceAsOfCertainDate(String companyTickerSymbol, double qty, String date);

  double getStockPriceAsOfCertainMonthEnd(String companyTickerSymbol, String yearMonth, double qty, String output);

  String fetchOutputStringFromURLByInterval(String companyTickerSymbol, String interval);
}
