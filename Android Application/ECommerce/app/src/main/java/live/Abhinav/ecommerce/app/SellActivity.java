package live.Abhinav.ecommerce.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import helper.SQLiteHandler;
import live.Abhinav.ecommerce.extras.AppConfig;
import live.Abhinav.ecommerce.pojo.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static live.Abhinav.ecommerce.extras.Keys.EndpointBoxOffice.*;

public class SellActivity extends Activity {

    private static final String TAG = "SellActivity";

    Bitmap bitmap;

    private ProgressDialog dialog = null;

    private static final int REQUEST_CODE = 100;
    ProgressDialog dialog1 = null;

    ImageView thumbnailPic;
    TextView tv_productName;
    TextView tv_productPrice;
    TextView tv_productDescription;
    int serverResponseCode = 0;

    /**
     * Spinners
     */
    Spinner categorySpinner;
    Spinner subCategorySpinner;
    private AppController volleySingleton;
    private RequestQueue requestQueue;
    private ProgressDialog pDialog;
    ArrayList<String> arrayList = new ArrayList<String>();


    String name;
    String email;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sell);

        volleySingleton = AppController.getInstance();
        requestQueue = volleySingleton.getRequestQueue();

        tv_productName = (TextView) findViewById(R.id.name);
        tv_productPrice = (TextView) findViewById(R.id.price);
        tv_productDescription = (TextView) findViewById(R.id.description);

        //Sqlite database handler
//        db = new SQLiteHandler(getApplicationContext());
//        HashMap<String, String> user = db.getUserDetails();

        User user = DashboardActivity.user1;
        name = user.getName();
        email = user.getEmail();



        //Progress Dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        sendCategoryRequest(AppConfig.URL_CATEGORIES);
        //Find all the spinners
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        subCategorySpinner = (Spinner) findViewById(R.id.subcategorySpinner);

        //Set listener for categorySpinner  the 3 spinners
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = (String) parent.getSelectedItem();
                sendSubCatRequest(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        Log.d("Lifecycle", "onCreate()");
    }

    public void callImageFragment(View view) {

        clickedButton();
    }

    // When user clicks button, calls AsyncTask.
    // Before attempting to fetch the URL, makes sure that there is a network connection.
    public void myClickHandler(View view) {
        // Gets the URL from the UI's text field.
        String stringUrl = AppConfig.URL_IP ;
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            dialog = ProgressDialog.show(SellActivity.this, "", "Uploading file...", true);
            new DownloadWebpageTask().execute(stringUrl);
        } else {
//            resultTV.setText("No network connection available.");
        }
    }

    public void respond(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    // Uses AsyncTask to create a task away from the main UI thread. This task takes a
    // URL string and uses it to create an HttpUrlConnection. Once the connection
    // has been established, the AsyncTask downloads the contents of the webpage as
    // an InputStream. Finally, the InputStream is converted into a string, which is
    // displayed in the UI by the AsyncTask's onPostExecute method.
    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
//                return downloadUrl(urls[0], "" + getFilesDir().getPath() + getPackageName() + "/files/image.jpg");
                Log.d("Lifecycle", urls[0]);
                return downloadUrl(urls[0], "/data/data/" + getPackageName() + "/files/image.jpg");
            } catch (IOException e) {
                Log.d("Lifecycle", "Unable to retrieve web page. URL may be invalid.");
                Log.d("Lifecycle", urls[0]);
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
//            resultTV.setText(result);
            try {
                showResults(result, bitmap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            dialog.dismiss();

        }
    }

    // Given a URL, establishes an HttpUrlConnection and retrieves
// the web page content as a InputStream, which it returns as
// a string.
    private String downloadUrl(String myurl, String sourceFileUri) throws IOException {
        InputStream is = null;
        HttpURLConnection conn;
        DataOutputStream dos;

        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);
        if (!sourceFile.isFile()) {
            Log.e("uploadFile", "Source File Does not exist");
            return null;
        }

        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            FileInputStream fileInputStream = new FileInputStream(sourceFile);

            URL url = new URL(myurl);

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(0);
            conn.setConnectTimeout(0);
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("uploaded_file", sourceFileUri);
            conn.setRequestProperty("product_name", tv_productName.getText().toString());
            conn.setRequestProperty("product_price", tv_productPrice.getText().toString());
            conn.setRequestProperty("product_category", categorySpinner.getSelectedItem().toString());
            conn.setRequestProperty("product_subcategory", subCategorySpinner.getSelectedItem().toString());
            conn.setRequestProperty("product_description", tv_productDescription.getText().toString());
            conn.setRequestProperty("product_seller", email);

            //Send request
            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + sourceFileUri + "\"" + lineEnd);
            dos.writeBytes(lineEnd);
            bytesAvailable = fileInputStream.available(); // create a buffer of  maximum size
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }
            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
