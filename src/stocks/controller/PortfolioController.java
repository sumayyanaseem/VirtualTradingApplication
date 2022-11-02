package stocks.controller;

import stocks.model.PortfolioModel;


/**
 * This interface act as Controller for virtual-trading application.
 * The main objective of this interface is to review inputs from the view and call the corresponding model functions accordingly.
 */
public interface PortfolioController {

  /**
   * This is the starting point of this application.
   * @param model model of this application.
   */
  void start();

}
