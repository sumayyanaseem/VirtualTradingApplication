package stocks.model;

import java.util.Date;

public interface PortfolioModel {

  PortfolioModel buyStocks(int quantity,String CompanyName, String portfolioName);

  PortfolioModel sellStocks(int quantity,String CompanyName, String portfolioName);

  PortfolioModel createPortfolio(String portfolioName);

  double getTotalValueOfPortfolioOnCertainDate(Date date, String portfolioName);

}
