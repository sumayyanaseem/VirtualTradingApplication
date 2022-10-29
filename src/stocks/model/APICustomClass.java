package stocks.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class APICustomClass {

  public Double fetchStockPriceAsOfCertainDate(String companyTickerSymbol, String givenDate) throws IllegalArgumentException {

    String output = fetchOutputStringFromURL(companyTickerSymbol);
    double res = 0.0;
    boolean flag = false;
    String lines[] = output.split(System.lineSeparator());

    try {
      //hardcoded purchasedate for testing
      Date dateForWhichDataIsNeeded = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
              .parse(givenDate);

      for (int i = 1; i < lines.length; i++) {
        String stkInfoByDate[] = lines[i].split(",");
        String dateStr = stkInfoByDate[0];
        Date start = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse(dateStr);

        if(start.compareTo(dateForWhichDataIsNeeded)<0)
        {
            break;
        }
        if (start.compareTo(dateForWhichDataIsNeeded) == 0) {
          res = Double.valueOf(stkInfoByDate[4]);
          flag = true;
          break;
        }
      }

    } catch (ParseException e) {
      System.out.println(e.getMessage());
    }
    if (!flag) {
      System.out.println("Stock price not available for this input.");
    }
    return res;

  }

  public Double fetchStockPriceAsOfToday(String companyTickerSymbol) {
    String pattern = "yyyy-MM-dd";
    String dateInString = null;
    try {
      Date date = new Date();
      SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
      dateFormat.format(date);
      if (dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse("16:10"))) {
        dateInString = new SimpleDateFormat(pattern).format(new Date(System.currentTimeMillis()));
      } else {
        dateInString = new SimpleDateFormat(pattern).format(new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000));
      }
    } catch (ParseException e) {
      System.out.println(e.getMessage());
    }
    return fetchStockPriceAsOfCertainDate(companyTickerSymbol, dateInString);

  }


  public String fetchOutputStringFromURL(String companyTickerSymbol) {

    String apiKey = "W0M1JOKC82EZEQA8";
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
