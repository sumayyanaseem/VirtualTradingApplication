import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import stocks.model.PortfolioImplModel;
import stocks.model.PortfolioModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PortfolioImplModelTest {

  private PortfolioModel model;

  int min = 1;
  int max = 100;

  @Before
  public void setUp() {
    model = new PortfolioImplModel();
  }

  private int generateRandomNumber() {
    return (int) (Math.random() * (max - min + 1) + min);
  }

  @Test
  public void testCreatePortfolio() {
    String pname="";
    String expected="Invalid portfolioName provided";
    String actual ="";
    try {
      model.createPortfolioIfCreatedManually(pname);
    } catch(IllegalArgumentException e){
      actual = e.getMessage();
    }
    assertEquals(actual,expected);

    actual ="";
    pname=null;
    try {
      model.createPortfolioIfCreatedManually(pname);
    } catch(IllegalArgumentException e){
      actual = e.getMessage();
    }
    assertEquals(actual,expected);
  }

  @Test
  public void testBuyStocks() {
    String cName = "dash";
    String quantity = String.valueOf(generateRandomNumber());
    buyStocks(quantity, cName, "testPortfolio");
    assertTrue(model.toString().contains(quantity));
    assertTrue(model.toString().contains(cName.toUpperCase()));
    assertTrue(model.toString().contains("testPortfolio"));
  }

  @Test
  public void testFractionalStocks() {
    String cName = "dash";
    String quantity = String.valueOf(2.50);
    buyStocks(quantity, cName, "testPortfolio");
    assertTrue(model.toString().contains(quantity));
    assertTrue(model.toString().contains(cName.toUpperCase()));
    assertTrue(model.toString().contains("testPortfolio"));
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

    String cName = "goog";
    String quantity = String.valueOf(100);
    String expected = "Given company doesnt exist in our records.Please provide valid  companyTicker symbol.";
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
      buyStocks(quantity, cName, "testPortfolio");
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
    assertTrue(model.toString().contains(quantity));
    assertTrue(model.toString().contains(cName.toUpperCase()));
    assertTrue(model.toString().contains("testPortfolio"));

    //Adding third
    quantity = String.valueOf(generateRandomNumber());
    cName = "shop";
    buyStocks(quantity, cName, "testPortfolio");
    assertTrue(model.toString().contains(quantity));
    assertTrue(model.toString().contains(cName.toUpperCase()));

    //Adding fourth
    quantity = String.valueOf(generateRandomNumber());
    cName = "twtr";
    buyStocks(quantity, cName, "testPortfolio");


    assertTrue(model.toString().contains(quantity));
    assertTrue(model.toString().contains(cName.toUpperCase()));

  }

  @Test
  public void testInvalidFilePath() {
    String path = "";
    String expected = "Given path doesnt exist.Please provide valid path.";
    String actual = "";
    try {
      model.createPortfolioUsingFilePath(path);
    } catch (IllegalArgumentException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    path = null;
    actual = "";
    try {
      model.createPortfolioUsingFilePath(path);
    } catch (IllegalArgumentException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    path = "123";
    actual = "";
    try {
      model.createPortfolioUsingFilePath(path);
    } catch (IllegalArgumentException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    path = "P2_output.csv";
    actual = "";
    try {
      model.createPortfolioUsingFilePath(path);
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
      model.getTotalValueOfPortfolioOnCertainDate(date, pName);
    } catch (IllegalArgumentException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    date = "2022";
    actual = "";
    try {
      model.getTotalValueOfPortfolioOnCertainDate(date, pName);
    } catch (IllegalArgumentException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    date = "abc";
    actual = "";
    try {
      model.getTotalValueOfPortfolioOnCertainDate(date, pName);
    } catch (IllegalArgumentException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    date = "22-10";
    actual = "";
    try {
      model.getTotalValueOfPortfolioOnCertainDate(date, pName);
    } catch (IllegalArgumentException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    date = "-10-10-1000";
    actual = "";
    try {
      model.getTotalValueOfPortfolioOnCertainDate(date, pName);
    } catch (IllegalArgumentException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    date = "2023-10-10";
    actual = "";
    try {
      model.getTotalValueOfPortfolioOnCertainDate(date, pName);
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
      model.viewCompositionOfCurrentPortfolio(pName);
    } catch (IllegalArgumentException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    pName = null;
    actual = "";
    expected="Invalid portfolioName provided";
    try {
      model.viewCompositionOfCurrentPortfolio(pName);
    } catch (IllegalArgumentException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    pName = "A2";
    actual = "";
    expected="Given portfolio doesnt exist.Please provide valid portfolioName.";
    try {
      model.viewCompositionOfCurrentPortfolio(pName);
    } catch (IllegalArgumentException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);
  }


  @Test
  public void testInvalidPortfolioNameToViewTotalValue() {
    String pName = "";
    String date="2020-10-10";
    String expected = "Invalid portfolioName provided";
    String actual = "";
    try {
      model.getTotalValueOfPortfolioOnCertainDate(date,pName);
    } catch (IllegalArgumentException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    pName = null;
    actual = "";
    expected="Invalid portfolioName provided";
    try {
      model.getTotalValueOfPortfolioOnCertainDate(date,pName);
    } catch (IllegalArgumentException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);

    pName = "A2";
    actual = "";
    expected="Given portfolio doesnt exist.Please provide valid portfolioName.";
    try {
      model.getTotalValueOfPortfolioOnCertainDate(date,pName);
    } catch (IllegalArgumentException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);
  }

  @Test
  public void testCreateAndViewImmediately() {
    String pName ="testPortfolio1";
    buyMultipleStocks(pName);
    model.createPortfolioIfCreatedManually(pName);
    File f = new File("/Users/achennak/2022/fall/pdp/Group Projects/Stocks/userPortfolios/"+pName+"_output.csv");
    assertTrue(f.exists());
    model.viewCompositionOfCurrentPortfolio(pName);
    String date="2022-10-01";
    double res =model.getTotalValueOfPortfolioOnCertainDate(date,pName);
    assertTrue(res>0.0);

    f.deleteOnExit();
  }

  @Test
  public void testCreateAndViewTotalValue() {
    String pName ="testPortfolio2";
    buyMultipleStocks(pName);
    model.createPortfolioIfCreatedManually(pName);
    File f = new File("/Users/achennak/2022/fall/pdp/Group Projects/Stocks/userPortfolios/"+pName+"_output.csv");
    assertTrue(f.exists());
    model.viewCompositionOfCurrentPortfolio(pName);
    String date="2009-10-01";
    double res =model.getTotalValueOfPortfolioOnCertainDate(date,pName);
    assertTrue(res>0.0);

    f.deleteOnExit();
  }

  @Test
  public void testLoadFile() {
     model.createPortfolioUsingFilePath("/Users/achennak/2022/fall/pdp/Group Projects/Stocks/test/test.csv");
     assertFalse(model.toString().isEmpty());
  }

  @Test
  public void testLoadAndViewCompositionAndTotalValue(){
    model.createPortfolioUsingFilePath("/Users/achennak/2022/fall/pdp/Group Projects/Stocks/test/test.csv");
    List<List<String>> results=model.viewCompositionOfCurrentPortfolio("currentInstance");
    assertFalse(results.isEmpty());
    double res=model.getTotalValueOfPortfolioOnCertainDate("2022-10-01","currentInstance");
    assertFalse(res==0.0);
  }

  @Test
  public void testViewCompositionForPersistedPortfolio() {
    String pName = "P3";
    List<List<String>> results=model.viewCompositionOfCurrentPortfolio(pName);
    assertFalse(results.isEmpty());
  }


  @Test
  public void testViewTotalValueForPersistedPortfolio() {
    String date="2020-10-10";
    String pName = "P3";
    Double res=model.getTotalValueOfPortfolioOnCertainDate(date,pName);
    System.out.println(res);
    assertFalse(res==0.0);

  }

  @Test
  public void testViewCompositionForCurrentPortfolio() {
    String pName = "currentInstance";
    List<List<String>> results=model.viewCompositionOfCurrentPortfolio(pName);
    assertTrue(results.isEmpty());
  }


  @Test
  public void testViewTotalValueForCurrentPortfolio() {
    String date="2020-10-10";
    String pName = "currentInstance";
    double res=model.getTotalValueOfPortfolioOnCertainDate(date,pName);
    assertTrue(res==0.0);

  }

  private void buyStocks(String quantity, String cName, String pfName) {
    model.buyStocks(quantity, cName, pfName);
  }

  private void buyMultipleStocks(String name){
    //adding doordash
    String quantity = String.valueOf(generateRandomNumber());
    String cName = "dash";
    buyStocks(quantity, cName, name);

    //Adding second
    quantity = String.valueOf(generateRandomNumber());
    cName = "orcl";
    buyStocks(quantity, "orcl", name);
    assertTrue(model.toString().contains(quantity));
    assertTrue(model.toString().contains(cName.toUpperCase()));
    assertTrue(model.toString().contains("testPortfolio"));

    //Adding third
    quantity = String.valueOf(generateRandomNumber());
    cName = "shop";
    buyStocks(quantity, cName, name);
    assertTrue(model.toString().contains(quantity));
    assertTrue(model.toString().contains(cName.toUpperCase()));

    quantity = String.valueOf(generateRandomNumber());
    cName = "shop";
    buyStocks(quantity, cName, name);

    //Adding fourth
    quantity = String.valueOf(generateRandomNumber());
    cName = "twtr";
    buyStocks(quantity, cName, name);

    assertTrue(model.toString().contains(quantity));
    assertTrue(model.toString().contains(cName.toUpperCase()));
  }
}
