package indonesia.konfeksi.com.androidkonfeksi.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import indonesia.konfeksi.com.androidkonfeksi.R;

public class Return extends AppCompatActivity {

    private AlertDialog.Builder dialog;
    private LayoutInflater inflater;
    private View dialogView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);

        dialog = new AlertDialog.Builder(Return.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_return, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        searchView = dialogView.findViewById(R.id.searchView);
    }
}
