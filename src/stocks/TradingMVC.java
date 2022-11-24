package stocks;

import stocks.controller.PortfolioGUIController;
import stocks.model.FlexiblePortfolioImpl;
import stocks.view.PortfolioGUIView;
import stocks.view.PortfolioGUIViewImpl;

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
    PortfolioGUIView view = new PortfolioGUIViewImpl();
    PortfolioGUIController controller = new PortfolioGUIController(model,view);


  }
}