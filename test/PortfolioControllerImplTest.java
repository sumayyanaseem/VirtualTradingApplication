import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import stocks.controller.PortfolioController;
import stocks.controller.PortfolioControllerImpl;
import stocks.model.PortfolioModel;
import stocks.view.PortfolioView;

public class PortfolioControllerImplTest {


  class MockModel implements PortfolioModel {

    private StringBuilder log;

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
    public List<List<String>> readFromCSVFile(String portfolioName) {
      return null;
    }

    @Override
    public void createPortfolioIfCreatedManually(String portfolioName) {

    }
  }

  class MockView implements PortfolioView {

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
    public String callToViewOnHowToCreatePortfolio() {
      setChoice(input);
      return choice;
    }

    @Override
    public String callToViewToGetFilePath() {
      setFilePath(input);
      return filePath;
    }

    @Override
    public String callToViewToAskPortfolioName() {
      setPName(input);
      return pName;
    }

    @Override
    public String callToViewToChoiceOption() {
      setChoice(input);
      return choice;
    }

    @Override
    public String callToViewToAskQuantity() {
      setQuantity(input);
      return quantity;
    }

    @Override
    public String callToViewToAskCompanyTicker() {
      setCompanyName(input);
      return companyName;
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
    public String getPortfolioNameToView() {
      setPName(input);
      return pName;
    }

    @Override
    public void displayComposition(List<List<String>> records) {

    }

    @Override
    public String askForPortfolioNameToGetValuation() {
      setPName(input);
      return pName;
    }
  }





  @Test
  public void initialState(){

   // model.createPortfolioIfCreatedManually("P3");

  }

  @Test
  public void testStart(){
    InputStream in = new ByteArrayInputStream("1".getBytes());
    PortfolioView view = new MockView(in);
    PortfolioController portfolioController = new PortfolioControllerImpl(view);
    portfolioController.start(new MockModel());
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
}
