package stocks.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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

}
