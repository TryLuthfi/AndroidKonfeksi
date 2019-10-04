package indonesia.konfeksi.com.androidkonfeksi.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.NestedScrollView;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import indonesia.konfeksi.com.androidkonfeksi.Interface.RecyclerViewClickListener;
import indonesia.konfeksi.com.androidkonfeksi.R;
import indonesia.konfeksi.com.androidkonfeksi.adapter.ReturnRecyclerAdapter;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductHistoryPenjualan;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductIsiKonfirmasiKasir;
import indonesia.konfeksi.com.androidkonfeksi.konfigurasi.konfigurasi;
import indonesia.konfeksi.com.androidkonfeksi.request.RequestHandler;

public class Return extends AppCompatActivity implements RecyclerViewClickListener {

    private static final String TAG = "Return";

    private AlertDialog dialog;
    private LayoutInflater inflater;
    private View dialogView;

    private EditText cariBarang;

    List<ProductHistoryPenjualan> productBarang;
    List<ProductHistoryPenjualan> barangPilih;
    List<ProductIsiKonfirmasiKasir> productList;
    List<ProductIsiKonfirmasiKasir> productDelete;
    ProductIsiKonfirmasiKasir isiBarang;

    private String kode;
    private String id_detail_penjualan;

    private ProgressDialog loading2;
    private ProgressDialog loadingg;;

    private TextView input_no_nota;
    private TextView input_tgl_nota;
    private TextView input_waktu;
    private TextView input_kasir;
    private TextView input_no_hp;
    private TextView input_alamat;
    private TextView total_harga;
    private TextView error ;

    private NumberFormat formatRupiah;

    private NestedScrollView linear;

    private ProgressBar progressBar;

    private RecyclerView recyclerView;

    private String idListener;
    private RelativeLayout delete;

