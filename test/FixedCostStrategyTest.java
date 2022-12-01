import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stocks.model.FlexiblePortfolioImpl;
import stocks.model.IFlexible;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * test class for fixed cost averaging investment strategy..
 */
public class FixedCostStrategyTest {

  private IFlexible portfolio;

  private String portfolioName = "testFixedCostStrategy";

  private double investmentAmount = 10000;
  private double commissionFee = 30;

  private Map<String, Double> stockAndPercent;

  @Before
  public void setUp() {
    stockAndPercent = new HashMap<>();
    stockAndPercent.put("GOOG", 10.0);
    stockAndPercent.put("META", 30.0);
    stockAndPercent.put("ORCL", 50.0);
    stockAndPercent.put("TWTR", 10.0);
    portfolio = new FlexiblePortfolioImpl();

  }

  @Test
  public void testFixedCostWithFractionalWeights() {
    Map<String, Double> stockAndPercent = new HashMap<>();
    stockAndPercent.put("GOOG", 10.5);
    stockAndPercent.put("META", 29.5);
    stockAndPercent.put("ORCL", 49.5);
    stockAndPercent.put("TWTR", 10.5);
    String date = "2022-10-03";
    portfolio.createEmptyPortfolio(portfolioName, "flexible");
    portfolio.fixedAmountStrategy(portfolioName,
            stockAndPercent, investmentAmount, commissionFee, date);
    List<List<String>> results = portfolio.viewCompositionOfCurrentPortfolio(portfolioName, date);
    StringBuilder dates = new StringBuilder();
    for (List<String> list : results) {
      for (String s : list) {
        //System.out.println(s);
        dates.append(list.get(2).toString());
      }
    }
    assertTrue(dates.toString().contains(date));
    File f = new File("userPortfolios/" + portfolioName + "_output.json");
    assertTrue(f.exists());
    f.deleteOnExit();
  }


  @Test
  public void testFixedCostWithStockMapValueIsNull() {
    stockAndPercent.put(null, 10.5);
    stockAndPercent.put("META", 29.5);
    stockAndPercent.put("ORCL", 49.5);
    stockAndPercent.put("TWTR", 10.5);
    String date = "2022-10-03";
    String expected = "Stock name can't be empty or null";
    String actual = "";
    try {
      portfolio.createEmptyPortfolio(portfolioName, "flexible");
      portfolio.fixedAmountStrategy(portfolioName,
              stockAndPercent, investmentAmount, commissionFee, date);
    } catch (IllegalArgumentException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);
  }

  @Test
  public void testWithNegativeWeights() {
    String date = "2022-10-03";
    stockAndPercent.put("GOOG", -10.0);
    stockAndPercent.put("META", 30.0);
    stockAndPercent.put("ORCL", 50.0);
    stockAndPercent.put("TWTR", 10.0);
    String expected = "Specified percent value is negative. Please enter positive percentages only";
    String actual = "";
    try {
      portfolio.createEmptyPortfolio(portfolioName, "flexible");
      portfolio.fixedAmountStrategy(portfolioName,
              stockAndPercent, investmentAmount, commissionFee, date);
    } catch (IllegalArgumentException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);
  }

  @Test
  public void testFixedCostStrategyWithValidInputs() {
    String date = "2022-10-03";
    portfolio.createEmptyPortfolio(portfolioName, "flexible");
    portfolio.fixedAmountStrategy(portfolioName,
            stockAndPercent, investmentAmount, commissionFee, date);
    List<List<String>> results = portfolio.viewCompositionOfCurrentPortfolio(portfolioName, date);
    StringBuilder dates = new StringBuilder();
    for (List<String> list : results) {
      for (String s : list) {
        //System.out.println(s);
        dates.append(list.get(2).toString());
      }
    }
    assertTrue(dates.toString().contains(date));
    File f = new File("userPortfolios/" + portfolioName + "_output.json");
    assertTrue(f.exists());
    f.deleteOnExit();
    //On holiday
    date = "2022-01-01";
    portfolio.createEmptyPortfolio(portfolioName, "flexible");
    portfolio.fixedAmountStrategy(portfolioName,
            stockAndPercent, investmentAmount, commissionFee, date);
    results = portfolio.viewCompositionOfCurrentPortfolio(portfolioName, "2022-01-03");
    dates = new StringBuilder();
    for (List<String> list : results) {
      for (String s : list) {
        //System.out.println(s);
        dates.append(list.get(2).toString());
      }
    }
    assertFalse(dates.toString().contains(date));
    assertTrue(dates.toString().contains("2022-01-03"));
    f = new File("userPortfolios/" + portfolioName + "_output.json");
    assertTrue(f.exists());
    f.deleteOnExit();
  }


