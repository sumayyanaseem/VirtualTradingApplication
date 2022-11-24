package stocks;

import stocks.controller.PortfolioGUIController;
import stocks.model.FlexiblePortfolioImpl;
import stocks.view.PortfolioViewJFrame;
import stocks.view.PortfolioViewJFrameImpl;

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
   /* PortfolioView view = new PortfolioViewImpl(System.out);
    PortfolioController controller = new PortfolioControllerImpl( System.in, view);
    controller.start();*/

    FlexiblePortfolioImpl model = new FlexiblePortfolioImpl();
    PortfolioGUIController controller = new PortfolioGUIController(model);
    PortfolioViewJFrame view = new PortfolioViewJFrameImpl();
    controller.setView(view);
  }
}