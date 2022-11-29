package stocks.view;

import java.util.List;

import stocks.controller.Features;

public interface PortfolioGUIView extends IViewInterface {
  void addFeatures(Features features);

  void showDialogue(String s);

  void showDisplayPanelToEnterBuyInfo();

  void showDisplayPanelToEnterSellInfo();

  void exitGracefully();

  void updatePortfolioList(List<String> list);
}
