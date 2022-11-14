package stocks.model;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * This class implements PortfolioModel.
 */
public class InFlexiblePortfolioImpl extends AbstractPortfolio {

  private static final String action = "add";

  @Override
  public void buyStocks(String quantity, String cName, String date, String portfolioName)
          throws IllegalArgumentException {
    validateQuantity(quantity);
    validateIfCompanyExists(cName);
    validateIfPortfolioAlreadyExists(portfolioName);
    this.portfolioName = portfolioName;
    double priceBought = apiCustomInterface.fetchLatestStockPriceOfThisCompany(cName);
    if (priceBought != -1) {
      String pattern = "yyyy-MM-dd";
      String todayDateStr = new SimpleDateFormat(pattern).format(
              new Date(System.currentTimeMillis()));
      double qty = Double.parseDouble(quantity);
      double totalVal = priceBought * qty;
      String companyName = cName.toUpperCase();
      Stock s = new Stock(companyName, qty, totalVal,action,priceBought,todayDateStr);
      List<Stock> listOfOneStock = new ArrayList<>();
      listOfOneStock.add(s);
      if (stockMap.isEmpty()) {
        Map<String, List<Stock>> m = new HashMap<>();
        m.put(companyName, listOfOneStock);
        stockMap.put(portfolioName, m);

      } else {
        Map<String, List<Stock>> m1 = stockMap.get(portfolioName);
        if (!m1.containsKey(companyName)) {
          m1.put(companyName, listOfOneStock);
          stockMap.put(portfolioName, m1);
        } else {
          List<Stock> s1 = m1.get(companyName);
          double totQty = s1.get(0).getQty() + qty;
          double val = s1.get(0).getTotalValue() + totalVal;
          Stock s2 = new Stock(companyName, totQty, val,action,priceBought,todayDateStr);
          List<Stock> ls=new ArrayList<>();
          ls.add(s2);
          m1.put(companyName, ls);
          stockMap.put(portfolioName, m1);
        }
      }
    }

  }


  @Override
  public void createPortfolioIfCreatedManually(String portfolioName) {
    if (portfolioName == null || portfolioName.equals("")) {
      throw new IllegalArgumentException("Invalid portfolioName provided");
    }
   /* List<String[]> temp = new ArrayList<>();
    String[] t = new String[5];
    t[0] = "CompanyName";
    t[1] = "Quantity";
    t[2] = "PriceBought";
    t[3] = "DatePurchase";
    t[4] = "TotalValueOwned";
    temp.add(t);
    if (!stockMap.isEmpty()) {
      Map<String, List<Stock>> mm = stockMap.get(portfolioName);
      for (Map.Entry<String, List<Stock>> entry : mm.entrySet()) {
        String[] s1 = new String[5];
        s1[0] = entry.getValue().get(0).getCompanyTickerSymbol();
        s1[1] = String.format("%.2f", entry.getValue().get(0).getQty());
        s1[2] = String.valueOf(entry.getValue().get(0).getPriceOfStockAsOfGivenDate());
        s1[3] = String.valueOf(entry.getValue().get(0).getDateOfAction());
        s1[4] = String.format("%.2f", entry.getValue().get(0).getTotalValue());
        temp.add(s1);
      }*/
      this.portfolioName = portfolioName;
      //TODO:check this
      //parser.write(temp, portfolioName);
      parser.writeIntoFile(portfolioName,stockMap.get(portfolioName),"inflexible");
    }


  @Override
  public Portfolio getInstance() {
    return new InFlexiblePortfolioImpl();
  }

  @Override
  public void sellStocks(String companyName, String quantity, String date, String portfolioName) {
    throw new UnsupportedOperationException("This operation is not supported in Inflexible portfolio");
  }

  @Override
  public void updatePortfolio(String companyName, String quantity, String date, String portfolioName, String action) {
    throw new UnsupportedOperationException("This operation is not supported in Inflexible portfolio");
  }

