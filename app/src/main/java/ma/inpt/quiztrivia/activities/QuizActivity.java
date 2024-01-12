package ma.inpt.quiztrivia.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import ma.inpt.quiztrivia.constants.Constants;
import ma.inpt.quiztrivia.utils.PlayAudio;
import ma.inpt.quiztrivia.R;
import ma.inpt.quiztrivia.dialogs.TimerDialog;
import ma.inpt.quiztrivia.data.TriviaQuestion;
import ma.inpt.quiztrivia.db.TriviaQuizHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {

    /*
    On crée les 4 bouttons correspondants à chaque réponse proposée
    et 3 TextViews où on va insérer notre question, la catégorie en header et le compteur
     */
    Button buttonA,buttonB,buttonC,buttonD;
    TextView questionText,txtCategoryHeading,timeText;

    TriviaQuizHelper triviaQuizHelper;
    TriviaQuestion currentQuestion;

    int qid = 1; //Variable qui stocke l'id de la question currentQuestion

    int sizeofQuiz = 6; // total size of Quiz

    /*
    Un Handler est une classe threading par laquelle nous pouvons envoyer et traiter des objets
    Message et Runnable associés à la MessageQueue d’un thread.
     */
    private final Handler handler = new Handler();
    private final Handler handler2 = new Handler();

    AnimationDrawable anim;

    //Les imageViews pour chaque LifeLine
    ImageView imgLifeline1;
    ImageView imgLifeline2;
    ImageView imgLifeline3;

    //Le nombre de vies allouées à chaque joueur pour chaque round est 3
    int lifeLineVal = 3;


    private static final long COUNTDOWN_IN_MILLIS = 32000;

    private CountDownTimer countDownTimer;

    private long timeLeftMillis;

    long savedTime =0;


    private TimerDialog timerDialog;

    //Variables qu'on va modifier selon le nombre de réponses correctes/mauvaises du joueur
    int correct=0;
    int wrong = 0;
    int score = 0;

    //On ajoute les animations lorsque l'utilisateur choisit une bonne ou mauvaise réponse
    Animation correctAnsAnimation;
    Animation wrongAnsAnimation;

    int FLAG = -1;
    PlayAudio playAudio;

    //Variable de la catégorie
    String categoryValue;

    long backPressedTime = 0;
    private ArrayList<TriviaQuestion> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionText = findViewById(R.id.txtTriviaQuestion);
        txtCategoryHeading = findViewById(R.id.txtCategoryHeading);
        buttonA = findViewById(R.id.buttonA);
        buttonB = findViewById(R.id.buttonB);
        buttonC = findViewById(R.id.buttonC);
        buttonD = findViewById(R.id.buttonD);
        timeText = findViewById(R.id.txtTimer);

        imgLifeline1 = findViewById(R.id.imgLifeLine1);
        imgLifeline2 = findViewById(R.id.imgLifeLine2);
        imgLifeline3 = findViewById(R.id.imgLifeLine3);

        /*
        On récupère la catégorie choisie précédemment par le joueur
        et on l'affiche comme header de la page du quiz
         */

        Intent intentCategory = getIntent();
        categoryValue = intentCategory.getStringExtra("Category");
        txtCategoryHeading.setText(categoryValue);

        /*
        On lance le compteur avec la classe TimerDialog
         */
        timerDialog = new TimerDialog(this);

        correctAnsAnimation = AnimationUtils.loadAnimation(this,R.anim.correct_ans_animation);
        correctAnsAnimation.setRepeatCount(3);

        wrongAnsAnimation = AnimationUtils.loadAnimation(this,R.anim.wrong_ans_animation);
        wrongAnsAnimation.setRepeatCount(3);

        //On lance la musique
        playAudio = new PlayAudio(this);

        //On appelle une nouvelle instance de TriviaQuizHelper et on ouvre la BDD
        triviaQuizHelper = new TriviaQuizHelper(this);
        triviaQuizHelper.getReadableDatabase();

        //On récupère les questions de la category "CategoryValue" avec getQuestionsWithCategory dans une liste
        list = triviaQuizHelper.getQuestionsWithCategory(categoryValue);

        //On utilise la méthode Shuffle de collections pour générer les questions de manière aléatoire
        Collections.shuffle(list);

        //On insère l'objet TriviaQuestion avec l'identifiant qid dans currentQuestion
        currentQuestion = list.get(qid);

        updateQueAnsOptions(); //On met à jour les éléments de réponses et la question à chaque fois qu'on termine une question


    }

    private void updateQueAnsOptions() {

        buttonA.setBackgroundResource(R.drawable.default_options_bg);
        buttonB.setBackgroundResource(R.drawable.default_options_bg);
        buttonC.setBackgroundResource(R.drawable.default_options_bg);
        buttonD.setBackgroundResource(R.drawable.default_options_bg);

        questionText.setText(currentQuestion.getQuestion()); //Le texte de la question
        buttonA.setText(currentQuestion.getOption1()); //Réponse N°1
        buttonB.setText(currentQuestion.getOption2()); //Réponse N°2
        buttonC.setText(currentQuestion.getOption3()); //Réponse N°3
        buttonD.setText(currentQuestion.getOption4()); //Réponse N°4


        timeLeftMillis = COUNTDOWN_IN_MILLIS; //Compteur du temps restant
        startCountDown(); //On commence le décompte

    }

    //Méthode pour préparer la question suivante
    private void SetNewQuestion(){

        /*
        On incrémente qid pour obtenir un autre objet TriviaQuestion
        dans CurrentQuestion et donc passer à une autre question
         */
        qid++;
        currentQuestion = list.get(qid);

        enableOptions();
        updateQueAnsOptions();
    }

    //La méthode qui calcule le nombre de vies du joueur dans un round
    void healthFunctionality(){
        /*
       - Si le joueur a choisi une réponse fausse, l'icône qui représente une vie disparaît
       - On décrémente le nombre de vies
         */

        if (wrong == 1){
            imgLifeline3.setVisibility(View.INVISIBLE);
            lifeLineVal--;
        }else if (wrong == 2){
            imgLifeline2.setVisibility(View.INVISIBLE);
            lifeLineVal--;
        }else if (wrong == 3){
            imgLifeline1.setVisibility(View.INVISIBLE);
            lifeLineVal--;
        }

        //Si le joueur a perdu les 3 vies, on affiche "Game Over" comme Toast message
        if (lifeLineVal == 0){
            Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();

            Handler delay = new Handler();
            delay.postDelayed(new Runnable() {
                @Override
                public void run() {
                    /*
                    On redirige l'utilisateur vers la page des scores après avoir perdu
                    avec un décalage de 1000ms précisé grâce à delay de la classe Handler
                     */
                    Intent intent = new Intent(QuizActivity.this,ScoreActivity.class);
                    startActivity(intent);

                }
            },1000);
        }

    }

    public void buttonA(View view) {

        countDownTimer.cancel(); //On arrête le compteur dès que l'utilisateur clique sur le bouton

        disableOptions();

        buttonA.setBackgroundResource(R.drawable.flash_background);
        anim = (AnimationDrawable) buttonA.getBackground(); //animation pour montrer le choix du joueur
        anim.start();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            //On compare la réponse choisie par le joueur avec la bonne réponse
                if (currentQuestion.getOption1().equals(currentQuestion.getAnswerNr())){

                    //Si c'est bien la bonne réponse on change la couleur d'arrière plan du bouton en vert avec l'animation
                    buttonA.setBackgroundResource(R.drawable.correct_green_options_bg);
                    buttonA.startAnimation(correctAnsAnimation);

                    correct++; //on incrémente la variable qui contient le nombre de réponses correctes

                    FLAG = 1;
                    playAudio.setAudioforEvent(FLAG);

                    score = score + 10; //On incrémente le score avec un pas de 10

                    Log.i("QuizInfo","Correct");

                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            /*
                            Tant qu'on a pas atteint le nombre de questions maximum
                             pour chaque quiz, on génére la question suivante. Sinon,
                             on passe au résultat final (score)
                             */
                            if (qid != sizeofQuiz){
                                SetNewQuestion();

                            }else {
                                finalResult();
                            }
                        }
                    },2000); //On précise qu'il faut avoir un delay entre chaque question
                }else {

                    /*
                    Si le joueur a chosi une mauvaise réponse, on change la couleur de l'arrière plan
                    en rouge pour lui indiquer que c'est une mauvaise réponse, on lance l'animation
                    on incrémente la variable qui contient le nombre de réponses fausses.

                    Ensuite, on appelle la fonction healthFunctionality pour modifier les 3 vies du joueur
                    selon le nombre wrong
                    */

                    buttonA.setBackgroundResource(R.drawable.wrong_red_options_bg);
                    buttonA.startAnimation(wrongAnsAnimation);
                    wrong++;

                    healthFunctionality();

                    FLAG = 2;
                    playAudio.setAudioforEvent(FLAG);
                    Handler handler3 = new Handler();
                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if(currentQuestion.getOption2().equals(currentQuestion.getAnswerNr())){
                                buttonB.setBackgroundResource(R.drawable.correct_green_options_bg);
                            }else if (currentQuestion.getOption3().equals(currentQuestion.getAnswerNr())){
                                buttonC.setBackgroundResource(R.drawable.correct_green_options_bg);
                            }else {
                                buttonD.setBackgroundResource(R.drawable.correct_green_options_bg);
                            }
                        }
                    },2000);

                    Handler handler4 = new Handler();
                    handler4.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid != sizeofQuiz){

                                SetNewQuestion();

                            }else {
                                finalResult();
                            }
                        }
                    },3000);
                }
            }
        },5000);
    }
    /*
    On refait le même processus pour les boutons buttonB, buttonC et buttonD
     */
    public void buttonB(View view) {

        countDownTimer.cancel();

        disableOptions();
        buttonB.setBackgroundResource(R.drawable.flash_background);
        anim = (AnimationDrawable) buttonB.getBackground();
        anim.start();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (currentQuestion.getOption2().equals(currentQuestion.getAnswerNr())){

                    buttonB.setBackgroundResource(R.drawable.correct_green_options_bg);
                    buttonB.startAnimation(correctAnsAnimation);
                    correct++;

                    FLAG = 1;
                    playAudio.setAudioforEvent(FLAG);
                    score = score + 10;


                    Log.i("QuizInfo","Correct");

                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid != sizeofQuiz){

                                SetNewQuestion();

                            }else {
                                finalResult();
                            }
                        }
                    },2000);
                }else {

                    buttonB.setBackgroundResource(R.drawable.wrong_red_options_bg);
                    buttonB.startAnimation(wrongAnsAnimation);
                    wrong++;

                    healthFunctionality();

                    FLAG = 2;
                    playAudio.setAudioforEvent(FLAG);
                    Handler handler3 = new Handler();
                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if(currentQuestion.getOption1().equals(currentQuestion.getAnswerNr())){
                                buttonA.setBackgroundResource(R.drawable.correct_green_options_bg);
                            }else if (currentQuestion.getOption3().equals(currentQuestion.getAnswerNr())){
                                buttonC.setBackgroundResource(R.drawable.correct_green_options_bg);
                            }else {
                                buttonD.setBackgroundResource(R.drawable.correct_green_options_bg);
                            }
                        }
                    },2000);

                    Handler handler4 = new Handler();
                    handler4.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid != sizeofQuiz){

                                SetNewQuestion();

                            }else {
                                finalResult();
                            }
                        }
                    },3000);
                }
            }
        },5000);
    }

    public void buttonC(View view) {

        countDownTimer.cancel();

        disableOptions();
        buttonC.setBackgroundResource(R.drawable.flash_background);
        anim = (AnimationDrawable) buttonC.getBackground();
        anim.start();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (currentQuestion.getOption3().equals(currentQuestion.getAnswerNr())){

                    buttonC.setBackgroundResource(R.drawable.correct_green_options_bg);
                    buttonC.startAnimation(correctAnsAnimation);
                    correct++;

                    FLAG = 1;
                    playAudio.setAudioforEvent(FLAG);
                    score = score + 10;


                    Log.i("QuizInfo","Correct");

                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid != sizeofQuiz){

                                SetNewQuestion();

                            }else {
                                finalResult();
                            }
                        }
                    },2000);
                }else {

                    buttonC.setBackgroundResource(R.drawable.wrong_red_options_bg);
                    buttonC.startAnimation(wrongAnsAnimation);
                    wrong++;

                    healthFunctionality();

                    FLAG = 2;
                    playAudio.setAudioforEvent(FLAG);
                    Handler handler3 = new Handler();
                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if(currentQuestion.getOption2().equals(currentQuestion.getAnswerNr())){
                                buttonB.setBackgroundResource(R.drawable.correct_green_options_bg);
                            }else if (currentQuestion.getOption1().equals(currentQuestion.getAnswerNr())){
                                buttonA.setBackgroundResource(R.drawable.correct_green_options_bg);
                            }else {
                                buttonD.setBackgroundResource(R.drawable.correct_green_options_bg);
                            }
                        }
                    },2000);

                    Handler handler4 = new Handler();
                    handler4.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid != sizeofQuiz){

                                SetNewQuestion();

                            }else {
                                finalResult();
                            }
                        }
                    },3000);
                }
            }
        },5000);

    }

    public void buttonD(View view) {

        countDownTimer.cancel();
        disableOptions();
        buttonD.setBackgroundResource(R.drawable.flash_background);
        anim = (AnimationDrawable) buttonD.getBackground();
        anim.start();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (currentQuestion.getOption4().equals(currentQuestion.getAnswerNr())){

                    buttonD.setBackgroundResource(R.drawable.correct_green_options_bg);
                    buttonD.startAnimation(correctAnsAnimation);
                    correct++;

                    FLAG = 1;
                    playAudio.setAudioforEvent(FLAG);
                    score = score + 10;


                    Log.i("QuizInfo","Correct");

                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid != sizeofQuiz){

                                SetNewQuestion();

                            }else {
                                finalResult();
                            }
                        }
                    },2000);
                }else {

                    buttonD.setBackgroundResource(R.drawable.wrong_red_options_bg);
                    buttonD.startAnimation(wrongAnsAnimation);
                    wrong++;

                    healthFunctionality();

                    FLAG = 2;
                    playAudio.setAudioforEvent(FLAG);

                    Handler handler3 = new Handler();
                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if(currentQuestion.getOption2().equals(currentQuestion.getAnswerNr())){
                                buttonB.setBackgroundResource(R.drawable.correct_green_options_bg);
                            }else if (currentQuestion.getOption3().equals(currentQuestion.getAnswerNr())){
                                buttonC.setBackgroundResource(R.drawable.correct_green_options_bg);
                            }else {
                                buttonA.setBackgroundResource(R.drawable.correct_green_options_bg);
                            }
                        }
                    },2000);

                    Handler handler4 = new Handler();
                    handler4.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid != sizeofQuiz){

                                SetNewQuestion();

                            }else {
                                finalResult();
                            }
                        }
                    },3000);


                }


            }
        },5000);

    }

    //L'interface countDownTimer intégrée dans android nous permet de générer un compteur

    @Override
    protected void onResume() {
        super.onResume();
        countDownTimer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
        finish();
    }

    @Override
    public void onBackPressed() {

        countDownTimer.cancel();

        if (backPressedTime + 2000 > System.currentTimeMillis()){
            //On redirige le joueur vers la page de catégories
            Intent intent = new Intent(QuizActivity.this,CategoryActivity.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "Press Again to Exit", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();


    }

    private void disableOptions(){
        buttonA.setEnabled(false);
        buttonB.setEnabled(false);
        buttonC.setEnabled(false);
        buttonD.setEnabled(false);

    }

    private void enableOptions(){
        buttonA.setEnabled(true);
        buttonB.setEnabled(true);
        buttonC.setEnabled(true);
        buttonD.setEnabled(true);

    }

    /*
    La méthode finalResult est appelée pour passer à la page GameOverScreen quand le joueur a perdu
     */
    private void finalResult(){

        Intent resultIntent = new Intent(QuizActivity.this,GameOverScreen.class);
        resultIntent.putExtra(Constants.TOTAL_QUESTIONS,sizeofQuiz);
        resultIntent.putExtra(Constants.SCORE,score);
        resultIntent.putExtra(Constants.WRONG,wrong);
        resultIntent.putExtra(Constants.CORRECT,correct);
        resultIntent.putExtra("Category",categoryValue);
        startActivity(resultIntent);
        finish();

    }

    // La méthode permet de commencer le décompte et d'afficher le temps restant en secondes
    private void startCountDown(){

        countDownTimer = new CountDownTimer(timeLeftMillis,1000) {
            @Override
            public void onTick(long millsUntilFinished) {
                timeLeftMillis = millsUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {

                timeLeftMillis = 0;
                updateCountDownText();

            }
        }.start();

    }

    private void updateCountDownText(){


        int seconds = (int) (timeLeftMillis/1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(),"%02d",seconds);
        savedTime = Long.parseLong(timeFormatted);
        timeText.setText(timeFormatted);

        if (timeLeftMillis < 10000){
            /*
            On change la couleur du temps restant pour notifier le joueur
            qu'il doit répondre avant la fin du compteur
            */
            timeText.setTextColor(ContextCompat.getColor(this,R.color.red));

        }else {
            timeText.setTextColor(ContextCompat.getColor(this,R.color.black));
        }

        if (timeLeftMillis == 0){
            //Si le joueur ne choisit pas de réponse jusqu'à la fin du temps alloué, on affiche un toast message
            Toast.makeText(this, "Times Up!", Toast.LENGTH_SHORT).show();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    timerDialog.timerDialog();
                }
            },2000);
        }
    }
}
