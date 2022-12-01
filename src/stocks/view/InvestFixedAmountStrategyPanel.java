package stocks.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import stocks.controller.Features;

/**
 * This class represents a panel for creating InvestFixedAmountStrategy.
 */
public class InvestFixedAmountStrategyPanel extends JPanel implements PanelInterface {

  private final JLabel getEnterPortfolioNameJLabel;
  private final JComboBox<String> portfolioNamesJCombo;
  private final JLabel enterAmountJLabel;
  private final JTextField enterAmountJTextField;
  private final JLabel enterStartDateJLabel;
  private final JTextField enterStartDateJTextField;
  private final JLabel enterCommissionJLabel;
  private final JTextField enterCommissionJTextField;
  private final JTextArea displayStocks;
  private final JLabel enterStockAndPercentsJLabel;
  private final JTextField enterStockAndPercentsJTextField;
  private final JButton investBtn;

  /**
   * constructs an InvestFixedAmountStrategyPanel object.
   * @param portfolioList contains list of portfolios.
   */
  public InvestFixedAmountStrategyPanel(List<String> portfolioList) {
    investBtn = new JButton("Invest");
    investBtn.setActionCommand("INVEST");

    enterAmountJLabel = new JLabel("Enter Fixed Investment Amount");
    enterAmountJTextField = new JTextField(30);
    enterStartDateJLabel = new JLabel("Enter Date( YYYY-MM-DD ):");
    enterStartDateJTextField = new JTextField(30);
    enterCommissionJLabel = new JLabel("Enter Commission");
    enterCommissionJTextField = new JTextField(30);
    displayStocks = new JTextArea(5, 10);
    enterStockAndPercentsJLabel = new JLabel("Enter stock1 weight1,stock 2 weight 2,...(ex: META 50,GOOG 20...");
    enterStockAndPercentsJTextField = new JTextField(30);

    getEnterPortfolioNameJLabel = new JLabel("Select Portfolio to invest:");
    portfolioNamesJCombo = new JComboBox<>();
    portfolioNamesJCombo.setModel(new DefaultComboBoxModel<>(portfolioList.toArray(new String[0])));
    setLayout();
  }

  private void setLayout() {
    GroupLayout layout = new GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(getEnterPortfolioNameJLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(portfolioNamesJCombo, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(enterAmountJLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(enterAmountJTextField)
                    .addComponent(enterStartDateJLabel, GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                    .addComponent(enterStartDateJTextField)
                    .addComponent(enterCommissionJLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(enterCommissionJTextField)

                    .addComponent(enterStockAndPercentsJLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(enterStockAndPercentsJTextField)

                    .addComponent(investBtn));


    layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(getEnterPortfolioNameJLabel)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(portfolioNamesJCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(35, 35, 35)
                            .addComponent(enterAmountJLabel)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(enterAmountJTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(35, 35, 35)
                            .addComponent(enterStartDateJLabel)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(enterStartDateJTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(32, 32, 32)
                            .addComponent(enterCommissionJLabel)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(enterCommissionJTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(26, 26, 26)


                            .addComponent(enterStockAndPercentsJLabel)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(enterStockAndPercentsJTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(26, 26, 26)


                            .addComponent(investBtn)
                            .addGap(43, 43, 43))
    );
  }


  @Override
  public void delegateActions(Features feature) throws IllegalArgumentException {
    investBtn.addActionListener(l -> {
      String portfolioName = portfolioNamesJCombo.getSelectedItem().toString();
      double investmentAmount = Double.parseDouble(enterAmountJTextField.getText());
      double commissionFee = Double.parseDouble(enterCommissionJTextField.getText());
      String dateStart = enterStartDateJTextField.getText();
      String stocksAndPercents = enterStockAndPercentsJTextField.getText();
      String[] splitData = stocksAndPercents.split(",");
      Map<String, String> mapOfPercents = new HashMap<>();
      for (int i = 0; i < splitData.length; i++) {
        String[] eachStock = splitData[i].trim().split("\\s+");
        String comapany = eachStock[0].trim().toUpperCase();
        String percent = eachStock[1].trim();
        mapOfPercents.put(comapany, percent);
      }
      feature.investFixedAmountStrategy(portfolioName, mapOfPercents, investmentAmount, commissionFee, dateStart);
      reset();
      repaint();
    });
  }


  private void reset() {
    enterAmountJTextField.setText("");
    enterAmountJTextField.setText("");
    enterStartDateJTextField.setText("");
    enterCommissionJTextField.setText("");
    enterCommissionJTextField.setText("");
    displayStocks.setText("");
    enterStockAndPercentsJTextField.setText("");

  }
}
