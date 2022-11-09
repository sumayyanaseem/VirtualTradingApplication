package stocks.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * This class represents a custom class for all the APIs related to fetching stock price.
 */
class APICustomClass implements APICustomInterface{


  /**
   * gives the latest available stock price for the given company ticker.
   *
   * @return the stock price of company.
   */
  @Override
  public Double fetchLatestStockPriceOfThisCompany(String companyTickerSymbol) {

    Double price = Double.valueOf(-1);
    String name = companyTickerSymbol.toUpperCase();
    String output = fetchOutputStringFromURL(companyTickerSymbol);
    String lines[] = output.split(System.lineSeparator());
    //String path = "availableStocks" + File.separator + "daily_" + name + ".csv";
    //ClassLoader classLoader = getClass().getClassLoader();
    //InputStream is = classLoader.getSystemClassLoader().getResourceAsStream(path);
    String[] values = lines[1].split(",");
    price = Double.valueOf(values[4]);

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
    Date givenDateObj = null;
    String name = companyTickerSymbol.toUpperCase();
    //String path = "availableStocks" + File.separator + "daily_" + name + ".csv";
    //ClassLoader classLoader = getClass().getClassLoader();
    //InputStream is = classLoader.getSystemClassLoader().getResourceAsStream(path);
    String output = fetchOutputStringFromURL(companyTickerSymbol);
    String lines[] = output.split(System.lineSeparator());
    List<List<String>> records = new ArrayList<>();
    for(int i=1;i<lines.length;i++)
    {
      String[] values = lines[i].split(",");
      records.add(Arrays.asList(values));
    }
    try {
      givenDateObj = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
              .parse(date);
      for (int i = 0; i < records.size(); i++) {
        List<String> infoByDate = new ArrayList<>(records.get(i));
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
      throw new IllegalArgumentException("Stock Price is not available for this past date");
    }
    return Double.valueOf(latestAvailableStkPrice) * qty;
  }


  @Override
  public String fetchOutputStringFromURL( String companyTickerSymbol) {

    String apiKey = "5KFQLJAEXPPU6DJ9";
    String stockSymbol = companyTickerSymbol; //ticker symbol for Google
    URL url = null;

    try {
      /*
      create the URL. This is the query to the web service. The query string
      includes the type of query (DAILY stock prices), stock symbol to be
      looked up, the API key and the format of the returned
      data (comma-separated values:csv). This service also supports JSON
      which you are welcome to use.
       */
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + stockSymbol + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {
      /*
      Execute this query. This returns an InputStream object.
      In the csv format, it returns several lines, each line being separated
      by commas. Each line contains the date, price at opening time, highest
      price for that date, lowest price for that date, price at closing time
      and the volume of trade (no. of shares bought/sold) on that date.

      This is printed below.
       */
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