    private String jumlah_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);

        input_no_nota = findViewById(R.id.input_no_nota);
        input_tgl_nota = findViewById(R.id.input_tgl_nota);
        input_waktu = findViewById(R.id.input_waktu);
        productList = new ArrayList<>();
        productDelete = new ArrayList<>();

        progressBar = findViewById(R.id.progressbar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Return.this));
        input_kasir = findViewById(R.id.input_kasir);
        input_no_hp = findViewById(R.id.input_no_hp);
        error = findViewById(R.id.error);
        input_alamat = findViewById(R.id.input_alamat);
        total_harga = findViewById(R.id.total_harga);
        dialog = new AlertDialog.Builder(Return.this).create();
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_return, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);
        cariBarang = dialogView.findViewById(R.id.cariBarang);
        dialog.show();
        productBarang = new ArrayList<>();
        linear = findViewById(R.id.linear);
        delete = findViewById(R.id.delete);

        delete.setVisibility(View.INVISIBLE);

        Locale localeID = new Locale("in", "ID");

        formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        ambilBarang();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                updateData();
                tambahBarangPembelian();

            }
        });

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

                        input_no_nota.setText(productBarang.get(i).getNo_nota());
                        input_tgl_nota.setText(productBarang.get(i).getDate());
                        input_waktu.setText(productBarang.get(i).getTime());
                        input_kasir.setText(productBarang.get(i).getNama_karyawan());
                        input_no_hp.setText(productBarang.get(i).getNo_telp());
                        input_alamat.setText(productBarang.get(i).getAlamat());
                        double hargabarang = Double.parseDouble(productBarang.get(i).getTotal_harga());
                        total_harga.setText(formatRupiah.format((double)hargabarang));

                        linear.setVisibility(View.VISIBLE);

                        String no_nota = input_no_nota.getText().toString().trim();
                        String tanggal = input_tgl_nota.getText().toString().trim();
                        String waktu = input_waktu.getText().toString().trim();
                        String kasir = input_kasir.getText().toString().trim();
                        String no_telp = input_no_hp.getText().toString().trim();
                        String alamat = input_alamat.getText().toString().trim();
                        String total = total_harga.getText().toString().trim();

                        if(no_nota.isEmpty()){
                            input_no_nota.setText("Tidak Ada");
                        }
                        if(tanggal.isEmpty()){
                            input_tgl_nota.setText("Tidak Ada");
                        }
                        if(waktu.isEmpty()){
                            input_waktu.setText("Tidak Ada");
                        }
                        if(kasir.isEmpty()){
                            input_kasir.setText("Tidak Ada");
                        }
                        if(no_telp.isEmpty()){
                            input_no_hp.setText("Tidak Ada");
                        }
                        if(alamat.isEmpty()){
                            input_alamat.setText("Tidak Ada");
                        }
                        if(total.isEmpty()){
                            total_harga.setText("Tidak Ada");
                        }

                        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.URL_DETAIL_KONFIRMASI_KASIR+productBarang.get(i).getId_penjualan(),
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject obj = new JSONObject(response);

                                            JSONArray array = obj.getJSONArray("detail");

                                            //traversing through all the object
                                            for (int i = 0; i < array.length(); i++) {
                                                JSONObject product = array.getJSONObject(i);

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

                                            //creating adapter object and Xsetting it to recyclerview
                                            ReturnRecyclerAdapter adapter = new ReturnRecyclerAdapter(Return.this, productList, Return.this);
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
                        Volley.newRequestQueue(Return.this).add(stringRequest);

                        dialog.dismiss();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void ambilBarang(){
        loading2 = ProgressDialog.show(Return.this, "Updating...", "Mohon Tunggu...", false, false);
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
                                loading2.dismiss();
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
        final Intent intent = new Intent(getApplicationContext(), DashBoard.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        Toast.makeText(this, productList.get(position).getId_detail_penjualan(), Toast.LENGTH_SHORT).show();
        delete.setVisibility(View.VISIBLE);
        id_detail_penjualan = productList.get(position).getId_detail_penjualan();
        jumlah_data = String.valueOf(productList.size());
        isiBarang = productList.get(position);
        productDelete.add(isiBarang);
    }

//    private void updateData() {
//
//        class AddData extends AsyncTask<Void, Void, String> {
//
//            ProgressDialog loading;
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                loading = ProgressDialog.show(Return.this, "Menghapus Data...", "Mohon Tunggu...", false, false);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                loading.dismiss();
//                Toast.makeText(Return.this, s, Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            protected String doInBackground(Void... v) {
//
//                HashMap<String, String> params = new HashMap<>();
////                for(int i = 0; i < productDelete.size(); i++) {
////                    params.put("id_detail_penjualan["+i+"]", productDelete.get(0).getId_detail_penjualan());
////                }
//                params.put("id_detail_penjualan", id_detail_penjualan);
//
//                RequestHandler rh = new RequestHandler();
//                String res = rh.sendPostRequest(konfigurasi.URL_UPDATE_STATUS, params);
//                return res;
//            }
//        }
//
//        AddData ae = new AddData();
//        ae.execute();
//    }

    public void tambahBarangPembelian(){
        loadingg = ProgressDialog.show(Return.this, "Menambah Data...", "Mohon Tunggu...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, konfigurasi.URL_UPDATE_STATUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//                            JSONObject resObj = new JSONObject(response);
//                            if(resObj.getString("success").equals("true")){
                            if (response.contains(konfigurasi.LOGIN_SUCCESS)) {
                                loadingg.dismiss();
//                                Log.d(TAG, "onResponse123: " + resObj.toString());
                                Toast.makeText(Return.this, "Berhasil Menambah Data", Toast.LENGTH_SHORT).show();
                            }else{
                                loadingg.dismiss();
//                                Log.d(TAG, "onResponse false: " + resObj.toString());
//                                Toast.makeText(Return.this, resObj.getString("message"), Toast.LENGTH_LONG).show();
                                Toast.makeText(Return.this, "eror", Toast.LENGTH_LONG).show();
                            }
                        } catch (Throwable t) {
                            loadingg.dismiss();
                            Log.d("My App", "Could not parse malformed JSON: \"" + response + "\"");
                        }
                    }
    },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error);
                        loadingg.dismiss();
                        Toast.makeText(Return.this, "Server Tidak Terjangkau", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                for(int i = 0; i < productDelete.size(); i++) {
//                    params.put("id_detail_penjualan", productDelete.get(0).getId_detail_penjualan());
                    params.put("id_detail_penjualan", id_detail_penjualan);
                }
                params.put("jumlah_data", jumlah_data);

                Log.d(TAG, "getParams: " + params.toString());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }
}


