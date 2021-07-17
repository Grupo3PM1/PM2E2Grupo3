package com.aplicacion.pm2e1grupo3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private String Dato, Nombre, Telefono, Latitud, Longitud;
    private Boolean SelectedRow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla2);
        client = new AsyncHttpClient();

        Lista = (ListView) findViewById(R.id.lista);

        btnregresar = (Button)findViewById(R.id.btnregresar);
        btnregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapa = new Intent(getApplicationContext(), Pantalla1.class);
                startActivity(mapa);
            }
        });

        ObtenerLista();  //FUNCION PARA EXTRAER DATOS DE LA BD

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

                Dato = lista.get(position).getID().toString();
                Nombre = lista.get(position).getNombre();
                Telefono = lista.get(position).getTelefono();
                Latitud = lista.get(position).getLatitud();
                Longitud = lista.get(position).getLatitud();
                SelectedRow = true;
            }
        });

        Button btnDelete = (Button) findViewById(R.id.btneliminar);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SelectedRow==true) {
                    AlertDialog.Builder builder= new AlertDialog.Builder(Pantalla2.this);
                    builder.setMessage("Desea eliminar a "+ Nombre);
                    builder.setTitle("Eliminar");

                    builder.setPositiveButton("SÍ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            EliminarItem();

                            Intent intent = new Intent(Pantalla2.this, Pantalla2.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    Toast.makeText(Pantalla2.this, "Seleccione un registro", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void ObtenerLista() {
        String URL = RestApiMethod.ApiGetUrl;

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

    private void EliminarItem() {
        // AQUÍ IRÁ EL CODIGO PARA ELIMINAR REGISTRO DE LA BD
    }
}