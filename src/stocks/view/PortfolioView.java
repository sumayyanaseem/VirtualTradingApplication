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

 String getPortfolioNameToView();

 String getDateForValuation();

 List<List<String>> readFromCSV(String portfolioName);

}