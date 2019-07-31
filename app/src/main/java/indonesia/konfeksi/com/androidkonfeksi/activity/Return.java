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
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
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
import indonesia.konfeksi.com.androidkonfeksi.adapter.IsiKonfirmasiKasirAdapter;
import indonesia.konfeksi.com.androidkonfeksi.adapter.ReturnRecyclerAdapter;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductHistoryPenjualan;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductIsiKonfirmasiKasir;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductPenjualanBarang;
import indonesia.konfeksi.com.androidkonfeksi.konfigurasi.konfigurasi;

public class Return extends AppCompatActivity {

    private static final String TAG = "Return";
    private AlertDialog dialog;
    private LayoutInflater inflater;
    private View dialogView;
    List<ProductIsiKonfirmasiKasir> productList;
    private EditText cariBarang;
    private String kode;

    private RelativeLayout all;

    private RecyclerView recyclerView;

    private TextView no_nota;
    private TextView tanggal_nota;
    private TextView waktu_nota;
    private TextView kasir_nota;

    private TextView no_hp_pelanggan;
    private TextView alamat_pelanggan;

    private TextView total_harga;

    List<ProductHistoryPenjualan> productBarang;

    List<ProductIsiKonfirmasiKasir> productBarang2;

    List<ProductHistoryPenjualan> barangPilih;

    List<ProductIsiKonfirmasiKasir> barangPilih2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);

        all = findViewById(R.id.all);

        no_nota = findViewById(R.id.input_no_nota);
        tanggal_nota = findViewById(R.id.input_tgl_nota);
        waktu_nota = findViewById(R.id.input_waktu);
        kasir_nota = findViewById(R.id.input_kasir);

        no_hp_pelanggan = findViewById(R.id.input_no_hp);
        alamat_pelanggan = findViewById(R.id.input_alamat);

        total_harga = findViewById(R.id.total_harga);

        recyclerView =  findViewById(R.id.recyclerviewR);

        dialog = new AlertDialog.Builder(Return.this).create();
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_return, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);

        cariBarang = dialogView.findViewById(R.id.cariBarang);

        dialog.show();
        all.setVisibility(View.INVISIBLE);

        productBarang = new ArrayList<>();
        productBarang2 = new ArrayList<>();

        ambilBarang();
        loadProducts();

        cariBarang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                kode = cariBarang.getText().toString().trim();
                barangPilih = new ArrayList<>();
                barangPilih2 = new ArrayList<>();
                for(int i = 0; i < productBarang.size(); i++){
                    for(int a = 0; a < productBarang2.size(); a++) {
                        if(productBarang.get(i).getNo_faktur().equalsIgnoreCase(kode)) {

                            barangPilih.add(productBarang.get(i));
                            barangPilih2.add(productBarang2.get(a));
                            recyclerView.setLayoutManager(new LinearLayoutManager(Return.this));

                            ReturnRecyclerAdapter adapter = new ReturnRecyclerAdapter(Return.this, barangPilih2);
                            recyclerView.setAdapter(adapter);
                            dialog.dismiss();

                            all.setVisibility(View.VISIBLE);

                            no_nota.setText(barangPilih.get(0).getNo_nota());
                            tanggal_nota.setText(barangPilih.get(0).getDate());
                            waktu_nota.setText(barangPilih.get(0).getTime());
                            kasir_nota.setText(barangPilih.get(0).getNama_karyawan());
                            no_hp_pelanggan.setText(barangPilih.get(0).getNo_telp());
                            alamat_pelanggan.setText(barangPilih.get(0).getAlamat());
                            total_harga.setText(barangPilih.get(0).getTotal_harga());

                            String no_notaa = no_nota.getText().toString().trim();
                            String tanggal = tanggal_nota.getText().toString().trim();
                            String waktu = waktu_nota.getText().toString().trim();
                            String kasir = kasir_nota.getText().toString().trim();
                            String no_telp = no_hp_pelanggan.getText().toString().trim();
                            String alamat = alamat_pelanggan.getText().toString().trim();
                            String total = total_harga.getText().toString().trim();

                            if(no_notaa.isEmpty()){
                                no_nota.setText("Tidak Ada");
                            }
                            if(tanggal.isEmpty()){
                                tanggal_nota.setText("Tidak Ada");
                            }
                            if(waktu.isEmpty()){
                                waktu_nota.setText("Tidak Ada");
                            }
                            if(kasir.isEmpty()){
                                kasir_nota.setText("Tidak Ada");
                            }
                            if(no_telp.isEmpty()){
                                no_hp_pelanggan.setText("Tidak Ada");
                            }
                            if(alamat.isEmpty()){
                                alamat_pelanggan.setText("Tidak Ada");
                            }
                            if(total.isEmpty()){
                                total_harga.setText("Tidak Ada");
                            }

                        }
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

    private void loadProducts(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.URL_GET_HISTORYPENJUALAN+barangPilih.get(0).getId_penjualan(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for(int i = 0; i < array.length(); i++){
                                JSONObject returnJson = array.getJSONObject(i);
                                ProductIsiKonfirmasiKasir barang = new ProductIsiKonfirmasiKasir(
                                        returnJson.getString("id_detail_penjualan"),
                                        returnJson.getString("id_penjualan"),
                                        returnJson.getString("id_barang"),
                                        returnJson.getString("id_varian_harga"),
                                        returnJson.getString("qty"),
                                        returnJson.getString("total_harga"),
                                        returnJson.getString("ket"),
                                        returnJson.getString("kode_barang"),
                                        returnJson.getString("id_karyawan"),
                                        returnJson.getString("diskon_persen"),
                                        returnJson.getString("diskon_rupiah"),
                                        returnJson.getString("nama_barang"),
                                        returnJson.getString("kode_barcode"),
                                        returnJson.getString("image"),
                                        returnJson.getString("konsinasi"),
                                        returnJson.getString("date_input"),
                                        returnJson.getString("date_edit"),
                                        returnJson.getString("ukuran"),
                                        returnJson.getString("meter"),
                                        returnJson.getString("warna"),
                                        returnJson.getString("stok_jual"),
                                        returnJson.getString("harga")
                                );
                                productBarang2.add(barang);
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

    @Override
    public void onBackPressed() {
        dialog.dismiss();
        super.onBackPressed();
    }
}