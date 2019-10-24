package indonesia.konfeksi.com.androidkonfeksi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import indonesia.konfeksi.com.androidkonfeksi.R;
import indonesia.konfeksi.com.androidkonfeksi.adapter.IsiKonfirmasiKasirAdapter;
import indonesia.konfeksi.com.androidkonfeksi.adapter.PenjualanTambahAdapter;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductIsiKonfirmasiKasir;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductPenjualanBarang;
import indonesia.konfeksi.com.androidkonfeksi.konfigurasi.konfigurasi;

public class isiKonfirmasiKasir extends AppCompatActivity {
    private static final String TAG = "isiKonfirmasiKasir";
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    private String mPostTanggal = null;
    private String mPostWaktu = null;
    private String mPostNoNota = null;
    private String mPostIdPenjualan = null;
    private String mPostNamaPelanggan = null;
    private String mPostNamaKaryawan = null;
    private String mPostNamaPengambil = null;
    private String mPostNoTelp = null;
    private String mPostAlamat = null;
    List<ProductPenjualanBarang> mPostListBarang = new ArrayList<>();
    private String mPostBiaya = null;


    private TextView nonota;
    private TextView tanggal;
    private TextView waktu;
    private TextView karyawan;
    private TextView pengambil;
    private TextView nama_pelanggan;
    private TextView total_harga;
    private TextView no_telp;
    private TextView alamat;
    private TextView error;
    private NumberFormat formatRupiah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_konfirmasi_kasir);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(isiKonfirmasiKasir.this));

        nonota = findViewById(R.id.nonota);
        tanggal = findViewById(R.id.tanggal);
        waktu = findViewById(R.id.waktu);
        karyawan = findViewById(R.id.karyawan);
        pengambil = findViewById(R.id.pengabil);
        nama_pelanggan = findViewById(R.id.nama_pelanggan);
        total_harga = findViewById(R.id.total_harga);
        no_telp = findViewById(R.id.no_telp);
        alamat = findViewById(R.id.alamat);
        error = findViewById(R.id.error);

        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        mPostTanggal = getIntent().getExtras().getString("tanggal");
        mPostWaktu = getIntent().getExtras().getString("waktu");
        mPostNoNota = getIntent().getExtras().getString("no_nota");
        mPostIdPenjualan = getIntent().getExtras().getString("id_penjualan");
        mPostNamaPelanggan = getIntent().getExtras().getString("nama_pelanggan");
        mPostNamaKaryawan = getIntent().getExtras().getString("nama_karyawan");
        mPostNamaPengambil = getIntent().getExtras().getString("nama_pengambil");
        mPostNoTelp = getIntent().getExtras().getString("no_telp");
        mPostAlamat = getIntent().getExtras().getString("alamat");
        mPostListBarang = (List<ProductPenjualanBarang>) getIntent().getSerializableExtra("barang");
        mPostBiaya = getIntent().getExtras().getString("total_harga");


        double hargabarang1 = Double.parseDouble(mPostBiaya);
        total_harga.setText(formatRupiah.format((double)hargabarang1));

        nonota.setText(mPostNoNota);
        tanggal.setText(mPostTanggal);
        waktu.setText(mPostWaktu);
        karyawan.setText(mPostNamaKaryawan);
        pengambil.setText(mPostNamaPengambil);
        nama_pelanggan.setText(mPostNamaPelanggan);

        no_telp.setText(mPostNoTelp.isEmpty()? "-" : mPostNoTelp);
        alamat.setText(mPostAlamat.isEmpty()? "-" : mPostAlamat);

        loadProducts();

        Log.d(TAG, "onCreate: " + mPostListBarang.get(0).getNamaBarang());


        PenjualanTambahAdapter adapter = new PenjualanTambahAdapter(isiKonfirmasiKasir.this, mPostListBarang);
        recyclerView.setAdapter(adapter);

        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        error.setVisibility(View.VISIBLE);
    }

    private void loadProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.URL_DETAIL_KONFIRMASI_KASIR+mPostIdPenjualan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array = new JSONArray(response);
                            Log.d(TAG, "onResponse: " + array);
//                            for (int i = 0; i < array.length(); i++) {
//                                JSONObject product = array.getJSONObject(i);
//
//                                if(mPostIdPenjualan.equals(product.getString("id_penjualan"))) {
//                                    productList.add(new ProductIsiKonfirmasiKasir(
//                                            product.getString("id_detail_penjualan"),
//                                            product.getString("id_penjualan"),
//                                            product.getString("id_barang"),
//                                            product.getString("id_varian_harga"),
//                                            product.getString("qty"),
//                                            product.getString("total_harga"),
//                                            product.getString("ket"),
//                                            product.getString("kode_barang"),
//                                            product.getString("id_karyawan"),
//                                            product.getString("diskon_persen"),
//                                            product.getString("diskon_rupiah"),
//                                            product.getString("nama_barang"),
//                                            product.getString("kode_barcode"),
//                                            product.getString("image"),
//                                            product.getString("konsinasi"),
//                                            product.getString("date_input"),
//                                            product.getString("date_edit"),
//                                            product.getString("ukuran"),
//                                            product.getString("meter"),
//                                            product.getString("warna"),
//                                            product.getString("stok_jual"),
//                                            product.getString("harga")
//                                    ));
//                                }
//                            }
//
//                            //creating adapter object and Xsetting it to recyclerview
//                            IsiKonfirmasiKasirAdapter adapter = new IsiKonfirmasiKasirAdapter(isiKonfirmasiKasir.this, productList);
//                            recyclerView.setAdapter(adapter);
//
//                            progressBar.setVisibility(View.INVISIBLE);
//                            recyclerView.setVisibility(View.VISIBLE);
//                            error.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.INVISIBLE);
//                        txErr.setVisibility(View.VISIBLE);
//                        cobaLagi.setVisibility(View.VISIBLE);
                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(isiKonfirmasiKasir.this).add(stringRequest);
    }
}
