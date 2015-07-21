package live.Abhinav.ecommerce.pojo;

/**
 * Created by Abhinav on 6/19/2015.
 */
public class Transaction {
    String date;
    String otherPartyName;
    String cost;
    Boolean isBuy;
    Boolean isSell;

    public Transaction() {

    }
    public Transaction(String date, String otherPartyName, String cost, Boolean isBuy, Boolean isSell) {
        this.date = date;
        this.otherPartyName = otherPartyName;
        this.cost = cost;
        this.isBuy = isBuy;
        this.isSell = isSell;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOtherPartyName() {
        return otherPartyName;
    }

    public void setOtherPartyName(String otherPartyName) {
        this.otherPartyName = otherPartyName;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public Boolean getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(Boolean isBuy) {
        this.isBuy = isBuy;
    }

    public Boolean getIsSell() {
        return isSell;
    }

    public void setIsSell(Boolean isSell) {
        this.isSell = isSell;
    }

    @Override
    public String toString() {
        return "Date: "+date+
                " OtherPartyName: "+otherPartyName+
                " Cost: "+cost+
                " isBuy: "+isBuy+
                " isSell: "+isSell;
    }
}
