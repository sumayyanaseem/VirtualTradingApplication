package stocks.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;

import stocks.customapi.APICustomClass;
import stocks.customapi.APICustomInterface;

/**
 * This class represents implementation for fixed cost strategy.
 */
public class FixedCostStrategyImpl implements StrategyInterface {
  private final APICustomInterface apiCustom;
  private final String dateStart;

  private final IFlexible flexible;


  /**
   * constructs an object for FixedCostStrategyImpl class with given start date and
   * flexible portfolio object.
   *
   * @param dateStart start date of investment.
   * @param flexible  portfolio interface object for flexible portfolio.
   */

  public FixedCostStrategyImpl(String dateStart, IFlexible flexible) {
    this.dateStart = dateStart;
    apiCustom = new APICustomClass();
    this.flexible = flexible;
  }


  @Override
  public void applyStrategyOnPortfolio(String portfolioName,
                                       Map<String, Double> stockAndPercent,
<<<<<<< HEAD
                                       double investmentAmount,
                                       double commissionFee)
=======
                                       double investmentAmount, double commissionFee)
>>>>>>> 5b65f16c2e6c26b6532cd9d87dc8631cad64b8e9
          throws IllegalArgumentException {

    if (portfolioName == null || portfolioName.equals("")) {
      throw new IllegalArgumentException("The portfolio name cannot be null or empty.");
    }

    if (dateStart == null || dateStart.equals("")) {
      throw new IllegalArgumentException("Date can't be empty or null");
    }

    if (stockAndPercent == null || stockAndPercent.isEmpty()) {
      throw new IllegalArgumentException("Stocks and weights map provided is empty.");
    }


    if (investmentAmount <= 0) {
      throw new IllegalArgumentException("amount cant be less than 0");
    }

    if (commissionFee <= 0) {
      throw new IllegalArgumentException("commission fee cant be less than 0");
    }

    validateDateFormat(dateStart);


    String format = "yyyy-MM-dd";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH);
    LocalDate dateStartObj = LocalDate.parse(dateStart, formatter);

    validateMapAndValuesForDollarCostStrategy(stockAndPercent);


    LocalDate dateToInvest = dateStartObj;
    Calendar cal = GregorianCalendar.from(dateToInvest.atStartOfDay(
            ZoneId.systemDefault()));


    while (isHoliday(cal)) {
      dateToInvest = dateToInvest.plusDays(1);
      cal = GregorianCalendar.from(dateToInvest
              .atStartOfDay(ZoneId.systemDefault()));

    }


    String dt = dateToInvest.format(formatter);
    invest(portfolioName, stockAndPercent, investmentAmount, commissionFee, dt);


  }


  private void validateMapAndValuesForDollarCostStrategy(
          Map<String, Double> stockAndPercent)
          throws IllegalArgumentException {

    validateMapEntriesAndPercent(stockAndPercent);

    for (String stockName : stockAndPercent.keySet()) {
      flexible.validateIfCompanyExists(stockName.trim().toUpperCase());
    }

  }

  private void validateDateFormat(String date) {
    String format = "yyyy-MM-dd";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH);
    try {
      LocalDate ld = LocalDate.parse(date, formatter);
      String result = ld.format(formatter);
      if (!result.equals(date)) {
        throw new IllegalArgumentException("Invalid dateFormat provided."
                + "Please provide date in YYYY-MM-DD format only.");
      }

    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Invalid dateFormat provided."
              + "Please provide date in YYYY-MM-DD format only.");
    }
  }


  private void validateMapEntriesAndPercent(Map<String, Double> stockAndPercent)
          throws IllegalArgumentException {


    double totalSum = 0.0;

    for (Map.Entry<String, Double> stockEntry : stockAndPercent.entrySet()) {

      String stockName = stockEntry.getKey();

      if (stockName == null || stockName.equals("")) {
        throw new IllegalArgumentException("Stock name can't be empty or null");
      }

      double percentValue = stockEntry.getValue();

      if (percentValue < 0) {
        throw new IllegalArgumentException("Specified percent value is negative. "
                + "Please enter positive percentages only");
      }
      totalSum += percentValue;

    }

    if (totalSum < 100 || totalSum > 100.0) {
      throw new IllegalArgumentException("total percentage is not exactly 100");
    }

  }


  private void invest(String portfolioName, Map<String, Double> stockAndPercent, double amount,
                      double commissionFee, String date) throws IllegalArgumentException {


    for (Map.Entry<String, Double> entry : stockAndPercent.entrySet()) {

      String companyName = entry.getKey();
      double percent = entry.getValue();
      double investment = (amount * percent) / 100.00;

      if (investment == 0.0) {
        continue;
      }

      try {
        executeBuy(companyName, portfolioName, investment, date, commissionFee);


      } catch (IllegalArgumentException e) {
        //catch this exception where you are calling dollar cost averaging.
        //throw new IllegalArgumentException(e.getMessage());
      }


    }

  }


  private void executeBuy(String tickerSymbol, String portfolioName, double amount,
                          String date, double commissionFee)
          throws IllegalArgumentException {


    double pricePerStock = apiCustom.getStockPriceAsOfCertainDate(
            tickerSymbol.trim().toUpperCase(), 1, date);

    double sharesCount = amount / pricePerStock;

    if (sharesCount == 0.0) {
      throw new IllegalArgumentException("Shares can't be bought. You don't have enough funds");
    }

    String qtyStr = String.format("%.2f", sharesCount);
    // System.out.println(qtyStr);
    flexible.updatePortfolio(tickerSymbol.trim().toUpperCase(), qtyStr,
            date, portfolioName,
            "buy", String.valueOf(commissionFee));


  }


  private boolean isHoliday(Calendar calInstance) {


    if (calInstance.get(Calendar.DAY_OF_WEEK) == 7) {
      return true;
    }

    if (calInstance.get(Calendar.DAY_OF_WEEK) == 1) {
      return true;
    }


    if (calInstance.get(Calendar.DAY_OF_MONTH) == 25 && calInstance.get(Calendar.MONTH) == 11
    ) {
      return true;
    }


    if (calInstance.get(Calendar.DAY_OF_MONTH) == 1 && calInstance.get(Calendar.MONTH) == 0
    ) {
      return true;
    }


    if (calInstance.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 3 && calInstance.get(Calendar.MONTH) == 0
            && calInstance.get(Calendar.DAY_OF_WEEK) == 2) {
      return true;
    }

    if (calInstance.get(Calendar.MONTH) == 1
            && calInstance.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 3
            && calInstance.get(Calendar.DAY_OF_WEEK) == 2) {
      return true;
    }

    if (calInstance.get(Calendar.DAY_OF_WEEK) == 2 && calInstance.get(Calendar.MONTH) == 4
            && calInstance.get(Calendar.DAY_OF_MONTH) > (31 - 7)) {
      return true;
    }

    if (calInstance.get(Calendar.DAY_OF_MONTH) == 4 && calInstance.get(Calendar.MONTH) == 6) {
      return true;
    }


    if (calInstance.get(Calendar.DAY_OF_WEEK) == 2 && calInstance.get(Calendar.MONTH) == 8
            && calInstance.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 1
    ) {
      return true;
    }

    if (calInstance.get(Calendar.MONTH) == Calendar.NOVEMBER
            && calInstance.get(Calendar.DAY_OF_MONTH) == 11) {
      return true;
    }

    return (calInstance.get(Calendar.DAY_OF_WEEK) == 5 && calInstance.get(Calendar.MONTH) == 10
            && calInstance.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 4);

  }

}