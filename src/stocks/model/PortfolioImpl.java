package stocks.model;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PortfolioImpl implements Portfolio{

  public class companyStockStructure {

    private String companyTickerSymbol;

    List<stockInfo> list = new LinkedList<>();

    companyStockStructure(String companyTickerSymbol,List<stockInfo> stockInfoList){
      this.companyTickerSymbol = companyTickerSymbol;
      this.list.addAll(stockInfoList);
    }

    class stockInfo(){
      Double purchasedPrice;

      long quantity;

      Timestamp dateSold;

      Timestamp datePurchased;

      Long totalValue;
    }


  }


  Map<String,Map<String,List<stockInfo>>> portfolioMap = new HashMap<>();

  String initialportfolio;

  public PortfolioImpl(String portfolioName,String companyName){
   if(!portfolioMap.isEmpty()){
     Map<String,List<stockInfo>> companyStockList= portfolioMap.get(portfolioName);
     if(!companyStockList.isEmpty()){
       List<stockInfo> list =companyStockList.get(companyName);
       if(!list.isEmpty()){
         Date currentDate = getCurrentDate();
         stockInfo stock=  list.stream().filter(str -> (str.getDatePurchased()<currentDate).findLast();
         stockInfo newStockInfo =new stockInfo();
         newStockInfo.setQuantity(stock.getQuantity()+quantity);
//add all other details.
         list.add(newStockInfo);

       } else{

       }
     } else {
       //create a new list and add
     }

   } else {
     //create a new portfolio and add caompany details
   }


    //Read from JSON file to map
  }

  @Override
  public void buyStocks(int quantity, String CompanyName, String portfolioName) {

    if(!portfolioMap.isEmpty()){
      List<companyStockStructure> listOfStocks= portfolioMap.get(portfolioName);
      if(!listOfStocks.isEmpty()){
        companyStockStructure c= listOfStocks.stream().filter(str -> str.getCompanyTickerSymbol().equals(CompanyName)).findFirst();
        c.setQuantity(c.getQuantity()+quantity);
        c.setPurchasedPrice();//Avg of new price and older price to be taken.
        c.setTotalValue();

      }
    } else {
      companyStockStructure c = new companyStockStructure();
      c.setCompanyTickerSymbol(CompanyName);
      c.setQuantity(quantity);
      c.setPurchasedPrice();//the current trading price of this company.
      c.setTotalValue();
      List<companyStockStructure> listOfStocks= new LinkedList<>();
      listOfStocks.add(c);
      portfolioMap.put(portfolioName,listOfStocks);
    }

  }

  @Override
  public void sellStocks(int quantity, String CompanyName, String portfolioName) {

    if(!portfolioMap.isEmpty()){
      List<companyStockStructure> listOfStocks= portfolioMap.get(portfolioName);
      if(!listOfStocks.isEmpty()){
        companyStockStructure c= listOfStocks.stream().filter(str -> str.getCompanyTickerSymbol().equals(CompanyName)).findFirst();
        c.setQuantity(c.getQuantity()-quantity);
        c.setPurchasedPrice();//Avg of new price and older price to be taken.
        c.setTotalValue();
        c.setDateSold();
      }
    }
  }

  @Override
  public void createPortfolio(String portfolioName) {


  }

  @Override
  public void getTotalValueOfPortfolioOnCertainDate(Date date, String portfolioName) {

  }


}
