package com.tanzoft.habarihub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tanzoft.habarihub.adapters.HabariHubDrawerAdapter;
import com.tanzoft.habarihub.fragments.About;
import com.tanzoft.habarihub.fragments.BlogsFragment;
import com.tanzoft.habarihub.fragments.FragmentView;
import com.tanzoft.habarihub.fragments.HabariHubFragmentHandler;
import com.tanzoft.habarihub.fragments.NewsPapersFragment;
import com.tanzoft.habarihub.fragments.SubscriptionDialog;
import com.tanzoft.habarihub.mediaplayer.MediaActivity;

import org.apache.http.protocol.HTTP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HabariHubMainActivity extends ActionBarActivity {


    final private String OPTION = "option"; //a key for each options from the array of options
    String mTitle = " ";//title for the action bar
    int mPosition = -1;//an integer to check the row position of list views;position starts at zero
    HabariHubFragmentHandler fragment;
    private String[] mDrawerOptions; //an array of string that will hold the names of the options for the drawer
    private DrawerLayout mDrawerLayout;//a java object for the whole drawer layout view
    private ListView mDrawerList; //a list view object for the list view options
    private LinearLayout mDrawer;//A java object for the left drawer
    private ActionBarDrawerToggle mDrawerToggle; //toggle button for drawer toggle
    private List<HashMap<String, String>> mList;//a list of hash maps to hold mapped images and left drawer options
    private HabariHubDrawerAdapter mAdapter; //a custom adapter to adapt the list views on the left drawer

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		/*setting the whole drawerLayout */

        //Getting an array of options from resources
        mDrawerOptions = getResources().getStringArray(R.array.drawerMenu);
        //Title of the Activity
        mTitle = (String) getTitle();
        //Getting a reference to DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Getting a reference to the drawer listview
        mDrawerList = (ListView) findViewById(R.id.drawer_list);
        //Getting a reference to the sidebar drawer
        mDrawer = (LinearLayout) findViewById(R.id.left_drawer);


        //Each row in the list stores an option which is a HashMap text
        mList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 5; ++i) {
            HashMap<String, String> hm = new HashMap<String, String>();//HashMap object
            hm.put(OPTION, mDrawerOptions[i]);//putting the respective option to HashMap
            mList.add(hm);//adding HashMap into array List
        }

        //Keys used in HashMap
        String[] from = {OPTION};

        //Ids of views in listviewLayout
        int[] to = {R.id.drawerOption};

        //Instatiating an adapter to store each items
        //R.layout.drawer_layout defines the layout of each item
        mAdapter = new HabariHubDrawerAdapter(this, mList, R.layout.drawer_layout, from, to);//Mapping each HashMap to a ListView

        //Setting the adapter to the listView
        mDrawerList.setAdapter(mAdapter);

        //Creating a ToggleButton for NavigationDrawer
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {


            //called when drawer is opened
            public void onDrawerClosed(View view) {
                highlightSelectedOption();
                supportInvalidateOptionsMenu();
            }

            //Called when a drawer is opened
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle("Habari Hub");
                supportInvalidateOptionsMenu();
            }
        };

        //Setting event listener for the drawer
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //ItemClick event handler for the drawer items
        mDrawerList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
                                    long arg3) {

                //Closing the Drawer

                mDrawerLayout.postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        mDrawerLayout.closeDrawer(mDrawer);

                    }
                }, 50);

                switch (pos) {

                    case 0:

                        fragment = new BlogsFragment();
                        runFragment(fragment);
                        break;

                    case 1:

                        fragment = new NewsPapersFragment();
                        runFragment(fragment);
                        break;

                    case 2:

                        Intent radio = new Intent(HabariHubMainActivity.this, MediaActivity.class);
                        startActivity(radio);
                        break;

                    case 3:
                        Intent tabbed = new Intent(HabariHubMainActivity.this, MainActivityTabbed.class);
                        startActivity(tabbed);
                        break;

                    case 4:
                        fragment = new About();
                        runFragment(fragment);
                        showFragment(pos);
                        break;
                }


            }

        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fragment = new BlogsFragment();
        runFragment(fragment);

    }


    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }


        switch (item.getItemId()) {

            case R.id.rate:

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id="
                                    + "com.tanzoft.habarihub")
                    ));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id="
                                    + "com.tanzoft.habarihub")
                    ));
                }

                return true;

            case R.id.settings:
                Intent settings = new Intent(this, com.tanzoft.habarihub.Settings.SettingsActivity.class);
                startActivity(settings);
                return true;

            case R.id.report:
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
            /*
			 * The intent does not have a URI, so declare the "text/plain" MIME
			 * type, emailIntent.setType("text/plain");
			 */
                emailIntent.setType(HTTP.PLAIN_TEXT_TYPE);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{
                        "mlabwa1@gmail.com", "cmeo226@yahoo.com",
                        "pkinasha@gmail.com", "igotti47@gmail.com"}); // recipients
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Habari Hub Error");
                startActivity(Intent.createChooser(emailIntent,
                        "Choose Email Client"));
                return true;

            case R.id.share:
                Intent share = new Intent("android.intent.action.SEND");
                share.setType("text/plain");
                share.putExtra("android.intent.extra.TEXT",
                        "https://play.google.com/store/apps/details?id=com.tanzoft.habarihub");
                share.putExtra("android.intent.extra.SUBJECT",
                        "Try Habari Hub Android app!");
                startActivity(Intent.createChooser(share, "Share Habari Hub!!"));
                return true;

            case R.id.add:

                SubscriptionDialog dialog = new SubscriptionDialog();

                dialog.show(getSupportFragmentManager(), "add dialog");


        }

        return super.onOptionsItemSelected(item);
    }


    //Highlight the selected option 0 to 4
    protected void highlightSelectedOption() {
        int selectedItem = mDrawerList.getCheckedItemPosition();
        if (selectedItem > 5) {

            mDrawerList.setItemChecked(mPosition, true);


        } else {

            mPosition = selectedItem;
        }

        if (mPosition != -1) {
            getSupportActionBar().setTitle(mDrawerOptions[mPosition]);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }


    public void showFragment(int position) {

        //Currently selected country
        mTitle = mDrawerOptions[position];

        // Creating a fragment object
        HabariHubFragmentHandler hFragment = new FragmentView();

        // Creating a Bundle object
        Bundle data = new Bundle();

        // Setting the index of the currently selected item of mDrawerList
        data.putInt("position", position);

        // Setting the position to the fragment

        hFragment.setArguments(data);

        // Getting reference to the FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Creating a fragment transaction
        FragmentTransaction ft = fragmentManager.beginTransaction();

        // Adding a fragment to the fragment transaction
        // ft.replace(R.id.content_frame, oFragment);
        ft.replace(R.id.content_frame, hFragment);


        // Committing the transaction
        ft.commit();
    }


    private void runFragment(HabariHubFragmentHandler fragment) {

        // Creating a Bundle object
        Bundle data = new Bundle();

        // Setting the position to the fragment

        fragment.setArguments(data);

        // Getting reference to the FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Creating a fragment transaction
        FragmentTransaction ft = fragmentManager.beginTransaction();

        // Adding a fragment to the fragment transaction
        ft.replace(R.id.content_frame, fragment);

        // Committing the transaction
        ft.commit();
    }
}
