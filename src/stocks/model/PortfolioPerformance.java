package stocks.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortfolioPerformance {

  public long daysBetween(String date1, String date2)
  {
    LocalDate startDate = LocalDate.parse("2016-08-31");
    LocalDate endDate = LocalDate.parse("2016-09-30");

    long days = ChronoUnit.DAYS.between(startDate, endDate);
    return days;
  }
  public long weeksBetween(String date1, String date2)
  {

//add validations if user is entering dates which dont exists
    LocalDate startDate = LocalDate.parse("2016-08-31");
    LocalDate endDate = LocalDate.parse("2016-09-30");

    long weeksInYear = ChronoUnit.WEEKS.between(startDate, endDate);
    return weeksInYear;
  }
  public long monthsBetween(String date1, String date2)
  {
            long monthsBetween = ChronoUnit.MONTHS.between(
            YearMonth.from(LocalDate.parse(date1)),
            YearMonth.from(LocalDate.parse(date2))
    );
    return monthsBetween;
  }
  public long yearsBetween(String date1, String date2)
  {
    //add validations date 1 < date 2
    long yearsBetween = ChronoUnit.YEARS.between(
            YearMonth.from(LocalDate.parse("2014-08-31")),
            YearMonth.from(LocalDate.parse("2016-11-30"))
    );
    return yearsBetween;
  }

  public long quartersBetween(String date1, String date2)
  {
    //add validations date 1 < date 2
    long quarters =
            IsoFields.QUARTER_YEARS.between(LocalDate.parse("2014-08-31"), LocalDate.parse("2014-12-30"));
    return quarters;
  }

  public void display(String portfolioName, String date1, String date2)
  {
    long months=`monthsBetween`(date1,date2);
    if(months>=5 && months<=30) {
      Map<String, Double> m = new HashMap<>(); // map --> yyyy-mm = val --> for every stock in portfolio keep adding val to its corresponding yyyy-mm

      //iterate over the portfolio
      //get each stock's list
      //get stock name of each and stock qty
      for (int i = 0; i < m[portfolio].size(); i++) {
        List listofmonths;
        for (int j = 0; j < m[portfolio][stk].size(); j++) {
          String stkname=m[portfolio][stk][j].getstkname();
          String qty=m[portfolio][stk][j].getqty();
          String yymm = m[portfolio][stk][j].getdate().getyymm();
          String action = m[portfolio][stk][j].getdate().getbuysell();
          listofmonths.add(yymm);
          String output = fetchOutputStringFromURL(stkname, "TIME_SERIES_MONTHLY");
          System.out.println(output);
          String lines[] = output.split(System.lineSeparator());
          map<yymm,val> mm;
          int flag = 0;
          for(int k=0;k<listofmonths.size();k++) {

            for (int i = 1; i < lines.length; i++) {
              String[] values = lines[i].split(",");
              if (values[0].contains(listofmonths[k]) {
                mm[listofmonths[k]] = Double.valueOf(values[4]);
                break;
              }

            }
          }
        }

        for (int j = 0; j < m[portfolio][stk].size(); j++) {
          String stkname=m[portfolio][stk][j].getstkname();
          String qty=m[portfolio][stk][j].getqty();
          String yymm = m[portfolio][stk][j].getdate().getyymm();
          String action = m[portfolio][stk][j].getdate().getbuysell();
          m[yymm]=qty*mm[yymm] if buy else -qty*mm[yymm];
        }


      }
    }

  }



  public String fetchOutputStringFromURL( String companyTickerSymbol, String delta) {

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
              + ".co/query?function="
              + delta
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
