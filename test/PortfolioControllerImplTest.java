import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import stocks.controller.PortfolioController;
import stocks.controller.PortfolioControllerImpl;
import stocks.model.PortfolioModel;
import stocks.model.Portfolio;
import stocks.view.PortfolioView;
import stocks.view.PortfolioViewImpl;

import static org.junit.Assert.assertTrue;

/**
 * Tests for the PortfolioControllerImpl.
 */
public class PortfolioControllerImplTest {

  class MockModel implements PortfolioModel {

    private final StringBuilder log;

    public MockModel(StringBuilder log) {
      this.log = log;
    }

    @Override
    public double getTotalValueOfPortfolioOnCertainDate(String date, String portfolioName, Portfolio portfolio) {
      return 0;
    }

    @Override
    public void loadPortfolioUsingFilePath(String filePath, Portfolio portfolio) {
      log.append("inputs for createPortfolioUsingFilePath: " + filePath + "\n");
    }

    @Override
    public List<List<String>> viewCompositionOfCurrentPortfolio(String portfolioName, String date, Portfolio portfolio) {
      List<List<String>> dummy = new ArrayList<>();
      log.append("inputs for viewCompositionOfCurrentPortfolio: " + portfolioName + "\n");
      return dummy;
    }

    @Override
    public void createPortfolioIfCreatedManually(String portfolioName, Portfolio portfolio) {
      log.append("inputs for createPortfolioIfCreatedManually: " + portfolioName + "\n");

    }

    @Override
    public void validateIfCompanyExists(String companyName, Portfolio portfolio) {
      log.append("validateIfCompanyExists : " + companyName + "\n");

    }

    @Override
    public void validateIfPortfolioAlreadyExists(String portfolioName, Portfolio portfolio) {
      log.append("validateIfPortfolioAlreadyExists : " + portfolioName + "\n");

    }

    @Override
    public void validateIfPortfolioDoesntExists(String name, Portfolio portfolio) {
      log.append("validateIfPortfolioDoesntExists :" + name + "\n");

    }

    @Override
    public void buyStocks(String companyName, String quantity, String date, String portfolioName, Portfolio portfolio) {

    }

    @Override
    public void sellStocks(String companyName, String quantity, String date, String portfolioName, Portfolio portfolio) {

    }

    @Override
    public void updatePortfolio(String companyName, String quantity, String date, String portfolioName, Portfolio portfolio, String action) {

    }

    @Override
    public void updatePortfolioUsingFilePath(String path, String companyName, String quantity, String date, String portfolioName, Portfolio portfolio, String action) throws IllegalArgumentException {

    }

    @Override
    public double getTotalMoneyInvestedOnCertainDate(String date, String portfolioName, Portfolio portfolio) {
      return 0;
    }

    @Override
    public Map<String,Double> getPortfolioPerformanceOvertime(String startTime, String endTime, String portfolioName, Portfolio portfolio) {
      return null;
    }
  }

  private InputStream in;

  private PortfolioView view;

  private PortfolioController portfolioController;

  private OutputStream bytes;

  private PortfolioModel model;

  private StringBuilder mockLog;

  @Before
  public void setUp() {
    in = new ByteArrayInputStream("1\n".getBytes());
    bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioViewImpl(out);
    mockLog = new StringBuilder();
    model = new MockModel(mockLog);
    portfolioController = new PortfolioControllerImpl(model,in, view);
  }


  @Test
  public void testInitialState() {
    String expected = "Enter 1: To create a Portfolio "
            + "2: To query the portfolio details 3: To load a Portfolio\n";
    in = new ByteArrayInputStream("".getBytes());
    portfolioController = new PortfolioControllerImpl(model,in, view);
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
            + "(either 1,2 or 3)\n"
            + "Enter 1: To create a Portfolio 2: To query the portfolio details"
            + " 3: To load a Portfolio";
    in = new ByteArrayInputStream("4\na\n-1\n".getBytes());
    portfolioController = new PortfolioControllerImpl(model,in, view);
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
    String exp1 = "Enter the name of the company to be added to portfolio";
    in = new ByteArrayInputStream("1\nsample\ndash\n200\n".getBytes());
    portfolioController = new PortfolioControllerImpl(model,in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(exp1));
    String log = "validateIfPortfolioAlreadyExists : sample";
    assertTrue(mockLog.toString().contains(log));
    log = "validateIfCompanyExists : dash";
    assertTrue(mockLog.toString().contains(log));
  }

  @Test
  public void testInvalidInputsForCreate() {
    String expected = "Enter 1: To create a Portfolio "
            + "2: To query the portfolio details 3: To load a Portfolio";
    String exp1 = "Enter the name of the company to be added to portfolio";
    in = new ByteArrayInputStream("1\nsample\ngoog\n".getBytes());
    portfolioController = new PortfolioControllerImpl(model,in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(exp1));
    String log = "validateIfPortfolioAlreadyExists : sample";
    assertTrue(mockLog.toString().contains(log));
    log = "validateIfCompanyExists : goog";
    assertTrue(mockLog.toString().contains(log));
  }

