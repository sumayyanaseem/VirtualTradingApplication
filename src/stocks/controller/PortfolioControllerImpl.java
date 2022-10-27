package stocks.controller;
import java.io.IOException;

import stocks.model.PortfolioModel;
import stocks.view.PortfolioView;

public class PortfolioControllerImpl implements PortfolioController {

  private PortfolioModel model;

  private PortfolioView view;

  private String portfolioName;


  public PortfolioControllerImpl(PortfolioModel model, PortfolioView view) {
    this.model = model;
    this.view = view;
    this.portfolioName = "";
  }


  public void start() throws IOException {
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

  private void askUserWhatHeWantsToView(){
    String option = view.checkIfUserWantsToViewCompositionOrTotalValue();
    switch (option) {
      case "1":
        String name = view.getPortfolioNameToview();
        view.viewCompositionOfCurrentPortfolio(name);
        break;
      case "2":
        String date = view.getDateForValuation();
        String val=model.calculateValuationAsOfDate(date);
        view.displayTotalValue(date,val);
        break;
    }
  }

  private void createOrUpdatePortfolio() throws IOException {
    String option = view.createOrUpdateExistingPortfolio();
    switch (option) {
      case "1":
        getCreatePortfolioChoice();
        break;
      case "2":
        UpdatePortfolio();
        break;
    }
  }


  private void UpdatePortfolio() throws IOException {
    String portfolioName = view.callToViewToAskPortfolioName();
    this.portfolioName = portfolioName;
    BuyOrSellStocks(portfolioName);
  }



  private void getCreatePortfolioChoice() throws IOException {
    String option = view.callToViewOnHowToCreatePortfolio();//using Json/XML file or individual inputs
    switch (option) {
      case "1":
        String filePath = view.callToViewToGetFilePath();
        model.createPortfolioUsingFilePath(filePath);
        finalExitCondition();
        break;
      case "2":
        createNewPortfolioForCurrentUser();
        break;
    }

  }

  private void stoppingCondition(String portfolioName) throws IOException {
    String option = view.askUserIfHeWantsToContinue();
    switch (option) {
      case "1":
        BuyOrSellStocks(portfolioName);
        break;
      case "2":
        finalExitCondition();
        break;
    }
  }

  private void createNewPortfolioForCurrentUser() throws IOException {
    String portfolioName = view.callToViewToAskPortfolioName();
    this.portfolioName = portfolioName;
   // model.createPortfolio(portfolioName);
    BuyOrSellStocks(portfolioName);
  }

  private void BuyOrSellStocks(String portfolioName) throws IOException {
    String option = view.callToViewToChoiceOption();//Buy or Sell choice
    switch (option) {
      case "1":
        String companyName = view.callToViewToAskCompanyTicker();
        String quantity = view.callToViewToAskQuantity();
        model.buyStocks(quantity, companyName, portfolioName);
        stoppingCondition(this.portfolioName);
        break;
      case "2":
        //companyName = view.callToViewToAskCompanyTicker();
       // quantity = view.callToViewToAskQuantity();
        // model.sellStocks(quantity, companyName, portfolioName);
       // stoppingCondition(this.portfolioName);
        break;
    }
  }

  private void finalExitCondition() throws IOException {
    String option = view.checkIfUserWantsToExit();

    switch(option){
      case "1":
        createOrUpdatePortfolio();
        break;
      case "2":
        //do nothing
        break;
    }

  }
}