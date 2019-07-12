package indonesia.konfeksi.com.androidkonfeksi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
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
import indonesia.konfeksi.com.androidkonfeksi.adapter.KonfirmasiKasirAdapter;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductKonfirmasiKasir;
import indonesia.konfeksi.com.androidkonfeksi.konfigurasi.konfigurasi;

public class isiKonfirmasiKasir extends AppCompatActivity {
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    List<ProductKonfirmasiKasir> productList;

    private String mPostNoNota = null;
    private String mPostKaryawan = null;
    private String mPostTanggal = null;
    private TextView nonota;
    private TextView tanggal;
    private TextView karyawan;

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

        mPostNoNota = getIntent().getExtras().getString("no_nota");
        mPostKaryawan = getIntent().getExtras().getString("nama_karyawan");
        mPostTanggal = getIntent().getExtras().getString("tanggal");

        nonota.setText(mPostNoNota);
        tanggal.setText(mPostTanggal);
        karyawan.setText(mPostKaryawan);

        loadProducts();
    }

    private void loadProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.URL_KONFIRMASI_KASIR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject product = array.getJSONObject(i);
                                //adding the product to product list
//                                int harga = Integer.parseInt(product.getString("harga_barang"));
//                                if(harga > 10000)
                                if(mPostNoNota.equals(product.getString("no_nota"))) {
                                    productList.add(new ProductKonfirmasiKasir(
                                            product.getString("id_penjualan"),
                                            product.getString("id_karyawan"),
                                            product.getString("id_pelanggan"),
                                            product.getString("date"),
                                            product.getString("time"),
                                            product.getString("no_faktur"),
                                            product.getString("no_nota"),
                                            product.getString("metode_pembayaran"),
                                            product.getString("diskon_persen"),
                                            product.getString("diskon_rupiah"),
                                            product.getString("total_harga"),
                                            product.getString("biaya"),
                                            product.getString("selisih"),
                                            product.getString("status"),
                                            product.getString("kode_karyawan"),
                                            product.getString("username"),
                                            product.getString("password"),
                                            product.getString("nama"),
                                            product.getString("alamat"),
                                            product.getString("kota"),
                                            product.getString("negara"),
                                            product.getString("kode_pos"),
                                            product.getString("no_telp"),
                                            product.getString("email"),
                                            product.getString("date_input"),
                                            product.getString("date_edit"),
                                            product.getString("token"),
                                            product.getString("id_posisi"),
                                            product.getString("kode_pelanggan"),
                                            product.getString("nama_toko"),
                                            product.getString("no_telp2"),
                                            product.getString("no_telp3"),
                                            product.getString("catatan"),
                                            product.getString("nama_karyawan")
                                    ));
                                }
                            }

                            //creating adapter object and Xsetting it to recyclerview
                            KonfirmasiKasirAdapter adapter = new KonfirmasiKasirAdapter(isiKonfirmasiKasir.this, productList);
                            recyclerView.setAdapter(adapter);

                            progressBar.setVisibility(View.INVISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.INVISIBLE);
//                            txErr.setVisibility(View.VISIBLE);
//                            cobaLagi.setVisibility(View.VISIBLE);
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
