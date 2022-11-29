import org.junit.Test;

import stocks.TradingMVC;

public class TradingMVCTest {


  @Test
  public void testStartApplicationForTextUI(){
    String[] args=new String[1];
    args[0]="console";
    TradingMVC.main(args);
  }

  @Test
  public void testStartApplicationForGUI(){
    String[] args=new String[1];
    args[0]="gui";
    TradingMVC.main(args);

  }
}
