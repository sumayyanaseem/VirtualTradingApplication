package stocks.customapi;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static stocks.customapi.LocalCacheForAPI.getStockRecordsForCompany;
import static stocks.customapi.LocalCacheForAPI.insertRecordsIntoCache;

/**
 * This class represents a custom class for all the APIs related to fetching stock price.
 */
public class APICustomClass implements APICustomInterface {

  private static final String apiKey = "5KFQLJAEXPPU6DJ9";
  private static final String urlString = "https://www.alphavantage.co/query?function=TIME_SERIES_";

  @Override
  public Double fetchLatestStockPriceOfThisCompany(String companyTickerSymbol) {

    double price;
    String[] lines = getStockRecordsForCompany(companyTickerSymbol);
    if (lines == null) {
      String output = fetchOutputStringFromURLByInterval(companyTickerSymbol, "DAILY");
      lines = output.split(System.lineSeparator());
    }
    String[] values = lines[1].split(",");
    price = Double.parseDouble(values[4]);

    return price;
  }

  @Override
  public void checkIPODate(String companyName, String date) {
    Date availableDateObj = null;
    Date givenDateObj;
    boolean flag = false;
    String[] lines = getStockRecordsForCompany(companyName);
    if (lines == null) {
      String output = fetchOutputStringFromURLByInterval(companyName, "DAILY");
      lines = output.split(System.lineSeparator());
    }
    List<List<String>> records = new ArrayList<>();
    for (int i = 1; i < lines.length; i++) {
      String[] values = lines[i].split(",");
      records.add(Arrays.asList(values));
    }
    try {
      givenDateObj = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
              .parse(date);
      for (List<String> record : records) {
        List<String> infoByDate = new ArrayList<>(record);
        String availableDate = infoByDate.get(0);
        availableDateObj = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse(availableDate);
        if (availableDateObj.compareTo(givenDateObj) <= 0) {
          flag = true;
          break;
        }

      }
    } catch (ParseException e) {
      throw new IllegalArgumentException("file not found in our records "
              + "for given company " + companyName);
    }


    if (availableDateObj != null && availableDateObj.compareTo(givenDateObj) > 0) {
      if (!flag) {
        throw new IllegalArgumentException("Given date "
                + "is before IPO Date.Please provide a valid date.");
      }
    }

  }


  @Override
  public double getStockPriceAsOfCertainDate(String companyTickerSymbol, double qty, String date) {
    if (qty == 0.0) {
      return 0.0;
    }
    String latestAvailableStkPrice = "0.0";
    Date availableDateObj = null;
    Date givenDateObj;
    String[] lines = getStockRecordsForCompany(companyTickerSymbol);
    if (lines == null) {
      String output = fetchOutputStringFromURLByInterval(companyTickerSymbol, "DAILY");
      lines = output.split(System.lineSeparator());
    }
    List<List<String>> records = new ArrayList<>();
    for (int i = 1; i < lines.length; i++) {
      String[] values = lines[i].split(",");
      records.add(Arrays.asList(values));
    }
    try {
      givenDateObj = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
              .parse(date);
      for (List<String> record : records) {
        List<String> infoByDate = new ArrayList<>(record);
        String availableDate = infoByDate.get(0);
        latestAvailableStkPrice = infoByDate.get(4);
        availableDateObj = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse(availableDate);
        if (availableDateObj.compareTo(givenDateObj) <= 0) {
          break;
        }

      }
    } catch (ParseException e) {
      throw new IllegalArgumentException("file not found in our records "
              + "for given company " + companyTickerSymbol);
    }


    if (availableDateObj != null && availableDateObj.compareTo(givenDateObj) > 0) {
      latestAvailableStkPrice = "0";
    }
    return Double.parseDouble(latestAvailableStkPrice) * qty;
  }


  /**
   * fetches result from alphavantage API given the company name and interval ( daily/monthly etc )
   * @param companyTickerSymbol name of the company for hich we need to query the API result
   * @param interval time seriies string - DAILY/MONTHLY/YEARLY etc
   * @return result from API in the form of a string.
   */
  public String fetchOutputStringFromURLByInterval(String companyTickerSymbol, String interval) {


    URL url;

    try {
      /*
      create the URL. This is the query to the web service. The query string
      includes the type of query (DAILY stock prices), stock symbol to be
      looked up, the API key and the format of the returned
      data (comma-separated values:csv). This service also supports JSON
      which you are welcome to use.
       */
      url = new URL(this.urlString
              + interval
              + "&symbol"
              + "=" + companyTickerSymbol + "&apikey=" + apiKey + "&datatype=csv" + "&outputsize=full");

    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    InputStream in;
    StringBuilder output = new StringBuilder();
    try {
      in = url.openStream();
      int b;
      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + companyTickerSymbol);
    }

    if (interval.equalsIgnoreCase("DAILY")) {
      insertRecordsIntoCache(companyTickerSymbol, output);
    }
    return output.toString();
  }


}