package com.example.root.cooperativa;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.root.cooperativa.login.Global;
import com.example.root.cooperativa.transacciones.frg_deposito;
import com.example.root.cooperativa.transacciones.frg_reporte;
import com.example.root.cooperativa.transacciones.frg_retiro;
import com.example.root.cooperativa.transacciones.frg_transferencia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener,
        frg_desactivar_cliente.OnFragmentInteractionListener,
        frg_modificar.OnFragmentInteractionListener,
        frg_listar.OnFragmentInteractionListener,
        frg_nuevoCliente.OnFragmentInteractionListener,
        frg_retiro.OnFragmentInteractionListener,
        frg_deposito.OnFragmentInteractionListener,
        frg_reporte.OnFragmentInteractionListener,frg_transferencia.OnFragmentInteractionListener {

    TextView txView;
    String contenido="";

    ServicioWeb hiloConexion;
    Boolean respuesta;


    Global global = new Global();

    private StringRequest stringRequest;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requestQueue = Volley.newRequestQueue(MainActivity.this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_ingresar) {
            frg_nuevoCliente frgUno = new frg_nuevoCliente();
            FragmentTransaction transactionUno = getSupportFragmentManager().beginTransaction();
            transactionUno.replace(R.id.contenedor_principal, frgUno);
            transactionUno.commit();
            // Handle the camera action
        } else if (id == R.id.nav_buscar) {
            frg_listar frg = new frg_listar();
            FragmentTransaction transactionUno = getSupportFragmentManager().beginTransaction();
            transactionUno.replace(R.id.contenedor_principal, frg);
            transactionUno.commit();

            //String url = "https://proyetoturismov1.herokuapp.com/servicioweb/zona?nombreZona=gualel";
/*
            frg_listar frgUno = new frg_listar();
            FragmentTransaction transactionUno = getSupportFragmentManager().beginTransaction();
            transactionUno.replace(R.id.contenedor_principal, frgUno);
            transactionUno.commit();
*/

        } else if (id == R.id.nav_desactivar) {
            frg_desactivar_cliente frg = new frg_desactivar_cliente();
            FragmentTransaction transactionUno = getSupportFragmentManager().beginTransaction();
            transactionUno.replace(R.id.contenedor_principal, frg);
            transactionUno.commit();


        } else if (id == R.id.nav_modificar) {
            frg_modificar frg = new frg_modificar();
            FragmentTransaction transactionUno = getSupportFragmentManager().beginTransaction();
            transactionUno.replace(R.id.contenedor_principal, frg);
            transactionUno.commit();

        } else if (id == R.id.nav_deposito) {
            frg_deposito frg = new frg_deposito();
            FragmentTransaction transactionUno = getSupportFragmentManager().beginTransaction();
            transactionUno.replace(R.id.contenedor_principal, frg);
            transactionUno.commit();
        } else if (id == R.id.nav_retiro) {
            frg_retiro frg = new frg_retiro();
            FragmentTransaction transactionUno = getSupportFragmentManager().beginTransaction();
            transactionUno.replace(R.id.contenedor_principal, frg);
            transactionUno.commit();
        } else if (id == R.id.nav_transferencia) {
            frg_transferencia frg = new frg_transferencia();
            FragmentTransaction transactionUno = getSupportFragmentManager().beginTransaction();
            transactionUno.replace(R.id.contenedor_principal, frg);
            transactionUno.commit();
        }else if (id == R.id.nav_reporte) {
            frg_reporte frg = new frg_reporte();
            FragmentTransaction transactionUno = getSupportFragmentManager().beginTransaction();
            transactionUno.replace(R.id.contenedor_principal, frg);
            transactionUno.commit();
        }else if (id == R.id.nav_buscar_cliente) {
            final Dialog dlgbuscar = new Dialog(MainActivity.this);
            dlgbuscar.setContentView(R.layout.dlg_buscar);
            final EditText txtBuscarCedula = (EditText)dlgbuscar.findViewById(R.id.txt_buscar_cedula);
            Button btnbuscar = (Button) dlgbuscar.findViewById(R.id.btn_buscar);
            dlgbuscar.show();
            btnbuscar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "http://"+global.getIp()+":8080/Proyecto/api/cuenta/buscarCedula?cedula="+txtBuscarCedula.getText().toString();
                    hiloConexion = new ServicioWeb();
                    hiloConexion.execute(url, "1");
                    dlgbuscar.hide();
                }
            });

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean cargarWebService(){

        respuesta = false;
        String url = "http://"+global.getIp()+":8080/Proyecto/api/cuenta/modificarCliente";
        JSONObject json = new JSONObject();
        /*try {
            json.put("cedula",txtBuscarCedula.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        respuesta = true;
                        Toast.makeText(MainActivity.this, "BIEN "+response, Toast.LENGTH_SHORT).show();
                        //              serverResp.setText("String Response : "+ response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                        respuesta = false;
                        Toast.makeText(MainActivity.this, "ERROR: Cliente no encontrado", Toast.LENGTH_SHORT).show();
                //    serverResp.setText("Error getting response");
            }
        });
        //jsonObjectRequest.setTag(REQ_TAG);
        requestQueue.add(jsonObjectRequest);
        return respuesta;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nav_ingresar:
                Fragment nuevoCliente = new frg_nuevoCliente();
                FragmentTransaction transactionUno = getSupportFragmentManager().beginTransaction();
                transactionUno.show(nuevoCliente);
                final EditText cedula = (EditText)findViewById(R.id.txt_ingresar_cedula);
                final EditText nombres = (EditText)findViewById(R.id.txt_ingresar_nombres);
                final EditText apellidos = (EditText)findViewById(R.id.txt_ingresar_apellidos);
                final EditText genero = (EditText)findViewById(R.id.txt_ingresar_genero);
                final EditText estadoCivil = (EditText)findViewById(R.id.txt_ingresar_EstadoCivil);
                final EditText fechaNacimiento = (EditText)findViewById(R.id.txt_ingresar_fechaNacimiento);
                final EditText correo = (EditText)findViewById(R.id.txt_ingresar_correo);
                final EditText telefono = (EditText)findViewById(R.id.txt_ingresar_telefono);
                final EditText celular = (EditText)findViewById(R.id.txt_ingresar_cedula);
                final EditText direccion = (EditText)findViewById(R.id.txt_ingresar_direccion);
                final Button guardar = (Button)findViewById(R.id.btn_ingresar_guardar);
                String jsonObject =
                        "{\"cedula\":\""+cedula+"\",\n" +
                        "\"nombres\":\""+nombres+"\",\n" +
                        "\"apellidos\":\""+apellidos+"\",\n" +
                        "\"genero\":\""+genero+"\",\n" +
                        "\"estadoCivil\":\""+estadoCivil+"\",\n" +
                        "\"fechaNacimiento\":\""+fechaNacimiento+"\",\n" +
                        "\"correo\":\""+correo+"\",\n" +
                        "\"telefono\":\""+telefono+"\",\n" +
                        "\"celular\":\""+celular+"\",\n" +
                        "\"direccion\":"+direccion+"\",\n" +
                        "\"estado\":\"1\"\n" +
                        "}";
                txView.setText(jsonObject);
                break;

        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public class ServicioWeb extends AsyncTask<String,Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String cadena = strings[0];
            String devuelve = "";
            URL url;
            if (strings[1]=="1"){
                try {
                    url = new URL(cadena);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    int codigoConnection = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();
                    if (codigoConnection==HttpURLConnection.HTTP_OK){
                        InputStream in = new BufferedInputStream(connection.getInputStream());
                        BufferedReader br = new BufferedReader(new InputStreamReader(in));
                        devuelve = br.readLine();
                        return devuelve;
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return devuelve;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                obtenerObjeros(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);

        }
    }

    private void obtenerObjeros(String entrada) throws JSONException {
        Dialog dlgbuscarCliente = new Dialog(MainActivity.this);
        dlgbuscarCliente.setContentView(R.layout.dlg_buscar_cliente);
        EditText cedula = (EditText)dlgbuscarCliente.findViewById(R.id.txt_buscarCliente_cedula);
        EditText nombres = (EditText)dlgbuscarCliente.findViewById(R.id.txt_buscarCliente_nombres);
        EditText apellidos = (EditText)dlgbuscarCliente.findViewById(R.id.txt_buscarCliente_apellidos);
        EditText genero = (EditText)dlgbuscarCliente.findViewById(R.id.txt_buscarCliente_genero);
        EditText estadoCivil = (EditText)dlgbuscarCliente.findViewById(R.id.txt_buscarCliente_EstadoCivil);
        EditText fechaNacimiento = (EditText)dlgbuscarCliente.findViewById(R.id.txt_buscarCliente_fechaNacimiento);
        EditText correo = (EditText)dlgbuscarCliente.findViewById(R.id.txt_buscarCliente_correo);
        EditText telefono = (EditText)dlgbuscarCliente.findViewById(R.id.txt_buscarCliente_telefono);
        EditText celular = (EditText)dlgbuscarCliente.findViewById(R.id.txt_buscarCliente_celuar);
        EditText direccion = (EditText)dlgbuscarCliente.findViewById(R.id.txt_buscarCliente_direccion);
        EditText tipoCuenta = (EditText)dlgbuscarCliente.findViewById(R.id.txt_buscarCliente_tipoCuenta);
        EditText saldo = (EditText)dlgbuscarCliente.findViewById(R.id.txt_buscarCliente_saldo);
        dlgbuscarCliente.show();

        if (entrada.length()>0){
        JSONObject cuenta = new JSONObject(entrada);
        JSONObject cliente = new JSONObject();
            cliente = cuenta.getJSONObject("clienteId");
            cedula.setText(cliente.getString("cedula"));
            nombres.setText(cliente.getString("nombres"));
            apellidos.setText(cliente.getString("apellidos"));
            genero.setText(cliente.getString("genero"));
            estadoCivil.setText(cliente.getString("estadoCivil"));
            fechaNacimiento.setText(cliente.getString("fechaNacimiento"));
            correo.setText(cliente.getString("correo"));
            telefono.setText(cliente.getString("telefono"));
            celular.setText(cliente.getString("celular"));
            direccion.setText(cliente.getString("direccion"));
            tipoCuenta.setText(cuenta.getString("tipoCuenta"));

            saldo.setText(cuenta.getString("saldo"));
        }else {
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
            dlgbuscarCliente.hide();
        }

    }


}
