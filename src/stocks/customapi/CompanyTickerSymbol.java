package stocks.customapi;

/**
 * This enum represents stocks supported by application and IPO dates for each.
 */
public enum CompanyTickerSymbol {
  GOOG("2014-05-01"),
  META("2013-01-01"),
  AMZN("2000-01-01"),
  CCL("2000-01-01"),
  COIN("2021-05-01"),
  DASH("2020-12-20"),
  DKNG("2019-07-25"),
  F("2000-01-01"),
  GT("2000-01-01"),
  IBM("2000-01-01"),
  INTC("2000-01-01"),
  AAPL("2000-01-01"),
  ABNB("2020-12-20"),
  NU("2021-12-20"),
  NVDA("2000-01-01"),
  NXPI("2010-09-01"),
  ORCL("2000-01-01"),
  SHOP("2015-05-30"),
  SOFI("2020-12-10"),
  T("2000-01-01"),
  TLRY("2018-08-01"),
  TREX("2000-01-01"),
  TSLA("2010-08-01"),
  TSP("2011-07-01"),
  TWTR("2013-12-01"),
  UBER("2019-06-01");

  public final String label;

  CompanyTickerSymbol(String label) {
    this.label = label;
  }

  public String getEndDate() {
    return label;
  }
}
