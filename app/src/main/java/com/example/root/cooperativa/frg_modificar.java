package com.example.root.cooperativa;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.root.cooperativa.login.Global;


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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frg_modificar.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frg_modificar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frg_modificar extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String cedula = "0";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView txtcedula, txtnombres, txtapellidos, txtgenero,
            txtEstadoCivil, txtFechaNacimiento, txtCorreo,
            txtTelefono, txtCelular, txtDireccion, txtTipoCuenta;
    Button btnModificar;
    String contenido="";



    Global global = new Global();

    ServicioWeb hiloConexion;
    private RequestQueue requestQueue;

    private OnFragmentInteractionListener mListener;

    public frg_modificar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frg_modificar.
     */
    // TODO: Rename and change types and number of parameters
    public static frg_modificar newInstance(String param1, String param2) {
        frg_modificar fragment = new frg_modificar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        final Dialog dlgBuscar = new Dialog(getActivity());
        dlgBuscar.setContentView(R.layout.dlg_buscar);
        final EditText cajaCedula = (EditText)dlgBuscar.findViewById(R.id.txt_buscar_cedula);

        final Button btnBuscar = (Button)dlgBuscar.findViewById(R.id.btn_buscar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cajaCedula.length()<=0){
                    Toast.makeText(getContext(), "Ingrese cédula", Toast.LENGTH_SHORT).show();
                }else {
                    String url = "http://"+global.getIp()+":8080/Proyecto/api/cuenta/buscarCedulaCliente?cedula="+cajaCedula.getText().toString();
                    hiloConexion = new ServicioWeb();
                    hiloConexion.execute(url, "1");
                    dlgBuscar.hide();
                }
            }
        });
        dlgBuscar.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View vista = inflater.inflate(R.layout.fragment_frg_modificar, container, false);

        requestQueue = Volley.newRequestQueue(getActivity());

        txtcedula = (EditText)vista.findViewById(R.id.txt_modificar_cedula);
        txtnombres = (EditText)vista.findViewById(R.id.txt_modificar_nombres);
        txtapellidos = (EditText)vista.findViewById(R.id.txt_modificar_apellidos);
        txtgenero = (EditText)vista.findViewById(R.id.txt_modificar_genero);
        txtEstadoCivil = (EditText)vista.findViewById(R.id.txt_modificar_EstadoCivil);
        txtFechaNacimiento = (EditText)vista.findViewById(R.id.txt_modificar_fechaNacimiento);
        txtCorreo = (EditText)vista.findViewById(R.id.txt_modificar_correo);
        txtTelefono = (EditText)vista.findViewById(R.id.txt_modificar_telefono);
        txtCelular = (EditText)vista.findViewById(R.id.txt_modificar_celuar);
        txtDireccion = (EditText)vista.findViewById(R.id.txt_modificar_direccion);


        txtcedula.setFocusable(false);
        txtgenero.setFocusable(false);
        txtFechaNacimiento.setFocusable(false);

        btnModificar = (Button)vista.findViewById(R.id.btn_modificar_guardar);
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cargarWebService();


//                requestQueue.add(stringRequest);
            }
        });

        // Inflate the layout for this fragment

        return vista;
    }

    private void cargarWebService(){

        String url = "http://"+global.getIp()+":8080/Proyecto/api/cuenta/modificarCliente";
        JSONObject json = new JSONObject();
        try {
            json.put("cedula",txtcedula.getText().toString());
            json.put("nombres",txtnombres.getText().toString());
            json.put("apellidos",txtapellidos.getText().toString());
            json.put("genero",txtgenero.getText().toString());
            json.put("estadoCivil",txtEstadoCivil.getText().toString());
            json.put("fechaNacimiento",txtFechaNacimiento.getText().toString());
            json.put("correo",txtCorreo.getText().toString());
            json.put("telefono",txtTelefono.getText().toString());
            json.put("celular",txtCelular.getText().toString());
            json.put("direccion",txtDireccion.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getContext(), "Cliente modificado", Toast.LENGTH_SHORT).show();
                        //              serverResp.setText("String Response : "+ response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Cliente modificado", Toast.LENGTH_SHORT).show();
                //    serverResp.setText("Error getting response");
            }
        });
        //jsonObjectRequest.setTag(REQ_TAG);
        requestQueue.add(jsonObjectRequest);
        txtcedula.setText("");
        txtnombres.setText("");
        txtapellidos.setText("");
        txtgenero.setText("");
        txtEstadoCivil.setText("");
        txtFechaNacimiento.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtCelular.setText("");
        txtDireccion.setText("");


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class ServicioWeb extends AsyncTask<String,Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String cadena = strings[0];
            String devuelve = "No devuelve nada";
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
        obtenerParadas(entrada);
    }
    private void obtenerParadas(String str) throws JSONException {
        JSONObject jsonObject = new JSONObject(str);
            txtcedula.setText(jsonObject.getString("cedula"));
            txtnombres.setText(jsonObject.getString("nombres"));
            txtapellidos.setText(jsonObject.getString("apellidos"));
            txtgenero.setText(jsonObject.getString("genero"));
            txtEstadoCivil.setText(jsonObject.getString("estadoCivil"));
            txtFechaNacimiento.setText(jsonObject.getString("fechaNacimiento"));
            txtCorreo.setText(jsonObject.getString("correo"));
            txtCelular.setText(jsonObject.getString("celular"));
            txtTelefono.setText(jsonObject.getString("telefono"));
            txtDireccion.setText(jsonObject.getString("direccion"));
    }




}
