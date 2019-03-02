package com.example.root.cooperativa;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
 * {@link frg_listar.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frg_listar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frg_listar extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView txView;
    String contenido="";

    ServicioWeb hiloConexion;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public frg_listar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frg_listar.
     */
    // TODO: Rename and change types and number of parameters
    public static frg_listar newInstance(String param1, String param2) {
        frg_listar fragment = new frg_listar();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vista = inflater.inflate(R.layout.fragment_frg_listar, container, false);
        txView = (TextView)vista.findViewById(R.id.txt_listar);

        String url = "http://10.30.4.189:8080/Proyecto/api/cuenta/todos";
        hiloConexion = new ServicioWeb();
        hiloConexion.execute(url, "1");

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

    @Override
    public void onClick(View v) {

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
        txView.setText(entrada);
        obtenerParadas(entrada);
    }
    private void obtenerParadas(String str) throws JSONException {
            /*
        JSONArray jsonArray = new JSONArray(str);
        for (int i = 0; i < jsonArray.length(); i++) {
            if (Integer.parseInt(jsonArray.getJSONObject(i).getString("estado"))==1){
            contenido+="\n";
            contenido += jsonArray.getJSONObject(i).getString("cedula");
            contenido+="\n";
            contenido += jsonArray.getJSONObject(i).getString("nombres");
            contenido+="\n";
            contenido += jsonArray.getJSONObject(i).getString("apellidos");
            contenido+="\n";
            contenido += jsonArray.getJSONObject(i).getString("genero");
            contenido+="\n";
            contenido += jsonArray.getJSONObject(i).getString("estadoCivil");
            contenido+="\n";
            contenido += jsonArray.getJSONObject(i).getString("fechaNacimiento");
            contenido+="\n";
            contenido += jsonArray.getJSONObject(i).getString("correo");
            contenido+="\n";
            contenido += jsonArray.getJSONObject(i).getString("telefono");
            contenido+="\n";
            contenido += jsonArray.getJSONObject(i).getString("celular");
            contenido+="\n";
            contenido += jsonArray.getJSONObject(i).getString("direccion");
                contenido+="\n";
            }else{
                contenido+="\n";
                contenido+="Usuario con cedula: "+jsonArray.getJSONObject(i).getString("cedula")+" se encuentra inactivo.";
                contenido+="\n";
            }
        }
        txView.setText(contenido);
*/

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
