import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import stocks.customapi.APICustomClass;
import stocks.customapi.APICustomInterface;
import stocks.model.FlexiblePortfolioImpl;
import stocks.model.InFlexiblePortfolioImpl;
import stocks.model.Portfolio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

abstract class AbstractPortfolioTest {
  protected Portfolio portfolio;

  protected abstract void initialisePortfolio();

  protected final int min = 1;
  protected final int max = 100;

  protected int generateRandomNumber() {
    return (int) (Math.random() * (max - min + 1) + min);
  }

  public static final class FlexiblePortfolioImplTest extends AbstractPortfolioTest {

    private static final String date = "2022-10-01";
    private static final String com = "10";

    private final APICustomInterface apiCustomInterface = new APICustomClass("https://www.alphavantage.co/query?function=TIME_SERIES_");

    @Override
    @Before
    public void initialisePortfolio() {
      portfolio = new FlexiblePortfolioImpl(apiCustomInterface);
    }

    @Test
    public void testMultipleBuysAndSells() {
      buyMultipleStocks("test123", date);
      sellMultipleStocks("test123", date);
    }

    @Test
    public void testSellBeforeBuy() {
      String pName = "testFlexible";
      String expected = "It is impossible to sell a stock without first purchasing it.";
      String actual = "";
      String cName = "goog";
      String quantity = "10";
      String date = "2022-10-01";
      try {
        portfolio.sellStocks(cName, quantity, date, com, pName);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);
    }

    @Test
    public void testPortfolioPerformanceDaily() {
      String pName = "testFlexible";
      String date1 = "2022-11-01";
      String date2 = "2022-11-14";
      Map<String, Double> res = portfolio.getPortfolioPerformanceOvertime(date1, date2, pName);
      List<String> dates = getDatesHelper(date1, date2, 1);
      assertFalse(res.isEmpty());
      for (int i = 0; i < res.size(); i++) {
        assertTrue(res.get(dates.get(i)) != 0);
      }
    }

    private List<String> getDatesHelper(String date1, String date2, int interval) {
      List<String> dates = new ArrayList<>();
      try {
        Date start = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse(date1);

        Date end = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse(date2);

        Date current = start;

        while (current.before(end)) {
          String todayDateStr = new SimpleDateFormat("yyyy-MM-dd").format(
                  new Date(String.valueOf(current)));

          Calendar calendar = Calendar.getInstance();
          calendar.setTime(current);
          calendar.add(Calendar.DATE, interval);
          current = calendar.getTime();
          dates.add(todayDateStr);
        }
      } catch (Exception e) {
        //do nothing
      }
      return dates;
    }

    @Test
    public void testPortfolioPerformanceWeekly() {
      String pName = "testFlexible";
      String date1 = "2022-09-01";
      String date2 = "2022-11-14";
      Map<String, Double> res = portfolio.getPortfolioPerformanceOvertime(date1, date2, pName);
      List<String> dates = getDatesHelper(date1, date2, 7);
      assertFalse(res.isEmpty());
      for (int i = 0; i < res.size(); i++) {
        assertTrue(res.get(dates.get(i)) != 0);
      }
    }


    @Test
    public void testPortfolioPerformanceWhenTwoDatesAreSame() {
      String pName = "testFlexible";
      String date1 = "2022-09-01";
      String date2 = "2022-09-01";
      String expected="End date must be greater than start date. Please enter valid dates";
      String actual="";
      try {
        Map<String, Double> res = portfolio.getPortfolioPerformanceOvertime(date1, date2, pName);
      } catch(IllegalArgumentException e){
        actual=e.getMessage();
      }
      assertEquals(expected,actual);
    }

    @Test
    public void testPortfolioPerformanceWhenEndDateIsMoreThanStartDate() {
      String pName = "testFlexible";
      String date1 = "2022-09-01";
      String date2 = "2022-08-01";
      String expected="End date must be greater than start date. Please enter valid dates";
      String actual="";
      try {
        Map<String, Double> res = portfolio.getPortfolioPerformanceOvertime(date1, date2, pName);
      } catch(IllegalArgumentException e){
        actual=e.getMessage();
      }
      assertEquals(expected,actual);
    }



