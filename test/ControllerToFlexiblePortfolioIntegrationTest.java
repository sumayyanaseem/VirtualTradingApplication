import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

import stocks.controller.PortfolioController;
import stocks.controller.PortfolioControllerImpl;
import stocks.view.PortfolioView;
import stocks.view.PortfolioViewImpl;

import static org.junit.Assert.assertTrue;

/**
 * test class for integration tests between controller and flexible portfolio.
 */
public class ControllerToFlexiblePortfolioIntegrationTest {

  private InputStream in;

  private PortfolioView view;

  private PortfolioController portfolioController;

  private OutputStream bytes;


  @Before
  public void setUp() {
    in = new ByteArrayInputStream("1\n".getBytes());
    bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioViewImpl(out);
    portfolioController = new PortfolioControllerImpl(in, view);
  }

  @Test
  public void testPortfolioPerformanceDaily() {
    String date1 = "2022-11-01";
    String date2 = "2022-11-14";
    String expected = "Enter 1: To view composition  2: To get TotalValue of portfolio "
            + " 3: To get Total CostBasis  4:To display performance of Portfolio\n";
    in = new ByteArrayInputStream(("2\ntestFlexible\n4\n2022-11-01\n2022-11-14\n").getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains("*"));
    List<String> dates = getDatesHelper(date1, date2, 1);
    for (int i = 0; i < dates.size(); i++) {
      assertTrue(bytes.toString().contains(dates.get(i)));
    }
  }

  @Test
  public void testPortfolioPerformanceWeekly() {
    String date1 = "2022-09-01";
    String date2 = "2022-11-14";
    String expected = "Enter 1: To view composition  2: To get TotalValue of portfolio "
            + " 3: To get Total CostBasis  4:To display performance of Portfolio\n";
    in = new ByteArrayInputStream(("2\ntestFlexible\n4\n2022-09-01\n2022-11-14\n").getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains("*"));
    List<String> dates = getDatesHelper(date1, date2, 7);
    for (int i = 0; i < dates.size(); i++) {
      assertTrue(bytes.toString().contains(dates.get(i)));
    }
  }

  @Test
  public void testPortfolioPerformanceMonthly() {
    String date1 = "2022-01-01";
    String date2 = "2022-11-14";
    String expected = "Enter 1: To view composition  2: To get TotalValue of portfolio "
            + " 3: To get Total CostBasis  4:To display performance of Portfolio\n";
    in = new ByteArrayInputStream(("2\ntestFlexible\n4\n2022-01-01\n2022-11-14\n").getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains("*"));
    String monthStart = date1.substring(0, 8) + "01";
    List<String> dates = getDatesHelper(monthStart, date2, 31);
    for (int i = 0; i < dates.size(); i++) {
      assertTrue(bytes.toString().contains(dates.get(i).substring(0, 7)));
    }

  }

  @Test
  public void testPortfolioPerformanceYearly() {
    String date1 = "2009-01-01";
    String date2 = "2022-11-14";
    String expected = "Enter 1: To view composition  2: To get TotalValue of portfolio "
            + " 3: To get Total CostBasis  4:To display performance of Portfolio\n";
    in = new ByteArrayInputStream(("2\ntestFlexible\n4\n2009-01-01\n2022-11-14\n").getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains("*"));
    String yearStart = date1.substring(0, 4) + "-12-31";
    List<String> dates = getDatesHelper(yearStart, date2, 365);
    for (int i = 0; i < dates.size(); i++) {
      assertTrue(bytes.toString().contains(dates.get(i).substring(0, 7)));
    }
  }

  @Test
  public void testPortfolioPerformanceQuarterly() {
    String date1 = "2020-01-01";
    String date2 = "2022-11-14";
    String expected = "Enter 1: To view composition  2: To get TotalValue of portfolio "
            + " 3: To get Total CostBasis  4:To display performance of Portfolio\n";
    in = new ByteArrayInputStream(("2\ntestFlexible\n4\n2020-01-01\n2022-11-14\n").getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains("*"));
    String monthStart = date1.substring(0, 8) + "01";
    List<String> dates = getDatesHelper(monthStart, date2, 90);
    for (int i = 0; i < dates.size(); i++) {
      assertTrue(bytes.toString().contains(dates.get(i).substring(0, 7)));
    }
  }

