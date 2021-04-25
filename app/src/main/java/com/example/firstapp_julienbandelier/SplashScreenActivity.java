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
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplashScreenActivity extends AppCompatActivity {
    private static int Splash_Time_out = 4000; // temps d'attente avant affichage de l'affichage de l'activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen_activity);
        // Affichage de la page de chargement
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Intent HomeInstant = new Intent(SplashScreenActivity.this, QRCodeActivity.class);
                    startActivity(HomeInstant);
                    finish();
                }
            },Splash_Time_out);
        }
}