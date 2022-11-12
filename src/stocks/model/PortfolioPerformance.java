package stocks.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import stocks.customParser.customCSVParserImpl;

public class PortfolioPerformance {

  public long daysBetween(String date1, String date2)
  {
    LocalDate startDate = LocalDate.parse("2016-08-31");
    LocalDate endDate = LocalDate.parse("2016-09-30");

    long days = ChronoUnit.DAYS.between(startDate, endDate);
    return days;
  }
  public long weeksBetween(String date1, String date2)
  {

//add validations if user is entering dates which dont exists
    LocalDate startDate = LocalDate.parse("2016-08-31");
    LocalDate endDate = LocalDate.parse("2016-09-30");

    long weeksInYear = ChronoUnit.WEEKS.between(startDate, endDate);
    return weeksInYear;
  }
  public long monthsBetween(String date1, String date2)
  {
            long monthsBetween = ChronoUnit.MONTHS.between(
            YearMonth.from(LocalDate.parse(date1)),
            YearMonth.from(LocalDate.parse(date2))
    );
    return monthsBetween;
  }


  Map<String,Double> generateMapWithMonthKeys(String date1, String date2)
  {

    Map<String,Double> m = new HashMap<>();
    int year1=Integer.valueOf(date1.substring(0,4));
    int year2=Integer.valueOf(date2.substring(0,4));
    int month1=Integer.valueOf(date1.substring(5,7));
    int month2=Integer.valueOf(date2.substring(5,7));

    if(year1==year2)
    {
      for(int i=month1;i<=month2;i++)
      {
        String key=date1.substring(0,4);
        int d=i/10;
        if(d==0)
        {
          key=key+"-0"+String.valueOf(i);
        }
        else
        {
          key=key+"-"+String.valueOf(i);
        }
        m.put(key,0.0);

      }
    }
    else
    {
      int n=year2-year1;
      String yr= date1.substring(0,4);
      for(int i=month1;i<=12;i++)
      {
        String key=date1.substring(0,4);
        int d=i/10;
        if(d==0)
        {
          key=key+"-0"+String.valueOf(i);
        }
        else
        {
          key=key+"-"+String.valueOf(i);
        }
        m.put(key,0.0);

      }
      int nextYr=0;
      while(n-1>0)
      {
        nextYr=Integer.valueOf(yr);
        nextYr=nextYr+1;

        for(int i=1;i<=12;i++)
        {
          String key=String.valueOf(nextYr);
          int d=i/10;
          if(d==0)
          {
            key=key+"-0"+String.valueOf(i);
          }
          else
          {
            key=key+"-"+String.valueOf(i);
          }
          m.put(key,0.0);

        }
        n--;

      }

      int lastYr= nextYr+1;

      for(int i=1;i<=month2;i++)
      {
        String key=String.valueOf(nextYr);
        int d=i/10;
        if(d==0)
        {
          key=key+"-0"+String.valueOf(i);
        }
        else
        {
          key=key+"-"+String.valueOf(i);
        }
        m.put(key,0.0);

      }


    }
    return m;
  }
  public Map<String,Double> displayCopy( String date1, String date2, String portfolioName) {
    //Map<String,Map<String, List<Stock>>> mp,
    Map<String,Map<String, List<Stock>>> mp = readFromFile(portfolioName);
    long months = monthsBetween(date1, date2);
    long days = daysBetween(date1, date2);
    long weeks = weeksBetween(date1, date2);
    Map<String, List<Stock>> portfolioValueMap = mp.get(portfolioName);
    Map<Integer, Integer> noOfDays = new HashMap<>();
    noOfDays.put(1, 31);
    noOfDays.put(2, 29);
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


    if (months >= 5 && months <= 30) {
      Map<String, Double> monthWiseTotalValues = new HashMap<>(); // map --> yyyy-mm = val --> for every stock in portfolio keep adding val to its corresponding yyyy-mm

      // suppose date range is feb 2022 to april 2022. our below map will have 2022-02-->0.0, 2022-03-->0.0, 2022-04-->0.0
      Map<String, Double> mapOfMonths = generateMapWithMonthKeys(date1, date2);

      // for every month in given range by user, get the last date of month and for that date iterate
      // over all the stocks in portfolio and get the netquantity of that stock as of the last date
      // of the month and multiply the new quantity with stock price of that month
      String monthEndDate;
      for (Map.Entry<String, Double> entry : mapOfMonths.entrySet()) {
        String dt = entry.getKey();
        int mnth = Integer.valueOf(dt.substring(5, 7));
        int daysInMonth = noOfDays.get(mnth);
        monthEndDate = dt + "-" + String.valueOf(daysInMonth);


        double totalValueOfPortfolioMonthEnd = 0.0;
        for (Map.Entry<String, List<Stock>> companyInfo : portfolioValueMap.entrySet()) {

          double netQty = getQuantityOnThisDateForGivenCompanyName(monthEndDate, companyInfo.getKey());
          double stkValueMonthEnd = getStockPriceAsOfCertainMonthEnd(companyInfo.getKey(), dt, netQty);
          totalValueOfPortfolioMonthEnd = totalValueOfPortfolioMonthEnd + stkValueMonthEnd;
        }

        monthWiseTotalValues.put(dt, totalValueOfPortfolioMonthEnd);

      }


    }

    else if(weeks>=5 && weeks<=30) {
      Map<String, Double> weekWiseTotalValues = new HashMap<>(); // map --> yyyy-mm = val --> for every stock in portfolio keep adding val to its corresponding yyyy-mm

      // suppose date range is feb 2022 to april 2022. our below map will have 2022-02-->0.0, 2022-03-->0.0, 2022-04-->0.0
      String data = fetchOutputStringFromURLByInterval(companyInfo.getKey(),"WEEKLY");
      Map<String, Double> weekWiseTotalValues = generateMapWithDayKeys(date1, date2, data);
      for(Map.Entry<String, Double> allWeeksInRange : weekWiseTotalValues.entrySet())
      {
        double netQty = getQuantityOnThisDateForGivenCompanyName(allWeeksInRange.getKey(), companyInfo.getKey());
        double stkValueMonthEnd = getStockPriceAsOfCertainWeek(companyInfo.getKey(), netQty, allWeeksInRange.getKey(),data);

        dayWiseTotalValues.put(allDaysInRange.getKey(),dayWiseTotalValues.get(allDaysInRange.getKey())+stkValueMonthEnd)
      }

    }


    else if (days >= 5 && days <= 30) {
      Map<String, Double> dayWiseTotalValues = new HashMap<>();

      for (Map.Entry<String, List<Stock>> companyInfo : portfolioValueMap.entrySet()) {
        String data = fetchOutputStringFromURLByInterval(companyInfo.getKey());

        if(dayWiseTotalValues.size()==0)
        {
          dayWiseTotalValues = generateMapWithDayKeys(date1, date2, data);
        }
        for(Map.Entry<String, Double> allDaysInRange : dayWiseTotalValues.entrySet())
        {
          double netQty = getQuantityOnThisDateForGivenCompanyName(allDaysInRange.getKey(), companyInfo.getKey());
          double stkValueMonthEnd = getStockPriceAsOfCertainDate(companyInfo.getKey(), netQty, allDaysInRange.getKey());

          dayWiseTotalValues.put(allDaysInRange.getKey(),dayWiseTotalValues.get(allDaysInRange.getKey())+stkValueMonthEnd)
        }

      }

    }
  }

