package stocks.controller;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import stocks.model.FlexiblePortfolioImpl;
import stocks.model.IModel;
import stocks.model.InFlexiblePortfolioImpl;
import stocks.model.Portfolio;
import stocks.view.PortfolioView;

/**
 * This class implements the methods of Portfolio Controller.
 */
public class PortfolioControllerImpl implements PortfolioController {

  private IModel model;
  private String portfolioName;
  private final PortfolioView view;
  private final Scanner input;
  private boolean isFlexible;

  private Portfolio flexiblePortfolio;

  private Portfolio inflexiblePortfolio;

  /**
   * Constructs PortfolioControllerImpl with given input stream and view objects.
   *
   * @param in   the input stream.
   * @param view the view object.
   */
  public PortfolioControllerImpl(IModel model, InputStream in, PortfolioView view) {
    this.input = new Scanner(in);
    this.view = view;
    this.portfolioName = "";
    this.isFlexible = false;
    this.model = model;
  }

  @Override
  public void start() {
    view.callToViewToChooseCreateOrView();
    String option = String.valueOf(input.nextLine());
    if (validateInitialInputsFromUser(option)) {
      start();
    }
    switch (option) {
      case "1":
        create();
        break;
      case "2":
        askUserWhatHeWantsToView();
        break;
      case "3":
        loadPortfolio();
        break;
      case "4":
        updatePortfolio();
        break;
      default:
        break;
    }
  }

  private void create() {
    view.createFlexibleOrInFlexiblePortfolio();
    String option = input.nextLine();
    if (validateInputsFromUSer(option)) {
      create();
    }
    if (option.equals("1")) {
      //flexible
      flexiblePortfolio = new FlexiblePortfolioImpl();
      isFlexible = true;
      createFlexiblePortfolioForCurrentUser(flexiblePortfolio);
    } else if (option.equals("2")) {
      //inFlexible
      inflexiblePortfolio = new InFlexiblePortfolioImpl();
      createInFlexiblePortfolioForCurrentUser(inflexiblePortfolio);
    }
  }

  private void createFlexiblePortfolioForCurrentUser(Portfolio portfolio) {
    view.getPortfolioName();
    String portfolioName = input.nextLine();
    if (validateIfPortfolioExists(portfolioName)) {
      createFlexiblePortfolioForCurrentUser(portfolio);
    }
    this.portfolioName = portfolioName;
    buyOrSellStocksHelper(portfolio, portfolioName);
  }


  private void buyOrSellStocksHelper(Portfolio portfolio, String portfolioName) {
    view.displayMessageToBuyOrSell();
    String option = input.nextLine();
    if (validateInputsFromUSer(option)) {
      buyOrSellStocksHelper(portfolio, portfolioName);
    }
    if (option.equals("1")) {
      String companyName = companyHelper();
      String quantity = quantityHelper();
      String date = dateHelper();
      portfolio.buyStocks(companyName, quantity, date, portfolioName);
    } else if (option.equals("2")) {
      String companyName = companyHelper();
      String quantity = quantityHelper();
      String date = dateHelper();//should add more validations
      portfolio.sellStocks(companyName, quantity, date, portfolioName);
    }
    continueBuyingOrSellingInPortfolio(portfolio, portfolioName);

  }

  private void continueBuyingOrSellingInPortfolio(Portfolio portfolio, String portfolioName) {
    view.checkIfUserWantsToContinueBuyingOrSellingPortfolio();
    String option = input.nextLine();
    if (validateInputsFromUSer(option)) {
      continueBuyingOrSellingInPortfolio(portfolio, portfolioName);
    }
    if (option.equals("1")) {
      //1 for continue
      buyOrSellStocksHelper(portfolio, portfolioName);
    } else if (option.equals("2")) {
      //continue further
      finalExitCondition();
    }
  }


  private void createInFlexiblePortfolioForCurrentUser(Portfolio portfolio) {
    view.getPortfolioName();
    String portfolioName = input.nextLine();
    if (validateIfPortfolioExists(portfolioName)) {
      createInFlexiblePortfolioForCurrentUser(portfolio);
    }
    this.portfolioName = portfolioName;
    addStocks(portfolio, portfolioName);
  }

