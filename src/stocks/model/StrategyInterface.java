package stocks.model;

import java.util.Map;

public interface StrategyInterface {

  void applyStrategyOnPortfolio(String portfolioName, Map<String, Double> stockAndWeights,
                                   double investmentAmount,  double commission) throws IllegalArgumentException;

}
