package live.Abhinav.ecommerce.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.getbase.floatingactionbutton.FloatingActionButton;
import live.Abhinav.ecommerce.adapters.AdapterTopProducts;
import live.Abhinav.ecommerce.app.*;
import live.Abhinav.ecommerce.extras.AppConfig;
import live.Abhinav.ecommerce.pojo.Product;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static live.Abhinav.ecommerce.extras.Keys.EndpointBoxOffice.*;

public class DashboardFragment extends Fragment {

    DashboardActivity mainActivity;
    FloatingActionButton actionSell;
    FloatingActionButton actionBuy;

    private AppController volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;

    private ArrayList<Product> listTopProducts = new ArrayList<Product>();

    private AdapterTopProducts adapterTopProducts;


    //Recycler view
    private RecyclerView recyclerViewTopProducts;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        volleySingleton = AppController.getInstance();
        requestQueue = volleySingleton.getRequestQueue();


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (DashboardActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewTopProducts = (RecyclerView) view.findViewById(R.id.listMovieHits);
        recyclerViewTopProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterTopProducts = new AdapterTopProducts(getActivity());
        recyclerViewTopProducts.setAdapter(adapterTopProducts);
        actionSell = (FloatingActionButton) view.findViewById(R.id.action_sell);
        actionBuy  = (FloatingActionButton) view.findViewById(R.id.action_buy);

        fab();

        sendJsonRequest();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static String getRequestUrl(int limit) {
        return AppConfig.URL_LIST_TOP_PRODUCTS;
    }

    private ArrayList<Product> parseJSONResponse(JSONArray response) {
        ArrayList<Product> listMovies = new ArrayList<Product>();
        if (response != null && response.length() > 0) {
            try {
                StringBuilder data = new StringBuilder();
                JSONArray jsonArray=response;
                for (int i=0;i<jsonArray.length();i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String pName=jsonObject.getString(KEY_NAME);
                    String pPrice=jsonObject.getString(KEY_PRICE);
                    String pUrlThumbnail= AppConfig.URL_THUBMNAIL_BASE +jsonObject.getString(KEY_THUMBNAIL);
                    String pSNo = jsonObject.getString(KEY_SNO);

                    data.append(pName + " " + pPrice + " " + pUrlThumbnail + " " + pSNo + "\n");

                    Product product = new Product();
                    product.setpName(pName);
                    product.setpPrice(pPrice);
                    product.setpUrlThumbnail(pUrlThumbnail);
                    product.setpSNo(pSNo);

                    listMovies.add(product);

                }
                Log.d("Lifecycle2", listMovies.toString());
            } catch (JSONException e) {
                Log.d("Lifecycle", "Inside JSON EXCEPTION: "+e);
            }
        }
        return listMovies;
    }

    private void sendJsonRequest() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, getRequestUrl(20),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listTopProducts = parseJSONResponse(response);
                        adapterTopProducts.setMovieList(listTopProducts);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
//                        Log.d("Lifecycle4",volleyError.getMessage());
                    }
                });
        requestQueue.add(request);
    }
    public void fab() {

        actionSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent buyIntent = new Intent(getActivity(), SellActivity.class);
                startActivity(buyIntent);
            }
        });

        actionBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buyIntent = new Intent(getActivity(), BuyActivity.class);
                startActivity(buyIntent);
            }
        });
    }
}