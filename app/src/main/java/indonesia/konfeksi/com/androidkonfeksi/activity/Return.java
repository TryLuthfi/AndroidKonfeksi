package indonesia.konfeksi.com.androidkonfeksi.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import indonesia.konfeksi.com.androidkonfeksi.Interface.RecyclerViewClickListener;
import indonesia.konfeksi.com.androidkonfeksi.R;
import indonesia.konfeksi.com.androidkonfeksi.adapter.DialogRecyclerAdapter;
import indonesia.konfeksi.com.androidkonfeksi.adapter.ReturnRecyclerAdapter;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductHistoryPenjualan;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductPenjualanBarang;
import indonesia.konfeksi.com.androidkonfeksi.konfigurasi.konfigurasi;

public class Return extends AppCompatActivity {

    private static final String TAG = "Return";
    private AlertDialog dialog;
    private LayoutInflater inflater;
    private View dialogView;
    private EditText cariBarang;
    List<ProductHistoryPenjualan> productBarang;
    List<ProductHistoryPenjualan> productBarangRecyclerview;
    List<ProductHistoryPenjualan> barangPilih;
    private String kode;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);

        dialog = new AlertDialog.Builder(Return.this).create();
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_return, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        cariBarang = dialogView.findViewById(R.id.cariBarang);
        dialog.show();
        productBarang = new ArrayList<>();

        recyclerView =  findViewById(R.id.recyclerviewR);

        ambilBarang();

        cariBarang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                kode = cariBarang.getText().toString().trim();
                barangPilih = new ArrayList<>();
                for(int i = 0; i < productBarang.size(); i++){
                    if(productBarang.get(i).getNo_faktur().equalsIgnoreCase(kode)){
                        barangPilih.add(productBarang.get(i));
                        recyclerView.setLayoutManager(new LinearLayoutManager(Return.this));

                        productBarangRecyclerview = new ArrayList<>();
                        ReturnRecyclerAdapter adapter = new ReturnRecyclerAdapter(Return.this, barangPilih);
                        recyclerView.setAdapter(adapter);
                        dialog.dismiss();
                    }else {
                        Toast.makeText(Return.this, "Tidak Ada", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void ambilBarang(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.URL_GET_HISTORYPENJUALAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for(int i = 0; i < array.length(); i++){
                                JSONObject returnJson = array.getJSONObject(i);
                                ProductHistoryPenjualan barang = new ProductHistoryPenjualan(
                                        returnJson.getString("id_penjualan"),
                                        returnJson.getString("id_karyawan"),
                                        returnJson.getString("id_pelanggan"),
                                        returnJson.getString("date"),
                                        returnJson.getString("time"),
                                        returnJson.getString("no_faktur"),
                                        returnJson.getString("no_nota"),
                                        returnJson.getString("metode_pembayaran"),
                                        returnJson.getString("diskon_persen"),
                                        returnJson.getString("diskon_rupiah"),
                                        returnJson.getString("total_harga"),
                                        returnJson.getString("biaya"),
                                        returnJson.getString("selisih"),
                                        returnJson.getString("status"),
                                        returnJson.getString("kode_karyawan"),
                                        returnJson.getString("username"),
                                        returnJson.getString("password"),
                                        returnJson.getString("nama"),
                                        returnJson.getString("alamat"),
                                        returnJson.getString("kota"),
                                        returnJson.getString("negara"),
                                        returnJson.getString("kode_pos"),
                                        returnJson.getString("no_telp"),
                                        returnJson.getString("email"),
                                        returnJson.getString("date_input"),
                                        returnJson.getString("date_edit"),
                                        returnJson.getString("token"),
                                        returnJson.getString("id_posisi"),
                                        returnJson.getString("kode_pelanggan"),
                                        returnJson.getString("nama_toko"),
                                        returnJson.getString("no_telp2"),
                                        returnJson.getString("no_telp3"),
                                        returnJson.getString("catatan"),
                                        returnJson.getString("nama_karyawan")
                                );
                                productBarang.add(barang);
                                Log.d(TAG, "onResponse: " + barang);
                            }

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

        Volley.newRequestQueue(this).add(stringRequest);

    }
}