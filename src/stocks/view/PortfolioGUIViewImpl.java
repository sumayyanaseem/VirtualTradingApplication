package stocks.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import stocks.controller.Features;


/**
 * Represents class to create GUI frame.
 */
public class PortfolioGUIViewImpl extends JFrame implements PortfolioGUIView {

  private final JPanel mainPanel;
  private final JPanel dynamicPanel;
  private final JSplitPane jSplitPane;
  private JButton createPortfolioButton;
  private JButton sellStockButton;
  private JButton queryPortfolioButton;
  private JButton dollarCostButton;
  private JButton investButton;
  private JButton buyStockButton;
  private JButton exitButton;
  private List<String> listOfPortfolios;

  /**
   * constructs an PortfolioGUIViewImpl object.
   */
  public PortfolioGUIViewImpl() {
    super("Virtual Stock Trading Application");
    listOfPortfolios = new ArrayList<>();
    mainPanel = new JPanel();
    jSplitPane = new JSplitPane();
    dynamicPanel = new JPanel();
    this.add(jSplitPane);
    createAllButtons();
    setAllCommandsAction();
    setSize(900, 500);
    setLocation(250, 250);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setMainLayout();
    jSplitPane.setLeftComponent(mainPanel);
    setDynamicLayout();
    jSplitPane.setRightComponent(dynamicPanel);
    pack();
    setVisible(true);
  }

  private void setMainLayout() {
    GroupLayout groupLayout = new GroupLayout(mainPanel);
    mainPanel.setLayout(groupLayout);
    groupLayout.setHorizontalGroup(groupLayout.createParallelGroup()
            .addComponent(createPortfolioButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(buyStockButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(sellStockButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(queryPortfolioButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(dollarCostButton, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(investButton, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(exitButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
    groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(groupLayout.createSequentialGroup()
                    .addComponent(createPortfolioButton, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(buyStockButton, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(sellStockButton, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(queryPortfolioButton, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(dollarCostButton, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(investButton, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(exitButton, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()));

  }

  private void setDynamicLayout() {
    GroupLayout groupLayout = new GroupLayout(dynamicPanel);
    dynamicPanel.setLayout(groupLayout);
    groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGap(0, 611, Short.MAX_VALUE)
    );
    groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGap(0, 510, Short.MAX_VALUE)
    );

  }

  private void createAllButtons() {
    createPortfolioButton = new JButton("Create");
    buyStockButton = new JButton("Buy");
    sellStockButton = new JButton("Sell");
    dollarCostButton = new JButton("Dollar Cost Averaging Strategy");
    queryPortfolioButton = new JButton("View");
    investButton = new JButton("Invest Fixed Amount Strategy");
    exitButton = new JButton("Exit");
  }

  private void setAllCommandsAction() {
    createPortfolioButton.setActionCommand("CREATE_PORTFOLIO");
    buyStockButton.setActionCommand("BUY_STOCK");
    sellStockButton.setActionCommand("SELL_STOCK");
    dollarCostButton.setActionCommand("DOLLAR_COST_STRATEGY");
    queryPortfolioButton.setActionCommand("QUERY_Portfolio");
    investButton.setActionCommand("INVEST");
    exitButton.setActionCommand("EXIT");
  }


  @Override
  public void addFeatures(Features feature) {

    //create portfolio
    createPortfolioButton.addActionListener(event -> {
      CreatePortfolioPanel createPortfolioPanel = new CreatePortfolioPanel();
      jSplitPane.setRightComponent(createPortfolioPanel);
      createPortfolioPanel.delegateActions(feature);
    });

    //terminate the program.
    exitButton.addActionListener(event -> feature.exitTheProgram());

    buyStockButton.addActionListener(event -> {
      BuyStocksPanel buyStocksPanel = new BuyStocksPanel(this.listOfPortfolios);
      jSplitPane.setRightComponent(buyStocksPanel);
      buyStocksPanel.delegateActions(feature);
    });

    sellStockButton.addActionListener(event -> {
      SellStocksPanel sellStocksPanel = new SellStocksPanel(this.listOfPortfolios);
      jSplitPane.setRightComponent(sellStocksPanel);
      sellStocksPanel.delegateActions(feature);
    });


    queryPortfolioButton.addActionListener(event -> {
      QueryPortfolioPanel queryPortfolioPanel = new QueryPortfolioPanel(this.listOfPortfolios);
      JScrollPane scrollPane = new JScrollPane(queryPortfolioPanel);
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

      jSplitPane.setRightComponent(scrollPane);
      queryPortfolioPanel.delegateActions(feature);
    });



      dollarCostButton.addActionListener(l -> {
        DollarCostAvgStrategyPanel vp = new DollarCostAvgStrategyPanel(this.listOfPortfolios);
        JScrollPane jScrollPane = new JScrollPane(vp);
        jSplitPane.setRightComponent(jScrollPane);
          vp.delegateActions(feature);

      });


    try {
      investButton.addActionListener(l -> {
        InvestFixedAmountStrategyPanel investFixedAmountStrategyPanel = new InvestFixedAmountStrategyPanel(this.listOfPortfolios);
        JScrollPane jScrollPane = new JScrollPane(investFixedAmountStrategyPanel);
        jSplitPane.setRightComponent(jScrollPane);
        investFixedAmountStrategyPanel.delegateActions(feature);

      });
    } catch(IllegalArgumentException e ){
      displayMessage(e.getMessage());
    }

  }


  @Override
  public void displayMessage(String message) {
    JOptionPane.showMessageDialog(null, message, null,JOptionPane.INFORMATION_MESSAGE);
  }


  @Override
  public void exitGracefully() {
    if (isDisplayable()) {
      dispose();
    }
  }

  @Override
  public void updatePortfolioList(List<String> listOfPortfolios) {
    this.listOfPortfolios = listOfPortfolios;
  }
}
