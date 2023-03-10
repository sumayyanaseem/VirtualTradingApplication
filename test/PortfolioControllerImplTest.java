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
import stocks.view.PortfolioView;
import stocks.view.PortfolioViewImpl;

import static org.junit.Assert.assertTrue;

/**
 * Tests for the PortfolioController implementation class for text based UI.
 */
public class PortfolioControllerImplTest {

  private InputStream in;

  private PortfolioView view;

  private PortfolioController portfolioController;

  private OutputStream bytes;

  @Before
  public void setUp() {
    in = new ByteArrayInputStream("1\n".getBytes());
    bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioViewImpl(out);
    portfolioController = new PortfolioControllerImpl(in, view);
  }


  @Test
  public void testInitialState() {
    String expected = "Enter 1: To create a Portfolio 2: To query the portfolio details "
            + "3: To load a Portfolio  4: To update a portfolio\n";
    in = new ByteArrayInputStream("".getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    System.out.println(bytes.toString());
    assertTrue(bytes.toString().equals(expected));
  }

  @Test
  public void testInitialInvalidInputs() {
    String expected = "Enter 1: To create a Portfolio "
            + "2: To query the portfolio details 3: To load a Portfolio";
    String error = "Invalid input provided.Please provide a valid input "
            + "(either 1,2,3 or 4)\n"
            + "Enter 1: To create a Portfolio 2: To query the portfolio details"
            + " 3: To load a Portfolio";
    in = new ByteArrayInputStream("6\na\n-1\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(error));
  }


  @Test
  public void testTakeInputsForCreate() {
    String expected = "Enter 1: To create a Portfolio "
            + "2: To query the portfolio details 3: To load a Portfolio";
    String exp1 = "Enter the name of the company\n";
    in = new ByteArrayInputStream("1\n1\nsample12\n1\ndash\n200\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    System.out.println(bytes.toString());
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(exp1));
  }

  @Test
  public void testInvalidInputsForCreate() {
    String expected = "Enter 1: To create a Portfolio "
            + "2: To query the portfolio details 3: To load a Portfolio";
    String exp1 = "Enter the name of the company";
    in = new ByteArrayInputStream("1\n1\nsample12\n1\nxyz\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(exp1));

  }

  @Test
  public void testAskUserWhatHeWantsToView() {
    in = new ByteArrayInputStream("2\ntestInFlexible\n1\n".getBytes());
    String expected = "Enter 1: To view composition  2: To get TotalValue of portfolio";
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    System.out.println(bytes.toString());
    assertTrue(bytes.toString().contains(expected));

  }

  @Test
  public void testAskUserWhatWantsToLoad() {
    in = new ByteArrayInputStream(("3\nuserPortfolios/"
            + "testInFlexible_output.json\n1\n").getBytes());
    String expected = "Enter 1: To view details of this portfolio.  "
            + "2: To exit and continue further trading. "
            + "3: To update loaded portfolio.\n";
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    System.out.println(bytes.toString());
    assertTrue(bytes.toString().contains(expected));

  }


  @Test
  public void testCreatePortfolioUsingFilePath() {
    String expected = "Enter the path of File to load Portfolio\n";
    in = new ByteArrayInputStream("3\npath\n".getBytes());
    StringBuilder mockLog = new StringBuilder();
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    assertTrue(bytes.toString().contains(expected));
  }

  @Test
  public void testInvalidInputs2() {
    in = new ByteArrayInputStream("2\ntestInFlexible\n7\n".getBytes());
    String expected = "Enter 1: To view composition  "
            + "2: To get TotalValue of portfolio";
    String error = "Invalid input provided."
            + "Please provide a valid input (either 1,2,3 or 4)\n"
            + "Enter 1: To view composition  2: To get TotalValue of portfolio";
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    System.out.println(bytes.toString());
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(error));

  }


  @Test
  public void testCreatePortfolioManuallyEndToEnd() {
    String expected = "Enter 1: To create a Portfolio 2: To query the portfolio details "
            + "3: To load a Portfolio  4: To update a portfolio\n"
            + "Enter 1: To create flexible portfolio  2: To create inflexible  portfolio\n"
            + "Enter the name of the Portfolio\n"
            + "Enter the name of the company\n"
            + "Enter the quantity of the stocks\n"
            + "Enter 1: To continue trading in current portfolio.  "
            + "2: To exit from current Portfolio.\n"
            + "Enter 1: To continue trading further. 2: To exit from this session.";
    in = new ByteArrayInputStream("1\n2\nsample\nmeta\n10\n2\n2\n".getBytes());
    String quantity = "10";
    String companyName = "meta";
    String portfolioName = "sample";
    String exp = "inputs for buyStocks: " + quantity + " " + companyName + " " + portfolioName;
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    assertTrue(bytes.toString().contains(expected));

    File f = new File("userPortfolios/" + portfolioName + "_output.json");
    assertTrue(f.exists());
    f.deleteOnExit();
  }


  @Test(expected = NoSuchElementException.class)
  public void testCreateNewPortfolioForCurrentUser() {
    String expected = "Enter the name of the Portfolio";
    in = new ByteArrayInputStream("1\nsample\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    portfolioController.start();
    assertTrue(bytes.toString().contains(expected));
  }

  @Test(expected = NoSuchElementException.class)
  public void testBuyStocks() {
    String expected = "Enter 1: To buy stocks 2: To sell stocks";
    String e = "Enter the quantity of the stocks.\n"
            + "Enter the quantity of the stocks.\n";
    in = new ByteArrayInputStream("1\n2\nsample\nmeta\n10\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    portfolioController.start();
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(e));
  }

  @Test
  public void testBuyMultipleStocks() {
    String expected = "Enter 1: To continue trading in current portfolio.  "
            + "2: To exit from current Portfolio.";
    in = new ByteArrayInputStream(("1\n2\ntest\nmeta\n10\n1\ndash\n20"
            + "\n1\namzn\n200\n2\n1\n").getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    assertTrue(bytes.toString().contains(expected));

  }

  @Test
  public void testInvalidDate() {
    String expected = "Enter date( YYYY-MM-DD format only)";
    String error = "Invalid dateFormat provided.Please "
            + "provide date in YYYY-MM-DD format only.\n"
            + "Enter date( YYYY-MM-DD format only)";
    in = new ByteArrayInputStream(("2\ntestInFlexible\n2\n2022\n1234\n22-10-11"
            + "\n2022-1\n2023-20-10\n").getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    System.out.println(bytes.toString());
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(error));
  }


  @Test
  public void testInvalidPortfolioNameToView() {
    String expected = "Enter 1: To create a Portfolio "
            + "2: To query the portfolio details 3: To load a Portfolio";
    String error = "Enter the name of the Portfolio";
    in = new ByteArrayInputStream("2\ntest.csv\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(error));
  }


  @Test
  public void testViewCompositionOfAPortfolio() {
    String expected = "Enter 1: To view composition  "
            + "2: To get TotalValue of portfolio";
    String output = "inputs for viewCompositionOfCurrentPortfolio: testInFlexible\n";
    in = new ByteArrayInputStream("2\ntestInFlexible\n1\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    assertTrue(bytes.toString().contains(expected));
  }

  @Test
  public void testViewTotalValueOfAPortfolioOnaCertainDate() {
    String expected = "Enter date( YYYY-MM-DD format only)";
    String output = "Total Valuation of Portfolio testInFlexible on 2022-10-01 is :";
    in = new ByteArrayInputStream("2\ntestInFlexible\n2\n2022-10-01\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    System.out.println(bytes.toString());
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(output));
  }


  @Test
  public void testInvalidQuantity() {
    String error = "Invalid quantity provided\n" + "Enter the quantity of the stocks\n";
    String error2 = "Quantity should be always a positive whole number.\n"
            + "Enter the quantity of the stocks";
    in = new ByteArrayInputStream("1\n2\ntest.csv\nmeta\n-100\n0.5\nabc\n".getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    System.out.println(bytes.toString());
    assertTrue(bytes.toString().contains(error));
    assertTrue(bytes.toString().contains(error2));
  }

  @Test
  public void testCreatePortfolioWithMultipleStocks() {
    String expected = "Enter 1: To continue trading in current portfolio.  "
            + "2: To exit from current Portfolio.";
    in = new ByteArrayInputStream(("1\n2\ntest1\nmeta\n10\n1\ndash\n20\n1\namzn"
            + "\n200\n2\n1\n1\ntest\n2\nshop\n20\n1\nnu\n10\n2\n2\n").getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    assertTrue(bytes.toString().contains(expected));
    File f = new File("userPortfolios/" + "test1" + "_output.json");
    assertTrue(f.exists());
    f.deleteOnExit();
  }


}


