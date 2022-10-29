package stocks;

import java.io.IOException;

import stocks.controller.PortfolioController;
import stocks.controller.PortfolioControllerImpl;
import stocks.model.PortfolioImplModel;
import stocks.view.PortfolioView;
import stocks.view.PortfolioViewImpl;

public class TradingMVC {

  public static void main(String[] args) throws IOException {

    //Create a view :
    PortfolioView view = new PortfolioViewImpl();

    PortfolioController controller = new PortfolioControllerImpl(view);

    controller.start(new PortfolioImplModel());
  }
}