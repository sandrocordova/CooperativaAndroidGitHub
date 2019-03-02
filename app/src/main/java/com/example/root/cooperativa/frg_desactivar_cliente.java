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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frg_desactivar_cliente.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frg_desactivar_cliente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frg_desactivar_cliente extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView txtCedulaDesactivar, txtNombreDesactivar;
    Button btnDesactivarAceptar;

    ServicioWeb hiloConexion;

    private RequestQueue requestQueue;
    private OnFragmentInteractionListener mListener;

    public frg_desactivar_cliente() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frg_desactivar_cliente.
     */
    // TODO: Rename and change types and number of parameters
    public static frg_desactivar_cliente newInstance(String param1, String param2) {
        frg_desactivar_cliente fragment = new frg_desactivar_cliente();
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
    }

    private void cargarWebService(){

        String url = "http://10.30.4.189:8080/Proyecto/api/cuenta/desactivar";
        JSONObject json = new JSONObject();
        try {
            json.put("cedula",txtCedulaDesactivar.getText().toString());
            json.put("orden",0);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(getContext(), "Respuesta: "+ response.toString(), Toast.LENGTH_SHORT).show();
                        //              serverResp.setText("String Response : "+ response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //    serverResp.setText("Error getting response");
            }
        });
        //jsonObjectRequest.setTag(REQ_TAG);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_frg_desactivar_cliente, container, false);
        txtCedulaDesactivar = (EditText)vista.findViewById(R.id.txt_desactivar_cedula);
        txtNombreDesactivar = (EditText)vista.findViewById(R.id.txt_desactivar_nombre);
        btnDesactivarAceptar = (Button)vista.findViewById(R.id.btn_desactivar_aceptar);
        btnDesactivarAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


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

                    String url = "http://10.20.2.58:8080/Proyecto/api/cuenta/getCedula?cedula=" + cajaCedula.getText().toString();
                    hiloConexion = new ServicioWeb();
                    hiloConexion.execute(url, "1");
                }
            }
        });
        dlgBuscar.show();
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

        Toast.makeText(getContext(), ""+str, Toast.LENGTH_SHORT).show();
        JSONObject jsonObject = new JSONObject(str);
        txtCedulaDesactivar.setText(jsonObject.getString("cedula"));
        txtNombreDesactivar.setText(jsonObject.getString("nombres")+" "+jsonObject.getString("apellidos"));

    }
}
