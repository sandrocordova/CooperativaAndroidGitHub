package com.example.root.cooperativa.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.root.cooperativa.MainActivity;
import com.example.root.cooperativa.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText txtLoginUsuario, txtLoginContrasenia;
    Button btnLogin;

    Global gbl = new Global();
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtLoginUsuario = (EditText)findViewById(R.id.txt_login_usuario);
        txtLoginContrasenia = (EditText)findViewById(R.id.txt_login_contrasenia);
        btnLogin = (Button)findViewById(R.id.btn_login_aceptar);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();
            }
        });



        requestQueue = Volley.newRequestQueue(LoginActivity.this );
    }
    private void cargarWebService(){

        String url = "http://"+gbl.getIp()+":8080/Proyecto/api/cuenta/buscarUsuario";

        gbl.setUsuario(txtLoginUsuario.getText().toString());
        JSONObject json = new JSONObject();
        try {
            json.put("nombre",txtLoginUsuario.getText().toString());
            json.put("clave",txtLoginContrasenia.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Intent intent;
                        intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        //              serverResp.setText("String Response : "+ response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();

                //    serverResp.setText("Error getting response");
            }
        });
        //jsonObjectRequest.setTag(REQ_TAG);
        requestQueue.add(jsonObjectRequest);
    }
}
