package stocks.controller;

import java.io.IOException;

import stocks.model.PortfolioModel;

public interface PortfolioController {

  void start(PortfolioModel model) throws IOException;

}
