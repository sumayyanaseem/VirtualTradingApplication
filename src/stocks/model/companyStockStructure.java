package stocks.model;

import java.security.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class companyStockStructure {

  private String companyTickerSymbol;
  
  List<stockInfo> list;

  companyStockStructure(String companyTickerSymbol,List<stockInfo> stockInfoList){
    this.companyTickerSymbol = companyTickerSymbol;
    if(this.list!=null && !this.list.isEmpty()) {
      this.list.addAll(stockInfoList);
    } else {
      this.list = new LinkedList<>();
      this.list.addAll(stockInfoList);
    }
  }
  /*TODO:from controller  call should be made similar to this:
  companyStockStructure c = new companyStockStructure();

  stockInfo  s = new stockInfo().setDatePurchased().setQuantityBought().
          build();
   c.list.add(s);*/


   class stockInfo() {

     //TODO: annotate with NotNull later for few attributes.
     private Double purchasedPrice;

     private long quantityBought;

     private Timestamp dateSold;

     private Timestamp datePurchased;

     private long totalValue;

     public stockInfo(Double purchasedPrice,Timestamp datePurchased,long quantityBought,long totalValue){
       this.purchasedPrice =purchasedPrice;
       this.datePurchased = datePurchased;
       this.quantityBought = quantityBought;
       this.totalValue = totalValue;

     }


     private static class stockInfoBuilder {
       Double purchasedPrice;

       long quantityBought;

       Timestamp dateSold;

       Timestamp datePurchased;

       long totalValue;

       public stockInfo build(){
         return new stockInfo(purchasedPrice,datePurchased,quantityBought,totalValue);
       }


       //TODO: add other setters here
       public stockInfoBuilder setPurchasedPrice(Double purchasedPrice) {
         this.purchasedPrice = purchasedPrice;
         return this;
       }

       public stockInfoBuilder setQuantityBought(long quantityBought) {
         this.quantityBought = quantityBought;
         return this;
       }

       public stockInfoBuilder setTotalValue(long totalValue) {
         this.totalValue = totalValue;
         return this;
       }

     }
   }

}
