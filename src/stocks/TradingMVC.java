package stocks;

import stocks.controller.PortfolioController;
import stocks.controller.PortfolioControllerImpl;
import stocks.model.PortfolioImplModel;

/**
 * Demonstrates a virtual trading Application. The application is
 * factored out into a model and controller.
 */
public class TradingMVC {

  public static void main(String[] args) {

    PortfolioController controller = new PortfolioControllerImpl(System.in, System.out);
    controller.start(new PortfolioImplModel());
  }
}