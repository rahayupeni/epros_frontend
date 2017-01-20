package com.example.renaissance.epros;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class main extends AppCompatActivity {

    private SQLiteHandler db;
    private static final String TAG = register_perusahaan_kriteriaacara.class.getSimpleName();
    private SessionManager session;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private void getUser() {
        String tag_string_req = "req_register";

        HashMap<String, String> login = db.getLogin();

        String name = login.get("uid");

        Uri builturi = Uri.parse(AppConfig.URL_GET_USER) .buildUpon()
                .appendQueryParameter("id",name).build();

        String  url = builturi.toString();
        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        String uid = jObj.getString("uid");
                        JSONObject user = jObj.getJSONObject("user");
                        String username = user.getString("username");
                        String email = user.getString("email");
                        String password = user.getString("password");
                        String authkey = user.getString("authKey");
                        String statusemail = user.getString("statusemail");
                        String statusbukti = user.getString("statusbukti");
                        String statussms = user.getString("statussms");
                        String statusupdate = user.getString("statusupdate");
                        String level = user.getString("level");
                        String nama = user.getString("nama");
                        String phone = user.getString("phone");
                        String alamat = user.getString("alamat");
                        String tanggal = user.getString("tanggal");
                        String cabang = user.getString("cabang");
                        String gambar = user.getString("gambar");
                        String latitude = user.getString("latitude");
                        String longitude = user.getString("longitude");
                        String acara = user.getString("acara");
                        String menerima = user.getString("menerima");
                        String jangka_waktu = user.getString("jangka_Waktu");
                        String timbal_balik = user.getString("timbal_balik");
                        String sponsor = user.getString("sponsor");


                        // Inserting row in users table
                        db.addUser(uid,username,password,authkey,level,statusemail, statussms,statusbukti,statusupdate,
                                nama, phone, alamat, tanggal, cabang, gambar, latitude,longitude, acara,
                                menerima, jangka_waktu, timbal_balik, sponsor) ;

                        db.deleteRegister();

//                        // Launch login activity
//                        Intent intent = new Intent(MainActivity.this,
//                                ProfileActivity.class);
//                        startActivity(intent);
//                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent intent = new Intent(this, setting.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.logout){
            session = new SessionManager(getApplicationContext());
            session.setLogin(false);

            db.deleteLogin();
            db.deleteUsers();

            // Launching the login activity
            Intent intent = new Intent(main.this, login_ac.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */


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
            switch (position) {
                case 0:
                    main_fg_1kontak tab1 = new main_fg_1kontak();
                    return tab1;
                case 1:
                    main_fg_2chat tab2 = new main_fg_2chat();
                    return tab2;
                case 2:
                    main_fg_3timeline tab3 = new main_fg_3timeline();
                    return tab3;
                case 3:
                    main_fg_4another tab4 = new main_fg_4another();
                    return tab4;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "KONTAK";
                case 1:
                    return "CHAT";
                case 2:
                    return "TIMELINE";
                case 3:
                    return "ANOTHER";
            }
            return null;
        }
    }
}
