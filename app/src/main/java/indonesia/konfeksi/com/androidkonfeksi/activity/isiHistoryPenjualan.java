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
import indonesia.konfeksi.com.androidkonfeksi.adapter.IsiKonfirmasiKasirAdapter;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductIsiKonfirmasiKasir;
import indonesia.konfeksi.com.androidkonfeksi.konfigurasi.konfigurasi;

public class isiHistoryPenjualan extends AppCompatActivity {
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    List<ProductIsiKonfirmasiKasir> productList;

    private String mPostKeyNoNota = null;
    private String mPostKeyTanggal = null;
    private String mPostKeyWaktu = null;
    private String mPostKeyKasir = null;
    private String mPostKeyNoTelp = null;
    private String mPostKeyAlamat= null;
    private String mPostIdPenjualan= null;
    private String mPostTotalHarga= null;

    private TextView input_no_nota ;
    private TextView input_tanggal ;
    private TextView input_waktu ;
    private TextView input_kasir ;
    private TextView input_no_telp ;
    private TextView input_alamat ;
    private TextView total_harga ;
    private TextView error ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_history_penjualan);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(isiHistoryPenjualan.this));

        productList = new ArrayList<>();

        input_no_nota = findViewById(R.id.input_no_nota);
        input_tanggal = findViewById(R.id.input_tgl_nota);
        input_waktu = findViewById(R.id.input_waktu);
        input_kasir = findViewById(R.id.input_kasir);
        input_no_telp = findViewById(R.id.input_no_hp);
        input_alamat = findViewById(R.id.input_alamat);
        total_harga = findViewById(R.id.total_harga);
        error = findViewById(R.id.error);

        mPostKeyNoNota = getIntent().getExtras().getString("no_nota");
        mPostKeyTanggal = getIntent().getExtras().getString("tanggal");
        mPostKeyWaktu = getIntent().getExtras().getString("waktu");
        mPostKeyKasir = getIntent().getExtras().getString("kasir");
        mPostKeyNoTelp = getIntent().getExtras().getString("no_telp");
        mPostKeyAlamat = getIntent().getExtras().getString("alamat");
        mPostIdPenjualan = getIntent().getExtras().getString("id_penjualan");
        mPostTotalHarga = getIntent().getExtras().getString("total_harga");

        input_no_nota.setText(mPostKeyNoNota);
        input_tanggal.setText(mPostKeyTanggal);
        input_waktu.setText(mPostKeyWaktu);
        input_kasir.setText(mPostKeyKasir);
        input_no_telp.setText(mPostKeyNoTelp);
        input_alamat.setText(mPostKeyAlamat);
        total_harga.setText(mPostTotalHarga);

        String no_nota = input_no_nota.getText().toString().trim();
        String tanggal = input_tanggal.getText().toString().trim();
        String waktu = input_waktu.getText().toString().trim();
        String kasir = input_kasir.getText().toString().trim();
        String no_telp = input_no_telp.getText().toString().trim();
        String alamat = input_alamat.getText().toString().trim();
        String total = total_harga.getText().toString().trim();

        if(no_nota.isEmpty()){
            input_no_nota.setText("Tidak Ada");
        }
        if(tanggal.isEmpty()){
            input_tanggal.setText("Tidak Ada");
        }
        if(waktu.isEmpty()){
            input_waktu.setText("Tidak Ada");
        }
        if(kasir.isEmpty()){
            input_kasir.setText("Tidak Ada");
        }
        if(no_telp.isEmpty()){
            input_no_telp.setText("Tidak Ada");
        }
        if(alamat.isEmpty()){
            input_alamat.setText("Tidak Ada");
        }
        if(total.isEmpty()){
            total_harga.setText("Tidak Ada");
        }

        loadProducts();

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
                            IsiKonfirmasiKasirAdapter adapter = new IsiKonfirmasiKasirAdapter(isiHistoryPenjualan.this, productList);
                            recyclerView.setAdapter(adapter);

                            progressBar.setVisibility(View.INVISIBLE);
                            error.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);

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
                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(isiHistoryPenjualan.this).add(stringRequest);
    }
}
