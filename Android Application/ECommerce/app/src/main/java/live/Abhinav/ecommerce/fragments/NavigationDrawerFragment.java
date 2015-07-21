package live.Abhinav.ecommerce.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import live.Abhinav.ecommerce.app.Communicator;
import live.Abhinav.ecommerce.pojo.Information;
import live.Abhinav.ecommerce.adapters.NavbarListAdapter;
import live.Abhinav.ecommerce.app.R;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawerFragment extends Fragment implements NavbarListAdapter.ClickListener {
    //Communicator
    Communicator comm;

    FragmentManager fragmentManager;
    public static final String TAG = "Lifecycle1";
    public static final String NAV_PREF_FILE_NAME = "navpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    private RecyclerView recyclerView;


    private NavbarListAdapter adapter;
    //
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));

        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);

        adapter = new NavbarListAdapter(getActivity(), getData());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        comm = (Communicator) getActivity();
    }

    public static List<Information> getData() {
        List<Information> data = new ArrayList<Information>();
        int[] icons = {R.drawable.ic_home_drawer,
                R.drawable.ic_transactions_drawer,
                R.drawable.ic_transactions_drawer,
                R.drawable.ic_transactions_drawer};
        String[] titles = {"Home", "Transactions","QR Code Scanner","Booked Items"};
//        for (int i = 0; i < titles.length && i < icons.length; i++) {
        for (int i = 0; i < titles.length && i< icons.length; i++) {
            Information current = new Information();
            current.setIconId( icons[i % icons.length]);
            current.setTitle(titles[i % titles.length]);
            data.add(current);
        }
        return data;
    }

    /**
     * Custom method to be called from MainActivity to setup the drawer
     * passing the toolbar and the layout file
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }
                //Draw the actionbar again
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                //Draw the actionbar again
                getActivity().invalidateOptionsMenu();

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset < 0.6) {
                    toolbar.setAlpha(1 - slideOffset);
                }
            }
        };
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }
        //Now set the listener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAV_PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAV_PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }

    @Override
    public void itemClicked(View view, int position) {

        switch (position) {
            case 0:
                Toast.makeText(getActivity(), "Case 0", Toast.LENGTH_SHORT).show();
                comm.respond(0);
                mDrawerLayout.closeDrawers();
                break;
            case 1:
                Toast.makeText(getActivity(), "Case 1", Toast.LENGTH_SHORT).show();
                comm.respond(1);
                mDrawerLayout.closeDrawers();
                break;
            case 2:
                Toast.makeText(getActivity(), "Case 2", Toast.LENGTH_SHORT).show();
                comm.respond(2);
                mDrawerLayout.closeDrawers();
                break;
            case 3:
                Toast.makeText(getActivity(), "See Booked Items", Toast.LENGTH_SHORT).show();
                comm.respond(3);
                mDrawerLayout.closeDrawers();
                break;
            default:
                Toast.makeText(getActivity(), "default", Toast.LENGTH_SHORT).show();
                break;
        }
//        startActivity(new Intent(getActivity(), SubActivity.class));
    }
}