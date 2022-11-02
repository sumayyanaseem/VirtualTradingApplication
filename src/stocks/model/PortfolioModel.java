package stocks.model;
import java.util.List;

/**
 * This interface act as model for virtual-trading application.
 * It has implemented all trivial operations of trading like creating a portfolio,
 * buying and selling stocks,total value of portfolio.
 */
public interface PortfolioModel {

  /**
   * @param quantity
   * @param CompanyName
   * @param portfolioName
   */
  void buyStocks(String quantity, String CompanyName, String portfolioName);


  /* void sellStocks(String quantity,String CompanyName, String portfolioName);*/

  /**
   * @param date
   * @param portfolioName
   * @return
   */

  double getTotalValueOfPortfolioOnCertainDate(String date, String portfolioName);

  /**
   * @param filePath
   */
  void createPortfolioUsingFilePath(String filePath);


  /**
   * @param portfolioName
   * @return
   */

  List<List<String>> viewCompositionOfCurrentPortfolio(String portfolioName);

  /**
   * @param portfolioName
   */
  void createPortfolioIfCreatedManually(String portfolioName);

  PortfolioModel getInstance();

}
