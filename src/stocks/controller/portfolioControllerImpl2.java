package stocks.controller;

import java.io.InputStream;
import java.util.Scanner;

import stocks.model.PortfolioModel;
import stocks.view.PortfolioView;

public class portfolioControllerImpl2 implements PortfolioController{

  private PortfolioModel model;
  private String portfolioName;
  private final PortfolioView view;
  private final Scanner input;


  public PortfolioControllerImpl(InputStream in, PortfolioView view) {
    this.input = new Scanner(in);
    this.view = view;
    this.portfolioName = "";
  }
  @Override
  public void start(PortfolioModel model) {

    while(input.hasNext()){
      String option = String.valueOf(input.nextLine());
      switch (option){

      }
    }

  }
}
