import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stocks.controller.Features;
import stocks.controller.PortfolioGUIController;
import stocks.model.FlexiblePortfolioImpl;
import stocks.model.IFlexible;
import stocks.view.PortfolioGUIView;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the PortfolioControllerGUI implementation class for GUI based view.
 */
public class PortfolioGUIControllerTest {

  static class MockModel implements IFlexible {

    private final StringBuilder log;

    public MockModel(StringBuilder log) {
      this.log = log;
    }

    @Override
    public double getTotalMoneyInvestedOnCertainDate(String date, String portfolioName) {
      log.append("inputs for getTotalMoneyInvestedOnCertainDate: "
              + date + " " + portfolioName + "\n");
      return 0;
    }

    @Override
    public void updatePortfolio(String companyName, String quantity,
                                String date, String portfolioName, String action, String com) throws IllegalArgumentException {
      log.append("inputs for updatePortfolio: " + companyName + " "
              + quantity + " " + date + " " + portfolioName + " " + action + "  " + com + "\n");
    }

    @Override
    public void updatePortfolioUsingFilePath(String path, String companyName,
                                             String quantity, String date, String portfolioName, String action, String com) throws IllegalArgumentException {
      log.append("inputs for updatePortfolioUsingFilePath: " + path
              + " " + companyName + " " + quantity + " " + date + " " + portfolioName + " " + action + "  " + com + "\n");

    }

    @Override
    public Map<String, Double> getPortfolioPerformanceOvertime(String startTime,
                                                               String endTime, String portfolioName) {
      log.append("inputs for getPortfolioPerformanceOvertime: " + startTime + " "
              + endTime + " " + portfolioName + "\n");

      return null;
    }

    @Override
    public void sellStocks(String companyName, String quantity,
                           String date, String com, String portfolioName) throws IllegalArgumentException {
      log.append("inputs for sellStocks: " + companyName + " "
              + quantity + " " + date + " " + portfolioName + "    " + com + "\n");

    }

    @Override
    public void createEmptyPortfolio(String portfolioName, String portfolioType) {
      log.append("inputs for createEmptyPortfolio: " + portfolioName + "    " + portfolioType + "\n");

    }

    @Override
    public List<String> getListOfPortfolioNames() {
      return null;
    }

    @Override
    public void dollarCostStrategy(String portfolioName,
                                   Map<String, Double> stockAndPercent, double investmentAmount,
                                   double commissionFee, int investmentInterval, String dateStart, String dateEnd) {
      log.append("inputs for dollarCostStrategy: " + portfolioName + " "
              + stockAndPercent + " " + investmentAmount + " " + commissionFee + "    " + investmentInterval + " " + dateStart + " " + dateEnd + "\n");
    }

    @Override
    public void fixedAmountStrategy(String portfolioName, Map<String, Double> stockAndPercent,
                                    double investmentAmount, double commissionFee, String date) {
      log.append("inputs for fixedAmountStrategy: " + portfolioName + " "
              + stockAndPercent + " " + investmentAmount + " " + commissionFee + "    " + date + "\n");

    }

    @Override
    public double getTotalValueOfPortfolioOnCertainDate(String date, String portfolioName) {
      log.append("inputs for getTotalValueOfPortfolioOnCertainDate: "
              + portfolioName + "    " + date + "\n");

      return 0;
    }

    @Override
    public void loadPortfolioUsingFilePath(String filePath) {
      log.append("inputs for loadPortfolioUsingFilePath: " + filePath + "\n");


    }

    @Override
    public List<List<String>> viewCompositionOfCurrentPortfolio(String portfolioName, String date) {
      log.append("inputs for viewCompositionOfCurrentPortfolio: "
              + portfolioName + "    " + date + "\n");

      return null;
    }

    @Override
    public void createPortfolioIfCreatedManually(String portfolioName) {
      log.append("inputs for createPortfolioIfCreatedManually: " + portfolioName + "\n");

    }

    @Override
    public void validateIfCompanyExists(String companyName) {
      log.append("inputs for validateIfCompanyExists: " + companyName + "\n");

    }

    @Override
    public void validateIfPortfolioAlreadyExists(String portfolioName) {
      log.append("inputs for validateIfPortfolioAlreadyExists: " + portfolioName + "\n");

    }

    @Override
    public void validateIfPortfolioDoesntExists(String name) {
      log.append("inputs for validateIfPortfolioDoesntExists: " + name + "\n");

    }

