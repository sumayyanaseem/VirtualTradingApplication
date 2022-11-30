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
import stocks.customparser.CustomParser;
import stocks.customparser.JsonParserImplementation;


public class DollarCostStrategyImpl implements StrategyInterface {
  private final APICustomInterface apiCustom;
  private final int investmentInterval;
  private final String dateStart;
  private final String dateEnd;

  private final IFlexible flexible;

  public DollarCostStrategyImpl(int investmentInterval,String dateStart,String dateEnd,IFlexible flexible){
    this.investmentInterval = investmentInterval;
    this.dateStart = dateStart;
    this.dateEnd= dateEnd;
    apiCustom = new APICustomClass();
    this.flexible = flexible;
  }


  @Override
  public void applyStrategyOnPortfolio(String portfolioName, Map<String, Double> stockAndPercent, double investmentAmount,  double commissionFee)
          throws IllegalArgumentException {

    if (portfolioName == null || portfolioName.equals("")) {
      throw new IllegalArgumentException("The portfolio name cannot be null or empty.");
    }

    if(stockAndPercent==null || stockAndPercent.isEmpty()){
      throw new IllegalArgumentException("Stocks and weights map provided is empty.");
    }

    if (dateStart == null || dateStart.equals("")) {
      throw new IllegalArgumentException("Start date can't be empty or null");
    }

    if (dateEnd == null) {
      throw new IllegalArgumentException("End date is null");
    }

    if (investmentInterval <=0) {
      throw new IllegalArgumentException("Can't apply strategy with specified interval."
              + " Investment interval should be atleast one day");
    }

    validateDateFormat(dateStart);
    if(!dateEnd.equals(""))
    validateDateFormat(dateEnd);


    String format = "yyyy-MM-dd";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH);
    LocalDate dateStartObj = LocalDate.parse(dateStart, formatter);

    LocalDate dateEndObj;
    if (dateEnd.equals("")) {
      dateEndObj = LocalDate.now();
    } else {
      dateEndObj = LocalDate.parse(dateEnd, formatter);
    }
    if (dateStartObj.compareTo(dateEndObj) > 0) {
      throw new IllegalArgumentException("start date can't be after end date. Strategy can't be applied.");
    }


    validateMapAndValuesForDollarCostStrategy(stockAndPercent, investmentAmount,
              investmentInterval, commissionFee);

    if (!dateEnd.equals("")) {
      if (dateEndObj.compareTo(LocalDate.now()) > 0) {
        dateEndObj = LocalDate.now();
      }
    }


    LocalDate dateToInvest = dateStartObj;
    Calendar cal = GregorianCalendar.from(dateToInvest.atStartOfDay(
            ZoneId.systemDefault()));

    LocalDate actualDateOfInvestment = dateStartObj;

    while (dateToInvest.compareTo(dateEndObj) <= 0) {

      if (isHoliday(cal)) {
        dateToInvest = dateToInvest.plusDays(1);
        cal = GregorianCalendar.from(dateToInvest
                .atStartOfDay(ZoneId.systemDefault()));
        continue;
      }

      String dt = dateToInvest.format(formatter);
      invest(portfolioName, stockAndPercent, investmentAmount, commissionFee, dt);

      dateToInvest = actualDateOfInvestment.plusDays(investmentInterval);
      actualDateOfInvestment = dateToInvest;
      cal = GregorianCalendar.from(dateToInvest
              .atStartOfDay(ZoneId.systemDefault()));
    }



