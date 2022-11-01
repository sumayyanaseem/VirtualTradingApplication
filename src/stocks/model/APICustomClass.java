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

  public Double fetchStockPriceAsOfCertainDate(String companyTickerSymbol, String givenDate)  {

    String output = fetchLatestStockDetailsOfCurrentCompany(companyTickerSymbol);
    double res = 0.0;
    boolean flag = false;
    String lines[] = output.split(System.lineSeparator());

    try {
      //hardcoded purchaseDate for testing
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
      return -1.0;
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
