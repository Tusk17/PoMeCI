package com.example.pomeci;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class salud_articulos extends AppCompatActivity {
    public WebView wv;
    String URLocal,URL;
    public static String DirEnviar;
    private BottomNavigationView bottomNavigationViewv;
    Intent b,c;
    ProgressBar progressBar;
    ProgressDialog progressDialog;

    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.articulos_salud);
        wv=findViewById(R.id.webView);
        SearchView sw=findViewById(R.id.sw);
        URLocal="https://www.paho.org/journal/es/search/node?keys=";

//WebView
        bottomNavigationViewv=findViewById(R.id.bottomNavigationView);
        wv.loadUrl("https://www.paho.org/journal/es/articulos");
        wv.setWebViewClient(new WebViewClient());
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);

        FloatingActionButton marcadores=findViewById(R.id.floatingMarcadores);
//Floating action button
        marcadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DirEnviar= wv.getUrl();
                toNewNote();
            }
        });

        //ProgressBar
        progressBar=(ProgressBar)findViewById(R.id.progressBar) ;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando, espere....");
        wv.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(android.webkit.WebView view, int newProgress) {

                progressBar.setVisibility(View.VISIBLE);

                progressBar.setProgress(newProgress);
                setTitle("Cargando....");
                progressDialog.show();
                if (newProgress == 100){
                    progressBar.setVisibility(View.GONE);
                    setTitle(view.getTitle());
                    progressDialog.dismiss();
                }
                super.onProgressChanged(view, newProgress);
            }
        });

//Botones y listeners
        Button b1=findViewById(R.id.b1);
        Button b2=findViewById(R.id.b2);
        Button b3=findViewById(R.id.b3);
        Button b4=findViewById(R.id.b4);
        Button b5=findViewById(R.id.b5);
        Button b6=findViewById(R.id.b6);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wv.loadUrl("https://www.paho.org/journal/es/articulos");
                URLocal="https://www.paho.org/journal/es/search/node?keys=";
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wv.loadUrl("https://www.elespanol.com/ciencia/salud/");
                URLocal="https://www.elespanol.com/buscador/?text=";
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wv.loadUrl("https://news.un.org/es/news/topic/health");
                URLocal="https://news.un.org/es/search/";
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wv.loadUrl("https://www.eluniversal.com.mx/ciencia-y-salud");
                URLocal="https://www.eluniversal.com.mx/resultados-busqueda/";
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wv.loadUrl("https://salud.nih.gov/temas-de-salud/");
                URLocal="https://search.nih.gov/search?utf8=%E2%9C%93&affiliate=nih-espanol&query=";
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wv.loadUrl("https://www.milenio.com/content/salud-nutricion");
                URLocal="https://www.milenio.com/buscador?text=";
            }
        });
//Bottom navigation y listeners de sus opciones
        bottomNavigationViewv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.inicioItem){
                    wv.clearHistory();
                    wv.clearCache(true);
                    finish();
                }
                if(item.getItemId()==R.id.buscarItem){
                    sw.setVisibility(View.VISIBLE);
                    sw.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            funka();
                            sw.setVisibility(View.GONE);
                            URL=sw.getQuery().toString();
                            wv.loadUrl(URLocal+URL);
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            return false;
                        }
                    });
                }
                if(item.getItemId()==R.id.marcadorItem){
                    updateUI();
                }
            return false;
            }
        });
    }
//Metodos fuera de OnCreate
    private void funka() {
        Toast.makeText(this,"Realizando la busqueda",Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {
            wv.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }
    private void updateUI() {
            Intent b = new Intent(this, Marcadores.class);
            startActivity(b);
    }
    private void toNewNote() {
        Intent c = new Intent(this, New_Note.class);
        c.putExtra(DirEnviar,DirEnviar);
        startActivity(c);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String Dir = intent.getStringExtra(Marcadores.Dire);
        wv.loadUrl(Dir);
    }
}
