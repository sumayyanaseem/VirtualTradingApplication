package stocks;

import stocks.controller.PortfolioController;
import stocks.controller.PortfolioControllerImpl;
import stocks.controller.PortfolioControllerJFrame;
import stocks.customapi.APICustomClass;
import stocks.model.FlexiblePortfolioImpl;
import stocks.model.PortfolioModel;
import stocks.model.PortfolioModelImpl;
import stocks.view.PortfolioView;
import stocks.view.PortfolioViewImpl;
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
    /*PortfolioView view = new PortfolioViewImpl(System.out);
    PortfolioModel model = new PortfolioModelImpl();
    PortfolioController controller = new PortfolioControllerImpl(model, System.in, view);
    controller.start();*/
    FlexiblePortfolioImpl model = new FlexiblePortfolioImpl(new APICustomClass("https://www.alphavantage.co/query?function=TIME_SERIES_"));
    PortfolioControllerJFrame controller = new PortfolioControllerJFrame(model);
    PortfolioViewJFrame view = new PortfolioViewJFrameImpl();
    controller.setView(view);
  }
}