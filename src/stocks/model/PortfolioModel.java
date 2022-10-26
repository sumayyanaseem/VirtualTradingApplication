package stocks.model;

import java.util.Date;

public interface PortfolioModel {

  PortfolioModel buyStocks(String quantity,String CompanyName, String portfolioName);

  PortfolioModel sellStocks(String quantity,String CompanyName, String portfolioName);

  PortfolioModel createPortfolio(String portfolioName);

  PortfolioModel createPortfolioUsingFilePath(String filePath);

  double getTotalValueOfPortfolioOnCertainDate(Date date, String portfolioName);

  boolean isPortfolioCreated();

}
