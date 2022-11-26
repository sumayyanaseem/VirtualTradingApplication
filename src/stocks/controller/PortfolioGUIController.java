package stocks.controller;

import java.util.List;
import java.util.Map;

import stocks.model.IFlexible;
import stocks.view.IViewInterface;
import stocks.view.PortfolioGUIView;

public class PortfolioGUIController implements Features, PortfolioController {
  private IFlexible model;
  private PortfolioGUIView view;

  public PortfolioGUIController(IFlexible m, IViewInterface v) {
    model = m;
    this.view = (PortfolioGUIView) v;
    view.addFeatures(this);
  }

  @Override
  public void createPortfolio(String pName, String pType) {
    try {
      model.createEmptyPortfolio(pName, pType);
      view.updatePortfolioList(model.getListOfPortfolioNames());
      view.showDialogue("portfolio " + pName + " created successfully");
    } catch (Exception e) {
      view.showDialogue("Error creating portfolio " + pName + " : " + e.getMessage());
    }
  }

  @Override
  public void buyStock(String ticker, String date, String qty, String comm, String pName) {
    try {
      model.updatePortfolio(ticker, qty, date, pName, "buy", comm);
      view.showDialogue("Bought stocks successfully");
    } catch (Exception e) {
      view.showDialogue("Error while trying to buy the stock : " + e.getMessage());
    }
  }

  @Override
  public void dollarCostStrategy(String portfolioName, Map<String, Double> stockAndPercent, double investmentAmount, double commissionFee, int investmentInterval,String dateStart, String dateEnd) {

    try {
      model.dollarCostStrategy(portfolioName, stockAndPercent,investmentAmount,commissionFee,investmentInterval,dateStart, dateEnd);
      view.showDialogue("Bought stocks successfully");
    } catch (Exception e) {
      view.showDialogue("Error while trying to buy the stock : " + e.getMessage());
    }

  }


  @Override
  public void sellStock(String ticker, String date, String qty, String comm, String pName) {
    try {
      model.updatePortfolio(ticker, qty, date, pName, "sell", comm);
      view.showDialogue("Sold stocks successfully");
    } catch (Exception e) {
      view.showDialogue("Error while trying to sell the stock : " + e.getMessage());
    }
  }

  @Override
  public double getTotalValue(String pName, String date) {
    return model.getTotalValueOfPortfolioOnCertainDate(date, pName);
  }

  @Override
  public double getCostBasis(String pName, String date) {

    return model.getTotalMoneyInvestedOnCertainDate(date, pName);

  }

  @Override
  public List<List<String>> viewComposition(String pName, String date) {
    return model.viewCompositionOfCurrentPortfolio(pName, date);
  }

  @Override
  public void exitTheProgram() {
    view.exitGracefully();
  }

  @Override
  public void start() {

  }
}
