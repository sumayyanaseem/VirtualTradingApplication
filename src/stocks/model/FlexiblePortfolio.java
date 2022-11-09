package stocks.model;

public interface FlexiblePortfolio {

  FlexiblePortfolio buyStocks(String companyName,int quantity,String date,String portfolioName);

  FlexiblePortfolio sellStocks(String companyName,int quantity,String date,String portfolioName);

  void getTotalMoneyInvestedOnCertainDate(String date,String portfolioName);

  void getTotalValueOfPortfolioOnCertainDate(String date,String portfolioName);

  void getPortfolioPerformanceOvertime(String startTime,String endTime,String portfolioName);
}
