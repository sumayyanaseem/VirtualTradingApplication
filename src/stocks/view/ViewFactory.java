package stocks.view;

public class ViewFactory {

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