  @Test
  public void testPersistAPortfolio() throws IOException {

    String expected = "Enter 1: To create flexible portfolio  2: To create inflexible  portfolio\n";
    in = new ByteArrayInputStream(("1\n1\nff\n1\ngoog\n10"
            + "\n2020-10-01\n10\n2\n1\n2\nff\n1\n2022-11-14\n").getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    assertTrue(bytes.toString().contains(expected));
    File f = new File("userPortfolios/" + "ff" + "_output.json");
    assertTrue(f.exists());
    BufferedReader br = new BufferedReader(new FileReader(f));
    String st;
    while ((st = br.readLine()) != null) {
      System.out.println(st);
      assertTrue(st.contains("CompanyName"));
      assertTrue(st.contains("Actions"));
      assertTrue(st.contains("goog"));
      assertTrue(st.contains("type"));
    }
    f.deleteOnExit();

  }

  @Test
  public void testUpdatePortfolio() {
    String expected = "Enter 1: To buy stocks in this portfolio  "
            + "2: To sell stocks in this portfolio ";
    String error = "Commission should be always a positive number.\n";
    String error2 = "Invalid Commission provided";
    in = new ByteArrayInputStream(("4\ntestFlexible\n1\ngoog\n10\n2020-10-"
            + "01\na\n-2\n0\n10\n2\n1\n2\ntestFlexible\n1\n2022-11-14\n").getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains(error));
    assertTrue(bytes.toString().contains(error2));
    assertTrue(bytes.toString().contains("goog"));
  }

  @Test
  public void testSellStocks() {
    String expected = "Enter 1: To buy stocks in this portfolio  "
            + "2: To sell stocks in this portfolio ";
    in = new ByteArrayInputStream(("4\ntestFlexible\n2\ngoog\n10\n2020-10-01"
            + "\n10\n2\n1\n2\ntestFlexible\n1\n2022-11-14\n").getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains("goog"));
  }

  @Test
  public void testUpdatePortfolioUsingFilePath() {
    String expected = "Enter 1: To view details of this portfolio.  "
            + "2: To exit and continue further trading. 3: To update loaded portfolio.";
    in = new ByteArrayInputStream(("3\nuserPortfolios/testFlexible_output.json\n3\n1"
            + "\ngoog\n10\n2020-10-01\na\n10\n2\n1\n2\ntestFlexible\n1\n2022-11-14\n").getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains("goog"));

  }

  @Test
  public void testCostBasis() {
    String expected = "Enter 1: To view composition  2: To get TotalValue of portfolio  "
            + "3: To get Total CostBasis  4:To display performance of Portfolio\n";
    in = new ByteArrayInputStream(("2\ntestFlexible\n3\n2020-10-01\n").getBytes());
    portfolioController = new PortfolioControllerImpl(in, view);
    try {
      portfolioController.start();
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    assertTrue(bytes.toString().contains(expected));
    assertTrue(bytes.toString().contains("2020-10-01"));
    assertTrue(bytes.toString().contains("Total cost basis from "
            + "the portfolioName testFlexible on 2020-10-01 is"));

  }

  private List<String> getDatesHelper(String date1, String date2, int interval) {
    List<String> dates = new ArrayList<>();
    try {
      Date start = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
              .parse(date1);

      Date end = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
              .parse(date2);

      Date current = start;

      while (current.before(end)) {
        String todayDateStr = new SimpleDateFormat("yyyy-MM-dd").format(
                new Date(String.valueOf(current)));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        calendar.add(Calendar.DATE, interval);
        current = calendar.getTime();
        dates.add(todayDateStr);
      }
    } catch (Exception e) {
      //do nothing
    }
    return dates;
  }


}
