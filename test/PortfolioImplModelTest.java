import org.junit.Before;
import org.junit.Test;

import stocks.model.PortfolioImplModel;
import stocks.model.PortfolioModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PortfolioImplModelTest {

  private PortfolioModel model;

  int min=0;
  int max=100;

  @Before
  public void setUp(){
    model = new PortfolioImplModel();
  }

  private int generateRandomNumber() {
    return (int) (Math.random() * (max - min + 1) + min);
  }

  @Test
  public void testCreatePortfolio(){
    //model.createPortfolioIfCreatedManually();
  }

  @Test
  public void testBuyStocks(){
    String cName= "dash";
    String quantity = String.valueOf(generateRandomNumber());
    buyStocks(quantity,cName,"testPortfolio");
    assertTrue(model.toString().contains(quantity));
    assertTrue(model.toString().contains(cName.toUpperCase()));
    assertTrue(model.toString().contains("testPortfolio"));
    assertEquals(1,model.size());
  }

  @Test
  public void testFractionalStocks(){
    String cName= "dash";
    String quantity = String.valueOf(2.50);
    buyStocks(quantity,cName,"testPortfolio");
    assertTrue(model.toString().contains(quantity));
    assertTrue(model.toString().contains(cName.toUpperCase()));
    assertTrue(model.toString().contains("testPortfolio"));
    assertEquals(1,model.size());
  }

  @Test
  public void testInvalidStocks(){
    String cName= "dash";
    String quantity = String.valueOf(-100);
    String expected = "Quantity should be always a positive whole number.";
    String actual ="";
    try {
      buyStocks(quantity, cName, "testPortfolio");
    } catch(IllegalArgumentException e){
      actual = e.getMessage();
    }
    assertEquals(0,model.size());
    assertEquals(actual,expected);

    actual ="";
    quantity = "abc";
    try {
      buyStocks(quantity, cName, "testPortfolio");
    } catch(IllegalArgumentException e){
      actual = e.getMessage();
    }
    assertEquals(0,model.size());
    assertEquals(actual,expected);


    actual ="";
    quantity = String.valueOf(0);
    try {
      buyStocks(quantity, cName, "testPortfolio");
    } catch(IllegalArgumentException e){
      actual = e.getMessage();
    }
    assertEquals(0,model.size());
    assertEquals(actual,expected);

    actual ="";
    quantity = "";
    try {
      buyStocks(quantity, cName, "testPortfolio");
    } catch(IllegalArgumentException e){
      actual = e.getMessage();
    }
    assertEquals(0,model.size());
    assertEquals(actual,expected);


    actual ="";
    quantity = null;
    expected="Invalid quantity provided";
    try {
      buyStocks(quantity, cName, "testPortfolio");
    } catch(IllegalArgumentException e){
      actual = e.getMessage();
    }
    assertEquals(0,model.size());
    assertEquals(actual,expected);
  }

  @Test
  public void testInvalidCompanyName(){

    String cName= "goog";
    String quantity = String.valueOf(100);
    String expected = "Given company doesnt exist in our records.Please provide valid  companyTicker symbol.";
    String actual ="";
    try {
      buyStocks(quantity, cName, "testPortfolio");
    } catch(IllegalArgumentException e){
      actual = e.getMessage();
    }
    assertEquals(0,model.size());
    assertEquals(actual,expected);

    //when company name is empty
    cName= "";
    actual ="";
    try {
      buyStocks(quantity, cName, "testPortfolio");
    } catch(IllegalArgumentException e){
      actual = e.getMessage();
    }
    assertEquals(0,model.size());
    assertEquals(actual,expected);

    //when company name is null
    cName= null;
    expected="Invalid companyName provided";
    actual ="";
    try {
      buyStocks(quantity, cName, "testPortfolio");
    } catch(IllegalArgumentException e){
      actual = e.getMessage();
    }
    assertEquals(0,model.size());
    assertEquals(actual,expected);

  }

  @Test
  public void testBuyMultipleStocks(){
    //adding doordash
    String quantity = String.valueOf(generateRandomNumber());
    String cName= "dash";
    buyStocks(quantity,cName,"testPortfolio");

    //Adding second
    quantity = String.valueOf(generateRandomNumber());
    cName= "orcl";
    buyStocks(quantity,"orcl","testPortfolio");
    assertTrue(model.toString().contains(quantity));
    assertTrue(model.toString().contains(cName.toUpperCase()));
    assertTrue(model.toString().contains("testPortfolio"));

    //Adding third
    quantity = String.valueOf(generateRandomNumber());
    cName= "shop";
    buyStocks(quantity,cName,"testPortfolio");
    assertTrue(model.toString().contains(quantity));
    assertTrue(model.toString().contains(cName.toUpperCase()));

    //Adding fourth
    quantity = String.valueOf(generateRandomNumber());
    cName= "twtr";
    buyStocks(quantity,cName,"testPortfolio");


    assertTrue(model.toString().contains(quantity));
    assertTrue(model.toString().contains(cName.toUpperCase()));
    assertEquals(4,model.size());

  }

  @Test
  public void testInvalidFilePath(){

  }

  @Test
  public void testInvalidDate(){
    String date ="";
    String pName ="testPfName";
    String expected="Invalid dateFormat provided.Please provide date in YYYY-MM-DD format only.";
    String actual ="";
    try {
      model.getTotalValueOfPortfolioOnCertainDate(date, pName);
    } catch(IllegalArgumentException e){
      actual = e.getMessage();
    }
    assertEquals(actual,expected);

    date ="2022";
    actual ="";
    try {
      model.getTotalValueOfPortfolioOnCertainDate(date, pName);
    } catch(IllegalArgumentException e){
      actual = e.getMessage();
    }
    assertEquals(actual,expected);

    date ="abc";
    actual ="";
    try {
      model.getTotalValueOfPortfolioOnCertainDate(date, pName);
    } catch(IllegalArgumentException e){
      actual = e.getMessage();
    }
    assertEquals(actual,expected);

    date ="22-10";
    actual ="";
    try {
      model.getTotalValueOfPortfolioOnCertainDate(date, pName);
    } catch(IllegalArgumentException e){
      actual = e.getMessage();
    }
    assertEquals(actual,expected);

    date ="-10-10-1000";
    actual ="";
    try {
      model.getTotalValueOfPortfolioOnCertainDate(date, pName);
    } catch(IllegalArgumentException e){
      actual = e.getMessage();
    }
    assertEquals(actual,expected);

    date ="2023-10-10";
    actual ="";
    try {
      model.getTotalValueOfPortfolioOnCertainDate(date, pName);
    } catch(IllegalArgumentException e){
      actual = e.getMessage();
    }
    assertEquals(actual,expected);
  }

  @Test
  public void testInvalidPortfolioNameToView(){

  }

  @Test
  public void testViewComposition(){
    //model.viewCompositionOfCurrentPortfolio();
  }

  private void buyStocks(String quantity,String cName,String pfName){
    model.buyStocks(quantity,cName,pfName);
  }
}
