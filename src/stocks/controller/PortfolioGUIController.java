package stocks.controller;

import stocks.model.IFlexible;
import stocks.view.IViewInterface;
import stocks.view.PortfolioGUIView;

public class PortfolioGUIController implements Features,PortfolioController{
  private IFlexible model;
  private PortfolioGUIView view;

  public PortfolioGUIController(IFlexible m, IViewInterface v) {
    model = m;
    this.view= (PortfolioGUIView) v;
    view.addFeatures(this);
  }

  @Override
  public void displayPanelToEnterPortfolioName() {
    view.showDisplayPanelToEnterPortfolioName();
  }

  @Override
  public void createPortfolio(String pName, String pType) {
    try {
      model.createEmptyPortfolio(pName, pType);
      view.showDialogue("portfolio "+pName+" created successfully");
    }
    catch(Exception e){
      view.showDialogue("Error creating portfolio "+pName+" : "+e.getMessage());
    }
  }

  @Override
  public void displayPanelToEnterBuyInfo() {
    view.showDisplayPanelToEnterBuyInfo();
  }

  @Override
  public void addBoughtStockToPortfolio(String ticker, String date, String qty, String comm, String pName) {
    try {
      model.buyStocks(ticker, qty, date, comm, pName);
      view.showDialogue("Buy order executed successfully");
    }
    catch(Exception e){
      view.showDialogue("Error while trying to buy the stock : "+e.getMessage());
    }
  }

  @Override
  public void displayPanelToEnterSellInfo() {
    view.showDisplayPanelToEnterSellInfo();
  }

  @Override
  public void addSoldStockToPortfolio(String ticker, String date, String qty, String comm, String pName) {
    try {
      model.sellStocks(ticker, qty, date, comm, pName);
      view.showDialogue("Sell order executed successfully");
    }
    catch(Exception e){
      view.showDialogue("Error while trying to sell the stock : "+e.getMessage());
    }
  }

  @Override
  public void displayPanelToQueryPortfolioDetails() {
    view.showDisplayPanelToEnterSellInfo();
  }

  @Override
  public void getTotalValue(String pName, String date) {
    try {
     double val= model.getTotalValueOfPortfolioOnCertainDate(date, pName);
      view.showDialogue("Total value of portfolio "+pName+" on "+date+" is :"+val);
    }
    catch(Exception e){
      view.showDialogue("Error while trying fetch total value : "+e.getMessage());
    }
  }

  @Override
  public void getCostBasis(String pName, String date) {
    try {
      double val= model.getTotalMoneyInvestedOnCertainDate(date, pName);
      view.showDialogue("Total Cost Basis of portfolio "+pName+" on "+date+" is :"+val);
    }
    catch(Exception e){
      view.showDialogue("Error while trying fetch total cost basis : "+e.getMessage());
    }
  }

  @Override
  public void start() {

  }
}
