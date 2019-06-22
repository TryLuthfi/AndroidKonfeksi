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
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, konfigurasi.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("id_karyawan",response.toString());
                        if (response.contains(konfigurasi.LOGIN_SUCCESS)) {
                            hideDialog();
                            String id_karyawan = response.toString();
                            Log.e("ididid_karyawan", id_karyawan);
                            setPreference(id_karyawan);
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

    void setPreference(String id_karyawan){
        SharedPreferences mSettings = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString("id_karyawan", id_karyawan);
        editor.apply();
    }
    private String getId_Karyawan(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String id_karyawan = preferences.getString("id_karyawan", "null");
        return id_karyawan;
    }
}