  private void addStocks(Portfolio portfolio, String portfolioName) {

    String companyName = companyHelper();
    String quantity = quantityHelper();
    model.addStocks(quantity, companyName, portfolioName, portfolio);
    stoppingCondition(portfolio,portfolioName);
  }


  private void updatePortfolio() {
    // displayAllAvailablePortfolios();//else display message that there are no portfolios available to update
    view.getPortfolioName();
    String name = input.nextLine();
    if (validateIfPortfolioDoesntExists(name)) {
      updatePortfolio();
    }
    this.portfolioName = name;
    Portfolio portfolio = new FlexiblePortfolioImpl();
    updateStocks(portfolio, portfolioName);
  }

  private void updateStocks(Portfolio portfolio, String portfolioName) {
    view.displayMessageToBuyOrSell();
    String option = input.nextLine();
    if (validateInputsFromUSer(option)) {
      updateStocks(portfolio, portfolioName);
    }
    if(option.equals("1")){
      //1 for buy
      String companyName = companyHelper();
      String quantity = quantityHelper();
      String date = dateHelper();
      portfolio.buyStocks(companyName, quantity, date, portfolioName);
    } else if (option.equals("2")) {
      //2 for sell
      String companyName = companyHelper();
      String quantity = quantityHelper();
      String date = dateHelper();
      portfolio.sellStocks(companyName,quantity,date,portfolioName);
    }
    continueUpdatingPortfolio(portfolio, portfolioName);
  }

  private void continueUpdatingPortfolio(Portfolio portfolio, String portfolioName) {
    view.checkIfUserWantsToContinueUpdatingPortfolio();
    String option = input.nextLine();
    if (validateInputsFromUSer(option)) {
      continueUpdatingPortfolio(portfolio, portfolioName);
    }
    if (option.equals("1")) {
      //1 for continue
      updateStocks(portfolio, portfolioName);
    } else if (option.equals("2")) {
      //continue further
      finalExitCondition();
    }
  }


  private void askUserWhatHeWantsToView() {
    String name = pNameHelper();
    viewHelper(name);
  }

  private void viewHelper(String name) {
    view.checkIfUserWantsToViewCompositionOrTotalValue();
    String option = String.valueOf(input.nextLine());
    if (validateInputsFromUSer(option)) {
      viewHelper(name);
    }
    if (option.equals("1")) {
      List<List<String>> records = model.viewCompositionOfCurrentPortfolio(name);
      view.displayComposition(records);
      viewHelper2(name);
    } else if(option.equals("2")){
      dateNotFoundHelper(name);
      viewHelper2(name);
    } else if(option.equals("3")){

    } else if(option.equals("4")){

    }

  }

  private void dateNotFoundHelper(String name) {
    String date = dateHelper();
    String val = null;
    try {
      val = String.format("%.2f", model.getTotalValueOfPortfolioOnCertainDate(date, name));
    } catch (IllegalArgumentException e) {
      view.displayErrorMessage(e.getMessage());
      dateNotFoundHelper(name);
    }
    if (val != null) {
      view.displayTotalValue(date, val, portfolioName);
    }
  }

  private void viewHelper2(String name) {
    view.askUserIfheWantsTOContinueViewing();
    String option = input.nextLine();
    if (validateInputsFromUSer(option)) {
      viewHelper2(name);
    }
    if (option.equals("1")) {
      viewHelper(name);
    } else {
      finalExitCondition();
    }

  }

  private String pNameHelper() {
    view.getPortfolioName();
    String name = input.nextLine();
    if (validateIfPortfolioDoesntExists(name)) {
      return pNameHelper();
    }
    return name;
  }

  private String dateHelper() {
    view.getDateForValuation();
    String date = input.nextLine();
    if (validateDate(date)) {
      return dateHelper();
    }
    return date;
  }

  private void loadPortfolio() {
    view.getFilePath();
    String filePath = input.nextLine();
    try {
      model.loadPortfolioUsingFilePath(filePath);
    } catch (RuntimeException e) {
      view.displayErrorMessage(e.getMessage());
      loadPortfolio();
    }
    exitFromLoadPortfolio();
  }

