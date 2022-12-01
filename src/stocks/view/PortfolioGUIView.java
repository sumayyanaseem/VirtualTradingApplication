package stocks.view;

import java.util.List;

import stocks.controller.Features;

/**
 * an interface to create GUI view by extending IViewInterface.
 */
public interface PortfolioGUIView extends IViewInterface {

  /**
   * constructs an InvestFixedAmountStrategyPanel object.
   *
   * @param features contains list of portfolios.
   */
  void addFeatures(Features features);

  /**
   * method to exit the program.
   */
  void exitGracefully();

  /**
   * method to update list of portfolios.
   *
   * @param list contains list of portfolios.
   */
  void updatePortfolioList(List<String> list);
}
