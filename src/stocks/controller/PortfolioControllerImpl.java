package stocks.controller;
import java.util.List;
import java.util.Objects;

import stocks.model.PortfolioImplModel;
import stocks.model.PortfolioModel;
import stocks.view.PortfolioView;

public class PortfolioControllerImpl implements PortfolioController {

  private PortfolioModel model;

  private PortfolioView view;

  private String portfolioName;


  public PortfolioControllerImpl(PortfolioView view) {
    this.view = view;
    this.portfolioName = "";
  }


  public void start(PortfolioModel model) {
    Objects.requireNonNull(model);
    this.model = model;
    String option = view.callToViewToChooseCreateOrView();
    switch (option) {
      case "1":
        createOrUpdatePortfolio();
        break;
      case "2":
        askUserWhatHeWantsToView();
        break;
    }
  }

  private void askUserWhatHeWantsToView() {
    String option = view.checkIfUserWantsToViewCompositionOrTotalValue();
    switch (option) {
      case "1":
        String name = view.getPortfolioNameToViewOrUpdate();
        List<List<String>> records = model.viewCompositionOfCurrentPortfolio(name);
        view.displayComposition(records);
        break;
      case "2":
        String pName = view.getPortfolioNameToViewOrUpdate();
        String date = view.getDateForValuation();
        String val= String.valueOf(model.getTotalValueOfPortfolioOnCertainDate(date,pName));
        view.displayTotalValue(date,val,portfolioName);
        break;
    }
  }

  private void createOrUpdatePortfolio()  {
    String option = view.createOrUpdateExistingPortfolio();
    switch (option) {
      case "1":
        getCreatePortfolioChoice();
        break;
      case "2":
        //UpdatePortfolio();
        break;
    }
  }


  private void UpdatePortfolio()  {
    String portfolioName = view.getPortfolioNameToViewOrUpdate();
    buyOrSellStocks(portfolioName);
  }


  private void getCreatePortfolioChoice() {
    String option = view.askUserOnHowToCreatePortfolio();//using Json/XML file or individual inputs
    switch (option) {
      case "1":
        String filePath = view.getFilePath();
        model.createPortfolioUsingFilePath(filePath);
        finalExitCondition();
        break;
      case "2":
        createNewPortfolioForCurrentUser();
        break;
    }

  }

  private void stoppingCondition(String portfolioName) {
    String option = view.askUserIfHeWantsToContinueTradingInCurrentPortfolio();
    switch (option) {
      case "1":
        buyOrSellStocks(portfolioName);
        break;
      case "2":
        //create portfolio file here , if it is a manual creation bfr
        model.createPortfolioIfCreatedManually(portfolioName);
        finalExitCondition();
        break;
    }
  }

  private void createNewPortfolioForCurrentUser()  {
    String portfolioName = view.getPortfolioNameToCreate();
    this.portfolioName = portfolioName;
    buyOrSellStocks(portfolioName);
  }

  private void buyOrSellStocks(String portfolioName){
    String option = view.getBuyOrSellChoiceFromUser();//Buy or Sell choice
    switch (option) {
      case "1":
        String companyName = view.getCompanyTicker();
        String quantity = view.getQuantity();
        model.buyStocks(quantity, companyName, portfolioName);
        stoppingCondition(portfolioName);
        break;
      case "2":
        //companyName = view.callToViewToAskCompanyTicker();
       // quantity = view.callToViewToAskQuantity();
        // model.sellStocks(quantity, companyName, portfolioName);
       // stoppingCondition(this.portfolioName);
        break;
    }
  }

  private void finalExitCondition()  {
    String option = view.checkIfUserWantsToExitCompletely();

    switch (option) {
      case "1":
        start(new PortfolioImplModel());
        break;
      case "2":
        //do nothing
        break;
    }

  }
}