  @Test
  public void testFixedCostStrategyWithInValidInputs() {
    String date = "2022-10-01";
    stockAndPercent = new HashMap<>();
    stockAndPercent.put("GOOG", 10.0);
    stockAndPercent.put("META", 30.0);
    stockAndPercent.put("ORCL", 50.0);
    stockAndPercent.put("TWTR", 10.0);
    String expected = "Date can't be empty or null";
    String actual = "";
    try {
      portfolio.fixedAmountStrategy(portfolioName,
              stockAndPercent, investmentAmount, commissionFee, null);
    } catch (IllegalArgumentException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    actual = "";
    try {
      portfolio.fixedAmountStrategy(portfolioName,
              stockAndPercent, investmentAmount, commissionFee, "");
    } catch (IllegalArgumentException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    expected = "amount cant be less than 0";
    actual = "";
    try {
      portfolio.fixedAmountStrategy(portfolioName,
              stockAndPercent, 0, commissionFee, date);
    } catch (IllegalArgumentException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    expected = "amount cant be less than 0";
    actual = "";
    try {
      portfolio.fixedAmountStrategy(portfolioName,
              stockAndPercent, -100, commissionFee, date);
    } catch (IllegalArgumentException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);


    expected = "commission fee cant be less than 0";
    actual = "";
    try {
      portfolio.fixedAmountStrategy(portfolioName,
              stockAndPercent, investmentAmount, 0, date);
    } catch (IllegalArgumentException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    expected = "commission fee cant be less than 0";
    actual = "";
    try {
      portfolio.fixedAmountStrategy(portfolioName,
              stockAndPercent, investmentAmount, -100, date);
    } catch (IllegalArgumentException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);


    expected = "Stocks and weights map provided is empty.";
    actual = "";
    try {
      portfolio.fixedAmountStrategy(portfolioName,
              null, investmentAmount, commissionFee, date);
    } catch (IllegalArgumentException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);


    expected = "Stocks and weights map provided is empty.";
    actual = "";
    try {
      portfolio.fixedAmountStrategy(portfolioName,
              new HashMap<>(), investmentAmount, commissionFee, date);
    } catch (IllegalArgumentException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);


    Map<String, Double> stockmap = new HashMap<>();
    stockmap.put("GOOG", 10.0);
    stockmap.put("META", 100.0);
    stockmap.put("ORCL", 50.0);
    stockmap.put("TWTR", 10.0);
    expected = "total percentage is not exactly 100";
    actual = "";
    try {
      portfolio.fixedAmountStrategy(portfolioName,
              stockmap, investmentAmount, commissionFee, date);
    } catch (IllegalArgumentException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);


    expected = "The portfolio name cannot be null or empty.";
    actual = "";
    try {
      portfolio.fixedAmountStrategy(null,
              stockAndPercent, investmentAmount, commissionFee, date);
    } catch (IllegalArgumentException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    expected = "The portfolio name cannot be null or empty.";
    actual = "";
    try {
      portfolio.fixedAmountStrategy("",
              stockAndPercent, investmentAmount, commissionFee, date);
    } catch (IllegalArgumentException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

  }


  @Test
  public void testWhenDatesFallBeforeIPO(){
    stockAndPercent = new HashMap<>();
    stockAndPercent.put("GOOG", 100.0);
    String expected="Given Date is before IPO";
    String actual="";
    try {
      portfolio.createEmptyPortfolio(portfolioName, "flexible");
      portfolio.fixedAmountStrategy(portfolioName,
              stockAndPercent, investmentAmount, commissionFee, "2004-10-01");
      List<List<String>> results = portfolio.viewCompositionOfCurrentPortfolio(portfolioName, "2021-01-31");
      assertTrue(results.size() == 0);

    } catch (Exception e) {
      actual=e.getMessage();
      System.out.println(e.getMessage());
    }
    assertEquals(actual,expected);
    File f = new File("userPortfolios/" + portfolioName + "_output.json");
    assertTrue(f.exists());
    f.deleteOnExit();

  }
}
