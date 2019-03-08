package com.example.root.cooperativa;

import android.content.Context;
import android.net.Uri;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.root.cooperativa.login.Global;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frg_nuevoCliente.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frg_nuevoCliente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frg_nuevoCliente extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText cedula;
    EditText nombres;
    EditText apellidos ;
    EditText genero ;
    EditText tipoCuenta ;
    EditText estadoCivil ;
    EditText fechaNacimiento ;
    EditText correo;
    EditText telefono;
    EditText celular ;
    EditText direccion;
    EditText mostrarPost;
    Button guardar ;


    private StringRequest stringRequest;


    Global global = new Global();


    private RequestQueue requestQueue;


    //guardar.setOnClickListener(this);
    private OnFragmentInteractionListener mListener;

    public frg_nuevoCliente() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frg_nuevoCliente.
     */
    // TODO: Rename and change types and number of parameters
    public static frg_nuevoCliente newInstance(String param1, String param2) {
        frg_nuevoCliente fragment = new frg_nuevoCliente();
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

        requestQueue = Volley.newRequestQueue(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_frg_nuevo_cliente, container, false);
         cedula = (EditText)vista.findViewById(R.id.txt_ingresar_cedula);
         nombres = (EditText)vista.findViewById(R.id.txt_ingresar_nombres);
         apellidos = (EditText)vista.findViewById(R.id.txt_ingresar_apellidos);
         genero = (EditText)vista.findViewById(R.id.txt_ingresar_genero);
         estadoCivil = (EditText)vista.findViewById(R.id.txt_ingresar_EstadoCivil);
         fechaNacimiento = (EditText)vista.findViewById(R.id.txt_ingresar_fechaNacimiento);
         correo = (EditText)vista.findViewById(R.id.txt_ingresar_correo);
         telefono = (EditText)vista.findViewById(R.id.txt_ingresar_telefono);
         celular = (EditText)vista.findViewById(R.id.txt_ingresar_celuar);
         direccion = (EditText)vista.findViewById(R.id.txt_ingresar_direccion);
         tipoCuenta = (EditText)vista.findViewById(R.id.txt_ingresar_tipoCuenta);
         guardar = (Button)vista.findViewById(R.id.btn_ingresar_guardar);
         guardar.setOnClickListener(this);

         return vista;
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


    private void cargarWebService(){

        String url = "http://"+global.getIp()+":8080/Proyecto/api/cuenta/guardarCliente";
        JSONObject json = new JSONObject();
        JSONObject json2 = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        Date date = new Date();
        try {
            json2.put("cedula",cedula.getText().toString());
            json2.put("nombres",nombres.getText().toString());
            json2.put("apellidos",apellidos.getText().toString());
            json2.put("celular",celular.getText().toString());
            json2.put("correo",correo.getText().toString());
            json2.put("direccion",direccion.getText().toString());
            json2.put("estadoCivil",estadoCivil.getText().toString());
            json2.put("fechaNacimiento",fechaNacimiento.getText().toString());
            json2.put("genero",genero.getText().toString());
            json2.put("telefono",telefono.getText().toString());
            json2.put("estado",true);

            /*
            String str = "{\n" +
                    "\"cedula\":\""+cedula.getText().toString()+"\",\n" +
                    "\"apellidos\":\""+apellidos.getText().toString()+"\",\n" +
                    "\"nombres\":\""+nombres.getText().toString()+"\",\n" +
                    "\"celular\":\""+celular.getText().toString()+"\",\n" +
                    "\"correo\":\""+correo.getText().toString()+"\",\n" +
                    "\"direccion\":\""+direccion.getText().toString()+"\", \n" +
                    "\"estadoCivil\":\""+estadoCivil.getText().toString()+"\",\n" +
                    "\"fechaNacimiento\":\""+fechaNacimiento.getText().toString()+"\",\n" +
                    "\"genero\":\""+genero.getText().toString()+"\",\n" +
                    "\"telefono\":\""+telefono.getText().toString()+"\",\n" +
                    "\"estado\":true}";
            String str = "{"+"cedula:"+"\""+cedula.getText().toString()+"\","+
                    "apellidos:"+"\""+apellidos.getText().toString()+"\","+
                    "nombres:"+"\""+nombres.getText().toString()+"\","+
                    "celular:"+"\""+celular.getText().toString()+"\","+
                    "celular:"+"\""+celular.getText().toString()+"\","+
                    "correo:"+"\""+correo.getText().toString()+"\","+
                    "direccion:"+"\""+direccion.getText().toString()+"\","+
                    "estadoCivil:"+"\""+estadoCivil.getText().toString()+"\","+
                    "fechaNacimiento:"+"\""+fechaNacimiento.getText().toString()+"\","+
                    "genero:"+"\""+genero.getText().toString()+"\","+
                    "telefono:"+"\""+telefono.getText().toString()+"\","+
                    "estado:"+"true"+"}";
*/

            json.put("estado", true);
            json.put("fechaApertura","1999-01-01");
            json.put("numero",cedula.getText().toString());
            json.put("saldo","0");
            json.put("tipoCuenta",tipoCuenta.getText().toString());
            json.put("clienteId", "");

            jsonArray.put(json2);
            jsonArray.put(json);
            //Toast.makeText(getContext(), "Respuesta: "+ json.toString(), Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, url, jsonArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Toast.makeText(getContext(), "Cliente Guardado", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getContext(), "CORRECTO: "+ response.toString(), Toast.LENGTH_SHORT).show();
                        //              serverResp.setText("String Response : "+ response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                      // Toast.makeText(getContext(), ""+ error.toString(), Toast.LENGTH_SHORT).show();
                //    serverResp.setText("Error getting response");
            }
        });
        //jsonObjectRequest.setTag(REQ_TAG);
        requestQueue.add(jsonObjectRequest);

        cedula.setText("");
        nombres.setText("");
        apellidos.setText("");
        estadoCivil.setText("");
        fechaNacimiento.setText("");
        telefono.setText("");
        celular.setText("");
        direccion.setText("");
        tipoCuenta.setText("");
        genero.setText("");
        correo.setText("");

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_ingresar_guardar:
                cargarWebService();

