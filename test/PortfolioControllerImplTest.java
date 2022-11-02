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

  @Test
  public void testInvalidInputs2(){
    String expected="Enter 1: To create Portfolio using external file  2: To create manually\n" +
            "Invalid input provided.Please provide a valid input (either 1 or 2)\n" +
            "Enter 1: To create Portfolio using external file  2: To create manually";
    String error="Invalid input provided.Please provide a valid input (either 1 or 2)";
    in = new ByteArrayInputStream("1\n1\n4\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in,view);
    try {
      portfolioController.start(new MockModel());
    } catch(NoSuchElementException e){

    }
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



  @Test(expected = NoSuchElementException.class)
  public void testCreateNewPortfolioForCurrentUser(){
    String expected = "Enter the name of the Portfolio";
    in = new ByteArrayInputStream("1\n1\n2\nsample\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    portfolioController.start(new MockModel());
    assertTrue(bytes.toString().contains(expected));
  }

  @Test
  public void testStoppingCondition(){

  }

  @Test
  public void testFinalExitCondition(){

  }

  @Test(expected = NoSuchElementException.class)
  public void testBuyStocks(){
    String expected = "Enter 1: To buy stocks 2: To sell stocks";
    String e ="Enter the quantity of the stocks.\n"+"Enter the quantity of the stocks.\n";
    in = new ByteArrayInputStream("1\n1\n2\nsample\n1\nmeta\n10\n1\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    portfolioController.start(new MockModel());
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(e));
  }

  @Test(expected = NoSuchElementException.class)
  public void testBuyMultipleStocks(){
    String expected = "Enter 1: To buy stocks 2: To sell stocks";
    String e ="Enter the quantity of the stocks.\n"+"Enter the quantity of the stocks.\n";
    in = new ByteArrayInputStream("1\n1\n2\nsample\n1\nmeta\n10\n1\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    portfolioController.start(new MockModel());
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(e));
  }

  @Test(expected = NoSuchElementException.class)
  public void testInvalidDate(){
    String expected = "Enter date as of which you need portfolio valuation( YYYY-MM-DD format only)";
    String error ="Invalid dateFormat provided.Please provide date in YYYY-MM-DD format only.\n" +
            "Enter date as of which you need portfolio valuation( YYYY-MM-DD format only)";
    in = new ByteArrayInputStream("2\n2\nP2\n2022\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    portfolioController.start(new MockModel());
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(error));


  }

  @Test
  public void testInvalidPath(){

  }

  @Test
  public void testInvalidPortfolioNameToView(){

  }

  @Test
  public void testMultiplePortfoliosCreated(){

  }

  @Test
  public void testViewCompositionOfAPortfolio(){

  }

  @Test
  public void testViewTotalValueOfAPortfolioOnaCertainDate(){

  }



}
