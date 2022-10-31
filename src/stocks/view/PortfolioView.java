package stocks.view;

import java.util.List;

public interface PortfolioView {

  String callToViewToChooseCreateOrView() throws IllegalArgumentException;

  String callToViewOnHowToCreatePortfolio() throws IllegalArgumentException;

  String callToViewToGetFilePath();

  String callToViewToAskPortfolioName();

  String callToViewToChoiceOption() throws IllegalArgumentException;

  String callToViewToAskQuantity() throws IllegalArgumentException;

  String callToViewToAskCompanyTicker() throws IllegalArgumentException;

  String askUserIfHeWantsToContinueTradingInCurrentPortfolio() throws IllegalArgumentException;

  String checkIfUserWantsToExitCompletely() throws IllegalArgumentException;

  String createOrUpdateExistingPortfolio() throws IllegalArgumentException;

  String checkIfUserWantsToViewCompositionOrTotalValue() throws IllegalArgumentException;

  void displayTotalValue(String date, String val, String portfolioName);

  String getDateForValuation() throws IllegalArgumentException;

  String getPortfolioNameToView() throws IllegalArgumentException;

  void displayComposition(List<List<String>> records);

  String askForPortfolioNameToGetValuation() throws IllegalArgumentException;
}