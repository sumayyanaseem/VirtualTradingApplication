package stocks.view;

import stocks.Features;

public interface PortfolioViewJFrame {
  void addFeatures(Features features);

  void showDisplayPanelToEnterPortfolioName();

  void showDialogue(String s);

  void showDisplayPanelToEnterBuyInfo();

  void showDisplayPanelToEnterSellInfo();
}
