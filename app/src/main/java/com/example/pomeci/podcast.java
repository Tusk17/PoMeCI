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

public class podcast extends AppCompatActivity {
    public WebView wv;
    String URLocal,URL;
    public static String DirEnviar;
    private BottomNavigationView bottomNavigationViewv;
    Intent b,c;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    FloatingActionButton marcadores;

    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_podcast);
        wv=findViewById(R.id.webView);
        SearchView sw=findViewById(R.id.sw);
        URLocal="https://podcasts.google.com/search/";
        marcadores=findViewById(R.id.floatingMarcadores);
//Floating action button
        marcadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DirEnviar= wv.getUrl();
                toNewNote();
            }
        });
//WebView
        bottomNavigationViewv=findViewById(R.id.bottomNavigationView);
        wv.loadUrl("https://podcasts.google.com/search/Salud");
        wv.setWebViewClient(new WebViewClient());
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);

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

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wv.loadUrl("https://podcasts.google.com/search/Salud");
                URLocal="https://podcasts.google.com/search/";
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wv.loadUrl("https://www.podiumpodcast.com/search/Salud");
                URLocal="https://www.podiumpodcast.com/search/";
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wv.loadUrl("https://tunein.com/radio/Retransmitir-Salud-g285/");
                URLocal="https://tunein.com/search/?query=";
            }
        });

//Bottom navigation y listeners de sus opciones
        bottomNavigationViewv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.inicioItem){

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
        }else{finish();}
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