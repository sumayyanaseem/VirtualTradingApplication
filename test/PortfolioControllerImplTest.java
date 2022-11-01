import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.NoSuchElementException;

import stocks.controller.PortfolioController;
import stocks.controller.PortfolioControllerImpl;
import stocks.model.PortfolioModel;
import stocks.view.PortfolioView;
import stocks.view.PortfolioViewImpl;
import static org.junit.Assert.assertTrue;

public class PortfolioControllerImplTest {

  class MockModel implements PortfolioModel {

    @Override
    public void buyStocks(String quantity, String CompanyName, String portfolioName)  {

    }

    @Override
    public double getTotalValueOfPortfolioOnCertainDate(String date, String portfolioName) {
      return 0;
    }

    @Override
    public void createPortfolioUsingFilePath(String filePath)  {

    }

    @Override
    public List<List<String>> viewCompositionOfCurrentPortfolio(String portfolioName) {
      return null;
    }


    @Override
    public void createPortfolioIfCreatedManually(String portfolioName) {

    }
  }


  private InputStream in;

  private PortfolioView view;

  private PrintStream out;

  private PortfolioController portfolioController;

  private OutputStream bytes;

  @Before
  public void setUp(){
    in = new ByteArrayInputStream("1\n".getBytes());
    bytes = new ByteArrayOutputStream();
    out = new PrintStream(bytes);
    view = new PortfolioViewImpl(out);
    portfolioController = new PortfolioControllerImpl(in,view);
  }


  @Test(expected = NoSuchElementException.class)
  public void initialState(){
    String expected="Enter 1: To trade stocks 2: To view the portfolio composition";
    in = new ByteArrayInputStream("".getBytes());
    portfolioController = new PortfolioControllerImpl(in,view);
    portfolioController.start(new MockModel());
    assertTrue(bytes.toString().contains(expected));
  }

  @Test(expected = NoSuchElementException.class)
  public void testInvalidInputs(){
    String expected="Enter 1: To trade stocks 2: To view the portfolio composition";
    String error="Invalid input provided.Please provide a valid input (either 1 or 2)";
    in = new ByteArrayInputStream("1\n4\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in,view);
    portfolioController.start(new MockModel());
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(error));
  }

