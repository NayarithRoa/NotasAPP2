package com.example.notas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.notas.db.helpers.DBnotas;
import com.example.notas.models.Notas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VerEstudiante extends AppCompatActivity {

    EditText txtNombre, txtnota1, txtnota2, txtnota3, txtnota4;
    Button btnGuarda;
    FloatingActionButton fabEditar, fabEliminar;

    Notas nota;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_estudiante);

        txtNombre = findViewById(R.id.txtNombre);
        txtnota1 = findViewById(R.id.txtnota1);
        txtnota2 = findViewById(R.id.txtnota2);
        txtnota3 = findViewById(R.id.txtnota3);
        txtnota4 = findViewById(R.id.txtnota4);
        btnGuarda = findViewById(R.id.btnGuarda);
        btnGuarda.setVisibility(View.INVISIBLE);
        fabEditar = findViewById(R.id.fabEditar);
        fabEliminar = findViewById(R.id.fabEliminar);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        final DBnotas dbNotas= new DBnotas(VerEstudiante.this);
        nota = dbNotas.verEstudiante(id);

        if(nota != null){
            txtNombre.setText(nota.getNombre());
            txtnota1.setText(nota.getNota_1().toString());
            txtnota2.setText(nota.getNota_2().toString());
            txtnota3.setText(nota.getNota_3().toString());
            txtnota4.setText(nota.getNota_4().toString());
            txtNombre.setInputType(InputType.TYPE_NULL);
            btnGuarda.setVisibility(View.INVISIBLE);
            txtNombre.setInputType(InputType.TYPE_NULL); //No permita que se habilite el teclado para escribir
            txtnota1.setInputType(InputType.TYPE_NULL);
            txtnota2.setInputType(InputType.TYPE_NULL);
            txtnota3.setInputType(InputType.TYPE_NULL);
            txtnota4.setInputType(InputType.TYPE_NULL);
        }

        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerEstudiante.this, EditarNotas.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });
    }
}