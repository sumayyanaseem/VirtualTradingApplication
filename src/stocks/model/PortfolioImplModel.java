package stocks.model;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.IOException;
public class PortfolioImplModel implements PortfolioModel {

  private Map<String, Map<String, Stock>> portfolioMap;

  private APICustomClass apiCustomClass;

  private CustomCSVParser customCSVParser;

  public PortfolioImplModel() {
    portfolioMap = new HashMap<>();
    apiCustomClass = new APICustomClass();
    customCSVParser = new CustomCSVParser();
  }

  @Override
  public void buyStocks(String quantity, String CompanyName, String portfolioName) throws IOException {

    String pattern = "yyyy-MM-dd";
    String dateInString = new SimpleDateFormat(pattern).format(new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000));
    double priceBought = apiCustomClass.fetchStockPriceAsOfToday(dateInString, CompanyName);

    String todayDateStr = new SimpleDateFormat(pattern).format(new Date(System.currentTimeMillis()));

    long qty = Long.valueOf(quantity);
    double totalval = priceBought * qty;
    Stock s = new Stock(CompanyName, qty, todayDateStr, null, priceBought, totalval);
    if (portfolioMap.isEmpty()) {
      Map<String, Stock> m = new HashMap<>();
      m.put(CompanyName, s);
      portfolioMap.put(portfolioName, m);
    } else {
      Map<String, Stock> m1 = portfolioMap.get(portfolioName);
      if (!m1.containsKey(CompanyName)) {
        m1.put(CompanyName, s);
        portfolioMap.put(portfolioName, m1);
      } else {
        Stock s1 = m1.get(CompanyName);
        long totQty = s1.getQty() + qty;
        double val = s1.getTotalValue() + totalval;
        Stock s2 = new Stock(CompanyName, totQty, todayDateStr, null, priceBought, val);
        m1.put(CompanyName, s2);
        portfolioMap.put(portfolioName, m1);
      }
    }

    helperMethodToWriteTOCSV(portfolioName);

  }

  private void helperMethodToWriteTOCSV(String portfolioName) throws IOException {
    Map<String, Stock> mm = portfolioMap.get(portfolioName);
    List<String[]> temp = new ArrayList<>();
    temp.add(new String[]{"CompanyName", "Quantity", "PriceBought", "DatePurchase", "TotalValueOwned"});
    for (Map.Entry<String, Stock> entry : mm.entrySet()) {
      String[] s1 = new String[5];
      s1[0] = entry.getValue().getCompanyTickerSymbol();
      s1[1] = String.valueOf(entry.getValue().getQty());
      s1[2] = String.valueOf(entry.getValue().getPriceBought());
      s1[3] = String.valueOf(entry.getValue().getDateBought());
      s1[4] = String.format("%.2f", entry.getValue().getTotalValue());

      temp.add(s1);
    }
    customCSVParser.writeTOCSV(temp, portfolioName);
  }


  @Override
  public void sellStocks(String quantity, String CompanyName, String portfolioName) {

  }

  @Override
  public double getTotalValueOfPortfolioOnCertainDate(String date, String portfolioName) {
    Map<String, Stock> m= portfolioMap.get(portfolioName);
    double totVal=0.0;
    for (Map.Entry<String,Stock> entry : m.entrySet()) {
      String stkName = entry.getKey();
      if(todayDate.equals(date))
      {
        totVal=totVal+entry.getValue().getQty()*apiCustomClass.fetchStockPriceAsOfCertainDate(datePrev, entry.getKey(), datePrev);
      }
      else
      totVal=totVal+entry.getValue().getQty()*apiCustomClass.fetchStockPriceAsOfCertainDate(entry.getValue().getDateBought(), entry.getKey(), date);
    }
    return totVal;
  }

  @Override
  public PortfolioModel createPortfolioUsingFilePath(String filePath) {
    //validate filepath is correct or not
    List<List<String>> listOfStocks =customCSVParser.readFromCSV(filePath);
    //TODO:if any merging is required do it else
    //call writeTOCSV function directly
    //helperMethodToWriteTOCSV(filePath);
    return null;
  }

  @Override
  public boolean isPortfolioCreated() {
    return false;
  }

  @Override
  public List<List<String>> readFromCSVFile(String portfolioName) {
    List<List<String>> records = customCSVParser.readFromCSVAndModifyData(portfolioName);
    //updating the total quantity as of today
    return records;
  }


}
