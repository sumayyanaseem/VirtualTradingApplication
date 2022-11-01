package stocks.controller;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

import stocks.model.PortfolioImplModel;
import stocks.model.PortfolioModel;

public class PortfolioControllerImpl implements PortfolioController {

  private PortfolioModel model;
  private String portfolioName;
  private final InputStream input;

  private final PrintStream out;


  public PortfolioControllerImpl(InputStream in,PrintStream out) {
    this.input =in;
    this.out=out;
    this.portfolioName = "";
  }


  public void start(PortfolioModel model) {
    Objects.requireNonNull(model);
    this.model = model;
    String option = callToViewToChooseCreateOrView();
    switch (option) {
      case "1":
        createOrUpdatePortfolio();
        break;
      case "2":
        askUserWhatHeWantsToView();
        break;
    }
  }

  private void askUserWhatHeWantsToView() {
    String option = checkIfUserWantsToViewCompositionOrTotalValue();
    switch (option) {
      case "1":
        String name = getPortfolioNameToViewOrUpdate();
        List<List<String>> records = model.viewCompositionOfCurrentPortfolio(name);
        displayComposition(records);
        break;
      case "2":
        String pName = getPortfolioNameToViewOrUpdate();
        String date = getDateForValuation();
        String val= String.valueOf(model.getTotalValueOfPortfolioOnCertainDate(date,pName));
        displayTotalValue(date,val,portfolioName);
        break;
    }
  }

  private void createOrUpdatePortfolio()  {
    String option = createOrUpdateExistingPortfolio();
    switch (option) {
      case "1":
        getCreatePortfolioChoice();
        break;
      case "2":
        //UpdatePortfolio();
        break;
    }
  }

  private void UpdatePortfolio()  {
    String portfolioName = getPortfolioNameToViewOrUpdate();
    buyOrSellStocks(portfolioName);
  }


  private void getCreatePortfolioChoice() {
    String option = askUserOnHowToCreatePortfolio();//using Json/XML file or individual inputs
    switch (option) {
      case "1":
        String filePath = getFilePath();
        model.createPortfolioUsingFilePath(filePath);
        finalExitCondition();
        break;
      case "2":
        createNewPortfolioForCurrentUser();
        break;
    }

  }

