package stocks;

import java.io.IOException;

import stocks.controller.PortfolioController;
import stocks.controller.PortfolioControllerImpl;
import stocks.model.PortfolioImplModel;
import stocks.view.PortfolioView;
import stocks.view.PortfolioViewImpl;

/**
 * Demonstrates a virtual trading Application. The application is
 *  factored out into a model,view and controller.
 */
public class TradingMVC {

  public static void main(String[] args) throws IOException {

    //Create a view :
    PortfolioView view = new PortfolioViewImpl(System.in);

    PortfolioController controller = new PortfolioControllerImpl(view);

    controller.start(new PortfolioImplModel());
  }
}