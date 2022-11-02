package stocks.view;

import java.util.List;

public interface PortfolioView {

  void  callToViewToChooseCreateOrView();

  void askUserOnHowToCreatePortfolio();

  void getFilePath();

  void getPortfolioName();

  void getBuyOrSellChoiceFromUser();//Buy or Sell

  void getQuantity();

  void getCompanyTicker();

  void askUserIfHeWantsToContinueTradingInCurrentPortfolio();

  void checkIfUserWantsToExitCompletely();

  void createOrUpdateExistingPortfolio();

  void checkIfUserWantsToViewCompositionOrTotalValue();

  void displayTotalValue(String date, String val, String portfolioName);

  void getDateForValuation();

  void displayComposition(List<List<String>> records);

  void displayErrorMessage(String message);

}
