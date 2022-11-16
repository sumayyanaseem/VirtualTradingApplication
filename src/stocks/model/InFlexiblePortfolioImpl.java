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

import stocks.customapi.APICustomInterface;

/**
 * This class implements PortfolioModel.
 */
public class InFlexiblePortfolioImpl extends AbstractPortfolio {

  private static final String action = "add";

  public InFlexiblePortfolioImpl(APICustomInterface apiCustomInterface) {
    super(apiCustomInterface);
  }

  @Override
  public void buyStocks(String cName, String quantity, String date,
                        String com, String portfolioName)
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
      Stock s = new Stock(companyName, qty, totalVal, action, priceBought, todayDateStr, 0.0);
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
          Stock s2 = new Stock(companyName, totQty, val, action, priceBought, todayDateStr, 0.0);
          List<Stock> ls = new ArrayList<>();
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
    this.portfolioName = portfolioName;
    if (!stockMap.isEmpty()) {
      parser.writeIntoFile(portfolioName, stockMap.get(portfolioName), "inflexible");
    }
  }


  @Override
  public void sellStocks(String companyName, String quantity,
                         String date, String com, String portfolioName) {
    throw new UnsupportedOperationException("This "
            + "operation is not supported in Inflexible portfolio");
  }

  @Override
  public void updatePortfolio(String companyName,
                              String quantity, String date,
                              String portfolioName,
                              String action, String com) {
    throw new UnsupportedOperationException("This operation "
            + "is not supported in Inflexible portfolio");
  }

  @Override
  public void updatePortfolioUsingFilePath(String path, String companyName,
                                           String quantity, String date,
                                           String portfolioName,
                                           String action, String com)
          throws IllegalArgumentException {
    throw new UnsupportedOperationException("This operation is "
            + "not supported in Inflexible portfolio");
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
        Date dateBoughtObj;
        Date givenDateObj;

        try {
          dateBoughtObj = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(dateBought);
          givenDateObj = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
        } catch (ParseException e) {
          throw new IllegalArgumentException("unable to parse date");
        }
        if (dateBoughtObj.compareTo(givenDateObj) <= 0) {

          totalCostBasis = totalCostBasis
                  + apiCustomInterface.getStockPriceAsOfCertainDate(
                  listOfStocks.get(0).getCompanyTickerSymbol(),
                  listOfStocks.get(0).getQty(),
                  listOfStocks.get(0).getDateOfAction());
        }
      }


    }

    return totalCostBasis;
  }

  @Override
  public Map<String, Double> getPortfolioPerformanceOvertime(
          String startTime, String endTime, String portfolioName) {
    throw new UnsupportedOperationException("This "
            + "operation is not supported in Inflexible portfolio");
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
      Map<String, List<Stock>> mapOfStkInfoPersisted = parser.readFromFile(portfolioName);
      for (Map.Entry<String, List<Stock>> entry : mapOfStkInfoPersisted.entrySet()) {
        String companyTickerSymbol = entry.getKey();
        double qty = entry.getValue().get(0).getQty();
        try {
          totValue = totValue + apiCustomInterface.getStockPriceAsOfCertainDate(
                  companyTickerSymbol, qty, date);
        } catch (IllegalArgumentException e) {
          throw new IllegalArgumentException(e.getMessage());
        }

      }


    }
    String value = String.format("%.2f", totValue);
    return Double.parseDouble(value);
  }

  @Override
  public void loadPortfolioUsingFilePath(String filePath) {
    Map<String, List<Stock>> listOfStocks;
    try {
      validateFilePath(filePath);
      listOfStocks = parser.readFromPathProvidedByUser(filePath);
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    this.portfolioName = "currentInstance";
    this.stockMap.put(portfolioName, listOfStocks);
  }

  @Override
  protected List<String> getResultsToDisplayComposition(Stock stock, String date) {
    List<String> temp = new ArrayList<>();
    temp.add(stock.getCompanyTickerSymbol());
    temp.add(String.valueOf(stock.getQty()));
    temp.add(stock.getDateOfAction());
    temp.add(stock.getAction());
    return temp;
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


}
