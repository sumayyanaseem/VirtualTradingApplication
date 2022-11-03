package stocks.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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
class APICustomClass {


  /**
   * gives the latest available stock price for the given company ticker.
   *
   * @return the stock price of company.
   */
  public Double fetchLatestStockPriceOfThisCompany(String companyTickerSymbol) {

    Double price = Double.valueOf(-1);
    String name = companyTickerSymbol.toUpperCase();
    String path = "availableStocks" + File.separator + "daily_" + name + ".csv";
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream is = classLoader.getSystemClassLoader().getResourceAsStream(path);
    if (is != null) {
      try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
        String line = br.readLine();
        if ((line = br.readLine()) != null) {
          String[] values = line.split(",");
          price = Double.valueOf(values[4]);
        }
      } catch (FileNotFoundException ex) {
        System.out.println("file not found in our records for " +
                "given company " + companyTickerSymbol);
      } catch (IOException e) {
        System.out.println("file not found in our records for " +
                "given company " + companyTickerSymbol);
      }
    }
    return price;
  }


  /**
   * gives the stock price for the given company ticker as of a certain given date.
   *
   * @return the stock price of company for the given date.
   */
  public double getStockPriceAsOfCertainDate(String companyTickerSymbol, double qty, String date) {
    String latestAvailableStkPrice = "0.0";
    Date availableDateObj = null;
    Date givenDateObj = null;
    String name = companyTickerSymbol.toUpperCase();
    String path = "availableStocks" + File.separator + "daily_" + name + ".csv";
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream is = classLoader.getSystemClassLoader().getResourceAsStream(path);
    List<List<String>> records = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
      String line = br.readLine();
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
        records.add(Arrays.asList(values));
      }
    } catch (UnsupportedEncodingException e) {
      System.out.println("file not found in our records for given company " + companyTickerSymbol);
      return 0.0;
    } catch (IOException e) {
      System.out.println("file not found in our records for given company " + companyTickerSymbol);
      return 0.0;
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
      throw new IllegalArgumentException("file not found in our records " +
              "for given company " + companyTickerSymbol);
    }


    if (availableDateObj != null && availableDateObj.compareTo(givenDateObj) > 0) {
      throw new IllegalArgumentException("Stock Price is not available for this past date");
    }
    return Double.valueOf(latestAvailableStkPrice) * qty;
  }

}
