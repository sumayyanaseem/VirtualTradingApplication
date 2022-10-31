package stocks.model;
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
    double priceBought = apiCustomClass.fetchStockPriceAsOfToday(CompanyName);

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
    String pattern = "yyyy-MM-dd";
    String todayDate =new SimpleDateFormat(pattern).format(new Date(System.currentTimeMillis()));
    String datePrev =new SimpleDateFormat(pattern).format(new Date(System.currentTimeMillis()-24*60*60*1000));
    Map<String, Stock> m= portfolioMap.get(portfolioName);
    //fetch the details from csv file map
    double totVal=0.0;
    for (Map.Entry<String,Stock> entry : m.entrySet()) {
      String stkName = entry.getKey();
      if(todayDate.equals(date))
      {
        totVal=totVal+entry.getValue().getQty()*apiCustomClass.fetchStockPriceAsOfCertainDate(entry.getKey(), datePrev);
      }
      else
      totVal=totVal+entry.getValue().getQty()*apiCustomClass.fetchStockPriceAsOfCertainDate(entry.getKey(), date);
    }
    return totVal;
  }

  @Override
  public void createPortfolioUsingFilePath(String filePath) throws IOException {
    //validate filepath is correct or not
    List<List<String>> listOfStocks =customCSVParser.readFromCSV(filePath);
    //helperMethodToWriteTOCSV(filePath);
    Map<String,List<String>> mapOfStocks = new HashMap<>();
    String pattern = "yyyy-MM-dd";
    String todayDate =new SimpleDateFormat(pattern).format(new Date(System.currentTimeMillis()));
    List<String[]> resultList=new ArrayList<>();
    String[] headers = new String[]{"CompanyName","Quantity","PriceBought","DatePurchase","TotalValueWhenPurchased"};
    resultList.add(headers);
    for(int i=1;i<listOfStocks.size();i++)
    {
      String[] temp = new String[5];
      String sName= listOfStocks.get(i).get(0);
      double sPrice = apiCustomClass.fetchStockPriceAsOfToday(sName);
      if(!mapOfStocks.containsKey(sName)) {
        mapOfStocks.put(sName, listOfStocks.get(i));
        temp = listOfStocks.get(i).toArray(new String[5]);
        temp[2]= String.valueOf(sPrice);
        temp[3]= todayDate;
        temp[4]= String.valueOf(Long.valueOf(listOfStocks.get(i).get(1))*sPrice);
      }
      else {
        List<String> list1=mapOfStocks.get(sName);
        List<String> list2=listOfStocks.get(i);
        mapOfStocks.remove(sName);
        listOfStocks.remove(i);
        long totQty = Long.valueOf(list1.get(1))+Long.valueOf(list2.get(1));
        String totQtyStr=String.valueOf(totQty);
        //String totVal=String.valueOf(Integer.valueOf(list1.get(4))+Integer.valueOf(list2.get(4)));

        list1.set(1,totQtyStr);
        //list1.set(4,totVal);
        mapOfStocks.put(sName,list1);
        temp = list1.toArray(new String[5]);

        temp[2]= String.valueOf(sPrice);
        temp[3]= todayDate;
        temp[4]= String.valueOf(totQty*sPrice);
      }
      resultList.add(temp);

    }
    customCSVParser.writeTOCSV(resultList,filePath);

  }

  @Override
  public boolean isPortfolioCreated() {
    return false;
  }

  @Override
  public List<List<String>> readFromCSVFile(String portfolioName) {
    List<List<String>> records = customCSVParser.readFromCSVAndModifyData(portfolioName);
    List<String> list = records.get(0);
    String name="TotalValueOwnedAsOfToday";
    List<String> list1 = new ArrayList<>();
    list1.addAll(list);
    list1.add(name);
    List<List<String>> results = new ArrayList<>();
    results.add(list1);
    for(int i=1;i<records.size();i++){
      list1 = new ArrayList<>();
     String cName =records.get(i).get(0);
     String quantity = records.get(i).get(1);
     Double currentPrice =apiCustomClass.fetchStockPriceAsOfToday(cName);
     Double currentTotalPrice = Long.parseLong(quantity)*currentPrice;
     list1.addAll(records.get(i));
     list1.add(String.valueOf(currentTotalPrice));
      results.add(list1);
    }
    return results;
  }


}
