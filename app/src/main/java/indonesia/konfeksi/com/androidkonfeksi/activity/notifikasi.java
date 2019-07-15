package indonesia.konfeksi.com.androidkonfeksi.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import indonesia.konfeksi.com.androidkonfeksi.R;

public class notifikasi extends AppCompatActivity {
    public TextView notification;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifikasi);
        notification= findViewById(R.id.teks);
    }
}
