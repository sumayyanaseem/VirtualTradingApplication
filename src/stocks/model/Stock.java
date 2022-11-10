package stocks.model;


/**
 * This class represents a stock object with all the relavant details for a stock
 * such as ticker symbol, quantity, buy/sell price and dates and total value.
 */

public class Stock {
  private final String companyTickerSymbol;
  private final Double qty;
  private final double totalValue;
  private final String action;
  private final double priceOfStockAsOfGivenDate;

  private final String dateOfAction;

  /**
   * Constructs a Stock object and initializes all the relevant fields for a stock.
   */
  public Stock(String companyTickerSymbol, double qty, double totalValue, String action, double priceOfStockAsOfGivenDate, String dateOfAction) {
    this.companyTickerSymbol = companyTickerSymbol;
    this.qty = qty;
    this.totalValue = totalValue;
    this.action = action;
    this.priceOfStockAsOfGivenDate = priceOfStockAsOfGivenDate;
    this.dateOfAction = dateOfAction;
  }

  public double getPriceOfStockAsOfGivenDate() {
    return priceOfStockAsOfGivenDate;
  }

  public String getAction() {
    return action;
  }

  public String getDateOfAction() {
    return dateOfAction;
  }


  public double getTotalValue() {
    return totalValue;
  }

  /**
   * gives the total quantity of stock.
   *
   * @return the number of stocks bught for this company.
   */
  public double getQty() {
    return qty;
  }

  /**
   * gives the ticker symbol for the stock.
   *
   * @return the string symbol representation of a company.
   */
  public String getCompanyTickerSymbol() {
    return companyTickerSymbol;
  }


  @Override
  public String toString() {
    StringBuilder res = new StringBuilder();
    res.append("company : ").append(getCompanyTickerSymbol());
    res.append(", ");
    res.append("quantity : ").append(String.format("%.2f", getQty()));
    res.append(", ");
    res.append("\n");
    return res.toString();
  }
}