  private void exitFromLoadPortfolio() {
    view.callExitFromLoad();
    String option = input.nextLine();
    if (validateInputsFromUSer(option)) {
      exitFromLoadPortfolio();
    }
    if (option.equals("1")) {
      viewHelper("currentInstance");
    } else if (option.equals("2")) {
      finalExitCondition();
    }
  }

  private void stoppingCondition(Portfolio portfolio, String portfolioName) {
    view.askUserIfHeWantsToContinueTradingInCurrentPortfolio();
    String option = input.nextLine();
    if (validateInputsFromUSer(option)) {
      stoppingCondition(portfolio, portfolioName);
    }
    if (option.equals("1")) {
      addStocks(portfolio, portfolioName);
    } else {
      model.createPortfolioIfCreatedManually(portfolioName, portfolio);
      finalExitCondition();
    }

  }


  private String companyHelper() {
    view.getCompanyTicker();
    String companyName = input.nextLine();
    if (validateIfCompanyExists(companyName)) {
      return companyHelper();
    }
    return companyName;
  }

  private String quantityHelper() {
    view.getQuantity();
    String quantity = input.nextLine();
    if (validateQuantity(quantity)) {
      return quantityHelper();
    }
    return quantity;
  }

  private void finalExitCondition() {
    view.checkIfUserWantsToExitCompletely();
    String option = input.nextLine();
    if (validateInputsFromUSer(option)) {
      finalExitCondition();
    }
    if (option.equals("1")) {
      start();
    }

  }

  private boolean validateInputsFromUSer(String input) {
    if (input.equals("1") || input.equals("2")) {
      //do nothing
    } else {
      view.displayErrorMessage("Invalid input provided."
              + "Please provide a valid input (either 1 or 2)");
      return true;
    }
    return false;
  }

  private boolean validateInitialInputsFromUser(String input) {
    if (input.equals("1") || input.equals("2") || input.equals("3")) {
      //do nothing
    } else {
      view.displayErrorMessage("Invalid input provided."
              + "Please provide a valid input (either 1,2 or 3)");
      return true;
    }
    return false;
  }

  private boolean validateDate(String date) {
    String format = "yyyy-MM-dd";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH);
    try {
      LocalDate ld = LocalDate.parse(date, formatter);
      String result = ld.format(formatter);
      if (!result.equals(date)) {
        view.displayErrorMessage("Invalid dateFormat provided."
                + "Please provide date in YYYY-MM-DD format only.");
        return true;
      } else {
        String todayDateStr = new SimpleDateFormat(format).format(
                new Date(System.currentTimeMillis()));
        Date todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse(todayDateStr);
        Date givenDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse(date);
        if (givenDate.compareTo(todayDate) > 0) {
          view.displayErrorMessage("Future Date provided."
                  + "Please provide date less then or equal to today");
          return true;
        }
      }

    } catch (IllegalArgumentException | DateTimeParseException | ParseException e) {
      view.displayErrorMessage("Invalid dateFormat provided."
              + "Please provide date in YYYY-MM-DD format only.");
      return true;
    }
    return false;
  }

  private boolean validateQuantity(String quantity) {
    try {
      long q = Long.parseLong(quantity);
      if (q <= 0 || q > Integer.MAX_VALUE) {
        view.displayErrorMessage("Invalid quantity provided");
        return true;
      }
    } catch (IllegalArgumentException e) {
      view.displayErrorMessage("Quantity should be always a positive whole number.");
      return true;
    }
    return false;
  }

  private boolean validateIfPortfolioExists(String portfolioName) {
    try {
      model.validateIfPortfolioAlreadyExists(portfolioName);
    } catch (IllegalArgumentException e) {
      view.displayErrorMessage(e.getMessage());
      return true;
    }
    return false;
  }

  private boolean validateIfPortfolioDoesntExists(String portfolioName) {
    try {
      model.validateIfPortfolioDoesntExists(portfolioName);
    } catch (IllegalArgumentException e) {
      view.displayErrorMessage(e.getMessage());
      return true;
    }
    return false;
  }

  private boolean validateIfCompanyExists(String companyName) {
    try {
      model.validateIfCompanyExists(companyName);
    } catch (IllegalArgumentException e) {
      view.displayErrorMessage(e.getMessage());
      return true;
    }
    return false;
  }
}