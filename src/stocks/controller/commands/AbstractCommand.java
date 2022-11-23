package stocks.controller.commands;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;


import stocks.customapi.CompanyTickerSymbol;
import stocks.model.Portfolio;
import stocks.view.PortfolioView;

public class AbstractCommand {

  private final PortfolioView view;
  private final Scanner input;

  public  AbstractCommand(PortfolioView view,Scanner input){
    this.view=view;
    this.input=input;
  }

  protected boolean validateInputsFromUSer(String input) {
    if (input.equals("1") || input.equals("2")) {
      //do nothing
    } else {
      view.displayErrorMessage("Invalid input provided."
              + "Please provide a valid input (either 1 or 2)");
      return true;
    }
    return false;
  }

  protected boolean validateInitialInputsFromUser(String input) {
    if (input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4")) {
      //do nothing
    } else {
      view.displayErrorMessage("Invalid input provided."
              + "Please provide a valid input (either 1,2,3 or 4)");
      return true;
    }
    return false;
  }

  protected String dateHelper() {
    view.getDate();
    String date = input.nextLine();
    if (validateDate(date)) {
      return dateHelper();
    }
    return date;
  }

  protected String dateHelperInFlexiblePortfolio(String companyName) {
    view.getDate();
    String date = input.nextLine();
    if (validateDate(date) || validateDateToCheckIfBeforeIPO(date, companyName)) {
      return dateHelperInFlexiblePortfolio(companyName);
    }
    return date;
  }

  protected String companyHelper(Portfolio portfolio) {
    view.getCompanyTicker();
    String companyName = input.nextLine();
    if (validateIfCompanyExists(companyName, portfolio)) {
      return companyHelper(portfolio);
    }
    return companyName;
  }

  protected String quantityHelper() {
    view.getQuantity();
    String quantity = input.nextLine();
    if (validateQuantity(quantity)) {
      return quantityHelper();
    }
    return quantity;
  }

  protected String commissionHelper() {
    view.getCommission();
    String com = input.nextLine();
    if (validateCom(com)) {
      return commissionHelper();
    }
    return com;
  }

  protected boolean validateDate(String date) {
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


  protected boolean validateInputsFromUSerAfterLoad(String input) {
    if (input.equals("1") || input.equals("2") || input.equals("3")) {
      //do nothing
    } else {
      view.displayErrorMessage("Invalid input provided."
              + "Please provide a valid input (either 1 or 2 or 3)");
      return true;
    }
    return false;
  }

  protected boolean validateQuantity(String quantity) {
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

  protected boolean validateCom(String quantity) {
    try {
      double q = Double.parseDouble(quantity);
      if (q <= 0 || q > Integer.MAX_VALUE) {
        view.displayErrorMessage("Invalid Commission provided");
        return true;
      }
    } catch (IllegalArgumentException e) {
      view.displayErrorMessage("Commission should be always a positive number.");
      return true;
    }
    return false;
  }

  protected boolean validateIfPortfolioExists(String portfolioName, Portfolio portfolio) {
    try {
      portfolio.validateIfPortfolioAlreadyExists(portfolioName);
    } catch (IllegalArgumentException e) {
      view.displayErrorMessage(e.getMessage());
      return true;
    }
    return false;
  }

  protected boolean validateIfPortfolioDoesntExists(String portfolioName) {
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

  protected boolean validateIfCompanyExists(String companyName, Portfolio portfolio) {
    try {
      portfolio.validateIfCompanyExists(companyName);
    } catch (IllegalArgumentException e) {
      view.displayErrorMessage(e.getMessage());
      return true;
    }
    return false;
  }

  protected boolean validateDateToCheckIfBeforeIPO(String date, String companyName) {
    for (CompanyTickerSymbol companyTickerSymbol : CompanyTickerSymbol.values()) {
      if (companyTickerSymbol.name().equalsIgnoreCase(companyName)) {
        try {
          Date givenDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                  .parse(date);
          Date ipoDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                  .parse(companyTickerSymbol.getEndDate());

          if (givenDate.compareTo(ipoDate) < 0) {
            view.displayErrorMessage("Given date is before IPO Date.Please provide a valid date.");
            return true;
          }
          break;
        } catch (ParseException e) {
          view.displayErrorMessage(e.getMessage());
          return true;
        }
      }
    }
    return false;
  }

}
