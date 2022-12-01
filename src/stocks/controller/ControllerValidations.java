package stocks.controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;

import stocks.model.Portfolio;
import stocks.view.IViewInterface;

 /**
 * Represents a class for controller validations.
 */
public class ControllerValidations {

  private final IViewInterface view;

   /**
    * Constructs the ControllerValidations object by taking view object as input
    *
    * @param view    represents view object
    */
  public ControllerValidations(IViewInterface view){
    this.view=view;
  }

   /**
    * method to validate options 1 or 2 from user.
    *
    * @param input input 1/2 from user
    * @return valid/invalid input provided ?
    */
  public boolean validateInputsFromUSer(String input) {
    if (input.equals("1") || input.equals("2")) {
      //do nothing
    } else {
      view.displayMessage("Invalid input provided."
              + "Please provide a valid input (either 1 or 2)");
      return true;
    }
    return false;
  }

   /**
    * method to validate options 1 or 2 or 3 or 4 from user.
    *
    * @param input input 1/2 /3/4from user
    * @return valid/invalid input provided ?
    */
  public boolean validateInitialInputsFromUser(String input) {
    if (input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4")) {
      //do nothing
    } else {
      view.displayMessage("Invalid input provided."
              + "Please provide a valid input (either 1,2,3 or 4)");
      return true;
    }
    return false;
  }

   /**
    * method to validate date from user.
    *
    * @param date string date provided by user
    * @return valid/invalid date provided ?
    */
  public boolean dateHelper(String date) {
    if (validateDate(date)) {
      return true;
    }
    return false;
  }

   /**
    * method to validate date from user.
    *
    * @param date string date provided by used
    * @param companyName stock name provided by usedr
    * @return valid/invalid date provided ?
    */
  public boolean dateHelperInFlexiblePortfolio(String date,String companyName) {
    if (validateDate(date)) {
      return true;
    }
    return false;
  }

   /**
    * method to validate if company exists.
    *
    * @param portfolio object of portfolio.
    * @param companyName stock name provided by user.
    * @return valid/invalid company provided ?
    */
  public boolean companyHelper(Portfolio portfolio,String companyName) {
    if (validateIfCompanyExists(companyName, portfolio)) {
      return true;
    }
    return false;
  }

   /**
    * method to validate quantity.
    *
    * @param quantity the quantity provided by user.
    * @return valid/invalid company provided ?
    */
  public boolean quantityHelper(String quantity) {
    if (validateQuantity(quantity)) {
      return true;
    }
    return false;
  }


   /**
    * method to validate commission.
    *
    * @param com the commission provided by user.
    * @return valid/invalid commission provided ?
    */
  public boolean commissionHelper(String com) {
    if (validateCom(com)) {
      return true;
    }
    return false;
  }

   /**
    * method to validate date.
    *
    * @param date the date provided by user.
    * @return valid/invalid date provided ?
    */
  public boolean validateDate(String date) {
    String format = "yyyy-MM-dd";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH);
    try {
      LocalDate ld = LocalDate.parse(date, formatter);
      String result = ld.format(formatter);
      if (!result.equals(date)) {
        view.displayMessage("Invalid dateFormat provided."
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
          view.displayMessage("Future Date provided."
                  + "Please provide date less then or equal to today");
          return true;
        }
      }

    } catch (IllegalArgumentException | DateTimeParseException | ParseException e) {
      view.displayMessage("Invalid dateFormat provided."
              + "Please provide date in YYYY-MM-DD format only.");
      return true;
    }
    return false;
  }


   /**
    * method to validate data from user after load.
    *
    * @param input the date provided by user.
    * @return valid/invalid input provided ?
    */
  public boolean validateInputsFromUSerAfterLoad(String input) {
    if (input.equals("1") || input.equals("2") || input.equals("3")) {
      //do nothing
    } else {
      view.displayMessage("Invalid input provided."
              + "Please provide a valid input (either 1 or 2 or 3)");
      return true;
    }
    return false;
  }


   /**
    * method to validate quantity from user.
    *
    * @param quantity the quantity provided by user.
    * @return valid/invalid input provided ?
    */
  public boolean validateQuantity(String quantity) {
    try {
      long q = Long.parseLong(quantity);
      if (q <= 0 || q > Integer.MAX_VALUE) {
        view.displayMessage("Invalid quantity provided");
        return true;
      }
    } catch (IllegalArgumentException e) {
      view.displayMessage("Quantity should be always a positive whole number.");
      return true;
    }
    return false;
  }

   /**
    * method to validate commission from user.
    *
    * @param quantity the commission provided by user.
    * @return valid/invalid commission provided ?
    */
  public boolean validateCom(String quantity) {
    try {
      double q = Double.parseDouble(quantity);
      if (q <= 0 || q > Integer.MAX_VALUE) {
        view.displayMessage("Invalid Commission provided");
        return true;
      }
    } catch (IllegalArgumentException e) {
      view.displayMessage("Commission should be always a positive number.");
      return true;
    }
    return false;
  }

   /**
    * method to validate if portfolio already exists.
    *
    * @param portfolioName the name of portfolio provided by user.
    * @return valid/invalid name provided ?
    */
  public boolean validateIfPortfolioExists(String portfolioName, Portfolio portfolio) {
    try {
      portfolio.validateIfPortfolioAlreadyExists(portfolioName);
    } catch (IllegalArgumentException e) {
      view.displayMessage(e.getMessage());
      return true;
    }
    return false;
  }

   /**
    * method to validate if portfolio doesnt exists.
    *
    * @param portfolioName the name of portfolio provided by user.
    * @return valid/invalid portfolio provided ?
    */
  public boolean validateIfPortfolioDoesntExists(String portfolioName) {
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
      view.displayMessage(e.getMessage());
      return true;
    }
    return false;
  }


   /**
    * method to validate if company exists.
    *
    * @param companyName the name of company provided by user.
    * @return valid/invalid company provided ?
    */
  public boolean validateIfCompanyExists(String companyName, Portfolio portfolio) {
    try {
      portfolio.validateIfCompanyExists(companyName);
    } catch (IllegalArgumentException e) {
      view.displayMessage(e.getMessage());
      return true;
    }
    return false;
  }

}
