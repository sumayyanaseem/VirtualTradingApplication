package stocks.controller.commands;

import java.util.Scanner;

import stocks.model.IFlexible;
import stocks.view.PortfolioView;

public class CreateFlexiblePortfolioCommand extends AbstractCommand implements Command  {

  private final PortfolioView view;
  private final Scanner input;


  private IFlexible flexiblePortfolioTypeObj;

  public CreateFlexiblePortfolioCommand(PortfolioView view, Scanner input,IFlexible flexiblePortfolioTypeObj ){
    super(view,input);
    this.view = view;
    this.input = input;
    this.flexiblePortfolioTypeObj=flexiblePortfolioTypeObj;
  }

  @Override
  public void execute() {
    // need to return portfolioname created
    createFlexiblePortfolioForCurrentUser(flexiblePortfolioTypeObj);
  }

  private void createFlexiblePortfolioForCurrentUser(IFlexible portfolio) {
    view.getPortfolioName();
    String portfolioName = input.nextLine();
    if (validateIfPortfolioExists(portfolioName, portfolio)) {
      createFlexiblePortfolioForCurrentUser(portfolio);
    }
    buyOrSellStocksHelper(portfolio, portfolioName);
  }

  private void buyOrSellStocksHelper(IFlexible portfolio, String portfolioName) {
    view.displayMessageToBuyOrSell();
    String option = input.nextLine();
    if (validateInputsFromUSer(option)) {
      buyOrSellStocksHelper(portfolio, portfolioName);
    }
    if (option.equals("1")) {
      String companyName = companyHelper(portfolio);
      String quantity = quantityHelper();
      String date = dateHelperInFlexiblePortfolio(companyName);
      String com = commissionHelper();
      portfolio.validateIfPortfolioAlreadyExists(portfolioName);
      try {
        portfolio.buyStocks(companyName, quantity, date, com,portfolioName);
      } catch (IllegalArgumentException e) {
        view.displayErrorMessage(e.getMessage());
      }
    } else if (option.equals("2")) {
      String companyName = companyHelper(portfolio);
      String quantity = quantityHelper();
      String date = dateHelperInFlexiblePortfolio(companyName);
      String com = commissionHelper();
      try {
        portfolio.sellStocks(companyName, quantity, date, com,portfolioName);
      } catch (IllegalArgumentException e) {
        view.displayErrorMessage(e.getMessage());
      }
    }
    continueBuyingOrSellingInPortfolio(portfolio, portfolioName);

  }

  private void continueBuyingOrSellingInPortfolio(IFlexible portfolio, String portfolioName) {
    view.checkIfUserWantsToContinueUpdatingPortfolio();
    String option = input.nextLine();
    if (validateInputsFromUSer(option)) {
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
