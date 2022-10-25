package stocks.model;

import java.util.Date;

public interface Portfolio {

  Portfolio buyStocks(int quantity,String CompanyName, String portfolioName);

  Portfolio sellStocks(int quantity,String CompanyName, String portfolioName);

  Portfolio createPortfolio(String portfolioName);

  double getTotalValueOfPortfolioOnCertainDate(Date date, String portfolioName);

}
