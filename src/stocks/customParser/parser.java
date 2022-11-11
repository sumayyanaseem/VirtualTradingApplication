package stocks.customParser;

import java.util.List;


/**
 * This class represents all the parser functions w.r.t files reading/writing.
 */

public interface parser {


  /**
   * reads from file of corresponding portfolio.
   *
   * @return the info read from  file as a list of each stock's data.
   */
  List<List<String>> readFromFile(String portFolioName);

  /**
   * reads the  file from path provided.
   *
   * @return the info read from  file as a list of each stock's data.
   */
  List<List<String>> readFromPathProvidedByUser(String path);
}
