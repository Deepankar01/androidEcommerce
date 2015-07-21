package live.Abhinav.ecommerce.pojo;

/**
 * Created by abhin on 7/21/2015.
 */
public class ProductBooked {
    private String pName;
    private String pPrice;
    private String pUrlThumbnail;
    private String pSNo;
    private String sellerName;
    private String qrValue;

    public ProductBooked() {
    }

    public ProductBooked(String pName, String pPrice, String pUrlThumbnail, String pSNo, String sellerName, String qrValue) {
        this.pName = pName;
        this.pPrice = pPrice;
        this.pUrlThumbnail = pUrlThumbnail;
        this.pSNo = pSNo;
        this.sellerName = sellerName;
        this.qrValue = qrValue;
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

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getQrValue() {
        return qrValue;
    }

    public void setQrValue(String qrValue) {
        this.qrValue = qrValue;
    }
}
