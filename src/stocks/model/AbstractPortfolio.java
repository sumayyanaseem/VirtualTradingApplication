package stocks.model;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import stocks.customAPI.APICustomClass;
import stocks.customAPI.APICustomInterface;
import stocks.customParser.JsonParserImplementation;

abstract class AbstractPortfolio implements Portfolio{

  protected String portfolioName;
  protected  Map<String, Map<String, List<Stock>>> stockMap;

  protected final APICustomInterface apiCustomInterface;
  protected final JsonParserImplementation parser;

  public AbstractPortfolio() {
    this.portfolioName = "";
    this.stockMap = new HashMap<>();
    this.apiCustomInterface = new APICustomClass("https://www.alphavantage.co/query?function=TIME_SERIES_");
    this.parser = new JsonParserImplementation();
  }

  @Override
  public void addStocks(String quantity, String companyName, String portfolioName) {

  }

  @Override
  public double getTotalValueOfPortfolioOnCertainDate(String date, String portfolioName) {
    return 0;
  }

  @Override
  public void loadPortfolioUsingFilePath(String filePath) {

  }

  @Override
  public List<List<String>> viewCompositionOfCurrentPortfolio(String portfolioName) {
    return null;
  }

  @Override
  public void createPortfolioIfCreatedManually(String portfolioName) {

  }

  @Override
  public void validateIfCompanyExists(String companyName) {
    if (companyName == null) {
      throw new IllegalArgumentException("Invalid companyName provided");
    }
    boolean found =false;
    for (CompanyTickerSymbol companyTickerSymbol : CompanyTickerSymbol.values()) {
      if (companyTickerSymbol.name().equalsIgnoreCase(companyName)) {
        found = true;
        break;
      }
    }
    if(!found){
      throw new IllegalArgumentException("Given company doesnt exist in our records."
              + "Please provide valid  companyTicker symbol.");
    }
  }

  @Override
  public void validateIfPortfolioAlreadyExists(String portfolioName) {
    if (portfolioName == null || portfolioName.equals("")) {
      throw new IllegalArgumentException("Invalid portfolioName provided");
    }
    String path = "userPortfolios/" + portfolioName + "_output" + ".csv";
    File f = new File(path);
    if (f.isFile() && f.exists()) {
      throw new IllegalArgumentException("Given portfolio exist."
              + "Please provide valid portfolioName.");
    }
  }

  @Override
  public void validateIfPortfolioDoesntExists(String portfolioName) {
    if (portfolioName == null) {
      throw new IllegalArgumentException("Invalid portfolioName provided");
    }
    String path = "userPortfolios/" + portfolioName + "_output" + ".csv";
    File f = new File(path);
    if (!f.isFile() || !f.exists()) {
      throw new IllegalArgumentException("Given portfolio doesnt exist."
              + "Please provide valid portfolioName.");
    }
  }

  @Override
  public String toString() {
    StringBuilder res = new StringBuilder();

    if (stockMap != null && !stockMap.isEmpty()) {
      res.append("PortfolioName : ").append(this.portfolioName);
      res.append("\n");
      Map<String, List<Stock>> temp = stockMap.get(this.portfolioName);
      for (Map.Entry<String, List<Stock>> entry : temp.entrySet()) {
        res.append(entry.getValue().toString());
      }

    }
    return res.toString();
  }


  public void validateQuantity(String quantity) {
    if (quantity == null) {
      throw new IllegalArgumentException("Invalid quantity provided");
    }
    try {
      double q = Double.parseDouble(quantity);
      if (q <= 0 || q > Integer.MAX_VALUE) {
        throw new IllegalArgumentException("Invalid quantity provided");
      }
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Quantity should be always a positive whole number.");
    }
  }

  protected void validateIfPortfolioExists(String portfolioName,String action){
    //if action is sell,validate this portfolio exists or not

    //if action is buy or add , then validate

    //if buy/add is coming from create operation, then portfolio file shouldnt exist.

    //if buy is coming from update operation,then portfolio file should exist.
  }

  protected void validateDate(String date) {
    String format = "yyyy-MM-dd";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH);
    try {
      LocalDate ld = LocalDate.parse(date, formatter);
      String result = ld.format(formatter);
      if (!result.equals(date)) {
        throw new IllegalArgumentException("Invalid dateFormat provided."
                + "Please provide date in YYYY-MM-DD format only.");
      } else {
        String todayDateStr = new SimpleDateFormat(format).format(
                new Date(System.currentTimeMillis()));
        Date todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse(todayDateStr);
        Date givenDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse(date);
        if (givenDate.compareTo(todayDate) > 0) {
          throw new IllegalArgumentException("Future Date provided."
                  + "Please provide date less then or equal to today");
        }
      }
    } catch (IllegalArgumentException | DateTimeParseException e) {
      throw new IllegalArgumentException("Invalid dateFormat provided."
              + "Please provide date in YYYY-MM-DD format only.");
    } catch (ParseException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

}
