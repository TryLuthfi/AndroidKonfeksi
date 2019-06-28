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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import indonesia.konfeksi.com.androidkonfeksi.R;
import indonesia.konfeksi.com.androidkonfeksi.adapter.ProductsAdapter;
import indonesia.konfeksi.com.androidkonfeksi.adapter.VProductsAdapter;
import indonesia.konfeksi.com.androidkonfeksi.json.Product;
import indonesia.konfeksi.com.androidkonfeksi.json.VProduct;
import indonesia.konfeksi.com.androidkonfeksi.konfigurasi.konfigurasi;

public class IsiBarang extends AppCompatActivity {
    private RecyclerView recyclerView;
    List<VProduct> productList;
    private ProgressBar progressBar;

    private TextView kode_barang;
    private TextView nama_barang;
    private TextView diskon_rp;
    private String mPostIdBarang = null;
    private String mPostKodeBarang = null;
    private String mPostKodeBarcode = null;
    private String mPostNamaBarang = null;
    private String mPostDiskonRupiah = null;
    private String mPostHargaBarang = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_barang);

        kode_barang  = findViewById(R.id.kode_barang);
        nama_barang  = findViewById(R.id.nama_barang);
        diskon_rp  = findViewById(R.id.diskon_rp);

        mPostIdBarang = getIntent().getExtras().getString("id_barang");
        mPostKodeBarang = getIntent().getExtras().getString("kode_barang");
        mPostKodeBarcode = getIntent().getExtras().getString("kode_barang");
        mPostNamaBarang = getIntent().getExtras().getString("nama_barang");
        mPostDiskonRupiah = getIntent().getExtras().getString("diskon_rupiah");
        mPostHargaBarang = getIntent().getExtras().getString("harga_barang");

        kode_barang.setText(mPostKodeBarang);
        nama_barang.setText(mPostNamaBarang);
        diskon_rp.setText(mPostDiskonRupiah);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);

        productList = new ArrayList<>();
        loadProducts();
    }

    private void loadProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.URL_GET_BARANG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                if(mPostIdBarang.equals(product.getString("id_barang"))) {
                                    productList.add(new VProduct(
                                            product.getString("id_barang"),
                                            product.getString("kode_barang"),
                                            product.getString("kode_barcode"),
                                            product.getString("nama_barang"),
                                            product.getString("diskon_persen"),
                                            product.getString("diskon_rupiah"),
                                            product.getString("ukuran"),
                                            product.getString("meter"),
                                            product.getString("warna"),
                                            product.getString("stok_jual"),
                                            product.getString("date_input"),
                                            product.getString("date_edit"),
                                            product.getString("harga_barang")
                                    ));
                                }
                            }

                            VProductsAdapter adapter = new VProductsAdapter(IsiBarang.this, productList);
                            recyclerView.setAdapter(adapter);

                            progressBar.setVisibility(View.INVISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }
}
