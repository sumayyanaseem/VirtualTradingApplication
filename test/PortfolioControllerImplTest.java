import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import stocks.controller.PortfolioController;
import stocks.controller.PortfolioControllerImpl;
import stocks.model.PortfolioModel;
import stocks.view.PortfolioView;
import stocks.view.PortfolioViewImpl;

import static org.junit.Assert.assertTrue;

public class PortfolioControllerImplTest {

  class MockModel implements PortfolioModel {

    private StringBuilder log;

    MockModel(StringBuilder log){
      this.log=log;
    }

    @Override
    public void buyStocks(String quantity, String CompanyName, String portfolioName) {
      log.append("inputs for buyStocks: "+quantity+" "+CompanyName+" "+portfolioName+"\n");

    }

    @Override
    public double getTotalValueOfPortfolioOnCertainDate(String date, String portfolioName) {
      log.append("inputs for getTotalValueOfPortfolioOnCertainDate: "+date+" "+portfolioName+"\n");
      return 0.0;
    }

    @Override
    public void createPortfolioUsingFilePath(String filePath) {
      log.append("inputs for createPortfolioUsingFilePath: "+filePath+"\n");
    }

    @Override
    public List<List<String>> viewCompositionOfCurrentPortfolio(String portfolioName) {
      List<List<String>> dummy=new ArrayList<>();
      log.append("inputs for viewCompositionOfCurrentPortfolio: "+portfolioName+"\n");
      return dummy;
    }

    @Override
    public void createPortfolioIfCreatedManually(String portfolioName) {
      log.append("inputs for createPortfolioIfCreatedManually: "+portfolioName+"\n");
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
    public PortfolioModel getInstance() {
      return new MockModel(new StringBuilder());
    }
  }

  private InputStream in;

  private PortfolioView view;

  private PrintStream out;

  private PortfolioController portfolioController;

  private OutputStream bytes;

  PortfolioModel model;

  @Before
  public void setUp(){
    in = new ByteArrayInputStream("1\n".getBytes());
    bytes = new ByteArrayOutputStream();
    out = new PrintStream(bytes);
    view = new PortfolioViewImpl(out);
    StringBuilder mockLog = new StringBuilder();
    model = new MockModel(mockLog);
    portfolioController = new PortfolioControllerImpl(in,view);
  }


