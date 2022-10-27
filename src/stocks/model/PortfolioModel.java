package stocks.model;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface PortfolioModel {

  void buyStocks(String quantity,String CompanyName, String portfolioName) throws IOException;

  void sellStocks(String quantity,String CompanyName, String portfolioName);

  double calculateValuationAsOfDate(String date, String portfolioName);

  PortfolioModel createPortfolioUsingFilePath(String filePath);

  double getTotalValueOfPortfolioOnCertainDate(Date date, String portfolioName);

  boolean isPortfolioCreated();

}
