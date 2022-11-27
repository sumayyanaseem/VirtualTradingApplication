package stocks.controller;

import java.util.List;
import java.util.Map;

import stocks.model.IFlexible;
import stocks.view.IViewInterface;
import stocks.view.PortfolioGUIView;

public class PortfolioGUIController implements Features, PortfolioController {
  private final IFlexible model;
  private final PortfolioGUIView view;

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
      view.displayMessage("portfolio " + pName + " created successfully");
    } catch (Exception e) {
      view.displayMessage("Error creating portfolio " + pName + " : " + e.getMessage());
    }
  }

  @Override
  public void buyStock(String ticker, String date, String qty, String comm, String pName) {
    try {
      model.updatePortfolio(ticker, qty, date, pName, "buy", comm);
      view.displayMessage("Bought stocks successfully");
    } catch (Exception e) {
      view.displayMessage("Error while trying to buy the stock : " + e.getMessage());
    }
  }

  @Override
  public void investFixedAmountStrategy(String portfolioName, Map<String, Double> stockAndPercent, double investmentAmount, double commissionFee, String date) {

  }

  @Override
  public void dollarCostStrategy(String portfolioName, Map<String, Double> stockAndPercent, double investmentAmount, double commissionFee, int investmentInterval,String dateStart, String dateEnd) {

    try {
      model.dollarCostStrategy(portfolioName, stockAndPercent,investmentAmount,commissionFee,investmentInterval,dateStart, dateEnd);
      view.displayMessage("Bought stocks successfully");
    } catch (Exception e) {
      view.displayMessage("Error while trying to buy the stock : " + e.getMessage());
    }

  }


  @Override
  public void sellStock(String ticker, String date, String qty, String comm, String pName) {
    try {
      model.updatePortfolio(ticker, qty, date, pName, "sell", comm);
      view.displayMessage("Sold stocks successfully");
    } catch (Exception e) {
      view.displayMessage("Error while trying to sell the stock : " + e.getMessage());
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

  @Override
  public List<String> getStocksInPortfolio(String portfolioName){
    return model.getStocksInPortfolio(portfolioName);
  }
}
