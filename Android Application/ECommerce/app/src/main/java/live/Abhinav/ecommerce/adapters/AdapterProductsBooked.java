package live.Abhinav.ecommerce.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import live.Abhinav.ecommerce.app.AppController;
import live.Abhinav.ecommerce.app.R;
import live.Abhinav.ecommerce.pojo.ProductBooked;

import java.util.ArrayList;

/**
 * Created by abhin on 7/21/2015.
 */
public class AdapterProductsBooked extends RecyclerView.Adapter<AdapterProductsBooked.ViewHolderProducts> {

    private ViewQrClickListener viewQrClickListener;
    private ArrayList<ProductBooked> productArrayList = new ArrayList<ProductBooked>();
    private LayoutInflater layoutInflater;
    private AppController volleySingleton;
    private ImageLoader imageLoader;

    public AdapterProductsBooked(Context context) {
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = AppController.getInstance();
        imageLoader = volleySingleton.getImageLoader();
    }

    public void setTopProductList(ArrayList<ProductBooked> productArrayList) {
        this.productArrayList = productArrayList;
        notifyItemRangeChanged(0, productArrayList.size());
    }

    @Override
    public ViewHolderProducts onCreateViewHolder(ViewGroup parent, int i) {
        View view = layoutInflater.inflate(R.layout.row_booked_product, parent, false);
        ViewHolderProducts viewHolder = new ViewHolderProducts(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderProducts holder, int position) {
        ProductBooked currentProduct = productArrayList.get(position);
        holder.productName.setText(currentProduct.getpName());
        holder.sellerName.setText(currentProduct.getSellerName());
        holder.qrValue.setText(currentProduct.getQrValue());
        holder.productPrice.setText(currentProduct.getpPrice());
        holder.btnViewQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RelativeLayout relativeLayout = (RelativeLayout) view.getParent();
                TextView textView = (TextView) relativeLayout.findViewById(R.id.qrValue);
                String qrText = (String) textView.getText();
                Log.d("Abhinav", "qrValue" + qrText);
                if (viewQrClickListener != null) {
                    viewQrClickListener.buttonClicked(qrText);
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
        private Button btnViewQr;


        public ViewHolderProducts(View itemView) {
            super(itemView);
            productThumbnail = (ImageView) itemView.findViewById(R.id.productThumbnail);
            productName = (TextView) itemView.findViewById(R.id.productName);
            sellerName = (TextView) itemView.findViewById(R.id.sellerName);
            qrValue = (TextView) itemView.findViewById(R.id.qrValue);
            productPrice = (TextView) itemView.findViewById(R.id.productPrice);
            btnViewQr = (Button) itemView.findViewById(R.id.btnViewQr);
        }
    }

    public void setBuyItemClickListener(ViewQrClickListener viewQrClickListener) {
        this.viewQrClickListener = viewQrClickListener;
    }

    public interface ViewQrClickListener {
        public void buttonClicked(String qrValue);
    }
}