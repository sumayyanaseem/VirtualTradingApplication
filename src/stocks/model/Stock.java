package stocks.model;


/**
 * This class represents a stock object with all the relevant details for a stock
 * such as ticker symbol, quantity, buy/sell price and dates and total value.
 */

public class Stock {
  private final String companyTickerSymbol;
  private final double qty;
  private final double totalValue;
  private final String action;
  private final double priceOfStockAsOfGivenDate;

  private final String dateOfAction;

  private final double commission;

  /**
   * Constructs a Stock object and initializes all the relevant fields for a stock.
   */
  public Stock(String companyTickerSymbol, double qty,
               double totalValue, String action,
               double priceOfStockAsOfGivenDate,
               String dateOfAction, double commission) {
    this.companyTickerSymbol = companyTickerSymbol;
    this.qty = qty;
    this.totalValue = totalValue;
    this.action = action;
    this.priceOfStockAsOfGivenDate = priceOfStockAsOfGivenDate;
    this.dateOfAction = dateOfAction;
    this.commission = commission;
  }

  /**
   * gives commission of this transaction.
   *
   * @return returns commission.
   */
  public double getCommission() {
    return commission;
  }

  /**
   * gives the price of stock.
   *
   * @return returns stock price.
   */
  public double getPriceOfStockAsOfGivenDate() {
    return priceOfStockAsOfGivenDate;
  }

  /**
   * gives action buy/sell.
   *
   * @return returns action of transaction.
   */
  public String getAction() {
    return action;
  }

  /**
   * gives date of transaction.
   *
   * @return returns date of the action.
   */
  public String getDateOfAction() {
    return dateOfAction;
  }

  /**
   * gives total value.
   *
   * @return return total value.
   */
  public double getTotalValue() {
    return totalValue;
  }

  /**
   * gives the total quantity of stock.
   *
   * @return the number of stocks bought for this company.
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
    return "company : " + getCompanyTickerSymbol()
            + ", "
            + "quantity : " + String.format("%.2f", getQty())
            + ", "
            + "\n";
  }
}
