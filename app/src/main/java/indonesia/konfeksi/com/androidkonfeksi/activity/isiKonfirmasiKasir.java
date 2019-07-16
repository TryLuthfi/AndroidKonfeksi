package indonesia.konfeksi.com.androidkonfeksi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import indonesia.konfeksi.com.androidkonfeksi.json.ProductIsiKonfirmasiKasir;
import indonesia.konfeksi.com.androidkonfeksi.konfigurasi.konfigurasi;

public class isiKonfirmasiKasir extends AppCompatActivity {
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    List<ProductIsiKonfirmasiKasir> productList;

    private String mPostNoNota = null;
    private String mPostKaryawan = null;
    private String mPostIdPenjualan = null;
    private String mPostTanggal = null;
    private String mPostBiaya = null;
    private String mPostNamaPelanggan = null;
    private String mPostNoTelp = null;
    private String mPostAlamat = null;
    private TextView nonota;
    private TextView tanggal;
    private TextView total_harga;
    private TextView karyawan;
    private TextView nama_pelanggan;
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

        productList = new ArrayList<>();

        nonota = findViewById(R.id.nonota);
        tanggal = findViewById(R.id.tanggal);
        karyawan = findViewById(R.id.karyawan);
        nama_pelanggan = findViewById(R.id.nama_pelanggan);
        total_harga = findViewById(R.id.total_harga);
        no_telp = findViewById(R.id.no_telp);
        alamat = findViewById(R.id.alamat);
        error = findViewById(R.id.error);

        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        mPostNoNota = getIntent().getExtras().getString("no_nota");
        mPostIdPenjualan = getIntent().getExtras().getString("id_penjualan");
        mPostKaryawan = getIntent().getExtras().getString("nama_karyawan");
        mPostTanggal = getIntent().getExtras().getString("tanggal");
        mPostBiaya = getIntent().getExtras().getString("total_harga");
        mPostNamaPelanggan = getIntent().getExtras().getString("nama_pelanggan");
        mPostNoTelp = getIntent().getExtras().getString("no_telp");
        mPostAlamat = getIntent().getExtras().getString("alamat");

        double hargabarang1 = Double.parseDouble(mPostBiaya);
        total_harga.setText(formatRupiah.format((double)hargabarang1));

        nonota.setText(mPostNoNota);
        tanggal.setText(mPostTanggal);
        karyawan.setText(mPostKaryawan);
        nama_pelanggan.setText(mPostNamaPelanggan);
        no_telp.setText(mPostNoTelp);
        alamat.setText(mPostAlamat);

        loadProducts();

//        Toast.makeText(isiKonfirmasiKasir.this, konfigurasi.URL_DETAIL_KONFIRMASI_KASIR+mPostIdPenjualan, Toast.LENGTH_SHORT).show();
    }

    private void loadProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.URL_DETAIL_KONFIRMASI_KASIR+mPostIdPenjualan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray array = obj.getJSONArray("detail");

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject product = array.getJSONObject(i);

                                if(mPostIdPenjualan.equals(product.getString("id_penjualan"))) {
                                    productList.add(new ProductIsiKonfirmasiKasir(
                                            product.getString("id_detail_penjualan"),
                                            product.getString("id_penjualan"),
                                            product.getString("id_barang"),
                                            product.getString("id_varian_harga"),
                                            product.getString("qty"),
                                            product.getString("total_harga"),
                                            product.getString("ket"),
                                            product.getString("kode_barang"),
                                            product.getString("id_karyawan"),
                                            product.getString("diskon_persen"),
                                            product.getString("diskon_rupiah"),
                                            product.getString("nama_barang"),
                                            product.getString("kode_barcode"),
                                            product.getString("image"),
                                            product.getString("konsinasi"),
                                            product.getString("date_input"),
                                            product.getString("date_edit"),
                                            product.getString("ukuran"),
                                            product.getString("meter"),
                                            product.getString("warna"),
                                            product.getString("stok_jual"),
                                            product.getString("harga")
                                    ));
                                }
                            }

                            //creating adapter object and Xsetting it to recyclerview
                            IsiKonfirmasiKasirAdapter adapter = new IsiKonfirmasiKasirAdapter(isiKonfirmasiKasir.this, productList);
                            recyclerView.setAdapter(adapter);

                            progressBar.setVisibility(View.INVISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
                            error.setVisibility(View.VISIBLE);

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
