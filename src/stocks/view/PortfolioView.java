package stocks.view;

import java.util.List;
import java.util.Map;


/**
 * This interface acts as view for virtual-trading application.
 * It has implemented methods required for displaying messages and outputs to user.
 */
public interface PortfolioView extends IViewInterface {


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
   * @param date the date as of which the total value is needed.
   * @param val total value.
   * @param portfolioName name of the portfolio.
   */
  void displayTotalValue(String date, String val, String portfolioName);


  /**
   * displays the message to user asking to enter date.
   * @param records list of stock records to be displayed in composition.
   */
  void displayComposition(List<List<String>> records);


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

  /**
   * Display the message to check if its buy or sell.
   */
  void displayMessageToBuyOrSell();

  /**
   * Display the message to user to continue updating portfolio.
   */
  void checkIfUserWantsToContinueUpdatingPortfolio();

  /**
   * dispplay a message to check if its flexible or inflexible creation.
   */
  void createFlexibleOrInFlexiblePortfolio();

  /**
   * Display the total cost invested.
   *
   * @param totalCost     total investment.
   * @param date          date required
   * @param portfolioName name of the portfolio.
   */
  void displayTheTotalCost(double totalCost, String date, String portfolioName);

  /**
   * Display the portfolio performance over the time.
   *
   * @param result        result from model.
   * @param startDate     start point.
   * @param endDate       end point.
   * @param portfolioName name of the portfolio.
   */
  void displayPortfolioPerformance(Map<String,
          Double> result, String startDate,
                                   String endDate, String portfolioName);

  /**
   * get commission for the transaction.
   */
  void getCommission();
}
