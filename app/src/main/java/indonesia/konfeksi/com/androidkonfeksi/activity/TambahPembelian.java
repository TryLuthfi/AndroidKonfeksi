package indonesia.konfeksi.com.androidkonfeksi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import indonesia.konfeksi.com.androidkonfeksi.R;
import indonesia.konfeksi.com.androidkonfeksi.konfigurasi.konfigurasi;

public class TambahPembelian extends AppCompatActivity {
    private static final String TAG = "TambahPembelian";
    private String date;
    private TextView tgl;

    private TextView nonota;
    private TextView nofaktur;
    private Spinner namaSupplier;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pembelian);

        nonota = findViewById(R.id.id_nonota);
        nofaktur = findViewById(R.id.id_nofaktur);
        namaSupplier = findViewById(R.id.id_namasupplier);

        ambilNoNota();
    }



    private void ambilNoNota() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.URL_GET_NONOTA,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject obj = new JSONObject(response);
                        nonota.setText("NOTA" + String.format("%03d", Integer.parseInt(obj.getString("nota"))));
                        nofaktur.setText("FAK" + String.format("%03d", Integer.parseInt(obj.getString("nota"))));

                        JSONArray arrSupplier = obj.getJSONArray("supplier");
                        List<String> supplierName = new ArrayList<String>();
                        Log.d(TAG, "onResponse: " + arrSupplier);
                        for(int i = 0; i < arrSupplier.length(); i++){
                            JSONObject supplierJson = arrSupplier.getJSONObject(i);
                            String namasupplier = supplierJson.getString("nama_supplier");
                            supplierName.add(namasupplier);
                            Log.d(TAG, "onResponse: " + supplierName + "   " + namasupplier);
                        }

                        ArrayAdapter<String> adapterSpinnerSupplier = new ArrayAdapter<String>(TambahPembelian.this, android.R.layout.simple_spinner_dropdown_item, supplierName);
                        namaSupplier.setAdapter(adapterSpinnerSupplier);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d(TAG, "onResponse: " + e);
                    }
                }
            },
            new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error);
                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }


}
