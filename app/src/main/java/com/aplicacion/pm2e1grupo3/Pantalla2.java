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

import java.util.ArrayList;

public class Pantalla2 extends AppCompatActivity {

    Button btnregresar;
    EditText buscar;
    ListView Lista;
    ArrayList<String> ArrayItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla2);

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
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, ArrayContactos);
        Lista.setAdapter(adp);

        buscar = (EditText) findViewById(R.id.txtbuscar);
        buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adp.getFilter().filter(s);
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

            }
        });
    }


    private void ObtenerLista() {

        // AQUÍ SE OBTENDRAN LOS DATOS DIRECTAMENTE DESDE LA BD CON CONSULTA SQL

        FillList();
    }

    private void FillList() {

        // AQUÍ SE VISUALIZARAN LOS ITEMS

    }

    /*private void buscarProducto(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
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