    @Override
    public void buyStocks(String companyName, String quantity, String date,
                          String com, String portfolioName) throws IllegalArgumentException {
      log.append("inputs for buyStocks: " + companyName + " " + quantity + " "
              + date + " " + com + " " + portfolioName + "\n");

    }
  }

  static class MockView implements PortfolioGUIView {

    private final StringBuilder log;

    public MockView(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void displayMessage(String s) {
      log.append("inputs for displayMessage: " + s + "\n");
    }

    @Override
    public void addFeatures(Features features) {
      log.append("inputs for addFeatures: " + features + "\n");
    }

    @Override
    public void exitGracefully() {

    }

    @Override
    public void updatePortfolioList(List<String> list) {
      log.append("inputs for updatePortfolioList: " + list + "\n");
    }
  }


  private MockView view;

  private PortfolioGUIController portfolioController;

  private PortfolioGUIController portfolioGUIController;

  private MockModel mockModel;

  private IFlexible model;

  private StringBuilder mockModelLog;

  private StringBuilder mockViewLog;


  private static final String ticker = "goog";
  private static final String date = "2020-10-01";
  private static final String qty = "20";
  private static final String comm = "30";
  private final String pName = "GUI_test_controller";

  @Before
  public void setUp() {
    mockViewLog = new StringBuilder();
    view = new MockView(mockViewLog);
    mockModelLog = new StringBuilder();
    mockModel = new MockModel(mockModelLog);
    portfolioController = new PortfolioGUIController(mockModel, view);
    model = new FlexiblePortfolioImpl();
    portfolioGUIController = new PortfolioGUIController(model, view);
  }

  @Test
  public void testBuyStockMockModel() {
    String log = "inputs for updatePortfolio: goog 20 2020-10-01 GUI_test_controller buy  30";
    String view = "inputs for displayMessage: Bought stocks successfully";
    portfolioController.buyStock(ticker, date, qty, comm, pName);
    assertTrue(mockModelLog.toString().contains(log));
    assertTrue(mockViewLog.toString().contains(view));
  }

  @Test
  public void testSellStockMockModel() {
    String log = "inputs for updatePortfolio: goog 20 2020-10-01 GUI_test_controller sell  30";
    String view = "inputs for displayMessage: Sold stocks successfully";
    portfolioController.sellStock(ticker, date, qty, comm, pName);
    assertTrue(mockModelLog.toString().contains(log));
    assertTrue(mockViewLog.toString().contains(view));
  }

  @Test
  public void testViewCompositionMockModel() {
    String log = "inputs for viewCompositionOfCurrentPortfolio: GUI_test_controller    2020-10-01";
    portfolioController.viewComposition(pName, date);
    System.out.println(mockModelLog.toString());
    assertTrue(mockModelLog.toString().contains(log));
  }

  @Test
  public void testTotalValueMockModel() {
    String log = "inputs for getTotalValueOfPortfolioOnCertainDate: GUI_test_controller    2020-10-01";
    portfolioController.getTotalValue(pName, date);
    //System.out.println(mockModelLog.toString());
    System.out.println(mockViewLog.toString());
    assertTrue(mockModelLog.toString().contains(log));
    // assertTrue(mockViewLog.toString().contains(view));
  }

  @Test
  public void testTotalCostBasisMockModel() {
    String log = "inputs for getTotalMoneyInvestedOnCertainDate: 2020-10-01 GUI_test_controller";
    portfolioController.getCostBasis(pName, date);
    System.out.println(mockModelLog.toString());
    assertTrue(mockModelLog.toString().contains(log));
  }

