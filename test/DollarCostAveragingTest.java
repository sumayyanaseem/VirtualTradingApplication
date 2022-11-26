public class DollarCostAveragingTest {

 /* @Test
  public void testDollarCostStrategyStarAndEnd() {
    Map<String, Double> stockAndPercent = new HashMap<>();
    stockAndPercent.put("GOOG",25.0);
    stockAndPercent.put("META",25.0);
    stockAndPercent.put("ORCL",25.0);
    stockAndPercent.put("TWTR",25.0);
    //String pName, Map<String, Double> stockAndPercent, double investmentAmount, int investmentInterval, String dateStart, String dateEnd, double commissionFee)
    InvestmentStrategyInterface dc = new DollarCostStrategyImpl();

    try{
      dc.dollarCostAveragingStrategy("samplestartandend",stockAndPercent, 1000000.0,30,"2020-08-01","2021-03-31",5.0);
    }
    catch(Exception e)
    {
      //do nothing
    }
  }




  @Test
  public void testDollarCostStrategyStart() {
    Map<String, Double> stockAndPercent = new HashMap<>();
    stockAndPercent.put("GOOG",10.0);
    stockAndPercent.put("META",30.0);
    stockAndPercent.put("ORCL",50.0);
    stockAndPercent.put("TWTR",10.0);
    //String pName, Map<String, Double> stockAndPercent, double investmentAmount, int investmentInterval, String dateStart, String dateEnd, double commissionFee)
    InvestmentStrategyInterface dc = new DollarCostStrategyImpl();

    try{
      dc.dollarCostAveragingStrategy("samplestart",stockAndPercent, 1000000.0,90,"2020-08-01","",5.0);
    }
    catch(Exception e)
    {
      //do nothing
    }
  }

  @Test
  public void testDollarCostStrategyStartInvalidPercent() {
    Map<String, Double> stockAndPercent = new HashMap<>();
    stockAndPercent.put("GOOG",10.0);
    stockAndPercent.put("META",100.0);
    stockAndPercent.put("ORCL",50.0);
    stockAndPercent.put("TWTR",10.0);
    //String pName, Map<String, Double> stockAndPercent, double investmentAmount, int investmentInterval, String dateStart, String dateEnd, double commissionFee)
    InvestmentStrategyInterface dc = new DollarCostStrategyImpl();


      dc.dollarCostAveragingStrategy("samplestartinvalidpercent",stockAndPercent, 1000000.0,90,"2020-08-01","",5.0);

  }

  @Test
  public void testDollarCostStrategyStartFutureEndLessAmount() {
    Map<String, Double> stockAndPercent = new HashMap<>();
    stockAndPercent.put("GOOG",10.0);
    stockAndPercent.put("META",30.0);
    stockAndPercent.put("ORCL",50.0);
    stockAndPercent.put("TWTR",10.0);
    //String pName, Map<String, Double> stockAndPercent, double investmentAmount, int investmentInterval, String dateStart, String dateEnd, double commissionFee)
    InvestmentStrategyInterface dc = new DollarCostStrategyImpl();

    dc.dollarCostAveragingStrategy("samplefutureend",stockAndPercent, 100,90,"2020-08-01","2024-01-01",5.0);

  }


  @Test
  public void testDollarCostStrategyStartFutureEndMoreAmount() {
    Map<String, Double> stockAndPercent = new HashMap<>();
    stockAndPercent.put("GOOG",10.0);
    stockAndPercent.put("META",30.0);
    stockAndPercent.put("ORCL",50.0);
    stockAndPercent.put("TWTR",10.0);
    //String pName, Map<String, Double> stockAndPercent, double investmentAmount, int investmentInterval, String dateStart, String dateEnd, double commissionFee)
    InvestmentStrategyInterface dc = new DollarCostStrategyImpl();

    dc.dollarCostAveragingStrategy("samplefutureendmoreamnt",stockAndPercent, 10000,90,"2020-08-01","2024-01-01",5.0);

  }*/



}
