package stocks.customapi;

/**
 * This Interface represents helper functions to get fetch stock prices.
 */
public interface APICustomInterface {

  Double fetchLatestStockPriceOfThisCompany(String companyTickerSymbol);

  double getStockPriceAsOfCertainDate(String companyTickerSymbol, double qty, String date);

  String fetchOutputStringFromURLByInterval(String companyTickerSymbol, String interval);
}
