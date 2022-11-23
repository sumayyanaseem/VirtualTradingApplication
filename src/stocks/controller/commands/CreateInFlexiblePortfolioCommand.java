package stocks.controller.commands;

import java.util.Scanner;

import stocks.model.Portfolio;
import stocks.view.PortfolioView;

public class CreateInFlexiblePortfolioCommand extends AbstractCommand implements Command{

  private final PortfolioView view;

  private final Scanner input;

  private Portfolio inflexiblePortfolioTypeObj;

  public CreateInFlexiblePortfolioCommand(PortfolioView view,Scanner input, Portfolio inflexiblePortfolioTypeObj ){
    super(view,input);
    this.view = view;
    this.input = input;
    this.inflexiblePortfolioTypeObj=inflexiblePortfolioTypeObj;
  }

  @Override
  public void execute() {
    //need to return portfolioname created.
    createInFlexiblePortfolioForCurrentUser(inflexiblePortfolioTypeObj);
  }

  private void createInFlexiblePortfolioForCurrentUser(Portfolio portfolio) {
    view.getPortfolioName();
    String portfolioName = input.nextLine();
    if (validateIfPortfolioExists(portfolioName, portfolio)) {
      createInFlexiblePortfolioForCurrentUser(portfolio);
    }
    addStocks(portfolio, portfolioName);
  }

  private void addStocks(Portfolio portfolio, String portfolioName) {

    String companyName = companyHelper(portfolio);
    String quantity = quantityHelper();
    portfolio.buyStocks(companyName, quantity, null, portfolioName, null);
    stoppingCondition(portfolio, portfolioName);
  }

  private void stoppingCondition(Portfolio portfolio, String portfolioName) {
    view.askUserIfHeWantsToContinueTradingInCurrentPortfolio();
    String option = input.nextLine();
    if (validateInputsFromUSer(option)) {
      stoppingCondition(portfolio, portfolioName);
    }
    if (option.equals("1")) {
      addStocks(portfolio, portfolioName);
    } else {
      portfolio.createPortfolioIfCreatedManually(portfolioName);
    }

  }




}
