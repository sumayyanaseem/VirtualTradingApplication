import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import stocks.customapi.APICustomClass;
import stocks.customapi.APICustomInterface;
import stocks.model.InFlexiblePortfolioImpl;
import stocks.model.Portfolio;
import stocks.model.PortfolioModel;
import stocks.model.PortfolioModelImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * The test class for ModelImpl.
 */
public class ModelImplTest {

  static class MockPortfolio implements Portfolio {

    private final StringBuilder log;

    public MockPortfolio(StringBuilder log) {
      this.log = log;
    }

    @Override
    public double getTotalValueOfPortfolioOnCertainDate(
            String date, String portfolioName) {
      log.append("inputs for getTotalValueOfPortfolioOnCertainDate: "
              + date + " " + portfolioName + "\n");
      return 0;
    }

    @Override
    public void loadPortfolioUsingFilePath(String filePath) {
      log.append("inputs for loadPortfolioUsingFilePath: " + filePath + "\n");
    }

    @Override
    public List<List<String>> viewCompositionOfCurrentPortfolio(
            String portfolioName, String date) {
      log.append("inputs for viewCompositionOfCurrentPortfolio: "
              + date + " " + portfolioName + "\n");
      return null;
    }

    @Override
    public void createPortfolioIfCreatedManually(String portfolioName) {
      log.append("inputs for createPortfolioIfCreatedManually: "
              + " " + portfolioName + "\n");
    }

    @Override
    public void validateIfCompanyExists(String companyName) {
      log.append("inputs for validateIfCompanyExists: " + " ").append(companyName).append("\n");
    }

    @Override
    public void validateIfPortfolioAlreadyExists(String portfolioName) {
      log.append("inputs for validateIfPortfolioAlreadyExists: " + " " + portfolioName + "\n");
    }

    @Override
    public void validateIfPortfolioDoesntExists(String name) {
      log.append("inputs for validateIfPortfolioDoesntExists: " + " " + name + "\n");
    }

    @Override
    public void buyStocks(String companyName, String quantity, String date, String com, String portfolioName) throws IllegalArgumentException {
      log.append("inputs for buyStocks: " + companyName + " " + quantity + " " + date + " " + portfolioName + "\n");

    }

    @Override
    public void sellStocks(String companyName, String quantity, String date, String com, String portfolioName) throws IllegalArgumentException {
      log.append("inputs for sellStocks: " + companyName + " " + quantity + " " + date + " " + portfolioName + "\n");

    }

    @Override
    public void updatePortfolio(String companyName, String quantity, String date, String portfolioName, String action, String com) throws IllegalArgumentException {
      log.append("inputs for updatePortfolio: " + companyName + " " + quantity + " " + date + " " + portfolioName + "\n");

    }

    @Override
    public void updatePortfolioUsingFilePath(String path, String companyName, String quantity, String date, String portfolioName, String action, String com) throws IllegalArgumentException {

    }

    @Override
    public double getTotalMoneyInvestedOnCertainDate(String date, String portfolioName) {
      log.append("inputs for getTotalMoneyInvestedOnCertainDate: "
              + date + " " + portfolioName + "\n");
      return 0;
    }

    @Override
    public Map<String, Double> getPortfolioPerformanceOvertime(
            String startTime, String endTime, String portfolioName) {
      log.append("inputs for getPortfolioPerformanceOvertime: "
              + startTime + " " + endTime + " " + portfolioName + "\n");
      return null;
    }
  }

  private PortfolioModel model;

  private Portfolio inflexiblePortfolio;

  private Portfolio mockPortfolio;

  private static final String companyName = "test-company";

  private static final String quantity = "100";

  private static final String date = "2004-10-01";

  private static final String portfolioName = "testPortfolio";

  private static final String path = "userPortfolios/testInFlexible_output.json";

  private static final String com="10";

  private StringBuilder mockLog;
  private final APICustomInterface apiCustomInterface = new APICustomClass("https://www.alphavantage.co/query?function=TIME_SERIES_");


  @Before
  public void setUp() {
    model = new PortfolioModelImpl();
    inflexiblePortfolio = new InFlexiblePortfolioImpl(apiCustomInterface);
    mockLog = new StringBuilder();
    mockPortfolio = new MockPortfolio(mockLog);
  }


