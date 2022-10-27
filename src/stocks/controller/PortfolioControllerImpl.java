package stocks.controller;
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



  public void start() {
    String option = view.callToViewToChooseCreateOrView();
    switch (option) {
      case "1":
        createOrUpdatePortfolio();
        break;
      case "2":
       // String fileName = model.getFileName();
       // view.callToViewTODisplay(fileName);
        break;
    }
  }

  private void createOrUpdatePortfolio(){
    String option = view.createOrUpdateExistingPortfolio();
    switch (option) {
      case "create":
        getCreatePortfolioChoice();
        break;
      case "update":
        UpdatePortfolio();
        break;
    }
  }


  private void UpdatePortfolio(){
    String portfolioName = view.callToViewToAskPortfolioName();
    this.portfolioName = portfolioName;
    BuyOrSellStocks(portfolioName);
  }



  private void getCreatePortfolioChoice() {
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

  private void stoppingCondition(String portfolioName){
    String option = view.askUserIfHeWantsToContinue();
    switch (option) {
      case "yes":
        BuyOrSellStocks(portfolioName);
        break;
      case "No":
        finalExitCondition();
        break;
    }
  }

  private void createNewPortfolioForCurrentUser() {
    String portfolioName = view.callToViewToAskPortfolioName();
    this.portfolioName = portfolioName;
   // model.createPortfolio(portfolioName);
    BuyOrSellStocks(portfolioName);
  }

  private void BuyOrSellStocks(String portfolioName){
    String option = view.callToViewToChoiceOption();//Buy or Sell choice
    switch (option) {
      case "1":
        String quantity = view.callToViewToAskQuantity();
        String companyName = view.callToViewToAskCompanyTicker();
        model.buyStocks(quantity, companyName, portfolioName);
        stoppingCondition(this.portfolioName);
        break;
      case "2":
        quantity = view.callToViewToAskQuantity();
        companyName = view.callToViewToAskCompanyTicker();
        // model.sellStocks(quantity, companyName, portfolioName);
        stoppingCondition(this.portfolioName);
        break;
    }
  }

  private void finalExitCondition(){
    String option = view.checkIfUserWantsToExit();

    switch(option){
      case "yes":
        createOrUpdatePortfolio();
        break;
      case "No":
        //do nothing
        break;
    }

  }
}