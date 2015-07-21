package live.Abhinav.ecommerce.pojo;

import java.util.Date;

/**
 * Created by Abhinav on 6/17/2015.
 */
public class Product {

    private String pName;
    private String pPrice;
    private String pUrlThumbnail;
    private String pSNo;


    public Product() {
    }


    public Product(String pName, String pPrice, String pUrlThumbnail, String pSNo) {
        this.pName = pName;
        this.pPrice = pPrice;
        this.pUrlThumbnail = pUrlThumbnail;
        this.pSNo = pSNo;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpPrice() {
        return pPrice;
    }

    public void setpPrice(String pPrice) {
        this.pPrice = pPrice;
    }

    public String getpUrlThumbnail() {
        return pUrlThumbnail;
    }

    public void setpUrlThumbnail(String pUrlThumbnail) {
        this.pUrlThumbnail = pUrlThumbnail;
    }

    public String getpSNo() {
        return pSNo;
    }

    public void setpSNo(String pSNo) {
        this.pSNo = pSNo;
    }

    @Override
    public String toString() {
        return "Sno:" + pSNo +
                " Name " + pName +
                " Price " + pPrice +
                " UrlThumbnail " + pUrlThumbnail;
    }
}