   /* if (actualEnddate == null) {

      portfolio.addStrategy(new DollarCostStrategy(startdate, investmentInterval,
              commissionFee, amount, stockNameAndWeight,
              actualInvestmentDate));
    } else {

      portfolio.addStrategy(new DollarCostStrategy(startdate, actualEnddate, investmentInterval,
              commissionFee, amount, stockNameAndWeight,
              actualInvestmentDate));
    }*/


  }


  private void validateMapAndValuesForDollarCostStrategy(
                                                         Map<String, Double> stockAndPercent,
                                                         double investmentAmount, int investmentInterval,
                                                         double commissionFee)
          throws IllegalArgumentException {


  /*  if (dateEnd == null) {
      throw new IllegalArgumentException("end date is null");
    }
    if (dateStart == null || dateStart.equals("")) {
      throw new IllegalArgumentException("start date can't be empty or null");
    }

    validateDateFormat(dateStart);
    validateDateFormat(dateEnd);


       String format = "yyyy-MM-dd";
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH);
       LocalDate dateStartObj = LocalDate.parse(dateStart, formatter);

       LocalDate dateEndObj;
       if (dateEnd.equals("")) {
         dateEndObj = LocalDate.now();
       } else {
         dateEndObj = LocalDate.parse(dateEnd, formatter);
       }
       if (dateStartObj.compareTo(dateEndObj) > 0) {
         throw new IllegalArgumentException("start date can't be after end date. Strategy can't be applied.");
       }

*/
    if (stockAndPercent != null && stockAndPercent.size()<1) {
      throw new IllegalArgumentException("Portfolio should consist atleast one stock to apply "
              + "dollar cost strategy");
    }


    for (String stockName : stockAndPercent.keySet()) {
      flexible.validateIfCompanyExists(stockName.trim().toUpperCase());
    }


    if (investmentAmount <= 0.00) {
      throw new IllegalArgumentException("Amount cant be less than 0");
    }

    if (commissionFee < 0.00) {
      throw new IllegalArgumentException("Commission fee cant be less than 0");
    }

    validateMapEntriesAndPercent(stockAndPercent);

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
        throw new IllegalArgumentException("Specified percent value is negative. Please enter positive persentages only");
      }
      totalSum += percentValue;

    }

    if ( totalSum  < 100 ||  totalSum > 100.0 ) {
      throw new IllegalArgumentException("total percentage is not exactly 100");
    }

  }


  public void invest(String portfolioName, Map<String, Double> stockAndPercent, double amount,
                       double commissionFee, String date) throws IllegalArgumentException {

   /* PortfolioInterface portfolio = getPortfolio(portfolioName);

    if (portfolio == null) {
      throw new IllegalArgumentException("The portfolio does not exist.");
    }*/

   // isAmountCommissionPercentageValid(amount, commissionFee, stockNameAndWeight);

    StringBuilder investmentSummary = new StringBuilder();
    for (Map.Entry<String, Double> entry : stockAndPercent.entrySet()) {

      String companyName = entry.getKey();
      double percent = entry.getValue();
      double investment = ( amount * percent ) / 100.00;

      if(investment==0.0)
        continue;

        try {
          executeBuy(companyName, portfolioName, investment, date, commissionFee);

          //investmentSummary.append("Successfully invested in " + tickerSymbol + "\n");
        } catch (IllegalArgumentException e) {
          //catch this exception where you are calling dollar cost averaging.
          //throw new IllegalArgumentException(e.getMessage());
        }


    }
    //return investmentSummary.deleteCharAt(investmentSummary.length() - 1).toString();
  }



  public void executeBuy(String tickerSymbol, String portfolioName, double amount,
                               String date, double commissionFee)
          throws IllegalArgumentException {

    //validateDataofBuyShare(tickerSymbol, portfolioName, amount, date, commissionFee);



    double pricePerStock = apiCustom.getStockPriceAsOfCertainDate(tickerSymbol.trim().toUpperCase(), 1,date);

    /*if (sharePrice == 0.00) {

      throw new IllegalArgumentException("stock data for the ticker " + tickerSymbol + ""
              + " doesn't exist for the provided date");
    }*/

    double sharesCount = (double) ( amount / pricePerStock );

    if (sharesCount == 0.0) {
      throw new IllegalArgumentException("shares can't be bought. You don't have enough funds");
    }

    String qtyStr = String.format("%.2f",sharesCount);
   // System.out.println(qtyStr);
    flexible.updatePortfolio(tickerSymbol.trim().toUpperCase(), qtyStr ,date, portfolioName,"buy", String.valueOf(commissionFee));

  }



  private boolean isHoliday(Calendar calInstance) {


    if (calInstance.get(Calendar.DAY_OF_WEEK) == 7 )
      return true;

    if(calInstance.get(Calendar.DAY_OF_WEEK) == 1) {
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
            && calInstance.get(Calendar.DAY_OF_MONTH) > ( 31 - 7 )) {
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

    return ( calInstance.get(Calendar.DAY_OF_WEEK) == 5 && calInstance.get(Calendar.MONTH) == 10
            && calInstance.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 4);

  }

}