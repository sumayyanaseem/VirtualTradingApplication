package stocks.controller;

import stocks.Features;
import stocks.model.FlexiblePortfolioImpl;
import stocks.model.PortfolioModel;
import stocks.view.PortfolioViewJFrame;

public class PortfolioControllerJFrame implements Features {
  private FlexiblePortfolioImpl model;
  private PortfolioViewJFrame view;

  public PortfolioControllerJFrame(FlexiblePortfolioImpl m) {
    model = m;
  }

  public void setView(PortfolioViewJFrame v) {
    view = v;
    //provide view with all the callbacks
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
}
