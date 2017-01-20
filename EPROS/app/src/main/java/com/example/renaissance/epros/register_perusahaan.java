package com.example.renaissance.epros;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class register_perusahaan extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnDatePicker;
    EditText txtDate;
    private int mYear, mMonth, mDay;

    private static final String TAG = register_perusahaan.class.getSimpleName();
    private EditText Username;
    private EditText Password;
    private EditText RePassword;
    private EditText Email;
    private EditText NamaPerusahaan;
    private EditText Notelp;
    private EditText Cabang;
    private EditText Alamat;
    private ProgressDialog pDialog;
    private SQLiteHandler db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_perusahaan);

        Username = (EditText) findViewById(R.id.pusername);
        Password = (EditText) findViewById(R.id.password);
        RePassword = (EditText) findViewById(R.id.rpassword);
        Email = (EditText) findViewById(R.id.pemail);
        Cabang = (EditText) findViewById(R.id.pcabang);
        NamaPerusahaan = (EditText) findViewById(R.id.namaperusahaan);
        Notelp = (EditText) findViewById(R.id.ptelp);
        Alamat = (EditText) findViewById(R.id.palamat);
        Button next = (Button) findViewById(R.id.pnext);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        SessionManager session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(register_perusahaan.this,
                    main.class);
            startActivity(intent);
            finish();
        }

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String username = Username.getText().toString().trim();
                String password = Password.getText().toString().trim();
                String repassword = RePassword.getText().toString().trim();
                String email = Email.getText().toString().trim();
                String namaperusahaan = NamaPerusahaan.getText().toString().trim();
                String cabang = Cabang.getText().toString().trim();
                String notelp = Notelp.getText().toString().trim();
                String alamat = Alamat.getText().toString().trim();

                if (!username.isEmpty() && !password.isEmpty() && !cabang.isEmpty() && !repassword.isEmpty() && !namaperusahaan.isEmpty() && !notelp.isEmpty() && !alamat.isEmpty() && !email.isEmpty()) {
                    if (password.compareTo(repassword) == 0){
                        Register(username, password, email, namaperusahaan, notelp, alamat, cabang);
                    }else {
                        Toast.makeText(getApplicationContext(),
                                "Mohon maaf password anda tidak match", Toast.LENGTH_LONG)
                                .show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
        btnDatePicker=(ImageButton) findViewById(R.id.btn_date);
        txtDate=(EditText)findViewById(R.id.in_date);
        btnDatePicker.setOnClickListener(this);
    }

    private void Register(final String username, final String password, final String email, final String namaperusahaan, final String notelp, final String alamat, final String cabang) {

        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DAFTAR_PERUSAHAAN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");

                        // Inserting row in users table
                        db.addRegister(uid);

                        // Launch login activity
                        Intent intent = new Intent(register_perusahaan.this,
                                register_perusahaan_kriteriaacara.class);
                        startActivity(intent);
                        finish();
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
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                params.put("nama", namaperusahaan);
                params.put("alamat", alamat);
                params.put("phone", notelp);
                params.put("cabang", cabang);

                return params;
            }

        };

        // Adding request to request queue
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(this, pilihstatus.class));
        finish();

    }
}
