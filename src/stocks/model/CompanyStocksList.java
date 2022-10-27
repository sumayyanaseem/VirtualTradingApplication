package stocks.model;

import java.security.Timestamp;

public class CompanyStocksList {

  //TODO: annotate with NotNull later for few attributes.
  private Double purchasedPrice;

  private long quantityBought;

  private Timestamp dateSold;

  private Timestamp datePurchased;

  private long totalValue;

  private String companyName;

  /*TODO:from controller  call should be made similar to this:
  companyStockStructure c = new companyStockStructure();

  stockInfo  s = new stockInfo().setDatePurchased().setQuantityBought().
          build();
   c.list.add(s);*/

  public CompanyStocksList(){

  }

  public CompanyStocksList(Double purchasedPrice, Timestamp datePurchased, long quantityBought, long totalValue){
       this.purchasedPrice =purchasedPrice;
       this.datePurchased = datePurchased;
       this.quantityBought = quantityBought;
       this.totalValue = totalValue;

     }

     private static class companyStockListBuilder {

       Double purchasedPrice;

       long quantityBought;

       Timestamp dateSold;

       Timestamp datePurchased;

       long totalValue;

       public CompanyStocksList build(){
         return new CompanyStocksList(purchasedPrice,datePurchased,quantityBought,totalValue);
       }


       //TODO: add other setters here
       public companyStockListBuilder setPurchasedPrice(Double purchasedPrice) {
         this.purchasedPrice = purchasedPrice;
         return this;
       }

       public companyStockListBuilder setQuantityBought(long quantityBought) {
         this.quantityBought = quantityBought;
         return this;
       }

       public companyStockListBuilder setTotalValue(long totalValue) {
         this.totalValue = totalValue;
         return this;
       }

     }


}
