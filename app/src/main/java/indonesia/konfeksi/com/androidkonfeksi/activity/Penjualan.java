package indonesia.konfeksi.com.androidkonfeksi.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import indonesia.konfeksi.com.androidkonfeksi.R;
import indonesia.konfeksi.com.androidkonfeksi.adapter.PenjualanAdapter;
import indonesia.konfeksi.com.androidkonfeksi.adapter.ProductsAdapter;
import indonesia.konfeksi.com.androidkonfeksi.json.Product;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductPenjualan;
import indonesia.konfeksi.com.androidkonfeksi.konfigurasi.konfigurasi;

public class Penjualan extends AppCompatActivity {
    private static final String TAG = "Penjualan";
    private String date;
    private String time;
    private String idPelanggan;
    private String TelpPelanggan;
    private String AlamatPelanggan;
    private String CatatanPelanggan;
    private String mPostKeyNama = null;
    private Spinner input_nama_pelanggan;
    private Spinner kodeeBarang;
    private TextView input_tgl_nota;
    private TextView input_waktu;
    private TextView input_no_nota;
    private TextView input_kasir;
    private TextView input_no_hp;
    private TextView input_alamat;
    private TextView input_info_lain;
    private RecyclerView recyclerView;
    private Button tambah_pembelian;
    private AlertDialog.Builder dialog;
    private LayoutInflater inflater;
    private View dialogView;
    private PenjualanAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan);

        input_tgl_nota = findViewById(R.id.input_tgl_nota);
        input_waktu = findViewById(R.id.input_waktu);
        input_no_nota = findViewById(R.id.input_no_nota);
        input_kasir = findViewById(R.id.input_kasir);
        input_nama_pelanggan = findViewById(R.id.input_nama_pelanngan);
        input_no_hp = findViewById(R.id.input_no_hp);
        input_alamat = findViewById(R.id.input_alamat);
        input_info_lain = findViewById(R.id.input_info_lain);
        tambah_pembelian = findViewById(R.id.tambah_pembelian);
        mPostKeyNama = Objects.requireNonNull(getIntent().getExtras()).getString("NamaKaryawan");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        dialog = new AlertDialog.Builder(Penjualan.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_penjualan, null);
        dialog.setView(dialogView);

        kodeeBarang = dialogView.findViewById(R.id.kodeBarang);
        TextView namaaBarang = dialogView.findViewById(R.id.namaBarang);
        TextView hargaaBarang = dialogView.findViewById(R.id.hargaBarang);
        TextView qtyBarang = dialogView.findViewById(R.id.qtyBarang);
        TextView subTootal = dialogView.findViewById(R.id.subTotal);

        ambilbarang();

        ArrayList<String> penjualan = new ArrayList<>();
        penjualan.add("001");
        penjualan.add("002");
        penjualan.add("003");
        penjualan.add("004");
        penjualan.add("005");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PenjualanAdapter(this, penjualan);
        recyclerView.setAdapter(adapter);

        setDate();
        settime();

        input_tgl_nota.setText(date);
        input_waktu.setText(time);

        if (mPostKeyNama != null) {
            input_kasir.setText(mPostKeyNama);
        }else {
            input_kasir.setText("");
        }

        ambilNoNotaPenjualan();

