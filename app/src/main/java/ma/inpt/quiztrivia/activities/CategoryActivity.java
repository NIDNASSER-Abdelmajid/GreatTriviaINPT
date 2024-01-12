package ma.inpt.quiztrivia.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ma.inpt.quiztrivia.constants.Constants;
import ma.inpt.quiztrivia.R;

/*
L'activité Category affiche les 5 catégories proposées par notre application,
elle est implémentée par View.OnClickListener pour pouvoir charger les pages QuizActivity
selon la catégorie choisie par l'utilisateur
 */

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener{

    //On doit créer 5 bouttons pour nos 5 catégories
    Button btInformatique,btHistGeo,btDiv,btScience,btSport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


    /*findViewById est une méthode qui trouve la View à partir du fichier de ressources
        activity_category.xml
    */
        btInformatique = findViewById(R.id.bt_Informatique);
        btHistGeo = findViewById(R.id.bt_HistGeo);
        btSport = findViewById(R.id.bt_Sport);
        btScience = findViewById(R.id.bt_Science);
        btDiv = findViewById(R.id.bt_Divertissement);

        //On relie les bouttons avec setOnClickListener pour avoir une réponse de l'app après clic
        btInformatique.setOnClickListener(this);
        btHistGeo.setOnClickListener(this);
        btScience.setOnClickListener(this);
        btSport.setOnClickListener(this);
        btDiv.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        /*
        La méthode onClick précise ce que l'app doit faire si l'utilisateur clique sur
        l'un des 5 bouttons, d'où l'utilité du switch
        */
        switch (view.getId()){

            case R.id.bt_Informatique:  // L'utilisateur choisie la catégorie Informatique

                Intent intentInformatique = new Intent(CategoryActivity.this,QuizActivity.class);
                intentInformatique.putExtra("Category", Constants.INFORMATIQUE);
                startActivity(intentInformatique);
                finish();
                break;

            case R.id.bt_HistGeo:  // L'utilisateur choisie la catégorie Histoire et Géographie

                Intent intentHistGeo = new Intent(CategoryActivity.this,QuizActivity.class);
                intentHistGeo.putExtra("Category",Constants.HISTOIREGEO);
                startActivity(intentHistGeo);
                finish();
                break;


            case R.id.bt_Sport:  // L'utilisateur choisie la catégorie Sport

                Intent intentSport = new Intent(CategoryActivity.this,QuizActivity.class);
                intentSport.putExtra("Category",Constants.SPORT);
                startActivity(intentSport);
                finish();
                break;


            case R.id.bt_Science:  // L'utilisateur choisie la catégorie Science

                Intent intentScience = new Intent(CategoryActivity.this,QuizActivity.class);
                intentScience.putExtra("Category",Constants.SCIENCE);
                startActivity(intentScience);
                finish();
                break;

            case R.id.bt_Divertissement:  // L'utilisateur choisie la catégorie Divertissement
                Intent intentDivertissement = new Intent(CategoryActivity.this,QuizActivity.class);
                intentDivertissement.putExtra("Category",Constants.DIVERTISSEMENT);
                startActivity(intentDivertissement);
                finish();
                break;
        }

    }

    //On crée une méthode au cas où l'utilisateur veut revenir à l'écran d'accueil HomeScreenActivity
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CategoryActivity.this, HomeScreen.class);
        startActivity(intent);
        finish();
    }

}