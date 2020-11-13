package com.example.carnetizacion_alcaldia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class SegundoActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private ZXingScannerView mScannerView;
    private String mensaje,men,resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segundo);
    }

    public void btn(View v) {  // metodo que se dezpliega desde onClick del boton y dezpliegua el scanner
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void handleResult(Result result) {
        Intent anterior = new Intent(this, MainActivity.class);
        Log.v("HandleResult",result.getText());
        Log.v("result", result.getBarcodeFormat().toString());
        mensaje = result.getText();
        men = mensaje.substring(48,58);
        resultado = manejoCaracteres(men);  // metodo retorna el numero de la cedula ya formateada en la variable resultado
        anterior.putExtra("dato", resultado);
        Toast.makeText(this, resultado,Toast.LENGTH_LONG).show();
        //builder.setMessage("Se lee la cedula:  " + resultado);
        //AlertDialog alertDialog = builder.create();
        //alertDialog.show();
        startActivity(anterior);

        //mScannerView.resumeCameraPreview(this); // con esta sentencia aseguramos que despues de leer un qr podamos seguir leyendo mas

    }

    // crear metodo para ser usado con la cedula = a la variable resultado
    //con lo cual se desea buscar en la base de datos para traer el registro


    public String manejoCaracteres(String mensaje){
        String cedula = mensaje;
        int num = Integer.parseInt(cedula);
        cedula = Integer.toString(num);


        return cedula;
    }


    // metodo de retorno al primer intent
    public void regreso(View view){
        Intent anterior = new Intent(this, MainActivity.class);
        startActivity(anterior);


    }
}