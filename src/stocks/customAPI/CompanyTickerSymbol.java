package stocks.customAPI;

public enum CompanyTickerSymbol {
  GOOG("2000-01-01"),
  META("2000-01-01"),
  AMZN("2000-01-01"),
  CCL("2000-01-01"),
  COIN("2000-01-01"),
  DASH("2000-01-01"),
  DKNG("2000-01-01"),
  F("2000-01-01"),
  GT("2000-01-01"),
  IBM("2000-01-01"),
  INTC("2000-01-01"),
  AAPL("2000-01-01"),
  ABNB("2000-01-01"),
  NU("2000-01-01"),
  NVDA("2000-01-01"),
  NXPI("2000-01-01"),
  ORCL("2000-01-01"),
  SHOP("2000-01-01"),
  SOFI("2000-01-01"),
  T("2000-01-01"),
  TLRY("2000-01-01"),
  TREX("2000-01-01"),
  TSLA("2000-01-01"),
  TSP("2000-01-01"),
  TWTR("2000-01-01"),
  UBER("2000-01-01");

  public final String label;

  CompanyTickerSymbol(String label) {
    this.label = label;
  }

  public String getEndDate() {
    return label;
  }
}
