package stocks.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PortfolioImplModel implements PortfolioModel{

 private Map<String, Map<String,List<companyStockStructure>>> allPortfolioMap;


  //TODO:check why is this needed?
  //String initialPortfolio;


  //TODO:Have one more constructor to read fileInput and remove the below logic to a helper method.

  public PortfolioImplModel(String portfolioName){
    allPortfolioMap = new HashMap<>();
    Map<String,List<companyStockStructure>> portfolioMapForStocks = new HashMap<>();
    allPortfolioMap.put(portfolioName,portfolioMapForStocks);
  }

  @Override
  public PortfolioModel buyStocks(int quantity, String CompanyName, PortfolioModel portfoliosObj) {


    for (Map.Entry<String, Map<String,List<companyStockStructure>>> entry : (PortfolioImplModel)portfoliosObj.allPortfolioMap.entrySet()) {
      System.out.println(entry.getKey() + ":" + entry.getValue());
    }
  }


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
  public PortfolioModel sellStocks(int quantity, String CompanyName, String portfolioName) {

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



  }

  @Override
  public double getTotalValueOfPortfolioOnCertainDate(Date date, String portfolioName) {

  }


}
