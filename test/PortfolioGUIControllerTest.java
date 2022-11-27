import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import stocks.controller.PortfolioGUIController;
import stocks.model.IFlexible;
import stocks.view.PortfolioGUIView;
import stocks.view.PortfolioGUIViewImpl;

public class PortfolioGUIControllerTest {

  static class MockModel implements IFlexible{

    private final StringBuilder log;

    public MockModel(StringBuilder log) {
      this.log = log;
    }

    @Override
    public double getTotalMoneyInvestedOnCertainDate(String date, String portfolioName) {
      return 0;
    }

    @Override
    public void updatePortfolio(String companyName, String quantity, String date, String portfolioName, String action, String com) throws IllegalArgumentException {

    }

    @Override
    public void updatePortfolioUsingFilePath(String path, String companyName, String quantity, String date, String portfolioName, String action, String com) throws IllegalArgumentException {

    }

    @Override
    public Map<String, Double> getPortfolioPerformanceOvertime(String startTime, String endTime, String portfolioName) {
      return null;
    }

    @Override
    public void sellStocks(String companyName, String quantity, String date, String com, String portfolioName) throws IllegalArgumentException {

    }

    @Override
    public void createEmptyPortfolio(String portfolioName, String portfolioType) {

    }

    @Override
    public List<String> getListOfPortfolioNames() {
      return null;
    }

    @Override
    public void dollarCostStrategy(String portfolioName, Map<String, Double> stockAndPercent, double investmentAmount, double commissionFee, int investmentInterval, String dateStart, String dateEnd) {

    }

    @Override
    public void fixedAmountStrategy(String portfolioName, Map<String, Double> stockAndPercent, double investmentAmount, double commissionFee, String date) {

    }

    @Override
    public List<String> getStocksInPortfolio(String portfolioName) {
      return null;
    }

    @Override
    public double getTotalValueOfPortfolioOnCertainDate(String date, String portfolioName) {
      return 0;
    }

    @Override
    public void loadPortfolioUsingFilePath(String filePath) {

    }

    @Override
    public List<List<String>> viewCompositionOfCurrentPortfolio(String portfolioName, String date) {
      return null;
    }

    @Override
    public void createPortfolioIfCreatedManually(String portfolioName) {

    }

    @Override
    public void validateIfCompanyExists(String companyName) {

    }

    @Override
    public void validateIfPortfolioAlreadyExists(String portfolioName) {

    }

    @Override
    public void validateIfPortfolioDoesntExists(String name) {

    }

    @Override
    public void buyStocks(String companyName, String quantity, String date, String com, String portfolioName) throws IllegalArgumentException {

    }
  }


  private PortfolioGUIView view;

  private PortfolioGUIController portfolioController;

  private MockModel model;

  private StringBuilder mockLog;

  private OutputStream bytes;

  private static final String ticker="goog";
  private static final String date="2020-10-01";
  private static final String qty="20";
  private static final String comm="30";
  private String pName="testGUIController";
  @Before
  public void setUp(){
    bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view=new PortfolioGUIViewImpl();
    mockLog = new StringBuilder();
    model = new MockModel(mockLog);
    portfolioController=new PortfolioGUIController(model,view);
  }

  @Test
  public void testBuyStock(){
    portfolioController.buyStock(ticker,date,qty,comm,pName);
  }

  @Test
  public void testSellStock(){
    portfolioController.sellStock(ticker,date,qty,comm,pName);
  }

  @Test
  public void testViewComposition(){
   // portfolioController.viewComposition();
  }

  @Test
  public void testTotalValue(){
   // portfolioController.getTotalValue();
  }

  @Test
  public void testTotalCostBasis(){
   // portfolioController.getCostBasis();
  }

  @Test
  public void testDollarCostStrategy(){
   // portfolioController.dollarCostStrategy();
  }

  @Test
  public void testInvestFixedAmount(){
   // portfolioController.investFixedAmountStrategy();
  }
}
