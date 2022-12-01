package stocks.view;

/**
 * This class represents a factory class to generate view objects.
 */

public class ViewFactory {

  /**
   * constructs an view factory object based on the given type.
   *
   * @param type the type of UI (text/GUI).
   * @return an object of IViewInterface.
   */
  public static IViewInterface generateViewFactory(String type) {
    if (type.equals("GUI")) {
      return new PortfolioGUIViewImpl();
    } else if (type.equals("CONSOLE")) {
      return new PortfolioViewImpl(System.out);
    } else {
      return new PortfolioViewImpl(System.out);
    }
  }
}
