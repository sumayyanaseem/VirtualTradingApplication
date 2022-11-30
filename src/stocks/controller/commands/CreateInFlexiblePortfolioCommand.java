package stocks.controller.commands;

import java.util.Scanner;

import stocks.controller.ControllerValidations;
import stocks.model.Portfolio;
import stocks.view.PortfolioView;

public class CreateInFlexiblePortfolioCommand  implements Command{

  private final PortfolioView view;

  private final Scanner input;

  private Portfolio inflexiblePortfolioTypeObj;

  private ControllerValidations controllerValidations;

  public CreateInFlexiblePortfolioCommand(PortfolioView view,Scanner input, Portfolio inflexiblePortfolioTypeObj ){
    this.view = view;
    this.input = input;
    this.inflexiblePortfolioTypeObj=inflexiblePortfolioTypeObj;
    this.controllerValidations = new ControllerValidations(view);
  }

  @Override
  public void execute() {
    //need to return portfolioname created.
    createInFlexiblePortfolioForCurrentUser(inflexiblePortfolioTypeObj);
  }

  private void createInFlexiblePortfolioForCurrentUser(Portfolio portfolio) {
    view.getPortfolioName();
    String portfolioName = input.nextLine();
    if (controllerValidations.validateIfPortfolioExists(portfolioName, portfolio)) {
      createInFlexiblePortfolioForCurrentUser(portfolio);
    }
    addStocks(portfolio, portfolioName);
  }

  private void addStocks(Portfolio portfolio, String portfolioName) {

    String companyName = companyHelper1(portfolio);
    String quantity = quantityHelper1();
    portfolio.buyStocks(companyName, quantity, null, null, portfolioName);
    stoppingCondition(portfolio, portfolioName);
  }

  private void stoppingCondition(Portfolio portfolio, String portfolioName) {
    view.askUserIfHeWantsToContinueTradingInCurrentPortfolio();
    String option = input.nextLine();
    if (controllerValidations.validateInputsFromUSer(option)) {
      stoppingCondition(portfolio, portfolioName);
    }
    if (option.equals("1")) {
      addStocks(portfolio, portfolioName);
    } else {
      portfolio.createPortfolioIfCreatedManually(portfolioName);
    }

  }

  private String companyHelper1(Portfolio portfolio) {
    view.getCompanyTicker();
    String companyName = input.nextLine();
    if (controllerValidations.companyHelper(portfolio,companyName)) {
      return companyHelper1(portfolio);
    }
    return companyName;
  }

  private String quantityHelper1() {
    view.getQuantity();
    String quantity = input.nextLine();
    if (controllerValidations.quantityHelper(quantity)) {
      return quantityHelper1();
    }
    return quantity;
  }




}