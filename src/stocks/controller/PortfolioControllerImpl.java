package stocks.controller;

import java.io.File;
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

import stocks.model.PortfolioImplModel;
import stocks.model.PortfolioModel;
import stocks.view.PortfolioView;

public class PortfolioControllerImpl implements PortfolioController {

  private PortfolioModel model;
  private String portfolioName;
  private PortfolioView view;
  private Scanner input;


  public PortfolioControllerImpl(InputStream in, PortfolioView view) {
    this.input = new Scanner(in);
    this.view = view;
    this.portfolioName = "";
  }


  public void start(PortfolioModel model) {
    Objects.requireNonNull(model);
    this.model = model;
    view.callToViewToChooseCreateOrView();
    String option = String.valueOf(input.nextLine());
    if(validateInputsFromUSer(option)){
      start(model);
    }
    switch (option) {
      case "1":
        getCreatePortfolioChoice(model);
        break;
      case "2":
        askUserWhatHeWantsToView();
        break;
    }

  }

  public void startCopy(PortfolioModel model) {
    Objects.requireNonNull(model);
    view.callToViewToChooseCreateOrView();
    String option = String.valueOf(input.nextLine());
    if(validateInputsFromUSer(option)){
      startCopy(model);
    }
    switch (option) {
      case "1":
        startHelper(model.getInstance());
        break;
      case "2":
        //this.model = model;
        askUserWhatHeWantsToView();
        break;
    }

  }

  private void startHelper(PortfolioModel model){
    getCreatePortfolioChoice(model);
  }

  private void askUserWhatHeWantsToView() {
    view.checkIfUserWantsToViewCompositionOrTotalValue();
    String option = String.valueOf(input.nextLine());
    if(validateInputsFromUSer(option)){
      askUserWhatHeWantsToView();
    }
    switch (option) {
      case "1":
        String name = pNameHelper();
        List<List<String>> records = model.viewCompositionOfCurrentPortfolio(name);
        view.displayComposition(records);
        finalExitCondition();
        break;
      case "2":
        String pName = pNameHelper();
        String date = dateHelper();
        String val = String.format("%.2f",model.getTotalValueOfPortfolioOnCertainDate(date, pName));
        view.displayTotalValue(date, val, portfolioName);
        finalExitCondition();
        break;
    }
  }

  private String pNameHelper(){
    view.getPortfolioName();
    String name = input.nextLine();
    if(validateIfPortfolioExists(name)){
      return pNameHelper();
    }
    return name;
  }

  private String dateHelper(){
    view.getDateForValuation();
    String date = input.nextLine();
    if(validateDate(date)){
      return dateHelper();
    }
    return date;
  }

  private void UpdatePortfolio() {
    view.getPortfolioName();
    String portfolioName = input.nextLine();
    buyOrSellStocks(portfolioName);
  }


  private void getCreatePortfolioChoice(PortfolioModel model) {
    view.askUserOnHowToCreatePortfolio();
    String option = String.valueOf(input.nextLine());
    if(validateInputsFromUSer(option)){
      getCreatePortfolioChoice(model);
    }
    getCreatePortfolioChoiceHelper(option,model);

  }

  private void viewHelper(){

  }

  private void getCreatePortfolioChoiceHelper(String option,PortfolioModel model){
    switch (option) {
      case "1":
        view.getFilePath();
        String filePath = input.nextLine();
        try {
          model.createPortfolioUsingFilePath(filePath);
        }
        catch(RuntimeException e){
          view.displayErrorMessage("FilePath Doesn't exist. Try again with correct path.");
          getCreatePortfolioChoiceHelper(option,model);
        }
        finalExitCondition();
        break;
      case "2":
        createNewPortfolioForCurrentUser();
        break;
    }
  }

  private void stoppingCondition(String portfolioName) {
    view.askUserIfHeWantsToContinueTradingInCurrentPortfolio();
    String option = input.nextLine();
    if(validateInputsFromUSer(option)){
      stoppingCondition(portfolioName);
    }
    switch (option) {
      case "1":
        buyOrSellStocks(portfolioName);
        break;
      case "2":
        //create portfolio file here , if it is a manual creation bfr
        model.createPortfolioIfCreatedManually(portfolioName);
        finalExitCondition();
        break;
    }
  }

  private void createNewPortfolioForCurrentUser() {
    view.getPortfolioName();
    String portfolioName = input.nextLine();
    this.portfolioName = portfolioName;
    buyOrSellStocks(portfolioName);
  }

  private void buyOrSellStocks(String portfolioName) {
        view.getCompanyTicker();
        String companyName = input.nextLine();
        String quantity = quantityHelper();
        model.buyStocks(quantity, companyName, portfolioName);
        stoppingCondition(portfolioName);
  }

  private String quantityHelper(){
    view.getQuantity();
    String quantity = input.nextLine();
    if(validateQuantity(quantity)){
      return quantityHelper();
    }
    return quantity;
  }

  private void finalExitCondition() {
    view.checkIfUserWantsToExitCompletely();
    String option = input.nextLine();
    if(validateInputsFromUSer(option)){
      finalExitCondition();
    }
    switch (option) {
      case "1":
        startCopy(this.model);
        break;
      case "2":
        //do nothing
        break;
    }

  }

  private boolean validateInputsFromUSer(String input) {
    if (input.equals("1") || input.equals("2")) {
      //do nothing
    } else {
     view.displayErrorMessage("Invalid input provided.Please provide a valid input (either 1 or 2)");
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
        view.displayErrorMessage("Invalid dateFormat provided.Please provide date in YYYY-MM-DD format only.");
        return true;
      } else {
        String todayDateStr = new SimpleDateFormat(format).format(new Date(System.currentTimeMillis()));
        Date todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse(todayDateStr);
        Date givenDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse(date);
        if(givenDate.compareTo(todayDate)>0)
          view.displayErrorMessage("Future Date provided.Please provide date less then or equal to today");
        return true;
      }

    } catch (IllegalArgumentException | DateTimeParseException | ParseException e) {
      view.displayErrorMessage("Invalid dateFormat provided.Please provide date in YYYY-MM-DD format only.");
      return true;
    }
  }

  private boolean validateQuantity(String quantity) {
    try {
      Integer q = Integer.parseInt(quantity);
      if (q <= 0) {
        view.displayErrorMessage("Invalid quantity provided");
        return true;
      }
    } catch (IllegalArgumentException e) {
      view.displayErrorMessage("Quantity should be always a positive whole number.");
      return true;
    }
    return false;
  }

  public boolean validateIfPortfolioExists(String portfolioName){
    try{
      model.validateIfPortfolioAlreadyExists(portfolioName);
    } catch(IllegalArgumentException e){
      view.displayErrorMessage(e.getMessage());
      return true;
    }
    return false;
  }

  public boolean validateIfCompanyExists(String companyName){
    try{
      model.validateIfCompanyExists(companyName);
    } catch(IllegalArgumentException e){
      view.displayErrorMessage(e.getMessage());
      return true;
    }
    return false;
  }
}