  @Override
  public void updatePortfolioUsingFilePath(String path, String companyName, String quantity, String date, String portfolioName, String action) throws IllegalArgumentException {
    throw new UnsupportedOperationException("This operation is not supported in Inflexible portfolio");
  }

  @Override
  public double getTotalMoneyInvestedOnCertainDate(String date, String portfolioName) {
    double totalCostBasis = 0.0;
    int noOfRecords = 0;
    Map<String, List<Stock>> m = null;
    if (portfolioName.equals("currentInstance") || this.portfolioName.equals(portfolioName)) {
      if (!stockMap.isEmpty()) {
        m = stockMap.get(portfolioName);
      }
    } else {
      validateIfPortfolioDoesntExists(portfolioName);
      m = parser.readFromFile(portfolioName);
      stockMap.put(portfolioName, m);
    }
    if (m != null) {
      for (Map.Entry<String, List<Stock>> entry : m.entrySet()) {
        List<Stock> listOfStocks = entry.getValue();
        String dateBought = listOfStocks.get(0).getDateOfAction();
        Date dateBoughtObj, givenDateObj;
        try {
          dateBoughtObj = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(dateBought);
          givenDateObj = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
        } catch (ParseException e) {
          throw new IllegalArgumentException("unable to parse date");
        }
        if (dateBoughtObj.compareTo(givenDateObj) <= 0) {

          totalCostBasis = totalCostBasis + apiCustomInterface.getStockPriceAsOfCertainDate(listOfStocks.get(0).getCompanyTickerSymbol(), listOfStocks.get(0).getQty(), listOfStocks.get(0).getDateOfAction());
          totalCostBasis = totalCostBasis + commissionPerTransaction;
        }
      }


    }

    return totalCostBasis;
  }

  @Override
  public Map<String, Double> getPortfolioPerformanceOvertime(String startTime, String endTime, String portfolioName) {
    return null;
  }

