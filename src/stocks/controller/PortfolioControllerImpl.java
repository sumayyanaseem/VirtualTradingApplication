package stocks.controller;

import stocks.model.PortfolioModel;
import stocks.view.PortfolioView;

public class PortfolioControllerImpl implements PortfolioController {

  private PortfolioModel model;

  private PortfolioView view;

  public PortfolioControllerImpl(PortfolioModel model, PortfolioView view) {
    this.model = model;
    this.view = view;
  }

  public void start() {
    String option = view.callToViewToChooseCreateOrView();
    switch (option) {
      case "1":
        getCreatePortfolioChoice();
        break;
      case "2":
        //Json json = fetchDetailsFromModelToDisplay();
        //view.callToViewTODisplay(json);
        break;
    }
  }

  private void getCreatePortfolioChoice() {
    String option = view.callToViewOnHowToCreatePortfolio();//using Json/XML file or individual inputs
    switch (option) {
      case "1":
        createPortfolioForCurrentUser();
        break;
      case "2":
        String filePath = view.callToViewToGetFilePath();
        model.createPortfolioUsingFilePath(filePath);
        break;
    }

  }

  private void createPortfolioForCurrentUser() {
    String portfolioName = view.callToViewToAskPortfolioName();
    model.createPortfolio(portfolioName);
    String option = view.callToViewToChoiceOption();
    switch (option) {
      case "1":
        String quantity = view.callToViewToAskQuantity();
        String companyName = view.callToViewToAskCompanyTicker();
        model.buyStocks(quantity, companyName, portfolioName);
        break;
      case "2":
        quantity = view.callToViewToAskQuantity();
        companyName = view.callToViewToAskCompanyTicker();
       // model.sellStocks(quantity, companyName, portfolioName);
        break;
    }
  }
}
