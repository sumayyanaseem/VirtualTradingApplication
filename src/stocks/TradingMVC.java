package stocks;

import stocks.controller.PortfolioController;
import stocks.controller.PortfolioControllerImpl;
import stocks.view.PortfolioView;
import stocks.view.PortfolioViewImpl;

/**
 * Demonstrates a virtual trading Application. The application is
 * factored out into a model,view and controller.
 */
public class TradingMVC {

  /**
   * main method is the start point of virtual trading application.
   *
   * @param args Not used.
   */
  public static void main(String[] args) {
    PortfolioView view = new PortfolioViewImpl(System.out);
    PortfolioController controller = new PortfolioControllerImpl( System.in, view);
    controller.start();
  }
}