/*


                String url = "http://10.30.3.130:8080/ProyectoCooperativa/ServicioRest/cuenta";
                stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>(){

                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getContext(), "Cliente insertado", Toast.LENGTH_SHORT).show();
                            }

                        },
                        new Response.ErrorListener(){

                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Date date = new Date();
                        String json = "{\n" +
                                "\"cedula\":\""+cedula.getText().toString()+"\",\n" +
                                "\"apellidos\":\""+apellidos.getText().toString()+"\",\n" +
                                "\"nombres\":\""+nombres.getText().toString()+"\",\n" +
                                "\"celular\":\""+celular.getText().toString()+"\",\n" +
                                "\"correo\":\""+correo.getText().toString()+"\",\n" +
                                "\"direccion\":\""+direccion.getText().toString()+"\", \n" +
                                "\"estadoCivil\":\""+estadoCivil.getText().toString()+"\",\n" +
                                "\"fechaNacimiento\":\""+fechaNacimiento.getText().toString()+"\",\n" +
                                "\"genero\":\""+genero.getText().toString()+"\",\n" +
                                "\"telefono\":\""+telefono.getText().toString()+"\",\n" +
                                "\"estado\":\"true\"}";

                        Map<String,String> map = new HashMap<>();
                        map.put("estado", "true");
                        map.put("fechaApertura", ""+ date);
                        map.put("numero", ""+ cedula.getText().toString());
                        map.put("saldo", ""+ "0.00");
                        map.put("tipoCuenta", ""+ tipoCuenta.getText().toString());
                        map.put("clienteId", json);


                        map.put("cedula", );
                        map.put("nombres", );
                        map.put("apellidos", );
                        map.put("genero", apellidos.getText().toString());
                        map.put("estadoCivil", apellidos.getText().toString());
                        map.put("celular", apellidos.getText().toString());
                        map.put("correo", apellidos.getText().toString());
                        map.put("direccion", apellidos.getText().toString());
                        map.put("fechaNacimiento", apellidos.getText().toString());
                        map.put("telefono", apellidos.getText().toString());
                        map.put("estado", "1");
                        return map;
                    }
                };
                Toast.makeText(getContext(), "Guardado", Toast.LENGTH_SHORT).show();
                requestQueue.add(stringRequest);
                */
                break;

        }
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
}