////////////////////////////////////////////////////

            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("Lifecycle", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);

            fileInputStream.close();
            dos.flush();
            dos.close();

            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream, int len) throws IOException {
        Reader reader;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    /**
     * Camera Related Methods
     */
    public void clickedButton() {
        Log.d("Lifecycle", "clickedButton() called");
        thumbnailPic = (ImageView) findViewById(R.id.thumbnailPic);
        if (thumbnailPic != null)
            callCamera();
//        else
//            Toast.makeText(getActivity(), "Null", Toast.LENGTH_LONG).show();
    }

    //work of camera begins
    public void callCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == SellActivity.RESULT_OK) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                thumbnailPic.setImageBitmap(bp);
                respond(bp);
                // Image captured and saved to fileUri specified in the Intent
                data.setData(Uri.parse("/storage/sdcard/saved_images/"));
                SaveImage(bp);


                Log.d("Lifecycle", "Data saved to " + data.getData());
            } else if (resultCode == SellActivity.RESULT_CANCELED) {
                Toast.makeText(this, "User cancelled the operation", Toast.LENGTH_LONG).show();
            } else {
                // Image capture failed, advise user
                Toast.makeText(this, "Image Capture Failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void SaveImage(Bitmap finalBitmap) {
        File file = getFilesDir();
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = openFileOutput("image.jpg", SellActivity.MODE_PRIVATE);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Log.d("Lifecycle", "Image saved to " + file.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//Camera works ends


    //For result
    public void showResults(String result, Bitmap bp) throws JSONException {

        JSONObject resultJson = new JSONObject(result);
        String nameR = resultJson.getString("product_name");
        String priceR = resultJson.getString("product_price");
        String categoryR = resultJson.getString("product_category");
        String subcategoryR = resultJson.getString("product_subcategory");
        String descriptionR = resultJson.getString("product_description");

        Log.d("Lifecycle", "result=" + nameR + ":" + priceR + ":"+categoryR+":"+":"+subcategoryR + descriptionR);
    }
    /**
     * Volley Methods
     */
    public void sendCategoryRequest(String url) {
        pDialog.setMessage("Fetching Categories...");
        showDialog();
        JsonArrayRequest catRequest = new JsonArrayRequest(Request.Method.GET,
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Log.d(TAG, jsonArray + "");
                        //Parse categories list returned
                        arrayList = parseJSON(jsonArray, KEY_CATEGORY_NAME, KEY_CATEGORY_ID);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SellActivity.this, android.R.layout.simple_spinner_item, arrayList);
                        categorySpinner.setAdapter(adapter);
                        hideDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                if (volleyError.getMessage() != null)
                Log.d(TAG, volleyError + " ");
                hideDialog();
            }
        });
        requestQueue.add(catRequest);
    }
    public ArrayList<String> parseJSON(JSONArray response, String KEY_FIELD_NAME, String KEY_FIELD_ID) {
        ArrayList<String> categoryList = new ArrayList<String>();
        if (response != null && response.length() > 0) {
            try {
                StringBuilder data = new StringBuilder();
                JSONArray jsonArray = response;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String categoryName = jsonObject.getString(KEY_FIELD_NAME);
                    String categoryId = jsonObject.getString(KEY_FIELD_ID);

                    categoryList.add(categoryName);
                    data.append(categoryId + " " + categoryName);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return categoryList;
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    public void sendSubCatRequest(final String selectedCategory) {
        Log.d(TAG, "sendSubCatRequest " + AppConfig.URL_SUBCATEGORIES + selectedCategory);
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_SUBCATEGORIES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "response: " + response);
                        hideDialog();

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            arrayList = parseJSON(jsonArray, KEY_SUBCATEGORY_NAME, KEY_SUBCATEGORY_ID);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(SellActivity.this, android.R.layout.simple_spinner_item, arrayList);
                            subCategorySpinner.setAdapter(adapter);
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
                params.put("category", selectedCategory);
                return params;
            }
        };
        requestQueue.add(strReq);
    }
}