    @Test
    public void testPortfolioPerformanceMonthly() {
      String pName = "testFlexible";
      String date1 = "2022-01-01";
      String date2 = "2022-11-14";
      Map<String, Double> res = portfolio.getPortfolioPerformanceOvertime(date1, date2, pName);
      assertFalse(res.isEmpty());
      String monthStart = date1.substring(0,8)+"01";
      List<String> dates = getDatesHelper(monthStart, date2, 31);
      System.out.println(res.size()+" "+dates.size());
      int i=0;
      for(int j=0;j<dates.size();j++){
        System.out.println(dates.get(j));
      }
      for (Map.Entry<String,Double> map:res.entrySet()) {
        assertEquals(map.getKey().substring(0,7),dates.get(i).substring(0,7));
        assertTrue(map.getValue()!=0);
        i++;
      }
    }

    @Test
    public void testPortfolioPerformanceQuarterly() {
      String pName = "testFlexible";
      String date1 = "2020-01-01";
      String date2 = "2022-11-14";
      Map<String, Double> res = portfolio.getPortfolioPerformanceOvertime(date1, date2, pName);
      assertFalse(res.isEmpty());

    }


    @Test
    public void testPortfolioPerformanceYearly() {
      String pName = "testFlexible";
      String date1 = "2009-01-01";
      String date2 = "2022-11-14";
      Map<String, Double> res = portfolio.getPortfolioPerformanceOvertime(date1, date2, pName);
      assertFalse(res.isEmpty());
    }


    @Test
    public void testLoadAndUpdatePortfolio() {
      String path = "userPortfolios/testFlexible_output.json";
      portfolio.loadPortfolioUsingFilePath(path);
      List<List<String>> results = portfolio.viewCompositionOfCurrentPortfolio(
              "currentInstance", date);
      assertFalse(results.isEmpty());

      String pName = "currentInstance";
      String cName = "goog";
      String quantity = "10";
      String date = "2020-10-01";
      String action = "buy";
      double before = portfolio.getTotalMoneyInvestedOnCertainDate("2020-09-30", pName);

      portfolio.updatePortfolioUsingFilePath(path, cName, quantity, date, pName, action, com);

      pName = "currentInstance";
      cName = "goog";
      quantity = "10";
      date = "2022-10-01";
      action = "sell";

      portfolio.updatePortfolioUsingFilePath(path, cName, quantity, date, pName, action, com);
      double after = portfolio.getTotalMoneyInvestedOnCertainDate("2022-10-02", pName);
      assertTrue(before != after);
    }

    @Test
    public void testSellAndBuyOnSameDay() {
      String pName = "testFlexible";
      String cName = "aapl";
      String quantity = "10";
      String date = "2022-11-01";
      double before = portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      portfolio.buyStocks(cName, quantity, date, com, pName);
      portfolio.sellStocks(cName, quantity, date, com, pName);
      double after = portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      assertEquals(before, after, 0.01);
    }

