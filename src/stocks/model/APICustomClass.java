package stocks.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class APICustomClass {

  public Double fetchLatestStockPriceOfThisCompany(String companyTickerSymbol) {

    Double price = Double.valueOf(-1);
    String name =companyTickerSymbol.toUpperCase();
    String path = "availableStocks/"+"daily_"+name+".csv";
    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
      String line = br.readLine();
      if((line = br.readLine()) != null) {
        String[] values = line.split(",");
        price = Double.valueOf(values[4]);
      }
    } catch (FileNotFoundException ex) {
      System.out.println("file not found in our records for given company "+companyTickerSymbol);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
   return price;
  }




  public double getStockPriceAsOfCertainDate(String companyTickerSymbol,double qty,String date )
  {
    String latestAvailableStkPrice="0.0";

    String name = companyTickerSymbol.toUpperCase();
    String path = "csvFiles/" + "daily_" + name + ".csv";
    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
      String line= br.readLine();
      List<List<String>> records = new ArrayList<>();
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
        records.add(Arrays.asList(values));
      }
      try {
        Date givenDateObj = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse(date);
        for (int i = 0; i < records.size(); i++) {
          List<String> infoByDate = new ArrayList<>(records.get(i));
          String availableDate = infoByDate.get(0);
          latestAvailableStkPrice = infoByDate.get(4);
          Date availableDateObj = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                  .parse(availableDate);
          if (availableDateObj.compareTo(givenDateObj) <= 0) {
            break;

          }

        }
      }
      catch(ParseException p){
        System.out.println("parse exception");
      }
    } catch (FileNotFoundException ex) {
      throw new RuntimeException(ex);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return Double.valueOf(latestAvailableStkPrice)*qty;
  }

}
