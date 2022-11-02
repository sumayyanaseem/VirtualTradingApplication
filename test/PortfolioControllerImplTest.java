import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

import stocks.controller.PortfolioController;
import stocks.controller.PortfolioControllerImpl;
import stocks.model.PortfolioImplModel;
import stocks.view.PortfolioView;
import stocks.view.PortfolioViewImpl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PortfolioControllerImplTest {

  class MockModel extends PortfolioImplModel{

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
    String error ="FilePath Doesn't exist. Try again with correct path.\n";
    in = new ByteArrayInputStream("1\n1\npath\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    portfolioController.start(new MockModel());
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(error));
  }

  @Test
  public void testInvalidInputs2(){
    String expected="Enter 1: To create Portfolio using external file  2: To create manually\n" +
            "Invalid input provided.Please provide a valid input (either 1 or 2)\n" +
            "Enter 1: To create Portfolio using external file  2: To create manually";
    String error="Invalid input provided.Please provide a valid input (either 1 or 2)";
    in = new ByteArrayInputStream("1\n4\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in,view);
    try {
      portfolioController.start(new MockModel());
    } catch(NoSuchElementException e){

    }
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(error));

  }

  @Test
  public void testCreatePortfolioManuallyEndToEnd(){
    String expected = "Enter 1: To trade stocks 2: To view the portfolio composition\n" +
            "Enter 1: To create Portfolio using external file  2: To create manually\n" +
            "Enter the name of the Portfolio\n" +
            "Enter the name of the company to be added to portfolio\n" +
            "Enter the quantity of the stocks\n" +
            "Enter 1: To continue trading in current portfolio.  2: To exit from current Portfolio.\n" +
            "Enter 1: To continue trading further. 2: To exit from this session.";
    in = new ByteArrayInputStream("1\n2\nsample\nmeta\n10\n2\n2\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    MockModel m = new MockModel();
    portfolioController.start(m);
    assertTrue(bytes.toString().contains(expected));
    File file = new File("UserPortfolios/sample.csv");
    assertTrue(file.isFile());
  }



  @Test(expected = NoSuchElementException.class)
  public void testCreateNewPortfolioForCurrentUser(){
    String expected = "Enter the name of the Portfolio";
    in = new ByteArrayInputStream("1\n2\nsample\n".getBytes());
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
    in = new ByteArrayInputStream("1\n2\nsample\nmeta\n10\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    portfolioController.start(new MockModel());
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(e));
  }

  @Test
  public void testBuyMultipleStocks(){
    String expected = "Enter 1: To continue trading in current portfolio.  2: To exit from current Portfolio.";
    in = new ByteArrayInputStream("1\n2\ntest\nmeta\n10\n1\ndash\n20\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start(new MockModel());
    } catch(NoSuchElementException e){

    }
    assertTrue(bytes.toString().contains(expected));
    File file = new File("UserPortfolios/test.csv");
    assertFalse(file.exists());

  }

  @Test
  public void testInvalidDate(){
    String expected = "Enter date as of which you need portfolio valuation( YYYY-MM-DD format only)";
    String error ="Invalid dateFormat provided.Please provide date in YYYY-MM-DD format only.\n" +
            "Enter date as of which you need portfolio valuation( YYYY-MM-DD format only)";
    in = new ByteArrayInputStream("2\n2\nP2\n2022\n1234\n22-10-11\n2022-1".getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start(new MockModel());
    } catch(NoSuchElementException e ){

    }
    System.out.println(bytes.toString());
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(error));


  }

  @Test
  public void testInvalidFilePath(){

  }

  @Test
  public void testCreatePortfolioUsingPath(){

  }

  @Test
  public void testInvalidPortfolioNameToView(){
    String expected = "Enter 1: To view composition of existing portfolio . 2: To get TotalValue of a portfolio";
    String error ="Given portfolio doesnt exist.Please provide valid portfolioName.\n" +
            "Enter the name of the Portfolio";
    in = new ByteArrayInputStream("2\n1\ntest\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start(new MockModel());
    } catch(NoSuchElementException e){

    }
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(error));
  }

  @Test
  public void testMultiplePortfoliosCreated(){

  }

  @Test
  public void testViewCompositionOfAPortfolio(){
    String expected = "Enter 1: To view composition of existing portfolio . 2: To get TotalValue of a portfolio";
    String output = "CompanyName Quantity PriceBought DatePurchase TotalValueOwned TotalValueOwnedAsOfToday \n" +
            "META        10.00    99.2        2022-11-01   992.00          992.00 ";
    in = new ByteArrayInputStream("2\n1\nsample\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start(new MockModel());
    } catch(NoSuchElementException e){

    }
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(output));
  }

  @Test
  public void testViewTotalValueOfAPortfolioOnaCertainDate(){
    String expected = "Enter date as of which you need portfolio valuation( YYYY-MM-DD format only)";
    String output = "Total Valuation of Portfolio   on 2022-10-01 is :1356.80";
    in = new ByteArrayInputStream("2\n2\nsample\n2022-10-01\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start(new MockModel());
    } catch(NoSuchElementException e){

    }
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(output));
  }

  @Test
  public void testCreateViewAndTotalValue(){

  }

  @Test
  public void testInvalidQuantity(){
    String expected = "Enter the name of the company to be added to portfolio\n "+"Enter the quantity of the stocks\n";
    String error ="Invalid quantity provided\n"+"Enter the quantity of the stocks\n";
    String error2="Quantity should be always a positive whole number.\n" +
            "Enter the quantity of the stocks";
    in = new ByteArrayInputStream("1\n2\ntest\nmeta\n-100\n0.5\nabc\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start(new MockModel());
    } catch(NoSuchElementException e){

    }
    System.out.println(bytes.toString());
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(error));
    assertTrue(bytes.toString().contains(error2));
  }



}
