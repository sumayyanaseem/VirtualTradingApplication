package stocks.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;



/**
 * This class implements PortfolioModel.
 */
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
    customCSVParser.writeTOCSV(temp, portfolioName);
  }

  /* @Override
  public void sellStocks(String quantity, String CompanyName, String portfolioName) {

  }*/

  @Override
  public double getTotalValueOfPortfolioOnCertainDate(String date, String portfolioName) {
    double totValue=0.0;
    List<List<String>> listOfStkInfoPersisted=customCSVParser.readFromCSV(portfolioName);
    for(int j=1;j<listOfStkInfoPersisted.size();j++) {
      String companyTickerSymbol = listOfStkInfoPersisted.get(j).get(0);
      long qty = Long.valueOf(listOfStkInfoPersisted.get(j).get(1));
      String latestAvailableStkPrice;

      String name = companyTickerSymbol.toUpperCase();
      String path = "csvFiles/" + "daily_" + name + ".csv";
      try (BufferedReader br = new BufferedReader(new FileReader(path))) {
        String line= br.readLine();
        List<List<String>> records = new ArrayList<>();
        while ((line = br.readLine()) != null) {
          String[] values = line.split(",");
          records.add(Arrays.asList(values));
        }
        try {
          Date givenDateObj = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                  .parse(date);
          for (int i = 0; i < records.size(); i++) {
            List<String> infoByDate = new ArrayList<>(records.get(i));
            String availableDate = infoByDate.get(0);
            latestAvailableStkPrice = infoByDate.get(4);
            Date availableDateObj = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                    .parse(availableDate);
            if (availableDateObj.compareTo(givenDateObj) <= 0) {
              totValue = totValue + Double.valueOf(latestAvailableStkPrice)*qty;
              break;
            }

          }
        }
        catch(ParseException p){
          System.out.println("parse exception");
        }
      } catch (FileNotFoundException ex) {
        throw new RuntimeException(ex);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    return totValue;
  }

  @Override
  public void createPortfolioUsingFilePath(String filePath)  {
    //validate filepath is correct or not
    List<List<String>> listOfStocks = customCSVParser.readFromCSV(filePath);
    //helperMethodToWriteTOCSV(filePath);
    Map<String, List<String>> mapOfStocks = new HashMap<>();
    String pattern = "yyyy-MM-dd";
    String todayDate = new SimpleDateFormat(pattern).format(new Date(System.currentTimeMillis()));
    List<String[]> resultList = new ArrayList<>();
    String[] headers = new String[]{"CompanyName", "Quantity", "PriceBought", "DatePurchase", "TotalValueWhenPurchased"};
    resultList.add(headers);
    for (int i = 1; i < listOfStocks.size(); i++) {
      //String[] temp = new String[5];
      String sName = listOfStocks.get(i).get(0);
      double sPrice = apiCustomClass.fetchLatestStockPriceOfThisCompany(sName);


      if(!mapOfStocks.containsKey(sName)) {

        List<String> stkInfoList=new ArrayList<>(listOfStocks.get(i));
        stkInfoList.add(String.valueOf(sPrice));
        stkInfoList.add(todayDate);
        stkInfoList.add(String.format("%.2f",Long.valueOf(listOfStocks.get(i).get(1))*sPrice));

        mapOfStocks.put(sName, stkInfoList);

      }
      else {
        List<String> list1=mapOfStocks.get(sName);
        List<String> list2=listOfStocks.get(i);
        mapOfStocks.remove(sName);
        //listOfStocks.remove(i);
        long totQty = Long.valueOf(list1.get(1))+Long.valueOf(list2.get(1));
        String totQtyStr=String.valueOf(totQty);
        listOfStocks.get(i).set(1,totQtyStr);
        //String totVal=String.valueOf(Integer.valueOf(list1.get(4))+Integer.valueOf(list2.get(4)));
        List<String> stkInfoList=new ArrayList<>(listOfStocks.get(i));
        stkInfoList.add(String.valueOf(sPrice));
        stkInfoList.add(todayDate);
        stkInfoList.add(String.format("%.2f",Long.valueOf(listOfStocks.get(i).get(1))*sPrice));

        mapOfStocks.put(sName,stkInfoList);
      }

    }

    for (Map.Entry<String, List<String>> e : mapOfStocks.entrySet()) {
      String[] temp = new String[5];
      temp = e.getValue().toArray(new String[5]);
      resultList.add(temp);
    }
    customCSVParser.writeTOCSV(resultList,filePath+" output");
  }

  @Override
  public List<List<String>> viewCompositionOfCurrentPortfolio(String portfolioName) {

    List<List<String>> records = customCSVParser.readFromCSV(portfolioName);
    List<String> list = records.get(0);
    String name = "TotalValueOwnedAsOfToday";
    List<String> list1 = new ArrayList<>();
    list1.addAll(list);
    list1.add(name);
    List<List<String>> results = new ArrayList<>();
    results.add(list1);
    for (int i = 1; i < records.size(); i++) {
      list1 = new ArrayList<>();
      String cName = records.get(i).get(0);
      String quantity = records.get(i).get(1);
      double currentPrice = apiCustomClass.fetchLatestStockPriceOfThisCompany(cName);
      if (currentPrice != -1) {
        double currentTotalPrice = Double.parseDouble(quantity) * currentPrice;
        list1.addAll(records.get(i));
        list1.add(String.format("%.2f",currentTotalPrice));
        results.add(list1);
      }
    }
    return results;
  }


}
