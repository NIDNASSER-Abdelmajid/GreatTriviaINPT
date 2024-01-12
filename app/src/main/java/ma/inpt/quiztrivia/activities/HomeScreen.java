package ma.inpt.quiztrivia.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import ma.inpt.quiztrivia.R;
import ma.inpt.quiztrivia.utils.AppController;
import ma.inpt.quiztrivia.utils.SettingsPreferences;
import static ma.inpt.quiztrivia.utils.AppController.StopSound;


public class HomeScreen extends AppCompatActivity implements View.OnClickListener {

    //On crée notre bouton principal "Play"
    Button btPlayQuiz;
    //On ajoute deux ImageView pour insérer les icônes Settings et ScoreList
    ImageView imgSettingsScreen,imgScoreScreen;

    public static Context context;

    long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);


        btPlayQuiz = findViewById(R.id.bt_PlayButton);
        imgSettingsScreen = findViewById(R.id.img_settingsH);
        imgScoreScreen = findViewById(R.id.img_scoreH);

        imgSettingsScreen.setOnClickListener(this);
        imgScoreScreen.setOnClickListener(this);
        btPlayQuiz.setOnClickListener(this);

        context = getApplicationContext(); //On récupère l'état de l'application
        AppController.currentActivity = this;
        if (SettingsPreferences.getMusicEnableDisable(context)){
            try {
                //On lance la musique si le joueur a configuré Music sur ON dans SettingsPreferences
                AppController.playMusic();

            }catch (IllegalStateException e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.bt_PlayButton:
                /*
                Si l'utilisateur clique sur le bouton play, il est redirigé vers la page
                des catégories où il peut effectuer son choix
                 */
                Intent playIntent = new Intent(HomeScreen.this,CategoryActivity.class);
                startActivity(playIntent);
                finish();
                break;

            case R.id.img_settingsH:
                /*
                Si l'utilisateur clique sur l'icône Settings, il est redirigé vers la page
                des paramètres où il peut configurer l'app (Musique)
                 */
                Intent settingIntent = new Intent(HomeScreen.this,Settings.class);
                startActivity(settingIntent);
                finish();
                break;

            case R.id.img_scoreH:
                /*
                Si l'utilisateur clique sur l'icône score, il est redirigé vers la page
                des scores où il peut visualiser son record pour chaque catégorie
                 */
                Intent scoreIntent = new Intent(HomeScreen.this,ScoreActivity.class);
                startActivity(scoreIntent);
                finish();
                break;

        }

    }

    /*
    On doit définir une méthode au cas où le joueur souhaite quitter notre application,
    c'est le rôle de la méthode onBackPressed.
     */
    @Override
    public void onBackPressed() {

        //On arrête la musique si elle est lancée
        StopSound();

        if (backPressedTime + 2000 > System.currentTimeMillis()){
            /*
            La classe AlertDialog est une sous-classe de Dialog qui permet d'afficher
            un message dans une bôite d'alerte, on l'utilise ici pour demander au joueur
            s'il souhaite réellement quitter l'application avec deux chois Yes/No
            après avoir cliqué sur le bouton de retour pour une période de temps calculée
             */
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
