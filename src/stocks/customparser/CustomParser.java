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

  void appendIntoFile(String portfolioName, String companyName, String quantity,
                      String action, String date, String com);


  void writeIntoFile(String portFolioName, Map<String, List<Stock>> map, String type);

  String getTypeOfFile(String portFolioName);

  String getTypeOfLoadedFile(String path);

  void appendIntoFileUsingFilePath(String path, String portfolioName,
                                   String companyName, String quantity,
                                   String action, String date, String com);


}
