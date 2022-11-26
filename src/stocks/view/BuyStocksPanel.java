package stocks.view;

import java.util.List;

import javax.swing.*;

import stocks.controller.Features;

public class BuyStocksPanel extends JPanel implements PanelInterface {

  private JButton buyStocksBtn;
  private JLabel enterCommissionJLabel;
  private JTextField enterCommissionJTextField;
  private JLabel enterDateJLabel;
  private JTextField enterDateJTextField;
  private JLabel enterPortfolioNameJLabel;
  private JLabel enterStocksJLabel;
  private JTextField enterStocksJTextField;
  private JLabel enterTickerJLabel;
  private JTextField enterTickerJTextField;
  private JComboBox<String> portfolioNamesJCombo;
  private List<String> portfolioList;

  public BuyStocksPanel(List<String> portfolioList) {
    this.portfolioList = portfolioList;
    buyStocksBtn = new JButton("Buy Stocks");
    buyStocksBtn.setActionCommand("BUY");
    enterCommissionJLabel = new JLabel("Enter Commission");
    enterDateJLabel = new JLabel("Enter Date(YYYY-MM-DD)");
    enterTickerJLabel = new JLabel("Enter Company Ticker Symbol");
    enterStocksJLabel = new JLabel("Enter number of stocks to buy");
    enterPortfolioNameJLabel = new JLabel("Enter Portfolio Name");

    enterDateJTextField = new JTextField(30);
    enterStocksJTextField = new JTextField(30);
    enterTickerJTextField = new JTextField();
    enterCommissionJTextField = new JTextField();

    portfolioNamesJCombo = new JComboBox<>();
    portfolioNamesJCombo.setModel(new DefaultComboBoxModel<>(portfolioList.toArray(new String[0])));

    setLayout();

  }

  private void setLayout() {
    GroupLayout layout = new GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(enterTickerJLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(enterTickerJTextField)
                    .addComponent(enterDateJLabel, GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                    .addComponent(enterDateJTextField)
                    .addComponent(enterStocksJLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(enterStocksJTextField)
                    .addComponent(enterPortfolioNameJLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buyStocksBtn)
                    .addComponent(enterCommissionJLabel)
                    .addComponent(enterCommissionJTextField)
                    .addComponent(portfolioNamesJCombo, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));


    layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(enterTickerJLabel)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(enterTickerJTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(35, 35, 35)
                            .addComponent(enterDateJLabel)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(enterDateJTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(32, 32, 32)
                            .addComponent(enterStocksJLabel)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(enterStocksJTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(26, 26, 26)
                            .addComponent(enterCommissionJLabel)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(enterCommissionJTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                            .addComponent(enterPortfolioNameJLabel)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(portfolioNamesJCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(35, 35, 35)
                            .addComponent(buyStocksBtn)
                            .addGap(43, 43, 43))
    );
  }

  @Override
  public void delegateActions(Features feature) {
    buyStocksBtn.addActionListener(l -> {
      feature.buyStock(enterTickerJTextField.getText(), enterDateJTextField.getText(), enterStocksJTextField.getText(), enterCommissionJTextField.getText(), portfolioNamesJCombo.getSelectedItem().toString());
      reset();
      repaint();
    });

  }

  private void reset() {
    enterTickerJTextField.setText("");
    enterDateJTextField.setText("");
    enterStocksJTextField.setText("");
    enterCommissionJTextField.setText("");
  }
}
