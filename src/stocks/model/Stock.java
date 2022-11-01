package stocks.model;

public class Stock {
  String companyTickerSymbol;
  Double qty;
  double priceBought;

  String dateBought;

  String dateSold;
  double totalValue;

  public Stock(String companyTickerSymbol, double qty, String dateBought, String dateSold, double priceBought, double totalValue) {
    this.companyTickerSymbol = companyTickerSymbol;
    this.qty = qty;
    this.dateBought = dateBought;
    this.dateSold = dateSold;
    this.priceBought = priceBought;
    this.totalValue = totalValue;
  }

  public double getPriceBought() {
    return priceBought;
  }

  public double getTotalValue() {
    return totalValue;
  }

  public double getQty() {
    return qty;
  }

  public String getCompanyTickerSymbol() {
    return companyTickerSymbol;
  }

  public String getDateBought() {
    return dateBought;
  }

  public String getDateSold() {
    return dateSold;
  }
}
