package stocks.model;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * This class implements PortfolioModel.
 */
public class PortfolioImplModel implements PortfolioModel {

  private Map<String, Map<String, Stock>> portfolioMap;

  private APICustomClass apiCustomClass;

  private CustomCSVParser customCSVParser;
  private String pName;



  public PortfolioImplModel() {
    portfolioMap = new HashMap<>();
    apiCustomClass = new APICustomClass();
    customCSVParser = new CustomCSVParser();
  }

  @Override
  public void buyStocks(String quantity, String cName, String portfolioName)  {

    String pattern = "yyyy-MM-dd";
    double priceBought =apiCustomClass.fetchLatestStockPriceOfThisCompany(cName);
    if (priceBought != -1) {
      String todayDateStr = new SimpleDateFormat(pattern).format(new Date(System.currentTimeMillis()));
      Double qty = Double.valueOf(quantity);
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
    List<String[]> temp = new ArrayList<>();
    temp.add(new String[]{"CompanyName", "Quantity", "PriceBought", "DatePurchase", "TotalValueOwned"});
    Map<String, Stock> mm = portfolioMap.get(portfolioName);
    for (Map.Entry<String, Stock> entry : mm.entrySet()) {
      String[] s1 = new String[5];
      s1[0] = entry.getValue().getCompanyTickerSymbol();
      s1[1] = String.format("%.2f",entry.getValue().getQty());
      s1[2] = String.valueOf(entry.getValue().getPriceBought());
      s1[3] = String.valueOf(entry.getValue().getDateBought());
      s1[4] = String.format("%.2f", entry.getValue().getTotalValue());
      temp.add(s1);
    }
    this.pName =portfolioName;
    customCSVParser.writeTOCSV(temp, portfolioName);
  }

  @Override
  public PortfolioModel getInstance() {
    return new PortfolioImplModel();
  }

  /* @Override
  public void sellStocks(String quantity, String CompanyName, String portfolioName) {

  }*/

  @Override
  public double getTotalValueOfPortfolioOnCertainDate(String date, String portfolioName) {
    double totValue=0.0;
    if(this.pName.equals("currentInstance")){
      Map<String,Stock> map = portfolioMap.get(this.pName);
      for(Map.Entry<String,Stock> entry:map.entrySet()){
        Stock s =  entry.getValue();
        totValue=totValue+s.getTotalValue();
      }
      return totValue;
    } else {
      List<List<String>> listOfStkInfoPersisted=customCSVParser.readFromCSV(portfolioName);
      for(int j=1;j<listOfStkInfoPersisted.size();j++) {
        String companyTickerSymbol = listOfStkInfoPersisted.get(j).get(0);
        double qty = Double.valueOf(listOfStkInfoPersisted.get(j).get(1));

        totValue=totValue+apiCustomClass.getStockPriceAsOfCertainDate(companyTickerSymbol,qty,date);

      }
      return totValue;
    }
  }

  @Override
  public void createPortfolioUsingFilePath(String filePath)  {
    List<List<String>> listOfStocks;
    try {
      listOfStocks = customCSVParser.readFromPathProvidedByUser(filePath);
    }
    catch (Exception e) {
      System.out.println(e.getStackTrace());
      throw new RuntimeException(e);
    }
    Map<String, Stock> mapOfStocks = new HashMap<>();
    String pattern = "yyyy-MM-dd";
    String todayDate = new SimpleDateFormat(pattern).format(new Date(System.currentTimeMillis()));
    for (int i = 1; i < listOfStocks.size(); i++) {
      String sName = listOfStocks.get(i).get(0);
      double sPrice = apiCustomClass.fetchLatestStockPriceOfThisCompany(sName);
      if(!mapOfStocks.containsKey(sName)) {
        Stock st=new Stock(listOfStocks.get(i).get(0), Double.valueOf(listOfStocks.get(i).get(1)),todayDate,null,sPrice,Double.valueOf(listOfStocks.get(i).get(1))*sPrice);
        mapOfStocks.put(sName, st);
      }
      else {
        Stock list1=  mapOfStocks.get(sName);
        List<String> list2=listOfStocks.get(i);
        mapOfStocks.remove(sName);
        double totQty = Double.valueOf(list1.getQty())+Double.valueOf(list2.get(1));
        Stock st=new Stock(listOfStocks.get(i).get(0),totQty ,todayDate,null,sPrice,Double.valueOf(listOfStocks.get(i).get(1))*sPrice);
        mapOfStocks.put(sName,st);
      }

    }
    this.pName="currentInstance";
    portfolioMap.put(pName,mapOfStocks);
  }
  @Override
  public List<List<String>> viewCompositionOfCurrentPortfolio(String portfolioName) {
    List<List<String>> results = new ArrayList<>();
    if(this.pName.equals("currentInstance")){
      Map<String,Stock> map = portfolioMap.get(this.pName);
      String[] headers = new String[]{"CompanyName", "Quantity", "PriceBought", "DatePurchase", "TotalValueWhenPurchased"};
      results.add(List.of(headers));
      for(Map.Entry<String,Stock> entry:map.entrySet()){
        List<String> temp = new ArrayList<>();
        Stock s =  entry.getValue();
        temp.add(s.getCompanyTickerSymbol());
        temp.add(String.valueOf(s.getQty()));
        temp.add(String.valueOf(s.getPriceBought()));
        temp.add(s.getDateBought());
        temp.add(String.valueOf(s.getTotalValue()));
        results.add(temp);
      }
      return results;
    } else {
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
      return results;
    }

  }


}
