package com.example.tareasensegundoplano2021;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button buttonEmpezar, buttonParar,buttonParar2,buttonEmpezar2;
    TextView textViewCrono,textViewCrono2;
    Thread hilo;
    private boolean hiloActivo=false;
    private boolean entrar=false;
    int contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonEmpezar = findViewById(R.id.buttonEmpezar);
        buttonEmpezar2 = findViewById(R.id.buttonEmpezar2);
        buttonParar = findViewById(R.id.buttonParar);
        buttonParar2 = findViewById(R.id.buttonParar2);
        textViewCrono = findViewById(R.id.textViewCrono);
        textViewCrono2 = findViewById(R.id.textViewCrono2);

        buttonEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hiloActivo=true;
                hilo = new Thread() {

                    @Override
                    public void run() {
                        while (hiloActivo) {
                            int segundos = contador % 60;
                            int minutos = contador / 60;


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textViewCrono.setText(String.format("%2d:%2d", minutos, segundos));
                                }
                            });

                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            contador++;
                        }

                    }
                };
                hilo.start();
            }
        });

        buttonEmpezar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entrar=true;
                MiCronometro miCronometro=new MiCronometro(0,textViewCrono2);
                miCronometro.execute();
            }
        });

        buttonParar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hiloActivo=false;
            }
        });

        buttonParar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entrar=false;
                MiCronometro miCronometro=new MiCronometro(0,textViewCrono2);
                miCronometro.execute();
            }
        });

    }
    private class MiCronometro extends AsyncTask<String,String,String> {

        int micontador;
        TextView textView;


        MiCronometro(int inicio,TextView tv){
            micontador=inicio;
            textView=tv;
        }
        @Override
        protected void onPreExecute() {
            //antes de ejecutar

        }

        @Override
        protected String doInBackground(String... strings) {
            //mientras se ejecuta


            while(entrar){
                int segundos=micontador%60;
                int minutos=micontador/60;
                String crono=String.format("%02d:%02d",minutos,segundos);

                publishProgress(crono);
                micontador++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            textView.setText(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