  @Test(expected = NoSuchElementException.class)
  public void testStart(){
    String expected="Enter 1: To create Portfolio using external file  2: To create manually";
    in = new ByteArrayInputStream("1\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in,view);
    portfolioController.start(new MockModel());
    assertTrue(bytes.toString().contains(expected));
  }

  @Test(expected = NoSuchElementException.class)
  public void testAskUserWhatHeWantsToView(){
    in = new ByteArrayInputStream("2\n".getBytes());
    String expected="Enter 1: To view composition of existing portfolio . 2: To get TotalValue of a portfolio";
    portfolioController = new PortfolioControllerImpl(in,view);
    portfolioController.start(new MockModel());
    assertTrue(bytes.toString().contains(expected));
  }

 /* @Test(expected = NoSuchElementException.class)
  public void testCreateOrUpdatePortfolio() {
    String expected = "Enter 1: To create Portfolio using external file  2: To create manually";
    in = new ByteArrayInputStream("1\n1\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    portfolioController.start(new MockModel());
    assertTrue(bytes.toString().contains(expected));
  }*/

  @Test(expected = NoSuchElementException.class)
  public void testGetCreatePortfolioChoice(){
    String expected = "Enter 1: To create Portfolio using external file  2: To create manually";
    in = new ByteArrayInputStream("1\n1\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    portfolioController.start(new MockModel());
    assertTrue(bytes.toString().contains(expected));
  }

  @Test(expected = NoSuchElementException.class)
  public void testCreatePortfolioUsingFilePath(){
    String expected = "Enter the path of File which is used to create Portfolio";
    in = new ByteArrayInputStream("1\n1\n1\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    portfolioController.start(new MockModel());
    //System.out.println(out.toString());
    assertTrue(bytes.toString().contains(expected));
  }

  @Test(expected = NoSuchElementException.class)
  public void testInvalidInputs2(){
    String expected="Enter the path of File which is used to create Portfolio";
    String error="Invalid input provided.Please provide a valid input (either 1 or 2)";
    in = new ByteArrayInputStream("1\n1\n4\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in,view);
    portfolioController.start(new MockModel());
    System.out.println(bytes.toString());
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(error));

  }

  @Test(expected = NoSuchElementException.class)
  public void testCreatePortfolioManuallyEndToEnd(){
    String expected = "Enter the name of the Portfolio";
    in = new ByteArrayInputStream("1\n1\n2\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    portfolioController.start(new MockModel());
    assertTrue(bytes.toString().contains(expected));
  }

  @Test
  public void testStoppingCondition(){

  }

  @Test
  public void testCreateNewPortfolioForCurrentUser(){

  }

  @Test
  public void testFinalExitCondition(){

  }

  @Test
  public void testBuyOrSellStocks(){

  }

  /*PortfolioView portfolioView;
  InputStream in;

  PrintStream out;

  @Before
  public void set(){
    in = new ByteArrayInputStream("1".getBytes());
    OutputStream bytes = new ByteArrayOutputStream();
    out = new PrintStream(bytes);
    portfolioView = new PortfolioViewImpl(in,out);
  }


  @Test
  public void testCallToViewToChooseCreateOrView(){
    String output =portfolioView.callToViewToChooseCreateOrView();
    assertEquals(output,"1");
  }

  @Test
  public void testCallToViewOnHowToCreatePortfolio(){
    String output =portfolioView.askUserOnHowToCreatePortfolio();
    assertEquals(output,"1");
  }

  @Test
  public void testCallToViewToChoiceOption(){
    in = new ByteArrayInputStream("2".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    String output =portfolioView.getBuyOrSellChoiceFromUser();
    assertEquals(output,"2");
  }

  @Test
  public void testCallToViewToGetFilePath(){
    in = new ByteArrayInputStream("src/stocks/view/".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    String output =portfolioView.getFilePath();
    assertEquals(output,"src/stocks/view/");
  }

  @Test
  public void testCallToViewToAskQuantity(){
    in = new ByteArrayInputStream("100".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    String output =portfolioView.getQuantity();
    assertEquals(output,"100");
  }

  @Test
  public void testInvalidQuantity(){
    in = new ByteArrayInputStream("0".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    String expected="Quantity should be always a positive whole number";
    String actual="";
    try {
      portfolioView.getQuantity();
    } catch(IllegalArgumentException e){
      actual=e.getMessage();
    }
    assertEquals(expected,actual);
    actual="";
    in = new ByteArrayInputStream("-10".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    try {
      portfolioView.getQuantity();
    } catch(IllegalArgumentException e){
      actual=e.getMessage();
    }
    assertEquals(expected,actual);
  }

  @Test
  public void testCallToViewToAskCompanyTicker(){
    in = new ByteArrayInputStream("GOOG".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    String output =portfolioView.getCompanyTicker();
    assertEquals(output,"GOOG");
  }

  @Test
  public void testAskUserIfHeWantsToContinueTradingInCurrentPortfolio(){
    String output =portfolioView.askUserIfHeWantsToContinueTradingInCurrentPortfolio();
    assertEquals(output,"1");
  }

  @Test
  public void testCheckIfUserWantsToExitCompletely(){
    String output =portfolioView.checkIfUserWantsToExitCompletely();
    assertEquals(output,"1");
  }

  @Test
  public void testCreateOrUpdateExistingPortfolio(){
    String output =portfolioView.createOrUpdateExistingPortfolio();
    assertEquals(output,"1");
  }

  @Test
  public void testGetPortfolioNameToCreate(){
    in = new ByteArrayInputStream("P2".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    String output =portfolioView.getPortfolioNameToViewOrUpdate();
    assertEquals(output,"P2");
  }

  @Test
  public void testGetPortfolioNameToView(){
    in = new ByteArrayInputStream("P2".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    String output =portfolioView.getPortfolioNameToViewOrUpdate();
    assertEquals(output,"P2");
  }

  @Test
  public void testInValidPortfolioNameToView(){
    in = new ByteArrayInputStream("sample".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    String expected="Given portfolio doesnt exist";
    String actual="";
    try {
      portfolioView.getPortfolioNameToViewOrUpdate();
    } catch(IllegalArgumentException e){
      actual = e.getMessage();
    }
    assertEquals(expected,actual);

  }

  @Test
  public void testDisplayTotalValue(){

  }

  @Test
  public void  testGetDateForValuation(){
    in = new ByteArrayInputStream("2022-10-10".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    String output =portfolioView.getDateForValuation();
    assertEquals(output,"2022-10-10");
  }

  @Test
  public void  testInValidDateForValuation(){
    in = new ByteArrayInputStream("20-10-10".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    String expected ="Invalid dateFormat provided.";
    String actual="";
    try {
      portfolioView.getDateForValuation();
    } catch(IllegalArgumentException e){
      actual = e.getMessage();
    }
    assertEquals(actual,expected);
    actual="";
    in = new ByteArrayInputStream("20-10".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    try {
      portfolioView.getDateForValuation();
    } catch(IllegalArgumentException e){
      actual = e.getMessage();
    }
    assertEquals(actual,expected);
    actual="";
    in = new ByteArrayInputStream("sample".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    try {
      portfolioView.getDateForValuation();
    } catch(IllegalArgumentException e){
      actual = e.getMessage();
    }
    assertEquals(actual,expected);

  }

  @Test
  public void testDisplayComposition(){

  }

  @Test
  public void testInvalidUserInputs(){
    in = new ByteArrayInputStream("3".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    String expected = "Invalid input provided";
    portfolioView.getBuyOrSellChoiceFromUser();
    assertEquals(expected,out.toString());
  }*/
}
