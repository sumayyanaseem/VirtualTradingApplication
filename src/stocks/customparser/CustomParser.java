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


<<<<<<< HEAD:src/stocks/customParser/CustomParser.java
  void appendIntoFile(String portfolioName, String companyName, String quantity, String action, String date,String com);
=======
  void appendIntoFile(String portfolioName, String companyName,
                      String quantity, String action, String date);
>>>>>>> 7d1d57095663cae004e2ceb15e53c942f0c7cb5e:src/stocks/customparser/CustomParser.java


  void writeIntoFile(String portFolioName, Map<String, List<Stock>> map, String type);

  String getTypeOfFile(String portFolioName);

  String getTypeOfLoadedFile(String path);

<<<<<<< HEAD:src/stocks/customParser/CustomParser.java
  void appendIntoFileUsingFilePath(String path, String portfolioName, String companyName, String quantity, String action, String date,String com);
=======
  void appendIntoFileUsingFilePath(String path, String portfolioName,
                                   String companyName, String quantity, String action, String date);
>>>>>>> 7d1d57095663cae004e2ceb15e53c942f0c7cb5e:src/stocks/customparser/CustomParser.java
}
