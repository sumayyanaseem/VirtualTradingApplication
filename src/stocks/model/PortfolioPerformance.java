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

  Map<String,Double> generateMapWithMonthKeys(String date1, String date2)
  {

    Map<String,Double> m = new HashMap<>();
    int year1=Integer.valueOf(date1.substring(0,4));
    int year2=Integer.valueOf(date2.substring(0,4));
    int month1=Integer.valueOf(date1.substring(5,7));
    int month2=Integer.valueOf(date2.substring(5,7));

    if(year1==year2)
    {
      for(int i=month1;i<=month2;i++)
      {
        String key=date1.substring(0,4);
        int d=i/10;
        if(d==0)
        {
          key=key+"-0"+String.valueOf(i);
        }
        else
        {
          key=key+"-"+String.valueOf(i);
        }
        m.put(key,0.0);

      }
    }
    else
    {
      int n=year2-year1;
      String yr= date1.substring(0,4);
      for(int i=month1;i<=12;i++)
      {
        String key=date1.substring(0,4);
        int d=i/10;
        if(d==0)
        {
          key=key+"-0"+String.valueOf(i);
        }
        else
        {
          key=key+"-"+String.valueOf(i);
        }
        m.put(key,0.0);

      }
      int nextYr=0;
      while(n-1>0)
      {
        nextYr=Integer.valueOf(yr);
        nextYr=nextYr+1;

        for(int i=1;i<=12;i++)
        {
          String key=String.valueOf(nextYr);
          int d=i/10;
          if(d==0)
          {
            key=key+"-0"+String.valueOf(i);
          }
          else
          {
            key=key+"-"+String.valueOf(i);
          }
          m.put(key,0.0);

        }
        n--;

      }

      int lastYr= nextYr+1;

      for(int i=1;i<=month2;i++)
      {
        String key=String.valueOf(nextYr);
        int d=i/10;
        if(d==0)
        {
          key=key+"-0"+String.valueOf(i);
        }
        else
        {
          key=key+"-"+String.valueOf(i);
        }
        m.put(key,0.0);

      }


    }
    return m;
  }
  public void displayCopy(Map<String,Map<String,List<Stock>>> mp, String portfolioName, String date1, String date2) {
    long months = monthsBetween(date1, date2);
    Map<Integer,Integer> noOfDays = new HashMap<>();
    noOfDays.put(1,31);
    noOfDays.put(2,29);
    noOfDays.put(3,31);
    noOfDays.put(4,30);
    noOfDays.put(5,31);
    noOfDays.put(6,30);
    noOfDays.put(7,31);
    noOfDays.put(8,31);
    noOfDays.put(9,30);
    noOfDays.put(10,31);
    noOfDays.put(11,30);
    noOfDays.put(12,31);

    if (months >= 5 && months <= 30) {
      Map<String, Double> monthWiseTotalValues = new HashMap<>(); // map --> yyyy-mm = val --> for every stock in portfolio keep adding val to its corresponding yyyy-mm

      // suppose date range is feb 2022 to april 2022. our below map will have 2022-02-->0.0, 2022-03-->0.0, 2022-04-->0.0
      Map<String,Double> mapOfMonths =generateMapWithMonthKeys(date1,date2);

      // for every month in given range by user, get the last date of month and for that date iterate
      // over all the stocks in portfolio and get the netquantity of that stock as of the last date
      // of the month and multiply the new quantity with stock price of that month
      String monthEndDate;
      for (Map.Entry<String, Double> entry : mapOfMonths.entrySet()) {
        String dt=entry.getKey();
        int mnth = Integer.valueOf(dt.substring(5,7));
        int daysInMonth = noOfDays.get(mnth);
        monthEndDate = dt + String.valueOf(daysInMonth);

        Map<String, List<Stock>> portfolioValueMap = mp.get(portfolioName);
        double totalValueOfPortfolioMonthEnd=0.0;
        for (Map.Entry<String, List<Stock>> companyInfo : portfolioValueMap.entrySet()) {

          double netQty=getQuantityOnThisDateForGivenCompanyName(monthEndDate,companyInfo.getKey());
          double stkValueMonthEnd = getStockPriceAsOfCertainDate(companyInfo.getKey(),netQty,monthEndDate);
          totalValueOfPortfolioMonthEnd=totalValueOfPortfolioMonthEnd+stkValueMonthEnd;
        }

        monthWiseTotalValues.put(monthEndDate,totalValueOfPortfolioMonthEnd);

      }
    }

  public void display(Map<String,Map<String,List<Stock>>> mp, String portfolioName, String date1, String date2)
  {
    long months=monthsBetween(date1,date2);
    if(months>=5 && months<=30) {
      Map<String, Double> m = new HashMap<>(); // map --> yyyy-mm = val --> for every stock in portfolio keep adding val to its corresponding yyyy-mm

      //iterate over the portfolio
      //get each stock's list
      //get stock name of each and stock qty


        Map<String,List<Stock>> listOfCompanies = mp.get(portfolioName);

      for (Map.Entry<String, List<Stock>> entry : listOfCompanies.entrySet()) {
        String companyName = entry.getKey();
        List<Stock> stockTransactions = entry.getValue();
        List listofmonths = new ArrayList<>();
        for (int j = 0; j < stockTransactions.size(); j++) {

          String actionDate = stockTransactions.get(j).getDateOfAction();
          String subDate = actionDate.substring(0, 7);
          listofmonths.add(subDate);
        }
          String output = fetchOutputStringFromURL(companyName, "TIME_SERIES_MONTHLY");
          System.out.println(output);
          String lines[] = output.split(System.lineSeparator());
          Map<String,Double> mm=new HashMap<>();

          for(int k=0;k<listofmonths.size();k++) {

            for (int i = 1; i < lines.length; i++) {
              String[] values = lines[i].split(",");
              if (values[0].substring(0,7).equals(listofmonths.get(k))){
                mm.put(values[0].substring(0,7),Double.valueOf(values[4]));
                break;
              }

            }
          }

        for (int j = 0; j < stockTransactions.size(); j++) {

          double qty=stockTransactions.get(j).getQty();
          String actiionDate = stockTransactions.get(j).getDateOfAction();
          String action = stockTransactions.get(j).getAction();
          if(stockTransactions.get(j).getAction().equals("buy"))
          m.put(actiionDate.substring(0,7),qty*mm.get(actiionDate));
         else {
           //double val=stockTransactions.get(j).
            //m.put(actiionDate.substring(0, 7), qty * mm.get(actiionDate));
           // -qty * mm[yymm];
          }

        }

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
