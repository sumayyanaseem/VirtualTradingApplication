import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import stocks.view.PortfolioView;
import stocks.view.PortfolioViewImpl;

import static org.junit.Assert.assertEquals;

public class PortfolioViewImplTest {

  private PortfolioView portfolioView;

  private PrintStream out;

  private OutputStream bytes;


  @Before
  public void setUp(){
    bytes = new ByteArrayOutputStream();
    out = new PrintStream(bytes);
    portfolioView = new PortfolioViewImpl(out);
  }

  @Test
  public void testGetPortfolioName(){
    portfolioView.getPortfolioName();
    assertEquals(bytes.toString(),"Enter the name of the Portfolio\n");
  }


  @Test
  public void testCallToViewToChooseCreateOrView()  {
    portfolioView.callToViewToChooseCreateOrView();
    assertEquals(bytes.toString(),"Enter 1: To trade stocks 2: To view the portfolio composition\n");
  }



  @Test
  public void testAskUserOnHowToCreatePortfolio() {
    portfolioView.askUserOnHowToCreatePortfolio();
    assertEquals(bytes.toString(),"Enter 1: To create Portfolio using external file  2: To create manually\n");
  }



  @Test
  public void testGetFilePath() {
    portfolioView.getFilePath();
    assertEquals(bytes.toString(),"Enter the path of File which is used to create Portfolio\n");
  }

  @Test
  public void testGetBuyOrSellChoiceFromUser() {
    portfolioView.getBuyOrSellChoiceFromUser();
    assertEquals(bytes.toString(),"Enter 1: To buy stocks 2: To sell stocks\n");
  }

  @Test
  public void testGetQuantity() {
    portfolioView.getQuantity();
    assertEquals(bytes.toString(),"Enter the quantity of the stocks\n");

  }

  @Test
  public void testGetCompanyTicker() {
    portfolioView.getCompanyTicker();
    assertEquals(bytes.toString(),"Enter the name of the company to be added to portfolio\n");
  }

  @Test
  public void getAskUserIfHeWantsToContinueTradingInCurrentPortfolio() {
    portfolioView.askUserIfHeWantsToContinueTradingInCurrentPortfolio();
    assertEquals(bytes.toString(),"Enter 1: To continue trading in current portfolio.  2: To exit from current Portfolio.\n");
  }

  @Test
  public void getCheckIfUserWantsToExitCompletely() {
    portfolioView.checkIfUserWantsToExitCompletely();
    assertEquals(bytes.toString(),"Enter 1: To continue trading further. 2: To exit from this session.\n");

  }

  @Test
  public void testCreateOrUpdateExistingPortfolio() {
    portfolioView.createOrUpdateExistingPortfolio();
    assertEquals(bytes.toString(),"Enter 1: To create a portfolio. 2: To update a existing portfolio.\n");
  }

  @Test
  public void testCheckIfUserWantsToViewCompositionOrTotalValue() {
    portfolioView.checkIfUserWantsToViewCompositionOrTotalValue();
    assertEquals(bytes.toString(),"Enter 1: To view composition of existing portfolio . 2: To get TotalValue of a portfolio\n");
  }

  @Test
  public void testDisplayTotalValue() {
    portfolioView.displayTotalValue("2022-10-01","100.12","dummy");
    assertEquals(bytes.toString(),"Total Valuation of Portfolio " + "dummy" + "  on "+"2022-10-01"+ " is :" + "100.12"+"\n");

  }

  @Test
  public void testGetDateForValuation() {
    portfolioView.getDateForValuation();
    assertEquals(bytes.toString(),"Enter date as of which you need portfolio valuation( YYYY-MM-DD format only)");

  }



  @Test
  public void testDisplayErrorMessage() {
    portfolioView.displayErrorMessage("dummy message");
    assertEquals(bytes.toString(),"dummy message\n");

  }




}
