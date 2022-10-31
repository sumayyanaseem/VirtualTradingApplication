import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import stocks.view.PortfolioView;
import stocks.view.PortfolioViewImpl;

import static org.junit.Assert.assertEquals;

public class PortfolioViewImplTest {

  PortfolioView portfolioView;
  InputStream in;

  @Before
  public void set(){
   in = new ByteArrayInputStream("1".getBytes());
    portfolioView = new PortfolioViewImpl(in);
  }


  @Test
  public void testCallToViewToChooseCreateOrView(){
    String output =portfolioView.callToViewToChooseCreateOrView();
    assertEquals(output,"1");
  }

  @Test
  public void testCallToViewOnHowToCreatePortfolio(){
    String output =portfolioView.callToViewOnHowToCreatePortfolio();
    assertEquals(output,"1");
  }

  @Test
  public void testCallToViewToChoiceOption(){
    in = new ByteArrayInputStream("2".getBytes());
    portfolioView = new PortfolioViewImpl(in);
    String output =portfolioView.callToViewToChoiceOption();
    assertEquals(output,"2");
  }

  @Test
  public void testCallToViewToAskPortfolioName(){
    in = new ByteArrayInputStream("sample".getBytes());
    portfolioView = new PortfolioViewImpl(in);
    String output =portfolioView.callToViewToAskPortfolioName();
    assertEquals(output,"sample");
  }

  @Test
  public void testCallToViewToGetFilePath(){
    in = new ByteArrayInputStream("src/stocks/view/".getBytes());
    portfolioView = new PortfolioViewImpl(in);
    String output =portfolioView.callToViewToGetFilePath();
    assertEquals(output,"src/stocks/view/");
  }

  @Test
  public void testCallToViewToAskQuantity(){
    in = new ByteArrayInputStream("100".getBytes());
    portfolioView = new PortfolioViewImpl(in);
    String output =portfolioView.callToViewToAskQuantity();
    assertEquals(output,"100");
  }

  @Test
  public void testInvalidQuantity(){
    in = new ByteArrayInputStream("0".getBytes());
    portfolioView = new PortfolioViewImpl(in);
    String expected="Quantity should be always a positive whole number";
    String actual="";
    try {
      portfolioView.callToViewToAskQuantity();
    } catch(IllegalArgumentException e){
      actual=e.getMessage();
    }
    assertEquals(expected,actual);
    actual="";
    in = new ByteArrayInputStream("-10".getBytes());
    portfolioView = new PortfolioViewImpl(in);
    try {
      portfolioView.callToViewToAskQuantity();
    } catch(IllegalArgumentException e){
      actual=e.getMessage();
    }
    assertEquals(expected,actual);
  }

  @Test
  public void testCallToViewToAskCompanyTicker(){
    in = new ByteArrayInputStream("GOOG".getBytes());
    portfolioView = new PortfolioViewImpl(in);
    String output =portfolioView.callToViewToAskCompanyTicker();
    assertEquals(output,"GOOG");
  }

  @Test
  public void testAskUserIfHeWantsToContinueTradingInCurrentPortfolio(){
    String output =portfolioView.askUserIfHeWantsToContinueTradingInCurrentPortfolio();
    assertEquals(output,"1");
  }

  @Test
  public void testCheckIfUserWantsToExitCompletely(){
    String output =portfolioView.checkIfUserWantsToExitCompletely();
    assertEquals(output,"1");
  }

  @Test
  public void testCreateOrUpdateExistingPortfolio(){
    String output =portfolioView.createOrUpdateExistingPortfolio();
    assertEquals(output,"1");
  }

  @Test
  public void testGetPortfolioNameToView(){
    in = new ByteArrayInputStream("sample".getBytes());
    portfolioView = new PortfolioViewImpl(in);
    String output =portfolioView.getPortfolioNameToView();
    assertEquals(output,"sample");
  }

  @Test
  public void testDisplayTotalValue(){

  }

  @Test
  public void  testGetDateForValuation(){
    in = new ByteArrayInputStream("sample".getBytes());
    portfolioView = new PortfolioViewImpl(in);
    String output =portfolioView.getDateForValuation();
    assertEquals(output,"sample");
  }

  @Test
  public void testDisplayComposition(){

  }

  @Test
  public void testAskForPortfolioNameToGetValuation(){
    in = new ByteArrayInputStream("sample".getBytes());
    portfolioView = new PortfolioViewImpl(in);
    String output =portfolioView.askForPortfolioNameToGetValuation();
    assertEquals(output,"sample");
  }

  @Test
  public void testInvalidUserInputs(){
    in = new ByteArrayInputStream("3".getBytes());
    portfolioView = new PortfolioViewImpl(in);
    String expected = "Invalid input provided";
    String actual="";
    try {
      portfolioView.callToViewToChoiceOption();
    } catch(IllegalArgumentException e){
      actual = e.getMessage();
    }
    assertEquals(expected,actual);
  }
}
