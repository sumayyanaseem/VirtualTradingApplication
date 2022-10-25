package stocks.model;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class companyStockStructure {

  private String companyTickerSymbol;
  
  List<stockInfo> list = new LinkedList<>();

  companyStockStructure(String companyTickerSymbol,List<stockInfo> stockInfoList){
    this.companyTickerSymbol = companyTickerSymbol;
    this.list.addAll(stockInfoList);
  }
  
   class stockInfo(){
    Double purchasedPrice;

    long quantity;

    Timestamp dateSold;

    @NonNull
    Timestamp datePurchased;

    Long totalValue;
  }
  

}
