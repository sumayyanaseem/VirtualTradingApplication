package stocks.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import stocks.customParser.JsonParserImplementation;
import stocks.model.CompanyTickerSymbol;
import stocks.model.FlexiblePortfolioImpl;
import stocks.model.IModel;
import stocks.model.InFlexiblePortfolioImpl;
import stocks.model.Portfolio;
import stocks.view.PortfolioView;

/**
 * This class implements the methods of Portfolio Controller.
 */
public class PortfolioControllerImpl implements PortfolioController {
  private final IModel model;
  private String portfolioName;
  private final PortfolioView view;
  private final Scanner input;

  private Portfolio inflexiblePortfolioTypeObj;

  private Portfolio flexiblePortfolioTypeObj;

  private static final String flexibleType = "flexible";

  private static final String inflexibleType = "inflexible";

  private final JsonParserImplementation jsonParserImplementation;

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
    this.model = model;
    this.jsonParserImplementation = new JsonParserImplementation();
    this.inflexiblePortfolioTypeObj = new InFlexiblePortfolioImpl();
    this.flexiblePortfolioTypeObj = new FlexiblePortfolioImpl();
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
      flexiblePortfolioTypeObj = new FlexiblePortfolioImpl();
      //isFlexible = true;
      createFlexiblePortfolioForCurrentUser(flexiblePortfolioTypeObj);
    } else if (option.equals("2")) {
      //inFlexible
      inflexiblePortfolioTypeObj = new InFlexiblePortfolioImpl();
      createInFlexiblePortfolioForCurrentUser(inflexiblePortfolioTypeObj);
    }
  }

  private void createFlexiblePortfolioForCurrentUser(Portfolio portfolio) {
    view.getPortfolioName();
    String portfolioName = input.nextLine();
    if (validateIfPortfolioExists(portfolioName,portfolio)) {
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
      String companyName = companyHelper(portfolio);
      String quantity = quantityHelper();
      String date = dateHelperInFlexiblePortfolio(companyName);
      model.validateIfPortfolioAlreadyExists(portfolioName,portfolio);
      try {
        model.buyStocks(companyName, quantity, date, portfolioName, portfolio);
      } catch(IllegalArgumentException e){
        view.displayErrorMessage(e.getMessage());
      }
    } else if (option.equals("2")) {
      String companyName = companyHelper(portfolio);
      String quantity = quantityHelper();
      String date = dateHelperInFlexiblePortfolio(companyName);//should add more validations for chronological order
      try {
        model.sellStocks(companyName, quantity, date, portfolioName, portfolio);
      } catch(IllegalArgumentException e){
        view.displayErrorMessage(e.getMessage());
      }
    }
    continueBuyingOrSellingInPortfolio(portfolio, portfolioName);

  }

  private void continueBuyingOrSellingInPortfolio(Portfolio portfolio, String portfolioName) {
    view.checkIfUserWantsToContinueUpdatingPortfolio();
    String option = input.nextLine();
    if (validateInputsFromUSer(option)) {
      continueBuyingOrSellingInPortfolio(portfolio, portfolioName);
    }
    if (option.equals("1")) {
      //1 for continue
      buyOrSellStocksHelper(portfolio, portfolioName);
    } else if (option.equals("2")) {
      //continue further and create file here
      model.createPortfolioIfCreatedManually(portfolioName, portfolio);
      finalExitCondition();
    }
  }


  private void createInFlexiblePortfolioForCurrentUser(Portfolio portfolio) {
    view.getPortfolioName();
    String portfolioName = input.nextLine();
    if (validateIfPortfolioExists(portfolioName,portfolio)) {
      createInFlexiblePortfolioForCurrentUser(portfolio);
    }
    this.portfolioName = portfolioName;
    addStocks(portfolio, portfolioName);
  }

  private void addStocks(Portfolio portfolio, String portfolioName) {

    String companyName = companyHelper(portfolio);
    String quantity = quantityHelper();
    model.buyStocks(quantity, companyName, null,portfolioName, portfolio);
    stoppingCondition(portfolio, portfolioName);
  }


  private void updatePortfolio() {
    // displayAllAvailablePortfolios();//else display message that there are no portfolios available to update
    flexiblePortfolioTypeObj = new FlexiblePortfolioImpl();
    view.getPortfolioName();
    String name = input.nextLine();
    if (validateIfPortfolioDoesntExists(name)) {
      updatePortfolio();
    }
    this.portfolioName = name;
    updateStocks(flexiblePortfolioTypeObj, portfolioName);
  }

  private void updateStocks(Portfolio portfolio, String portfolioName) {
    view.displayMessageToBuyOrSell();
    String option = input.nextLine();
    if (validateInputsFromUSer(option)) {
      updateStocks(portfolio, portfolioName);
    }
    if (option.equals("1")) {
      //1 for buy
      String companyName = companyHelper(portfolio);
      String quantity = quantityHelper();
      String date = dateHelperInFlexiblePortfolio(companyName);
      try {
        model.updatePortfolio(companyName, quantity, date, portfolioName, portfolio, "buy");
      } catch(IllegalArgumentException e){
        view.displayErrorMessage(e.getMessage());
      }
    } else if (option.equals("2")) {
      //2 for sell
      String companyName = companyHelper(portfolio);
      String quantity = quantityHelper();
      String date = dateHelperInFlexiblePortfolio(companyName); // add more validations for chronological order for sell dates
      try {
        model.updatePortfolio(companyName, quantity, date, portfolioName, portfolio, "sell");
      } catch(IllegalArgumentException e){
        view.displayErrorMessage(e.getMessage());
      }
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
      model.createPortfolioIfCreatedManually(portfolioName, portfolio);
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

  private void askUserWhatHeWantsToView() {
    String name = pNameHelper();
    viewHelper(name);
  }

  private void viewHelper(String name) {
    view.checkIfUserWantsToViewCompositionOrTotalValue();
    String option = String.valueOf(input.nextLine());
    if (validateInitialInputsFromUser(option)) {
      viewHelper(name);
    }
    String type = jsonParserImplementation.getTypeOfFile(name);
    Portfolio portfolio = null;
    if (type.equals(flexibleType)) {
      portfolio = flexiblePortfolioTypeObj;
    } else if (type.equals(inflexibleType)) {
      portfolio = inflexiblePortfolioTypeObj;
    }
    if(option.equals("1")) {
      //if its flexible get date as well and pass it model
      List<List<String>> records;
      if(type.equals(flexibleType)){
        String date = dateHelper();
         records = model.viewCompositionOfCurrentPortfolio(name, date, portfolio);
      } else {
        records = model.viewCompositionOfCurrentPortfolio(name, null, portfolio);
      }
      view.displayComposition(records);
    } else if (option.equals("2")) {
      dateNotFoundHelper(name, portfolio);
    } else if (option.equals("3")) {
      // implement total-cost basis functionality
      String date = dateHelper();
      double totalCost = model.getTotalMoneyInvestedOnCertainDate(date, name, portfolio);
      view.displayTheTotalCost(totalCost, date, name);
    } else if (option.equals("4")) {
      // some part has been implemented. link it and test.

      String startDate = dateHelper();
      String endDate = dateHelper();//display the different message in view and add more validations for date(end>start date).
      Date start;
      Date end;
      try {
        start = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse(startDate);
        end = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse(endDate);
        if(end.compareTo(start)<0) {
          //need to recur until correct dates are entered.
          view.displayErrorMessage("End date is less than start date. Please enter valid dates");
        }


      }
      catch(Exception e){
        view.displayErrorMessage(e.getMessage());
      }
      Map<String, Double> result = model.getPortfolioPerformanceOvertime(startDate, endDate, name, portfolio);
      view.displayPortfolioPerformance(result, startDate, endDate, name);
    }
    viewHelper2(name);

  }

  private void dateNotFoundHelper(String name, Portfolio portfolio) {
    String date = dateHelper();
    String val = null;
    try {
      val = String.format("%.2f", model.getTotalValueOfPortfolioOnCertainDate(date, name, portfolio));
    } catch (IllegalArgumentException e) {
      view.displayErrorMessage(e.getMessage());
      dateNotFoundHelper(name, portfolio);
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


  private String dateHelperInFlexiblePortfolio(String companyName){
    view.getDateForValuation();
    String date = input.nextLine();
    if (validateDate(date) || validateDateToCheckIfBeforeIPO(date,companyName)) {
      return dateHelper();
    }
    return date;
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
      Object obj = new JSONParser().parse(new FileReader(filePath));
      JSONObject jsonObject = (JSONObject) obj;
      String type = (String) jsonObject.get("type");

      Portfolio portfolioTypeObj;
      if (type.equals(flexibleType)) {
        //flexible
        portfolioTypeObj = flexiblePortfolioTypeObj;
        model.loadPortfolioUsingFilePath(filePath, portfolioTypeObj);
      } else if (type.equals(inflexibleType)) {
        //inFlexible
        portfolioTypeObj = inflexiblePortfolioTypeObj;
        model.loadPortfolioUsingFilePath(filePath, portfolioTypeObj);
      }
    } catch (RuntimeException e) {
      view.displayErrorMessage(e.getMessage());
      loadPortfolio();
    } catch (IOException | org.json.simple.parser.ParseException e) {
      throw new RuntimeException(e);
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


  private String companyHelper(Portfolio portfolio) {
    view.getCompanyTicker();
    String companyName = input.nextLine();
    if (validateIfCompanyExists(companyName,portfolio)) {
      return companyHelper(portfolio);
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
    if (input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4")) {
      //do nothing
    } else {
      view.displayErrorMessage("Invalid input provided."
              + "Please provide a valid input (either 1,2,3 or 4)");
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

  private boolean validateIfPortfolioExists(String portfolioName,Portfolio portfolio) {
    try {
      model.validateIfPortfolioAlreadyExists(portfolioName,portfolio);
    } catch (IllegalArgumentException e) {
      view.displayErrorMessage(e.getMessage());
      return true;
    }
    return false;
  }

  private boolean validateIfPortfolioDoesntExists(String portfolioName) {
    try {
      if (portfolioName == null) {
        throw new IllegalArgumentException("Invalid portfolioName provided");
      }
      String path = "userPortfolios/" + portfolioName + "_output" + ".json";
      File f = new File(path);
      if (!f.isFile() || !f.exists()) {
        throw new IllegalArgumentException("Given portfolio doesnt exist."
                + "Please provide valid portfolioName.");
      }
    } catch (IllegalArgumentException e) {
      view.displayErrorMessage(e.getMessage());
      return true;
    }
    return false;
  }

  private boolean validateIfCompanyExists(String companyName,Portfolio portfolio) {
    try {
      model.validateIfCompanyExists(companyName,portfolio);
    } catch (IllegalArgumentException e) {
      view.displayErrorMessage(e.getMessage());
      return true;
    }
    return false;
  }

  private boolean validateDateToCheckIfBeforeIPO(String date, String companyName) {
    for (CompanyTickerSymbol companyTickerSymbol : CompanyTickerSymbol.values()) {
      if (companyTickerSymbol.name().equalsIgnoreCase(companyName)) {
        try {
          Date givenDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                  .parse(date);
          Date ipoDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                  .parse(companyTickerSymbol.getEndDate());

          if(givenDate.compareTo(ipoDate)<0){
            view.displayErrorMessage("Given date is before IPO Date.Please provide a valid date.");
            return true;
          }
          break;
        } catch(ParseException e ){
          view.displayErrorMessage(e.getMessage());
          return true;
        }
      }
    }
    return false;
  }

}