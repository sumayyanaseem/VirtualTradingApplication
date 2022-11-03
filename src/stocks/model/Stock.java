package stocks.model;


/**
 * This class represents a stock object with all the relavant details for a stock
 * such as ticker symbol, quantity, buy/sell price and dates and total value.
 */

public class Stock {
  String companyTickerSymbol;
  Double qty;
  double priceBought;

  String dateBought;

  String dateSold;
  double totalValue;

  /**
   * Constructs a Stock object and initializes all the relevant fields for a stock.
   */
  public Stock(String companyTickerSymbol, double qty, String dateBought, String dateSold, double priceBought, double totalValue) {
    this.companyTickerSymbol = companyTickerSymbol;
    this.qty = qty;
    this.dateBought = dateBought;
    this.dateSold = dateSold;
    this.priceBought = priceBought;
    this.totalValue = totalValue;
  }

  /**
   * gives the price at which stock was bought.
   *
   * @return the buy price of the stock.
   */
  public double getPriceBought() {
    return priceBought;
  }

  /**
   * gives the total value of the stock based on quantity and stock price.
   *
   * @return the total value calculated.
   */
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


  /**
   * gives the date on which the stock was bought.
   *
   * @return buy date of the stock.
   */
  public String getDateBought() {
    return dateBought;
  }


  /**
   * gives the date on which the stock was sold.
   *
   * @return sell date of the stock.
   */
  public String getDateSold() {
    return dateSold;
  }


  @Override
  public String toString(){
    StringBuilder res= new StringBuilder();
    res.append("company : "+getCompanyTickerSymbol());
    res.append(", ");
    res.append("quantity : " +String.format("%.2f",getQty()));
    res.append(", ");
    res.append("buyPrice : "+String.format("%.2f",getPriceBought()));
    res.append(", ");
    res.append("TotalValue : "+String.format("%.2f",getTotalValue()));
    res.append("\n");
    return res.toString();
  }
}
