package stocks.model;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface PortfolioModel {

  void buyStocks(String quantity,String CompanyName, String portfolioName) throws IOException;

  void sellStocks(String quantity,String CompanyName, String portfolioName);

  double getTotalValueOfPortfolioOnCertainDate(String date, String portfolioName);

  void createPortfolioUsingFilePath(String filePath) throws IOException;


  boolean isPortfolioCreated();

  List<List<String>> readFromCSVFile(String portfolioName);

}
