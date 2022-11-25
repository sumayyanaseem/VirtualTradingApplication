package stocks.controller.commands;

import java.util.Scanner;

import stocks.customparser.CustomParser;
import stocks.customparser.JsonParserImplementation;
import stocks.model.FlexiblePortfolioImpl;
import stocks.model.IFlexible;
import stocks.model.Portfolio;
import stocks.view.PortfolioView;

public class UpdatePortfolioCommand extends ControllerValidations implements Command {

  private final PortfolioView view;
  private final Scanner input;

  private IFlexible flexiblePortfolioTypeObj;

  private final CustomParser jsonParserImplementation;
  public UpdatePortfolioCommand(PortfolioView view, Scanner input){
    super(view);
    this.view = view;
    this.input = input;
    this.jsonParserImplementation = new JsonParserImplementation();
  }

  @Override
  public void execute() {
    updatePortfolio();
  }

  private void updatePortfolio() {
    view.getPortfolioName();
    String name = input.nextLine();
    if (validateIfPortfolioDoesntExists(name)) {
      updatePortfolio();
    }
    String type = jsonParserImplementation.getTypeOfFile(name);
    if (!type.equals("flexible")) {
      view.displayErrorMessage("Can not update an inflexible portfolio");
      throw new IllegalArgumentException("start again");
    }
    flexiblePortfolioTypeObj = new FlexiblePortfolioImpl();
    updateStocks(flexiblePortfolioTypeObj, name);
  }

  private void updateStocks(IFlexible portfolio, String portfolioName) {
    view.displayMessageToBuyOrSell();
    String option = input.nextLine();
    if (validateInputsFromUSer(option)) {
      updateStocks(portfolio, portfolioName);
    }
    if (option.equals("1")) {

      //1 for buy
      String companyName = companyHelper1(portfolio);
      String quantity = quantityHelper1();
      String date = dateHelperInFlexiblePortfolio1(companyName);
      String com = commissionHelper1();
      try {
        portfolio.updatePortfolio(companyName, quantity, date, portfolioName, "buy", com);
      } catch (IllegalArgumentException e) {
        view.displayErrorMessage(e.getMessage());
      }
    } else if (option.equals("2")) {
      //2 for sell
      String companyName = companyHelper1(portfolio);
      String quantity = quantityHelper1();
      String date = dateHelperInFlexiblePortfolio1(companyName);
      String com = commissionHelper1();
      // add more validations for chronological order for sell dates
      try {
        portfolio.updatePortfolio(companyName, quantity, date, portfolioName, "sell", com);
      } catch (IllegalArgumentException e) {
        view.displayErrorMessage(e.getMessage());
      }
    }
    continueUpdatingPortfolio(portfolio, portfolioName);
  }

  private String quantityHelper1() {
    view.getQuantity();
    String quantity = input.nextLine();
    if (quantityHelper(quantity)) {
      return quantityHelper1();
    }
    return quantity;
  }


  private String commissionHelper1(){
    view.getCommission();
    String com = input.nextLine();
    if (commissionHelper(com)) {
      return commissionHelper1();
    }
    return com;
  }

  private String companyHelper1(Portfolio portfolio) {
    view.getCompanyTicker();
    String companyName = input.nextLine();
    if (companyHelper(portfolio,companyName)) {
      return companyHelper1(portfolio);
    }
    return companyName;
  }

  protected String dateHelperInFlexiblePortfolio1(String companyName) {
    view.getDate();
    String date = input.nextLine();
    if (dateHelperInFlexiblePortfolio(date,companyName)) {
      return dateHelperInFlexiblePortfolio1(companyName);
    }
    return date;
  }


  private void continueUpdatingPortfolio(IFlexible portfolio, String portfolioName) {
    view.checkIfUserWantsToContinueUpdatingPortfolio();
    String option = input.nextLine();
    if (validateInputsFromUSer(option)) {
      continueUpdatingPortfolio(portfolio, portfolioName);
    }
    if (option.equals("1")) {
      //1 for continue
      updateStocks(portfolio, portfolioName);
    } else if (option.equals("2")) {
      //continue further
      portfolio.createPortfolioIfCreatedManually(portfolioName);
    }
  }


}
