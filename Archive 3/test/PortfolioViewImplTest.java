import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import stocks.view.PortfolioView;
import stocks.view.PortfolioViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tests for the PortfolioViewImpl.
 */
public class PortfolioViewImplTest {

  private PortfolioView portfolioView;

  private OutputStream bytes;


  @Before
  public void setUp() {
    bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    portfolioView = new PortfolioViewImpl(out);
  }

  @Test
  public void testGetPortfolioName() {
    portfolioView.getPortfolioName();
    assertEquals(bytes.toString(), "Enter the name of the Portfolio\n");
  }


  @Test
  public void testCallToViewToChooseCreateOrView() {
    portfolioView.callToViewToChooseCreateOrView();
    assertEquals(bytes.toString(), "Enter 1: To create a Portfolio " +
            "2: To query the portfolio details 3: To load a Portfolio\n");
  }


  @Test
  public void testAskUserOnHowToCreatePortfolio() {
    portfolioView.askUserOnHowToCreatePortfolio();
    assertEquals(bytes.toString(), "Enter 1: To create Portfolio using external file  " +
            "2: To create manually\n");
  }


  @Test
  public void testGetFilePath() {
    portfolioView.getFilePath();
    assertEquals(bytes.toString(), "Enter the path of File to load Portfolio\n");
  }

  @Test
  public void testGetBuyOrSellChoiceFromUser() {
    portfolioView.getBuyOrSellChoiceFromUser();
    assertEquals(bytes.toString(), "Enter 1: To buy stocks 2: To sell stocks\n");
  }

  @Test
  public void testGetQuantity() {
    portfolioView.getQuantity();
    assertEquals(bytes.toString(), "Enter the quantity of the stocks\n");

  }

  @Test
  public void testGetCompanyTicker() {
    portfolioView.getCompanyTicker();
    assertEquals(bytes.toString(), "Enter the name of the company to be added to portfolio\n");
  }

  @Test
  public void getAskUserIfHeWantsToContinueTradingInCurrentPortfolio() {
    portfolioView.askUserIfHeWantsToContinueTradingInCurrentPortfolio();
    assertEquals(bytes.toString(), "Enter 1: To continue trading in current portfolio.  " +
            "2: To exit from current Portfolio.\n");
  }

  @Test
  public void getCheckIfUserWantsToExitCompletely() {
    portfolioView.checkIfUserWantsToExitCompletely();
    assertEquals(bytes.toString(), "Enter 1: To continue trading further. " +
            "2: To exit from this session.\n");

  }

  @Test
  public void testCreateOrUpdateExistingPortfolio() {
    portfolioView.createOrUpdateExistingPortfolio();
    assertEquals(bytes.toString(), "Enter 1: To create a portfolio. " +
            "2: To update a existing portfolio.\n");
  }

  @Test
  public void testCheckIfUserWantsToViewCompositionOrTotalValue() {
    portfolioView.checkIfUserWantsToViewCompositionOrTotalValue();
    assertEquals(bytes.toString(), "Enter 1: To view composition " +
            " 2: To get TotalValue of portfolio\n");
  }

  @Test
  public void testDisplayTotalValue() {
    portfolioView.displayTotalValue("2022-10-01", "100.12", "dummy");
    assertEquals(bytes.toString(), "Total Valuation of Portfolio " +
            "dummy" + " on " + "2022-10-01" + " is :" + "100.12" + "\n");

  }

  @Test
  public void testGetDateForValuation() {
    portfolioView.getDateForValuation();
    assertEquals(bytes.toString(), "Enter date as of which you need " +
            "portfolio valuation( YYYY-MM-DD format only)\n");
  }


  @Test
  public void testDisplayErrorMessage() {
    portfolioView.displayErrorMessage("dummy message");
    assertEquals(bytes.toString(), "dummy message\n");
  }

  @Test
  public void testAskUserIfheWantsTOContinueViewing() {
    portfolioView.askUserIfheWantsTOContinueViewing();
    assertEquals(bytes.toString(), "Enter 1: To continue querying loaded portfolio   " +
            "2: To perform actions on other portfolios\n");
  }

  @Test
  public void testCallExitFromLoad() {
    portfolioView.callExitFromLoad();
    assertEquals(bytes.toString(), "Enter 1: To view details of this portfolio. " +
            " 2: To exit and continue further trading.\n");
  }

  @Test
  public void testDisplayComposition() {
    List<List<String>> records = null;
    portfolioView.displayComposition(records);
    assertTrue(bytes.toString().isEmpty());
    records = new ArrayList<>();
    portfolioView.displayComposition(records);
    assertTrue(bytes.toString().isEmpty());
    List<String> l = Arrays.asList("foo", "bar");
    records.add(l);
    portfolioView.displayComposition(records);
    assertTrue(bytes.toString().contains("foo"));

  }


}