    @Test
    public void testBuyBeforeIPO() {
      String pName = "testFlexible";
      String cName = "goog";
      String quantity = "10";
      String date = "2002-10-01";
      String expected = "Given date is before IPO Date.Please provide a valid date.";
      String actual = "";
      try {
        portfolio.buyStocks(cName, quantity, date, com, pName);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(expected, actual);
    }


    @Test
    public void testSellInFuture() {
      String pName = "testFlexible";
      String cName = "goog";
      String quantity = "10";
      String date = "2024-10-01";
      String expected = "Future Date provided.Please provide date less then or equal to today";
      String actual = "";
      try {
        //portfolio.buyStocks(cName, quantity, "2022-10-01", pName);
        portfolio.sellStocks(cName, quantity, date, com, pName);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(expected, actual);
    }

    @Test
    public void testCostBasis() {
      String pName = "testFlexible";
      String date = "2024-10-01";
      double res = portfolio.getTotalMoneyInvestedOnCertainDate(date, pName);
      assertTrue(res != 0);
    }

    @Test
    public void testCostBasisBeforeFirstPurchase() {
      String pName = "testFlexible";
      String date = "2000-10-01";
      double res = portfolio.getTotalMoneyInvestedOnCertainDate(date, pName);
      assertEquals(res, 0, 0.01);
    }

    @Test
    public void testUpdatePortfolio() {
      String pName = "testFlexible";
      String cName = "goog";
      String quantity = "10";
      String date = "2022-10-01";
      String action = "buy";
      double before = portfolio.getTotalMoneyInvestedOnCertainDate("2022-09-01", pName);
      portfolio.updatePortfolio(cName, quantity, date, pName, action, com);
      double after = portfolio.getTotalMoneyInvestedOnCertainDate(date, pName);
      assertTrue(before != after);
    }

    @Test
    public void testUpdatePortfolioSell() {
      String pName = "testFlexible";
      String cName = "goog";
      String quantity = "10";
      String date = "2022-10-01";
      String action = "sell";
      double before = portfolio.getTotalMoneyInvestedOnCertainDate("2022-09-01", pName);
      portfolio.updatePortfolio(cName, quantity, date, pName, action, com);
      double after = portfolio.getTotalMoneyInvestedOnCertainDate(date, pName);
      assertTrue(before != after);
    }


    @Test
    public void testInvalidPortfolioNameToView() {
      String pName = "";
      String expected = "Invalid portfolioName provided";
      String actual = "";
      try {
        portfolio.viewCompositionOfCurrentPortfolio(pName, null);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      pName = null;
      actual = "";
      expected = "Invalid portfolioName provided";
      try {
        portfolio.viewCompositionOfCurrentPortfolio(pName, date);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      pName = "A2";
      actual = "";
      expected = "Given portfolio doesnt exist.Please provide valid portfolioName.";
      try {
        portfolio.viewCompositionOfCurrentPortfolio(pName, date);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);
    }

    @Test
    public void testViewCompositionForPersistedPortfolio() {
      String pName = "testFlexible";
      List<List<String>> results = portfolio.viewCompositionOfCurrentPortfolio(pName, date);
      assertFalse(results.isEmpty());
    }


    @Test
    public void testCreatePortfolio() {
      String pname = "";
      String expected = "Invalid portfolioName provided";
      String actual = "";
      try {
        portfolio.createPortfolioIfCreatedManually(pname);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      actual = "";
      pname = null;
      try {
        portfolio.createPortfolioIfCreatedManually(pname);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);
    }

    @Test
    public void testBuyStocks() {
      String cName = "dash";
      String quantity = String.valueOf(generateRandomNumber());
      buyStocks(cName, quantity, date, "testPortfolio");
      assertTrue(portfolio.toString().contains(quantity));
      assertTrue(portfolio.toString().contains(cName.toUpperCase()));
      assertTrue(portfolio.toString().contains("testPortfolio"));
    }

    @Test
    public void testFractionalStocks() {
      String cName = "dash";
      String quantity = String.valueOf(2.50);
      buyStocks(cName, quantity, date, "testPortfolio");
      assertTrue(portfolio.toString().contains(quantity));
      assertTrue(portfolio.toString().contains(cName.toUpperCase()));
      assertTrue(portfolio.toString().contains("testPortfolio"));
    }

    @Test
    public void testInvalidStocks() {
      String cName = "dash";
      String quantity = String.valueOf(-100);
      String expected = "Quantity should be always a positive whole number.";
      String actual = "";
      try {
        buyStocks(cName, quantity, date, "testPortfolio");
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      actual = "";
      quantity = "abc";
      try {
        buyStocks(cName, quantity, date, "testPortfolio");
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);


      actual = "";
      quantity = String.valueOf(0);
      try {
        buyStocks(cName, quantity, date, "testPortfolio");
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      actual = "";
      quantity = "";
      try {
        buyStocks(cName, quantity, date, "testPortfolio");
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);


      actual = "";
      quantity = null;
      expected = "Invalid quantity provided";
      try {
        buyStocks(cName, quantity, date, "testPortfolio");
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);
    }

    @Test
    public void testInvalidCompanyName() {

      String cName = "net";
      String quantity = String.valueOf(100);
      String expected = "Given company doesnt exist in our records."
              + "Please provide valid  companyTicker symbol.";
      String actual = "";
      try {
        buyStocks(cName, quantity, date, "testPortfolio");
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }

      assertEquals(actual, expected);

      //when company name is empty
      cName = "";
      actual = "";
      try {
        buyStocks(cName, quantity, date, "test");
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      //when company name is null

      cName = null;
      expected = "Invalid companyName provided";
      actual = "";
      try {
        buyStocks(cName, quantity, date, "testPortfolio");
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

    }

    @Test
    public void testBuyMultipleStocks() {
      //adding doordash
      String quantity = String.valueOf(generateRandomNumber());
      String cName = "dash";
      buyStocks(cName, quantity, date, "testPortfolio");

      //Adding second
      quantity = String.valueOf(generateRandomNumber());
      cName = "orcl";
      buyStocks(cName, quantity, date, "testPortfolio");
      assertTrue(portfolio.toString().contains(quantity));
      assertTrue(portfolio.toString().contains(cName.toUpperCase()));
      assertTrue(portfolio.toString().contains("testPortfolio"));

      //Adding third
      quantity = String.valueOf(generateRandomNumber());
      cName = "shop";
      buyStocks(cName, quantity, date, "testPortfolio");
      assertTrue(portfolio.toString().contains(quantity));
      assertTrue(portfolio.toString().contains(cName.toUpperCase()));

      //Adding fourth
      quantity = String.valueOf(generateRandomNumber());
      cName = "twtr";
      buyStocks(cName, quantity, date, "testPortfolio");


      assertTrue(portfolio.toString().contains(quantity));
      assertTrue(portfolio.toString().contains(cName.toUpperCase()));

    }

    @Test
    public void testInvalidFilePath() {
      String path = "";
      String expected = "Given path doesnt exist.Please provide valid path.";
      String actual = "";
      try {
        portfolio.loadPortfolioUsingFilePath(path);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      path = null;
      actual = "";
      try {
        portfolio.loadPortfolioUsingFilePath(path);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      path = "123";
      actual = "";
      try {
        portfolio.loadPortfolioUsingFilePath(path);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      path = "P2_output.csv";
      actual = "";
      try {
        portfolio.loadPortfolioUsingFilePath(path);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);
    }

    @Test
    public void testInvalidDate() {
      String date = "";
      String pName = "ff";
      String expected = "Invalid dateFormat provided."
              + "Please provide date in YYYY-MM-DD format only.";
      String actual = "";
      try {
        portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      date = "2022";
      actual = "";
      try {
        portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      date = "abc";
      actual = "";
      try {
        portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      date = "22-10";
      actual = "";
      try {
        portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      date = "-10-10-1000";
      actual = "";
      try {
        portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);
      expected = "Future Date provided.Please provide date less then or equal to today";
      date = "2023-10-10";
      actual = "";
      try {
        portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);
    }


    @Test
    public void testInvalidPortfolioNameToViewTotalValue() {
      String pName = "";
      String date = "2020-10-10";
      String expected = "Invalid portfolioName provided";
      String actual = "";
      try {
        portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      pName = null;
      actual = "";
      expected = "Invalid portfolioName provided";
      try {
        portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      pName = "A2";
      actual = "";
      expected = "Given portfolio doesnt exist.Please provide valid portfolioName.";
      try {
        portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);
    }

    @Test
    public void testCreateAndViewImmediately() {
      String pName = "testPortfolio1";
      buyMultipleStocks(pName, date);
      portfolio.createPortfolioIfCreatedManually(pName);
      File f = new File("userPortfolios/" + pName + "_output.json");
      assertTrue(f.exists());
      portfolio.viewCompositionOfCurrentPortfolio(pName, date);
      String date = "2022-10-01";
      double res = portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      assertTrue(res > 0.0);

      f.deleteOnExit();
    }

    @Test
    public void testCreateAndViewTotalValue() {
      String pName = "testPortfolio2";
      buyMultipleStocks(pName, date);
      portfolio.createPortfolioIfCreatedManually(pName);
      File f = new File("userPortfolios/" + pName + "_output.json");
      assertTrue(f.exists());
      portfolio.viewCompositionOfCurrentPortfolio(pName, date);
      String date = "2022-10-01";
      double res = portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      assertTrue(res > 0.0);
      f.deleteOnExit();
    }

    @Test
    public void testLoadFile() {
      portfolio.loadPortfolioUsingFilePath("userPortfolios/testFlexible_output.json");
      assertFalse(portfolio.toString().isEmpty());
    }

    @Test
    public void testLoadAndViewCompositionAndTotalValue() {
      portfolio.loadPortfolioUsingFilePath("userPortfolios/testFlexible_output.json");
      List<List<String>> results = portfolio.viewCompositionOfCurrentPortfolio(
              "currentInstance", date);
      assertFalse(results.isEmpty());
      double res = portfolio.getTotalValueOfPortfolioOnCertainDate(
              "2022-10-01", "currentInstance");
      assertFalse(res == 0.0);
    }


    @Test
    public void testViewTotalValueForPersistedPortfolio() {
      String date = "2022-10-10";
      String pName = "testFlexible";
      double res = portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      // System.out.println(res);
      assertFalse(res == 0.0);

    }

    @Test
    public void testViewCompositionForCurrentPortfolio() {
      String pName = "currentInstance";
      List<List<String>> results = portfolio.viewCompositionOfCurrentPortfolio(pName, date);
      assertTrue(results.isEmpty());
    }


    @Test
    public void testViewTotalValueForCurrentPortfolio() {
      String date = "2020-10-10";
      String pName = "currentInstance";
      double res = portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      assertTrue(res == 0.0);

    }

    @Test
    public void testLoadLengthyFileAndView() {
      portfolio.loadPortfolioUsingFilePath("userPortfolios/testFlexible_output.json");
      String name = "currentInstance";
      String date = "2022-08-01";
      double res1 = portfolio.getTotalValueOfPortfolioOnCertainDate(date, name);
      //System.out.println(res1);
      date = "2022-09-01";
      double res2 = portfolio.getTotalValueOfPortfolioOnCertainDate(date, name);
      //System.out.println(res2);
      portfolio.viewCompositionOfCurrentPortfolio(name, date);
      assertFalse(res1 == res2);
    }

    private void buyStocks(String cName, String quantity, String date, String pfName) {
      portfolio.buyStocks(cName, quantity, date, com, pfName);
    }

    private void sellStocks(String cName, String quantity, String date, String pfName) {
      portfolio.sellStocks(cName, quantity, date, com, pfName);
    }


    private void buyMultipleStocks(String name, String date) {
      //adding doordash
      String quantity = "15";
      String cName = "dash";
      buyStocks(cName, quantity, date, name);

      //Adding second
      quantity = "25";
      cName = "orcl";
      buyStocks(cName, quantity, date, name);
      assertTrue(portfolio.toString().contains(quantity));
      assertTrue(portfolio.toString().contains(cName.toUpperCase()));
      assertTrue(portfolio.toString().contains(name));

      //Adding third
      quantity = "30";
      cName = "shop";
      buyStocks(cName, quantity, date, name);
      assertTrue(portfolio.toString().contains(quantity));
      assertTrue(portfolio.toString().contains(cName.toUpperCase()));

      quantity = "15";
      cName = "shop";
      buyStocks(cName, quantity, date, name);

      //Adding fourth
      quantity = "10";
      cName = "twtr";
      buyStocks(cName, quantity, date, name);

      assertTrue(portfolio.toString().contains(quantity));
      assertTrue(portfolio.toString().contains(cName.toUpperCase()));
    }


    private void sellMultipleStocks(String name, String date) {
      //adding doordash
      String quantity = "10";
      String cName = "dash";
      sellStocks(cName, quantity, date, name);

      //Adding second
      quantity = "20";
      cName = "orcl";
      sellStocks(cName, quantity, date, name);
      assertTrue(portfolio.toString().contains(quantity));
      assertTrue(portfolio.toString().contains(cName.toUpperCase()));
      assertTrue(portfolio.toString().contains(name));

      //Adding third
      quantity = "15";
      cName = "shop";
      sellStocks(cName, quantity, date, name);
      assertTrue(portfolio.toString().contains(quantity));
      assertTrue(portfolio.toString().contains(cName.toUpperCase()));

      quantity = "15";
      cName = "shop";
      sellStocks(cName, quantity, date, name);

      //Adding fourth
      quantity = "10";
      cName = "twtr";
      sellStocks(cName, quantity, date, name);

      assertTrue(portfolio.toString().contains(quantity));
      assertTrue(portfolio.toString().contains(cName.toUpperCase()));
    }
  }


  public static final class InFlexiblePortfolioImplTest extends AbstractPortfolioTest {

    private final APICustomInterface apiCustomInterface = new APICustomClass("https://www.alphavantage.co/query?function=TIME_SERIES_");

    @Override
    @Before
    public void initialisePortfolio() {
      portfolio = new InFlexiblePortfolioImpl(apiCustomInterface);
    }

    @Test
    public void testViewCompositionForPersistedPortfolio() {
      String pName = "testInFlexible";
      List<List<String>> results = portfolio.viewCompositionOfCurrentPortfolio(pName, null);
      assertFalse(results.isEmpty());
    }


    @Test
    public void testCreatePortfolio() {
      String pname = "";
      String expected = "Invalid portfolioName provided";
      String actual = "";
      try {
        portfolio.createPortfolioIfCreatedManually(pname);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      actual = "";
      pname = null;
      try {
        portfolio.createPortfolioIfCreatedManually(pname);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);
    }

    @Test
    public void testBuyStocks() {
      String cName = "dash";
      String quantity = String.valueOf(generateRandomNumber());
      buyStocks(cName, quantity, "testPortfolio");
      assertTrue(portfolio.toString().contains(quantity));
      assertTrue(portfolio.toString().contains(cName.toUpperCase()));
      assertTrue(portfolio.toString().contains("testPortfolio"));
    }

    @Test
    public void testFractionalStocks() {
      String cName = "dash";
      String quantity = String.valueOf(2.50);
      buyStocks(cName, quantity, "testPortfolio");
      assertTrue(portfolio.toString().contains(quantity));
      assertTrue(portfolio.toString().contains(cName.toUpperCase()));
      assertTrue(portfolio.toString().contains("testPortfolio"));
    }

    @Test
    public void testInvalidStocks() {
      String cName = "dash";
      String quantity = String.valueOf(-100);
      String expected = "Quantity should be always a positive whole number.";
      String actual = "";
      try {
        buyStocks(cName, quantity, "testPortfolio");
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      actual = "";
      quantity = "abc";
      try {
        buyStocks(cName, quantity, "testPortfolio");
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);


      actual = "";
      quantity = String.valueOf(0);
      try {
        buyStocks(cName, quantity, "testPortfolio");
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      actual = "";
      quantity = "";
      try {
        buyStocks(cName, quantity, "testPortfolio");
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);


      actual = "";
      quantity = null;
      expected = "Invalid quantity provided";
      try {
        buyStocks(cName, quantity, "testPortfolio");

      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);
    }

    @Test
    public void testInvalidCompanyName() {

      String cName = "net";
      String quantity = String.valueOf(100);
      String expected = "Given company doesnt exist in our records."
              + "Please provide valid  companyTicker symbol.";
      String actual = "";
      try {
        buyStocks(cName, quantity, "testPortfolio");
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }

      assertEquals(actual, expected);

      //when company name is empty
      cName = "";
      actual = "";
      try {
        buyStocks(cName, quantity, "test");
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      //when company name is null
      cName = null;
      expected = "Invalid companyName provided";
      actual = "";
      try {
        buyStocks(cName, quantity, "testPortfolio");
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

    }

    @Test
    public void testBuyMultipleStocks() {
      //adding doordash
      String quantity = String.valueOf(generateRandomNumber());
      String cName = "dash";
      buyStocks(cName, quantity, "testPortfolio");

      //Adding second
      quantity = String.valueOf(generateRandomNumber());
      cName = "orcl";
      buyStocks(cName, quantity, "testPortfolio");
      assertTrue(portfolio.toString().contains(quantity));
      assertTrue(portfolio.toString().contains(cName.toUpperCase()));
      assertTrue(portfolio.toString().contains("testPortfolio"));

      //Adding third
      quantity = String.valueOf(generateRandomNumber());
      cName = "shop";
      buyStocks(cName, quantity, "testPortfolio");
      assertTrue(portfolio.toString().contains(quantity));
      assertTrue(portfolio.toString().contains(cName.toUpperCase()));

      //Adding fourth
      quantity = String.valueOf(generateRandomNumber());
      cName = "twtr";
      buyStocks(cName, quantity, "testPortfolio");


      assertTrue(portfolio.toString().contains(quantity));
      assertTrue(portfolio.toString().contains(cName.toUpperCase()));

    }

    @Test
    public void testInvalidFilePath() {
      String path = "";
      String expected = "Given path doesnt exist.Please provide valid path.";
      String actual = "";
      try {
        portfolio.loadPortfolioUsingFilePath(path);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      path = null;
      actual = "";
      try {
        portfolio.loadPortfolioUsingFilePath(path);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      path = "123";
      actual = "";
      try {
        portfolio.loadPortfolioUsingFilePath(path);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      path = "P2_output.csv";
      actual = "";
      try {
        portfolio.loadPortfolioUsingFilePath(path);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);
    }

    @Test
    public void testInvalidDate() {
      String date = "";
      String pName = "testPfName";
      String expected = "Invalid dateFormat provided."
              + "Please provide date in YYYY-MM-DD format only.";
      String actual = "";
      try {
        portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      date = "2022";
      actual = "";
      try {
        portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      date = "abc";
      actual = "";
      try {
        portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      date = "22-10";
      actual = "";
      try {
        portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      date = "-10-10-1000";
      actual = "";
      try {
        portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      expected = "Future Date provided.Please provide"
              + " date less then or equal to today";
      date = "2023-10-10";
      actual = "";
      try {
        portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);
    }

    @Test
    public void testViewCostBasis() {

      String exception = "This operation is not supported in Inflexible portfolio";
      String actual = "";
      String pName = "testPfName";
      String date = "2020-10-01";
      try {
        portfolio.getTotalMoneyInvestedOnCertainDate(date, pName);
      } catch (UnsupportedOperationException e) {
        actual = e.getMessage();
      }
      assertEquals(exception, actual);

    }

    @Test
    public void testPortfolioPerformance() {
      String exception = "This operation is not supported in Inflexible portfolio";
      String actual = "";
      String pName = "testPfName";
      String date = "2020-10-01";
      try {
        portfolio.getPortfolioPerformanceOvertime(date, date, pName);
      } catch (UnsupportedOperationException e) {
        actual = e.getMessage();
      }
      assertEquals(exception, actual);
    }

    @Test
    public void testSellStocks() {
      String exception = "This operation is not supported in Inflexible portfolio";
      String actual = "";
      String pName = "testPfName";
      String date = "2020-10-01";
      try {
        portfolio.sellStocks("dummy", "10", date, "20", pName);
      } catch (UnsupportedOperationException e) {
        actual = e.getMessage();
      }
      assertEquals(exception, actual);
    }

    @Test
    public void testUpdatePortfolio() {
      String exception = "This operation is not supported in Inflexible portfolio";
      String actual = "";
      String pName = "testPfName";
      String date = "2020-10-01";
      try {
        portfolio.updatePortfolio("dummy", "10", "buy", date, "20", pName);
      } catch (UnsupportedOperationException e) {
        actual = e.getMessage();
      }
      assertEquals(exception, actual);
    }


    @Test
    public void testInvalidPortfolioNameToViewTotalValue() {
      String pName = "";
      String date = "2020-10-10";
      String expected = "Invalid portfolioName provided";
      String actual = "";
      try {
        portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      pName = null;
      actual = "";
      expected = "Invalid portfolioName provided";
      try {
        portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      pName = "A2";
      actual = "";
      expected = "Given portfolio doesnt exist.Please provide valid portfolioName.";
      try {
        portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);
    }

    @Test
    public void testCreateAndViewImmediately() {
      String pName = "testPortfolio1";
      buyMultipleStocks(pName);
      portfolio.createPortfolioIfCreatedManually(pName);
      File f = new File("userPortfolios/" + pName + "_output.json");
      assertTrue(f.exists());
      portfolio.viewCompositionOfCurrentPortfolio(pName, null);
      String date = "2022-10-01";
      double res = portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      assertTrue(res > 0.0);

      f.deleteOnExit();
    }

    @Test
    public void testCreateAndViewTotalValue() {
      String pName = "testPortfolio2";
      buyMultipleStocks(pName);
      portfolio.createPortfolioIfCreatedManually(pName);
      File f = new File("userPortfolios/" + pName + "_output.json");
      assertTrue(f.exists());
      portfolio.viewCompositionOfCurrentPortfolio(pName, null);
      String date = "2022-10-01";
      double res = portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      assertTrue(res > 0.0);
      f.deleteOnExit();
    }

    @Test
    public void testLoadFile() {
      portfolio.loadPortfolioUsingFilePath("userPortfolios/testInFlexible_output.json");
      assertFalse(portfolio.toString().isEmpty());
    }

    @Test
    public void testLoadAndViewCompositionAndTotalValue() {
      portfolio.loadPortfolioUsingFilePath("userPortfolios/testInFlexible_output.json");
      List<List<String>> results = portfolio.viewCompositionOfCurrentPortfolio(
              "currentInstance", null);
      assertFalse(results.isEmpty());
      double res = portfolio.getTotalValueOfPortfolioOnCertainDate(
              "2022-10-01", "currentInstance");
      assertFalse(res == 0.0);
    }


    @Test
    public void testViewTotalValueForPersistedPortfolio() {
      String date = "2022-10-10";
      String pName = "testInFlexible";
      Double res = portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      // System.out.println(res);
      assertFalse(res == 0.0);

    }

    @Test
    public void testViewCompositionForCurrentPortfolio() {
      String pName = "currentInstance";
      List<List<String>> results = portfolio.viewCompositionOfCurrentPortfolio(pName, null);
      assertTrue(results.isEmpty());
    }


    @Test
    public void testViewTotalValueForCurrentPortfolio() {
      String date = "2020-10-10";
      String pName = "currentInstance";
      double res = portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      assertTrue(res == 0.0);

    }

    @Test
    public void testLoadLengthyFileAndView() {
      portfolio.loadPortfolioUsingFilePath("userPortfolios/testInFlexible_output.json");
      String name = "currentInstance";
      String date = "2022-08-01";
      double res1 = portfolio.getTotalValueOfPortfolioOnCertainDate(date, name);
      // System.out.println(res1);
      date = "2022-09-01";
      double res2 = portfolio.getTotalValueOfPortfolioOnCertainDate(date, name);
      // System.out.println(res2);
      portfolio.viewCompositionOfCurrentPortfolio(name, null);
      // System.out.println(portfolio.toString());
      assertFalse(res1 == res2);
    }

    private void buyStocks(String cName, String quantity, String pfName) {
      portfolio.buyStocks(cName, quantity, null, "0", pfName);
    }

    @Test
    public void testInvalidPortfolioNameToView() {
      String pName = "";
      String expected = "Invalid portfolioName provided";
      String actual = "";
      try {
        portfolio.viewCompositionOfCurrentPortfolio(pName, null);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      pName = null;
      actual = "";
      expected = "Invalid portfolioName provided";
      try {
        portfolio.viewCompositionOfCurrentPortfolio(pName, null);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      pName = "A2";
      actual = "";
      expected = "Given portfolio doesnt exist.Please provide valid portfolioName.";
      try {
        portfolio.viewCompositionOfCurrentPortfolio(pName, null);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);
    }


    private void buyMultipleStocks(String name) {
      //adding doordash
      String quantity = String.valueOf(generateRandomNumber());
      String cName = "dash";
      buyStocks(cName, quantity, name);

      //Adding second
      quantity = String.valueOf(generateRandomNumber());
      cName = "orcl";
      buyStocks(cName, quantity, name);
      assertTrue(portfolio.toString().contains(quantity));
      assertTrue(portfolio.toString().contains(cName.toUpperCase()));
      assertTrue(portfolio.toString().contains("testPortfolio"));

      //Adding third
      quantity = String.valueOf(generateRandomNumber());
      cName = "shop";
      buyStocks(cName, quantity, name);
      assertTrue(portfolio.toString().contains(quantity));
      assertTrue(portfolio.toString().contains(cName.toUpperCase()));

      quantity = String.valueOf(generateRandomNumber());
      cName = "shop";
      buyStocks(cName, quantity, name);

      //Adding fourth
      quantity = String.valueOf(generateRandomNumber());
      cName = "twtr";
      buyStocks(cName, quantity, name);

      assertTrue(portfolio.toString().contains(quantity));
      assertTrue(portfolio.toString().contains(cName.toUpperCase()));
    }
  }
}
