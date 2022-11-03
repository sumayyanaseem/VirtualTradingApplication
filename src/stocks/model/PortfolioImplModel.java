package stocks.model;

import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * This class implements PortfolioModel.
 */
public class PortfolioImplModel implements PortfolioModel {

  private final Map<String, Map<String, Stock>> portfolioMap;

  private final APICustomClass apiCustomClass;

  private final CustomCSVParser customCSVParser;
  private String pName;

  /**
   * Constructs a PortfolioImplModel object and initializes a portfolio map and custom class references.
   */
  public PortfolioImplModel() {
    portfolioMap = new HashMap<>();
    apiCustomClass = new APICustomClass();
    customCSVParser = new CustomCSVParser();
    this.pName = "";
  }

  @Override
  public void buyStocks(String quantity, String cName, String portfolioName) throws IllegalArgumentException {
    validateQuantity(quantity);
    validateIfCompanyExists(cName);
    validateIfPortfolioAlreadyExists(portfolioName);
    this.pName = portfolioName;
    double priceBought = apiCustomClass.fetchLatestStockPriceOfThisCompany(cName);
    if (priceBought != -1) {
      String pattern = "yyyy-MM-dd";
      String todayDateStr = new SimpleDateFormat(pattern).format(new Date(System.currentTimeMillis()));
      double qty = Double.parseDouble(quantity);
      double totalVal = priceBought * qty;
      String companyName = cName.toUpperCase();
      Stock s = new Stock(companyName, qty, todayDateStr, null, priceBought, totalVal);
      if (portfolioMap.isEmpty()) {
        Map<String, Stock> m = new HashMap<>();
        m.put(companyName, s);
        portfolioMap.put(portfolioName, m);

      } else {
        Map<String, Stock> m1 = portfolioMap.get(portfolioName);
        if (!m1.containsKey(companyName)) {
          m1.put(companyName, s);
          portfolioMap.put(portfolioName, m1);
        } else {
          Stock s1 = m1.get(companyName);
          double totQty = s1.getQty() + qty;
          double val = s1.getTotalValue() + totalVal;
          Stock s2 = new Stock(companyName, totQty, todayDateStr, null, priceBought, val);
          m1.put(companyName, s2);
          portfolioMap.put(portfolioName, m1);
        }
      }
    }

  }


  @Override
  public void createPortfolioIfCreatedManually(String portfolioName) {
    if (portfolioName == null || portfolioName.equals("")) {
      throw new IllegalArgumentException("Invalid portfolioName provided");
    }
   // validateIfPortfolioAlreadyExists(portfolioName);
    List<String[]> temp = new ArrayList<>();
    temp.add(new String[]{"CompanyName", "Quantity", "PriceBought", "DatePurchase", "TotalValueOwned"});
    if (!portfolioMap.isEmpty()) {
      Map<String, Stock> mm = portfolioMap.get(portfolioName);
      for (Map.Entry<String, Stock> entry : mm.entrySet()) {
        String[] s1 = new String[5];
        s1[0] = entry.getValue().getCompanyTickerSymbol();
        s1[1] = String.format("%.2f", entry.getValue().getQty());
        s1[2] = String.valueOf(entry.getValue().getPriceBought());
        s1[3] = String.valueOf(entry.getValue().getDateBought());
        s1[4] = String.format("%.2f", entry.getValue().getTotalValue());
        temp.add(s1);
      }
      this.pName = portfolioName;
      customCSVParser.writeTOCSV(temp, portfolioName);
    }
  }

  @Override
  public PortfolioModel getInstance() {
    return new PortfolioImplModel();
  }

