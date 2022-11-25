package stocks;


import stocks.controller.ControllerFactory;
import stocks.controller.PortfolioController;
import stocks.model.FlexiblePortfolioImpl;
import stocks.model.IFlexible;
import stocks.view.IViewInterface;
import stocks.view.ViewFactory;

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
    String type = "";
    if (args.length != 0) {
      type = args[0].toUpperCase();
    } else {
      throw new IllegalArgumentException("Type of User Interface Not Specified");
    }
    if (type.equals("GUI") || type.equals("CONSOLE")) {
      //TODO:make changes to get correct model for gui.
      IFlexible im = new FlexiblePortfolioImpl();
      IViewInterface iv = ViewFactory.generateViewFactory(type);
      PortfolioController controller = ControllerFactory.generateControllerFactory(type, iv, im);
      controller.start();
    } else {
      throw new IllegalArgumentException("Invalid Type of User Interface");
    }
  }
}