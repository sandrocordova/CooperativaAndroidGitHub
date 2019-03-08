package com.example.root.cooperativa.transacciones;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.root.cooperativa.R;
import com.example.root.cooperativa.login.Global;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frg_deposito.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frg_deposito#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frg_deposito extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText txtCedulaDeposito, txtCantidadDeposito;
    private Button btnAceptarDeposito;
    private OnFragmentInteractionListener mListener;

    Global global = new Global();

    private String url = "http://"+global.getIp()+":8080/Proyecto/api/cuenta/deposito";
    private RequestQueue requestQueue;
    private JSONArray jsonArray = new JSONArray();

    public frg_deposito() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frg_deposito.
     */
    // TODO: Rename and change types and number of parameters
    public static frg_deposito newInstance(String param1, String param2) {
        frg_deposito fragment = new frg_deposito();
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

        View vista = inflater.inflate(R.layout.fragment_frg_deposito, container, false);
        txtCedulaDeposito = (EditText)vista.findViewById(R.id.txt_deposito_cedula);
        txtCantidadDeposito = (EditText)vista.findViewById(R.id.txt_deposito_cantidad);
        btnAceptarDeposito = (Button)vista.findViewById(R.id.btn_deposito_aceptar);
        btnAceptarDeposito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();
                txtCantidadDeposito.setText("");
                txtCantidadDeposito.setText("");
                Toast.makeText(getContext(), "Depósito realizado con éxito", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue = Volley.newRequestQueue(getActivity());
        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    private void cargarWebService(){

        JSONObject cantidad = new JSONObject();
        JSONObject cuenta = new JSONObject();

        Date date = new Date();
        try {
            cuenta.put("numero", txtCedulaDeposito.getText().toString());
            cantidad.put("valor",txtCantidadDeposito.getText().toString());
            cantidad.put("descripcion", "Depósito de $"
                    +txtCantidadDeposito.getText().toString()+" el día "+date);
            cantidad.put("responsable", ""+global.getUsuario());

            jsonArray.put(cuenta);
            jsonArray.put(cantidad);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, url, jsonArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        //              serverResp.setText("String Response : "+ response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getContext(), "ERROR: "+ error.toString(), Toast.LENGTH_SHORT).show();
                //    serverResp.setText("Error getting response");
            }
        });
        //jsonObjectRequest.setTag(REQ_TAG);
        requestQueue.add(jsonObjectRequest);
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
