package stocks.customparser;

import java.util.List;
import java.util.Map;

import stocks.model.Stock;


/**
 * This class represents all the parser functions w.r.t files reading/writing.
 */

public interface CustomParser {


  /**
   * reads from file of corresponding portfolio.
   *
   * @return the info read from  file as a list of each stock's data.
   */
  Map<String, List<Stock>> readFromFile(String portFolioName);

  /**
   * reads the  file from path provided.
   *
   * @return the info read from  file as a list of each stock's data.
   */
  Map<String, List<Stock>> readFromPathProvidedByUser(String path);

  /**
   * append into the given file.
   *
   * @param portfolioName name of the portfolio.
   * @param companyName   name of the company.
   * @param quantity      quantity of stocks.
   * @param action        buy/sell.
   * @param date          date of transaction.
   * @param com           commisison of transaction.
   */
  void appendIntoFile(String portfolioName, String companyName, String quantity,
                      String action, String date, String com);

  /**
   * write into given file.
   *
   * @param portFolioName name of the potfolio.
   * @param map           values to be written into file.
   * @param type          type of the portfolio.
   */
  void writeIntoFile(String portFolioName, Map<String, List<Stock>> map, String type);

  /**
   * get the type of file using name.
   *
   * @param portFolioName name of the portfolio.
   * @return returns the type of the portfolio.
   */
  String getTypeOfFile(String portFolioName);

  /**
   * get the type of file when path is provided.
   *
   * @param path path of the file.
   * @return returns the type of the portfolio.
   */
  String getTypeOfLoadedFile(String path);

  /**
   * Appends thr given info into the file.
   *
   * @param path          path of the file.
   * @param portfolioName name of the portfolio.
   * @param companyName   company ticker symbol.
   * @param quantity      quantity.
   * @param action        action -buy/sell.
   * @param date          date of transaction.
   * @param com           commission for this transaction.
   */
  void appendIntoFileUsingFilePath(String path, String portfolioName,
                                   String companyName, String quantity,
                                   String action, String date, String com);


  /**
   * fetches the portfolio name from file name.
   *
   * @param fileName filename from which portfolio name needs to be fetched.
   */
  String getPortfolioNameFromFileName(String fileName);


}