 /* @Test
  public void testInitialState(){
    String expected="Enter 1: To trade stocks 2: To view the portfolio composition\n";
    in = new ByteArrayInputStream("".getBytes());
    StringBuilder mockLog = new StringBuilder();
    //String expected = "Adding inputs: "+a+" "+b+"\n";
    PortfolioModel model = new MockModel(mockLog);
    portfolioController = new PortfolioControllerImpl(in,view);
    System.out.println("begin");
    try {
      portfolioController.start();
    } catch(NoSuchElementException e){

    }
    System.out.println("end");
    System.out.println(bytes.toString());
    assertTrue(bytes.toString().equals(expected));
  }

  @Test
  public void testInvalidInputs(){
    String expected="Enter 1: To trade stocks 2: To view the portfolio composition";
    String error="Invalid input provided.Please provide a valid input (either 1 or 2)";
    in = new ByteArrayInputStream("1\n4\n".getBytes());
    StringBuilder mockLog = new StringBuilder();
    //String expected = "Adding inputs: "+a+" "+b+"\n";
    PortfolioModel model = new MockModel(mockLog);
    portfolioController = new PortfolioControllerImpl(in,view);
    try {
      portfolioController.start();
    } catch(NoSuchElementException e){

    }
    System.out.println(bytes.toString());
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(error));
  }


  @Test
  public void testStart(){
    String expected="Enter 1: To create Portfolio using external file  2: To create manually";
    in = new ByteArrayInputStream("1\n".getBytes());
    StringBuilder mockLog = new StringBuilder();
    //String expected = "Adding inputs: "+a+" "+b+"\n";
    PortfolioModel model = new MockModel(mockLog);
    portfolioController = new PortfolioControllerImpl(in,view);
    try {
      portfolioController.start();
    } catch(NoSuchElementException e){
    }
    assertTrue(bytes.toString().contains(expected));
  }


  @Test
  public void testAskUserWhatHeWantsToView(){
    in = new ByteArrayInputStream("2\n".getBytes());
    StringBuilder mockLog = new StringBuilder();
    //String expected = "Adding inputs: "+a+" "+b+"\n";
    PortfolioModel model = new MockModel(mockLog);
    String expected="Enter 1: To view composition of existing portfolio . 2: To get TotalValue of a portfolio";
    portfolioController = new PortfolioControllerImpl(in,view);
    try {
      portfolioController.start();
    } catch(NoSuchElementException e){
    }
    System.out.println(bytes.toString());
    assertTrue(bytes.toString().contains(expected));
  }

  @Test
  public void testGetCreatePortfolioChoice(){
    String expected = "Enter 1: To create Portfolio using external file  2: To create manually\n" +
            "Enter the path of File which is used to create Portfolio\n";
    in = new ByteArrayInputStream("1\n1\n".getBytes());
    StringBuilder mockLog = new StringBuilder();
    PortfolioModel model = new MockModel(mockLog);
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch(NoSuchElementException e){
    }
    System.out.println(bytes.toString());
    assertTrue(bytes.toString().contains(expected));
  }

  @Test
  public void testCreatePortfolioUsingFilePath(){
    String expected = "Enter the path of File which is used to create Portfolio\n";
    String error ="FilePath Doesn't exist. Try again with correct path.";
    in = new ByteArrayInputStream("1\n1\npath\n".getBytes());
    StringBuilder mockLog = new StringBuilder();
    PortfolioModel model = new MockModel(mockLog);
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch(NoSuchElementException e){
    }

    System.out.println(bytes.toString());

    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(error));
  }

  @Test
  public void testInvalidInputs2(){
    String expected="Enter 1: To create Portfolio using external file  2: To create manually\n" +
            "Invalid input provided.Please provide a valid input (either 1 or 2)\n" +
            "Enter 1: To create Portfolio using external file  2: To create manually";
    String error="Invalid input provided.Please provide a valid input (either 1 or 2)";
    in = new ByteArrayInputStream("1\n4\n".getBytes());
    StringBuilder mockLog = new StringBuilder();
    PortfolioModel model = new MockModel(mockLog);
    portfolioController = new PortfolioControllerImpl(in,view);
    try {
      portfolioController.start();
    } catch(NoSuchElementException e){

    }
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(error));


  }*/


  @Test
  public void testCreatePortfolioManuallyEndToEnd(){
    String expected = "Enter 1: To trade stocks 2: To view the portfolio composition\n" +
            "Enter 1: To create Portfolio using external file  2: To create manually\n" +
            "Enter the name of the Portfolio\n" +
            "Enter the name of the company to be added to portfolio\n" +
            "Enter the quantity of the stocks\n" +
            "Enter 1: To continue trading in current portfolio.  2: To exit from current Portfolio.\n" +
            "Enter 1: To continue trading further. 2: To exit from this session.";
    in = new ByteArrayInputStream("1\n2\nsample\nmeta\n10\n2\n2\n".getBytes());
    StringBuilder mockLog = new StringBuilder();
    model = new MockModel(mockLog);
    String quantity="10";
    String companyName="meta";
    String portfolioName="sample";
    String exp="inputs for buyStocks: "+quantity+" "+companyName+" "+portfolioName+"\n";
    //model.buyStocks(quantity,companyName,portfolioName);
   // System.out.println(mockLog.toString().length());
    portfolioController = new PortfolioControllerImpl(in,view);
    try {
      portfolioController.start(model);
    } catch(NoSuchElementException e){

    }

    System.out.println(mockLog.toString().length());
   // assertEquals(exp,mockLog.toString());
    assertTrue(mockLog.toString().contains(exp));
    System.out.println(bytes.toString());
    assertTrue(bytes.toString().contains(expected));
    //remove below two lines
    //File file = new File("UserPortfolios/sample.csv");
    //assertTrue(file.isFile());
  }

}





