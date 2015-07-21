package live.Abhinav.ecommerce.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import live.Abhinav.ecommerce.adapters.AdapterTransactions;
import live.Abhinav.ecommerce.app.AppController;
import live.Abhinav.ecommerce.app.DashboardActivity;
import live.Abhinav.ecommerce.app.R;
import live.Abhinav.ecommerce.extras.AppConfig;
import live.Abhinav.ecommerce.pojo.Transaction;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static live.Abhinav.ecommerce.extras.Keys.EndpointBoxOffice.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyTransactionsFragment extends Fragment {

    private AppController volleySingleton;
    private RequestQueue requestQueue;

    //Array list of POJO
    private ArrayList<Transaction> listTransactions = new ArrayList<Transaction>();

    //Get a reference to the adapter to be passed to setAdapter() method
    private AdapterTransactions adapterTransactions;
    //Recycler view
    private RecyclerView recyclerViewTransactions;

    public MyTransactionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        volleySingleton = AppController.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_transactions, container, false);
        recyclerViewTransactions = (RecyclerView) view.findViewById(R.id.listTransactions);
        recyclerViewTransactions.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterTransactions = new AdapterTransactions(getActivity());
        recyclerViewTransactions.setAdapter(adapterTransactions);
        sendJsonRequest();

        return view;
    }

    public static String getRequestUrl(int limit) {
        return AppConfig.URL_LIST_TRANSACTIONS;
    }

    private ArrayList<Transaction> parseJSONResponse(JSONArray response) {
        ArrayList<Transaction> listTransactions = new ArrayList<Transaction>();
        if (response != null && response.length() > 0) {
            try {
                StringBuilder data = new StringBuilder();
                JSONArray jsonArray = response;
                Log.d("Lifecycle1", String.valueOf(response.length()));
                for (int i = 0; i < jsonArray.length(); i++) {
                    Log.d("Lifecycle", "Inside loop");

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String tDate = jsonObject.getString("date");
                    String tCost = jsonObject.getString("cost");
                    String tOtherPartyName = jsonObject.getString("otherParty");
                    Boolean isBuy = jsonObject.getBoolean("isBuy");
                    Boolean isSell = jsonObject.getBoolean("isSell");

                    data.append(tDate + " " + tCost + " " + tOtherPartyName + " " + (isBuy ? "Buy" : "Sell") + "\n");

                    Transaction product = new Transaction();
                    product.setDate(tDate);
                    product.setCost(tCost);
                    product.setOtherPartyName(tOtherPartyName);
                    product.setIsBuy(isBuy);
                    product.setIsSell(isSell);

                    listTransactions.add(product);
                    Log.d("Lifecycle", "Inside loop");
                }
                Log.d("Lifecycle2", listTransactions.toString());
            } catch (JSONException e) {
                Log.d("Lifecycle", "Inside JSON EXCEPTION: " + e);
            }
        }
        return listTransactions;
    }

    private void sendJsonRequest() {
        StringRequest request = new StringRequest(Request.Method.GET,
                getRequestUrl(20)+ DashboardActivity.user1.getEmail(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Lifecycle3", response.toString());
                        try {
                            JSONArray array = new JSONArray(response);
                            listTransactions = parseJSONResponse(array);
                            adapterTransactions.setListTransactions(listTransactions);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("Lifecycle4", volleyError + " ");
                    }
                });
        requestQueue.add(request);
    }
}