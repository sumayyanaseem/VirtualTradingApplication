package stocks.model;

import java.util.Map;

public interface InvestmentStrategyInterface extends IFlexible {

  void dollarCostAveragingStrategy(String portfolioName, Map<String, Double> stockNameAndPercent,
                                   double investmentAmount, int investmentInterval, String startDate,
                                   String endDate, double commission) throws IllegalArgumentException;
}