  @Override
  public double getTotalValueOfPortfolioOnCertainDate(String date, String portfolioName) {
    if (portfolioName == null || portfolioName.equals("")) {
      throw new IllegalArgumentException("Invalid portfolioName provided");
    }
    validateDate(date);
    double totValue = 0.0;
    if (portfolioName.equals("currentInstance") || this.portfolioName.equals(portfolioName)) {
      if (!stockMap.isEmpty()) {
        Map<String, List<Stock>> map = stockMap.get(this.portfolioName);
        for (Map.Entry<String, List<Stock>> entry : map.entrySet()) {
          List<Stock> s = entry.getValue();
          double temp;
          try {
            temp = apiCustomInterface.getStockPriceAsOfCertainDate(
                    s.get(0).getCompanyTickerSymbol(), s.get(0).getQty(), date);
          } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
          }
          totValue = totValue + temp;
        }
      }
    } else {
      validateIfPortfolioDoesntExists(portfolioName);
      Map<String,List<Stock>> mapOfStkInfoPersisted = parser.readFromFile(portfolioName);
      for (Map.Entry<String, List<Stock>> entry : mapOfStkInfoPersisted.entrySet()) {
        String companyTickerSymbol = entry.getKey();
        double qty = entry.getValue().get(0).getQty();
        try {
          totValue = totValue + apiCustomInterface.getStockPriceAsOfCertainDate(
                  companyTickerSymbol, qty, date);
        }catch (IllegalArgumentException e) {
          throw new IllegalArgumentException(e.getMessage());
        }

      }


    }
    return totValue;
  }

  @Override
  public void loadPortfolioUsingFilePath(String filePath) {
    validateFilePath(filePath);
    Map<String,List<Stock>> listOfStocks;
    //List<List<String>> listOfStocks;
    try {
      listOfStocks = parser.readFromPathProvidedByUser(filePath);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }

   /* String action="add";
    Map<String, List<Stock>> mapOfStocks = new HashMap<>();
    String pattern = "yyyy-MM-dd";
    String todayDate = new SimpleDateFormat(pattern).format(new Date(System.currentTimeMillis()));
    for (Map.Entry<String, List<Stock>> entry : listOfStocks.entrySet()) {
      String sName = entry.getKey();
      //double sPrice = apiCustomInterface.fetchLatestStockPriceOfThisCompany(sName);
      //if (sPrice != -1) {
        if (!mapOfStocks.containsKey(sName)) {
          //double value = Double.parseDouble(listOfStocks.get(i).get(1)) * sPrice;
          Stock st = new Stock(sName,
                  entry.getValue().get(0).getQty(),0.0, action ,0.0,todayDate);
          List<Stock> ls=new ArrayList<>();
          ls.add(st);
          mapOfStocks.put(sName, ls);
        } else {
          List<Stock> list1 = mapOfStocks.get(sName);
          List<Stock> list2 = entry.getValue();
          mapOfStocks.remove(sName);
          double totQty = list1.get(0).getQty() + list2.get(0).getQty();
          //double value = Double.parseDouble(listOfStocks.get(i).get(1)) * sPrice;
          Stock st = new Stock(sName, totQty, 0.0, action ,0.0,todayDate);
          List<Stock> ls=new ArrayList<>();
          ls.add(st);
          mapOfStocks.put(sName, ls);
        }
      }*/

    this.portfolioName = "currentInstance";
    stockMap.put(portfolioName, listOfStocks);
  }

  @Override
  public List<List<String>> viewCompositionOfCurrentPortfolio(String portfolioName, String date) {
    if (portfolioName == null || portfolioName.equals("")) {
      throw new IllegalArgumentException("Invalid portfolioName provided");
    }
    List<List<String>> results = new ArrayList<>();
    Map<String, List<Stock>> map = new HashMap<>();
    if (portfolioName.equals("currentInstance") || this.portfolioName.equals(portfolioName)) {
      if (!stockMap.isEmpty()) {
        map = stockMap.get(this.portfolioName);
      }
    }
    else {

      validateIfPortfolioDoesntExists(portfolioName);

      map = parser.readFromFile(portfolioName);
    }
     /* List<String> list = records.get(0);

      for (Map.Entry<String, List<Stock>> entry : mapOfStkInfoPersisted.entrySet()) {

      }


      List<List<String>> records = parser.readFromFile(portfolioName);
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
        double currentPrice = apiCustomInterface.fetchLatestStockPriceOfThisCompany(cName);
        if (currentPrice != -1) {
          double currentTotalPrice = Double.parseDouble(quantity) * currentPrice;
          list1.addAll(records.get(i));
          list1.add(String.format("%.2f", currentTotalPrice));
          results.add(list1);
        }
      }
    }*/
    String[] t = new String[3];
    t[0] = "CompanyName";
    t[1] = "Quantity";
    //t[2] = "PriceBought";
    t[2] = "DatePurchase";
    //t[4] = "TotalValueOwned";
    results.add(List.of(t));
    for (Map.Entry<String, List<Stock>> entry : map.entrySet()) {
      List<String> temp = new ArrayList<>();
      List<Stock> s = entry.getValue();
      temp.add(s.get(0).getCompanyTickerSymbol());
      temp.add(String.valueOf(s.get(0).getQty()));
      //temp.add(String.valueOf(s.get(0).getPriceOfStockAsOfGivenDate()));
      temp.add(s.get(0).getDateOfAction());
      //temp.add(String.format("%.2f", s.get(0).getTotalValue()));
      results.add(temp);
    }

    return results;

  }


  @Override
  public void validateIfPortfolioDoesntExists(String portfolioName) {
    if (portfolioName == null) {
      throw new IllegalArgumentException("Invalid portfolioName provided");
    }
    String path = "userPortfolios/" + portfolioName + "_output" + ".json";
    File f = new File(path);
    if (!f.isFile() || !f.exists()) {
      throw new IllegalArgumentException("Given portfolio doesnt exist."
              + "Please provide valid portfolioName.");
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
