package stocks.view;

import stocks.controller.Features;

public interface PortfolioGUIView extends IViewInterface {
  void addFeatures(Features features);

  void showDisplayPanelToEnterPortfolioName();

  void showDialogue(String s);

  void showDisplayPanelToEnterBuyInfo();

  void showDisplayPanelToEnterSellInfo();
}