  @Test
  public void testEndToEndTesting() {
    String view = "inputs for displayMessage: portfolio GUI_test_controller created successfully";
    try {
      portfolioGUIController.createPortfolio(pName, "flexible");
      assertTrue(mockViewLog.toString().contains(view));
      view = "inputs for displayMessage: Bought stocks successfully";
      portfolioGUIController.buyStock(ticker, date, qty, comm, pName);
      System.out.println(mockViewLog.toString());
      assertTrue(mockViewLog.toString().contains(view));
      view = "inputs for displayMessage: Sold stocks successfully";
      portfolioGUIController.sellStock(ticker, date, qty, comm, pName);
      assertTrue(mockViewLog.toString().contains(view));


      double value = portfolioGUIController.getTotalValue(pName, date);
      //System.out.println(value);
      assertTrue(value == 0);
      double costBasis = portfolioGUIController.getCostBasis(pName, date);
      //System.out.println(costBasis);
      assertTrue(costBasis != 0);

      Map<String, String> stockAndPercent = new HashMap<>();
      stockAndPercent.put("goog", "10.5");
      stockAndPercent.put("META", "29.5");
      stockAndPercent.put("ORCL", "49.5");
      stockAndPercent.put("TWTR", "10.5");
      portfolioGUIController.dollarCostStrategy(pName, stockAndPercent,
              10000, 20,
              30, "2020-01-01", "2022-11-11");
      System.out.println(mockViewLog.toString());
      assertTrue(mockViewLog.toString().contains(view));

      view = "inputs for displayMessage: Bought stocks via fixed amount strategy successfully\n";
      stockAndPercent = new HashMap<>();
      stockAndPercent.put("goog", "10.5");
      stockAndPercent.put("META", "29.5");
      stockAndPercent.put("ORCL", "49.5");
      stockAndPercent.put("TWTR", "10.5");
      portfolioGUIController.investFixedAmountStrategy(pName, stockAndPercent,
              10000, 20, "2020-01-01");
      System.out.println(mockViewLog.toString());
      assertTrue(mockViewLog.toString().contains(view));

      List<List<String>> results = portfolioGUIController.viewComposition(pName, date);

      StringBuilder dates = new StringBuilder();
      StringBuilder company = new StringBuilder();
      for (List<String> list : results) {
        dates.append(list.get(2).toString());
        company.append(list.get(0).toString());
      }

      assertTrue(dates.toString().contains("2020-01-02"));
      //Jan 1st is holiday and is not included.
      assertFalse(dates.toString().contains("2020-01-01"));
      //Dec 25th
      assertFalse(dates.toString().contains("2020-12-25"));
      //July 4th
      assertFalse(dates.toString().contains("2020-07-04"));
      //Nov 11
      assertFalse(dates.toString().contains("2020-11-11"));

      assertTrue(company.toString().contains("GOOG"));
      assertTrue(company.toString().contains("META"));
      assertTrue(company.toString().contains("ORCL"));
      assertTrue(company.toString().contains("TWTR"));


    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    File f = new File("userPortfolios/" + pName + "_output.json");
    assertTrue(f.exists());
    f.deleteOnExit();
  }

  @Test
  public void testDollarCost() {
    String view = "inputs for displayMessage: Bought stocks via dollar cost strategy successfully";
    Map<String, String> stockAndPercent = new HashMap<>();
    stockAndPercent.put("goog", "10.5");
    stockAndPercent.put("META", "29.5");
    stockAndPercent.put("ORCL", "49.5");
    stockAndPercent.put("TWTR", "10.5");
    portfolioGUIController.dollarCostStrategy(pName, stockAndPercent,
            10000, 20, 30,
            "2020-01-01", "2022-11-11");
    System.out.println(mockViewLog.toString());
    assertTrue(mockViewLog.toString().contains(view));

    view = "inputs for displayMessage: percentages should be in numbers";
    stockAndPercent = new HashMap<>();
    stockAndPercent.put("goog", null);
    stockAndPercent.put("META", "29.5");
    stockAndPercent.put("ORCL", "49.5");
    stockAndPercent.put("TWTR", "10.5");
    portfolioGUIController.dollarCostStrategy(pName, stockAndPercent,
            10000, 20, 30, "2020-01-01", "2022-11-11");

    System.out.println(mockViewLog.toString());
    assertTrue(mockViewLog.toString().contains(view));
  }

  @Test
  public void testFixedCost() {
    String view = "inputs for displayMessage: Bought stocks via fixed amount strategy successfully\n";
    Map<String, String> stockAndPercent = new HashMap<>();
    stockAndPercent.put("goog", "10.5");
    stockAndPercent.put("META", "29.5");
    stockAndPercent.put("ORCL", "49.5");
    stockAndPercent.put("TWTR", "10.5");
    portfolioGUIController.investFixedAmountStrategy(pName, stockAndPercent,
            10000, 20, "2020-01-01");
    System.out.println(mockViewLog.toString());
    assertTrue(mockViewLog.toString().contains(view));

    view = "inputs for displayMessage: percentages should be in numbers";
    stockAndPercent = new HashMap<>();
    stockAndPercent.put("goog", null);
    stockAndPercent.put("META", "29.5");
    stockAndPercent.put("ORCL", "49.5");
    stockAndPercent.put("TWTR", "10.5");
    portfolioGUIController.investFixedAmountStrategy(pName, stockAndPercent,
            10000, 20, "2020-01-01");
    assertTrue(mockViewLog.toString().contains(view));
  }


}
