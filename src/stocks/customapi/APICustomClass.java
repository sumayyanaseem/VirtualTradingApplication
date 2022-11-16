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

/**
 * This class represents a custom class for all the APIs related to fetching stock price.
 */
public class APICustomClass implements APICustomInterface {

  private static final String apiKey = "5KFQLJAEXPPU6DJ9";
  private final String urlString;

  public APICustomClass(String url) {
    this.urlString = url;
  }

  /**
   * gives the latest available stock price for the given company ticker.
   *
   * @return the stock price of company.
   */
  @Override
  public Double fetchLatestStockPriceOfThisCompany(String companyTickerSymbol) {

    double price = -1;
    String[] lines = getStockRecordsForCompany(companyTickerSymbol);
    if (lines == null) {
      String output = fetchOutputStringFromURLByInterval(companyTickerSymbol, "DAILY");
      lines = output.split(System.lineSeparator());
    }
    String[] values = lines[1].split(",");
    price = Double.parseDouble(values[4]);

    return price;
  }


  /**
   * gives the stock price for the given company ticker as of a certain given date.
   *
   * @return the stock price of company for the given date.
   */
  @Override
  public double getStockPriceAsOfCertainDate(String companyTickerSymbol, double qty, String date) {
    String latestAvailableStkPrice = "0.0";
    Date availableDateObj = null;
    Date givenDateObj;
    String output = fetchOutputStringFromURLByInterval(companyTickerSymbol, "DAILY");
    String[] lines = output.split(System.lineSeparator());
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
      //throw new IllegalArgumentException("Stock Price is not available for this past date");
      latestAvailableStkPrice = "0";
    }
    return Double.parseDouble(latestAvailableStkPrice) * qty;
  }

  @Override
  public double getStockPriceAsOfCertainMonthEnd(String companyTickerSymbol,
                                                 String yearMonth,
                                                 double qty, String output) {

    String[] lines = output.split(System.lineSeparator());
    List<List<String>> records = new ArrayList<>();
    double value = 0.0;
    for (int i = 1; i < lines.length; i++) {
      String[] values = lines[i].split(",");
      records.add(Arrays.asList(values));
    }


    for (List<String> record : records) {
      List<String> infoByDate = new ArrayList<>(record);
      String availableDate = infoByDate.get(0);

      if (availableDate.substring(0, 7).equals(yearMonth)) {
        value = Double.parseDouble(infoByDate.get(4));
        break;
      }

    }
    return value * qty;
  }


  @Override
  public String fetchOutputStringFromURLByInterval(String companyTickerSymbol, String interval) {


    String stockSymbol = companyTickerSymbol; //ticker symbol for Google
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
              + "=" + stockSymbol + "&apikey=" + apiKey + "&datatype=csv" + "&outputsize=full");

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
      throw new IllegalArgumentException("No price data found for " + stockSymbol);
    }

    return output.toString();

  }


}