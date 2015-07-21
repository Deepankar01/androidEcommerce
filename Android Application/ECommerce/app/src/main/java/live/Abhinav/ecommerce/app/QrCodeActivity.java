package live.Abhinav.ecommerce.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import live.Abhinav.ecommerce.extras.AppConfig;
import net.sourceforge.zbar.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class QrCodeActivity extends Activity {

    private static final String TAG = "QrCodeActivity";
    //Camera-----------------------start
    FrameLayout cameraPreview;
    private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;
    Button scanButton;

    ImageScanner scanner;

    private boolean barcodeScanned = false;
    private boolean previewing = true;
    //Camera-----------------------end

    private AppController volleySingleton;
    private RequestQueue requestQueue;
    private ProgressDialog pDialog;
    TextView qrText;
    static {
        System.loadLibrary("iconv");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        qrText= (TextView) findViewById(R.id.qrText);
        cameraPreview = (FrameLayout) findViewById(R.id.cameraPreview);
        cameraPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (barcodeScanned) {
                    barcodeScanned = false;

                    mCamera.setPreviewCallback(previewCb);
                    mCamera.startPreview();
                    previewing = true;
                }
            }
        });
    }

    /**
     * Camera specific methods
     * -------------start-----------------
     */
    public void prepareCamera() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        autoFocusHandler = new Handler();
        mCamera = getCameraInstance();

        /* Instance barcode scanner */
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);

        mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);

        cameraPreview.addView(mPreview);

    }

    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
        }
        return c;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            previewing = false;
            mCamera.setPreviewCallback(null);
            mPreview.getHolder().removeCallback(mPreview);
            mCamera.release();
            mCamera = null;
            cameraPreview.removeView(mPreview);
        }
    }

    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (previewing)
                mCamera.autoFocus(autoFocusCB);
        }
    };

    Camera.PreviewCallback previewCb = new Camera.PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size size = parameters.getPreviewSize();

            Image barcode = new Image(size.width, size.height, "Y800");
            barcode.setData(data);

            int result = scanner.scanImage(barcode);

            if (result != 0) {
                previewing = false;
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();

                SymbolSet syms = scanner.getResults();
                for (Symbol sym : syms) {
                    // scanText.setText("barcode result " + sym.getData());
                    String qrcode = sym.getData();
                    Toast.makeText(getApplicationContext(), qrcode, Toast.LENGTH_SHORT).show();
                    qrText.setText(qrcode);
                    String[] arr = qrcode.split(" ");
                    sendVerify(arr[0],arr[1],DashboardActivity.user1.getEmail());
//                    adapterProducts.test(sym.getData());
                    barcodeScanned = true;
                }
            }
        }
    };

    // Mimic continuous auto-focusing
    Camera.AutoFocusCallback autoFocusCB = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        prepareCamera();
    }

    /**
     * Camera specific methods
     * -------------end-----------------
     */
    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }
    private void sendVerify(final String qrvalue,final String email,final String seller) {
        //Progress Dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_VERIFY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "QrActivity jjj:!" +response+"!");
                        response=response.trim();
                        if(response.equalsIgnoreCase("Done")) {
                            Toast.makeText(QrCodeActivity.this, "Success",Toast.LENGTH_LONG).show();
                            qrText.setTextColor(Color.GREEN);
                        }
                        else  {
                            Toast.makeText(QrCodeActivity.this, "Failure",Toast.LENGTH_LONG).show();
                            qrText.setTextColor(Color.RED);
                        }
                        hideDialog();
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
                params.put("QRvalue", "'"+qrvalue+"'");
                params.put("buyer", email);
                params.put("seller", seller);

                return params;
            }
        };
        // Addin grequest to request queue
        AppController.getInstance().getRequestQueue().add(strReq);
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