package com.example.notas;

import static java.lang.Double.parseDouble;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notas.db.helpers.DBnotas;
import com.example.notas.models.Notas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditarNotas extends AppCompatActivity {

    EditText txtNombre, txtnota1, txtnota2, txtnota3, txtnota4;
    Button btnGuarda;
    boolean correcto=false;
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

        final DBnotas dbNotas= new DBnotas(EditarNotas.this);
        nota = dbNotas.verEstudiante(id);

        if(nota != null){
            txtNombre.setText(nota.getNombre());
            txtnota1.setText(nota.getNota_1().toString());
            txtnota2.setText(nota.getNota_2().toString());
            txtnota3.setText(nota.getNota_3().toString());
            txtnota4.setText(nota.getNota_4().toString());
             }

        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtNombre.getText().toString().equals("")) {
                    correcto = dbNotas.editNota(id, txtNombre.getText().toString(), parseDouble(txtnota1.getText().toString().trim()), parseDouble(txtnota2.getText().toString().trim()), parseDouble(txtnota3.getText().toString().trim()), parseDouble(txtnota4.getText().toString().trim()));

                    if(correcto){
                        Toast.makeText(EditarNotas.this, "NOTAS MODIFICADAS", Toast.LENGTH_LONG).show();
                        verRegistro();
                    } else {
                        Toast.makeText(EditarNotas.this, "ERROR AL MODIFICAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(EditarNotas.this, "DEBE LLENAR EL CAMPO DE NOMBRE", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void verRegistro(){
        Intent intent = new Intent(this, VerEstudiante.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }
}
