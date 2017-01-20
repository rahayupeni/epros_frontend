package com.example.renaissance.epros;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Renaissance on 1/12/2017.
 */

public class main_fg_1kontak extends Fragment {

    // Movies json url
    private static final String url = "http://cdo.ionsmart.co/bismillahepros/Api/api/getalluser";
    private ProgressDialog pDialog;
    private ArrayList<Teman> temanList = new ArrayList<>();
    private ListView listView;
    private PopupWindow pwindo;
    private TemanListAdapter adapter;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    private SQLiteHandler db;
    private static final String TAG = register_perusahaan_kriteriaacara.class.getSimpleName();
    private SessionManager session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = new SQLiteHandler(getActivity().getApplicationContext());
        getUser();
        View rootView = inflater.inflate(R.layout.main_fg_1kontak, container, false);
        return rootView;
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
                        Toast.makeText(getActivity().getApplicationContext(),
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
                Toast.makeText(getActivity().getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    Button Dprofile;
    Button Dchat;

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

}
