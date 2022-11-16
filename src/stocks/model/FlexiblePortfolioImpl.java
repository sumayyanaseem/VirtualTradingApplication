package stocks.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import stocks.customAPI.CompanyTickerSymbol;

public class FlexiblePortfolioImpl extends AbstractPortfolio {
  private static String action;


  @Override
  public void buyStocks(String companyName, String quantity, String date, String commission, String portfolioName) throws IllegalArgumentException {
    action = "buy";
    this.portfolioName = portfolioName;
    validateInputsForBuy(companyName, quantity, date,commission);
    String cName = companyName.toUpperCase();
    double q = Double.parseDouble(quantity);
    double c = Double.parseDouble(commission);
    Stock s = new Stock(cName, q, 0.0, action, 0.0, date, c);
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
            double totQty = stockList.get(i).getQty() + q;
            s = new Stock(companyName, totQty, 0.0, action, 0.0, date, c);
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
    Date date3;
    Date date4;
    try {
      date3 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
              .parse(date1);
      date4 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
              .parse(date2);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
    return date3.compareTo(date4) == 0;
  }

  @Override
  public void sellStocks(String companyName, String quantity, String date, String commission, String portfolioName) throws IllegalArgumentException {
    action = "sell";
    validateInputsForSell(quantity, date,commission);
    if (stockMap.isEmpty()) {
      throw new IllegalArgumentException("It is impossible to sell a stock without first purchasing it.");
    }
    Map<String, List<Stock>> m1 = stockMap.get(portfolioName);
    if (companyName == null || !m1.containsKey(companyName)) {
      throw new IllegalArgumentException("As the given company's stock does not exist with in current portfolio, it cannot be sold.");
    } else {
      //if sellMap is not empty, then validate if entry of this company exists or not.
      try {
        double netQuantity = getQuantityOnThisDateForGivenCompanyName(date, companyName);
        double q = Double.parseDouble(quantity);
        if (netQuantity == 0) {
          throw new IllegalArgumentException("trying to sell before buying");
        }
        if (netQuantity < q) {
          throw new IllegalArgumentException("The given quantity exceeds the net quantity.");
        } else {
          String lastSellDate = getLastSellDate(companyName);
          Date givenDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                  .parse(date);
          Date lastDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                  .parse(lastSellDate);
          if (lastDate.compareTo(givenDate) > 0) {
            throw new IllegalArgumentException("Sell date is not in the chronological order.Last date present is " + lastDate);
          } else {
            String cName = companyName.toUpperCase();
            double c = Double.parseDouble(commission);
            Stock s = new Stock(cName, q, 0.0, action, 0.0, date, c);
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

  private void updatePortfolioHelper(String companyName, String quantity, String date, String portfolioName, String commission, String action) throws IllegalArgumentException {
    validateInputsForUpdate(portfolioName, companyName, quantity, date, action, commission);
    this.portfolioName = portfolioName;
    if (stockMap.isEmpty()) {
      stockMap = new HashMap<>();
      Map<String, List<Stock>> map = parser.readFromFile(portfolioName);
      stockMap.put(portfolioName, map);
    }

    if (action.equalsIgnoreCase("buy")) {
      buyStocks(companyName, quantity, date, commission, portfolioName);
    } else if (action.equalsIgnoreCase("sell")) {
      sellStocks(companyName, quantity, date, commission, portfolioName);
    }
  }


  @Override
  public void updatePortfolio(String companyName, String quantity, String date, String portfolioName, String action, String commission) {
    try {
      updatePortfolioHelper(companyName, quantity, date, portfolioName, action, commission);
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    parser.appendIntoFile(portfolioName, companyName, quantity, action, date, commission);
  }

  @Override
  public void updatePortfolioUsingFilePath(String filePath, String companyName, String quantity, String date, String portfolioName, String action, String commission) {
    try {
      updatePortfolioHelper(companyName, quantity, date, portfolioName, action, commission);
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    parser.appendIntoFileUsingFilePath(filePath, portfolioName, companyName, quantity, action, date, commission);
  }


  private String getLastSellDate(String companyName) throws ParseException {
    Map<String, List<Stock>> m = stockMap.get(portfolioName);
    List<Stock> list = m.get(companyName);
    String date = "1000-12-31";
    Date todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            .parse(date);
    String date2;
    for (Stock stock : list) {
      if (stock.getAction().equals("sell")) {
        date2 = stock.getDateOfAction();
        Date datePresent = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse(date2);
        if (datePresent.compareTo(todayDate) > 0) {
          todayDate = datePresent;
          date = date2;
        }
      }
    }
    return date;
  }

  public double getQuantityOnThisDateForGivenCompanyName(String date, String companyName) throws ParseException {
    Map<String, List<Stock>> m = stockMap.get(portfolioName);
    List<Stock> list = m.get(companyName);
    Date givenDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            .parse(date);
    double quantity = 0;
    for (Stock stock : list) {
      String datePresent = stock.getDateOfAction();
      Date todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
              .parse(datePresent);
      if (todayDate.compareTo(givenDate) <= 0) {
        if (stock.getAction().equals("buy")) {
          quantity += stock.getQty();
        } else if (stock.getAction().equals("sell")) {
          quantity -= stock.getQty();
        }
      }
    }
    return quantity;
  }


  private boolean checkIfDateIsLessThanGivenDate(String date, Stock s) throws ParseException {
    Date givenDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            .parse(date);

    String datePresent = s.getDateOfAction();
    Date todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            .parse(datePresent);
    if (todayDate.compareTo(givenDate) <= 0) {
      return true;
    }
    return false;
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
      this.portfolioName = portfolioName;
      m = parser.readFromFile(portfolioName);
      this.stockMap.put(portfolioName, m);
    }
    if (m != null) {
      for (Map.Entry<String, List<Stock>> entry : m.entrySet()) {
        List<Stock> listOfStocks = entry.getValue();
        for (Stock listOfStock : listOfStocks) {
          String dateBought = listOfStock.getDateOfAction();
          Date dateBoughtObj, givenDateObj;
          try {
            dateBoughtObj = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(dateBought);
            givenDateObj = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
          } catch (ParseException e) {
            throw new IllegalArgumentException("unable to parse date");
          }
          if (listOfStock.getAction().equals("buy") && dateBoughtObj.compareTo(givenDateObj) <= 0) {
            noOfRecords++;
            totalCostBasis = totalCostBasis + apiCustomInterface.getStockPriceAsOfCertainDate(listOfStock.getCompanyTickerSymbol(), listOfStock.getQty(), date) + listOfStock.getCommission();
          }
        }
      }
    }
    String value = String.format("%.2f", totalCostBasis);
    return Double.parseDouble(value);
  }

  @Override
  public double getTotalValueOfPortfolioOnCertainDate(String date, String portfolioName) {
    if (portfolioName == null || portfolioName.equals("")) {
      throw new IllegalArgumentException("Invalid portfolioName provided");
    }
    validateDate(date);
    double totalValue = 0.0;
    Map<String, List<Stock>> m = null;
    if (portfolioName.equals("currentInstance") || this.portfolioName.equals(portfolioName)) {
      if (!stockMap.isEmpty()) {
        m = stockMap.get(portfolioName);
      }
    } else {
      validateIfPortfolioDoesntExists(portfolioName);
      this.portfolioName = portfolioName;
      m = parser.readFromFile(portfolioName);
      this.stockMap.put(portfolioName, m);
    }
    if (m != null) {
      for (Map.Entry<String, List<Stock>> entry : m.entrySet()) {
        String stkName = entry.getKey();
        try {
          double netQty = getQuantityOnThisDateForGivenCompanyName(date, stkName);
          totalValue = totalValue + apiCustomInterface.getStockPriceAsOfCertainDate(stkName, netQty, date);
        } catch (ParseException e) {
        }
      }
    }
    String value = String.format("%.2f", totalValue);
    return Double.parseDouble(value);

  }

  @Override
  public void loadPortfolioUsingFilePath(String filePath) {
    try {
      validateFilePath(filePath);
      Map<String, List<Stock>> records = parser.readFromPathProvidedByUser(filePath);
      this.portfolioName = "currentInstance";
      this.stockMap.put(portfolioName, records);
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }

  }


  @Override
  protected List<String> getResultsToDisplayComposition(Stock stock, String date) {
    try {
      if (checkIfDateIsLessThanGivenDate(date, stock)) {
        List<String> temp = new ArrayList<>();
        temp.add(stock.getCompanyTickerSymbol());
        temp.add(String.valueOf(stock.getQty()));
        temp.add(stock.getDateOfAction());
        temp.add(stock.getAction());
        return temp;
      }
    } catch (ParseException e) {

    }
    return null;
  }


  @Override
  public void createPortfolioIfCreatedManually(String portfolioName) {
    if (portfolioName == null || portfolioName.equals("")) {
      throw new IllegalArgumentException("Invalid portfolioName provided");
    }
    this.portfolioName = portfolioName;
    if (!stockMap.isEmpty()) {
      Map<String, List<Stock>> mm = stockMap.get(portfolioName);
      parser.writeIntoFile(portfolioName, mm, "flexible");
    }
  }

  private void printMap(Map<String, List<Stock>> stockMap) {
    for (Map.Entry<String, List<Stock>> entry : stockMap.entrySet()) {
      System.out.println(entry.getKey() + " values is ");
      List<Stock> list = entry.getValue();
      for (int i = 0; i < list.size(); i++) {
        System.out.println(list.get(i).getQty());
        System.out.println(list.get(i).getAction());
        System.out.println(list.get(i).getDateOfAction());
      }

    }
  }

  @Override
  public Map<String, Double> getPortfolioPerformanceOvertime(String startTime, String endTime, String portfolioName) {
    try {

      Map<String, List<Stock>> detailsMap = parser.readFromFile(portfolioName);
      //create a new map here and pass it to PortfolioPerformance
      PortfolioPerformance portfolioPerformance = new PortfolioPerformance(detailsMap);
      return portfolioPerformance.displayCopy(startTime, endTime, portfolioName);
    } catch (Exception e) {

    }
    return null;
  }

  private void validateInputsForBuy(String companyName, String quantity, String date, String commission) {
    validateIfCompanyExists(companyName);
    validateQuantity(quantity);
    validateDate(date);
    validateDateToCheckIfBeforeIPO(date, companyName);
    validateCommission(commission);
  }

  private void validateInputsForSell(String quantity, String date, String commission) {
    //validation on companyName and date chronological order is already handled in sellStocks method.
    validateQuantity(quantity);
    validateDate(date);
    validateCommission(commission);
  }

  private void validateInputsForUpdate(String portfolioName, String companyName, String quantity, String date, String action, String commission) {
    if (!portfolioName.equals("currentInstance")) {
      validateIfPortfolioDoesntExists(portfolioName);
    }
    if (action.equals("buy")) {
      validateInputsForBuy(companyName, quantity, date, commission);
    } else if (action.equals("sell")) {
      validateInputsForSell(quantity, date, commission);
    }
  }

  private void validateCommission(String com) {
    if (com == null) {
      throw new IllegalArgumentException("Invalid Commission provided");
    }
    try {
      double q = Double.parseDouble(com);
      if (q <= 0 || q > Integer.MAX_VALUE) {
        throw new IllegalArgumentException("Invalid Commission provided");
      }
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Commission should be always a positive whole number.");
    }
  }

  private void validateDateToCheckIfBeforeIPO(String date, String companyName) {
    for (CompanyTickerSymbol companyTickerSymbol : CompanyTickerSymbol.values()) {
      if (companyTickerSymbol.name().equalsIgnoreCase(companyName)) {
        try {
          Date givenDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                  .parse(date);
          Date ipoDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                  .parse(companyTickerSymbol.getEndDate());

          if (givenDate.compareTo(ipoDate) < 0) {
            throw new IllegalArgumentException("Given date is before IPO Date.Please provide a valid date.");
          }
          break;
        } catch (ParseException e) {
          throw new IllegalArgumentException(e.getMessage());
        }
      }
    }
  }

}
