package com.example.renaissance.epros;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.renaissance.epros.R.id.sponsor;

public class register_perusahaan_kriteriaacara extends AppCompatActivity {

    String[] timbalbalik ={"Banner","Makanan","Minuman","Uang","Transportasi","Tempat"};

    private static final String TAG = register_perusahaan_kriteriaacara.class.getSimpleName();;
    private EditText Acara;
    private EditText Menerima;
    private EditText JangkaWaktu;
    private EditText TimbalBalik;
    private EditText Sponsor;
    private Button Save;
    private ProgressDialog pDialog;
    private SQLiteHandler db;
    private String Acara2;
    private String Menerima2;
    private String TimbalBalik2;
    private String JangkaWaktu2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_perusahaan_kriteriaacara);

        final CheckBox formal = (CheckBox) findViewById(R.id.check_formal);
        final CheckBox informal = (CheckBox) findViewById(R.id.check_informal);

        if (formal.isChecked() && informal.isChecked()){
            formal.setText("formal");
            informal.setText("& informal");
        }else if (formal.isChecked()){
            formal.setText("formal");
        }else if (informal.isChecked()){
            informal.setText("informal");
        }

        final CheckBox sma = (CheckBox) findViewById(R.id.check_sma);
        final CheckBox univ = (CheckBox) findViewById(R.id.check_univ);
        final CheckBox umum = (CheckBox) findViewById(R.id.check_umum);

        if (sma.isChecked() && univ.isChecked() && umum.isChecked()){
            sma.setText("SMA, ");
            univ.setText("Universitas, ");
            umum.setText("& Umum");
        }else if (sma.isChecked() && univ.isChecked()){
            sma.setText("SMA ");
            univ.setText("& Universitas");
        }else if (sma.isChecked() && umum.isChecked()){
            sma.setText("SMA ");
            umum.setText("& Umum");
        }else if (univ.isChecked() && umum.isChecked()){
            univ.setText("Universitas ");
            umum.setText("& Umum");
        }else  if (sma.isChecked()){
            sma.setText("SMA");
        }else if (univ.isChecked()){
            univ.setText("Universitas");
        }else if (umum.isChecked()){
            umum.setText("Umum");
        }

        final CheckBox lisan = (CheckBox) findViewById(R.id.check_1);
        final CheckBox tulisan = (CheckBox) findViewById(R.id.check_2);
        final CheckBox medsos = (CheckBox) findViewById(R.id.check_3);
        final CheckBox stan = (CheckBox) findViewById(R.id.check_4);
        final CheckBox demo = (CheckBox) findViewById(R.id.check_5);

        if (lisan.isChecked() && tulisan.isChecked() && medsos.isChecked() && stan.isChecked() && demo.isChecked()){
            lisan.setText("Branding via Lisan, ");
            tulisan.setText("Branding via Tulisan, ");
            medsos.setText("Branding via Media Sosial, ");
            stan.setText("Penyediaan Stan, ");
            demo.setText("& Demo Produk");
        }else if (lisan.isChecked() && tulisan.isChecked() && medsos.isChecked() && stan.isChecked() ) {
            lisan.setText("Branding via Lisan, ");
            tulisan.setText("Branding via Tulisan, ");
            medsos.setText("Branding via Media Sosial, ");
            stan.setText("& Penyediaan Stan");
        }
//        }else if (lisan.isChecked() && tulisan.isChecked() && medsos.isChecked() && demo.isChecked()){
//            TimbalBalik2 = "Branding via Lisan, Tulisan, Media Sosial, & Demo Produk";
//        }else if (lisan.isChecked() && tulisan.isChecked() && stan.isChecked() && demo.isChecked()){
//            TimbalBalik2 = "Branding via Lisan, Tulisan, Penyediaan Stan, & Demo Produk";
//        }else if (lisan.isChecked() && medsos.isChecked() && stan.isChecked() && demo.isChecked()){
//            TimbalBalik2 = "Branding via Lisan, Media Sosial, Penyediaan Stan, & Demo Produk";
//        }else if (tulisan.isChecked() && medsos.isChecked() && stan.isChecked() && demo.isChecked()){
//            TimbalBalik2 = "Branding via Tulisan, Media Sosial, Penyediaan Stan, & Demo Produk";
//        }

        Spinner mySpinner=(Spinner) findViewById(R.id.spin);
        JangkaWaktu2 = mySpinner.getSelectedItem().toString();

//        Acara = (EditText) findViewById(R.id.acara);
//        Menerima = (EditText) findViewById(R.id.menerima);
//        JangkaWaktu = (EditText) findViewById(R.id.jangka);
//        TimbalBalik = (EditText) findViewById(R.id.balik);
        Sponsor = (EditText) findViewById(sponsor);
        Save = (Button) findViewById(R.id.simpan);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        SessionManager session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());
        Save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String acara = formal.getText().toString().trim() + informal.getText().toString().trim();
                String menerima = sma.getText().toString().trim() + univ.getText().toString().trim() + umum.getText().toString().trim();
                String jangkawaktu = JangkaWaktu2;
                String timbalbalik = lisan.getText().toString().trim() + tulisan.getText().toString().trim() + medsos.getText().toString().trim() + stan.getText().toString().trim() + demo.getText().toString().trim();
                String sponsor = Sponsor.getText().toString().trim();

//                if (!acara.isEmpty() && !menerima.isEmpty() && !jangkawaktu.isEmpty() && !timbalbalik.isEmpty() && !sponsor.isEmpty()) {
//                    Register(acara, menerima, jangkawaktu, timbalbalik, sponsor);
//                } else {
//                    Toast.makeText(getApplicationContext(),
//                            "Please enter your details!", Toast.LENGTH_LONG)
//                            .show();
//                }
                Register(acara, menerima, jangkawaktu, timbalbalik, sponsor);
            }
        });
//        Button button_next = (Button) findViewById(R.id.button_next);
//        button_next.setOnClickListener(new View.OnClickListener(){
//                                           public void onClick(View v){
//                                               startActivity(new Intent(register_perusahaan_kriteriaacara.this, profil.class));
//                                               finish();
//                                           }
//                                       }
//        );
//
//        MultiAutoCompleteTextView multiTextAutoComplete =(MultiAutoCompleteTextView)findViewById(R.id.multiAutoCompleteTextView1);
//
//        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, timbalbalik);
//
//        multiTextAutoComplete.setAdapter(adapter);
//        multiTextAutoComplete.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }

    private void Register(final String acara, final String menerima, final String jangkawaktu, final String timbalbalik, final String sponsor) {

        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        HashMap<String, String> regis = db.getRegister();

        String name = regis.get("uid");

        Uri builturi = Uri.parse(AppConfig.URL_NEXT_PERUSAHAAN) .buildUpon()
                .appendQueryParameter("id",name).build();

        String  url = builturi.toString();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL

                        // Launch login activity
                        Intent intent = new Intent(register_perusahaan_kriteriaacara.this,
                                GetLokasi.class);
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
                params.put("acara", acara);
                params.put("menerima", menerima);
                params.put("jangka_waktu", jangkawaktu);
                params.put("timbal_balik", timbalbalik);
                params.put("sponsor", sponsor);

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

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(this, register_perusahaan.class));
        finish();

    }
}
