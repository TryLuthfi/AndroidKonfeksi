package indonesia.konfeksi.com.androidkonfeksi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import indonesia.konfeksi.com.androidkonfeksi.R;

public class TambahPembelian extends AppCompatActivity {

    private String date;
    private TextView tgl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pembelian);

        tgl = findViewById(R.id.tanggal);
        setDate();
    }

    public void setDate() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        date = formatter.format(today);

        tgl.setText(date);
    }


}
