package stocks.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FlexiblePortfolioImpl implements FlexiblePortfolio {

  private String action;

  private APICustomClass apiCustomClass;

  private final String portfolioName;


  private final Map<String, Map<String, List<Stock>>> stockMap;


  public FlexiblePortfolioImpl(String portfolioName) {
    this.portfolioName = portfolioName;
    stockMap = new HashMap<>();
  }

  class StockComparator implements Comparator<Stock> {
    public int compare(Stock o1, Stock o2) {
      Date date2 = null;
      Date date1 = null;
      try {
        date1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse(o1.getDateOfAction());


        date2 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse(o2.getDateOfAction());

      } catch (ParseException e) {
        throw new RuntimeException(e);
      }
      return date1.compareTo(date2);
    }
  }


  public void createPortfolio(String portfolioName) {
    //create a new file with this portfolioName and headings in the file.
  }


  @Override
  public FlexiblePortfolio buyStocks(String companyName, int quantity, String date, String portfolioName) {
    action = "buy";
    validateInputs(action);
    //TODO:check if this API call is needed here
    double priceBought = apiCustomClass.getStockPriceAsOfCertainDate(companyName, quantity, date);
    if (priceBought != -1) {
      String cName = companyName.toUpperCase();
      Stock s = new Stock(cName, quantity, 0.0, action, priceBought, date);
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
              double totQty = stockList.get(i).getQty() + quantity;
              s = new Stock(companyName, totQty, 0.0, action, priceBought, date);
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
    return null;
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


  private void validateIfCompanyAlreadyExistsBeforeSelling(String companyName, String portfolioName) {
    Map<String, List<Stock>> m = stockMap.get(portfolioName);
    if (m.containsKey(companyName)) {
      return;
    }

  }

  @Override
  public FlexiblePortfolio sellStocks(String companyName, int quantity, String date, String portfolioName) {
    action = "sell";
    validateInputs(action);
    Map<String, List<Stock>> m1 = stockMap.get(portfolioName);
    if(!m1.containsKey(companyName)){
      //throw error
    } else if (stockMap.isEmpty()) {
        //then throw error that you cant sell without buying.
    } else {
        //if sellMap is not empty, then validate if entry of this company exists or not.
      int netQuantity = getQuantityOnThisDateForGivenCompanyName(date, companyName);
          if (netQuantity < quantity) {
            //then throw error , that its not valid.
          } else {
            String lastSellDate = getLastSellDate(companyName);
            if(lastSellDate>date) {
              //throw error
            } else {
              Stock s = new Stock(companyName, quantity, 0.0,action, 0.0, date);
              List<Stock> stockList = m1.get(companyName);
              stockList.add(s);
              m1.put(companyName, stockList);
              stockMap.put(portfolioName, m1);
            }
        }
    }
    return null;
  }


  private String getLastSellDate(String companyName){
    Map<String, List<Stock>> m = stockMap.get(portfolioName);
    List<Stock> list = m.get(companyName);
    String date = currentDate();
    String date2 = null;
    for(int i=0;i<list.size();i++){
      if (list.get(i).getAction().equals("sell")) {
        date2=list.get(i).getDateOfAction();
        if(date2<=date){
          date=date2;
        }
      }
    }
    return date;
  }
  private int getQuantityOnThisDateForGivenCompanyName(String date, String companyName) {
    Map<String, List<Stock>> m = stockMap.get(portfolioName);
    List<Stock> list = m.get(companyName);
    int quantity = 0;
    for (int i = 0; i < list.size(); i++) {
      String datePresent = list.get(i).getDateOfAction();
      if (datePresent <= date) {
        if (list.get(i).getAction().equals("buy")) {
          quantity += list.get(i).getQty();
        } else if(list.get(i).getAction().equals("sell")){
          quantity -= list.get(i).getQty();
        }
      }
    }
    return quantity;
  }

  @Override
  public void getTotalMoneyInvestedOnCertainDate(String date, String portfolioName) {

  }

  @Override
  public void getTotalValueOfPortfolioOnCertainDate(String date, String portfolioName) {

  }

  @Override
  public void getPortfolioPerformanceOvertime(String startTime, String endTime, String portfolioName) {


  }

  private void validateInputs(String action) {
    validatePortfolioName();
    validateCompanyName(action);
    validateQuantity(action);

    validateDate(action);
  }

  private void validateDate(String action) {
    String pattern = "yyyy-MM-dd";
    String todayDateStr = new SimpleDateFormat(pattern).format(
            new Date(System.currentTimeMillis()));
    if (action.equals("buy")) {

    } else if (action.equals("sell")) {

    }
  }
}