  @Test
  public void testAskUserWhatHeWantsToView() {
    in = new ByteArrayInputStream("2\nsample\n1\n".getBytes());
    String expected = "Enter 1: To view composition  2: To get TotalValue of portfolio";
    portfolioController = new PortfolioControllerImpl(model,in, view);
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
    in = new ByteArrayInputStream("3\nsample\n1\n".getBytes());
    String expected = "Enter 1: To view details of this portfolio.  "
            + "2: To exit and continue further trading.\n";
    portfolioController = new PortfolioControllerImpl(model,in, view);
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
    PortfolioModel model = new MockModel(mockLog);
    portfolioController = new PortfolioControllerImpl(model,in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    assertTrue(bytes.toString().contains(expected));
  }

  @Test
  public void testInvalidInputs2() {
    in = new ByteArrayInputStream("2\nsample\n3\n".getBytes());
    String expected = "Enter 1: To view composition  2: To get TotalValue of portfolio";
    String error = "Invalid input provided.Please provide a valid input (either 1 or 2)\n"
            + "Enter 1: To view composition  2: To get TotalValue of portfolio";
    portfolioController = new PortfolioControllerImpl(model,in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(error));

  }


  @Test
  public void testCreatePortfolioManuallyEndToEnd() {
    String expected = "Enter 1: To create a Portfolio "
            + "2: To query the portfolio details 3: To load a Portfolio\n"
            + "Enter the name of the Portfolio\n"
            + "Enter the name of the company to be added to portfolio\n"
            + "Enter the quantity of the stocks\n"
            + "Enter 1: To continue trading in current portfolio.  "
            + "2: To exit from current Portfolio.\n"
            + "Enter 1: To continue trading further. 2: To exit from this session.";
    in = new ByteArrayInputStream("1\nsample\nmeta\n10\n2\n2\n".getBytes());
    StringBuilder mockLog = new StringBuilder();
    model = new MockModel(mockLog);
    String quantity = "10";
    String companyName = "meta";
    String portfolioName = "sample";
    String exp = "inputs for buyStocks: " + quantity + " " + companyName + " " + portfolioName;
    portfolioController = new PortfolioControllerImpl(model,in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());

    }
    assertTrue(mockLog.toString().contains(exp));
    assertTrue(bytes.toString().contains(expected));
  }


  @Test(expected = NoSuchElementException.class)
  public void testCreateNewPortfolioForCurrentUser() {
    String expected = "Enter the name of the Portfolio";
    in = new ByteArrayInputStream("1\nsample\n".getBytes());
    portfolioController = new PortfolioControllerImpl(model,in, view);
    portfolioController.start();
    assertTrue(bytes.toString().contains(expected));
  }

  @Test(expected = NoSuchElementException.class)
  public void testBuyStocks() {
    String expected = "Enter 1: To buy stocks 2: To sell stocks";
    String e = "Enter the quantity of the stocks.\n" + "Enter the quantity of the stocks.\n";
    in = new ByteArrayInputStream("1\n2\nsample\nmeta\n10\n".getBytes());
    portfolioController = new PortfolioControllerImpl(model,in, view);
    portfolioController.start();
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(e));
  }

  @Test
  public void testBuyMultipleStocks() {
    String expected = "Enter 1: To continue trading in current portfolio.  "
            + "2: To exit from current Portfolio.";
    in = new ByteArrayInputStream(("1\ntest\nmeta\n10\n1\ndash\n20"
            + "\n1\namzn\n200\n2\n1\n").getBytes());
    portfolioController = new PortfolioControllerImpl(model,in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    assertTrue(bytes.toString().contains(expected));

  }

  @Test
  public void testInvalidDate() {
    String expected = "Enter date as of which you need portfolio "
            + "valuation( YYYY-MM-DD format only)";
    String error = "Invalid dateFormat provided.Please "
            + "provide date in YYYY-MM-DD format only.\n"
            + "Enter date as of which you need portfolio "
            + "valuation( YYYY-MM-DD format only)";
    in = new ByteArrayInputStream(("2\nP2\n2\n2022\n1234\n22-10-11"
            + "\n2022-1\n2023-20-10\n").getBytes());
    portfolioController = new PortfolioControllerImpl(model,in, view);
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
    portfolioController = new PortfolioControllerImpl(model,in, view);
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
    String expected = "Enter 1: To view composition  2: To get TotalValue of portfolio";
    String output = "inputs for viewCompositionOfCurrentPortfolio: sample\n";
    in = new ByteArrayInputStream("2\nsample\n1\n".getBytes());
    portfolioController = new PortfolioControllerImpl(model,in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    assertTrue(bytes.toString().contains(expected));
    assertTrue(mockLog.toString().contains(output));
  }

  @Test
  public void testViewTotalValueOfAPortfolioOnaCertainDate() {
    String expected = "Enter date as of which you need portfolio "
            + "valuation( YYYY-MM-DD format only)";
    String output = "Total Valuation of Portfolio  on 2022-10-01 is";
    in = new ByteArrayInputStream("2\nsample\n2\n2022-10-01\n".getBytes());
    portfolioController = new PortfolioControllerImpl(model,in, view);
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
    portfolioController = new PortfolioControllerImpl(model,in, view);
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
    in = new ByteArrayInputStream(("1\ntest\nmeta\n10\n1\ndash\n20\n1\namzn"
            + "\n200\n2\n1\n1\ntest2\nshop\n20\n1\nnu\n10\n2\n2\n").getBytes());
    portfolioController = new PortfolioControllerImpl(model,in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    assertTrue(bytes.toString().contains(expected));
  }
}


