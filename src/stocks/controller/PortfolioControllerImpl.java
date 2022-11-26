package stocks.controller;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import stocks.controller.commands.ControllerValidations;
import stocks.controller.commands.Command;
import stocks.controller.commands.CreateFlexiblePortfolioCommand;
import stocks.controller.commands.CreateInFlexiblePortfolioCommand;
import stocks.controller.commands.LoadPortfolioCommand;
import stocks.controller.commands.UpdatePortfolioCommand;
import stocks.customparser.CustomParser;
import stocks.customparser.JsonParserImplementation;
import stocks.model.FlexiblePortfolioImpl;
import stocks.model.IFlexible;
import stocks.model.InFlexiblePortfolioImpl;
import stocks.model.Portfolio;
import stocks.view.IViewInterface;
import stocks.view.PortfolioView;

/**
 * This class implements the methods of Portfolio Controller.
 */
public class PortfolioControllerImpl extends ControllerValidations implements PortfolioController {
  private String portfolioName;
  private final PortfolioView view;
  private final Scanner input;

  private Portfolio inflexiblePortfolioTypeObj;

  private IFlexible flexiblePortfolioTypeObj;

  private final CustomParser jsonParserImplementation;

  private static final String flexibleType = "flexible";

  private static final String inflexibleType = "inflexible";

