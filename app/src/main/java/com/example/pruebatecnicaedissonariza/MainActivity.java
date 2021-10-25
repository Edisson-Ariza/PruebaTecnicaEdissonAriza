package com.example.pruebatecnicaedissonariza;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText txtUser, txtPassword;
    Button btnEnviar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnEnviar = findViewById(R.id.btnLogin);
        txtPassword=findViewById(R.id.edtPassword);
        txtUser= findViewById(R.id.edtUsuario);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login(txtPassword.getText().toString(), txtUser.getText().toString());
            }
        });
    }
    private void Login(final String password, final String usuario) {
        String url = "https://makita.com.co/ventas/conexion_app_ventas";
        final String[] token = new String[1];
        final String[] uid = new String[1];
        StringRequest postResquest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    uid[0] = object.getString("token_uid");
                    token[0] =object.getString("token_Id");
                }catch (JSONException e){
                    e.printStackTrace();
                }
                if (token[0]!=null) {
                    Intent i = new Intent(MainActivity.this, MainActivity2.class);
                    i.putExtra("token", token[0]);
                    i.putExtra("uid", uid[0]);
                    startActivity(i);
                }else{
                    Toast.makeText(MainActivity.this,"Usuario o contraseña incorrectos \n"+"Por favor, ingrese: \n" + " Usuario: desarrollo_110022\n" + "Password: desarrollo_110022",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("usuario", usuario);
                params.put("password", password);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postResquest);
    }


}