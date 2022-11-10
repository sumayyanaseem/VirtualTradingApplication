package stocks.model;

//not needed now
public class SellStocks extends Stock {
  private final double priceSold;
  private final String dateSold;
  private static final String action = "sell";

  public SellStocks(String companyTickerSymbol, double qty, double totalValue, double priceSold, String dateSold) {
    super(companyTickerSymbol, qty, totalValue, action, priceOfStockAsOfGivenDate, dateOfAction);
    this.priceSold = priceSold;
    this.dateSold = dateSold;
  }


  /**
   * gives the date on which the stock was sold.
   *
   * @return sell date of the stock.
   */
  public String getDateSold() {
    return dateSold;
  }

  public String getAction() {
    return action;
  }

  public double getPriceSold() {
    return priceSold;
  }
}