  @Override
  public double getTotalValueOfPortfolioOnCertainDate(String date, String portfolioName) {
    if (portfolioName == null || portfolioName.equals("")) {
      throw new IllegalArgumentException("Invalid portfolioName provided");
    }
    validateDate(date);
    double totValue = 0.0;
    if (portfolioName.equals("currentInstance") || this.pName.equals(portfolioName)) {
      if (!portfolioMap.isEmpty()) {
        Map<String, Stock> map = portfolioMap.get(this.pName);
        for (Map.Entry<String, Stock> entry : map.entrySet()) {
          Stock s = entry.getValue();
          double temp;
          try {
            temp = apiCustomClass.getStockPriceAsOfCertainDate(s.getCompanyTickerSymbol(), s.getQty(), date);
          } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
          }
          totValue = totValue + temp;
        }
      }
    } else {
      validateIfPortfolioDoesntExists(portfolioName);
      List<List<String>> listOfStkInfoPersisted = customCSVParser.readFromCSV(portfolioName);
      for (int j = 1; j < listOfStkInfoPersisted.size(); j++) {
        String companyTickerSymbol = listOfStkInfoPersisted.get(j).get(0);
        double qty = Double.parseDouble(listOfStkInfoPersisted.get(j).get(1));
        try {
          totValue = totValue + apiCustomClass.getStockPriceAsOfCertainDate(companyTickerSymbol, qty, date);
        } catch (IllegalArgumentException e) {
          throw new IllegalArgumentException(e.getMessage());
        }

      }
    }
    return totValue;
  }

  @Override
  public void createPortfolioUsingFilePath(String filePath) {
    validateFilePath(filePath);
    List<List<String>> listOfStocks;
    try {
      listOfStocks = customCSVParser.readFromPathProvidedByUser(filePath);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
    Map<String, Stock> mapOfStocks = new HashMap<>();
    String pattern = "yyyy-MM-dd";
    String todayDate = new SimpleDateFormat(pattern).format(new Date(System.currentTimeMillis()));
    for (int i = 1; i < listOfStocks.size(); i++) {
      String sName = listOfStocks.get(i).get(0);
      double sPrice = apiCustomClass.fetchLatestStockPriceOfThisCompany(sName);
      if (sPrice != -1) {
        if (!mapOfStocks.containsKey(sName)) {
          double value = Double.parseDouble(listOfStocks.get(i).get(1)) * sPrice;
          Stock st = new Stock(listOfStocks.get(i).get(0), Double.parseDouble(listOfStocks.get(i).get(1)), todayDate, null, sPrice, value);
          mapOfStocks.put(sName, st);
        } else {
          Stock list1 = mapOfStocks.get(sName);
          List<String> list2 = listOfStocks.get(i);
          mapOfStocks.remove(sName);
          double totQty = list1.getQty() + Double.parseDouble(list2.get(1));
          double value = Double.parseDouble(listOfStocks.get(i).get(1)) * sPrice;
          Stock st = new Stock(listOfStocks.get(i).get(0), totQty, todayDate, null, sPrice, value);
          mapOfStocks.put(sName, st);
        }
      }
    }

    this.pName = "currentInstance";
    portfolioMap.put(pName, mapOfStocks);


  }

  @Override
  public List<List<String>> viewCompositionOfCurrentPortfolio(String portfolioName) {
    if (portfolioName == null || portfolioName.equals("")) {
      throw new IllegalArgumentException("Invalid portfolioName provided");
    }
    List<List<String>> results = new ArrayList<>();
    if (portfolioName.equals("currentInstance") || this.pName.equals(portfolioName)) {
      if (!portfolioMap.isEmpty()) {
        Map<String, Stock> map = portfolioMap.get(this.pName);
        String[] headers = new String[]{"CompanyName", "Quantity", "PriceBought", "DatePurchase", "TotalValueWhenPurchased"};
        results.add(List.of(headers));
        for (Map.Entry<String, Stock> entry : map.entrySet()) {
          List<String> temp = new ArrayList<>();
          Stock s = entry.getValue();
          temp.add(s.getCompanyTickerSymbol());
          temp.add(String.valueOf(s.getQty()));
          temp.add(String.valueOf(s.getPriceBought()));
          temp.add(s.getDateBought());
          temp.add(String.valueOf(s.getTotalValue()));
          results.add(temp);
        }
      }
    } else {
      validateIfPortfolioDoesntExists(portfolioName);
      List<List<String>> records = customCSVParser.readFromCSV(portfolioName);
      List<String> list = records.get(0);
      String name = "TotalValueOwnedAsOfToday";
      List<String> list1 = new ArrayList<>();
      list1.addAll(list);
      list1.add(name);
      results.add(list1);
      for (int i = 1; i < records.size(); i++) {
        list1 = new ArrayList<>();
        String cName = records.get(i).get(0);
        String quantity = records.get(i).get(1);
        double currentPrice = apiCustomClass.fetchLatestStockPriceOfThisCompany(cName);
        if (currentPrice != -1) {
          double currentTotalPrice = Double.parseDouble(quantity) * currentPrice;
          list1.addAll(records.get(i));
          list1.add(String.format("%.2f", currentTotalPrice));
          results.add(list1);
        }
      }
    }
    return results;

  }


  /**
   * gives the portfolio info in string format.
   *
   * @return a string constructed using the portfolio map.
   */
  @Override
  public String toString() {
    StringBuilder res = new StringBuilder();

    if (portfolioMap != null && !portfolioMap.isEmpty()) {
      res.append("PortfolioName : ").append(this.pName);
      res.append("\n");
      Map<String, Stock> temp = portfolioMap.get(this.pName);
      for (Map.Entry<String, Stock> entry : temp.entrySet()) {
        res.append(entry.getValue().toString());
      }

    }
    return res.toString();
  }


  private void validateQuantity(String quantity) {
    if (quantity == null) {
      throw new IllegalArgumentException("Invalid quantity provided");
    }
    try {
      double q = Double.parseDouble(quantity);
      if (q <= 0 || q > Integer.MAX_VALUE) {
        throw new IllegalArgumentException("Invalid quantity provided");
      }
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Quantity should be always a positive whole number.");
    }
  }

  @Override
  public void validateIfCompanyExists(String companyName) {
    if (companyName == null) {
      throw new IllegalArgumentException("Invalid companyName provided");
    }
    String name = companyName.toUpperCase();
    String path = "availableStocks" + File.separator + "daily_" + name + ".csv";
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream is = classLoader.getSystemClassLoader().getResourceAsStream(path);
    if (is == null) {
      throw new IllegalArgumentException("Given company doesnt exist in our records.Please provide valid  companyTicker symbol.");
    }

  }

  @Override
  public void validateIfPortfolioAlreadyExists(String portfolioName) {
    if (portfolioName == null || portfolioName.equals("")) {
      throw new IllegalArgumentException("Invalid portfolioName provided");
    }
    String path = "userPortfolios/" + portfolioName + "_output" + ".csv";
    File f = new File(path);
    if (f.isFile() && f.exists()) {
      throw new IllegalArgumentException("Given portfolio exist.Please provide valid portfolioName.");
    }
  }

  @Override
  public void validateIfPortfolioDoesntExists(String portfolioName) {
    if (portfolioName == null) {
      throw new IllegalArgumentException("Invalid portfolioName provided");
    }
    String path = "userPortfolios/" + portfolioName + "_output" + ".csv";
    File f = new File(path);
    if (!f.isFile() || !f.exists()) {
      throw new IllegalArgumentException("Given portfolio doesnt exist.Please provide valid portfolioName.");
    }
  }


  private void validateDate(String date) {
    String format = "yyyy-MM-dd";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH);
    try {
      LocalDate ld = LocalDate.parse(date, formatter);
      String result = ld.format(formatter);
      if (!result.equals(date)) {
        throw new IllegalArgumentException("Invalid dateFormat provided.Please provide date in YYYY-MM-DD format only.");
      } else {
        String todayDateStr = new SimpleDateFormat(format).format(new Date(System.currentTimeMillis()));
        Date todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse(todayDateStr);
        Date givenDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse(date);
        if (givenDate.compareTo(todayDate) > 0)
          throw new IllegalArgumentException("Future Date provided.Please provide date less then or equal to today");
      }
    } catch (IllegalArgumentException | DateTimeParseException e) {
      throw new IllegalArgumentException("Invalid dateFormat provided.Please provide date in YYYY-MM-DD format only.");
    } catch (ParseException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  private void validateFilePath(String path) {

    if (path == null) {
      throw new IllegalArgumentException("Given path doesnt exist.Please provide valid path.");
    }
    File f = new File(path);
    if (!f.isFile() || !f.exists()) {
      throw new IllegalArgumentException("Given path doesnt exist.Please provide valid path.");
    }
  }


}
