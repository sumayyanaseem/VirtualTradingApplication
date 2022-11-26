import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import stocks.controller.PortfolioGUIController;
import stocks.model.IFlexible;
import stocks.view.PortfolioGUIView;

public class PortfolioGUIControllerTest {
  //TODO test fractional dollar cost strategy
  private InputStream in;

  private PortfolioGUIView view;

  private PortfolioGUIController portfolioController;

  private OutputStream bytes;

  static class MockModel implements IFlexible{

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
  @Before
  public void setUp(){

  }

  @Test
  public void test(){

  }
}
