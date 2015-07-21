package live.Abhinav.ecommerce.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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


public class LoginActivity extends Activity {

    //Logcat tag
    private static final String TAG = LoginActivity.class.getSimpleName();

    private Button btnLogin, btnLinkToRegister;
    private EditText inputEmail, inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        //Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        //Session Manager
        session = new SessionManager(getApplicationContext());

        //SQlite DB handler
        db=new SQLiteHandler(getApplicationContext());

// Use only if not asking for login
// session.setLogin(false);

        //Check if user is already logged in or not
        if (session.isLoggedIn()) {
            //User is already logged in
            //Take him to main activity
            Toast.makeText(this, "Already",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }

        //LoginButton click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                //Check for empty data in the form
                if (email.trim().length() > 0 && password.trim().length() > 0) {
                    //call checkLogin to login user if enter credentials were correct
                    checkLogin(email, password);

                } else {
                    //Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(), "Please enter the credentials.", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Link to register screen Button click event
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Function to verify login details in MYSQL DATABASE
     * supplied from login activity
     * @param email entered emailId
     * @param password entered password
     */
    private void checkLogin(final String email,final String password) {
        //Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging In.");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Login Response: " + response);

                        hideDialog();

                        try {
                            JSONObject jObj=new JSONObject(response);
                            boolean error=jObj.getBoolean("error");

                            //Check for error node in json
                            if(!error) {
                                //User successfully loggedin
                                //Create login session
                                session.setLogin(true);

                                //Now add to SQLite database
                                String uid=jObj.getString("uid");
                                JSONObject user=jObj.getJSONObject("user");
                                String name=user.getString("name");
                                String email=user.getString("email");
                                String created_at=user.getString("created_at");
                                //Insert row in users table
                                db.addUser(name, email, uid, created_at);

                                //Launch DashBoard Activity
                                Intent intent=new Intent(LoginActivity.this,DashboardActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                //error occurred in LOGIN. Get the error message
                                String errorMsg  = jObj.getString("error_msg");
                                Log.d(TAG, errorMsg + " ...");
                                Toast.makeText(getApplicationContext(),errorMsg,Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG,"onErrorResponse Login Error: "+volleyError.getMessage());
                Toast.makeText(getApplicationContext(), volleyError.getMessage()+"...",Toast.LENGTH_LONG).show();
                hideDialog();
            }})
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Posting error to login URL
                Map<String,String> params = new HashMap<String, String>();
                params.put("tag","login");
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };

        //Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq,tag_string_req);
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