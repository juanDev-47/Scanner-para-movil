package com.example.carnetizacion_alcaldia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

public class CarnetizacionBD extends SQLiteOpenHelper {
    //private static final String NOMBRE_BD = "administracion";
    //private static final String TABLA_CITAS = "CREATE TABLE Citas ( id INTEGER NOT NULL, Cedula TEXT NOT NULL, Nombre TEXT NOT NULL, Apellidos TEXT NOT NULL, Fecha TEXT NOT NULL, Hora TEXT NOT NULL, Estado boolean DEFAULT 1, PRIMARY KEY(id))";

    public CarnetizacionBD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {
        BaseDeDatos.execSQL("CREATE TABLE Citas ( id int primary key, Cedula text, Nombre text, Apellidos text, Fecha text, Hora text)");
        BaseDeDatos.execSQL("INSERT INTO Citas (Cedula,Nombre,Apellidos,Fecha,Hora ) VALUES ('1037633965','Juan Pablo', 'Arenas Velez', '13-11-2020', '11:30:00')");
        BaseDeDatos.execSQL("INSERT INTO Citas (Cedula,Nombre,Apellidos,Fecha,Hora ) VALUES ('1017229224','Francisco Javier', 'Silva Cadavid', '30-11-2020', '12:30:00')");
        BaseDeDatos.execSQL("INSERT INTO Citas (Cedula,Nombre,Apellidos,Fecha,Hora ) VALUES ('1001368569','Santiago', 'Valencia Marulanda', '04-12-2020', '01:30:00')");
        BaseDeDatos.execSQL("INSERT INTO Citas (Cedula,Nombre,Apellidos,Fecha,Hora ) VALUES ('1007241215','Felipe', 'Gonzalez Diaz', '11-12-2020', '03:00:00')");
        BaseDeDatos.execSQL("INSERT INTO Citas (Cedula,Nombre,Apellidos,Fecha,Hora ) VALUES ('71375739','Andres Felipe', 'Graciano Monsalve', '25-12-2020', '12:30:00')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }



}