  private Map<String, Double> generateMapWithDayKeys(String date1, String date2, String data)
  {
    Map<String,Double> result=new HashMap<>();
    String lines[] = data.split(System.lineSeparator());
    Date givenDateObj1;
    Date givenDateObj2;
    Date availableDateObj;
    List<List<String>> records = new ArrayList<>();
    for(int i=1;i<lines.length;i++)
    {
      String[] values = lines[i].split(",");
      records.add(Arrays.asList(values));
    }
    try {
      givenDateObj1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
              .parse(date1);
      givenDateObj2 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
              .parse(date2);
      int i;
      for (i = 0; i < records.size(); i++) {
        List<String> infoByDate = new ArrayList<>(records.get(i));
        String availableDate = infoByDate.get(0);
        availableDateObj = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse(availableDate);
        if (availableDateObj.compareTo(givenDateObj2) <= 0) {
          result.put(availableDate,0.0);
          break;
        }

      }
      for(int j=i+1;j<records.size();j++)
      {
        List<String> infoByDate = new ArrayList<>(records.get(j));
        String availableDate = infoByDate.get(0);
        result.put(availableDate,0.0);
        availableDateObj = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse(availableDate);
        if (availableDateObj.compareTo(givenDateObj1) <= 0) {
          break;
        }
      }

    } catch (ParseException e) {
      throw new IllegalArgumentException("file not found in our records "
              + "for given company " + companyTickerSymbol);
    }

  }

  /*public long yearsBetween(String date1, String date2)
  {
    //add validations date 1 < date 2
    long yearsBetween = ChronoUnit.YEARS.between(
            YearMonth.from(LocalDate.parse("2014-08-31")),
            YearMonth.from(LocalDate.parse("2016-11-30"))
    );
    return yearsBetween;
  }

  public long quartersBetween(String date1, String date2)
  {
    //add validations date 1 < date 2
    long quarters =
            IsoFields.QUARTER_YEARS.between(LocalDate.parse("2014-08-31"), LocalDate.parse("2014-12-30"));
    return quarters;
  }*/

}



