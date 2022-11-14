import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import stocks.model.FlexiblePortfolioImpl;
import stocks.model.InFlexiblePortfolioImpl;
import stocks.model.Portfolio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

abstract class AbstractPortfolioTest {
  protected Portfolio portfolio;




  public static final class flexiblePortfolioImplTest extends AbstractPortfolioTest{

    @Before
    public void setUp() {
      portfolio = new FlexiblePortfolioImpl();
    }


  }


  public static final class InFlexiblePortfolioImplTest extends AbstractPortfolioTest{

    private final int min = 1;
    private final int max = 100;

    private int generateRandomNumber() {
      return (int) (Math.random() * (max - min + 1) + min);
    }

    @Before
    public void setUp() {
      portfolio = new InFlexiblePortfolioImpl();
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
      buyStocks(quantity, cName, "testPortfolio");
      assertTrue(portfolio.toString().contains(quantity));
      assertTrue(portfolio.toString().contains(cName.toUpperCase()));
      assertTrue(portfolio.toString().contains("testPortfolio"));
    }

    @Test
    public void testFractionalStocks() {
      String cName = "dash";
      String quantity = String.valueOf(2.50);
      buyStocks(quantity, cName, "testPortfolio");
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
        buyStocks(quantity, cName, "testPortfolio");
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      actual = "";
      quantity = "abc";
      try {
        buyStocks(quantity, cName, "testPortfolio");
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);


      actual = "";
      quantity = String.valueOf(0);
      try {
        buyStocks(quantity, cName, "testPortfolio");
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      actual = "";
      quantity = "";
      try {
        buyStocks(quantity, cName, "testPortfolio");
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);


      actual = "";
      quantity = null;
      expected = "Invalid quantity provided";
      try {
        buyStocks(quantity, cName, "testPortfolio");
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
        buyStocks(quantity, cName, "testPortfolio");
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }

      assertEquals(actual, expected);

      //when company name is empty
      cName = "";
      actual = "";
      try {
        buyStocks(quantity, cName, "test");
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      //when company name is null
      cName = null;
      expected = "Invalid companyName provided";
      actual = "";
      try {
        buyStocks(quantity, cName, "testPortfolio");
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
      buyStocks(quantity, cName, "testPortfolio");

      //Adding second
      quantity = String.valueOf(generateRandomNumber());
      cName = "orcl";
      buyStocks(quantity, "orcl", "testPortfolio");
      assertTrue(portfolio.toString().contains(quantity));
      assertTrue(portfolio.toString().contains(cName.toUpperCase()));
      assertTrue(portfolio.toString().contains("testPortfolio"));

      //Adding third
      quantity = String.valueOf(generateRandomNumber());
      cName = "shop";
      buyStocks(quantity, cName, "testPortfolio");
      assertTrue(portfolio.toString().contains(quantity));
      assertTrue(portfolio.toString().contains(cName.toUpperCase()));

      //Adding fourth
      quantity = String.valueOf(generateRandomNumber());
      cName = "twtr";
      buyStocks(quantity, cName, "testPortfolio");


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
      String expected = "Invalid dateFormat provided.Please provide date in YYYY-MM-DD format only.";
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
    public void testInvalidPortfolioNameToView() {
      String pName = "";
      String expected = "Invalid portfolioName provided";
      String actual = "";
      try {
        portfolio.viewCompositionOfCurrentPortfolio(pName,null);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      pName = null;
      actual = "";
      expected = "Invalid portfolioName provided";
      try {
        portfolio.viewCompositionOfCurrentPortfolio(pName,null);
      } catch (IllegalArgumentException e) {
        actual = e.getMessage();
      }
      assertEquals(actual, expected);

      pName = "A2";
      actual = "";
      expected = "Given portfolio doesnt exist.Please provide valid portfolioName.";
      try {
        portfolio.viewCompositionOfCurrentPortfolio(pName,null);
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
      buyMultipleStocks(pName);
      portfolio.createPortfolioIfCreatedManually(pName);
      File f = new File("userPortfolios/" + pName + "_output.json");
      assertTrue(f.exists());
      portfolio.viewCompositionOfCurrentPortfolio(pName,null);
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
      portfolio.viewCompositionOfCurrentPortfolio(pName,null);
      String date = "2022-10-01";
      double res = portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      assertTrue(res > 0.0);
      f.deleteOnExit();
    }

    @Test
    public void testLoadFile() {
      portfolio.loadPortfolioUsingFilePath("userPortfolios/aa_output.json");
      assertFalse(portfolio.toString().isEmpty());
    }

    @Test
    public void testLoadAndViewCompositionAndTotalValue() {
      portfolio.loadPortfolioUsingFilePath("userPortfolios/aa_output.json");
      List<List<String>> results = portfolio.viewCompositionOfCurrentPortfolio("currentInstance",null);
      assertFalse(results.isEmpty());
      double res = portfolio.getTotalValueOfPortfolioOnCertainDate("2022-10-01", "currentInstance");
      assertFalse(res == 0.0);
    }

    @Test
    public void testViewCompositionForPersistedPortfolio() {
      String pName = "aa";
      List<List<String>> results = portfolio.viewCompositionOfCurrentPortfolio(pName,null);
      assertFalse(results.isEmpty());
    }


    @Test
    public void testViewTotalValueForPersistedPortfolio() {
      String date = "2022-10-10";
      String pName = "aa";
      Double res = portfolio.getTotalValueOfPortfolioOnCertainDate(date, pName);
      System.out.println(res);
      assertFalse(res == 0.0);

    }

    @Test
    public void testViewCompositionForCurrentPortfolio() {
      String pName = "currentInstance";
      List<List<String>> results = portfolio.viewCompositionOfCurrentPortfolio(pName,null);
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
      portfolio.loadPortfolioUsingFilePath("userPortfolios/aa_output.json");
      String name = "currentInstance";
      String date = "2022-08-01";
      double res1 = portfolio.getTotalValueOfPortfolioOnCertainDate(date, name);
      System.out.println(res1);
      date = "2022-09-01";
      double res2 = portfolio.getTotalValueOfPortfolioOnCertainDate(date, name);
      System.out.println(res2);
      portfolio.viewCompositionOfCurrentPortfolio(name,null);
      System.out.println(portfolio.toString());
      assertFalse(res1 == res2);
    }

    private void buyStocks(String quantity, String cName, String pfName) {
      portfolio.buyStocks(quantity, cName,null, pfName);
    }

    private void buyMultipleStocks(String name) {
      //adding doordash
      String quantity = String.valueOf(generateRandomNumber());
      String cName = "dash";
      buyStocks(quantity, cName, name);

      //Adding second
      quantity = String.valueOf(generateRandomNumber());
      cName = "orcl";
      buyStocks(quantity, "orcl", name);
      assertTrue(portfolio.toString().contains(quantity));
      assertTrue(portfolio.toString().contains(cName.toUpperCase()));
      assertTrue(portfolio.toString().contains("testPortfolio"));

      //Adding third
      quantity = String.valueOf(generateRandomNumber());
      cName = "shop";
      buyStocks(quantity, cName, name);
      assertTrue(portfolio.toString().contains(quantity));
      assertTrue(portfolio.toString().contains(cName.toUpperCase()));

      quantity = String.valueOf(generateRandomNumber());
      cName = "shop";
      buyStocks(quantity, cName, name);

      //Adding fourth
      quantity = String.valueOf(generateRandomNumber());
      cName = "twtr";
      buyStocks(quantity, cName, name);

      assertTrue(portfolio.toString().contains(quantity));
      assertTrue(portfolio.toString().contains(cName.toUpperCase()));
    }
  }


}
