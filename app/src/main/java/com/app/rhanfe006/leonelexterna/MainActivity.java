package com.app.rhanfe006.leonelexterna;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends ActionBarActivity implements OnClickListener {

    private EditText txtArchivo;
    private Button btnGuardar, btnAbrir;
    private static final int READ_BLOCK_SIZE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtArchivo = (EditText) findViewById(R.id.txtArchivo);
        btnGuardar = (Button)findViewById(R.id.btnGuardar);
        btnAbrir = (Button)findViewById(R.id.btnAbrir);
        btnGuardar.setOnClickListener(this);
        btnAbrir.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        File sdCard, directory, file = null;

        try {
            // verificamos si la memoria externa se cneucntra montada
            if (Environment.getExternalStorageState().equals("Memoria montada")) {

                // Obtenemos el directorio de la memoria externa
                sdCard = Environment.getExternalStorageDirectory();

                if (v.equals(btnGuardar)) {
                    String str = txtArchivo.getText().toString();

                    // con esta clase nos permite gravar el texto en el archivo
                    FileOutputStream fout = null;
                    try {

                        directory = new File(sdCard.getAbsolutePath() + "/Mis archivos");
                        // se crea el directorio donde se cerrara el archivo

                        directory.mkdirs();


                        file = new File(directory, "MiArchivo.txt");

                        fout = new FileOutputStream(file);


                        OutputStreamWriter ows = new OutputStreamWriter(fout);
                        ows.write(str); // Escribe en el buffer la cadena de texto
                        ows.flush(); // Volca lo que hay en el buffer al archivo
                        ows.close(); // Cierra el archivo de texto

                        Toast.makeText(getBaseContext(), "El archivo se ha Guardado Correctamente!!!", Toast.LENGTH_SHORT).show();

                        txtArchivo.setText("");

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

                if (v.equals(btnAbrir)) {
                    try {

                        //Obtenemos el direcorio donde se encuentra nuestro archivo a leer
                        directory = new File(sdCard.getAbsolutePath() + "/Mis archivos");

                        //Creamos un objeto File de nuestro archivo a leer
                        file = new File(directory, "MiArchivo.txt");


                        FileInputStream fin = new FileInputStream(file);

                        //Creaos un objeto InputStreamReader que permite leer el archivo abierto
                        InputStreamReader isr = new InputStreamReader(fin);

                        char[] inputBuffer = new char[READ_BLOCK_SIZE];
                        String str = "";


                        int charRead;
                        while ((charRead = isr.read(inputBuffer)) > 0) {
                            String strRead = String.copyValueOf(inputBuffer, 0, charRead);
                            str += strRead;

                            inputBuffer = new char[READ_BLOCK_SIZE];
                        }

                        // Se muestra el texto leido en la caje de texto
                        txtArchivo.setText(str);

                        isr.close();

                        Toast.makeText(getBaseContext(), "El se ha cargado correctamente", Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                }
            }else{
                Toast.makeText(getBaseContext(), "El almacenamineto externo no esta dispoible", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            // TODO: handle exception

        }
    }
}