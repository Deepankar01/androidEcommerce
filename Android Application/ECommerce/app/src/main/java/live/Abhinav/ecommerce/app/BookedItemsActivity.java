package live.Abhinav.ecommerce.app;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import live.Abhinav.ecommerce.adapters.AdapterProductsBooked;
import live.Abhinav.ecommerce.extras.AppConfig;
import live.Abhinav.ecommerce.pojo.ProductBooked;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static live.Abhinav.ecommerce.extras.Keys.EndpointBoxOffice.*;
import static live.Abhinav.ecommerce.extras.Keys.EndpointBoxOffice.KEY_QRVALUE;
import static live.Abhinav.ecommerce.extras.Keys.EndpointBoxOffice.KEY_SELLER_NAME;


public class BookedItemsActivity extends ActionBarActivity implements AdapterProductsBooked.ViewQrClickListener {

    private AppController volleySingleton;
    private RequestQueue requestQueue;
    private ProgressDialog pDialog;
    ArrayList<String> arrayList = new ArrayList<String>();

    private static final String TAG = BookedItemsActivity.class.getSimpleName();


    RecyclerView recyclerView;
    private AdapterProductsBooked adapter;
    private ArrayList<ProductBooked> listTopProducts = new ArrayList<ProductBooked>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_items);

        volleySingleton = AppController.getInstance();
        requestQueue = volleySingleton.getRequestQueue();


        //Progress Dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        recyclerView = (RecyclerView) findViewById(R.id.listBookedProducts);
        sendProductRequest(DashboardActivity.user1.getEmail());
    }

    private void sendProductRequest(final String email) {
        Log.d(TAG, "sendSubCatRequest " + AppConfig.URL_BOOKED_ITEMS + email);
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_BOOKED_ITEMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Product response: " + response);
                        hideDialog();

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            listTopProducts = parseJSONResponse(jsonArray);

                            adapter = new AdapterProductsBooked(BookedItemsActivity.this);
                            adapter.setBuyItemClickListener(BookedItemsActivity.this);
                            adapter.setTopProductList(listTopProducts);

                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(BookedItemsActivity.this));

                            hideDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Registration error: " + volleyError.getMessage());
                Toast.makeText(getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("buyer", "'"+email+"'");
                return params;
            }
        };
        requestQueue.add(strReq);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private ArrayList<ProductBooked> parseJSONResponse(JSONArray response) {
        ArrayList<ProductBooked> listProducts = new ArrayList<ProductBooked>();
        if (response != null && response.length() > 0) {
            try {
                StringBuilder data = new StringBuilder();
                JSONArray jsonArray = response;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String pName = jsonObject.getString(KEY_NAME);
                    String pPrice = jsonObject.getString(KEY_PRICE);
                    String pUrlThumbnail = AppConfig.URL_THUBMNAIL_BASE + jsonObject.getString(KEY_THUMBNAIL);
                    String pSNo = jsonObject.getString(KEY_SNO);
                    String sellerName = jsonObject.getString(KEY_SELLER_NAME);
                    String qrValue = jsonObject.getString(KEY_QRVALUE);

                    data.append(pName + " " + pPrice + " " + pUrlThumbnail + " " + pSNo + " " + sellerName + " " + qrValue + "\n");

                    ProductBooked product = new ProductBooked();
                    product.setpName(pName);
                    product.setSellerName(sellerName);
                    product.setQrValue(qrValue);
                    product.setpPrice(pPrice);
                    product.setpUrlThumbnail(pUrlThumbnail);
                    product.setpSNo(pSNo);

                    listProducts.add(product);
                }
                Log.d("Lifecycle2", listProducts.toString());
            } catch (JSONException e) {
                Log.d("Lifecycle", "Inside JSON EXCEPTION: " + e);
            }
        }
        return listProducts;
    }

    @Override
    public void buttonClicked(String qrValue) {
        sendViewQrRequest(qrValue, DashboardActivity.user1.getEmail());
    }

    private void sendViewQrRequest(final String qrValue, final String email) {


        String QRvalue = qrValue;   //you will be having it from the previous screen just inform when you r here
        String Buyer = email;


        String url = "http://chart.apis.google.com/chart?chs=200x200&cht=qr&chld=|1&chl=" + Uri.encode(QRvalue + " " + Buyer);


        //volley Library
        ImageRequest ir = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                ImageView iv;
                iv = (ImageView) findViewById(R.id.qrImage);
                iv.setImageBitmap(response);
            }
        }, 0, 0, null, null);

        requestQueue.add(ir);
        requestQueue.start();
    }
}
