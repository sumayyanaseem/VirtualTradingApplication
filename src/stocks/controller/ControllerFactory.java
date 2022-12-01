package stocks.controller;


import stocks.model.IFlexible;
import stocks.view.IViewInterface;


/**
 * Represents a factory which generates controller objects based on the view type.
 */
public class ControllerFactory {

  /**
   * Method to construct the controller object based on text/gui view types.
   *
   * @param type represents type of the view.
   * @param iv   the view interface object.
   * @param im   the model object.
   * @return controller object generated based on view type.
   */
  public static PortfolioController generateControllerFactory(
          String type, IViewInterface iv, IFlexible im) {
    if (type.equals("GUI")) {
      return new PortfolioGUIController(im, iv);
    } else if (type.equals("CONSOLE")) {
      return new PortfolioControllerImpl(System.in, iv);
    } else {
      return new PortfolioControllerImpl(System.in, iv);
    }
  }
}
