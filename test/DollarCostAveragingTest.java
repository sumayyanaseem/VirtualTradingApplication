import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stocks.model.FlexiblePortfolioImpl;
import stocks.model.IFlexible;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
  public void testWhenWeightsAreFractional(){

  }

  @Test
  public void testWhenInputsAreInvalid(){
    String expected="Start date can't be empty or null";
    String actual="";
    flexible.createEmptyPortfolio(pName,"flexible");
    try {

      flexible.dollarCostStrategy(pName,stockAndPercent,10000,commission,5,null, "2022-10-01");
    } catch (Exception e) {
     actual=e.getMessage();
    }
    assertEquals(actual,expected);
    actual="";
    try {
      flexible.dollarCostStrategy(pName,stockAndPercent,10000,commission,5,"", "2022-10-01");
    } catch (Exception e) {
      actual=e.getMessage();
    }
    assertEquals(actual,expected);

    expected="End date is null";
    actual="";
    try {
      flexible.dollarCostStrategy(pName,stockAndPercent,10000,commission,5, "2022-10-01",null);
    } catch (Exception e) {
      actual=e.getMessage();
    }
    assertEquals(actual,expected);

    expected="The portfolio name cannot be null or empty.";
    actual="";
    try {
      flexible.dollarCostStrategy(null,stockAndPercent,10000,commission,5, "2022-10-01","2022-10-01");
    } catch (Exception e) {
      actual=e.getMessage();
    }
    assertEquals(actual,expected);

    expected="Stocks and weights map provided is empty.";
    actual="";
    try {
      flexible.dollarCostStrategy(pName,null,10000,commission,5, "2022-10-01","2022-10-01");
    } catch (Exception e) {
      actual=e.getMessage();
    }
    assertEquals(actual,expected);

    expected="Amount cant be less than 0";
    actual="";
    try {
      flexible.dollarCostStrategy(pName,stockAndPercent,0,commission,5, "2022-10-01","2022-10-01");
    } catch (Exception e) {
      actual=e.getMessage();
    }
    assertEquals(actual,expected);

    expected="Can't apply strategy with specified interval. Investment interval should be atleast one day";
    actual="";
    try {
      flexible.dollarCostStrategy(pName,stockAndPercent,1000,commission,0, "2022-10-01","2022-10-01");
    } catch (Exception e) {
      actual=e.getMessage();
    }
    assertEquals(actual,expected);

    File f = new File("userPortfolios/" + pName+ "_output.json");
    assertTrue(f.exists());
    f.deleteOnExit();
  }

  @Test
  public void testWhenDatesFallOnHoliday(){
    try {
      flexible.createEmptyPortfolio(pName,"flexible");
      flexible.dollarCostStrategy(pName,stockAndPercent,10000,commission,5,"2020-01-01", "2021-03-31");
      List<List<String>> results=flexible.viewCompositionOfCurrentPortfolio(pName,"2020-01-31");
      for(List<String> list:results){
          String dates=list.get(2).toString();
          System.out.println(dates.charAt(1));
         //assertTrue(s.contains("2020-01-02"));
         //assertFalse(s.contains("2020-01-01"));

      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    File f = new File("userPortfolios/" + pName+ "_output.json");
    assertTrue(f.exists());
    f.deleteOnExit();
  }



  @Test
  public void testDollarCostStrategyStarAndEnd() {

    try {
      flexible.createEmptyPortfolio(pName,"flexible");
      flexible.dollarCostStrategy(pName,stockAndPercent,10000,commission,5,"2020-08-01", "2021-03-31");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    File f = new File("userPortfolios/" + pName+ "_output.json");
    assertTrue(f.exists());
    f.deleteOnExit();
  }


  @Test
  public void testDollarCostStrategyStartWithNoEndDate() {

    try {
      flexible.createEmptyPortfolio(pName, "flexible");
      flexible.dollarCostStrategy(pName, stockAndPercent, 10000, commission, 5, "2020-08-01", "");
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
    String expected="total percentage is not exactly 100";
    String actual ="";
    try {
      flexible.createEmptyPortfolio(pName,"flexible");
      flexible.dollarCostStrategy(pName,stockAndPercent,10000,commission,5,"2020-08-01", "2021-03-31");
    } catch (Exception e) {
      actual =e.getMessage();
    }
    assertEquals(expected,actual);
    File f = new File("userPortfolios/" + pName+ "_output.json");
    assertTrue(f.exists());
    f.deleteOnExit();

  }

  @Test
  public void testDollarCostStrategyEndDateIsFuture() {
    try {
      flexible.createEmptyPortfolio(pName,"flexible");
      flexible.dollarCostStrategy(pName,stockAndPercent,10000,commission,5,"2020-08-01", "2024-03-31");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    File f = new File("userPortfolios/" + pName+ "_output.json");
    assertTrue(f.exists());
   // f.deleteOnExit();
  }


  @Test
  public void testDollarCostStrategyEndFuture() {
    Map<String, Double> stockAndPercent = new HashMap<>();
    stockAndPercent.put("GOOG", 10.0);
    stockAndPercent.put("META", 30.0);
    stockAndPercent.put("ORCL", 50.0);
    stockAndPercent.put("TWTR", 10.0);
    // dollarCostAveragingStrategy.applyStrategyOnPortfolio("samplefutureendmoreamnt",stockAndPercent, 10000,90,"2020-08-01","2024-01-01",5.0);

  }


}
