package indonesia.konfeksi.com.androidkonfeksi.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import indonesia.konfeksi.com.androidkonfeksi.Interface.RecyclerViewClickListener;
import indonesia.konfeksi.com.androidkonfeksi.R;
import indonesia.konfeksi.com.androidkonfeksi.adapter.DialogRecyclerAdapter;
import indonesia.konfeksi.com.androidkonfeksi.adapter.PenjualanAdapter;
import indonesia.konfeksi.com.androidkonfeksi.adapter.PenjualanTambahAdapter;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductPenjualanBarang;
import indonesia.konfeksi.com.androidkonfeksi.konfigurasi.konfigurasi;

public class Penjualan extends AppCompatActivity implements RecyclerViewClickListener {
    private static final String TAG = "Penjualan";
    private NestedScrollView scroll_penjualan;

    // nota layout
    private TextView input_no_nota;
    private TextView input_tgl_nota;
    private TextView input_waktu;
    private TextView input_kasir;
    private Spinner input_pengambil_barang;
    List<Penjualan.ListKaryawan> listKaryawans = new ArrayList<>();
    private String kodeKaryawan;

    // pelanggan layout
    private Spinner input_nama_pelanggan;
    private TextView input_no_hp;
    private TextView input_alamat;
    private TextView input_info_lain;
    // pelanggan logic
    List<Penjualan.ListPelanggan> listPelanggan = new ArrayList<>();
    private String idPelanggan;
    private String TelpPelanggan;
    private String AlamatPelanggan;
    private String CatatanPelanggan;
    List<Penjualan.ListPotonganPelanggan> listPotonganPelanggan = new ArrayList<>();


    // prepare layout
    private LinearLayout isi;
    private ProgressBar loading;
    private TextView error;
    private TextView txErr;
    private Button cobaLagi;

    // Barang
    List<ProductPenjualanBarang> listBarang  = new ArrayList<>();
    private RecyclerView container_barang;
    private AlertDialog dialogPencarianBarang;
    private String kode;
    List<ProductPenjualanBarang> barangDitemukan = new ArrayList<>();
    private AlertDialog dialogBarangDitemukan;
    private EditText kodeBarangDialog;
    private TextView namaBarangDialog;
    private TextView hargaBarangDialog;
    private EditText qtyBarang;
    private TextView subTootal;
    ProductPenjualanBarang barangTerpilih = null;
    PenjualanTambahAdapter penjualanadapter;
    List<ProductPenjualanBarang> listBarangTepilih = new ArrayList<>();
    private CardView c_kirim_penjualan;
    private Button kirim_penjualan;
    private TextView total_biaya;
    private RecyclerView recyclerViewDialog;
    private Button tambah_pembelian;

