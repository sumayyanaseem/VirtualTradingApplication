package stocks.view;

import javax.swing.*;

import stocks.controller.Features;
/**
 * This class represents a panel for creating a portfolio.
 */
public class CreatePortfolioPanel extends JPanel implements PanelInterface {

  // in create portfolio panel
  private JLabel portfolioNameLabel;

  private JTextField portfolioNameInputTxtField;
  private JButton createPortfolioBtn;

  /**
   * constructs an CreatePortfolioPanel object.
   */
  public CreatePortfolioPanel() {
    initialiseAllComponents();
  }

  private void initialiseAllComponents() {
    portfolioNameLabel = new JLabel("Enter the name of portfolio");
    portfolioNameInputTxtField = new JTextField();
    createPortfolioBtn = new JButton("Create Portfolio");
    addCommandActionsToComponents();
    setLayout();

  }

  private void addCommandActionsToComponents() {
    createPortfolioBtn.setActionCommand("CREATE");
  }

  private void setLayout() {
    GroupLayout layout = new GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(portfolioNameLabel, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(createPortfolioBtn, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                                            .addComponent(portfolioNameInputTxtField, GroupLayout.Alignment.LEADING)))
                            .addContainerGap(278, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(portfolioNameLabel)
                            .addGap(18, 18, 18)
                            .addComponent(portfolioNameInputTxtField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(26, 26, 26)
                            .addComponent(createPortfolioBtn)
                            .addContainerGap(400, Short.MAX_VALUE))
    );
  }

  @Override
  public void delegateActions(Features f) {
    createPortfolioBtn.addActionListener(l -> {
      try {
        if (portfolioNameInputTxtField.getText().equals("")) {
          displayMessage("Portfolio name cannot be empty!!");
        } else {
          f.createPortfolio(portfolioNameInputTxtField.getText(), "flexible");
          reset();
          portfolioNameInputTxtField.repaint();
        }
      } catch (Exception e) {
        displayMessage("Portfolio already exists!");
      }
    });
  }

  private void displayMessage(String message) {
    JOptionPane.showMessageDialog(null, message, "InfoBox: " + "Error",
            JOptionPane.INFORMATION_MESSAGE);
  }

  private void reset() {
    portfolioNameInputTxtField.setText("");
  }
}
