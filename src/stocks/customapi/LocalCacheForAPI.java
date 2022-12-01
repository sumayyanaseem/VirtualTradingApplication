package stocks.customapi;

import java.util.HashMap;
import java.util.Map;


/**
 * This class represents methods related to caching the data from Alphavantage API.
 */
public class LocalCacheForAPI {

  private static Map<String, String[]> apiInFo;

  /**
   * inserts the stock data from alphavantage API into cache .
   *
   * @param companyTickerSymbol name of the company.
   * @param output              the output string
   */
  static void insertRecordsIntoCache(String companyTickerSymbol, StringBuilder output) {
    //System.out.println("insertRecordsIntoCache  "+companyTickerSymbol);
    if (apiInFo == null || apiInFo.isEmpty()) {
      apiInFo = new HashMap<>();
      apiInFo.put(companyTickerSymbol, output.toString().split(System.lineSeparator()));
    } else if (!apiInFo.containsKey(companyTickerSymbol)) {
      apiInFo.put(companyTickerSymbol, output.toString().split(System.lineSeparator()));
    }

  }

  /**
   * gets the stock records for the given company ticker.
   *
   * @param companyTickerSymbol name of the company.
   * @return a list of strings with stock data.
   */
  static String[] getStockRecordsForCompany(String companyTickerSymbol) {
    //System.out.println("getStockRecordsForCompany  "+companyTickerSymbol);
    if (apiInFo == null || apiInFo.isEmpty()) {
      return null;
    } else if (apiInFo.containsKey(companyTickerSymbol)) {
      String[] res = apiInFo.get(companyTickerSymbol);
      return res;
    }
    return null;
  }

}
