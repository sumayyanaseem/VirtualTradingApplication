package stocks.controller.commands;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;

import stocks.model.IFlexible;
import stocks.model.Portfolio;
import stocks.view.PortfolioView;


/**
 * This represents the load command class for flexible/inflexible portfolio
 * that provides methods to load a flexible/inflexible portfolio through execute method.
 */
public class LoadPortfolioCommand implements Command {

  private final PortfolioView view;

  private final Portfolio inflexiblePortfolioTypeObj;

  private final IFlexible flexiblePortfolioTypeObj;

  private static final String flexibleType = "flexible";

  private static final String inflexibleType = "inflexible";

  private final String filePath;

  /**
   * Constructs the load command object by taking scanner, view objects to read input
   * and display output using view respectively, and also takes flexible and inflexible portfolio objects.
   *
   * @param view                       represents view object
   * @param inflexiblePortfolioTypeObj the object of an inflexible portfolio.
   * @param flexiblePortfolioTypeObj   the object of an flexible portfolio.
   * @param filePath                   the path of file to be loaded.
   */
  public LoadPortfolioCommand(PortfolioView view, Portfolio inflexiblePortfolioTypeObj, IFlexible flexiblePortfolioTypeObj, String filePath) {
    this.view = view;
    this.inflexiblePortfolioTypeObj = inflexiblePortfolioTypeObj;
    this.flexiblePortfolioTypeObj = flexiblePortfolioTypeObj;
    this.filePath = filePath;
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
