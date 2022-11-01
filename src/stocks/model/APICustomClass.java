package stocks.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class APICustomClass {

  public Double fetchLatestStockPriceOfThisCompany(String companyTickerSymbol) {

    Double price = Double.valueOf(-1);
    String name =companyTickerSymbol.toUpperCase();
    String path = "csvFiles/"+"daily_"+name+".csv";
    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
      String line = br.readLine();
      if((line = br.readLine()) != null) {
        String[] values = line.split(",");
        price = Double.valueOf(values[4]);
      }
    } catch (FileNotFoundException ex) {
      throw new RuntimeException(ex);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
   return price;
  }

}
