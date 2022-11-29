package stocks.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import stocks.customapi.APICustomClass;
import stocks.customapi.APICustomInterface;

/**
 * This class represents Performance of the portfolio over time..
 */
public class PortfolioPerformance {
  private final APICustomInterface apiCustom;
  Map<String, List<Stock>> portfolioMap;
  Map<String, Double> mapOfValues;


  private boolean isLeapYear(String y) {
    int year = Integer.parseInt(y);
    return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
  }

  private long yearsBetween(String date1, String date2) {
    //add validations date 1 < date 2
    long yearsBetween = ChronoUnit.YEARS.between(
            YearMonth.from(LocalDate.parse(date1)),
            YearMonth.from(LocalDate.parse(date2))
    );
    return yearsBetween;
  }

  private long quartersBetween(String date1, String date2) {
    //add validations date 1 < date 2
    long quarters =
            IsoFields.QUARTER_YEARS.between(LocalDate.parse(date1), LocalDate.parse(date2));
    return quarters;
  }


  PortfolioPerformance(Map<String, List<Stock>> portfolioMap) {
    apiCustom = new APICustomClass();
    this.portfolioMap = portfolioMap;
    mapOfValues = new LinkedHashMap<>();
  }

  private long daysBetween(String date1, String date2) {
    LocalDate startDate = LocalDate.parse(date1);
    LocalDate endDate = LocalDate.parse(date2);

    long days = ChronoUnit.DAYS.between(startDate, endDate);
    return days;
  }

  private long weeksBetween(String date1, String date2) {

    LocalDate startDate = LocalDate.parse(date1);
    LocalDate endDate = LocalDate.parse(date2);

    long weeksInYear = ChronoUnit.WEEKS.between(startDate, endDate);
    return weeksInYear;
  }

  private long monthsBetween(String date1, String date2) {
    long monthsBetween = ChronoUnit.MONTHS.between(
            YearMonth.from(LocalDate.parse(date1)),
            YearMonth.from(LocalDate.parse(date2))
    );
    return monthsBetween;
  }


  /**
   * constructs a map with values of portfolio for each date in the range.
   *
   * @param date1         the start date of the range.
   * @param date2         the end date of the range.
   * @param portfolioName the portfolio for which values are needed.
   */
  public Map<String, Double> displayCopy(String date1,
                                         String date2,
                                         String portfolioName)
          throws ParseException {


    long months = monthsBetween(date1, date2);
    long days = daysBetween(date1, date2);
    long weeks = weeksBetween(date1, date2);
    long years = yearsBetween(date1, date2);
    long quaters = quartersBetween(date1, date2);

    Map<Integer, Integer> noOfDays = new HashMap<>();
    noOfDays.put(1, 31);
    noOfDays.put(2, 28);
    noOfDays.put(3, 31);
    noOfDays.put(4, 30);
    noOfDays.put(5, 31);
    noOfDays.put(6, 30);
    noOfDays.put(7, 31);
    noOfDays.put(8, 31);
    noOfDays.put(9, 30);
    noOfDays.put(10, 31);
    noOfDays.put(11, 30);
    noOfDays.put(12, 31);

    if (days <= 30) {
      generateHistogramDates(date1, date2, 1);
    } else if (weeks >= 5 && weeks <= 30) {
      generateHistogramDates(date1, date2, 7);
    } else if (months >= 5 && months <= 30) {
      String monthStart = date1.substring(0, 8) + "01";
      monthHelper(monthStart, date2, 31, noOfDays, mapOfValues);
    } else if (quaters >= 5 && quaters <= 30) {
      String monthStart = date1.substring(0, 8) + "01";
      monthHelper(monthStart, date2, 90, noOfDays, mapOfValues);
    } else if (years >= 5 && years <= 30) {
      String yearStart = date1.substring(0, 4) + "-12-31";
      monthHelper(yearStart, date2, 365, noOfDays, mapOfValues);
    }
    return mapOfValues;
  }

  private void monthHelper(String date1, String date2,
                           int interval, Map<Integer, Integer> noOfDays,
                           Map<String, Double> mapOfValues) {
    List<String> monthsbtw = getDates(date1, date2, interval);

    for (int i = 0; i < monthsbtw.size(); i++) {
      String mon = monthsbtw.get(i).substring(5, 7);
      int daysInThisMonth = noOfDays.get(Integer.parseInt(mon));
      int xx = Integer.parseInt(mon);
      int d = xx / 10;
      String key = "";
      if (d == 0) {
        key = key + "0" + String.valueOf(xx);
      } else {
        key = key + String.valueOf(xx);
      }
      if (key.equals("02") && isLeapYear(monthsbtw.get(i).substring(0, 4))) {
        daysInThisMonth = 29;
      }
      String endOfMonth = monthsbtw.get(i).substring(0, 5)
              + key + "-" + String.valueOf(daysInThisMonth);

      double res = getTotalValueOfPortfolioOnCertainDate(endOfMonth);

      mapOfValues.put(endOfMonth, res);
    }
  }


  private double getQuantityOnThisDateForGivenCompanyName(
          String date, String companyName) throws ParseException {

    List<Stock> list = portfolioMap.get(companyName);
    Date givenDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            .parse(date);
    double quantity = 0;
    for (Stock stock : list) {
      String datePresent = stock.getDateOfAction();
      Date todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
              .parse(datePresent);
      if (todayDate.compareTo(givenDate) <= 0) {
        if (stock.getAction().equals("buy")) {
          quantity += stock.getQty();
        } else if (stock.getAction().equals("sell")) {
          quantity -= stock.getQty();
        }
      }
    }
    return quantity;
  }


  private void generateHistogramDates(String date1, String date2, int interval) {
    List<String> listOfDates = getDates(date1, date2, interval);
    if (listOfDates != null && !listOfDates.isEmpty()) {
      for (int i = 0; i < listOfDates.size(); i++) {
        double res = getTotalValueOfPortfolioOnCertainDate(listOfDates.get(i));
        mapOfValues.put(listOfDates.get(i), res);
      }
    }
  }


  private double getTotalValueOfPortfolioOnCertainDate(String date) {
    double totalValue = 0.0;
    if (portfolioMap != null) {
      for (Map.Entry<String, List<Stock>> entry : portfolioMap.entrySet()) {
        String stkName = entry.getKey();
        try {
          double netQty = getQuantityOnThisDateForGivenCompanyName(date, stkName);
          totalValue = totalValue + apiCustom.getStockPriceAsOfCertainDate(stkName, netQty, date);
        } catch (ParseException e) {
          //do nothing
        }
      }
    }
    String value = String.format("%.2f", totalValue);
    return Double.parseDouble(value);

  }

  private List<String> getDates(String startObj, String endObj, int interval) {
    List<String> dates = new ArrayList<>();
    try {
      Date start = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
              .parse(startObj);

      Date end = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
              .parse(endObj);

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







