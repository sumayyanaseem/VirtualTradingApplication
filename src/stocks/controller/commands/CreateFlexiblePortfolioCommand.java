package stocks.controller.commands;

import java.util.Scanner;

import stocks.controller.ControllerValidations;
import stocks.model.IFlexible;
import stocks.model.Portfolio;
import stocks.view.PortfolioView;

public class CreateFlexiblePortfolioCommand implements Command  {

  private final PortfolioView view;
  private final Scanner input;

  private IFlexible flexiblePortfolioTypeObj;

  private ControllerValidations controllerValidations;

  public CreateFlexiblePortfolioCommand(PortfolioView view, Scanner input,IFlexible flexiblePortfolioTypeObj ){
    this.view = view;
    this.input = input;
    this.flexiblePortfolioTypeObj=flexiblePortfolioTypeObj;
    this.controllerValidations = new ControllerValidations(view);
  }

  @Override
  public void execute() {
    // need to return portfolioname created
    createFlexiblePortfolioForCurrentUser(flexiblePortfolioTypeObj);
  }

  private void createFlexiblePortfolioForCurrentUser(IFlexible portfolio) {
    view.getPortfolioName();
    String portfolioName = input.nextLine();
    if (controllerValidations.validateIfPortfolioExists(portfolioName, portfolio)) {
      createFlexiblePortfolioForCurrentUser(portfolio);
    }
    buyOrSellStocksHelper(portfolio, portfolioName);
  }

  private void buyOrSellStocksHelper(IFlexible portfolio, String portfolioName) {
    view.displayMessageToBuyOrSell();
    String option = input.nextLine();
    if (controllerValidations.validateInputsFromUSer(option)) {
      buyOrSellStocksHelper(portfolio, portfolioName);
    }
    if (option.equals("1")) {
      String companyName = companyHelper1(portfolio);
      String quantity = quantityHelper1();
      String date = dateHelperInFlexiblePortfolio1(companyName);
      String com = commissionHelper1();
      portfolio.validateIfPortfolioAlreadyExists(portfolioName);
      try {
        portfolio.buyStocks(companyName, quantity, date, com,portfolioName);
      } catch (IllegalArgumentException e) {
        view.displayMessage(e.getMessage());
      }
    } else if (option.equals("2")) {
      String companyName = companyHelper1(portfolio);
      String quantity = quantityHelper1();
      String date = dateHelperInFlexiblePortfolio1(companyName);
      String com = commissionHelper1();
      try {
        portfolio.sellStocks(companyName, quantity, date, com,portfolioName);
      } catch (IllegalArgumentException e) {
        view.displayMessage(e.getMessage());
      }
    }
    continueBuyingOrSellingInPortfolio(portfolio, portfolioName);

  }

  protected String dateHelperInFlexiblePortfolio1(String companyName) {
    view.getDate();
    String date = input.nextLine();
    if (controllerValidations.dateHelperInFlexiblePortfolio(date,companyName)) {
      return dateHelperInFlexiblePortfolio1(companyName);
    }
    return date;
  }

  private String commissionHelper1(){
    view.getCommission();
    String com = input.nextLine();
    if (controllerValidations.commissionHelper(com)) {
      return commissionHelper1();
    }
    return com;
  }

  private String quantityHelper1() {
    view.getQuantity();
    String quantity = input.nextLine();
    if (controllerValidations.quantityHelper(quantity)) {
      return quantityHelper1();
    }
    return quantity;
  }

  private String companyHelper1(Portfolio portfolio) {
    view.getCompanyTicker();
    String companyName = input.nextLine();
    if (controllerValidations.companyHelper(portfolio,companyName)) {
      return companyHelper1(portfolio);
    }
    return companyName;
  }

  private void continueBuyingOrSellingInPortfolio(IFlexible portfolio, String portfolioName) {
    view.checkIfUserWantsToContinueUpdatingPortfolio();
    String option = input.nextLine();
    if (controllerValidations.validateInputsFromUSer(option)) {
      continueBuyingOrSellingInPortfolio(portfolio, portfolioName);
    }
    if (option.equals("1")) {
      //1 for continue
      buyOrSellStocksHelper(portfolio, portfolioName);
    } else if (option.equals("2")) {
      portfolio.createPortfolioIfCreatedManually(portfolioName);
    }
  }

}
