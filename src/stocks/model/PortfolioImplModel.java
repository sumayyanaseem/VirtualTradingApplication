package stocks.model;
import java.util.Date;

public class PortfolioImplModel implements PortfolioModel{

 private String portfolioName;
 private CompanyStocksList companyStocksList;


 public String getPortfolioName() {
  return this.portfolioName;
 }
 public void setPortfolioName(String name) {
  this.portfolioName = name;
 }
 public CompanyStocksList getCompanyStocksList() {
  return this.companyStocksList;
 }



  public PortfolioImplModel(){
   //portfolioMap = new HashMap<>();
  }

  //TODO:Have one more constructor to read fileInput and remove the below logic to a helper method.
 public PortfolioImplModel(String portfolioName, String companyName){
  /* if(!portfolioMap.isEmpty()){
     Map<String,List<companyStockStructure.stockInfo>> companyStockList= portfolioMap.get(portfolioName);
     if(!companyStockList.isEmpty()){
       List<companyStockStructure.stockInfo> list =companyStockList.get(companyName);
       if(!list.isEmpty()){
         Date currentDate = getCurrentDate();
         companyStockStructure.stockInfo stock=  list.stream().filter(str -> (str.getDatePurchased()<currentDate).findLast();
         companyStockStructure.stockInfo newStockInfo =new companyStockStructure.stockInfo();
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
   }*/


    //Read from JSON file to map
  }

  @Override
  public PortfolioModel buyStocks(String quantity, String CompanyName, String portfolioName) {

    /*if(!portfolioMap.isEmpty()){
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
    }*/

     return null;

  }

  @Override
  public PortfolioModel sellStocks(String quantity, String CompanyName, String portfolioName) {

   /* if(!portfolioMap.isEmpty()){
      List<companyStockStructure> listOfStocks= portfolioMap.get(portfolioName);
      if(!listOfStocks.isEmpty()){
        companyStockStructure c= listOfStocks.stream().filter(str -> str.getCompanyTickerSymbol().equals(CompanyName)).findFirst();
        c.setQuantity(c.getQuantity()-quantity);
        c.setPurchasedPrice();//Avg of new price and older price to be taken.
        c.setTotalValue();
        c.setDateSold();
      }
    }*/

    return null;
  }

  @Override
  public PortfolioModel createPortfolio(String portfolioName) {
    setPortfolioName(portfolioName);
    companyStocksList = new CompanyStocksList();
  }

  @Override
  public PortfolioModel createPortfolioUsingFilePath(String filePath) {
    return null;
  }

  @Override
  public double getTotalValueOfPortfolioOnCertainDate(Date date, String portfolioName) {
  return 0.0;
  }

 @Override
 public boolean isPortfolioCreated() {
  return false;
 }


}