  /**
   * Constructs PortfolioControllerImpl with given input stream and view objects.
   *
   *
   * @param in    the input stream.
   * @param view  the view object.
   */
  public PortfolioControllerImpl(InputStream in, IViewInterface view) {
    super((PortfolioView) view);
    this.view = (PortfolioView) view;
    this.input=new Scanner(in);
    this.portfolioName = "";
    this.inflexiblePortfolioTypeObj = new InFlexiblePortfolioImpl();
    this.flexiblePortfolioTypeObj = new FlexiblePortfolioImpl();
    this.jsonParserImplementation = new JsonParserImplementation();
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


  private void create(){
    view.createFlexibleOrInFlexiblePortfolio();
    String option = input.nextLine();
    if (validateInputsFromUSer(option)) {
      create();
    }
    if (option.equals("1")) {
      //flexible
      flexiblePortfolioTypeObj = new FlexiblePortfolioImpl();
      Command cmd = new CreateFlexiblePortfolioCommand(view,input,flexiblePortfolioTypeObj);
      cmd.execute();
      finalExitCondition();
    } else if (option.equals("2")) {
      //inFlexible
      inflexiblePortfolioTypeObj = new InFlexiblePortfolioImpl();
      Command cmd = new CreateInFlexiblePortfolioCommand(view,input,inflexiblePortfolioTypeObj);
      cmd.execute();
      finalExitCondition();
    }
  }

  private void updatePortfolio(){
    try {
      Command cmd = new UpdatePortfolioCommand(view, input);
      cmd.execute();
    } catch(IllegalArgumentException e){
      if(e.getMessage().equals("start again")){
        start();
      }
    }
    finalExitCondition();
  }

  private void viewHelper2(String name) {
    view.askUserIfheWantsTOContinueViewing();
    String option = input.nextLine();
    if (validateInputsFromUSer(option)) {
      viewHelper2(name);
    }
    if (option.equals("1")) {
      //call to view command again here
      viewHelper(name);
    } else {
      finalExitCondition();
    }

  }

  private void viewHelperForCurrentInstance(String name, String filePath) {
    view.checkIfUserWantsToViewCompositionOrTotalValue();

    String option = String.valueOf(input.nextLine());
    if (validateInitialInputsFromUser(option)) {
      viewHelperForCurrentInstance(name, filePath);
    }
    String type = jsonParserImplementation.getTypeOfLoadedFile(filePath);

    helperForViewHelper(type, option, name, filePath);

  }


  private void viewHelper2ForCurrentInstance(String name, String filePath) {
    view.askUserIfheWantsTOContinueViewing();
    String option = input.nextLine();
    if (validateInputsFromUSer(option)) {
      viewHelper2ForCurrentInstance(name, filePath);
    }
    if (option.equals("1")) {
      viewHelperForCurrentInstance(name, filePath);
    } else {
      finalExitCondition();
    }
  }

  private void loadPortfolio(){
    view.getFilePath();
    String filePath = input.nextLine();
    try {
      Command cmd = new LoadPortfolioCommand(view, input, inflexiblePortfolioTypeObj, flexiblePortfolioTypeObj, filePath);
      cmd.execute();
    } catch(IllegalArgumentException e){
      if(e.getMessage().equals("start again")){
        start();
      }
    }
    exitFromLoadPortfolio(filePath);
  }

  private void exitFromLoadPortfolio(String filePath) {
    view.callExitFromLoad();
    String option = input.nextLine();
    if (validateInputsFromUSerAfterLoad(option)) {
      exitFromLoadPortfolio(filePath);
    }
    if (option.equals("1")) {
      viewHelperForCurrentInstance("currentInstance", filePath);
    } else if (option.equals("2")) {
      finalExitCondition();
    } else if (option.equals("3")) {
      updatePortfolioForCurrentInstance(filePath);
    }
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

  private void updateStocksForCurrentInstance(
          String path, IFlexible portfolio, String portfolioName) {
    view.displayMessageToBuyOrSell();
    String option = input.nextLine();
    if (validateInputsFromUSer(option)) {
      updateStocksForCurrentInstance(path, portfolio, portfolioName);
    }
    if (option.equals("1")) {
      //1 for buy
      String companyName = companyHelper1(portfolio);
      String quantity = quantityHelper1();
      String date = dateHelperInFlexiblePortfolio1(companyName);
      String com = commissionHelper1();
      try {
        portfolio.updatePortfolioUsingFilePath(
                path, companyName, quantity,
                date, portfolioName, "buy", com);
      } catch (IllegalArgumentException e) {
        view.displayErrorMessage(e.getMessage());
      }
    } else if (option.equals("2")) {
      //2 for sell
      String companyName = companyHelper1(portfolio);
      String quantity = quantityHelper1();
      String date = dateHelperInFlexiblePortfolio1(companyName);
      String com = commissionHelper1();
      try {
        portfolio.updatePortfolioUsingFilePath(
                path, companyName, quantity, date,
                portfolioName, "sell", com);
      } catch (IllegalArgumentException e) {
        view.displayErrorMessage(e.getMessage());
      }
    }
    continueUpdatingPortfolioForCurrentInstance(path, portfolio, portfolioName);
  }

  private String commissionHelper1(){
    view.getCommission();
    String com = input.nextLine();
    if (commissionHelper(com)) {
      return commissionHelper1();
    }
    return com;
  }

  private String quantityHelper1() {
    view.getQuantity();
    String quantity = input.nextLine();
    if (quantityHelper(quantity)) {
      return quantityHelper1();
    }
    return quantity;
  }

  private String companyHelper1(Portfolio portfolio) {
    view.getCompanyTicker();
    String companyName = input.nextLine();
    if (companyHelper(portfolio,companyName)) {
      return companyHelper1(portfolio);
    }
    return companyName;
  }


  protected String dateHelperInFlexiblePortfolio1(String companyName) {
    view.getDate();
    String date = input.nextLine();
    if (dateHelperInFlexiblePortfolio(date,companyName)) {
      return dateHelperInFlexiblePortfolio1(companyName);
    }
    return date;
  }

  protected String dateHelper1() {
    view.getDate();
    String date = input.nextLine();
    if (dateHelper(date)) {
      return dateHelper1();
    }
    return date;
  }


  void updatePortfolioForCurrentInstance(String filePath) {

    this.portfolioName = "currentInstance";
    String type = jsonParserImplementation.getTypeOfLoadedFile(filePath);
    if (!type.equals("flexible")) {
      view.displayErrorMessage("Can not update an inflexible portfolio");
      start();
    }
    updateStocksForCurrentInstance(filePath, flexiblePortfolioTypeObj, portfolioName);
  }

  private void continueUpdatingPortfolioForCurrentInstance(
          String path, IFlexible portfolio, String portfolioName) {
    view.checkIfUserWantsToContinueUpdatingPortfolio();
    String option = input.nextLine();
    if (validateInputsFromUSer(option)) {
      continueUpdatingPortfolioForCurrentInstance(path, portfolio, portfolioName);
    }
    if (option.equals("1")) {
      //1 for continue
      updateStocksForCurrentInstance(path, portfolio, portfolioName);
    } else if (option.equals("2")) {
      //continue further
      //model.createPortfolioIfCreatedManually(portfolioName, portfolio);
      finalExitCondition();
    }
  }

  private void askUserWhatHeWantsToView() {
    String name = pNameHelper();
    portfolioName =name;
    viewHelper(name);
  }

  private void viewHelper(String name) {
    view.checkIfUserWantsToViewCompositionOrTotalValue();

    String option = String.valueOf(input.nextLine());
    if (validateInitialInputsFromUser(option)) {
      viewHelper(name);
    }
    String type = null;
    if (!name.equals("currentInstance")) {
      type = jsonParserImplementation.getTypeOfFile(name);
    }
    helperForViewHelper(type, option, name, "");
  }

  private void helperForViewHelper(String type, String option, String name, String path) {
    Portfolio portfolio = null;
    IFlexible flexible=null;
    if (type.equals(flexibleType)) {
      flexible = flexiblePortfolioTypeObj;
    } else if (type.equals(inflexibleType)) {
      portfolio =  inflexiblePortfolioTypeObj;
    }
    switch (option) {
      case "1":
        List<List<String>> records;
        if (type.equals(flexibleType)) {
          String date = dateHelper1();
          records = flexible.viewCompositionOfCurrentPortfolio(name, date);
        } else {
          records = portfolio.viewCompositionOfCurrentPortfolio(name, null);
        }
        view.displayComposition(records);
        break;
      case "2":
        if (type.equals(flexibleType)) {
          dateNotFoundHelper(name, flexible);
        } else {
          dateNotFoundHelper(name, portfolio);
        }
        break;
      case "3":
        if (type.equals(flexibleType)) {
          String date = dateHelper1();
          double totalCost = flexible.getTotalMoneyInvestedOnCertainDate(date, name);
          view.displayTheTotalCost(totalCost, date, name);
        } else {
          view.displayErrorMessage("This "
                  + "operation is not supported in Inflexible portfolio");
        }
        break;
      case "4":
        portfolioPerformanceHelper(type, name, flexible);
        break;
      default:
        break;
    }
    if (name.equals("currentInstance")) {
      viewHelper2ForCurrentInstance(name, path);
    } else {
      viewHelper2(name);
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

  private void portfolioPerformanceHelper(String type, String pName, IFlexible portfolio) {
    if (type.equals(flexibleType)) {
      String startDate = dateHelper1();
      String endDate = dateHelper1();
      Date start;
      Date end;
      Date min;
      try {
        start = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse(startDate);
        end = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse(endDate);
        min = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse("1990-01-01");
        if (start.compareTo(min) <= 0) {
          view.displayErrorMessage("start date must be greater than 1990 year."
                  + " Please enter valid start and end dates");
          portfolioPerformanceHelper(type, pName, portfolio);
        } else if (end.compareTo(start) <= 0) {
          //need to recur until correct dates are entered.
          view.displayErrorMessage("End date must be greater than start date."
                  + " Please enter valid start and end dates");
          portfolioPerformanceHelper(type, pName, portfolio);
        } else {
          Map<String, Double> result = portfolio.getPortfolioPerformanceOvertime(
                  startDate, endDate, pName);
          view.displayPortfolioPerformance(result, startDate, endDate, pName);
        }
      } catch (Exception e) {
        view.displayErrorMessage(e.getMessage());
      }

    } else {
      view.displayErrorMessage("This "
              + "operation is not supported in Inflexible portfolio");
    }
  }


  private void dateNotFoundHelper(String name, Portfolio portfolio) {
    String date = dateHelper1();
    String val = null;
    try {
      val = String.format("%.2f", portfolio.getTotalValueOfPortfolioOnCertainDate(
              date, name));
    } catch (IllegalArgumentException e) {
      view.displayErrorMessage(e.getMessage());
      dateNotFoundHelper(name, portfolio);
    }
    if (val != null) {
      view.displayTotalValue(date, val, portfolioName);
    }
  }

}