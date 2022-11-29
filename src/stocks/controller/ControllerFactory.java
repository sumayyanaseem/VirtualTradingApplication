package stocks.controller;


import stocks.model.IFlexible;
import stocks.view.IViewInterface;

public interface ControllerFactory {


  static PortfolioController generateControllerFactory(String type, IViewInterface iv, IFlexible im) {
    if (type.equals("GUI")) {
      return new PortfolioGUIController(im, iv);
    } else if (type.equals("CONSOLE")) {
      return new PortfolioControllerImpl(System.in, iv);
    } else {
      return new PortfolioControllerImpl(System.in, iv);
    }
  }
}
