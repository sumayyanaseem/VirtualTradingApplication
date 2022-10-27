package stocks;

import java.io.IOException;

import stocks.controller.PortfolioController;
import stocks.controller.PortfolioControllerImpl;
import stocks.model.PortfolioImplModel;
import stocks.model.PortfolioModel;
import stocks.view.PortfolioView;
import stocks.view.PortfolioViewImpl;

public class TradingMVC {

  public static void main(String[] args) throws IOException {

    //fetch student record based on his roll no from the database
    PortfolioModel model = new PortfolioImplModel();

    //Create a view :
    PortfolioView view = new PortfolioViewImpl();

    PortfolioController controller = new PortfolioControllerImpl(model, view);

    controller.start();
  }
}