package stocks.model;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

abstract class AbstractPortfolio implements Portfolio{
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
    String name = companyName.toUpperCase();
    String path = "availableStocks" + File.separator + "daily_" + name + ".csv";
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream is = classLoader.getSystemClassLoader().getResourceAsStream(path);
    if (is == null) {
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

    if (portfolioMap != null && !portfolioMap.isEmpty()) {
      res.append("PortfolioName : ").append(this.pName);
      res.append("\n");
      Map<String, Stock> temp = portfolioMap.get(this.pName);
      for (Map.Entry<String, Stock> entry : temp.entrySet()) {
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






}
