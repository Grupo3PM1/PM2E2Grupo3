package com.aplicacion.pm2e1grupo3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pantalla1 extends AppCompatActivity {

    Button btnsalvar, btnsalvados, btnmapa, btnfoto;
    EditText etnombre, ettelefono, etlatitud, etlongitud;
    ImageView ObjImagen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla1);

        btnmapa = (Button)findViewById(R.id.btnmapa);
        btnsalvar = (Button)findViewById(R.id.btnsalvar);
        btnsalvados = (Button)findViewById(R.id.btnsalvados);
        etnombre = (EditText)findViewById(R.id.etnombre);
        ettelefono = (EditText)findViewById(R.id.ettelefono);
        etlatitud = (EditText)findViewById(R.id.etlatitud);
        etlongitud = (EditText)findViewById(R.id.etlongitud);

        ObjImagen = (ImageView) findViewById(R.id.fotografia);
        btnfoto = (Button) findViewById(R.id.btnfoto);

        btnfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                abrirCamara();
            }
        });

        btnmapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapa = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(mapa);
            }
        });

        String lgt = getIntent().getStringExtra("longitud");
        String ltt = getIntent().getStringExtra("latitud");

        etlongitud.setText(""+lgt);
        etlatitud.setText(""+ltt);

        btnsalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar();

            }
        });

        btnsalvados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapa = new Intent(getApplicationContext(), Pantalla3.class);
                startActivity(mapa);
            }
        });

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

        if(nom.isEmpty()){
            etnombre.setError("DEBE INGRESAR EL NOMBRE");
            retorno = false;
        }
        if(tel.isEmpty()){
            ettelefono.setError("DEBE INGRESAR EL NUMERO TELEFONICO");
            retorno = false;
        }

        return retorno;
    }


}