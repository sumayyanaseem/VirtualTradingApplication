import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import stocks.view.PortfolioView;
import stocks.view.PortfolioViewImpl;

import static org.junit.Assert.assertEquals;

public class PortfolioViewImplTest {

  PortfolioView portfolioView;
  InputStream in;

  PrintStream out;

  @Before
  public void set(){
   in = new ByteArrayInputStream("1".getBytes());
    OutputStream bytes = new ByteArrayOutputStream();
    out = new PrintStream(bytes);
    portfolioView = new PortfolioViewImpl(in,out);
  }


  @Test
  public void testCallToViewToChooseCreateOrView(){
    String output =portfolioView.callToViewToChooseCreateOrView();
    assertEquals(output,"1");
  }

  @Test
  public void testCallToViewOnHowToCreatePortfolio(){
    String output =portfolioView.askUserOnHowToCreatePortfolio();
    assertEquals(output,"1");
  }

  @Test
  public void testCallToViewToChoiceOption(){
    in = new ByteArrayInputStream("2".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    String output =portfolioView.getBuyOrSellChoiceFromUser();
    assertEquals(output,"2");
  }

  @Test
  public void testCallToViewToGetFilePath(){
    in = new ByteArrayInputStream("src/stocks/view/".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    String output =portfolioView.getFilePath();
    assertEquals(output,"src/stocks/view/");
  }

  @Test
  public void testCallToViewToAskQuantity(){
    in = new ByteArrayInputStream("100".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    String output =portfolioView.getQuantity();
    assertEquals(output,"100");
  }

  @Test
  public void testInvalidQuantity(){
    in = new ByteArrayInputStream("0".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    String expected="Quantity should be always a positive whole number";
    String actual="";
    try {
      portfolioView.getQuantity();
    } catch(IllegalArgumentException e){
      actual=e.getMessage();
    }
    assertEquals(expected,actual);
    actual="";
    in = new ByteArrayInputStream("-10".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    try {
      portfolioView.getQuantity();
    } catch(IllegalArgumentException e){
      actual=e.getMessage();
    }
    assertEquals(expected,actual);
  }

  @Test
  public void testCallToViewToAskCompanyTicker(){
    in = new ByteArrayInputStream("GOOG".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    String output =portfolioView.getCompanyTicker();
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
  public void testGetPortfolioNameToCreate(){
    in = new ByteArrayInputStream("P2".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    String output =portfolioView.getPortfolioNameToViewOrUpdate();
    assertEquals(output,"P2");
  }

  @Test
  public void testGetPortfolioNameToView(){
    in = new ByteArrayInputStream("P2".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    String output =portfolioView.getPortfolioNameToViewOrUpdate();
    assertEquals(output,"P2");
  }

  @Test
  public void testInValidPortfolioNameToView(){
    in = new ByteArrayInputStream("sample".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    String expected="Given portfolio doesnt exist";
    String actual="";
    try {
      portfolioView.getPortfolioNameToViewOrUpdate();
    } catch(IllegalArgumentException e){
      actual = e.getMessage();
    }
    assertEquals(expected,actual);

  }

  @Test
  public void testDisplayTotalValue(){

  }

  @Test
  public void  testGetDateForValuation(){
    in = new ByteArrayInputStream("2022-10-10".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    String output =portfolioView.getDateForValuation();
    assertEquals(output,"2022-10-10");
  }

  @Test
  public void  testInValidDateForValuation(){
    in = new ByteArrayInputStream("20-10-10".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    String expected ="Invalid dateFormat provided.";
    String actual="";
    try {
      portfolioView.getDateForValuation();
    } catch(IllegalArgumentException e){
      actual = e.getMessage();
    }
    assertEquals(actual,expected);
    actual="";
    in = new ByteArrayInputStream("20-10".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    try {
      portfolioView.getDateForValuation();
    } catch(IllegalArgumentException e){
      actual = e.getMessage();
    }
    assertEquals(actual,expected);
    actual="";
    in = new ByteArrayInputStream("sample".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    try {
      portfolioView.getDateForValuation();
    } catch(IllegalArgumentException e){
      actual = e.getMessage();
    }
    assertEquals(actual,expected);

  }

  @Test
  public void testDisplayComposition(){

  }

  @Test
  public void testInvalidUserInputs(){
    in = new ByteArrayInputStream("3".getBytes());
    portfolioView = new PortfolioViewImpl(in,out);
    String expected = "Invalid input provided";
    portfolioView.getBuyOrSellChoiceFromUser();
    assertEquals(expected,out.toString());
  }
}
