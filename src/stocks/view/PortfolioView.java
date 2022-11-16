package stocks.view;

import java.util.List;
import java.util.Map;


/**
 * This interface acts as view for virtual-trading application.
 * It has implemented methods required for displaying messages and outputs to user.
 */
public interface PortfolioView {


  /**
   * displays options to create or view the protgolio.
   */
  void callToViewToChooseCreateOrView();


  /**
   * displays options to user about how to create the portfolio ( manual inputs / load a file ).
   */
  void askUserOnHowToCreatePortfolio();


  /**
   * displays the message to user to enter the file path.
   */
  void getFilePath();


  /**
   * displays the message to user to enter the portfolio name.
   */
  void getPortfolioName();


  /**
   * displays the message to user to ask whether to buy or sell.
   */
  void getBuyOrSellChoiceFromUser();//Buy or Sell


  /**
   * displays the message to user to enter the quantity.
   */
  void getQuantity();


  /**
   * displays the message to user to enter the stock name.
   */
  void getCompanyTicker();


  /**
   * displays the message to user asking if trading in current portfolio needs to be continued.
   */
  void askUserIfHeWantsToContinueTradingInCurrentPortfolio();


  /**
   * displays the message to user asking if he wants to exit completely.
   */
  void checkIfUserWantsToExitCompletely();


  /**
   * displays the message to user asking if he wants create or update a new portfolio.
   */
  void createOrUpdateExistingPortfolio();

  /**
   * displays the message to user asking if he wants view composition or calculate total value.
   */
  void checkIfUserWantsToViewCompositionOrTotalValue();

  /**
   * displays the message to user asking if he wants view composition or calculate total value.
   */
  void displayTotalValue(String date, String val, String portfolioName);


  /**
   * displays the message to user asking to enter date.
   */
  void displayComposition(List<List<String>> records);


  /**
   * displays the given error message.
   */
  void displayErrorMessage(String message);


  /**
   * displays the message to user asking if he wants to exit after loading the file.
   */
  void callExitFromLoad();


  /**
   * displays the message to user asking to enter date.
   */

  void getDate();

  /**
   * displays the message to user asking if he wants to continue view.
   */
  void askUserIfheWantsTOContinueViewing();

  void displayMessageToBuyOrSell();

  void checkIfUserWantsToContinueUpdatingPortfolio();

  void createFlexibleOrInFlexiblePortfolio();

  void displayTheTotalCost(double totalCost, String date, String portfolioName);

  void displayPortfolioPerformance(Map<String, Double> result, String startDate, String endDate, String portfolioName);
}
