package stocks.view;

import java.util.List;


import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import stocks.controller.Features;


/**
 * This class represents a panel fo QueryPortfolio action.
 */
public class QueryPortfolioPanel extends JPanel implements PanelInterface {

  private final JComboBox<String> portfoliosToViewJCombo;
  private final JLabel enterPortfolioNameJLabel;
  private final JLabel enterDateJLabel;
  private final JTextField enterDateJTextField;
  private final JButton viewPortfolioCompositionBtn;
  private final JButton viewTotalValueBtn;
  private final JButton viewTotalCostBasisBtn;
  private final JTextArea viewPortfolioJTextArea;

  private final JScrollPane jScrollPane;

  /**
   * constructs an QueryPortfolioPanel object.
   *
   * @param portfolioList contains list of portfolios.
   */
  public QueryPortfolioPanel(List<String> portfolioList) {
    enterDateJLabel = new JLabel("Enter Date(YYYY-MM-DD)");
    enterPortfolioNameJLabel = new JLabel("Enter Portfolio name");
    viewPortfolioCompositionBtn = new JButton("View Composition");
    viewTotalValueBtn = new JButton("View Total Value");
    viewTotalCostBasisBtn = new JButton("View Total Cost basis");

    enterDateJTextField = new JTextField();
    portfoliosToViewJCombo = new JComboBox<>();
    viewPortfolioJTextArea = new JTextArea();
    viewPortfolioJTextArea.setColumns(20);
    viewPortfolioJTextArea.setRows(5);
    viewPortfolioJTextArea.setEditable(false);
    jScrollPane = new JScrollPane();


    portfoliosToViewJCombo.setModel(
            new DefaultComboBoxModel<>(portfolioList.toArray(new String[0])));

    viewTotalCostBasisBtn.setActionCommand("TOTAL_COST");
    viewTotalValueBtn.setActionCommand("TOTAL_VALUE");
    viewPortfolioCompositionBtn.setActionCommand("DISPLAY_COMPOSITION");
    jScrollPane.setViewportView(viewPortfolioJTextArea);
    setLayout();

  }

  private void setLayout() {
    GroupLayout layout = new GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane)
            .addComponent(enterPortfolioNameJLabel,
                    GroupLayout.Alignment.LEADING, 0,
                    GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(portfoliosToViewJCombo,
                    GroupLayout.Alignment.LEADING, 0,
                    GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(enterDateJLabel,
                    GroupLayout.Alignment.LEADING,
                    GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(enterDateJTextField, GroupLayout.Alignment.LEADING)
            .addGap(32, 32, 32)
            .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(viewPortfolioCompositionBtn,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(viewTotalValueBtn,
                                    GroupLayout.PREFERRED_SIZE,
                                    280, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(viewTotalCostBasisBtn,
                                    GroupLayout.PREFERRED_SIZE,
                                    280, GroupLayout.PREFERRED_SIZE))
            )
            .addGap(32, 32, 32));
    layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(enterPortfolioNameJLabel)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(portfoliosToViewJCombo,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(16, 16, 16)
                            .addComponent(enterDateJLabel)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(enterDateJTextField,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(16, 16, 16)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(viewPortfolioCompositionBtn)
                                    .addComponent(viewTotalValueBtn)
                                    .addComponent(viewTotalCostBasisBtn))
                            .addGap(18, 18, 18)
                            .addComponent(jScrollPane)
                            .addContainerGap())
    );
  }

  @Override
  public void delegateActions(Features feature) {
    viewPortfolioCompositionBtn.addActionListener(l -> {

      List<List<String>> records = feature.viewComposition(
              portfoliosToViewJCombo.getSelectedItem().toString(), enterDateJTextField.getText());
      StringBuilder detailedOutput = new StringBuilder();
      for (int i = 0; i < records.size(); i++) {
        for (int j = 0; j < records.get(i).size(); j++) {
          detailedOutput.append(records.get(i).get(j) + "\t");
        }
        detailedOutput.append("\n");
      }
      viewPortfolioJTextArea.setText(detailedOutput.toString());
      reset();
      repaint();
    });


    viewTotalValueBtn.addActionListener(l -> {
      double value;
      try {
        value = feature.getTotalValue(
                portfoliosToViewJCombo.getSelectedItem().toString(), enterDateJTextField.getText());
      } catch (Exception e) {
        return;
      }
      String detailedOutput = "Total value of portfolio "
              + portfoliosToViewJCombo.getSelectedItem().toString()
              + " on " + enterDateJTextField.getText() + " is :" + value;
      viewPortfolioJTextArea.setText(detailedOutput);

      reset();
      repaint();
    });

    viewTotalCostBasisBtn.addActionListener(l -> {

      double totalCostBasis = feature.getCostBasis(
              portfoliosToViewJCombo.getSelectedItem().toString(), enterDateJTextField.getText());
      String detailedOutput = "Total Cost Basis of portfolio "
              + portfoliosToViewJCombo.getSelectedItem().toString()
              + " on " + enterDateJTextField.getText() + " is :" + totalCostBasis;
      viewPortfolioJTextArea.setText(detailedOutput);
      reset();
      repaint();
    });
  }

  private void reset() {
    enterDateJTextField.setText("");
  }
}
