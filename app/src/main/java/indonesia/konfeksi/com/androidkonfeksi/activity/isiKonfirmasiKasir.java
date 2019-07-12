package indonesia.konfeksi.com.androidkonfeksi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import indonesia.konfeksi.com.androidkonfeksi.R;

public class isiKonfirmasiKasir extends AppCompatActivity {
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

        nonota = findViewById(R.id.nonota);
        tanggal = findViewById(R.id.tanggal);
        karyawan = findViewById(R.id.karyawan);

        mPostNoNota = getIntent().getExtras().getString("no_nota");
        mPostKaryawan = getIntent().getExtras().getString("nama_karyawan");
        mPostTanggal = getIntent().getExtras().getString("tanggal");

        nonota.setText(mPostNoNota);
        tanggal.setText(mPostTanggal);
        karyawan.setText(mPostKaryawan);
    }
}
