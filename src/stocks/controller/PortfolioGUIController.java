package stocks.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stocks.model.IFlexible;
import stocks.view.IViewInterface;
import stocks.view.PortfolioGUIView;

/**
 * This class implements the methods of Portfolio Controller for GUI.
 */
public class PortfolioGUIController implements Features, PortfolioController {
  private final IFlexible model;
  private final PortfolioGUIView view;

  /**
   * Constructs PortfolioGUIController with given model and view objects.
   *
   * @param m the model object.
   * @param v the view object.
   */
  public PortfolioGUIController(IFlexible m, IViewInterface v) {
    model = m;
    this.view = (PortfolioGUIView) v;
    view.addFeatures(this);
    view.updatePortfolioList(model.getListOfPortfolioNames());
  }

  @Override
  public void createPortfolio(String pName, String pType) {
    try {
      model.validateIfPortfolioAlreadyExists(pName);

    } catch (Exception e) {
      view.displayMessage("portfolio already exists with this name. Try a new name");
      return;
    }
    try {
      model.createEmptyPortfolio(pName, pType);
      view.updatePortfolioList(model.getListOfPortfolioNames());
      view.displayMessage("portfolio " + pName + " created successfully");
    } catch (Exception e) {
      view.displayMessage("Error creating portfolio " + pName + " : " + e.getMessage());
    }
  }

  @Override
  public void buyStock(String ticker, String date, String qty, String comm, String pName) {
    try {
      try {
        Integer.parseInt(qty);
      } catch (NumberFormatException nfe) {
        view.displayMessage("quantity should be an integer");
        return;
      }
      model.updatePortfolio(ticker, qty, date, pName, "buy", comm);
      view.displayMessage("Bought stocks successfully");
    } catch (Exception e) {
      view.displayMessage("Error while trying to buy the stock : " + e.getMessage());
    }
  }

  @Override
  public void investFixedAmountStrategy(String portfolioName,
                                        Map<String, String> stockAndPercent,
                                        double investmentAmount,
                                        double commissionFee, String date) {
    Map<String, Double> stockPercentValues = new HashMap<>();
    double val;
    try {
      for (Map.Entry<String, String> entry : stockAndPercent.entrySet()) {
        try {
          val = Double.parseDouble(entry.getValue());

        } catch (Exception e) {
          view.displayMessage("percentages should be in numbers");
          return;
        }
        stockPercentValues.put(entry.getKey(), val);
      }
      model.fixedAmountStrategy(portfolioName,
              stockPercentValues, investmentAmount, commissionFee, date);
      view.displayMessage("Bought stocks via fixed amount strategy successfully");
    } catch (Exception e) {
      view.displayMessage("Error while trying to buy the stock : " + e.getMessage());
    }
  }

  @Override
  public void dollarCostStrategy(String portfolioName,
                                 Map<String, String> stockAndPercent,
                                 String investmentAmount,
                                 String commissionFee, String investmentInterval,
                                 String dateStart, String dateEnd) {
    int investInterval=0;
    double commFee=0;
    double investmentAnt = 0;
    try {
      investmentAnt = Double.parseDouble(investmentAmount);
    } catch(Exception e){
      view.displayMessage("Please provide valid investmentAmount");
    }
    try{
      commFee = Double.parseDouble(commissionFee);
    } catch(Exception e){
      view.displayMessage("Please provide valid CommissionFee");
    }
    try {

      investInterval = Integer.parseInt(investmentInterval);
    } catch(Exception e){
      view.displayMessage("InvestmentInterval must be positive whole number");
    }
    Map<String, Double> stockPercentValues = new HashMap<>();
    double val;
    try{
      for (Map.Entry<String, String> entry : stockAndPercent.entrySet()) {
        try {
          val = Double.parseDouble(entry.getValue());

        } catch (Exception e) {
          view.displayMessage("percentages should be in numbers");
          return;
        }
        stockPercentValues.put(entry.getKey(), val);
      }
      model.dollarCostStrategy(portfolioName, stockPercentValues,
              investmentAnt, commFee, investInterval,
              dateStart, dateEnd);
      view.displayMessage("Bought stocks via dollar cost strategy successfully");
    } catch (Exception e) {
      view.displayMessage("Error while trying to buy the stock : " + e.getMessage());
    }

  }


  @Override
  public void sellStock(String ticker, String date, String qty, String comm, String pName) {
    try {
      model.updatePortfolio(ticker, qty, date, pName, "sell", comm);
      view.displayMessage("Sold stocks successfully");
    } catch (Exception e) {
      view.displayMessage("Error while trying to sell the stock : " + e.getMessage());
    }
  }

  @Override
  public double getTotalValue(String pName, String date) throws IllegalArgumentException {
    double val;
    try {
      val = model.getTotalValueOfPortfolioOnCertainDate(date, pName);
    } catch (Exception e) {
      view.displayMessage("Error while trying to sell the stock : " + e.getMessage());
      throw new IllegalArgumentException(e);
    }

    return val;
  }

  @Override
  public double getCostBasis(String pName, String date) {
    return model.getTotalMoneyInvestedOnCertainDate(date, pName);
  }

  @Override
  public List<List<String>> viewComposition(String pName, String date) {
    return model.viewCompositionOfCurrentPortfolio(pName, date);
  }

  @Override
  public void exitTheProgram() {
    view.exitGracefully();
  }

  @Override
  public void start() {
      //do nothing
  }
}
