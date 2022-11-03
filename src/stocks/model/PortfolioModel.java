package stocks.model;
import java.io.IOException;
import java.util.List;

/**
 * This interface act as model for virtual-trading application.
 * It has implemented all trivial operations of trading like creating a portfolio,
 * buying and selling stocks,total value of portfolio.
 */
public interface PortfolioModel {

  void buyStocks(String quantity, String CompanyName, String portfolioName);

  double getTotalValueOfPortfolioOnCertainDate(String date, String portfolioName);

  /**
   * @param filePath
   */
  void createPortfolioUsingFilePath(String filePath);


  List<List<String>> viewCompositionOfCurrentPortfolio(String portfolioName);


  void createPortfolioIfCreatedManually(String portfolioName);

  void validateIfCompanyExists(String companyName);

  void validateIfPortfolioAlreadyExists(String portfolioName);

  void validateIfPortfolioDoesntExists(String name);

  PortfolioModel getInstance();

}
