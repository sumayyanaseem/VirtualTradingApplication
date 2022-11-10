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
import java.util.Objects;
import java.util.Scanner;

import stocks.model.FlexiblePortfolioImpl;
import stocks.model.PortfolioImplModel;
import stocks.model.PortfolioModel;
import stocks.view.PortfolioView;

/**
 * This class implements the methods of Portfolio Controller.
 */
public class PortfolioControllerImpl implements PortfolioController {

  private PortfolioModel model;
  private String portfolioName;
  private final PortfolioView view;
  private final Scanner input;
  private boolean isFlexible;


  /**
   * Constructs PortfolioControllerImpl with given input stream and view objects.
   *
   * @param in   the input stream.
   * @param view the view object.
   */
  public PortfolioControllerImpl(InputStream in, PortfolioView view) {
    this.input = new Scanner(in);
    this.view = view;
    this.portfolioName = "";
    this.isFlexible = false;
  }

  @Override
  public void start(PortfolioModel model) {
    Objects.requireNonNull(model);
    this.model = model;
    view.callToViewToChooseCreateOrView();
    String option = String.valueOf(input.nextLine());
    if (validateInitialInputsFromUser(option)) {
      start(model);
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

  private void startCopy(PortfolioModel model) {
    Objects.requireNonNull(model);
    view.callToViewToChooseCreateOrView();
    String option = String.valueOf(input.nextLine());
    if (validateInitialInputsFromUser(option)) {
      startCopy(model);
    }
    switch (option) {
      case "1":
        createNewPortfolioForCurrentUser(model.getInstance());
        break;
      case "2":
        askUserWhatHeWantsToView();
        break;
      case "3":
        loadPortfolio();
        break;
      case "4":
        PortfolioModel model = new FlexiblePortfolioImpl();
        updatePortfolio(model);
        break;
      default:
        break;
    }
  }

  private void create(){
    view.createFlexibleOrInFlexiblePortfolio();
    String option = input.nextLine();
    if(validateInputsFromUSer(option)){
      create();
    }
    if(option.equals("1")){
      //flexible
      PortfolioModel model = new FlexiblePortfolioImpl();
      createNewPortfolioForCurrentUser(model.getInstance());
    } else if(option.equals("2")){
      //inFlexible
      PortfolioModel model = new PortfolioImplModel();
      createNewPortfolioForCurrentUser(model.getInstance());
    }
  }

  private void updatePortfolio(PortfolioModel model){
   // displayAllAvailablePortfolios();//else display message that there are no portfolios available to update
    view.getPortfolioName();
    String name = input.nextLine();
    if (validateIfPortfolioDoesntExists(name)) {
      updatePortfolio(model);
    }
    this.portfolioName = name;
    if(isFlexible) {
      updateStocks(portfolioName,model);
    }
  }

  private void updateStocks(String portfolioName){
    view.displayMessageToBuyOrSell();
    String option = input.nextLine();
    if(validateInputsFromUSer(option)){
      updateStocks(portfolioName);
    }
    if(option.equals("1")){
      //1 for buy
    } else if(option.equals("2")){
      //2 for sell
    }
    continueUpdatingPortfolio(portfolioName);
  }

  private void continueUpdatingPortfolio(String portfolioName){
    view.checkIfUserWantsToContinueUpdatingPortfolio();
    String option = input.nextLine();
    if(validateInputsFromUSer(option)){
      continueUpdatingPortfolio(portfolioName);
    }
    if(option.equals("1")){
      //1 for continue
      updateStocks(portfolioName);
    } else if(option.equals("2")){
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
    } else {
      dateNotFoundHelper(name);
      viewHelper2(name);
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
      model.createPortfolioUsingFilePath(filePath);
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

  private void stoppingCondition(String portfolioName) {
    view.askUserIfHeWantsToContinueTradingInCurrentPortfolio();
    String option = input.nextLine();
    if (validateInputsFromUSer(option)) {
      stoppingCondition(portfolioName);
    }
    if (option.equals("1")) {
      buyOrSellStocks(portfolioName);
    } else {
      model.createPortfolioIfCreatedManually(portfolioName);
      finalExitCondition();
    }

  }

  private void createNewPortfolioForCurrentUser(PortfolioModel model) {
    view.getPortfolioName();
    String portfolioName = input.nextLine();
    if (validateIfPortfolioExists(portfolioName)) {
      createNewPortfolioForCurrentUser(model);
    }
    this.portfolioName = portfolioName;
    this.model = model;
    buyOrSellStocks(portfolioName);
  }

  private void buyOrSellStocks(String portfolioName) {

    String companyName = companyHelper();
    String quantity = quantityHelper();
    model.buyStocks(quantity, companyName, portfolioName);
    stoppingCondition(portfolioName);
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
      startCopy(this.model);
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