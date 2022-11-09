package stocks.model;

public class BuyStocks extends Stock {
  private final double priceBought;
  private final String dateBought;
  private static final String action = "buy";

  public BuyStocks(String companyTickerSymbol, double qty, double totalValue, double priceBought, String dateBought) {
    super(companyTickerSymbol, qty, totalValue, action, priceOfStockAsOfGivenDate, dateOfAction);
    this.priceBought = priceBought;
    this.dateBought = dateBought;
  }


  /**
   * gives the date on which the stock was bought.
   *
   * @return buy date of the stock.
   */
  public String getDateBought() {
    return dateBought;
  }


  /**
   * gives the price at which stock was bought.
   *
   * @return the buy price of the stock.
   */
  public double getPriceBought() {
    return priceBought;
  }

  public String getAction() {
    return action;
  }

}
