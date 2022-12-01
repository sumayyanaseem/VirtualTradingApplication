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
 * test class for Dollar cost averaging investment strategy..
 */
public class DollarCostAveragingTest {
  private IFlexible flexible;

  private double commission = 30;

  private String pName = "testDollarCostStrategy";

  private Map<String, Double> stockAndPercent;

  @Before
  public void setUp() {
    stockAndPercent = new HashMap<>();
    stockAndPercent.put("GOOG", 10.0);
    stockAndPercent.put("META", 30.0);
    stockAndPercent.put("ORCL", 50.0);
    stockAndPercent.put("TWTR", 10.0);
    flexible = new FlexiblePortfolioImpl();

  }

  @Test
  public void testWhenInputsAreInvalid() {
    String expected = "Start date can't be empty or null";
    String actual = "";
    flexible.createEmptyPortfolio(pName, "flexible");
    try {

      flexible.dollarCostStrategy(pName, stockAndPercent,
              10000, commission, 5, null, "2022-10-01");
    } catch (Exception e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);
    actual = "";
    try {
      flexible.dollarCostStrategy(pName, stockAndPercent,
              10000, commission, 5, "", "2022-10-01");
    } catch (Exception e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    expected = "End date is null";
    actual = "";
    try {
      flexible.dollarCostStrategy(pName, stockAndPercent,
              10000, commission, 5, "2022-10-01", null);
    } catch (Exception e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    expected = "The portfolio name cannot be null or empty.";
    actual = "";
    try {
      flexible.dollarCostStrategy(null,
              stockAndPercent, 10000, commission, 5, "2022-10-01", "2022-10-01");
    } catch (Exception e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    expected = "Stocks and weights map provided is empty.";
    actual = "";
    try {
      flexible.dollarCostStrategy(pName, null,
              10000, commission, 5, "2022-10-01", "2022-10-01");
    } catch (Exception e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    expected = "Stocks and weights map provided is empty.";
    actual = "";
    try {
      flexible.dollarCostStrategy(pName, new HashMap<>(),
              10000, commission, 5, "2022-10-01", "2022-10-01");
    } catch (Exception e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    expected = "Amount cant be less than 0";
    actual = "";
    try {
      flexible.dollarCostStrategy(pName, stockAndPercent,
              0, commission, 5, "2022-10-01", "2022-10-01");
    } catch (Exception e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    expected = "Can't apply strategy with specified interval. Investment interval should be atleast one day";
    actual = "";
    try {
      flexible.dollarCostStrategy(pName, stockAndPercent,
              1000, commission,
              0, "2022-10-01", "2022-10-01");
    } catch (Exception e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    expected = "Commission fee cant be less than 0";
    actual = "";
    try {
      flexible.dollarCostStrategy(pName, stockAndPercent,
              1000, 0,
              4, "2022-10-01", "2022-10-01");
    } catch (Exception e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    expected = "Commission fee cant be less than 0";
    actual = "";
    try {
      flexible.dollarCostStrategy(pName, stockAndPercent,
              1000, -10,
              4, "2022-10-01", "2022-10-01");
    } catch (Exception e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    expected = "Invalid dateFormat provided.Please provide date in YYYY-MM-DD format only.";
    actual = "";
    try {
      flexible.dollarCostStrategy(pName, stockAndPercent,
              1000, 20,
              4, "2022-01", "2022-10-01");
    } catch (Exception e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    expected = "Invalid dateFormat provided.Please provide date in YYYY-MM-DD format only.";
    actual = "";
    try {
      flexible.dollarCostStrategy(pName, stockAndPercent,
              1000, 20,
              4, "2022-01-02", "2022-01");
    } catch (Exception e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    File f = new File("userPortfolios/" + pName + "_output.json");
    assertTrue(f.exists());
    f.deleteOnExit();
  }

  @Test
  public void testWhenDatesFallOnHoliday() {
    try {
      flexible.createEmptyPortfolio(pName, "flexible");
      flexible.dollarCostStrategy(pName, stockAndPercent,
              10000, commission, 5, "2020-01-01", "2021-03-31");
      List<List<String>> results = flexible.viewCompositionOfCurrentPortfolio(pName, "2020-01-31");
      StringBuilder dates = new StringBuilder();
      StringBuilder company = new StringBuilder();
      for (List<String> list : results) {
        dates.append(list.get(2).toString());
        company.append(list.get(0).toString());
      }
      assertTrue(dates.toString().contains("2020-01-02"));
      //Jan 1st is holiday and is not included.
      assertFalse(dates.toString().contains("2020-01-01"));
      //Dec 25th
      assertFalse(dates.toString().contains("2020-12-25"));
      //July 4th
      assertFalse(dates.toString().contains("2020-07-04"));
      //Nov 11
      assertFalse(dates.toString().contains("2020-11-11"));

      assertTrue(company.toString().contains("GOOG"));
      assertTrue(company.toString().contains("META"));
      assertTrue(company.toString().contains("ORCL"));
      assertTrue(company.toString().contains("TWTR"));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    File f = new File("userPortfolios/" + pName + "_output.json");
    assertTrue(f.exists());
    f.deleteOnExit();
  }


  @Test
  public void testDollarCostStrategyStarAndEnd() {

    try {
      flexible.createEmptyPortfolio(pName, "flexible");
      flexible.dollarCostStrategy(pName, stockAndPercent,
              10000, commission, 5, "2020-08-01", "2021-03-31");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    File f = new File("userPortfolios/" + pName + "_output.json");
    assertTrue(f.exists());
    f.deleteOnExit();
  }


  @Test
  public void testDollarCostStrategyStartWithNoEndDate() {

    try {
      flexible.createEmptyPortfolio(pName, "flexible");
      flexible.dollarCostStrategy(pName, stockAndPercent,
              10000, commission, 5,
              "2020-08-01", "");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    File f = new File("userPortfolios/" + pName + "_output.json");
    assertTrue(f.exists());
    f.deleteOnExit();
  }

  @Test
  public void testDollarCostStrategyStartInvalidPercent() {
    Map<String, Double> stockAndPercent = new HashMap<>();
    stockAndPercent.put("GOOG", 10.0);
    stockAndPercent.put("META", 100.0);
    stockAndPercent.put("ORCL", 50.0);
    stockAndPercent.put("TWTR", 10.0);
    String expected = "total percentage is not exactly 100";
    String actual = "";
    try {
      flexible.createEmptyPortfolio(pName, "flexible");
      flexible.dollarCostStrategy(pName, stockAndPercent,
              10000, commission, 5,
              "2020-08-01", "2021-03-31");
    } catch (Exception e) {
      actual = e.getMessage();
    }
    assertEquals(expected, actual);
    File f = new File("userPortfolios/" + pName + "_output.json");
    assertTrue(f.exists());
    f.deleteOnExit();

  }

  @Test
  public void testFixedCostWithStockMapValueIsNull() {
    stockAndPercent.put(null, 10.5);
    stockAndPercent.put("META", 29.5);
    stockAndPercent.put("ORCL", 49.5);
    stockAndPercent.put("TWTR", 10.5);
    String expected = "Stock name can't be empty or null";
    String actual = "";
    try {
      flexible.createEmptyPortfolio(pName, "flexible");
      flexible.dollarCostStrategy(pName, stockAndPercent,
              10000, commission, 5,
              "2020-08-01", "2021-03-31");
    } catch (Exception e) {
      actual = e.getMessage();
    }
    assertEquals(expected, actual);

    File f = new File("userPortfolios/" + pName + "_output.json");
    assertTrue(f.exists());
    f.deleteOnExit();
  }

  @Test
  public void testDollarCostStrategyStartNegativePercent() {
    Map<String, Double> stockAndPercent = new HashMap<>();
    stockAndPercent.put("GOOG", -10.0);
    stockAndPercent.put("META", 30.0);
    stockAndPercent.put("ORCL", 50.0);
    stockAndPercent.put("TWTR", 10.0);
    String expected = "Specified percent value is negative. Please enter positive percentages only";
    String actual = "";
    try {
      flexible.createEmptyPortfolio(pName, "flexible");
      flexible.dollarCostStrategy(pName, stockAndPercent,
              10000, commission, 5,
              "2020-08-01", "2021-03-31");
    } catch (Exception e) {
      actual = e.getMessage();
    }
    assertEquals(expected, actual);
    File f = new File("userPortfolios/" + pName + "_output.json");
    assertTrue(f.exists());
    f.deleteOnExit();
  }

  @Test
  public void testDollarCostStrategyEndDateIsFuture() {
    try {
      flexible.createEmptyPortfolio(pName, "flexible");
      flexible.dollarCostStrategy(pName, stockAndPercent,
              10000, commission, 5,
              "2020-08-01", "2024-03-31");
      List<List<String>> results = flexible.viewCompositionOfCurrentPortfolio(pName, "2022-11-11");
      StringBuilder dates = new StringBuilder();
      StringBuilder company = new StringBuilder();
      for (List<String> list : results) {
        dates.append(list.get(2).toString());
        company.append(list.get(0).toString());
      }
      //Jan 1st is holiday and is not included.
      assertFalse(dates.toString().contains("2021-01-01"));
      //Dec 25th
      assertFalse(dates.toString().contains("2021-12-25"));
      //July 4th
      assertFalse(dates.toString().contains("2021-07-04"));
      //Nov 11
      assertFalse(dates.toString().contains("2021-11-11"));
      //Future dates
      assertFalse(dates.toString().contains("2024-01-11"));

      assertTrue(company.toString().contains("GOOG"));
      assertTrue(company.toString().contains("META"));
      assertTrue(company.toString().contains("ORCL"));
      assertTrue(company.toString().contains("TWTR"));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    File f = new File("userPortfolios/" + pName + "_output.json");
    assertTrue(f.exists());
    f.deleteOnExit();
  }


  @Test
  public void testDollarCostStrategyWithFractionalInvestment() {
    Map<String, Double> stockAndPercent = new HashMap<>();
    stockAndPercent.put("GOOG", 10.0);
    stockAndPercent.put("META", 30.0);
    stockAndPercent.put("ORCL", 50.0);
    stockAndPercent.put("TWTR", 10.0);
    try {
      flexible.createEmptyPortfolio(pName, "flexible");
      flexible.dollarCostStrategy(pName, stockAndPercent,
              10000.43, commission, 5,
              "2020-08-01", "2021-03-31");
      List<List<String>> results = flexible.viewCompositionOfCurrentPortfolio(pName, "2021-01-31");
      assertTrue(results.size() > 1);
      StringBuilder company = new StringBuilder();
      for (List<String> list : results) {
        company.append(list.get(0).toString());

      }
      assertTrue(company.toString().contains("GOOG"));
      assertTrue(company.toString().contains("META"));
      assertTrue(company.toString().contains("ORCL"));
      assertTrue(company.toString().contains("TWTR"));

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    File f = new File("userPortfolios/" + pName + "_output.json");
    assertTrue(f.exists());
    f.deleteOnExit();
  }


  @Test
  public void testDollarCostStrategyWithFractionalPercentages() {
    Map<String, Double> stockAndPercent = new HashMap<>();
    stockAndPercent.put("GOOG", 10.5);
    stockAndPercent.put("META", 29.5);
    stockAndPercent.put("ORCL", 49.5);
    stockAndPercent.put("TWTR", 10.5);
    try {
      flexible.createEmptyPortfolio(pName, "flexible");
      flexible.dollarCostStrategy(pName, stockAndPercent,
              10000.56, commission, 5,
              "2020-08-01", "2021-03-31");
      List<List<String>> results = flexible.viewCompositionOfCurrentPortfolio(pName, "2021-01-31");
      assertTrue(results.size() > 1);
      StringBuilder company = new StringBuilder();
      StringBuilder quantity = new StringBuilder();
      for (List<String> list : results) {
        company.append(list.get(0).toString());
        quantity.append(list.get(1).toString());

      }
      assertFalse(quantity.toString().isEmpty());
      assertTrue(company.toString().contains("GOOG"));
      assertTrue(company.toString().contains("META"));
      assertTrue(company.toString().contains("ORCL"));
      assertTrue(company.toString().contains("TWTR"));
    } catch (Exception e) {
      // do nothing
    }
  }


}
