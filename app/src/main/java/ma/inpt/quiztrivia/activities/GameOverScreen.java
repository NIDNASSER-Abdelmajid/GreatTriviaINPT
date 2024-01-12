package ma.inpt.quiztrivia.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ma.inpt.quiztrivia.constants.Constants;
import ma.inpt.quiztrivia.R;

/*
L'activité GameOverScreen s'affiche à l'utilisateur quand il n'a plus de vies (coeurs)
Elle affiche le nombre de bonnes réponses obtenues et le score total
 */
public class GameOverScreen extends AppCompatActivity {


    ImageView imgPlayAgain,imgPlayScreen,imgScoreScreen;
    TextView txtTotalScore,txtWrongQues,txtCorrectQues;

    int infoHighScore,histgeoHighScore,sportHighScore, scienceHighScore, divHighScore;

    public static final String SCOREPREFERENCE = "shared_preference";
    public static final String INFORMATIQUE = "informatique_high_score_preference";
    public static final String HISTGEO = "histgeo_high_score_preference";
    public static final String SPORT = "sport_high_score_preference";
    public static final String SCIENCE = "science_high_score_preference";
    public static final String DIVERTISSEMENT = "divertissement_score_preference";

    String quizCategory;
    int totalQuestion,wrong,correct,score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover_screen);

        imgPlayAgain = findViewById(R.id.img_Replay);
        imgPlayScreen = findViewById(R.id.img_home);
        imgScoreScreen = findViewById(R.id.img_Score);

        txtCorrectQues = findViewById(R.id.txtRightAnsContent);
        txtWrongQues = findViewById(R.id.txtWrongAnsContent);
        txtTotalScore = findViewById(R.id.txtTotalQues);


        Intent intentContent = getIntent();
        totalQuestion = intentContent.getIntExtra(Constants.TOTAL_QUESTIONS,0);
        score = intentContent.getIntExtra(Constants.SCORE,0);
        correct = intentContent.getIntExtra(Constants.CORRECT,0);
        wrong = intentContent.getIntExtra(Constants.WRONG,0);

        txtTotalScore.setText(Constants.TOTAL_QUESTIONS + totalQuestion);
        txtCorrectQues.setText(String.valueOf(correct));
        txtWrongQues.setText(String.valueOf(wrong));

        quizCategory = intentContent.getStringExtra("Category"); //On récupère la catégorie choisie par le joueur

        loadHighScore();

        //On vérifie la catégorie choisi par le joueur pour mettre à jour le record "highscore"

        if (quizCategory.equals("Informatique")){

            updateInfoHighScores(score);

        }else if (quizCategory.equals("HistoireGeographie")){

            updateHistgeoHighScores(score);

        }else if (quizCategory.equals("Sport")){

            updateSportHighScores(score);
        }else if (quizCategory.equals("Science")){

            updateScienceHighScores(score);
        }else if (quizCategory.equals("Divertissement")){

            updateDivHighScores(score);
        }

        //Si le joueur veut consulter son score, on ajoute un intent vers ScoreActivity
        imgScoreScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(GameOverScreen.this,ScoreActivity.class);
                startActivity(intent);
                finish();

            }
        });

        //Si le joueur veut revenir à la page d'accueil, on ajoute un intent vers HomeScreen
        imgPlayScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(GameOverScreen.this, HomeScreen.class);
                startActivity(intent);
                finish();

            }
        });

        //Si le joueur veut rejouer, on ajoute un intent vers QuizActivity
        imgPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(GameOverScreen.this,QuizActivity.class);
                intent.putExtra("Category",quizCategory);
                startActivity(intent);
                finish();
            }
        });

    }

    /*
    Les  5 méthodes suivantes servent à mettre à jour le record du joueur pour chaque catégorie,
    en comparant son score  avec son dernier record enregistré grâce à l'objet SharedPreferences.
     */

    private void updateInfoHighScores(int scoreToUpdate){

        if(scoreToUpdate > infoHighScore){

            infoHighScore = scoreToUpdate;
            SharedPreferences sharedPreferences = getSharedPreferences(SCOREPREFERENCE,MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(INFORMATIQUE,infoHighScore);
            editor.apply();

        }
    }

    private void updateHistgeoHighScores(int scoreToUpdate){

        if(scoreToUpdate > histgeoHighScore){

            histgeoHighScore = scoreToUpdate;
            SharedPreferences sharedPreferences = getSharedPreferences(SCOREPREFERENCE,MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(HISTGEO,histgeoHighScore);
            editor.apply();
        }

    }

    private void updateSportHighScores(int scoreToUpdate){

        if(scoreToUpdate > sportHighScore){

            sportHighScore = scoreToUpdate;
            SharedPreferences sharedPreferences = getSharedPreferences(SCOREPREFERENCE,MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(SPORT,sportHighScore);
            editor.apply();
        }

    }

    private void updateScienceHighScores(int scoreToUpdate){

        if(scoreToUpdate > scienceHighScore){

            scienceHighScore = scoreToUpdate;
            SharedPreferences sharedPreferences = getSharedPreferences(SCOREPREFERENCE,MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(SCIENCE,scienceHighScore);
            editor.apply();
        }

    }

    private void updateDivHighScores(int scoreToUpdate){

        if(scoreToUpdate > divHighScore){

            divHighScore = scoreToUpdate;
            SharedPreferences sharedPreferences = getSharedPreferences(SCOREPREFERENCE,MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(DIVERTISSEMENT,divHighScore);
            editor.apply();
        }

    }

    /*
    Cette dernière méthode va être aussi utilisée dans ScoreActivity
    pour afficher le HighScore du joueur dans chaque catégorie
    toujours en utilisant les données mémorisées par SharedPreferences
     */
    private void loadHighScore() {

        SharedPreferences sharedPreferencesInformatique = getSharedPreferences(SCOREPREFERENCE,MODE_PRIVATE);
        infoHighScore = sharedPreferencesInformatique.getInt(INFORMATIQUE,0);
        Log.i("Informatique SCORE", " " + infoHighScore);


        SharedPreferences sharedPreferencesHistgeo = getSharedPreferences(SCOREPREFERENCE,MODE_PRIVATE);
        histgeoHighScore = sharedPreferencesHistgeo.getInt(HISTGEO,0);
        Log.i("Histoire Géo SCORE", " " + histgeoHighScore);


        SharedPreferences sharedPreferencesSport = getSharedPreferences(SCOREPREFERENCE,MODE_PRIVATE);
        sportHighScore = sharedPreferencesSport.getInt(SPORT,0);
        Log.i("Sport SCORE", " " + sportHighScore);

        SharedPreferences sharedPreferencesScience = getSharedPreferences(SCOREPREFERENCE,MODE_PRIVATE);
        scienceHighScore = sharedPreferencesScience.getInt(SCIENCE,0);
        Log.i("Science SCORE", " " + scienceHighScore);

        SharedPreferences sharedPreferencesDiv = getSharedPreferences(SCOREPREFERENCE,MODE_PRIVATE);
        divHighScore = sharedPreferencesDiv.getInt(DIVERTISSEMENT,0);
        Log.i("Divertissement SCORE", " " + divHighScore);

    }
}