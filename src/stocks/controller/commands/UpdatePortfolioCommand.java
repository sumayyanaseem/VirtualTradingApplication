package stocks.controller.commands;

import java.util.Scanner;

import stocks.customparser.CustomParser;
import stocks.customparser.JsonParserImplementation;
import stocks.model.FlexiblePortfolioImpl;
import stocks.model.IFlexible;
import stocks.view.PortfolioView;

public class UpdatePortfolioCommand extends AbstractCommand implements Command {

  private final PortfolioView view;
  private final Scanner input;

  private IFlexible flexiblePortfolioTypeObj;

  private final CustomParser jsonParserImplementation;
  public UpdatePortfolioCommand(PortfolioView view, Scanner input){
    super(view,input);
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
      String companyName = companyHelper(portfolio);
      String quantity = quantityHelper();
      String date = dateHelperInFlexiblePortfolio(companyName);
      String com = commissionHelper();
      try {
        portfolio.updatePortfolio(companyName, quantity, date, portfolioName, "buy", com);
      } catch (IllegalArgumentException e) {
        view.displayErrorMessage(e.getMessage());
      }
    } else if (option.equals("2")) {
      //2 for sell
      String companyName = companyHelper(portfolio);
      String quantity = quantityHelper();
      String date = dateHelperInFlexiblePortfolio(companyName);
      String com = commissionHelper();
      // add more validations for chronological order for sell dates
      try {
        portfolio.updatePortfolio(companyName, quantity, date, portfolioName, "sell", com);
      } catch (IllegalArgumentException e) {
        view.displayErrorMessage(e.getMessage());
      }
    }
    continueUpdatingPortfolio(portfolio, portfolioName);
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
