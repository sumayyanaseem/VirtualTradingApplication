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
<<<<<<< HEAD
                                        double investmentAmount,
                                        double commissionFee, String date) {
=======
                                        double investmentAmount, double commissionFee, String date) {
>>>>>>> 5b65f16c2e6c26b6532cd9d87dc8631cad64b8e9
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
<<<<<<< HEAD
                                 double investmentAmount,
                                 double commissionFee, int investmentInterval,
                                 String dateStart, String dateEnd) {
=======
                                 double investmentAmount, double commissionFee,
                                 int investmentInterval, String dateStart, String dateEnd) {
>>>>>>> 5b65f16c2e6c26b6532cd9d87dc8631cad64b8e9
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
<<<<<<< HEAD
      model.dollarCostStrategy(portfolioName, stockPercentValues,
              investmentAmount, commissionFee, investmentInterval,
              dateStart, dateEnd);
=======
      model.dollarCostStrategy(portfolioName,
              stockPercentValues, investmentAmount,
              commissionFee, investmentInterval, dateStart, dateEnd);
>>>>>>> 5b65f16c2e6c26b6532cd9d87dc8631cad64b8e9
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
