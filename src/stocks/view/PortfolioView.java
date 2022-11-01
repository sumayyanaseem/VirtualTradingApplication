package stocks.view;

import java.util.List;

public interface PortfolioView {

  String callToViewToChooseCreateOrView();

  String askUserOnHowToCreatePortfolio();

  String getFilePath();

  String getPortfolioNameToCreate();

  String getBuyOrSellChoiceFromUser();

  String getQuantity();

  String getCompanyTicker();

  String askUserIfHeWantsToContinueTradingInCurrentPortfolio();

  String checkIfUserWantsToExitCompletely();

  String createOrUpdateExistingPortfolio();

  String checkIfUserWantsToViewCompositionOrTotalValue();

  void displayTotalValue(String date, String val, String portfolioName);

  String getDateForValuation();

  String getPortfolioNameToViewOrUpdate();

  void displayComposition(List<List<String>> records);
}