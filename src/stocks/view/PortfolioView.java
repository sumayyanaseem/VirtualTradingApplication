package stocks.view;

import java.util.List;

public interface PortfolioView {

 String  callToViewToChooseCreateOrView();

 String callToViewOnHowToCreatePortfolio();

 String callToViewToGetFilePath();

 String callToViewToAskPortfolioName();

 String callToViewToChoiceOption();//Buy or Sell

 String callToViewToAskQuantity();

 String callToViewToAskCompanyTicker();

 String askUserIfHeWantsToContinueTradingInCurrentPortfolio();

 String checkIfUserWantsToExitCompletely();

 String createOrUpdateExistingPortfolio();

 String checkIfUserWantsToViewCompositionOrTotalValue();

 void displayTotalValue(String date, String val, String portfolioName);

 String getDateForValuation();

 String getPortfolioNameToView();

 void displayComposition(List<List<String>> records);

}