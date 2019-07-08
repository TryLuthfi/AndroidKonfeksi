package indonesia.konfeksi.com.androidkonfeksi.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import indonesia.konfeksi.com.androidkonfeksi.R;
import indonesia.konfeksi.com.androidkonfeksi.konfigurasi.konfigurasi;

public class TambahPembelian extends AppCompatActivity {
    private static final String TAG = "TambahPembelian";
    private String date;
    private TextView tgl;

    private TextView nonota;
    private Spinner namaSupplier;
    private Spinner metodeBayar;
    private Spinner namaBarang;
    private EditText jumlahColi;
    private EditText keterangan;
    private Spinner status;
    private TextView nofaktur;
    private EditText namaGudang;
    private EditText biaya;
    private EditText totalHarga;

    String idSupplierPilih;
    String idBarangPilih;
    int idStatusPilih;
    int idPembayaranPilih;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pembelian);

        nonota = findViewById(R.id.id_nonota);
        namaSupplier = findViewById(R.id.id_namasupplier);
        metodeBayar = findViewById(R.id.id_metodepembayaran);
        namaBarang = findViewById(R.id.id_namabarang);
        jumlahColi = findViewById(R.id.id_jumlah);
        keterangan = findViewById(R.id.id_keterangan);
        status = findViewById(R.id.id_status);
        nofaktur = findViewById(R.id.id_nofaktur);
        namaGudang = findViewById(R.id.id_namagudang);
        biaya = findViewById(R.id.id_biaya);
        totalHarga = findViewById(R.id.id_totalharga);



        List<String> metodepembayaran = new ArrayList<String>();
        metodepembayaran.add("Cash");
        metodepembayaran.add("Piutang");
        ArrayAdapter<String> adapterBayarSupplier = new ArrayAdapter<String>(
                TambahPembelian.this,
                android.R.layout.simple_spinner_dropdown_item, metodepembayaran);
        metodeBayar.setAdapter(adapterBayarSupplier);

        List<String> statusbarang = new ArrayList<String>();
        statusbarang.add("Tampilkan");
        statusbarang.add("Sembunyikan");
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

        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    idStatusPilih = 1;
                }else{
                    idStatusPilih = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        metodeBayar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idPembayaranPilih = position;
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

    public void tambahBarangPembelian(View view){

//        String noNotaKirim = nonota.getText().toString();
//        String namaSupplierKirim = idSupplierPilih;
//        String namaBarangKirim = idBarangPilih;
//        String jumlahColiKirim = jumlahColi.getText().toString();
//        String keteranganKirim = keterangan.getText().toString();
//        int statusKirim = idStatusPilih;
//
//        String noFakturKirim = nofaktur.getText().toString();
//        String namaGudangKirim = namaGudang.getText().toString();
//        int metodeBayarKirim = idPembayaranPilih;
//        String biayaKirim = biaya.getText().toString();
//        String totalHargaKirim = totalHarga.getText().toString();

        if(getId_Karyawan() == "null"){
            Log.d(TAG, "tambahBarangPembelian: " + getId_Karyawan());
            Toast.makeText(TambahPembelian.this, "Id Karyawan tidak tersedia", Toast.LENGTH_LONG).show();
            return;
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, konfigurasi.URL_TAMBAH_PEMBELIAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject resObj = new JSONObject(response);
                            Log.d(TAG, "onResponse: " + resObj.toString());
                            if(resObj.getString("success").equals("true")){
                                Log.d(TAG, "onResponse: " + resObj.toString());

                            }else{
                                Log.d(TAG, "onResponse false: " + resObj.toString());
                                Toast.makeText(TambahPembelian.this, resObj.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (Throwable t) {
                            Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error);
                        Toast.makeText(TambahPembelian.this, "Server Tidak Terjangkau", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("no_faktur", nofaktur.getText().toString());
                params.put("no_nota", nonota.getText().toString());
                params.put("metode_pembayaran", String.valueOf(idPembayaranPilih));
                params.put("total_harga", totalHarga.getText().toString());
                params.put("biaya", biaya.getText().toString());
                params.put("id_karyawan", getId_Karyawan());
                params.put("id_supplier", idSupplierPilih);
                params.put("coly", jumlahColi.getText().toString());
                params.put("status", String.valueOf(idStatusPilih));
                params.put("id_barang", idBarangPilih);
                params.put("ket", keterangan.getText().toString());
                params.put("nama_gudang", namaGudang.getText().toString());
                Log.d(TAG, "getParams: " + params.toString());
                return params;
            }
        };


        Volley.newRequestQueue(this).add(stringRequest);


    }

    private String getId_Karyawan(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String id_karyawan = preferences.getString("id_karyawan", "null");
        return id_karyawan;
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

