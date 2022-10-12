package com.example.notas.db.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;
import com.example.notas.db.contracts.DbReaderContract.NotasSchema;
import com.example.notas.models.Notas;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBnotas extends DbHelper {

    private Context context;

    public DBnotas(@Nullable Context context) {
        super(context);
        this.context = context;
    }
    public long insert(Notas notas){
        boolean correcto=false;

        DbHelper dbHelper = new DbHelper(this.context);
        SQLiteDatabase db = dbHelper.getWritableDatabase(); //Crea base de datos
        try{

            ContentValues contentValues = new ContentValues();
            contentValues.put(NotasSchema.COLUMN_NAME_NOMBRES, notas.getNombre());
            contentValues.put(NotasSchema.COLUMN_NAME_NOTA1, notas.getNota_1());
            contentValues.put(NotasSchema.COLUMN_NAME_NOTA2, notas.getNota_2());
            contentValues.put(NotasSchema.COLUMN_NAME_NOTA3, notas.getNota_3());
            contentValues.put(NotasSchema.COLUMN_NAME_NOTA4, notas.getNota_4());
            return db.insert(NotasSchema.TABLE_NAME,null,contentValues);
        }catch (Exception ex){
            System.err.println(ex);
            return 0;
        }
    }

    public ArrayList<Notas> mostrarEstudiantes() {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase(); //Escribe

        ArrayList<Notas> listaEstudiante = new ArrayList<>();
        Notas nota;
        Cursor cursorEstudiante;

        cursorEstudiante = db.rawQuery("SELECT * FROM " + NotasSchema.TABLE_NAME + " ORDER BY nombres ASC", null);

        if (cursorEstudiante.moveToFirst()) {
            do {
                nota = new Notas();
                nota.setId(cursorEstudiante.getInt(0));
                nota.setNombre(cursorEstudiante.getString(1));

                listaEstudiante.add(nota);
            } while (cursorEstudiante.moveToNext());
        }

        cursorEstudiante.close();

        return listaEstudiante;
    }

    public Notas verEstudiante(int id) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase(); //Escribe

        Notas nota = null;
        Cursor cursorEstudiante;

        cursorEstudiante = db.rawQuery("SELECT * FROM " + NotasSchema.TABLE_NAME + " WHERE _ID=" + id + " LIMIT 1", null);

        if (cursorEstudiante.moveToFirst()) {

                nota = new Notas();
                nota.setId(cursorEstudiante.getInt(0));
                nota.setNombre(cursorEstudiante.getString(1));
                nota.setNota_1(cursorEstudiante.getDouble(2));
                nota.setNota_2(cursorEstudiante.getDouble(3));
                nota.setNota_3(cursorEstudiante.getDouble(4));
                nota.setNota_4(cursorEstudiante.getDouble(5));
        }

        cursorEstudiante.close();

        return nota;
    }
    public boolean editNota(int id, String nombre, Double nota1, Double nota2, Double nota3, Double nota4) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(this.context);
        SQLiteDatabase db = dbHelper.getWritableDatabase(); //Crea base de datos

        try {
            db.execSQL("UPDATE " + NotasSchema.TABLE_NAME + " SET nombres = '" + nombre + "', nota1 = '" + nota1 + "', nota2 = '" + nota2 + "', nota3 = '" + nota3 + "',nota4 = '" + nota4 + "' WHERE _id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
}