  private void stoppingCondition(String portfolioName) {
    String option = askUserIfHeWantsToContinueTradingInCurrentPortfolio();
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

  private void createNewPortfolioForCurrentUser()  {
    String portfolioName = getPortfolioNameToCreate();
    this.portfolioName = portfolioName;
    buyOrSellStocks(portfolioName);
  }

  private void buyOrSellStocks(String portfolioName){
    String option = getBuyOrSellChoiceFromUser();//Buy or Sell choice
    switch (option) {
      case "1":
        String companyName = getCompanyTicker();
        String quantity = getQuantity();
        model.buyStocks(quantity, companyName, portfolioName);
        stoppingCondition(portfolioName);
        break;
      case "2":
        //companyName = view.callToViewToAskCompanyTicker();
       // quantity = view.callToViewToAskQuantity();
        // model.sellStocks(quantity, companyName, portfolioName);
       // stoppingCondition(this.portfolioName);
        break;
    }
  }

  private void finalExitCondition()  {
    String option = checkIfUserWantsToExitCompletely();

    switch (option) {
      case "1":
        start(new PortfolioImplModel());
        break;
      case "2":
        //do nothing
        break;
    }

  }
  public String callToViewToChooseCreateOrView()  {
    out.println("Enter 1: To trade stocks 2: To view the portfolio composition");
    Scanner myObj = new Scanner(input);
    String res = myObj.nextLine();
    if(validateInputsFromUSer(res)){
      return callToViewToChooseCreateOrView();
    }
    return res;
  }


  public String askUserOnHowToCreatePortfolio()  {
    out.println("Enter 1: To create Portfolio using external file  2: To create manually");
    Scanner myObj = new Scanner(input);
    String res = myObj.nextLine();
    if(validateInputsFromUSer(res)){
      return askUserOnHowToCreatePortfolio();
    }
    return res;
  }


  public String getFilePath() {
    out.println("Enter the path of File which is used to create Portfolio");
    Scanner myObj = new Scanner(input);
    return myObj.nextLine();
  }


  public String getPortfolioNameToCreate() {
    out.println("Enter the name of the Portfolio");
    Scanner myObj = new Scanner(input);
    return myObj.nextLine();
  }


  public String getBuyOrSellChoiceFromUser()  {
    out.println("Enter 1: To buy stocks 2: To sell stocks");
    Scanner myObj = new Scanner(input);
    String res = myObj.nextLine();
    if(validateInputsFromUSer(res)){
      return getBuyOrSellChoiceFromUser();
    }
    return res;
  }


  public String getQuantity()  {
    out.println("Enter the quantity of the stocks.");
    Scanner myObj = new Scanner(input);
    String quantity = myObj.nextLine();
    validateQuantity(quantity);
    return quantity;
  }

  private void validateQuantity(String quantity) {
    try {
      Double q = Double.parseDouble(quantity);
      if (q <= 0) {
        out.println("Invalid quantity provided");
      }
    } catch (IllegalArgumentException e) {
      out.println("Quantity should be always a positive whole number.");
    }
  }


  public String getCompanyTicker()  {
    out.println("Enter the name of the company");
    Scanner myObj = new Scanner(input);
    String companyName = myObj.nextLine();
    //validateCompanyName(companyName);
    return companyName;
  }


  public String askUserIfHeWantsToContinueTradingInCurrentPortfolio() {
    out.println("Enter 1: To continue trading in current portfolio.  2: To exit from current Portfolio.");
    Scanner myObj = new Scanner(input);
    String res = myObj.nextLine();
    if(validateInputsFromUSer(res)){
      return askUserIfHeWantsToContinueTradingInCurrentPortfolio();
    }
    return res;
  }


  public String checkIfUserWantsToExitCompletely()  {
    out.println("Enter 1: To continue trading further. 2: To exit from this session.");
    Scanner myObj = new Scanner(input);
    String res = myObj.nextLine();
    if(validateInputsFromUSer(res)){
      return checkIfUserWantsToExitCompletely();
    }
    return res;
  }


  public String createOrUpdateExistingPortfolio()  {
    out.println("Enter 1: To create a portfolio. 2: To update a existing portfolio.");
    Scanner myObj = new Scanner(input);
    String res = myObj.nextLine();
    if(validateInputsFromUSer(res)){
      return createOrUpdateExistingPortfolio();
    }
    return res;
  }


  public String checkIfUserWantsToViewCompositionOrTotalValue()  {
    out.println("Enter 1: To view composition of existing portfolio . 2: To get TotalValue of a portfolio");
    Scanner myObj = new Scanner(input);
    String res = myObj.nextLine();
    if(validateInputsFromUSer(res)){
      return checkIfUserWantsToViewCompositionOrTotalValue();
    }
    return res;
  }


  public String getPortfolioNameToViewOrUpdate() {
    out.println("Enter the name of the portfolio");
    Scanner myObj = new Scanner(input);
    String name = myObj.nextLine();
    if(validateIfPortfolioExists(name)){
      return getPortfolioNameToViewOrUpdate();
    }
    return name;
  }


  public void displayTotalValue(String date, String val, String portfolioName) {
    out.println("Total Valuation of Portfolio " + portfolioName + " is :" + val);
  }


  public String getDateForValuation()  {
    out.println("Enter date as of which you need portfolio valuation( YYYY-MM-DD format only)");
    Scanner myObj = new Scanner(input);
    String date = myObj.nextLine();
    if(validateDate(date)){
      return getDateForValuation();
    }
    return date;
  }



  public void displayComposition(List<List<String>> records) {
    for (int i = 0; i < records.size(); i++) {

      for (int j = 0; j < records.get(i).size(); j++) {
        out.print(records.get(i).get(j));
        int len1 = records.get(0).get(j).length();
        int len2 = records.get(i).get(j).length();
        int len = len1 - len2;
        for (int k = 0; k < len; k++) {
          out.print(" ");
        }
        out.print(" ");
      }
      out.println();
    }
  }


  private boolean validateInputsFromUSer(String input) {
    if (input.equals("1") || input.equals("2")) {
      //do nothing
    } else {
      out.println("Invalid input provided.Please provide a valid input (either 1 or 2)");
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
        out.println("Invalid dateFormat provided.Please provide date in YYYY-MM-DD format only.");
        return true;
      }
    } catch (IllegalArgumentException | DateTimeParseException e) {
      out.println("Invalid dateFormat provided.Please provide date in YYYY-MM-DD format only.");
      return true;
    }
    return false;
  }

  private boolean validateIfPortfolioExists(String portfolioName) {
    String path = portfolioName + ".csv";
    File f = new File(path);
    if (!f.isFile()) {
      out.println("Given portfolio doesnt exist.Please provide valid portfolioName.");
      return true;
    }
    return false;
  }
}