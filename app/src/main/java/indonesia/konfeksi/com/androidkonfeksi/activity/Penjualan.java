package indonesia.konfeksi.com.androidkonfeksi.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import indonesia.konfeksi.com.androidkonfeksi.R;
import indonesia.konfeksi.com.androidkonfeksi.adapter.ProductsAdapter;
import indonesia.konfeksi.com.androidkonfeksi.json.Product;
import indonesia.konfeksi.com.androidkonfeksi.konfigurasi.konfigurasi;

public class Penjualan extends AppCompatActivity {
    private static final String TAG = "Penjualan";
    private String date;
    private String time;
    private String idPelanggan;
    private String TelpPelanggan;
    private String AlamatPelanggan;
    private String mPostKeyNama = null;
    private Spinner input_nama_pelanggan;
    private EditText input_tgl_nota;
    private EditText input_waktu;
    private EditText input_no_nota;
    private EditText input_kasir;
    private EditText input_no_hp;
    private EditText input_alamat;
    private EditText input_info_lain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan);

        input_tgl_nota = findViewById(R.id.input_tgl_nota);
        input_waktu = findViewById(R.id.input_waktu);
        input_no_nota = findViewById(R.id.input_no_nota);
        input_kasir = findViewById(R.id.input_kasir);
        input_nama_pelanggan = findViewById(R.id.input_nama_pelanngan);
        input_no_hp = findViewById(R.id.input_no_hp);
        input_alamat = findViewById(R.id.input_alamat);
        input_info_lain = findViewById(R.id.input_info_lain);
        mPostKeyNama = Objects.requireNonNull(getIntent().getExtras()).getString("NamaKaryawan");

        setDate();
        settime();

        input_tgl_nota.setText(date);
        input_waktu.setText(time);

        if (mPostKeyNama != null) {
            input_kasir.setText(mPostKeyNama);
        }else {
            input_kasir.setText("");
        }

        ambilNoNotaPenjualan();

        input_nama_pelanggan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Penjualan.StringWithTag pelanggan = (Penjualan.StringWithTag) parent.getItemAtPosition(position);
                Log.d(TAG, "onItemSelected: " + pelanggan.id + ", " + pelanggan.string + ", " + pelanggan.Telp + ", " + pelanggan.Alaamat);
                idPelanggan = (String) pelanggan.id;
                TelpPelanggan = (String) pelanggan.Telp;
                AlamatPelanggan = (String) pelanggan.Alaamat;

                input_no_hp.setText(TelpPelanggan);
                input_alamat.setText(AlamatPelanggan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    List<Penjualan.StringWithTag> pelangganName = new ArrayList<Penjualan.StringWithTag>();
    private void ambilNoNotaPenjualan() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.URL_GET_NONOTAPENJUALAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            String noNota = obj.getString("nota");
                            input_no_nota.setText(noNota);

                            JSONArray arrSupplier = obj.getJSONArray("pelanggan");
                            for(int i = 0; i < arrSupplier.length(); i++){
                                JSONObject supplierJson = arrSupplier.getJSONObject(i);
                                String namapelanggan = supplierJson.getString("nama");
                                String idpelanggan = supplierJson.getString("id_pelanggan");
                                String notelp = supplierJson.getString("no_telp");
                                String alamat = supplierJson.getString("alamat");
                                pelangganName.add(new StringWithTag(namapelanggan, idpelanggan, notelp, alamat));
                            }

                            ArrayAdapter<Penjualan.StringWithTag> adapterSpinnerPelanggan = new ArrayAdapter<Penjualan.StringWithTag>(
                                    Penjualan.this,
                                    android.R.layout.simple_spinner_dropdown_item, pelangganName);
                            input_nama_pelanggan.setAdapter(adapterSpinnerPelanggan);

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
    public void setDate() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        date = formatter.format(today);
    }

    public void settime(){
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        time = formatter.format(today);
    }

    private static class StringWithTag {
        public String string;
        public Object id;
        public String Telp;
        public String Alaamat;

        public StringWithTag(String string, Object id, String Telp, String Alaamat) {
            this.string = string;
            this.id = id;
            this.Telp = Telp;
            this.Alaamat = Alaamat;
        }

        @Override
        public String toString() {
            return string;
        }
    }

}