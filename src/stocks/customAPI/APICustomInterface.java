package stocks.customAPI;

public interface APICustomInterface {
  Double fetchLatestStockPriceOfThisCompany(String companyTickerSymbol);
  double getStockPriceAsOfCertainDate(String companyTickerSymbol, double qty, String date);
  String fetchOutputStringFromURLByInterval( String companyTickerSymbol, String interval);
}
