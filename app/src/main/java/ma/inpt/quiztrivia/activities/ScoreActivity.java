package ma.inpt.quiztrivia.activities;

import static ma.inpt.quiztrivia.utils.AppController.StopSound;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ma.inpt.quiztrivia.R;
/*
L'activité ScoreActivity sert à afficher le score du joueur dans chaque catégorie
 */
public class ScoreActivity extends AppCompatActivity {

    //On déclare les variables contenant les records du joueur pour chaque catégorie
    int infoHighScore,histgeoHighScore,sportHighScore, scienceHighScore, divHighScore;

    //On initialise nos textViews pour les catégories
    TextView txtResultForInfo,txtResultForHistgeo,txtResultForSport, txtResultForScience, txtResultForDivertissement;

    ImageView imgHomeScreen;

    public static Context context;

    long backPressedTime = 0;

    //Chaînes de caractères pour indiquer les highscore preferences de chaque catégorie
    public static final String SCOREPREFERENCE = "shared_preference";
    public static final String INFORMATIQUE = "informatique_high_score_preference";
    public static final String HISTGEO = "histgeo_high_score_preference";
    public static final String SPORT = "sport_high_score_preference";
    public static final String SCIENCE = "science_score_preference";
    public static final String DIVERTISSEMENT = "divertissement_score_preference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        txtResultForHistgeo = findViewById(R.id.txtResultForHistgeo);
        txtResultForInfo = findViewById(R.id.txtResultForInfo);
        txtResultForSport = findViewById(R.id.txtResultForSport);
        txtResultForScience = findViewById(R.id.txtResultForScience);
        txtResultForDivertissement = findViewById(R.id.txtResultForDivertissement);

        imgHomeScreen = findViewById(R.id.img_homeS);

        loadHighScore();

        imgHomeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Si l'utilisateur clique sur l'icône Home, il est redirigé vers la page d'accueil HomeScreenActivity
                Intent intent = new Intent(ScoreActivity.this, HomeScreen.class);
                startActivity(intent);
                finish();
            }
        });

    }

    //Méthode pour récupérer les records du joueur enregistré par l'objet SharedPreferences
    private void loadHighScore() {

        SharedPreferences sharedPreferencesInformatique = getSharedPreferences(SCOREPREFERENCE,MODE_PRIVATE);
        infoHighScore = sharedPreferencesInformatique.getInt(INFORMATIQUE,0);
        Log.i("Informatique SCORE", " " + infoHighScore);
        txtResultForInfo.setText("" + infoHighScore);

        SharedPreferences sharedPreferencesSport = getSharedPreferences(SCOREPREFERENCE,MODE_PRIVATE);
        sportHighScore = sharedPreferencesSport.getInt(SPORT,0);
        Log.i("Sport SCORE", " " + sportHighScore);
        txtResultForSport.setText("" + sportHighScore);

        SharedPreferences sharedPreferencesHistgeo = getSharedPreferences(SCOREPREFERENCE,MODE_PRIVATE);
        histgeoHighScore = sharedPreferencesHistgeo.getInt(HISTGEO,0);
        Log.i("Histoire Géo SCORE", " " + histgeoHighScore);
        txtResultForHistgeo.setText("" + histgeoHighScore);

        SharedPreferences sharedPreferencesScience = getSharedPreferences(SCOREPREFERENCE,MODE_PRIVATE);
        scienceHighScore = sharedPreferencesScience.getInt(HISTGEO,0);
        Log.i("Science SCORE", " " + histgeoHighScore);
        txtResultForScience.setText("" + scienceHighScore);

        SharedPreferences sharedPreferencesDiv = getSharedPreferences(SCOREPREFERENCE,MODE_PRIVATE);
        divHighScore = sharedPreferencesDiv.getInt(DIVERTISSEMENT, 0);
        Log.i("Divertissement SCORE", " " + histgeoHighScore);
        txtResultForDivertissement.setText("" + divHighScore);

    }

    @Override
    public void onBackPressed() {

        StopSound();

        if (backPressedTime + 2000 > System.currentTimeMillis()){

            new AlertDialog.Builder(this)
                    .setTitle("Do you want to Exit")
                    .setNegativeButton("No",null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            setResult(RESULT_OK,new Intent().putExtra("Exit",true));
                            finish();

                        }
                    }).create().show();

        }else {

            Toast.makeText(context, "Press Again to Exit", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();

    }
}