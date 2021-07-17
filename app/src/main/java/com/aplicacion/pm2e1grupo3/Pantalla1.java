package com.aplicacion.pm2e1grupo3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.StrictMode;
import android.provider.Settings;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Pantalla1 extends AppCompatActivity {

    Button btnsalvar, btnsalvados, btnfoto;
    EditText etnombre, ettelefono, etlatitud, etlongitud;
    ImageView ObjImagen;
    TextView txtlat, txtlong;
    Bitmap photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla1);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        btnsalvar = (Button)findViewById(R.id.btnsalvar);
        btnsalvados = (Button)findViewById(R.id.btnsalvados);
        etnombre = (EditText)findViewById(R.id.etnombre);
        ettelefono = (EditText)findViewById(R.id.ettelefono);
        etlatitud = (EditText)findViewById(R.id.etlatitud);
        etlongitud = (EditText)findViewById(R.id.etlongitud);

        etlatitud.setEnabled(false);
        etlongitud.setEnabled(false);

        ObjImagen = (ImageView) findViewById(R.id.fotografia);
        btnfoto = (Button) findViewById(R.id.btnfoto);

        btnfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                abrirCamara();
            }
        });

        btnsalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar();
                aggperson();
                Intent intent = new Intent(Pantalla1.this, Pantalla1.class);
                startActivity(intent);
                finish();
            }
        });

        btnsalvados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapa = new Intent(getApplicationContext(), Pantalla2.class);
                startActivity(mapa);
            }
        });

        Permisos();
    }

    public void Permisos(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }
    }

    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setPantalla1(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);

        //txtlat.setText("Localización agregada");
        //txtlong.setText("");
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class Localizacion implements LocationListener {
        Pantalla1 pantalla1;

        public Pantalla1 getPantalla1() {
            return pantalla1;
        }

        public void setPantalla1(Pantalla1 pantalla1) {
            this.pantalla1 = pantalla1;
        }

        @Override
        public void onLocationChanged(Location loc) {

            loc.getLatitude();
            loc.getLongitude();

            String Text1 =  ""+loc.getLatitude();
            String Text2 =  ""+loc.getLongitude();
            etlatitud.setText(Text1);
            etlongitud.setText(Text2);
            this.pantalla1.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            //GPS Desactivado
        }

        @Override
        public void onProviderEnabled(String provider) {
            //GPS Activado
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }

    private void  abrirCamara(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            ObjImagen.setImageBitmap(imgBitmap);
        }
    }

    public boolean validar(){
        boolean retorno= true;

        String nom= etnombre.getText().toString();
        String tel= ettelefono.getText().toString();
        String lat= etlatitud.getText().toString();
        String lgt= etlongitud.getText().toString();

        if(nom.isEmpty()){
            etnombre.setError("DEBE INGRESAR EL NOMBRE");
            retorno = false;
        }
        if(tel.isEmpty()){
            ettelefono.setError("DEBE INGRESAR EL NUMERO TELEFONICO");
            retorno = false;
        }
        if(lat.isEmpty() && lgt.isEmpty()){

            AlertDialog.Builder builder= new AlertDialog.Builder(Pantalla1.this);
            builder.setMessage("GPS no está activo");
            builder.setTitle("Ubicación");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Permisos();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

            retorno = false;
        }

        return retorno;
    }

    private void aggperson() {

        String url = RestApiMethod.ApiPostUrl;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error en Response", "onResponse: " +  error.getMessage().toString() );
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> parametros = new HashMap<String, String>();
                parametros.put("nombre",etnombre.getText().toString());
                parametros.put("telefono",ettelefono.getText().toString());
                //parametros.put("latitud",Double.valueOf(etlatitud.getText().toString()));
                parametros.put("latitud",etlatitud.getText().toString());
                parametros.put("longitud",etlongitud.getText().toString());
                parametros.put("foto",GetStringImage(ObjImagen));
                return parametros;
            }

        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

   public static String GetStringImage(ImageView ObjImagen)
    {
        Bitmap bitmap = ((BitmapDrawable)ObjImagen.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        byte[] imagebyte = stream.toByteArray();
        String encode = Base64.encodeToString(imagebyte, Base64.DEFAULT);
        return encode;

    }


}