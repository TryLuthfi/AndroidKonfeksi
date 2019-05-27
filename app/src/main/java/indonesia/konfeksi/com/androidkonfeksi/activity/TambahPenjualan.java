package indonesia.konfeksi.com.androidkonfeksi.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.Objects;

import indonesia.konfeksi.com.androidkonfeksi.R;

public class TambahPenjualan extends AppCompatActivity {
    private Spinner ttoopp;
    private Spinner metode_pembayaran;
    private Spinner berapa_kali_pembayaran;
    private EditText search_top;
    private EditText customer;
    private EditText no_faktur;
    private EditText pegawai;
    private EditText no_order;
    private EditText staff;
    private EditText diskon_persen;
    private EditText diskon_rp;
    private EditText search_bot;
    private LinearLayout tambah_order;
    private LinearLayout hold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_penjualan);

        ttoopp = findViewById(R.id.ttoopp);
        metode_pembayaran = findViewById(R.id.ttoopp);
        berapa_kali_pembayaran = findViewById(R.id.ttoopp);
        search_top = findViewById(R.id.search_top);
        customer = findViewById(R.id.customer);
        no_faktur = findViewById(R.id.no_faktur);
        pegawai = findViewById(R.id.pegawai);
        no_order = findViewById(R.id.no_order);
        staff = findViewById(R.id.staff);
        diskon_persen = findViewById(R.id.diskon_persen);
        diskon_rp = findViewById(R.id.diskon_rp);
        search_bot = findViewById(R.id.search_bot);
        tambah_order = findViewById(R.id.tambah_order);
        hold = findViewById(R.id.hold);

        tambah_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(TambahPenjualan.this);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setContentView(R.layout.tambahpenjualan_popup_tambahorder);
                LinearLayout scan_barcode = dialog.findViewById(R.id.scan_barcode);
                LinearLayout tambah = dialog.findViewById(R.id.tambah);
                Spinner nama_barang = dialog.findViewById(R.id.nama_barang);
                Spinner satuan = dialog.findViewById(R.id.satuan);
                EditText jumlah = dialog.findViewById(R.id.jumlah);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
    }
}
