package stocks.model;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PortfolioImpl implements Portfolio{

  List<Map<String,String>> allStocks;
  String portfolioName;

  public PortfolioImpl()
  {

  }
  public PortfolioImpl(List<Map<String,String>> allStocks, String portfolioName){
  this.allStocks=allStocks;
  this.portfolioName=portfolioName;
  }

  @Override
  public Portfolio buyStocks(String companyId, String qty) {
   /* int i;
    boolean isPresent = false;
    for (i = 0; i < allStocks.size(); i++) {

      // Print all elements of List
     if(allStocks.get(0).containsKey(companyId))
     {
       isPresent=true;
       break;
     }

     if(!isPresent)
     {
       Map<String,String> m=new HashMap<>();
       m.put("company",companyId);
       m.put("buyQty",qty);
      //get other details from url and add to map
       allStocks.add(m);
     }
     else
     {
       Map<String,String> m=new HashMap<>();
       m=allStocks.get(i);
       int boughtAlready = Integer.valueOf(m.get("buyQty"));
       int finalBuyQty=boughtAlready+Integer.valueOf(qty);
       m.put("buyQty",String.valueOf(finalBuyQty));
       //add other details from url
     }

    }
    return this;*/
  }

  @Override
  public Portfolio sellStocks(int quantity, String CompanyName, String portfolioName) {
    return null;
  }

  @Override
  public Portfolio createPortfolio(String portfolioName) {
    return new PortfolioImpl(allStocks,portfolioName);
  }

  @Override
  public double getTotalValueOfPortfolioOnCertainDate(Date date, String portfolioName) {
    //implementation for value as of now.
    int i;
    double total=0;
    Map<String,String> m=new HashMap<>();
    for (i = 0; i < allStocks.size(); i++) {
      m=allStocks.get(i);
      //fetch current price of stock from url
      total=total+m.get("buyQty")*currentPrice;
    }
    return total;
  }

 /* public class companyStockStructure {

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
  public Portfolio buyStocks(int quantity, String CompanyName, String portfolioName) {

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
  public Portfolio sellStocks(int quantity, String CompanyName, String portfolioName) {

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
  public Portfolio createPortfolio(String portfolioName) {


  }

  @Override
  public double getTotalValueOfPortfolioOnCertainDate(Date date, String portfolioName) {

  }*/





}
