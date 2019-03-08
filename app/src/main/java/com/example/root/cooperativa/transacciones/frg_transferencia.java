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
 * {@link frg_transferencia.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frg_transferencia#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frg_transferencia extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RequestQueue requestQueue;

    private JSONArray jsonArray = new JSONArray();


    Global global = new Global();


    private String url = "http://"+global.getIp()+":8080/Proyecto/api/cuenta/transferencia";
    private EditText txtCedulaTransferenciaEnvia, txtCedulaTransferenciaRecibe, txtCantidadDepositoTransferencia;
    private Button btnAceptarTransferencia;

    private OnFragmentInteractionListener mListener;

    public frg_transferencia() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frg_transferencia.
     */
    // TODO: Rename and change types and number of parameters
    public static frg_transferencia newInstance(String param1, String param2) {
        frg_transferencia fragment = new frg_transferencia();
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
        View vista = inflater.inflate(R.layout.fragment_frg_transferencia, container, false);
        txtCedulaTransferenciaEnvia = (EditText)vista.findViewById(R.id.txt_tranferencia_cedulaEnvia);
        txtCedulaTransferenciaRecibe = (EditText)vista.findViewById(R.id.txt_transferencia_cedulaRecibe);
        txtCantidadDepositoTransferencia = (EditText)vista.findViewById(R.id.txt_transferencia_cantidad);
        btnAceptarTransferencia = (Button)vista.findViewById(R.id.btn_transferencia_aceptar);
        btnAceptarTransferencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();
            }
        });

        requestQueue = Volley.newRequestQueue(getActivity());
        return vista;
    }

    private void cargarWebService(){

        JSONObject cantidad = new JSONObject();
        JSONObject envia = new JSONObject();
        JSONObject recibe = new JSONObject();

        Date date = new Date();
        try {
            envia.put("numeroR", txtCedulaTransferenciaEnvia.getText().toString());
            recibe.put("numeroD", txtCedulaTransferenciaRecibe.getText().toString());
            cantidad.put("valor",txtCantidadDepositoTransferencia.getText().toString());
            cantidad.put("descripcion", "El número de cuenta: "
                    +txtCedulaTransferenciaEnvia.getText().toString()+" realizó una transferencia de $"
                    +txtCantidadDepositoTransferencia.getText().toString()+" el día "+date+" a la cuenta "+txtCedulaTransferenciaRecibe.getText().toString());
            cantidad.put("responsable", ""+global.getUsuario());

            jsonArray.put(envia);
            jsonArray.put(recibe);
            jsonArray.put(cantidad);
            //Toast.makeText(getContext(), "Respuesta: "+ jsonArray.toString(), Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, url, jsonArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Toast.makeText(getContext(), "Transferencia realizada", Toast.LENGTH_SHORT).show();
                        //              serverResp.setText("String Response : "+ response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Transferencia realizada", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(), "ERROR: "+ error.toString(), Toast.LENGTH_SHORT).show();
                //    serverResp.setText("Error getting response");
            }
        });
        //jsonObjectRequest.setTag(REQ_TAG);
        requestQueue.add(jsonObjectRequest);
        txtCantidadDepositoTransferencia.setText("");
        txtCedulaTransferenciaRecibe.setText("");
        txtCedulaTransferenciaEnvia.setText("");
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
