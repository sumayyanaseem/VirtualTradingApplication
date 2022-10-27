package stocks.model;

import java.util.Date;
import java.util.List;

public interface PortfolioModel {

  void buyStocks(String quantity,String CompanyName, String portfolioName);

  void sellStocks(String quantity,String CompanyName, String portfolioName);

  PortfolioModel createPortfolioUsingFilePath(String filePath);

  double getTotalValueOfPortfolioOnCertainDate(Date date, String portfolioName);

  boolean isPortfolioCreated();

}
