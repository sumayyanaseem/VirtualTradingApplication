package stocks.model;

import java.util.Date;

public interface Portfolio {

  void buyStocks(int quantity,String CompanyName, String portfolioName);

  void sellStocks(int quantity,String CompanyName, String portfolioName);

  void createPortfolio(String portfolioName);

  void getTotalValueOfPortfolioOnCertainDate(Date date, String portfolioName);

}
