package stocks.controller;

import stocks.model.TradingModel;
import stocks.view.TradingView;

public class TradingController {

  private TradingModel model;
  private TradingView view;

  public TradingController(TradingModel model, TradingView view) {
    this.model = model;
    this.view = view;
  }

}
