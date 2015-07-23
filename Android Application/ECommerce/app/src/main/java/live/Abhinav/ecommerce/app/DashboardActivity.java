package live.Abhinav.ecommerce.app;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import helper.SQLiteHandler;
import helper.SessionManager;
import live.Abhinav.ecommerce.fragments.DashboardFragment;
import live.Abhinav.ecommerce.fragments.MyTransactionsFragment;
import live.Abhinav.ecommerce.fragments.NavigationDrawerFragment;
import live.Abhinav.ecommerce.pojo.User;

import java.util.HashMap;


public class DashboardActivity extends ActionBarActivity implements Communicator {
    //Logcat TAG
    private static final String TAG = DashboardActivity.class.getSimpleName();

    //Fragments
    FragmentManager fragmentManager;
    DashboardFragment dashboardFragment;
    MyTransactionsFragment myTransactionsFragment;
    QrCodeActivity qrCodeActivity;
    //    QrCodeScannerFragment qrCodeScannerFragment;
    public static User user1;

    //Toolbar
    private Toolbar toolbar;

    private TextView txtName, txtEmail;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_nav_below);


        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);


        fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.topContainer, new DashboardFragment(), "A");
//        transaction.add(R.id.bottomContainer, new MainFragment(), "M");
        transaction.commit();


        txtName = (TextView) findViewById(R.id.name);
        txtEmail = (TextView) findViewById(R.id.email);

        //Sqlite database handler
        db = new SQLiteHandler(getApplicationContext());

        //SessionManager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        //fetch user details from SQLITE DB
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        user1 = new User(name, email);

        Log.d(TAG, user.toString());
        //Display the user details on the screen
        txtName.setText(name);
        txtEmail.setText(email);
    }

    /**
     * Function to logout the user. Will set isLoggedIn flag to fasle in sharedPreferences file.
     * Clears the user data from sqlite  users table.
     */
    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();

        //Launching the login activity
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_search) {
//            startActivity(new Intent(this, SubActivity.class));
//            return true;
//        }

        //Logout button click
        if (id == R.id.action_logout) {
            Toast.makeText(this, "Logout", Toast.LENGTH_LONG).show();
            logoutUser();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void myFunc(View view) {
        Toast.makeText(this, "Clciked the button", Toast.LENGTH_LONG).show();
    }


    @Override
    public void respond(int id) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (id) {
            case 0:
                dashboardFragment = (DashboardFragment) fragmentManager.findFragmentByTag("A");
                myTransactionsFragment = (MyTransactionsFragment) fragmentManager.findFragmentByTag("B");
//                qrCodeScannerFragment = (QrCodeScannerFragment) fragmentManager.findFragmentByTag("C");
                if (myTransactionsFragment != null) transaction.hide(myTransactionsFragment);
//                if (qrCodeScannerFragment != null) transaction.hide(qrCodeScannerFragment);

                if (dashboardFragment != null) {
                    transaction.show(dashboardFragment);
                    Log.d("Lifecycle", "shown A");

                } else {
                    dashboardFragment = new DashboardFragment();
                    transaction.add(R.id.topContainer, dashboardFragment, "A");
                    Log.d("Lifecycle", "Added A");
                }
                transaction.commit();
                break;
            case 1:
                dashboardFragment = (DashboardFragment) fragmentManager.findFragmentByTag("A");
                myTransactionsFragment = (MyTransactionsFragment) fragmentManager.findFragmentByTag("B");
//                qrCodeScannerFragment = (QrCodeScannerFragment) fragmentManager.findFragmentByTag("C");

                if (dashboardFragment != null) transaction.hide(dashboardFragment);
//                if (qrCodeScannerFragment != null) transaction.hide(qrCodeScannerFragment);

                if (myTransactionsFragment != null) {
                    transaction.show(myTransactionsFragment);
                    Log.d("Lifecycle", "shown B");
                } else {
                    myTransactionsFragment = new MyTransactionsFragment();
                    transaction.add(R.id.topContainer, myTransactionsFragment, "B");
                    Log.d("Lifecycle", "Added B");
                }
                transaction.commit();
                break;
            case 2:
//                dashboardFragment = (DashboardFragment) fragmentManager.findFragmentByTag("A");
//                myTransactionsFragment = (MyTransactionsFragment) fragmentManager.findFragmentByTag("B");


                if (dashboardFragment != null) transaction.hide(dashboardFragment);
                if (myTransactionsFragment != null) transaction.hide(myTransactionsFragment);

//                if (qrCodeScannerFragment != null) {
//                    transaction.show(qrCodeScannerFragment);
//                    Log.d("Lifecycle", "shown C");
//                } else {
//                    qrCodeScannerFragment = new QrCodeScannerFragment();
//                    transaction.add(R.id.topContainer, qrCodeScannerFragment, "C");
//                    Log.d("Lifecycle", "Added c");
//                }
                transaction.commit();
                Intent qrCode = new Intent(this, QrCodeActivity.class);
                startActivity(qrCode);
                break;
            case 3:
//                dashboardFragment = (DashboardFragment) fragmentManager.findFragmentByTag("A");
//                myTransactionsFragment = (MyTransactionsFragment) fragmentManager.findFragmentByTag("B");


                if (dashboardFragment != null) transaction.hide(dashboardFragment);
                if (myTransactionsFragment != null) transaction.hide(myTransactionsFragment);

//                if (qrCodeScannerFragment != null) {
//                    transaction.show(qrCodeScannerFragment);
//                    Log.d("Lifecycle", "shown C");
//                } else {
//                    qrCodeScannerFragment = new QrCodeScannerFragment();
//                    transaction.add(R.id.topContainer, qrCodeScannerFragment, "C");
//                    Log.d("Lifecycle", "Added c");
//                }
                transaction.commit();
                Intent bookedItems = new Intent(this, BookedItemsActivity.class);
                startActivity(bookedItems);
                break;
        }
    }
}