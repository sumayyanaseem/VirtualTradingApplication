package stocks.customAPI;

import java.util.HashMap;
import java.util.Map;


/**
 * This class represents caching the data from Alphavantage API to avoid
 */
public class LocalCacheForAPI {

  private static Map<String, String[]> apiInFo;

  static void insertRecordsIntoCache(String companyTickerSymbol, StringBuilder output) {
    //System.out.println("insertRecordsIntoCache  "+companyTickerSymbol);
    if (apiInFo == null || apiInFo.isEmpty()) {
      apiInFo = new HashMap<>();
      apiInFo.put(companyTickerSymbol, output.toString().split(System.lineSeparator()));
    } else if (!apiInFo.containsKey(companyTickerSymbol)) {
      apiInFo.put(companyTickerSymbol, output.toString().split(System.lineSeparator()));
    }

  }

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
