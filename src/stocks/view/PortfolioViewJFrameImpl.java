package stocks.view;

import java.awt.*;

import javax.swing.*;

import stocks.Features;

public class PortfolioViewJFrameImpl extends JFrame implements PortfolioViewJFrame{


  private JPanel mainPanel;
  private JButton createPortfolioButton;
  private JButton sellStockButton;
  private JButton queryPortfolioButton;
  private JButton displayCompositionButton;
  private JButton buyButton;
  private JButton totalCostBasisButton;
  private JButton totalValueButton;
  private JButton dollarCostButton;
  private JButton sellButton;
  private JButton investButton;
  private JButton createButton;
  private JButton buyStockButton;

  // in create portfolio panel
  private JLabel portfolioNameInput;
  private JTextField portfolioNameInputTxtField;

  // in buy panel
  private JLabel buyTickerInput;
  private JTextField buyTickerInputTxtField;
  private JLabel buyDateInput;
  private JTextField buyDateInputTxtField;
  private JLabel buyQtyInput;
  private JTextField buyQtyInputTxtField;
  private JLabel buyCommInput;
  private JTextField buyCommInputTxtField;
  private JComboBox viewPortfoliosToAddBuy;

  // in sell panel
  private JLabel sellTickerInput;
  private JTextField sellTickerInputTxtField;
  private JLabel sellDateInput;
  private JTextField sellDateInputTxtField;
  private JLabel sellQtyInput;
  private JTextField sellQtyInputTxtField;
  private JLabel sellCommInput;
  private JTextField sellCommInputTxtField;
  private JComboBox viewPortfoliosToAddSell;

  //in query panel
  private JComboBox viewPortfoliosToQuery;
  private JLabel viewOnDateInput;
  private JLabel viewOnDateInputTxtField;

  //write others later


  public PortfolioViewJFrameImpl() {

    super("Virtual Stock Trading Application");

    setSize(900, 600);
    setLocation(250, 250);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new FlowLayout());



    createAllButtons();
    setAllCommandsAction();
    addElementToFrame();
    //mainPanel.setAutoscrolls(true);


    //Creating the panel and adding components
    JPanel leftPanel = new JPanel(); // the panel is not visible in output
    leftPanel.setLayout(new FlowLayout());
    leftPanel.add(createPortfolioButton);
    leftPanel.add(buyStockButton);
    leftPanel.add(sellStockButton);
    leftPanel.add(queryPortfolioButton);
    leftPanel.add(dollarCostButton);
    this.getContentPane().add(BorderLayout.WEST, leftPanel);

    JPanel rightPanel = new JPanel();
    this.getContentPane().add(BorderLayout.CENTER, rightPanel);

    pack();

    setVisible(true);


  }

  private void createAllButtons() {
    //display = new JLabel("Select the action you wanna perform\n");
    createPortfolioButton = new JButton("Create Portfolio");
      createButton = new JButton("create");
    buyStockButton = new JButton("Buy");
      buyButton = new JButton("Buy");

    sellStockButton = new JButton("Sell");
      sellButton = new JButton("Sell");

    dollarCostButton = new JButton("Dollar Cost Averaging Strategy");
    queryPortfolioButton = new JButton("Query a Portfolio");
      totalCostBasisButton = new JButton("Total Cost Basis");
      totalValueButton = new JButton("Total Value");
      displayCompositionButton = new JButton("Display Composition");
    investButton = new JButton("Invest");


  }

  private void setAllCommandsAction() {
    createPortfolioButton.setActionCommand("CREATE_PORTFOLIO");
    createButton.setActionCommand("CREATE");
    buyStockButton.setActionCommand("BUY_STOCK");
    buyButton.setActionCommand("BUY");
    sellStockButton.setActionCommand("SELL_STOCK");
    sellButton.setActionCommand("SELL");
    dollarCostButton.setActionCommand("DOLLAR_COST_STRATEGY");
    queryPortfolioButton.setActionCommand("QUERY_Portfolio");
    totalCostBasisButton.setActionCommand("TOTAL_COST");
    totalValueButton.setActionCommand("TOTAL_VALUE");
    displayCompositionButton.setActionCommand("DISPLAY_COMPOSITION");
    investButton.setActionCommand("INVEST");

  }


  private void addElementToFrame() {
    mainPanel = new JPanel();
    add(mainPanel);
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.add(createPortfolioButton);
    mainPanel.add(buyStockButton);
    mainPanel.add(sellStockButton);
    mainPanel.add(dollarCostButton);
    mainPanel.add(queryPortfolioButton);
    mainPanel.add(investButton);

  }




  @Override
  public void addFeatures(Features features) {

    createPortfolioButton.addActionListener(evt -> features.displayPanelToEnterPortfolioName());
    createButton.addActionListener(evt -> features.createPortfolio(portfolioNameInputTxtField.getText(),"flexible"));
    buyStockButton.addActionListener(evt -> features.displayPanelToEnterBuyInfo());
    buyButton.addActionListener(evt -> features.addBoughtStockToPortfolio(buyTickerInputTxtField.getText(),buyDateInputTxtField.getText(),buyQtyInputTxtField.getText(),buyCommInput.getText(), viewPortfoliosToAddBuy.getSelectedItem().toString()));
    sellStockButton.addActionListener(evt -> features.displayPanelToEnterSellInfo());
    sellButton.addActionListener(evt -> features.addSoldStockToPortfolio(sellTickerInputTxtField.getText(),sellDateInputTxtField.getText(),sellQtyInputTxtField.getText(),sellCommInputTxtField.getText(), viewPortfoliosToAddSell.getSelectedItem().toString()));
    queryPortfolioButton.addActionListener(evt -> features.displayPanelToQueryPortfolioDetails());
    totalValueButton.addActionListener(evt -> features.getTotalValue(viewPortfoliosToQuery.getSelectedItem().toString(),viewOnDateInputTxtField.getText()));
    totalCostBasisButton.addActionListener(evt -> features.getCostBasis(viewPortfoliosToQuery.getSelectedItem().toString(),viewOnDateInputTxtField.getText()));


  /*  exitButton.addActionListener(evt -> features.exitProgram());
    this.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 't') {
          features.toggleColor();
        }
      }

      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_C) {
          features.makeUppercase();
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_C) {
          features.restoreLowercase();
        }
      }
    });*/
  }

  @Override
  public void showDisplayPanelToEnterPortfolioName() {
    JPanel rightPanel = new JPanel();
    this.getContentPane().add(BorderLayout.CENTER, rightPanel);

  }

  @Override
  public void showDialogue(String s) {

  }

  @Override
  public void showDisplayPanelToEnterBuyInfo() {

  }

  @Override
  public void showDisplayPanelToEnterSellInfo() {

  }


}