//        kodeeBarang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Penjualan.StringWithTagg barang = (Penjualan.StringWithTagg) parent.getItemAtPosition(position);
//                Log.d(TAG, "onItemSelected: " + barang.id + ", " + barang.string + ", " + barang.Telp + ", " + barang.Alaamat + ", " + barang.Caatatan);
//                idPelanggan = (String) barang.id;
//                TelpPelanggan = (String) barang.Telp;
//                AlamatPelanggan = (String) barang.Alaamat;
//                CatatanPelanggan = (String) barang.Caatatan;
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        input_nama_pelanggan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Penjualan.StringWithTag pelanggan = (Penjualan.StringWithTag) parent.getItemAtPosition(position);
                Log.d(TAG, "onItemSelected: " + pelanggan.id + ", " + pelanggan.nama + ", " + pelanggan.Telp + ", " + pelanggan.Alaamat + ", " + pelanggan.Caatatan);
                idPelanggan = (String) pelanggan.id;
                TelpPelanggan = (String) pelanggan.Telp;
                AlamatPelanggan = (String) pelanggan.Alaamat;
                CatatanPelanggan = (String) pelanggan.Caatatan;

                input_no_hp.setText(TelpPelanggan);
                input_alamat.setText(AlamatPelanggan);

                if (CatatanPelanggan != null){
                    input_info_lain.setText(CatatanPelanggan);
                }else {
                    input_info_lain.setText("Tidak Ada Info Lain");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tambah_pembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.setPositiveButton("TAMBAH", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    List<Penjualan.StringWithTagg> barangName = new ArrayList<Penjualan.StringWithTagg>();
    private void ambilbarang() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.URL_GET_AMBIL_BARANG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray arrSupplier = obj.getJSONArray("datanya");
                            for(int i = 0; i < arrSupplier.length(); i++){
                                JSONObject supplierJson = arrSupplier.getJSONObject(i);
                                String id_barang = supplierJson.getString("id_barang");
                                String kode_barang = supplierJson.getString("kode_barang");
                                String nama_barang = supplierJson.getString("nama_barang");
                                String diskon_persen = supplierJson.getString("diskon_persen");
                                String diskon_rupiah = supplierJson.getString("diskon_rupiah");
                                String id_varian_harga = supplierJson.getString("id_varian_harga");
                                String ukuran = supplierJson.getString("ukuran");
                                String meter = supplierJson.getString("meter");
                                String warna = supplierJson.getString("warna");
                                String stok_jual = supplierJson.getString("stok_jual");
                                String harga = supplierJson.getString("harga");
                                barangName.add(new StringWithTagg(id_barang, kode_barang, nama_barang, diskon_persen, diskon_rupiah, id_varian_harga, ukuran, meter, warna, stok_jual, harga));
                            }

                            ArrayAdapter<Penjualan.StringWithTagg> adapterSpinnerBarang = new ArrayAdapter<StringWithTagg>(
                                    Penjualan.this,
                                    android.R.layout.simple_spinner_dropdown_item, barangName);
                            kodeeBarang.setAdapter(adapterSpinnerBarang);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, "onResponse: " + e);
                       ; }
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

    List<Penjualan.StringWithTag> pelangganName = new ArrayList<Penjualan.StringWithTag>();
    private void ambilNoNotaPenjualan() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.URL_GET_NONOTAPENJUALAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            String noNota = obj.getString("nota");
                            input_no_nota.setText(noNota);

                            JSONArray arrSupplier = obj.getJSONArray("pelanggan");
                            for(int i = 0; i < arrSupplier.length(); i++){
                                JSONObject supplierJson = arrSupplier.getJSONObject(i);
                                String namapelanggan = supplierJson.getString("nama");
                                String idpelanggan = supplierJson.getString("id_pelanggan");
                                String notelp = supplierJson.getString("no_telp");
                                String alamat = supplierJson.getString("alamat");
                                String catatan = supplierJson.getString("catatan");
                                pelangganName.add(new StringWithTag(namapelanggan, idpelanggan, notelp, alamat, catatan));
                            }

                            ArrayAdapter<Penjualan.StringWithTag> adapterSpinnerPelanggan = new ArrayAdapter<Penjualan.StringWithTag>(
                                    Penjualan.this,
                                    android.R.layout.simple_spinner_dropdown_item, pelangganName);
                            input_nama_pelanggan.setAdapter(adapterSpinnerPelanggan);

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
    public void setDate() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        date = formatter.format(today);
    }

    public void settime(){
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        time = formatter.format(today);
    }

    private static class StringWithTag {
        public String nama;
        public Object id;
        public String Telp;
        public String Alaamat;
        public String Caatatan;

        public StringWithTag(String nama, Object id, String Telp, String Alaamat, String Caatatan) {
            this.nama = nama;
            this.id = id;
            this.Telp = Telp;
            this.Alaamat = Alaamat;
            this.Caatatan = Caatatan;
        }

        @Override
        public String toString() {
            return nama;
        }
    }

    private static class StringWithTagg {
        public Object idBarang;
        public String kodeBarang;
        public String namaBarang;
        public String diskonPersen;
        public String diskonRupiah;
        public String idVarianHarga;
        public String ukuran;
        public String meter;
        public String warna;
        public String stokJual;
        public String harga;

        public StringWithTagg(Object idBarang, String kodeBarang, String namaBarang, String diskonPersen, String diskonRupiah, String idVarianHarga, String ukuran, String meter, String warna, String stokJual, String harga) {
            this.idBarang = idBarang;
            this.kodeBarang = kodeBarang;
            this.namaBarang = namaBarang;
            this.diskonPersen = diskonPersen;
            this.diskonRupiah = diskonRupiah;
            this.idVarianHarga = idVarianHarga;
            this.ukuran = ukuran;
            this.meter = meter;
            this.warna = warna;
            this.stokJual = stokJual;
            this.harga = harga;
        }

        @Override
        public String toString() {
            return namaBarang;
        }
    }
}