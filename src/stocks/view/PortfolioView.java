package stocks.view;

public interface PortfolioView {

 String  callToViewToChooseCreateOrView();

// void callToViewTODisplay(Json Json);

 String callToViewOnHowToCreatePortfolio();

 String callToViewToGetFilePath();

 String callToViewToAskPortfolioName();

 String callToViewToChoiceOption();//Buy or Sell

 String callToViewToAskQuantity();

 String callToViewToAskCompanyTicker();

 String askUserIfHeWantsToContinue();

 String checkIfUserWantsToExit();

 String createOrUpdateExistingPortfolio();

 String checkIfUserWantsToViewCompositionOrTotalValue();

}