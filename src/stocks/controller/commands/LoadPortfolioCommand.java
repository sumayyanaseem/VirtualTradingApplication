package stocks.controller.commands;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import stocks.model.IFlexible;
import stocks.model.Portfolio;
import stocks.view.PortfolioView;

public class LoadPortfolioCommand implements Command{

  private final PortfolioView view;

  private final Scanner input;

  private Portfolio inflexiblePortfolioTypeObj;

  private IFlexible flexiblePortfolioTypeObj;

  private static final String flexibleType = "flexible";

  private static final String inflexibleType = "inflexible";

  private final String filePath;

  public LoadPortfolioCommand(PortfolioView view,  Scanner input,Portfolio inflexiblePortfolioTypeObj,IFlexible flexiblePortfolioTypeObj,String filePath){
    this.view = view;
    this.input = input;
    this.inflexiblePortfolioTypeObj = inflexiblePortfolioTypeObj;
    this.flexiblePortfolioTypeObj = flexiblePortfolioTypeObj;
    this.filePath=filePath;
  }

  @Override
  public void execute() {
    loadPortfolio();
  }

  private void loadPortfolio() {


    try {
      Object obj = new JSONParser().parse(new FileReader(filePath));
      JSONObject jsonObject = (JSONObject) obj;
      String type = (String) jsonObject.get("type");

      Portfolio portfolioTypeObj;
      if (type.equals(flexibleType)) {
        //flexible
        portfolioTypeObj = flexiblePortfolioTypeObj;
        portfolioTypeObj.loadPortfolioUsingFilePath(filePath);
      } else if (type.equals(inflexibleType)) {
        //inFlexible
        portfolioTypeObj = inflexiblePortfolioTypeObj;
        portfolioTypeObj.loadPortfolioUsingFilePath(filePath);
      }
    } catch (RuntimeException e) {
      view.displayMessage(e.getMessage());
      loadPortfolio();
    } catch (IOException | org.json.simple.parser.ParseException e) {
      view.displayMessage(e.getMessage());
      throw new IllegalArgumentException("start again");
    }
  }





}
