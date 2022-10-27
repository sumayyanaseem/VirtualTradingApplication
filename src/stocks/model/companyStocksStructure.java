package stocks.model;

import java.security.Timestamp;

public class companyStocksStructure {

  //TODO: annotate with NotNull later for few attributes.
  private Double purchasedPrice;

  private long quantityBought;

  private Timestamp dateSold;

  private Timestamp datePurchased;

  private long totalValue;

  /*TODO:from controller  call should be made similar to this:
  companyStockStructure c = new companyStockStructure();

  stockInfo  s = new stockInfo().setDatePurchased().setQuantityBought().
          build();
   c.list.add(s);*/

  public companyStocksStructure(Double purchasedPrice, Timestamp datePurchased, long quantityBought, long totalValue){
       this.purchasedPrice =purchasedPrice;
       this.datePurchased = datePurchased;
       this.quantityBought = quantityBought;
       this.totalValue = totalValue;

     }

  public long getQuantityBought() {
    return quantityBought;
  }

  public double getTotalValue() {
    return totalValue;
  }


  private static class companyStockStructureBuilder {

       Double purchasedPrice;

       long quantityBought;

       Timestamp dateSold;

       Timestamp datePurchased;

       long totalValue;

       public companyStocksStructure build(){
         return new companyStocksStructure(purchasedPrice,datePurchased,quantityBought,totalValue);
       }


       //TODO: add other setters here
       public companyStockStructureBuilder setPurchasedPrice(Double purchasedPrice) {
         this.purchasedPrice = purchasedPrice;
         return this;
       }

       public companyStockStructureBuilder setQuantityBought(long quantityBought) {
         this.quantityBought = quantityBought;
         return this;
       }

       public companyStockStructureBuilder setTotalValue(long totalValue) {
         this.totalValue = totalValue;
         return this;
       }



     }


}
