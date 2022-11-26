package stocks.view;

import java.util.List;

import javax.swing.*;

import stocks.controller.Features;

public class InvestFixedAmountStrategyPanel extends JPanel implements PanelInterface{

  private JLabel getEnterPortfolioNameJLabel;
  private JComboBox<String> portfolioNamesJCombo;
  private JLabel enterAmountJLabel;
  private JTextField enterAmountJTextField;
  private JLabel enterStartDateJLabel;
  private JTextField enterStartDateJTextField;
  private JLabel enterCommissionJLabel;
  private JTextField enterCommissionJTextField;
  private JLabel displayStocksJLabel;
  private JTextArea displayStocks;
  private JLabel enterStockAndPercentsJLabel;
  private JTextField enterStockAndPercentsJTextField;
  private JButton investBtn;
  private List<String> portfolioList;

  public InvestFixedAmountStrategyPanel(List<String> portfolioList) {
    this.portfolioList = portfolioList;
    investBtn = new JButton("Invest");
    investBtn.setActionCommand("INVEST");

    enterAmountJLabel = new JLabel("Enter Investment Amount");
    enterAmountJTextField = new JTextField(30);
    enterStartDateJLabel = new JLabel("Enter Date( YYYY-MM-DD ):");
    enterStartDateJTextField = new JTextField(30);
    enterCommissionJLabel = new JLabel("Enter Commission");
    enterCommissionJTextField = new JTextField(30);
    displayStocksJLabel = new JLabel("Stocks Available in Portfolio:");
    displayStocks = new JTextArea(5,10);
    enterStockAndPercentsJLabel = new JLabel("Enter stock1 weight1,stock 2 weight 2,...(ex: META 50,GOOG 20...");
    enterStockAndPercentsJTextField = new JTextField(30);

    getEnterPortfolioNameJLabel = new JLabel("Select Portfolio to invest:");
    portfolioNamesJCombo = new JComboBox<>();
    portfolioNamesJCombo.setModel(new DefaultComboBoxModel<>(portfolioList.toArray(new String[0])));
    setLayout();
  }

  private void setLayout(){
    GroupLayout layout = new GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(enterAmountJLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(enterAmountJTextField)
                    .addComponent(enterStartDateJLabel, GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                    .addComponent(enterStartDateJTextField)
                    .addComponent(enterCommissionJLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(enterCommissionJTextField)
                    .addComponent(displayStocksJLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(displayStocks)
                    .addComponent(enterStockAndPercentsJLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(enterStockAndPercentsJTextField)
                    .addComponent(getEnterPortfolioNameJLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(portfolioNamesJCombo, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(investBtn));




    layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
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

                            .addComponent(displayStocksJLabel)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(displayStocks, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(26, 26, 26)


                            .addComponent(enterStockAndPercentsJLabel)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(enterStockAndPercentsJTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(26, 26, 26)


                            .addComponent(getEnterPortfolioNameJLabel)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(portfolioNamesJCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(35, 35, 35)
                            .addComponent(investBtn)
                            .addGap(43, 43, 43))
    );
  }


  @Override
  public void delegateActions(Features feature) {

  }
}
