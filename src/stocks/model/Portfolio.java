package stocks.model;

import java.util.Date;

public interface Portfolio {

  Portfolio buyStocks(String companyId, String qty);

  Portfolio sellStocks(int quantity,String CompanyName, String portfolioName);

  Portfolio createPortfolio(String portfolioName);

  double getTotalValueOfPortfolioOnCertainDate(Date date, String portfolioName);

}
