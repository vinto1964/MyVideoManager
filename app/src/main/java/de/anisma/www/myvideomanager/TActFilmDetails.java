package de.anisma.www.myvideomanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;



public class TActFilmDetails extends ActionBarActivity implements ActionBar.TabListener {

    AppGlobal myApp;

    int iPos = -1;
    long lFilmID = -1;
    List<DTFilmItem> filmlist = new ArrayList<DTFilmItem>();

    String sEditable;
    ImageView ivActorFoto;

    EditText edActRole, edActRoleOrder, edActFirstName, edActLastName, edPlot;
    EditText edComment;

    RatingBar rbRating;

    ImageButton ibAddActor, ibDeleteActor;

    ListView lvActors;
    Spinner spFunction, spGenre;
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_tact_film_details);
        setContentView(R.layout.activity_tact_film_details);

        myApp = (AppGlobal) getApplication();

        Intent intent = getIntent();
        sEditable = intent.getStringExtra("Edit");

        // Fragment "Allgemein"


        // Fragment "Schauspieler/in"
        ivActorFoto     = (ImageView) findViewById(R.id.ivActorFoto);
        edActRole       = (EditText) findViewById(R.id.edActRole);
        edActRoleOrder  = (EditText) findViewById(R.id.edActRoleOrder);
        edActFirstName  = (EditText) findViewById(R.id.edActFirstName);
        edActLastName   = (EditText) findViewById(R.id.edActLastName);
        ibAddActor      = (ImageButton) findViewById(R.id.ibFilmAdd);
        ibDeleteActor   = (ImageButton) findViewById(R.id.ibDeleteActor);
        lvActors        = (ListView) findViewById(R.id.lvActors);
        spFunction      = (Spinner) findViewById(R.id.spFunction);

        // Fragment "Plot"
        edPlot = (EditText) findViewById(R.id.edPlot);

        // Fragment "Rating"
        rbRating    = (RatingBar) findViewById(R.id.rbRating);
        spGenre     = (Spinner) findViewById(R.id.spGenre);
        edComment   = (EditText) findViewById(R.id.edComment);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        //mSectionsPagerAdapter = new MyPageAdapter();
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tact_film_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            //**************************************************************************************
            switch (position){
                case 0:
                    return FgmInfos.newInstance(0);

                case 1:
/*                    SecondFragment fragment1 = new SecondFragment();
                    return fragment1;*/
                    return FgmActors.newInstance(1);

                case 2:
/*                    ThirdFragment fragment2 = new ThirdFragment();
                    return fragment2;*/
                    return FgmPlot.newInstance(2);

                case 3:
/*                    FourthFragment fragment3 = new FourthFragment();
                    return fragment3;*/
                    return FgmComment.newInstance(3);
            }
            FgmInfos defaultFragment = new FgmInfos();
            return defaultFragment;
            //**************************************************************************************
            // return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
                case 3:
                    return getString(R.string.title_section4).toUpperCase();
            }
            return null;
        }
    }

/*    public static class FirstFragment extends Fragment {
        public FirstFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.activity_mact_film_details_infos, container, false);



            return view;
        }

    }

      public static class SecondFragment extends Fragment {
        public SecondFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.activity_mact_film_details_actors, container, false);
            return view;
        }


      public static class ThirdFragment extends Fragment {
        public ThirdFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.activity_mact_film_details_plot, container, false);
            return view;
        }
    }

    public static class FourthFragment extends Fragment {
        public FourthFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.activity_mact_film_details_comment, container, false);
            return view;
        }
    }*/

    //**********************************************************************************************




}