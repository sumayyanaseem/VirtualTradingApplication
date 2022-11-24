package stocks.controller;

public interface Features {
  void displayPanelToEnterPortfolioName();
  void createPortfolio(String pName, String pType);
  void displayPanelToEnterBuyInfo();
  void addBoughtStockToPortfolio(String ticker, String date, String qty, String comm, String pName);
  void displayPanelToEnterSellInfo();
  void addSoldStockToPortfolio(String ticker, String date, String qty, String comm, String pName);
  void displayPanelToQueryPortfolioDetails();
  void getTotalValue(String pName, String date);
  void getCostBasis(String pName, String date);
}
