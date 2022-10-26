package stocks.controller;

import java.io.File;
import java.io.InputStream;

import stocks.model.PortfolioModel;

public class PortfolioControllerImpl implements  PortfolioController{

  private PortfolioModel model;

  public  PortfolioControllerImpl(PortfolioModel model, InputStream in){
    this.model = model;
    this.input =in;
  }

  public void go(){
    callToViewToChooseCreateOrView();
    Switch(option){
      case 1:
        goForCreate();
        break;
      case 2:
        Json json= fetchDetailsFromModelToDisplay();
        callToViewTODispylay(json);
        break;
    }
  }

  public void goForCreate(){
    callToViewToHowToCreatePortfolio();//using Json/XML file or individual inputs
    switch(option){
      case 1:
        helper();
        break;
      case 2:
        File file = callToViewToGetFile();
        model.createUsingFile(file);
        break;
    }

  }

  public void helper(){
    callToViewToAskPortfoliName();
    //model.createPortfolio(portfolioName);
    //instead of above line, create object for PortfolioImplModel class.(use this obj for passPortofolioObject below)
    callToViewToChoiceOption();
    Switch(option) {
      case 1:
        callToViewToAskQuantity();
        callToViewToAskCompanyTicker();
        model.buyStocks(quantity,companyName,passPortofolioObject);
        break;
      case 2:
        callToViewToAskQuantity();
        callToViewToAskCompanyTicker();
        model.sellStocks(quantity,companyName,passPortofolioObject);
        break;
    }
  }
}
