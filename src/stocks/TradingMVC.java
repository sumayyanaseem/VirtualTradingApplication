package stocks;

import stocks.controller.PortfolioController;
import stocks.controller.PortfolioControllerImpl;
import stocks.model.PortfolioImplModel;
import stocks.model.PortfolioPerformance;
import stocks.view.PortfolioView;
import stocks.view.PortfolioViewImpl;

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
    PortfolioPerformance p = new PortfolioPerformance();
    //System.out.println(p.monthsBetween("a","b"));
    System.out.println(p.yearsBetween("a","b"));
    System.out.println(p.weeksBetween("a","b"));
    System.out.println(p.daysBetween("a","b"));
    System.out.println(p.quartersBetween("a","b"));
    p.display("META","2022-01-12","2022-08-12");
   /* PortfolioView view = new PortfolioViewImpl(System.out);
    PortfolioController controller = new PortfolioControllerImpl(System.in, view);
    controller.start(new PortfolioImplModel());*/
  }
}