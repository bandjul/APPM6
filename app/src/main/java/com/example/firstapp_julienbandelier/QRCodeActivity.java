/* First application Android studio
 * Ecole Superieure Technique
 * Auteur : Bandelier Julien
 * Filiaire : Technicien en informatique
 * Date: 31.03.2021
 * Version: 1.0
 * Description : programme permettant de lire un Qrcode et d'accéder à un site
 */

package com.example.firstapp_julienbandelier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class QRCodeActivity extends AppCompatActivity {
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private CodeScanner mCodeScanner;
    String resultat = "";
    TextView textvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode_activity);
        // Execution du code, seulement en mode portrait.
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {

            textvalue = (TextView) findViewById(R.id.ResultText);
            CodeScannerView scannerView = findViewById(R.id.scanner_view);
            mCodeScanner = new CodeScanner(this, scannerView);

            SharedPreferences preferences = getSharedPreferences("SITE_PREF", Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = preferences.edit();
            // Récupère la dernière valeur scanné
            if (preferences.contains(("LinkOfSite")))
                resultat = preferences.getString("LinkOfSite", "");
            textvalue.setText(resultat);

            // Affiche pop-up qui demande la permission pour la caméra
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            }
            // Fonction de scannage
            mCodeScanner.setDecodeCallback(new DecodeCallback() {
                @Override
                public void onDecoded(@NonNull final Result result) {

                    runOnUiThread(new Runnable() {

                        // Affichage du lien scanné
                        @Override
                        public void run() {
                            resultat = result.getText();
                            textvalue.setText(resultat);
                            editor.putString("LinkOfSite", resultat);
                            editor.commit();
                        }
                    });
                }
            });

            scannerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCodeScanner.startPreview();
                }
            });
        }
    }

    // Fonction du bouton "Accéder au site"
    public void OpenSite(View view) {
        // Ouverture de l'activité WebView
        // verifier si QRCode est un site, si oui ouvrir l'activité Webview
        if (resultat.startsWith("http") || resultat.startsWith("www")  ) {
            Intent intent = new Intent(this, WebviewActivity.class);
            intent.putExtra("site", resultat);
            startActivity(intent);
        }
        // Pop-up si rien n'est scanné
        else if (resultat.isEmpty()) {
            Toast.makeText(QRCodeActivity.this, getString(R.string.ErrorTexteQREmpty), Toast.LENGTH_SHORT).show();
        }
        // Pop-up si le texte n'est pas un site
        else {
            Toast.makeText(QRCodeActivity.this, getString(R.string.ErrorTexteQRNotSite), Toast.LENGTH_SHORT).show();
        }
    }
}


