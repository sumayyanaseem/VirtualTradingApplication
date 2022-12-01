package stocks.view;

import stocks.controller.Features;

/**
 * An interface to create different panels.
 */
public interface PanelInterface {

  /**
   * method to delegate actions on button click.
   *
   * @param feature feature object which has various actions that can called on portfolios.
   */
  void delegateActions(Features feature);

}
