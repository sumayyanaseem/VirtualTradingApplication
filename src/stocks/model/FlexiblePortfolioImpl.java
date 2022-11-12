package stocks.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FlexiblePortfolioImpl extends AbstractPortfolio {
  private String action;

  @Override
  public void buyStocks(String companyName, String quantity, String date, String portfolioName) {
    action = "buy";
    this.portfolioName = portfolioName;
    validateInputsForBuy(portfolioName, companyName, quantity, date);
    String cName = companyName.toUpperCase();
    double q = Double.valueOf(quantity);
    Stock s = new Stock(cName, q, 0.0, action, 0.0, date);
    if (stockMap.isEmpty()) {
      Map<String, List<Stock>> m = new HashMap<>();
      List<Stock> list = new ArrayList<>();
      list.add(s);
      m.put(companyName, list);
      stockMap.put(portfolioName, m);

    } else {
      Map<String, List<Stock>> m = stockMap.get(portfolioName);
      if (!m.containsKey(companyName)) {
        List<Stock> list = new ArrayList<>();
        list.add(s);
        m.put(companyName, list);
        stockMap.put(portfolioName, m);
      } else {
        boolean flag = false;
        List<Stock> stockList = m.get(companyName);
        for (int i = 0; i < stockList.size(); i++) {
          String dateBought = stockList.get(i).getDateOfAction();
          if (areDatesEqual(dateBought, date)) {
            double totQty = Double.parseDouble(stockList.get(i).getQty() + quantity);
            s = new Stock(companyName, totQty, 0.0, action, 0.0, date);
            stockList.remove(i);
            stockList.add(s);
            m.put(companyName, stockList);
            stockMap.put(portfolioName, m);
            flag = true;
          }
        }
        if (!flag) {
          stockList.add(s);
          m.put(companyName, stockList);
          stockMap.put(portfolioName, m);
        }
      }
    }

  }


  private boolean areDatesEqual(String date1, String date2) {
    Date date3 = null;
    Date date4 = null;
    try {
      date3 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
              .parse(date1);
      date4 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
              .parse(date2);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
    if (date3.compareTo(date4) == 0) {
      return true;
    }
    return false;
  }

  @Override
  public void sellStocks(String companyName, String quantity, String date, String portfolioName) {
    action = "sell";
    validateInputsForSell(portfolioName, companyName, quantity, date);
    if (stockMap.isEmpty()) {
      //throw an error that you cant sell bfr buying
    }
    Map<String, List<Stock>> m1 = stockMap.get(portfolioName);
    if (companyName == null || !m1.containsKey(companyName)) {
      //throw error that this company doesnt exist
    } else {
      //if sellMap is not empty, then validate if entry of this company exists or not.
      try {
        double netQuantity = getQuantityOnThisDateForGivenCompanyName(date, companyName);
        double q = Double.valueOf(quantity);
        if (netQuantity < q) {
          //then throw error , that its not valid.
        } else {
          String lastSellDate = getLastSellDate(companyName);
          Date givenDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                  .parse(date);
          Date lastDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                  .parse(lastSellDate);
          if (lastDate.compareTo(givenDate) > 0) {
            throw new IllegalArgumentException("Sell date is not in chronological order");
          } else {
            Stock s = new Stock(companyName, q, 0.0, action, 0.0, date);
            List<Stock> stockList = m1.get(companyName);
            stockList.add(s);
            m1.put(companyName, stockList);
            stockMap.put(portfolioName, m1);
          }
        }
      } catch (ParseException e) {

      }
    }
  }

  @Override
  public void updatePortfolio(String companyName, String quantity, String date, String portfolioName, String action) {
    if (action.equals("buy")) {
      if (stockMap.isEmpty()) {
        stockMap = new HashMap<>();
        Map<String, List<Stock>> map = parser.readFromFile(portfolioName);
        stockMap.put(portfolioName, map);
      }
      buyStocks(companyName, quantity, date, portfolioName);
      parser.appendIntoFile(portfolioName,companyName,quantity,action,date);
    } else if (action.equals("Sell")) {
      if (stockMap.isEmpty()) {
        stockMap = new HashMap<>();
        Map<String, List<Stock>> map = parser.readFromFile(portfolioName);
        stockMap.put(portfolioName, map);
      }
      sellStocks(companyName, quantity, date, portfolioName);
      parser.appendIntoFile(portfolioName,companyName,quantity,action,date);
    }
  }


  private String getLastSellDate(String companyName) throws ParseException {
    Map<String, List<Stock>> m = stockMap.get(portfolioName);
    List<Stock> list = m.get(companyName);
    String date = "1999-12-31";
    Date todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            .parse(date);
    String date2 = null;
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getAction().equals("sell")) {
        date2 = list.get(i).getDateOfAction();
        Date datePresent = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse(date2);
        if (datePresent.compareTo(todayDate) <= 0) {
          todayDate = datePresent;
        }
      }
    }
    return date;
  }

  private double getQuantityOnThisDateForGivenCompanyName(String date, String companyName) throws ParseException {
    Map<String, List<Stock>> m = stockMap.get(portfolioName);
    List<Stock> list = m.get(companyName);
    Date givenDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            .parse(date);
    double quantity = 0;
    for (int i = 0; i < list.size(); i++) {
      String datePresent = list.get(i).getDateOfAction();
      Date todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
              .parse(datePresent);
      if (todayDate.compareTo(givenDate) <= 0) {
        if (list.get(i).getAction().equals("buy")) {
          quantity += list.get(i).getQty();
        } else if (list.get(i).getAction().equals("sell")) {
          quantity -= list.get(i).getQty();
        }
      }
    }
    return quantity;
  }

  @Override
  public double getTotalMoneyInvestedOnCertainDate(String date, String portfolioName) {
    Map<String, List<Stock>> m = stockMap.get(portfolioName);
    double totalCostBasis = 0.0;
    for (Map.Entry<String, List<Stock>> entry : m.entrySet()) {

      List<Stock> listOfStocks = entry.getValue();
      for (int i = 0; i < listOfStocks.size(); i++) {
        String dateBought = listOfStocks.get(i).getDateOfAction();
        Date dateBoughtObj, givenDateObj;
        try {
          dateBoughtObj = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(dateBought);
          givenDateObj = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
        } catch (ParseException e) {
          throw new IllegalArgumentException("unable to parse date");
        }
        if (listOfStocks.get(i).getAction().equals("buy") && dateBoughtObj.compareTo(givenDateObj) <= 0) {
          totalCostBasis = totalCostBasis + listOfStocks.get(i).getQty() * listOfStocks.get(i).getPriceOfStockAsOfGivenDate();
        }
      }
    }
    return totalCostBasis;
  }

  @Override
  public void addStocks(String quantity, String companyName, String portfolioName) {

  }

  @Override
  public double getTotalValueOfPortfolioOnCertainDate(String date, String portfolioName) {

    double totalValue = 0.0;
    if(!stockMap.isEmpty()) {
      Map<String, List<Stock>> m = stockMap.get(portfolioName);

      for (Map.Entry<String, List<Stock>> entry : m.entrySet()) {
        String stkName = entry.getKey();
        try {
          double netQty = getQuantityOnThisDateForGivenCompanyName(date, stkName);
          totalValue = totalValue + apiCustomInterface.getStockPriceAsOfCertainDate(stkName, netQty, date);
        } catch (ParseException e) {
        }
      }
    } else {

    }
    return totalValue;

  }

  @Override
  public void loadPortfolioUsingFilePath(String filePath) {

  }

  @Override
  public List<List<String>> viewCompositionOfCurrentPortfolio(String portfolioName) {
    if (portfolioName == null || portfolioName.equals("")) {
      throw new IllegalArgumentException("Invalid portfolioName provided");
    }
    List<List<String>> results = new ArrayList<>();
    String[] t = new String[5];
    t[0] = "CompanyName";
    t[1] = "Quantity";
    t[2] = "PriceBought";
    t[3] = "Date";
    t[4] = "Action";
    results.add(List.of(t));
    if (portfolioName.equals("currentInstance") || this.portfolioName.equals(portfolioName)) {
      if (!stockMap.isEmpty()) {
        Map<String, List<Stock>> map = stockMap.get(this.portfolioName);
        for (Map.Entry<String, List<Stock>> entry : map.entrySet()) {
          List<String> temp = new ArrayList<>();
          List<Stock> s = entry.getValue();
          temp.add(s.get(0).getCompanyTickerSymbol());
          temp.add(String.valueOf(s.get(0).getQty()));
          temp.add(String.valueOf(s.get(0).getPriceOfStockAsOfGivenDate()));
          temp.add(s.get(0).getDateOfAction());
          temp.add(s.get(0).getAction());
          results.add(temp);
        }
      }
    } else {
      //TODO:update this validation
     // validateIfPortfolioDoesntExists(portfolioName);
      Map<String, List<Stock>>  records = parser.readFromFile(portfolioName);
      stockMap.put(portfolioName,records);
      for (Map.Entry<String, List<Stock>> entry : records.entrySet()) {
        List<Stock> s = entry.getValue();
        for(int i=0;i<s.size();i++) {
          List<String> temp = new ArrayList<>();
          temp.add(s.get(i).getCompanyTickerSymbol());
          temp.add(String.valueOf(s.get(i).getQty()));
          temp.add(String.valueOf(s.get(i).getPriceOfStockAsOfGivenDate()));
          temp.add(s.get(i).getDateOfAction());
          temp.add(s.get(i).getAction());
          results.add(temp);
        }
      }
    }
    return results;
  }

  @Override
  public void createPortfolioIfCreatedManually(String portfolioName) {
    if (!stockMap.isEmpty()) {
      Map<String, List<Stock>> mm = stockMap.get(portfolioName);
      printMap(mm);
      parser.writeIntoFile(portfolioName, mm, "flexible");
    }
  }

  private void printMap(Map<String, List<Stock>> stockMap){
    for (Map.Entry<String, List<Stock>> entry : stockMap.entrySet()) {
      System.out.println(entry.getKey()+" values is ");
      List<Stock> list = entry.getValue();
      for(int i=0;i<list.size();i++){
        System.out.println(list.get(i).getQty());
        System.out.println(list.get(i).getAction());
        System.out.println(list.get(i).getDateOfAction());
      }

    }
  }

  @Override
  public Portfolio getInstance() {
    return new FlexiblePortfolioImpl();
  }

  @Override
  public Map<String, Double> getPortfolioPerformanceOvertime(String startTime, String endTime, String portfolioName) {
    return null;
  }

  private void validateInputsForBuy(String portfolioName, String companyName, String quantity, String date) {
    validateIfPortfolioExists(portfolioName, action);
    validateIfCompanyExists(companyName);
    validateQuantity(quantity);
    validateDate(date);
  }

  private void validateInputsForSell(String portfolioName, String companyName, String quantity, String date) {
    validateIfPortfolioExists(portfolioName, action);
    validateQuantity(quantity);
    validateDate(date);
  }

}
