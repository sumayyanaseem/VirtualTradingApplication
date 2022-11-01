package stocks.view;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class PortfolioViewImpl implements PortfolioView {

  private final InputStream input;

  private final PrintStream out;

  public PortfolioViewImpl(InputStream in, PrintStream out) {
    input = in;
    this.out = out;
  }

  @Override
  public String callToViewToChooseCreateOrView()  {
    out.println("Enter 1: To trade stocks 2: To view the portfolio composition");
    Scanner myObj = new Scanner(input);
    String res = myObj.nextLine();
    if(validateInputsFromUSer(res)){
      return callToViewToChooseCreateOrView();
    }
    return res;
  }

  @Override
  public String askUserOnHowToCreatePortfolio()  {
    out.println("Enter 1: To create Portfolio using external file  2: To create manually");
    Scanner myObj = new Scanner(input);
    String res = myObj.nextLine();
    if(validateInputsFromUSer(res)){
      return askUserOnHowToCreatePortfolio();
    }
    return res;
  }

  @Override
  public String getFilePath() {
    out.println("Enter the path of File which is used to create Portfolio");
    Scanner myObj = new Scanner(input);
    return myObj.nextLine();
  }

  @Override
  public String getPortfolioNameToCreate() {
    out.println("Enter the name of the Portfolio");
    Scanner myObj = new Scanner(input);
    return myObj.nextLine();
  }

  @Override
  public String getBuyOrSellChoiceFromUser()  {
    out.println("Enter 1: To buy stocks 2: To sell stocks");
    Scanner myObj = new Scanner(input);
    String res = myObj.nextLine();
    if(validateInputsFromUSer(res)){
      return getBuyOrSellChoiceFromUser();
    }
    return res;
  }

  @Override
  public String getQuantity()  {
    out.println("Enter the quantity of the stocks.");
    Scanner myObj = new Scanner(input);
    String quantity = myObj.nextLine();
    validateQuantity(quantity);
    return quantity;
  }

  private void validateQuantity(String quantity) {
    try {
      int q = Integer.parseInt(quantity);
      if (q <= 0) {
        out.println("Invalid quantity provided");
      }
    } catch (IllegalArgumentException e) {
      out.println("Quantity should be always a positive whole number.");
    }
  }

  @Override
  public String getCompanyTicker()  {
    out.println("Enter the name of the company");
    Scanner myObj = new Scanner(input);
    String companyName = myObj.nextLine();
    //validateCompanyName(companyName);
    return companyName;
  }

  @Override
  public String askUserIfHeWantsToContinueTradingInCurrentPortfolio() {
    out.println("Enter 1: To continue trading in current portfolio.  2: To exit from current Portfolio.");
    Scanner myObj = new Scanner(input);
    String res = myObj.nextLine();
    if(validateInputsFromUSer(res)){
      return askUserIfHeWantsToContinueTradingInCurrentPortfolio();
    }
    return res;
  }

  @Override
  public String checkIfUserWantsToExitCompletely()  {
    out.println("Enter 1: To continue trading further. 2: To exit from this session.");
    Scanner myObj = new Scanner(input);
    String res = myObj.nextLine();
    if(validateInputsFromUSer(res)){
      return checkIfUserWantsToExitCompletely();
    }
    return res;
  }

  @Override
  public String createOrUpdateExistingPortfolio()  {
    out.println("Enter 1: To create a portfolio. 2: To update a existing portfolio.");
    Scanner myObj = new Scanner(input);
    String res = myObj.nextLine();
    if(validateInputsFromUSer(res)){
      return createOrUpdateExistingPortfolio();
    }
    return res;
  }

  @Override
  public String checkIfUserWantsToViewCompositionOrTotalValue()  {
    out.println("Enter 1: To view composition of existing portfolio . 2: To get TotalValue of a portfolio");
    Scanner myObj = new Scanner(input);
    String res = myObj.nextLine();
    if(validateInputsFromUSer(res)){
      return checkIfUserWantsToViewCompositionOrTotalValue();
    }
    return res;
  }

  @Override
  public String getPortfolioNameToViewOrUpdate() {
    out.println("Enter the name of the portfolio");
    Scanner myObj = new Scanner(input);
    String name = myObj.nextLine();
    if(validateIfPortfolioExists(name)){
      return getPortfolioNameToViewOrUpdate();
    }
    return name;
  }

  @Override
  public void displayTotalValue(String date, String val, String portfolioName) {
    out.println("Total Valuation of Portfolio " + portfolioName + " is :" + val);
  }

  @Override
  public String getDateForValuation()  {
    out.println("Enter date as of which you need portfolio valuation( YYYY-MM-DD format only)");
    Scanner myObj = new Scanner(input);
    String date = myObj.nextLine();
    if(validateDate(date)){
      return getDateForValuation();
    }
    return date;
  }


  @Override
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