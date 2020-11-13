package com.example.carnetizacion_alcaldia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.carnetizacion_alcaldia.CarnetizacionBD;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {


    private String dato;
    private EditText txtCedula,txtNombre,txtApellido,txtFecha,txtHora;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtCedula = findViewById(R.id.txt_cedula);
        txtNombre = findViewById(R.id.txt_nombre);
        txtApellido = findViewById(R.id.txt_apellido);
        txtFecha = findViewById(R.id.txt_fecha);
        txtHora = findViewById(R.id.txt_hora);
        dato = getIntent().getStringExtra("dato"); // aca obtenemos el valor de la variable del segundo activity
        txtCedula.setText(dato);

        // debajo usamos la estructura alerta para mostrar un mensaje
        if (txtCedula.length() != 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Resultado del scanner");
            builder.setMessage("Se lee la cedula:  " + dato);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }


    }

    public void btn(View v) {  // metodo que se dezpliega desde onClick del boton y nos lleva al intent al que apunta
        Intent siguiente = new Intent(this, SegundoActivity.class);
        startActivity(siguiente);
    }

    @Override
    public void handleResult(Result result) {

    }


    public void buscarCedula(View v) {
        String ced = txtCedula.getText().toString();
        if (ced.length() == 0 ) {
            Toast.makeText(this, "Ingresar cedula para procesar",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Se esta procesando la solicitud",Toast.LENGTH_LONG).show();
            Busqueda();
        }
    }

    //metodo para consultar la cedula en la base de datos
    private void Busqueda() {
        CarnetizacionBD admin = new CarnetizacionBD(this, "administracion1", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String cedu = txtCedula.getText().toString();

        SimpleDateFormat dataFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        String fecha = dataFormat.format(date);

        if (!cedu.isEmpty()) {
            Cursor fila = BaseDeDatos.rawQuery("SELECT Nombre,Apellidos,Fecha,Hora FROM Citas WHERE Cedula =" + cedu, null);
            if(fila.moveToFirst()){
                String nombre = fila.getString(0);
                String apellidos = fila.getString(1);
                String fecha1 = fila.getString(2);
                String hora = fila.getString(3);
                txtNombre.setText(fila.getString(0));
                txtApellido.setText(fila.getString(1));
                txtFecha.setText(fila.getString(2));
                txtHora.setText(fila.getString(3));
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                if(fecha1.equals(fecha)){
                    builder.setTitle("Información");
                    builder.setMessage("El usuario: " + nombre + " "+ apellidos + " identificado con cedula: " + cedu + " tiene cita para generar carné" +
                            " el dia de hoy" +fecha1+ " a las " + hora + " AM");
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }else {
                    builder.setTitle("Información");
                    builder.setMessage("El usuario: " + nombre + " " + apellidos + " identificado con cedula: " + cedu + " no tiene cita para generar carné" +
                            " el dia de hoy " + fecha + " la fecha de su cita es el dia " + fecha1 + " a las " + hora + " AM");
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                BaseDeDatos.close();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Resultado de la consulta");
                builder.setMessage("Se ha presentado un error, la cedula: " + cedu + " no existe en la base de datos actual ó no tiene agendada cita para " +
                        "solicitar el carné, debe registrarse primero");
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                BaseDeDatos.close();
            }
        } else {
            Toast.makeText(this, "No ha ingresado la cedula para generar la busqueda",Toast.LENGTH_LONG).show();
        }

    }

    private void insertar(View view){ // metodo para insertar en la base de datos empleando un boton y sensando los campos
        CarnetizacionBD admin = new CarnetizacionBD(this, "administracion1", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String cedula = txtCedula.getText().toString();
        String nombre = txtNombre.getText().toString();
        String apellido = txtApellido.getText().toString();
        String fecha = txtFecha.getText().toString();
        String hora = txtHora.getText().toString();

        if(!cedula.isEmpty() && !nombre.isEmpty() && !apellido.isEmpty() && !fecha.isEmpty() && !hora.isEmpty()){

        } else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cuidado");
            builder.setMessage("Todos los campos deben estas llenos");
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            BaseDeDatos.close();
        }


    }


}