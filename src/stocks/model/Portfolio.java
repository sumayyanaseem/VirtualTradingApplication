package stocks.model;

import java.util.List;

public class Portfolio {
  String portfolioName;
  List<Stock> listOfStocks;

  public Portfolio(String portfolioName,List listOfStocks)
  {
    this.portfolioName=portfolioName;
    this.listOfStocks=listOfStocks;
  }

}
