/* First application Android studio
 * Ecole Superieure Technique
 * Auteur : Bandelier Julien
 * Filiaire : Technicien en informatique
 * Date: 31.03.2021
 * Version: 1.0
 * Description : programme permettant de lire un Qrcode et d'accéder à un site
 */
package com.example.firstapp_julienbandelier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.webkit.WebViewClient;

public class WebviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);
        android.webkit.WebView webView;
        webView = (android.webkit.WebView)findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());

        // Affichage du site scanné
        Intent intent = getIntent();
        String SiteLink = "";
        if (intent.hasExtra("site")){
            SiteLink = intent.getStringExtra("site");
            webView.loadUrl(SiteLink.toString());
        }
    }
}