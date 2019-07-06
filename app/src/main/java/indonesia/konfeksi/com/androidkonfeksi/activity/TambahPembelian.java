package indonesia.konfeksi.com.androidkonfeksi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
    private Spinner metodeBayar;
    private Spinner namaBarang;
    private EditText jumlahColi;
    private EditText keterangan;
    private Spinner status;

    String idSupplierPilih;
    String idBarangPilih;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pembelian);

        nonota = findViewById(R.id.id_nonota);
        nofaktur = findViewById(R.id.id_nofaktur);
        namaSupplier = findViewById(R.id.id_namasupplier);
        metodeBayar = findViewById(R.id.id_metodepembayaran);
        namaBarang = findViewById(R.id.id_namabarang);
        jumlahColi = findViewById(R.id.id_jumlah);
        keterangan = findViewById(R.id.id_keterangan);
        status = findViewById(R.id.id_status);


        List<String> metodepembayaran = new ArrayList<String>();
        metodepembayaran.add("Cash");
        metodepembayaran.add("Piutang");
        ArrayAdapter<String> adapterBayarSupplier = new ArrayAdapter<String>(
                TambahPembelian.this,
                android.R.layout.simple_spinner_dropdown_item, metodepembayaran);
        metodeBayar.setAdapter(adapterBayarSupplier);

        List<String> statusbarang = new ArrayList<String>();
        metodepembayaran.add("Tampilkan");
        metodepembayaran.add("Sembunyikan");
        ArrayAdapter<String> adapterStatusBarang = new ArrayAdapter<String>(
                TambahPembelian.this,
                android.R.layout.simple_spinner_dropdown_item, statusbarang);
        status.setAdapter(adapterStatusBarang);

        ambilNoNota();

        namaSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag supplier = (StringWithTag) parent.getItemAtPosition(position);
                Log.d(TAG, "onItemSelected: " + supplier.id + ", " + supplier.string);
                idSupplierPilih = (String) supplier.id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        namaBarang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag barang = (StringWithTag) parent.getItemAtPosition(position);
                Log.d(TAG, "onItemSelected: " + barang.id + ", " + barang.string);
                idBarangPilih = (String) barang.id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    List<StringWithTag> supplierName = new ArrayList<StringWithTag>();
    List<StringWithTag> barangName = new ArrayList<StringWithTag>();
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
                        for(int i = 0; i < arrSupplier.length(); i++){
                            JSONObject supplierJson = arrSupplier.getJSONObject(i);
                            String namasupplier = supplierJson.getString("nama_supplier");
                            String idsupplier = supplierJson.getString("id_supplier");
                            supplierName.add(new StringWithTag(namasupplier, idsupplier));
                        }

                        ArrayAdapter<StringWithTag> adapterSpinnerSupplier = new ArrayAdapter<StringWithTag>(
                                TambahPembelian.this,
                                android.R.layout.simple_spinner_dropdown_item, supplierName);
                        namaSupplier.setAdapter(adapterSpinnerSupplier);


                        JSONArray arrBarang = obj.getJSONArray("barang");
                        for(int j = 0; j < arrBarang.length(); j++){
                            JSONObject barangJson = arrBarang.getJSONObject(j);
                            String namabarang = barangJson.getString("nama_barang");
                            String idbarang = barangJson.getString("id_barang");
                            //barangName.add(namabarang);
                            barangName.add(new StringWithTag(namabarang, idbarang));

                        }
                        ArrayAdapter<StringWithTag> adapterBarangSupplier = new ArrayAdapter<StringWithTag>(
                                TambahPembelian.this,
                                android.R.layout.simple_spinner_dropdown_item, barangName);
                        namaBarang.setAdapter(adapterBarangSupplier);



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

    private void tambahBarangPembelian(){
        String noNotaKirim = nonota.getText().toString();
        String namaSupplierKirim = idSupplierPilih;
        String namaBarangKirim = idBarangPilih;
        String jumlahColiKirim = jumlahColi.getText().toString();
        String keteranganKirim = keterangan.getText().toString();



    }





    private static class StringWithTag {
        public String string;
        public Object id;

        public StringWithTag(String string, Object id) {
            this.string = string;
            this.id = id;
        }

        @Override
        public String toString() {
            return string;
        }
    }


}

