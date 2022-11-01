package stocks.controller;

import java.io.IOException;

import stocks.model.PortfolioModel;


/**
 * This interface act as Controller for virtual-trading application.
 * The main objective of this interface is to review inputs from the view and call the corresponding model functions accordingly.
 */
public interface PortfolioController {

  /**
   *
   * @param model
   */
  void start(PortfolioModel model);

}
