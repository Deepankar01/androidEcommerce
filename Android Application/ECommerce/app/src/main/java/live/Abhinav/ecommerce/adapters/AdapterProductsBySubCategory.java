package live.Abhinav.ecommerce.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import live.Abhinav.ecommerce.app.AppController;
import live.Abhinav.ecommerce.app.R;
import live.Abhinav.ecommerce.pojo.ProductBySubCategory;

import java.util.ArrayList;

/**
 * Created by abhin on 7/21/2015.
 */
public class AdapterProductsBySubCategory extends RecyclerView.Adapter<AdapterProductsBySubCategory.ViewHolderProducts> {

    private BuyItemClickListener buyItemClickListener;
    private ArrayList<ProductBySubCategory> productArrayList = new ArrayList<ProductBySubCategory>();
    private LayoutInflater layoutInflater;
    private AppController volleySingleton;
    private ImageLoader imageLoader;

    public AdapterProductsBySubCategory(Context context) {
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = AppController.getInstance();
        imageLoader = volleySingleton.getImageLoader();
    }

    public void setTopProductList(ArrayList<ProductBySubCategory> productArrayList) {
        this.productArrayList = productArrayList;
        notifyItemRangeChanged(0, productArrayList.size());
    }

    @Override
    public ViewHolderProducts onCreateViewHolder(ViewGroup parent, int i) {
        View view = layoutInflater.inflate(R.layout.row_product_by_category, parent, false);
        ViewHolderProducts viewHolder = new ViewHolderProducts(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderProducts holder, int position) {
        ProductBySubCategory currentProduct = productArrayList.get(position);
        holder.productName.setText(currentProduct.getpName());
        holder.sellerName.setText(currentProduct.getSellerName());
        holder.qrValue.setText(currentProduct.getQrValue());
        holder.productPrice.setText(currentProduct.getpPrice());
        holder.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RelativeLayout relativeLayout = (RelativeLayout) view.getParent();
                TextView textView = (TextView) relativeLayout.findViewById(R.id.qrValue);
                String qrText = (String) textView.getText();
                Log.d("Abhinav", "qrValue" +qrText);
                if (buyItemClickListener != null) {
                    buyItemClickListener.buttonClicked(qrText);
                }
            }
        });
        String urlThumbnail = currentProduct.getpUrlThumbnail();
        if (urlThumbnail != null) {
            imageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean b) {
                    holder.productThumbnail.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    class ViewHolderProducts extends RecyclerView.ViewHolder {

        private TextView sellerName;
        private TextView qrValue;
        private ImageView productThumbnail;
        private TextView productName;
        private TextView productPrice;
        private Button btnBuy;


        public ViewHolderProducts(View itemView) {
            super(itemView);
            productThumbnail = (ImageView) itemView.findViewById(R.id.productThumbnail);
            productName = (TextView) itemView.findViewById(R.id.productName);
            sellerName = (TextView) itemView.findViewById(R.id.sellerName);
            qrValue = (TextView) itemView.findViewById(R.id.qrValue);
            productPrice = (TextView) itemView.findViewById(R.id.productPrice);
            btnBuy = (Button) itemView.findViewById(R.id.btnBuy);

        }
    }
    public void setBuyItemClickListener(BuyItemClickListener buyItemClickListener) {
        this.buyItemClickListener = buyItemClickListener;
    }

    public interface BuyItemClickListener {
        public void buttonClicked(String qrValue);
    }
}
