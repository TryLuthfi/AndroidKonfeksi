package indonesia.konfeksi.com.androidkonfeksi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import indonesia.konfeksi.com.androidkonfeksi.R;

public class isiHistoryPembelian extends AppCompatActivity {

    private String mPostNoFaktur = null;
    private String mPostNoNota = null;
    private String mPostNamaKaryawan = null;
    private String mPostNamaSupplier = null;
    private String mPostDate = null;
    private String mPostTime = null;
    private String mPostTotalHarga = null;
    private String mPostBiaya = null;
    private TextView nofaktur;
    private TextView noNota;
    private TextView namaKaryawan;
    private TextView namaSupplier;
    private TextView date;
    private TextView time;
    private TextView totalHarga;
    private TextView biaya;
    private NumberFormat formatRupiah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_history_pembelian);

        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        mPostNoFaktur = getIntent().getExtras().getString("no_faktur");
        mPostNoNota = getIntent().getExtras().getString("no_nota");
        mPostNamaKaryawan = getIntent().getExtras().getString("nama_karyawan");
        mPostNamaSupplier = getIntent().getExtras().getString("nama_supplier");
        mPostDate = getIntent().getExtras().getString("date");
        mPostTime = getIntent().getExtras().getString("time");
        mPostTotalHarga = getIntent().getExtras().getString("total_harga");
        mPostBiaya = getIntent().getExtras().getString("biaya");

        nofaktur = findViewById(R.id.id_nofaktur);
        noNota = findViewById(R.id.id_nonota);
        namaKaryawan = findViewById(R.id.id_namakaryawan);
        namaSupplier = findViewById(R.id.id_namasupplier);
        date = findViewById(R.id.id_date);
        time = findViewById(R.id.id_time);
        totalHarga = findViewById(R.id.id_totalharga);
        biaya = findViewById(R.id.id_biaya);

        double totalhargaD = Double.parseDouble(mPostTotalHarga);
        double biayaD = Double.parseDouble(mPostBiaya);

        nofaktur.setText(mPostNoFaktur);
        noNota.setText(mPostNoNota);
        namaKaryawan.setText(mPostNamaKaryawan);
        namaSupplier.setText(mPostNamaSupplier);
        date.setText(mPostDate);
        time.setText(mPostTime);
        totalHarga.setText(formatRupiah.format((double)totalhargaD));
        biaya.setText(formatRupiah.format((double)biayaD));

        String no_faktur = nofaktur.getText().toString().trim();
        String no_nota = noNota.getText().toString().trim();
        String nama_karyawan = namaKaryawan.getText().toString().trim();
        String nama_supplier = namaSupplier.getText().toString().trim();
        String datee = date.getText().toString().trim();
        String timee = time.getText().toString().trim();
        String total_harga = totalHarga.getText().toString().trim();
        String biayaa = biaya.getText().toString().trim();

        if (no_faktur.isEmpty()){
            nofaktur.setText("Tidak Ada");
        }

        if (no_nota.isEmpty()){
            noNota.setText("Tidak Ada");
        }

        if (nama_karyawan.isEmpty()){
            namaKaryawan.setText("Tidak Ada");
        }

        if (nama_supplier.isEmpty()){
            namaSupplier.setText("Tidak Ada");
        }

        if (datee.isEmpty()){
            date.setText("Tidak Ada");
        }

        if (timee.isEmpty()){
            time.setText("Tidak Ada");
        }

        if (total_harga.isEmpty()){
            totalHarga.setText("Tidak Ada");
        }

        if (biayaa.isEmpty()){
            biaya.setText("Tidak Ada");
        }
    }
}