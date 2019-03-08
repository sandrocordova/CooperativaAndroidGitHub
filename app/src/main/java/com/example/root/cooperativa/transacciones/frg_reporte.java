package com.example.root.cooperativa.transacciones;

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

import com.example.root.cooperativa.R;
import com.example.root.cooperativa.frg_modificar;
import com.example.root.cooperativa.login.Global;

import org.json.JSONArray;
import org.json.JSONException;

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
 * {@link frg_reporte.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frg_reporte#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frg_reporte extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String contenido="";
    TextView txtContenido;


    Global global = new Global();


    ServicioWeb hiloConexion;
    private OnFragmentInteractionListener mListener;

    public frg_reporte() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frg_reporte.
     */
    // TODO: Rename and change types and number of parameters
    public static frg_reporte newInstance(String param1, String param2) {
        frg_reporte fragment = new frg_reporte();
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
                    String url = "http://"+global.getIp()+":8080/Proyecto/api/cuenta/buscarTransaccion?cedula="+cajaCedula.getText().toString();
                    hiloConexion = new ServicioWeb();
                    hiloConexion.execute(url, "1");

                    dlgBuscar.hide();
                }
            }
        });
        dlgBuscar.show();
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

        JSONArray jsonArray = new JSONArray(str);
        for (int i = 0; i < jsonArray.length(); i++) {
                contenido += jsonArray.getJSONObject(i).getString("descripcion");
                contenido+="\n";

        }
        txtContenido.setText(contenido);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_frg_reporte, container, false);
        txtContenido = (TextView)vista.findViewById(R.id.txt_reporte_contenedor);

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
}
