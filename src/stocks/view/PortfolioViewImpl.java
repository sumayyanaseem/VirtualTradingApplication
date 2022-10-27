package stocks.view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PortfolioViewImpl implements PortfolioView {

  private InputStream input;

  public PortfolioViewImpl(){
    input= System.in;
  }
  @Override
  public String callToViewToChooseCreateOrView() {
    System.out.println("Enter 1: To trade stocks 2: To view the portfolio composition");
    Scanner myObj = new Scanner(input);
    return myObj.nextLine();
  }

  @Override
  public String callToViewOnHowToCreatePortfolio() {
    System.out.println("Enter 1: To create Portfolio using external file  2: To create manually");
    Scanner myObj = new Scanner(input);
    return myObj.nextLine();
  }

  @Override
  public String callToViewToGetFilePath() {
    System.out.println("Enter the path of File which is used to create Portfolio");
    Scanner myObj = new Scanner(input);
    return myObj.nextLine();
  }

  @Override
  public String callToViewToAskPortfolioName() {
    System.out.println("Enter the name of the Portfolio");
    Scanner myObj = new Scanner(input);
    return myObj.nextLine();
  }

  @Override
  public String callToViewToChoiceOption() {
    System.out.println("Enter 1: To buy stocks 2: To sell stocks");
    Scanner myObj = new Scanner(input);
    return myObj.nextLine();
  }

  @Override
  public String callToViewToAskQuantity() {
    System.out.println("Enter the quantity of the stocks.");
    Scanner myObj = new Scanner(input);
    String quantity = myObj.nextLine();
    validateQuantity(quantity);
    return quantity;
  }

  private void validateQuantity(String quantity){
    try {
      Integer q = Integer.parseInt(quantity);
      if(q<=0){
        throw new IllegalArgumentException("Invalid quantity provided");
      }
    } catch(IllegalArgumentException e){
      throw new IllegalArgumentException("Quantity should be always a whole number");
    }
  }

  @Override
  public String callToViewToAskCompanyTicker() {
    System.out.println("Enter the name of the company");
    Scanner myObj = new Scanner(input);
    String companyName = myObj.nextLine();
    //validateCompanyName(companyName);
    return companyName;
  }

  @Override
  public String askUserIfHeWantsToContinueTradingInCurrentPortfolio() {
    System.out.println("Enter 1: To continue trading in current portfolio.  2: To exit from current Portfolio.");
    Scanner myObj = new Scanner(input);
    return myObj.nextLine();
  }

  @Override
  public String checkIfUserWantsToExitCompletely() {
    System.out.println("Enter 1: To continue trading further. 2: To exit from this session.");
    Scanner myObj = new Scanner(input);
    return myObj.nextLine();
  }

  @Override
  public String createOrUpdateExistingPortfolio() {
    System.out.println("Enter 1: To create a portfolio. 2: To update a existing portfolio.");
    Scanner myObj = new Scanner(input);
    return myObj.nextLine();
  }

  @Override
  public String checkIfUserWantsToViewCompositionOrTotalValue() {
    System.out.println("Enter 1: To view composition of existing portfolio . 2: To get TotalValue of a portfolio");
    Scanner myObj = new Scanner(input);
    return myObj.nextLine();
  }

  @Override
  public String getPortfolioNameToView() {
    System.out.println("Enter the name of the portfolio you wanna view");
    Scanner myObj = new Scanner(input);
    //validate if this portfolio exists.
    return myObj.nextLine();
  }

  @Override
  public String getDateForValuation() {
    return null;
  }

  @Override
  public List<List<String>> readFromCSV(String portfolioName)  {
    String path = portfolioName+".csv";
    List<List<String>> records = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
      String line = br.readLine();
      String[] headings =line.split(",");
      for(int i=0;i<headings.length;i++) {
        System.out.print(headings[i] + " ");
      }
      System.out.print("\n");
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
        records.add(Arrays.asList(values));
      }
    } catch (FileNotFoundException ex) {
      throw new RuntimeException(ex);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    for(int i=0;i<records.size();i++){

      for(int j=0;j<records.get(i).size();j++){
        System.out.print(records.get(i).get(j));
        int len = records.get(0).get(j).length();
        for(int k=0;k<len;k++) {
          System.out.print(" ");
        }
      }
      System.out.println("");
    }

    return records;
  }


}