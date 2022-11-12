package stocks.customParser;

import java.util.List;
import java.util.Map;

import stocks.model.Stock;


/**
 * This class represents all the parser functions w.r.t files reading/writing.
 */

public interface Parser {


  /**
   * reads from file of corresponding portfolio.
   *
   * @return the info read from  file as a list of each stock's data.
   */
  Map<String,List<Stock>> readFromFile(String portFolioName);

  /**
   * reads the  file from path provided.
   *
   * @return the info read from  file as a list of each stock's data.
   */
  Map<String,List<Stock>> readFromPathProvidedByUser(String path);
}
