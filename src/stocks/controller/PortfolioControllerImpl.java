package stocks.controller;
import java.io.InputStream;
import java.util.List;
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

  public PortfolioControllerImpl(InputStream in,PortfolioView view) {
    this.input =new Scanner(in);
    this.view=view;
    this.portfolioName = "";
  }


  public void start(PortfolioModel model) {
    Objects.requireNonNull(model);
    this.model = model;
    view.callToViewToChooseCreateOrView();
    String option = input.nextLine();
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
    view.checkIfUserWantsToViewCompositionOrTotalValue();
    String option = input.nextLine();
            switch (option) {
      case "1":
        view.getPortfolioName();
        String name = input.nextLine();
        List<List<String>> records = model.viewCompositionOfCurrentPortfolio(name);
        view.displayComposition(records);
        break;
      case "2":
        view.getPortfolioName();
        String pName = input.nextLine();
        view.getDateForValuation();
        String date = input.nextLine();
        //validateDate(date);
        String val= String.valueOf(model.getTotalValueOfPortfolioOnCertainDate(date,pName));
        view.displayTotalValue(date,val,portfolioName);
        break;
    }
  }

  private void createOrUpdatePortfolio()  {
    view.createOrUpdateExistingPortfolio();
    String option = input.nextLine();
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
     view.getPortfolioName();
    String portfolioName = input.nextLine();
    buyOrSellStocks(portfolioName);
  }


  private void getCreatePortfolioChoice() {
    view.askUserOnHowToCreatePortfolio();
    String option = input.nextLine();
    switch (option) {
      case "1":
         view.getFilePath();
         String filePath = input.nextLine();
        model.createPortfolioUsingFilePath(filePath);
        finalExitCondition();
        break;
      case "2":
        createNewPortfolioForCurrentUser();
        break;
    }

  }

  private void stoppingCondition(String portfolioName) {
    view.askUserIfHeWantsToContinueTradingInCurrentPortfolio();
    String option =  input.nextLine();
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
    view.getPortfolioName();
    String portfolioName = input.nextLine();
    this.portfolioName = portfolioName;
    buyOrSellStocks(portfolioName);
  }

  private void buyOrSellStocks(String portfolioName){
    view.getBuyOrSellChoiceFromUser();//Buy or Sell choice
    String option = input.nextLine();
    switch (option) {
      case "1":
        view.getCompanyTicker();
        String companyName = input.nextLine();
        view.getQuantity();
        String quantity = input.nextLine();
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
    view.checkIfUserWantsToExitCompletely();
    String option = input.nextLine();
    switch (option) {
      case "1":
        start(new PortfolioImplModel());
        break;
      case "2":
        //do nothing
        break;
    }

  }

 /* private boolean validateInputsFromUSer(String input) {
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

  private void validateQuantity(String quantity) {
    try {
      Double q = Double.parseDouble(quantity);
      if (q <= 0) {
        out.println("Invalid quantity provided");
      }
    } catch (IllegalArgumentException e) {
      out.println("Quantity should be always a positive whole number.");
    }
  }*/
}