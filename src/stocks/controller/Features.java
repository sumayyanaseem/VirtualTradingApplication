package stocks.controller;

import java.util.List;
import java.util.Map;

public interface Features {
  void createPortfolio(String pName, String pType);

  void buyStock(String ticker, String date, String qty, String comm, String pName);

  void investFixedAmountStrategy(String portfolioName, Map<String, Double> stockAndPercent, double investmentAmount, double commissionFee,String date) ;

  void dollarCostStrategy(String portfolioName, Map<String, Double> stockAndPercent, double investmentAmount, double commissionFee, int investmentInterval,String dateStart, String dateEnd) ;

  void sellStock(String ticker, String date, String qty, String comm, String pName);

  double getTotalValue(String pName, String date);

  double getCostBasis(String pName, String date);

  List<List<String>> viewComposition(String pName, String date);

  void exitTheProgram();
}
