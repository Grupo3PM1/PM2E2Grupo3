package com.aplicacion.pm2e1grupo3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.aplicacion.pm2e1grupo3.tablas.lista;

import java.util.ArrayList;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

public class Pantalla2 extends AppCompatActivity {

    Button btnregresar;
    EditText buscar;
    ListView Lista;
    ArrayAdapter<lista> a;
    ArrayList <lista> lista;
    ArrayList<String> ArrayLista;
    private AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla2);
        client = new AsyncHttpClient();

        btnregresar = (Button)findViewById(R.id.btnregresar);
        btnregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapa = new Intent(getApplicationContext(), Pantalla1.class);
                startActivity(mapa);
            }
        });


        ObtenerLista();  //FUNCION PARA EXTRAER DATOS DE LA BD
        Lista = (ListView) findViewById(R.id.lista);


        buscar = (EditText) findViewById(R.id.txtbuscar);
        buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                a.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        Lista.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        Lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);

                // AQUÍ SE OBTENDRAN LOS DATOS DEL ITEM SELECCIONADO
                ObtenerLista();

            }
        });
    }


    private void ObtenerLista() {
        String URL = "";
        client.post(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    listarRegistros(new String (responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    private void listarRegistros(String respuesta){
        lista = new ArrayList<lista>();
        try {
            JSONArray jsonAreglo = new JSONArray(respuesta);
            for(int i=0;i<jsonAreglo.length();i++){
                lista l = new lista();
                l.setID(jsonAreglo.getJSONObject(i).getInt("id"));
                l.setNombre(jsonAreglo.getJSONObject(i).getString("nombre"));
                l.setTelefono(jsonAreglo.getJSONObject(i).getString("telefono"));
                l.setLatitud(jsonAreglo.getJSONObject(i).getString("latitud"));
                l.setLongitud(jsonAreglo.getJSONObject(i).getString("longitud"));
                //l.setImage(jsonAreglo.getJSONObject(i).get("foto"));
                lista.add(l);
            }

            FillList();
            a = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, ArrayLista);
            Lista.setAdapter(a);
        }
        catch (Exception e1){
            e1.printStackTrace();
        }

    }

    private void FillList() {

        ArrayLista = new ArrayList<String>();

        for (int i = 0;  i < lista.size(); i++){

            ArrayLista.add(lista.get(i).getNombre());
        }

    }

    /*private void buscarProducto(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                lista Item = null;
                ArrayLista = new ArrayList<lista>();

                for (int i = 0; i < response.length(); i++){
                    jsonObject = response.getJSONObject(i);
                    Item = new lista();
                    //String texto=jsonObject.getString("Apellido");
                    Item.setID(jsonObject.getInt("id"));
                    playerModel.setName(dataobj.getString("name"));

                    especieAnimal.setText(item.getEspecie());
                    Item.setNombre(jsonObject.getString("nombre"));
                    Item.setTelefono(jsonObject.getString("telefono"));
                    Item.setLatitud(jsonObject.getString("latitud"));
                    Item.setLongitud(jsonObject.getString("longitud"));
                    Item.setImage(jsonObject.getString("foto"));
                    ArrayLista.add(Item);
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        etxtPais.setText(jsonObject.getString("De_pais"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonArrayRequest);
    }*/
}