package stocks.model;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
public class PortfolioImplModel implements PortfolioModel{

 private Map<String, Map<String, Stock>> portfolioMap;

  public PortfolioImplModel(){
   portfolioMap = new HashMap<>();
  }

  //TODO:Have one more constructor to read fileInput and remove the below logic to a helper method.

  @Override
  public void buyStocks(String quantity, String CompanyName, String portfolioName) {

    String pattern = "yyyy-MM-dd";
    String dateInString =new SimpleDateFormat(pattern).format(new Date(System.currentTimeMillis()-24*60*60*1000));
    double priceBought= fetchStockPriceAsOfToday(dateInString,CompanyName);

    String todayDateStr =new SimpleDateFormat(pattern).format(new Date(System.currentTimeMillis()));

    long qty= Long.valueOf(quantity);
    double totalval= priceBought*qty;
    Stock s=new Stock(CompanyName,qty,todayDateStr,null,priceBought,totalval);
    if(portfolioMap.isEmpty())
    {
      Map<String,Stock> m= new HashMap<>();
      m.put(CompanyName,s);
      portfolioMap.put(portfolioName,m);
    }
    else
    {
      Map<String, Stock> m1= portfolioMap.get(portfolioName);
      if(!m1.containsKey(CompanyName))
      {
        m1.put(CompanyName,s);
        portfolioMap.put(portfolioName,m1);
      }
      else
      {
        Stock s1=m1.get(CompanyName);
        long totQty=s1.getQty()+qty;
        double val = s1.getTotalValue()+totalval;
        Stock s2=new Stock(CompanyName,totQty,todayDateStr,null,priceBought,val);
        m1.put(CompanyName,s2);
        portfolioMap.put(portfolioName,m1);
      }
    }

  }


  public Double fetchStockPriceAsOfCertainDate( String datePurchased, String companyTickerSymbol,  String givenDate) throws IllegalArgumentException {

    String output=fetchOutputStringFromURL(companyTickerSymbol);
    double res=0.0 ;
    boolean flag=false;
    String lines[] = output.split(System.lineSeparator());

    try {
      Date purchaseDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
              .parse(datePurchased);

      //hardcoded purchasedate for testing
      Date dateForWhichDataIsNeeded = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
              .parse(givenDate);


      if(dateForWhichDataIsNeeded.compareTo(purchaseDate)<0)
      {
        System.out.println("Stock was bought after the given date. Nothing to display");
        throw new IllegalArgumentException();
      }
      for (int i = 1; i < lines.length; i++) {
        String stkInfoByDate[] = lines[i].split(",");
        String dateStr = stkInfoByDate[0];
        Date start = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse(dateStr);

        if (start.compareTo(purchaseDate) < 0) {
          break;
        }
        if (start.compareTo(dateForWhichDataIsNeeded) == 0) {
          res = Double.valueOf(stkInfoByDate[4]);
          flag=true;
          break;
        }


      }

    } catch (ParseException e) {
      e.printStackTrace();
    }
    if(!flag)
    {
      System.out.println("Market closed on this day. Stock price not available.");
      throw new IllegalArgumentException();
    }
    return res;

  }

  public Double fetchStockPriceAsOfToday(String datePurchased, String companyTickerSymbol){
    String pattern = "yyyy-MM-dd";
    String dateInString =new SimpleDateFormat(pattern).format(new Date(System.currentTimeMillis()-24*60*60*1000));
    return fetchStockPriceAsOfCertainDate( datePurchased, companyTickerSymbol, dateInString);
  }


  public String fetchOutputStringFromURL( String companyTickerSymbol) {

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


  @Override
  public void sellStocks(String quantity, String CompanyName, String portfolioName) {

  }

  @Override
  public PortfolioModel createPortfolioUsingFilePath(String filePath) {
    return null;
  }

  @Override
  public double getTotalValueOfPortfolioOnCertainDate(Date date, String portfolioName) {
  return 0.0;
  }

 @Override
 public boolean isPortfolioCreated() {
  return false;
 }



}
