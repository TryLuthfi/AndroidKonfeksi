package indonesia.konfeksi.com.androidkonfeksi.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import indonesia.konfeksi.com.androidkonfeksi.R;
import indonesia.konfeksi.com.androidkonfeksi.konfigurasi.konfigurasi;

public class Login extends AppCompatActivity {

    private EditText username, password;
    private Button login;
    private Context context;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = Login.this;

        String id_karyawan = getId_Karyawan();
        if (id_karyawan != "null") {
            gotoCourseActivity();
        }

        progressDialog = new ProgressDialog(context);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.btn_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginclass();
            }
        });
    }

    private void loginclass() {
        //Getting values from edit texts
        final String usernamee = username.getText().toString().trim();
        final String passwordd = password.getText().toString().trim();
        progressDialog.setMessage("Login Process...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, konfigurasi.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("keterangan",response.toString());
                        if (response.contains(konfigurasi.LOGIN_SUCCESS)) {
                            hideDialog();
                            String id_karyawan = response.toString().split(";")[1];
                            String kode_karyawan = response.toString().split(";")[2];
                            String nama = response.toString().split(";")[3];
                            String alamat = response.toString().split(";")[4];
                            String kota = response.toString().split(";")[5];
                            String negara = response.toString().split(";")[6];
                            String kode_pos = response.toString().split(";")[7];
                            String no_telp = response.toString().split(";")[8];
                            String email = response.toString().split(";")[9];
                            String status = response.toString().split(";")[10];

                            Log.e("id_karyawan", id_karyawan);
                            Log.e("kode_karyawan", kode_karyawan);
                            Log.e("nama", nama);
                            Log.e("alamat", alamat);
                            Log.e("kota", kota);
                            Log.e("negara", negara);
                            Log.e("kode_pos", kode_pos);
                            Log.e("no_telp", no_telp);
                            Log.e("email", email);
                            Log.e("status", status);

                            setPreference(id_karyawan, kode_karyawan, nama, alamat, kota, negara, kode_pos, no_telp, email, status);
                            gotoCourseActivity();

                        } else {
                            hideDialog();
                            Toast.makeText(context, "Invalid username or password", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        hideDialog();
                        Toast.makeText(context, "The server unreachable", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put(konfigurasi.KEY_USERNAME, usernamee);
                params.put(konfigurasi.KEY_PASSWORD, passwordd);

                return params;
            }
        };


        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void gotoCourseActivity() {
        Intent intent = new Intent(context, DashBoard.class);
        startActivity(intent);
        finish();
    }

    void setPreference(String id_karyawan,String kode_karyawan,String nama,String alamat,String kota,
                       String negara,String kode_pos,String no_telp,String email,String status){

        SharedPreferences mSettings = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();

        editor.putString("id_karyawan", id_karyawan);
        editor.putString("kode_karyawan", kode_karyawan);
        editor.putString("nama", nama);
        editor.putString("alamat", alamat);
        editor.putString("kota", kota);
        editor.putString("negara", negara);
        editor.putString("kode_pos", kode_pos);
        editor.putString("no_telp", no_telp);
        editor.putString("email", email);
        editor.putString("status", status);

        editor.apply();
    }
    private String getId_Karyawan(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String id_karyawan = preferences.getString("id_karyawan", "null");
        return id_karyawan;
    }
}
