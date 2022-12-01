package stocks.model;

import java.util.Map;


/**
 * This interface represents a strategy interface which
 * offers a method to apply strategy.
 */
public interface StrategyInterface {

  /**
   * applies strategy on given portfolio with relavant parameters.
   * @param portfolioName name of the portfolio on which the strategy needs to be applied.
   * @param stockAndWeights a map which stores stocks and its corresponding weights.
   * @param investmentAmount amount to be invested using tis strategy.
   * @param commission the amount charged for this transaction.
   * @throws IllegalArgumentException
   */
  void applyStrategyOnPortfolio(String portfolioName, Map<String, Double> stockAndWeights,
                                double investmentAmount, double commission) throws IllegalArgumentException;

}