  @Test
  public void testBuyStocks() {
    String log = "inputs for buyStocks: " + companyName + " " + quantity + " " + date + " " + portfolioName + "\n";
    model.buyStocks(companyName, quantity, date, portfolioName, com,mockPortfolio);
    assertTrue(mockLog.toString().contains(log));
  }

  @Test
  public void testSellStocksInflexiblePortfolio() {
    String expected = "This operation is not supported in Inflexible portfolio";
    String actual = "";
    try {
      model.sellStocks(companyName, quantity, date, portfolioName, com,inflexiblePortfolio);
    } catch (UnsupportedOperationException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);
  }

  @Test
  public void testGetTotalValueOfPortfolioOnCertainDate() {
    String log = "inputs for getTotalValueOfPortfolioOnCertainDate: "
            + date + " " + portfolioName + "\n";
    model.getTotalValueOfPortfolioOnCertainDate(
            date, portfolioName, mockPortfolio);
    assertTrue(mockLog.toString().contains(log));
  }

  @Test
  public void testLoadPortfolioUsingFilePath() {
    String log = "inputs for loadPortfolioUsingFilePath: " + path + "\n";
    model.loadPortfolioUsingFilePath(path, mockPortfolio);
    assertTrue(mockLog.toString().contains(log));
  }

  @Test
  public void testViewCompositionOfCurrentPortfolio() {
    String log = "inputs for viewCompositionOfCurrentPortfolio: "
            + date + " " + portfolioName + "\n";
    model.viewCompositionOfCurrentPortfolio(portfolioName, date, mockPortfolio);
    assertTrue(mockLog.toString().contains(log));
  }

  @Test
  public void testCreatePortfolioIfCreatedManually() {
    String log = "inputs for createPortfolioIfCreatedManually: " + " " + portfolioName + "\n";
    model.createPortfolioIfCreatedManually(portfolioName, mockPortfolio);
    assertTrue(mockLog.toString().contains(log));
  }


  @Test
  public void testUpdatePortfolio() {
    String expected = "This operation is not supported in Inflexible portfolio";
    String actual = "";

    try {
      model.updatePortfolio(companyName, quantity, date, portfolioName, inflexiblePortfolio, "action",com);
    } catch (UnsupportedOperationException e) {
      actual = e.getMessage();
    }
    assertEquals(actual, expected);
  }

  @Test
  public void testGetTotalMoneyInvestedOnCertainDate() {
    String log = "inputs for getTotalMoneyInvestedOnCertainDate: "
            + date + " " + portfolioName + "\n";
    model.getTotalMoneyInvestedOnCertainDate(date, portfolioName, mockPortfolio);
    assertTrue(mockLog.toString().contains(log));
  }


  @Test
  public void testGetPortfolioPerformanceOvertime() {
    String log = "inputs for getPortfolioPerformanceOvertime: "
            + date + " " + date + " " + portfolioName + "\n";

    model.getPortfolioPerformanceOvertime(date, date, portfolioName, mockPortfolio);
    assertTrue(mockLog.toString().contains(log));
  }

  @Test
  public void testValidateIfCompanyExists() {
    String log = ("inputs for validateIfCompanyExists: " + " ") + (companyName) + ("\n");
    model.validateIfCompanyExists(companyName, mockPortfolio);
    assertTrue(mockLog.toString().contains(log));
  }

  @Test
  public void testValidateIfPortfolioAlreadyExists() {
    String log = ("inputs for validateIfPortfolioAlreadyExists: " + " "
            + portfolioName + "\n");
    model.validateIfPortfolioAlreadyExists(portfolioName, mockPortfolio);
    assertTrue(mockLog.toString().contains(log));
  }

  @Test
  public void testValidateIfPortfolioDoesntExists() {
    String log = "inputs for validateIfPortfolioDoesntExists: " + " "
            + portfolioName + "\n";
    model.validateIfPortfolioDoesntExists(portfolioName, mockPortfolio);
    assertTrue(mockLog.toString().contains(log));
  }
}