 /* @Test(expected = NoSuchElementException.class)
  public void testInvalidInputs(){
    String expected="Enter 1: To trade stocks 2: To view the portfolio composition";
    String error="Invalid input provided.Please provide a valid input (either 1 or 2)";
    in = new ByteArrayInputStream("1\n4\n".getBytes());
    portfolioController = new PortfolioControllerImpl(new MockModel(),in,view);
    portfolioController.start();
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(error));
  }

  @Test(expected = NoSuchElementException.class)
  public void testStart(){
    String expected="Enter 1: To create Portfolio using external file  2: To create manually";
    in = new ByteArrayInputStream("1\n".getBytes());
    portfolioController = new PortfolioControllerImpl(new MockModel(),in,view);
    portfolioController.start();
    assertTrue(bytes.toString().contains(expected));
  }

  @Test(expected = NoSuchElementException.class)
  public void testAskUserWhatHeWantsToView(){
    in = new ByteArrayInputStream("2\n".getBytes());
    String expected="Enter 1: To view composition of existing portfolio . 2: To get TotalValue of a portfolio";
    portfolioController = new PortfolioControllerImpl(new MockModel(),in,view);
    portfolioController.start();
    assertTrue(bytes.toString().contains(expected));
  }

  @Test(expected = NoSuchElementException.class)
  public void testGetCreatePortfolioChoice(){
    String expected = "Enter 1: To create Portfolio using external file  2: To create manually";
    in = new ByteArrayInputStream("1\n1\n".getBytes());
    portfolioController = new PortfolioControllerImpl(new MockModel(),in, view);
    portfolioController.start();
    assertTrue(bytes.toString().contains(expected));
  }

  @Test(expected = NoSuchElementException.class)
  public void testCreatePortfolioUsingFilePath(){
    String expected = "Enter the path of File which is used to create Portfolio";
    String error ="FilePath Doesn't exist. Try again with correct path.\n";
    in = new ByteArrayInputStream("1\n1\npath\n".getBytes());
    portfolioController = new PortfolioControllerImpl(new MockModel(),in, view);
    portfolioController.start();
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(error));
  }

  @Test
  public void testInvalidInputs2(){
    String expected="Enter 1: To create Portfolio using external file  2: To create manually\n" +
            "Invalid input provided.Please provide a valid input (either 1 or 2)\n" +
            "Enter 1: To create Portfolio using external file  2: To create manually";
    String error="Invalid input provided.Please provide a valid input (either 1 or 2)";
    in = new ByteArrayInputStream("1\n4\n".getBytes());
    portfolioController = new PortfolioControllerImpl(new MockModel(),in,view);
    try {
      portfolioController.start();
    } catch(NoSuchElementException e){

    }
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(error));


  }

  @Test
  public void testCreatePortfolioManuallyEndToEnd(){
    String expected = "Enter 1: To trade stocks 2: To view the portfolio composition\n" +
            "Enter 1: To create Portfolio using external file  2: To create manually\n" +
            "Enter the name of the Portfolio\n" +
            "Enter the name of the company to be added to portfolio\n" +
            "Enter the quantity of the stocks\n" +
            "Enter 1: To continue trading in current portfolio.  2: To exit from current Portfolio.\n" +
            "Enter 1: To continue trading further. 2: To exit from this session.";
    in = new ByteArrayInputStream("1\n2\nsample\nmeta\n10\n2\n2\n".getBytes());
    portfolioController = new PortfolioControllerImpl(new MockModel(),in, view);
    portfolioController.start();
    assertTrue(bytes.toString().contains(expected));
    //remove below two lines
    File file = new File("UserPortfolios/sample.csv");
    assertTrue(file.isFile());
  }



  @Test(expected = NoSuchElementException.class)
  public void testCreateNewPortfolioForCurrentUser(){
    String expected = "Enter the name of the Portfolio";
    in = new ByteArrayInputStream("1\n2\nsample\n".getBytes());
    portfolioController = new PortfolioControllerImpl(new MockModel(),in, view);
    portfolioController.start();
    assertTrue(bytes.toString().contains(expected));
  }

  @Test
  public void testStoppingCondition(){

  }

  @Test
  public void testFinalExitCondition(){

  }

  @Test(expected = NoSuchElementException.class)
  public void testBuyStocks(){
    String expected = "Enter 1: To buy stocks 2: To sell stocks";
    String e ="Enter the quantity of the stocks.\n"+"Enter the quantity of the stocks.\n";
    in = new ByteArrayInputStream("1\n2\nsample\nmeta\n10\n".getBytes());
    portfolioController = new PortfolioControllerImpl(new MockModel(),in, view);
    portfolioController.start();
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(e));
  }

  @Test
  public void testBuyMultipleStocks(){
    String expected = "Enter 1: To continue trading in current portfolio.  2: To exit from current Portfolio.";
    in = new ByteArrayInputStream("1\n2\ntest.csv\nmeta\n10\n1\ndash\n20\n".getBytes());
    portfolioController = new PortfolioControllerImpl(new MockModel(),in, view);
    try {
      portfolioController.start();
    } catch(NoSuchElementException e){

    }
    assertTrue(bytes.toString().contains(expected));
    File file = new File("UserPortfolios/test.csv.csv");
    assertFalse(file.exists());

  }

  @Test
  public void testInvalidDate(){
    String expected = "Enter date as of which you need portfolio valuation( YYYY-MM-DD format only)";
    String error ="Invalid dateFormat provided.Please provide date in YYYY-MM-DD format only.\n" +
            "Enter date as of which you need portfolio valuation( YYYY-MM-DD format only)";
    in = new ByteArrayInputStream("2\n2\nP2\n2022\n1234\n22-10-11\n2022-1\n2023-20-10\n".getBytes());
    portfolioController = new PortfolioControllerImpl(new MockModel(),in, view);
    try {
      portfolioController.start();
    } catch(NoSuchElementException e ){

    }
    System.out.println(bytes.toString());
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(error));


  }

  @Test
  public void testInvalidFilePath(){

  }

  @Test
  public void testCreatePortfolioUsingPath(){

  }

  @Test
  public void testInvalidPortfolioNameToView(){
    String expected = "Enter 1: To view composition of existing portfolio . 2: To get TotalValue of a portfolio";
    String error ="Given portfolio doesnt exist.Please provide valid portfolioName.\n" +
            "Enter the name of the Portfolio";
    in = new ByteArrayInputStream("2\n1\ntest.csv\n".getBytes());
    portfolioController = new PortfolioControllerImpl(new MockModel(),in, view);
    try {
      portfolioController.start();
    } catch(NoSuchElementException e){

    }
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(error));
  }

  @Test
  public void testMultiplePortfoliosCreated(){

  }

  @Test
  public void testViewCompositionOfAPortfolio(){
    String expected = "Enter 1: To view composition of existing portfolio . 2: To get TotalValue of a portfolio";
    String output = "CompanyName Quantity PriceBought DatePurchase TotalValueOwned TotalValueOwnedAsOfToday \n" +
            "META        10.00    99.2        2022-11-02   992.00          992.00   ";
    in = new ByteArrayInputStream("2\n1\nsample\n".getBytes());
    portfolioController = new PortfolioControllerImpl(new MockModel(),in, view);
    try {
      portfolioController.start();
    } catch(NoSuchElementException e){

    }
    System.out.println(bytes.toString());
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(output));
  }

  @Test
  public void testViewTotalValueOfAPortfolioOnaCertainDate(){
    String expected = "Enter date as of which you need portfolio valuation( YYYY-MM-DD format only)";
    String output = "Total Valuation of Portfolio   on 2022-10-01 is :1356.80";
    in = new ByteArrayInputStream("2\n2\nsample\n2022-10-01\n".getBytes());
    portfolioController = new PortfolioControllerImpl(new MockModel(),in, view);
    try {
      portfolioController.start();
    } catch(NoSuchElementException e){

    }
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(output));
  }

  @Test
  public void testCreateViewAndTotalValue(){

  }

  @Test
  public void testInvalidQuantity(){
    String error ="Invalid quantity provided\n"+"Enter the quantity of the stocks\n";
    String error2="Quantity should be always a positive whole number.\n" +
            "Enter the quantity of the stocks";
    in = new ByteArrayInputStream("1\n2\ntest.csv\nmeta\n-100\n0.5\nabc\n".getBytes());
    portfolioController = new PortfolioControllerImpl(new MockModel(),in, view);
    try {
      portfolioController.start();
    } catch(NoSuchElementException e){

    }
    System.out.println(bytes.toString());
    assertTrue(bytes.toString().contains(error));
    assertTrue(bytes.toString().contains(error2));
  }*/


