package live.Abhinav.ecommerce.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import helper.SQLiteHandler;
import helper.SessionManager;
import live.Abhinav.ecommerce.extras.AppConfig;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends ActionBarActivity {
    //Tag for logvcat
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnregister, btnLinkToLogin;
    private EditText inputFullName, inputEmail, inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputFullName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnregister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        //Progress Dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        //Session MAnager
        session = new SessionManager(getApplicationContext());

        //SQlite DB handler
        db = new SQLiteHandler(getApplicationContext());

        //Check if user is already logged in or not.
        if (session.isLoggedIn()) {
            //User is already loggedin. Take him to Dashboard
            Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }

        //Register button click event
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputFullName.getText().toString();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                //Check for empty fields
                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    registerUser(name, email, password);
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter the details.", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Link to Login Screen Button Click event
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Function to store user in MySQL database by sending the following post parameters in addition to tag=register
     *
     * @param name
     * @param email
     * @param password params are declared as final as they are being used inside the inner class
     */
    private void registerUser(final String name, final String email, final String password) {
        //Tag used to cancel the  request
        String tag_string_req = "req_register";

        pDialog.setMessage("Please wait while we register you.");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Regiter response: " + response);
                        hideDialog();

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");

                            if (!error) {
                                //User successfully stored in mySql

                                //Launch login activity
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                //Error occurred in registration. Get the error
                                String errorMsg = jObj.getString("error_msg");
                                Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                            }
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
                params.put("tag", "register");
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };
        // Addin grequest to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