    private ProgressDialog loadingg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan);
        scroll_penjualan = findViewById(R.id.scroll_penjualan);

        // prepare set layout
        isi = findViewById(R.id.isi);
        loading = findViewById(R.id.loading);
        error = findViewById(R.id.error);
        txErr = findViewById(R.id.err_ap);
        cobaLagi = findViewById(R.id.reload_ap);
        // prepare logic
        isi.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.VISIBLE);
        txErr.setVisibility(View.INVISIBLE);
        cobaLagi.setVisibility(View.INVISIBLE);

        // nota set layout
        input_no_nota = findViewById(R.id.input_no_nota);
        input_tgl_nota = findViewById(R.id.input_tgl_nota);
        input_waktu = findViewById(R.id.input_waktu);
        input_kasir = findViewById(R.id.input_kasir);
        input_pengambil_barang = findViewById(R.id.input_pengambil_barang);
        // nota logic
        input_tgl_nota.setText(setDate());
        input_waktu.setText(settime());
        getNota();
        input_kasir.setText(Objects.requireNonNull(getIntent().getExtras()).getString("NamaKaryawan"));
        getKaryawan();
        karyawanSpinner();

        // pelanggan set layout
        input_nama_pelanggan = findViewById(R.id.input_nama_pelanngan);
        input_no_hp = findViewById(R.id.input_no_hp);
        input_alamat = findViewById(R.id.input_alamat);
        input_info_lain = findViewById(R.id.input_info_lain);
        // pelanggan logic
        getPelanggan();
        getPotonganPelanggan();
        pelangganSpiner();



        // barang set layout
        container_barang = findViewById(R.id.recyclerView);
        tambah_pembelian = findViewById(R.id.tambah_pembelian);
        penjualanadapter = new PenjualanTambahAdapter(Penjualan.this, listBarangTepilih);
        container_barang.setLayoutManager(new LinearLayoutManager(Penjualan.this));
        container_barang.setAdapter(penjualanadapter);
        c_kirim_penjualan = findViewById(R.id.c_kirim_penjualan);
        kirim_penjualan = findViewById(R.id.kirim_penjualan);
        total_biaya = findViewById(R.id.total_biaya);
        //dialog cari barang
        setUpDialogTambahBarang();
        // barang logic
        getDataBarang();
        kirim_penjualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirimPenjualan();
            }
        });
    }

    // function for prepare
    public void reload(View v) {
        loading.setVisibility(View.VISIBLE);
        txErr.setVisibility(View.INVISIBLE);
        cobaLagi.setVisibility(View.INVISIBLE);
        container_barang.setVisibility(View.INVISIBLE);
        getNota();
        getKaryawan();

        getPelanggan();
        getPotonganPelanggan();

        getDataBarang();
    }

    // function for nota
    public void getNota(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.url_nota_penjualan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        input_no_nota.setText(response);
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
    public String setDate() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        return formatter.format(today);
    }
    public String settime(){
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        return formatter.format(today);
    }
    public void getKaryawan(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.url_karyawan_penjualan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray arrKaryawan = new JSONArray(response);
                            for(int i = 0; i < arrKaryawan.length(); i++){
                                JSONObject jsonKaryawan = arrKaryawan.getJSONObject(i);
                                String kodeKaryawan = jsonKaryawan.getString("kode_karyawan");
                                String namaKaryawan = jsonKaryawan.getString("nama");
                                listKaryawans.add(new ListKaryawan(namaKaryawan, kodeKaryawan));
                            }

                            ArrayAdapter<Penjualan.ListKaryawan> listKaryawanArrayAdapter = new ArrayAdapter<Penjualan.ListKaryawan>(
                                    Penjualan.this,
                                    android.R.layout.simple_spinner_dropdown_item, listKaryawans);
                            input_pengambil_barang.setAdapter(listKaryawanArrayAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error);
                        Toast.makeText(Penjualan.this, "Gagal Memuat Data Karyawan", Toast.LENGTH_LONG).show();
                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);
    }
    private static class ListKaryawan {
        public String nama;
        public Object kode;

        public ListKaryawan(String nama, Object kode) {
            this.nama = nama;
            this.kode = kode;
        }

        @Override
        public String toString() {
            return nama;
        }
    }
    private void karyawanSpinner(){
        input_pengambil_barang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Penjualan.ListKaryawan pelanggan = (Penjualan.ListKaryawan) parent.getItemAtPosition(position);
                kodeKaryawan = (String) pelanggan.kode;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // function for pelanggan
    public void getPelanggan(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.url_pelanggan_penjualan,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray arrPelanggan = new JSONArray(response);
                        for(int i = 0; i < arrPelanggan.length(); i++){
                            JSONObject jsonPelanggan = arrPelanggan.getJSONObject(i);
                            String namapelanggan = jsonPelanggan.getString("nama");
                            String idpelanggan = jsonPelanggan.getString("id_pelanggan");
                            String notelp = jsonPelanggan.getString("no_telp");
                            String alamat = jsonPelanggan.getString("alamat");
                            String catatan = jsonPelanggan.getString("catatan");
                            listPelanggan.add(new ListPelanggan(namapelanggan, idpelanggan, notelp, alamat, catatan));

                        }
                        ArrayAdapter<Penjualan.ListPelanggan> adapterSpinnerPelanggan = new ArrayAdapter<>(
                                Penjualan.this,
                                android.R.layout.simple_spinner_dropdown_item, listPelanggan);
                        input_nama_pelanggan.setAdapter(adapterSpinnerPelanggan);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "onErrorResponse: " + error);
                    Toast.makeText(Penjualan.this, "Gagal Memuat Data Karyawan", Toast.LENGTH_LONG).show();
                }
            });
        Volley.newRequestQueue(this).add(stringRequest);
    }
    private static class ListPelanggan {
        public String nama;
        public Object id;
        public String Telp;
        public String Alaamat;
        public String Caatatan;

        public ListPelanggan(String nama, Object id, String Telp, String Alaamat, String Caatatan) {
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
    private void getPotonganPelanggan(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.url_potongan_pelanggan_penjualan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray arrPotonganPelanggan = new JSONArray(response);
                            for(int i = 0; i < arrPotonganPelanggan.length(); i++){
                                JSONObject jsonPotonganPelanggan = arrPotonganPelanggan.getJSONObject(i);
                                String id_potongan = jsonPotonganPelanggan.getString("id");
                                String kode_pelanggan = jsonPotonganPelanggan.getString("kode_pelanggan");
                                String id_varian_harga = jsonPotonganPelanggan.getString("id_varian_harga");
                                String rekom_harga = jsonPotonganPelanggan.getString("rekom_harga");
                                String id_pelanggan= jsonPotonganPelanggan.getString("id_pelanggan");
                                listPotonganPelanggan.add(new ListPotonganPelanggan(id_potongan, kode_pelanggan, id_varian_harga, rekom_harga, id_pelanggan));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse potongan pelanggan: " + error);
                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);
    }
    private static class ListPotonganPelanggan {
        public String id;
        public String kode_pelanggan;
        public String id_varian_harga;
        public String rekom_harga;
        public String id_pelanggan;

        public ListPotonganPelanggan(String id,
                                    String kode_pelanggan,
                                    String id_varian_harga,
                                    String rekom_harga,
                                    String id_pelanggan) {
            this.id = id;
            this.kode_pelanggan = kode_pelanggan;
            this.id_varian_harga = id_varian_harga;
            this.rekom_harga = rekom_harga;
            this.id_pelanggan = id_pelanggan;
        }

        public String getId_pelanggan() {
            return id_pelanggan;
        }
        public String getId_varian_harga() { return  id_varian_harga; };
        public String getRekom_harga() { return  rekom_harga; }
    }
    private void pelangganSpiner(){
        input_nama_pelanggan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Penjualan.ListPelanggan pelanggan = (Penjualan.ListPelanggan) parent.getItemAtPosition(position);
                // Log.d(TAG, "onItemSelected: " + pelanggan.id + ", " + pelanggan.nama + ", " + pelanggan.Telp + ", " + pelanggan.Alaamat + ", " + pelanggan.Caatatan);
                idPelanggan = (String) pelanggan.id;
                TelpPelanggan = pelanggan.Telp;
                AlamatPelanggan = pelanggan.Alaamat;
                CatatanPelanggan = pelanggan.Caatatan;

                input_no_hp.setText(TelpPelanggan.isEmpty()? "Tidak Ada" : TelpPelanggan);
                input_alamat.setText(AlamatPelanggan.isEmpty()? "Tidak Ada" : AlamatPelanggan);
                input_info_lain.setText(CatatanPelanggan.isEmpty()? "Tidak Ada" : CatatanPelanggan);

                cekTopPelanggan(idPelanggan);
                gantiHargaBarang();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    void cekTopPelanggan(String idPelanggan){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.url_top_pelanggan_penjualan + "/" + idPelanggan,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

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
    void gantiHargaBarang(){
        for (int i = 0; i < listBarangTepilih.size(); i++){
            String potongan = setPotonganPelanggan(idPelanggan, listBarangTepilih.get(i).getIdVarianHarga());
            int hargaTotal = Integer.parseInt(listBarangTepilih.get(i).getHargaAwal()) - Integer.parseInt(potongan) ;
            listBarangTepilih.get(i).setHarga(Integer.toString(hargaTotal));
            listBarangTepilih.get(i).setSubTotal(listBarangTepilih.get(i).getQty() * hargaTotal);
        }
        penjualanadapter.notifyDataSetChanged();
        hitungTotal();
    }

    // function for barang
    void getDataBarang(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.url_barang_penjualan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res = new JSONObject(response);
                            JSONArray arrBarang = res.getJSONArray("datanya");
                            for(int i = 0; i < arrBarang.length(); i++){
                                JSONObject jsonBarang = arrBarang.getJSONObject(i);
                                ProductPenjualanBarang barang = new ProductPenjualanBarang(
                                        jsonBarang.getString("kode_barcode_varian"),
                                        jsonBarang.getString("kode_barang"),
                                        jsonBarang.getString("nama_barang"),
                                        jsonBarang.getString("satuan"),
                                        jsonBarang.getString("meter"),
                                        jsonBarang.getString("harga"), // harga awal
                                        jsonBarang.getString("harga"), // harga awal (nantinya dirubah harga setelah diskon)
                                        jsonBarang.getString("warna"),
                                        jsonBarang.getString("id_varian_harga"),
                                        jsonBarang.getString("id_barang"),
                                        0,
                                        0,
                                        jsonBarang.getString("stok_gudang")
                                );
                                listBarang.add(barang);
                            }

                            isi.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.INVISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "onResponse: " + e );
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.INVISIBLE);
                        txErr.setVisibility(View.VISIBLE);
                        cobaLagi.setVisibility(View.VISIBLE);
                        Log.d(TAG, "onErrorResponse: " + error);
                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);
    }
    void setUpDialogTambahBarang(){
        dialogPencarianBarang = new AlertDialog.Builder(Penjualan.this).create();
        LayoutInflater inflaterTambahBarang = getLayoutInflater();
        View dialogViewTambahBarang = inflaterTambahBarang.inflate(R.layout.form_penjualan, null);
        dialogPencarianBarang.setView(dialogViewTambahBarang);
        dialogPencarianBarang.setCancelable(true);

        kodeBarangDialog = dialogViewTambahBarang.findViewById(R.id.kodeBarang);
        namaBarangDialog = dialogViewTambahBarang.findViewById(R.id.namaBarang);
        hargaBarangDialog = dialogViewTambahBarang.findViewById(R.id.hargaBarang);
        qtyBarang = dialogViewTambahBarang.findViewById(R.id.qtyBarang);
        subTootal = dialogViewTambahBarang.findViewById(R.id.subTotal);

        tambah_pembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPencarianBarang.show();
                kodeBarangDialog.setText("");
                namaBarangDialog.setText("");
                hargaBarangDialog.setText("");
                qtyBarang.setText("");
                subTootal.setText("");
                kodeBarangDialog.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        kodeBarangDialog.post(new Runnable() {
                            @Override
                            public void run() {
                                InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputMethodManager.showSoftInput(kodeBarangDialog, InputMethodManager.SHOW_IMPLICIT);
                            }
                        });
                    }
                });
                kodeBarangDialog.requestFocus();
            }
        });

        dialogPencarianBarang.setButton(Dialog.BUTTON_POSITIVE,"TAMBAH", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (barangTerpilih == null){
                    Toast.makeText(getApplicationContext(), "Tidak Ada Barang Yang Di Pilih", Toast.LENGTH_SHORT).show();
                }else {
                    error.setVisibility(View.GONE);
                    container_barang.setVisibility(View.VISIBLE);
                    listBarangTepilih.add(barangTerpilih);
                    penjualanadapter.notifyDataSetChanged();
                    c_kirim_penjualan.setVisibility(View.VISIBLE);
                    barangTerpilih = null;
                    scroll_penjualan.post(new Runnable() {
                        @Override
                        public void run() {
                            scroll_penjualan.fullScroll(View.FOCUS_DOWN);
                        }
                    });
                    hitungTotal();
                }
            }
        });
        dialogPencarianBarang.setButton(Dialog.BUTTON_NEGATIVE,"CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        kodeBarangDialog.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    cariBarang();
                    return true;
                }
                return false;
            }
        });

        qtyBarang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s == null) return;
                if(s.length() != 0){
                    int qty = Integer.valueOf(s.toString());
                    int subTotal = Integer.parseInt(barangTerpilih.getHarga()) * qty;
                    barangTerpilih.setQty(qty);
                    barangTerpilih.setSubTotal(subTotal);
                    subTootal.setText("Rp. " + subTotal);

                }else{
                    subTootal.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    void cariBarang(){
        kode = kodeBarangDialog.getText().toString().trim();
        kode = kode.replace(" ", "");
        barangDitemukan.clear();

        for(int i = 0; i < listBarang.size(); i++){
            if(listBarang.get(i).getKodeBarcodeVarian().equalsIgnoreCase(kode)
                    || listBarang.get(i).getKodeBarang().equalsIgnoreCase(kode)
                    ){
                barangDitemukan.add(listBarang.get(i));
            }
        }

        if(barangDitemukan.size() > 1){
            dialogBarangDitemukan = new AlertDialog.Builder(Penjualan.this).create();
            LayoutInflater inflaterBarangDitemukan = getLayoutInflater();
            View dialogViewBarangDitemukan = inflaterBarangDitemukan.inflate(R.layout.recycler_dialog, null);
            dialogBarangDitemukan.setView(dialogViewBarangDitemukan);
            dialogBarangDitemukan.setCancelable(true);

            recyclerViewDialog =  dialogViewBarangDitemukan.findViewById(R.id.recyclerViewDialog);
            recyclerViewDialog.setLayoutManager(new LinearLayoutManager(Penjualan.this));

            dialogBarangDitemukan.show();

            DialogRecyclerAdapter adapter = new DialogRecyclerAdapter(Penjualan.this, barangDitemukan, Penjualan.this);
            recyclerViewDialog.setAdapter(adapter);
        }else{
            if(barangDitemukan.size() == 1){
                namaBarangDialog.setText(
                        barangDitemukan.get(0).getNamaBarang() + " " +
                                barangDitemukan.get(0).getWarna() + " " +
                                barangDitemukan.get(0).getSatuan() + " " +
                                "(" + barangDitemukan.get(0).getMeter() + " Pcs)");
                String hargaPotongan = setPotonganPelanggan(idPelanggan, barangDitemukan.get(0).getIdVarianHarga());
                String textHarga = Integer.parseInt(hargaPotongan) > 0 ? "Rp. " + barangDitemukan.get(0).getHarga() + ", Disc: <b> Rp. " + hargaPotongan + "</b>" : barangDitemukan.get(0).getHarga();
                hargaBarangDialog.setText(Html.fromHtml(textHarga));

                barangTerpilih = new ProductPenjualanBarang(
                        barangDitemukan.get(0).getKodeBarcodeVarian(),
                        barangDitemukan.get(0).getKodeBarang(),
                        barangDitemukan.get(0).getNamaBarang(),
                        barangDitemukan.get(0).getSatuan(),
                        barangDitemukan.get(0).getMeter(),
                        barangDitemukan.get(0).getHargaAwal(),
                        Integer.toString(Integer.parseInt(barangDitemukan.get(0).getHarga()) - Integer.parseInt(hargaPotongan)),
                        barangDitemukan.get(0).getWarna(),
                        barangDitemukan.get(0).getIdVarianHarga(),
                        barangDitemukan.get(0).getIdBarang(),
                        0,
                        0,
                        barangDitemukan.get(0).getStokGudang()
                );

                qtyBarang.setText("1");
                qtyBarang.setSelection(qtyBarang.getText().length());
            }
        }
    }
    public void recyclerViewListClicked(View v, int position){
        namaBarangDialog.setText(
                barangDitemukan.get(position).getNamaBarang() + " " +
                barangDitemukan.get(position).getWarna() + " " +
                barangDitemukan.get(position).getSatuan() + " " +
                "(" + barangDitemukan.get(position).getMeter() + " Pcs)");
        String hargaPotongan = setPotonganPelanggan(idPelanggan, barangDitemukan.get(position).getIdVarianHarga());
        String textHarga = Integer.parseInt(hargaPotongan) > 0 ? "Rp. " + barangDitemukan.get(position).getHarga() + ", Disc: <b> Rp. " + hargaPotongan + "</b>" : barangDitemukan.get(position).getHarga();
        hargaBarangDialog.setText(Html.fromHtml(textHarga));
        barangTerpilih = new ProductPenjualanBarang(
                barangDitemukan.get(position).getKodeBarcodeVarian(),
                barangDitemukan.get(position).getKodeBarang(),
                barangDitemukan.get(position).getNamaBarang(),
                barangDitemukan.get(position).getSatuan(),
                barangDitemukan.get(position).getMeter(),
                barangDitemukan.get(position).getHargaAwal(),
                Integer.toString(Integer.parseInt(barangDitemukan.get(position).getHarga()) - Integer.parseInt(hargaPotongan)),
                barangDitemukan.get(position).getWarna(),
                barangDitemukan.get(position).getIdVarianHarga(),
                barangDitemukan.get(position).getIdBarang(),
                0, 0,
                barangDitemukan.get(position).getStokGudang()
        );

        qtyBarang.setText("1");
        qtyBarang.setSelection(qtyBarang.getText().length());

        dialogBarangDitemukan.dismiss();
    }
    private String setPotonganPelanggan(String idPelanggan, String idVariaHarga){
        int potongan = 0;
        for (int i = 0; i < listPotonganPelanggan.size(); i++){
            if(listPotonganPelanggan.get(i).getId_pelanggan().equals(idPelanggan) && listPotonganPelanggan.get(i).getId_varian_harga().equals(idVariaHarga)){
                potongan = Integer.parseInt(listPotonganPelanggan.get(i).getRekom_harga());
            }
        }
        return Integer.toString(potongan);
    }
    void hitungTotal(){
        int total = 0;
        for (int i = 0; i < listBarangTepilih.size(); i++){
            total += listBarangTepilih.get(i).getSubTotal();
        }
        total_biaya.setText(Integer.toString(total));
    }
    void kirimPenjualan(){
        loadingg = ProgressDialog.show(Penjualan.this, "Mengirim Data...", "Mohon Tunggu...", false, false);

        if(getId_Karyawan().equals("null")){
            loadingg.dismiss();
            Toast.makeText(Penjualan.this, "Terjadi kesalahan Id Karyawan tidak tersedia", Toast.LENGTH_LONG).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, konfigurasi.url_simpan_penjualan,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Toast.makeText(Penjualan.this, response, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: " + response);
                    try {
                        JSONObject objResponse = new JSONObject(response);
                        Log.d(TAG, objResponse.toString(4));
                        JSONObject jsonRes = new JSONObject(response);
                        int sukses = Integer.parseInt(jsonRes.getString("success"));
                        if(sukses == 1){
                            //Toast.makeText(Penjualan.this, "Penjualan Berhasil Disimpan.", Toast.LENGTH_LONG).show();
                            AlertDialog alertDialog = new AlertDialog.Builder(Penjualan.this).create();
                            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            alertDialog.setMessage("Penjualan Berhasil Disimpan");
                            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            finish();
                                        }
                                    });
                            alertDialog.show();
                        }else{
                            Toast.makeText(Penjualan.this, "Gagal Melakukan Penjualan.", Toast.LENGTH_LONG).show();
                        }

                    } catch (Throwable t) {
                        Log.e(TAG, "Could not parse malformed JSON");
                    }

                    loadingg.dismiss();
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "onErrorResponse: " + error);
                    loadingg.dismiss();
                    Toast.makeText(Penjualan.this, "Terjadi Kesalahan. Periksa Koneksi Anda", Toast.LENGTH_LONG).show();
                }
            }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {               Map<String, String> params = new HashMap<>();
                params.put("dataFrom" , "dariandroid");
                params.put("id_pelanggan", idPelanggan);
                params.put("id_karyawan", getId_Karyawan());
                params.put("no_nota", input_no_nota.getText().toString());
                params.put("Total", total_biaya.getText().toString());
                params.put("pengambil_barang", kodeKaryawan);

                for(int i = 0; i < listBarangTepilih.size(); i++) {
                    params.put("id_barang[]", listBarangTepilih.get(i).getIdBarang());
                    params.put("id_varian_harga[]", listBarangTepilih.get(i).getIdVarianHarga());
                    params.put("harga_jual[]", listBarangTepilih.get(i).getHarga());
                    params.put("jumlah_beli[]", String.valueOf(listBarangTepilih.get(i).getQty()));
                    params.put("sub_total[]", String.valueOf(listBarangTepilih.get(i).getSubTotal()));
                }
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
}