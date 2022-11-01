import org.junit.Before;
import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import stocks.controller.PortfolioController;
import stocks.controller.PortfolioControllerImpl;
import stocks.model.PortfolioModel;

import static org.junit.Assert.assertEquals;

public class PortfolioControllerImplTest {

  class MockModel implements PortfolioModel {

    @Override
    public void buyStocks(String quantity, String CompanyName, String portfolioName)  {

    }

    @Override
    public double getTotalValueOfPortfolioOnCertainDate(String date, String portfolioName) {
      return 0;
    }

    @Override
    public void createPortfolioUsingFilePath(String filePath)  {

    }

    @Override
    public List<List<String>> viewCompositionOfCurrentPortfolio(String portfolioName) {
      return null;
    }


    @Override
    public void createPortfolioIfCreatedManually(String portfolioName) {

    }
  }

  /*class MockView implements PortfolioView {

    public String choice="1";
    public String pName;
    public String dateToViewTotalValue;
    public String filePath;
    public String quantity;

    public String companyName;

    InputStream in;

    String input;
    public MockView(InputStream in){
      this.in = in;
      input = in.toString();
    }

    public void setPName(String pName) {
      this.pName = pName;
    }

    public void setDateToViewTotalValue(String dateToViewTotalValue) {
      this.dateToViewTotalValue = dateToViewTotalValue;
    }

    public void setFilePath(String filePath) {
      this.filePath = filePath;
    }

    public void setQuantity(String quantity) {
      this.quantity = quantity;
    }

    public void setCompanyName(String companyName) {
      this.companyName = companyName;
    }

    public void setChoice(String choice){
      this.choice=choice;
    }


    @Override
    public String callToViewToChooseCreateOrView() {
      setChoice(input);
      return choice;
    }

    @Override
    public String askUserOnHowToCreatePortfolio() {
      return null;
    }

    @Override
    public String getFilePath() {
      return null;
    }

    @Override
    public String getPortfolioNameToCreate() {
      return null;
    }

    @Override
    public String getBuyOrSellChoiceFromUser() {
      return null;
    }

    @Override
    public String getQuantity() {
      return null;
    }

    @Override
    public String getCompanyTicker() {
      return null;
    }

    @Override
    public String askUserIfHeWantsToContinueTradingInCurrentPortfolio() {
      setChoice(input);
      return String.valueOf(choice);
    }

    @Override
    public String checkIfUserWantsToExitCompletely() {
      setChoice(input);
      return choice;
    }

    @Override
    public String createOrUpdateExistingPortfolio() {
      setChoice(input);
      return choice;
    }

    @Override
    public String checkIfUserWantsToViewCompositionOrTotalValue() {
      setChoice(input);
      return choice;
    }

    @Override
    public void displayTotalValue(String date, String val, String portfolioName) {

    }

    @Override
    public String getDateForValuation() {
      setDateToViewTotalValue(input);
      return dateToViewTotalValue;
    }

    @Override
    public String getPortfolioNameToViewOrUpdate() {
      setPName(input);
      return pName;
    }

    @Override
    public void displayComposition(List<List<String>> records) {

    }
  }*/





  @Test
  public void initialState(){

   // model.createPortfolioIfCreatedManually("P3");

  }

  @Test
  public void testStart(){
    /*InputStream in = new ByteArrayInputStream("1".getBytes());
    OutputStream out  new BufferedOutputStream();
    PortfolioController portfolioController = new PortfolioControllerImpl(in,);
    portfolioController.start(new MockModel());*/
  }

  @Test
  public void testAskUserWhatHeWantsToView(){
  }

  @Test
  public void testCreateOrUpdatePortfolio(){

  }

  @Test
  public void testUpdatePortfolio(){

  }

  @Test
  public void testGetCreatePortfolioChoice(){

  }

  @Test
  public void testStoppingCondition(){

  }

  @Test
  public void testCreateNewPortfolioForCurrentUser(){

  }

  @Test
  public void testFinalExitCondition(){

  }

  @Test
  public void testBuyOrSellStocks(){

  }

  /*PortfolioView portfolioView;
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
  }*/
}
