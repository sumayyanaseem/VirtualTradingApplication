package stocks.model;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import stocks.customapi.APICustomClass;
import stocks.customapi.APICustomInterface;
import stocks.customparser.CustomParser;
import stocks.customparser.JsonParserImplementation;

abstract class AbstractPortfolio implements Portfolio {

  protected String portfolioName;
  protected Map<String, Map<String, List<Stock>>> stockMap;
  protected final APICustomInterface apiCustomInterface;
  protected final CustomParser parser;

  public AbstractPortfolio() {
    this.portfolioName = "";
    this.stockMap = new HashMap<>();
    this.apiCustomInterface = new APICustomClass();
    this.parser = new JsonParserImplementation();
  }


  @Override
  public void validateIfCompanyExists(String companyName) {
    //Validate if company exists in our records.
    if (companyName == null) {
      throw new IllegalArgumentException("Invalid companyName provided");
    }
    boolean found = false;
   /* for (CompanyTickerSymbol companyTickerSymbol : CompanyTickerSymbol.values()) {
      if (companyTickerSymbol.name().equalsIgnoreCase(companyName)) {
        found = true;
        break;
      }
    }
    if (!found) {
      throw new IllegalArgumentException("Given company doesnt exist in our records."
              + "Please provide valid  companyTicker symbol.");
    }*/
    try {
      double price = apiCustomInterface.fetchLatestStockPriceOfThisCompany(companyName);
    } catch(Exception e ){
      throw new IllegalArgumentException("Given company doesnt exist in our records."
              + "Please provide valid  companyTicker symbol.");
    }
  }

  @Override
  public void validateIfPortfolioAlreadyExists(String portfolioName) {
    if (portfolioName == null || portfolioName.equals("")) {
      throw new IllegalArgumentException("Invalid portfolioName provided");
    }
    String path = "userPortfolios/" + portfolioName + "_output" + ".json";
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
    String path = "userPortfolios/" + portfolioName + "_output" + ".json";
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

  /**
   * validate the quantity.
   *
   * @param quantity quantity of stocks.
   */
  protected void validateQuantity(String quantity) {
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

  /**
   * validate the given date.
   *
   * @param date given date.
   */
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
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Invalid dateFormat provided."
              + "Please provide date in YYYY-MM-DD format only.");
    } catch (ParseException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public List<List<String>> viewCompositionOfCurrentPortfolio(String portfolioName, String date) {
    if (portfolioName == null || portfolioName.equals("")) {
      throw new IllegalArgumentException("Invalid portfolioName provided");
    }
    List<List<String>> results = new ArrayList<>();
    Map<String, List<Stock>> map = null;
    if (portfolioName.equals("currentInstance") || this.portfolioName.equals(portfolioName)) {
      if (!stockMap.isEmpty()) {
        map = stockMap.get(this.portfolioName);
      }
    } else {
      validateIfPortfolioDoesntExists(portfolioName);
      this.portfolioName = portfolioName;
      map = parser.readFromFile(portfolioName);
      stockMap.put(portfolioName, map);
    }
    if (map != null) {
      String[] t = new String[4];
      t[0] = "CompanyName";
      t[1] = "Quantity";
      t[2] = "Date";
      t[3] = "Action";
      results.add(List.of(t));
      for (Map.Entry<String, List<Stock>> entry : map.entrySet()) {
        List<Stock> s = entry.getValue();
        for (Stock stock : s) {
          List<String> temp = getResultsToDisplayComposition(stock, date);
          if (temp != null) {
            results.add(temp);
          }
        }
      }
    }
    return results;
  }

  /**
   * Get the values to display in composition.
   *
   * @param s    stock of the company.
   * @param date date required.
   * @return returns the values of the portfolio until the given date.
   */
  protected abstract List<String> getResultsToDisplayComposition(Stock s, String date);


  /**
   * validate the give path of the file.
   *
   * @param path path of the file.
   */
  protected void validateFilePath(String path) {

    if (path == null) {
      throw new IllegalArgumentException("Given path doesnt exist.Please provide valid path.");
    }
    File f = new File(path);
    if (!f.isFile() || !f.exists()) {
      throw new IllegalArgumentException("Given path doesnt exist.Please provide valid path.");
    }
  }
}
