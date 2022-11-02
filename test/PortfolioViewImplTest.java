import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

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

}
