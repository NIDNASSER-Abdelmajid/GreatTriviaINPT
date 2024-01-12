package ma.inpt.quiztrivia.db;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ma.inpt.quiztrivia.constants.Constants;
import ma.inpt.quiztrivia.data.TriviaQuestion;
import ma.inpt.quiztrivia.db.TriviaQuizContract.*;

import java.util.ArrayList;

/*
    SQLiteOpenHelper permet la création et la gestion des bases de données SQL
    ici on essaie de créer une base de données SQL pour nos questions
    et les réponses proposées.
*/
public class TriviaQuizHelper extends SQLiteOpenHelper {

    //On nomme notre base de données TriviaQuiz
    private static final String DATABASE_NAME = "GreatTriviaQuiz.db";
    private static final int DATBASE_VERSION = 13;

    private SQLiteDatabase db;


    public TriviaQuizHelper(Context context) {
        super(context, DATABASE_NAME,null, DATBASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        this.db = db;
        /*
        On crée la table où on va stocker les questions de notre app
        avec 8 colonnes: l'identifiant ID, la question, les 4 options,
        le numéro de la bonne réponse et la catégorie

        Pour cela, on insère les requêtes SQL dans des variables de type final String
        qu'on va exécuter à l'aide de la méthode void execSQL()
        */

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionTable.TABLE_NAME + " ( " +
                QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.COLUMN_QUESTION + " TEXT, " +
                QuestionTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionTable.COLUMN_ANSWER_NR + " TEXT, " +
                QuestionTable.COLUMN_CATEGORY + " TEXT " +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);

        //la méthode fillQuestionsTable nous permettera ultérieurement d'insérer les questions dans notre table
        fillQuestionsTable();
    }

    //Méthode onUpgrade pour mettre à jour les données des tables SQL
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME);
        onCreate(db);

    }

    //On insère 62 questions dans la table QuestionsTables
    private void fillQuestionsTable()
    {
        TriviaQuestion q1 = new TriviaQuestion("Quel est le nom du tout premier OS de Microsoft ?",
                "MS-DOS", "TELNET", "COBOL","D8086",
                "MS-DOS", Constants.INFORMATIQUE);
        addQuestions(q1);
        TriviaQuestion q2 = new TriviaQuestion("Lequel des langages informatiques suivants est utilisé pour l’intelligence artificielle?",
                "COBOL", "C", "PROLOG","FORTRAN",
                "PROLOG", Constants.INFORMATIQUE);
        addQuestions(q2);
        TriviaQuestion q3 = new TriviaQuestion("Le cerveau de tout système informatique est",
                "Unité arithmétique et logique ALU", "Unité de contôle", "CPU","Mémoire",
                "CPU", Constants.INFORMATIQUE);
        addQuestions(q3);
        TriviaQuestion q4 = new TriviaQuestion("HTTP est un protocole situé dans la couche",
                "Couche d'application", "Couche réseau", "Couche de transport","Toutes les réponses sont vraies",
                "Couche d'application", Constants.INFORMATIQUE);
        addQuestions(q4);
        TriviaQuestion q5 = new TriviaQuestion("Le système binaire utilise la base",
                "8", "2", "10", "16",
                "2",Constants.INFORMATIQUE);
        addQuestions(q5);
        TriviaQuestion q6 = new TriviaQuestion("Parmi les noms suivants, lequel ne correspond pas à une version d’Android",
                "Android Kitkat", "Android Lollipop", "Android Mlawy","Android Oreo",
                "Android Mlawy", Constants.INFORMATIQUE);
        addQuestions(q6);
        TriviaQuestion q7 = new TriviaQuestion("La capitale du Venezuela est",
                "Caracas", "Lima", "Santiago","Bogota",
                "Caracas", Constants.HISTOIREGEO);
        addQuestions(q7);
        TriviaQuestion q8 = new TriviaQuestion("Quel langage n'a pas de statut officiel en Suisse",
                "Italien", "Roumain", "Espagnol","Français",
                "Espagnol", Constants.HISTOIREGEO);
        addQuestions(q8);
        TriviaQuestion q9 = new TriviaQuestion("Quel pays est le 1er exportateur de pétrole au monde",
                "Arabie Saoudite", "USA", "Russie","Qatar",
                "USA", Constants.HISTOIREGEO);
        addQuestions(q9);
        TriviaQuestion q10 = new TriviaQuestion("Combien de pays y a-t-il dans l'Union européenne ?",
                "28", "20", "30","27",
                "27", Constants.HISTOIREGEO);
        addQuestions(q10);
        TriviaQuestion q11 = new TriviaQuestion("Quel pays n'a pas de frontières avec la Russie ?",
                "Maldovie", "Estonie", "Norvège","Corée du Nord",
                "Maldovie", Constants.HISTOIREGEO);
        addQuestions(q11);
        TriviaQuestion q12 = new TriviaQuestion("Quel événement a déclenché la 2ème guerre mondiale ?",
                "Invasion allemande de l'Union soviétique", "Invasion allemande de la Tchécoslovaquie",
                "L'annexion des Sudèles par l'Allemagne","Invasion allemande de la Pologne",
                "Invasion allemande de la Pologne", Constants.HISTOIREGEO);
        addQuestions(q12);
        TriviaQuestion q13 = new TriviaQuestion("En quelle année les troupes britanniques ont-elles incendié la Maison Blanche ?",
                "1789", "1812", "1813","1835",
                "1812", Constants.HISTOIREGEO);
        addQuestions(q13);
        TriviaQuestion q14 = new TriviaQuestion("Quelle conférence a tracé les frontières de l'Europe après la Seconde Guerre mondiale ?",
                "Conférence de Maastrich", "Conférence de Berlin", "Conférence de Postdam","Conférence de Nuremberg",
                "Conférence de Postdam", Constants.HISTOIREGEO);
        addQuestions(q14);
        TriviaQuestion q15 = new TriviaQuestion("L'achat de l'Alaska a impliqué ?",
                "USA et la Russie", "USA et Canada ", "USA et UK","Canada et UK",
                "USA et la Russie", Constants.HISTOIREGEO);
        addQuestions(q15);
        TriviaQuestion q16 = new TriviaQuestion("Quand l'Algérie est-elle devenue indépendante ?",
                "1967", "1962", "1959","1956",
                "1962", Constants.HISTOIREGEO);
        addQuestions(q16);
        TriviaQuestion q17 = new TriviaQuestion("Quand la révolte arabe a-t-elle eu lieu ?",
                "1919", "1912", "1920","1916",
                "1916", Constants.HISTOIREGEO);
        addQuestions(q17);
        TriviaQuestion q18 = new TriviaQuestion("Quel événement a marqué la fin de la guerre froide ?",
                "L'effondrement de l'URSS", "Crise des missiles cubains",
                "Tchernobyl","Destruction du mur de Berlin",
                "L'effondrement de l'URSS", Constants.HISTOIREGEO);
        addQuestions(q18);
        TriviaQuestion q19 = new TriviaQuestion("Quel leader russe NE FAIT PAS partie de la guerre froide ?",
                "Joseph Stalin", "Nikita Khrushchev", "Vladimir Lenin","Mikhail Gorbachev",
                "Vladimir Lenin", Constants.HISTOIREGEO);
        addQuestions(q19);
        TriviaQuestion q20 = new TriviaQuestion("Quelle est la capitale de l’Australie ?",
                "Canberra", "Sidney", "Maine","Mobile",
                "Canberra", Constants.HISTOIREGEO);
        addQuestions(q20);
        TriviaQuestion q21 = new TriviaQuestion("Quelle ville est surnommée « big Apple » aux USA ?",
                "Boston","New York","Silicon Valley","Miami",
                "New York", Constants.HISTOIREGEO);
        addQuestions(q21);
        TriviaQuestion q22 = new TriviaQuestion("Hawaï appartient à quel pays ?",
                "Cuba","Hawaii","USA","Suisse",
                "USA", Constants.HISTOIREGEO);
        addQuestions(q22);
        TriviaQuestion q23 = new TriviaQuestion("Combien d'États y-a-t-il aux États-Unis ?",
                "53","50","43","55",
                "50", Constants.HISTOIREGEO);
        addQuestions(q23);
        TriviaQuestion q24 = new TriviaQuestion("Quel est le plus long fleuve au monde ?",
                "Le Nile","Le Niagara","L'Alpe","L'Amazon",
                "L'Amazon", Constants.HISTOIREGEO);
        addQuestions(q24);
        TriviaQuestion q25 = new TriviaQuestion("Quel est le plus grand océan du monde ?",
                "L'océan Pacifique","L'océan Atlantique","L'océan Indien","L'océan Arctique",
                "L'océan Pacifique", Constants.HISTOIREGEO);
        addQuestions(q25);
        TriviaQuestion q26 = new TriviaQuestion("Où la première bombe atomique a-t-elle été utilisée ?",
                "Nagasaki","Nagoya","Hiroshima","Tokyo",
                "Hiroshima", Constants.HISTOIREGEO);
        addQuestions(q26);
        TriviaQuestion q27 = new TriviaQuestion("Quand la Seconde Guerre mondiale a-t-elle pris fin ?",
                "1945","1943","1951","1948",
                "1945", Constants.HISTOIREGEO);
        addQuestions(q27);
        TriviaQuestion q28 = new TriviaQuestion("Quand le premier homme a-t-il atterri sur la lune ?",
                "1950","1969","1954","1966",
                "1969", Constants.HISTOIREGEO);
        addQuestions(q28);
        TriviaQuestion q29 = new TriviaQuestion("En quelle année a eu lieu la découverte de l'Amérique ?",
                "1588","1376","1653","1492",
                "1492", Constants.HISTOIREGEO);
        addQuestions(q29);
        TriviaQuestion q30 = new TriviaQuestion("Quel est le pays le plus peuplé du monde ?",
                "L'Inde","USA","La Chine","La Russie",
                "La Chine", Constants.HISTOIREGEO);
        addQuestions(q30);
        TriviaQuestion q31 = new TriviaQuestion("L’hymne national de quel pays européen contient 158 vers",
                "Russie","Autriche","Maldovie","Grèce",
                "Grèce", Constants.HISTOIREGEO);
        addQuestions(q31);
        TriviaQuestion q32 = new TriviaQuestion("De quelle origine sont les frites ?",
                "Belgique","USA","Pays-Bas","France",
                "Belgique", Constants.HISTOIREGEO);
        addQuestions(q32);
        TriviaQuestion q33 = new TriviaQuestion("what happens in ... stays in ...",
                "California","Las vegas","New York","Boston",
                "Las vegas", Constants.HISTOIREGEO);
        addQuestions(q33);
        TriviaQuestion q34 = new TriviaQuestion("Dans quel pays la crème glacée a-t-elle été inventée ?",
                "Italie","USA","La Chine","Suisse",
                "La Chine", Constants.HISTOIREGEO);
        addQuestions(q34);
        TriviaQuestion q35 = new TriviaQuestion("Combien de fois Rafael Nadal a-t-il remporté Roland-Garros ?",
                "13", "6", "19","2",
                "13", Constants.SPORT);
        addQuestions(q35);
        TriviaQuestion q36 = new TriviaQuestion("Combien de joueurs de basket d'une même équipe figurent sur un terrain de basket ?",
                "7", "6", "5","8",
                "5", Constants.SPORT);
        addQuestions(q36);
        TriviaQuestion q37 = new TriviaQuestion("En quelle année a été créée la NBA ?",
                "1948", "1934", "1946","1952",
                "1946", Constants.SPORT);
        addQuestions(q37);
        TriviaQuestion q38 = new TriviaQuestion("Avant Miami, quelle était l'équipe de LeBron James ?",
                "Florida", "Toronto", "Houston","Cleveland",
                "Cleveland", Constants.SPORT);
        addQuestions(q38);
        TriviaQuestion q39 = new TriviaQuestion("Selon les astronautes d'Apollo, la lune sent comme ",
                "Marc de café", "le fromage", "de l'essence","Poudre à canon brûlée",
                "Poudre à canon brûlée",Constants.SCIENCE);
        addQuestions(q39);
        TriviaQuestion q40 = new TriviaQuestion("Chaque personne est différente, mais à quel point diffère-t-elle, génétiquement, des autres humains ?",
                "25%", "0.1%", "10%","2%",
                "0.1%", Constants.SCIENCE);
        addQuestions(q40);
        TriviaQuestion q41 = new TriviaQuestion("Quel insecte comestible a le même goût que du poulet?",
                "cafard brun", "bousier", "sauterelle","Cigale",
                "Cigale",Constants.SCIENCE);
        addQuestions(q41);
        TriviaQuestion q42 = new TriviaQuestion("Le sang humain est rouge lorsqu'il est exposé à l'air. De quelle couleur est le sang désoxygéné dans vos veines ?",
                "Rouge", "Transparent", "Marron","Bleu",
                "Rouge", Constants.SCIENCE);
        addQuestions(q42);
        TriviaQuestion q43 = new TriviaQuestion("Les empreintes digitales de quel animal ressemblent le plus à celles d'un humain ?",
                "Babouin", "Bonobo", "Koala","Chimpanzee",
                "Koala", Constants.SCIENCE);
        addQuestions(q43);
        TriviaQuestion q44 = new TriviaQuestion("Quel animal vit le plus longtemps ?",
                "Tortue des Galapagos", "Baleine arctique", "Elephant","Aigle",
                "Baleine arctique",Constants.SCIENCE);
        addQuestions(q44);
        TriviaQuestion q45 = new TriviaQuestion("Quelle est la différence entre le chameau et le dromadaire ?",
                "La longueur du cou", "Le couleur de la langue", "La taille de la queue","Le nombre de bosses",
                "Le nombre de bosses", Constants.SCIENCE);
        addQuestions(q45);
        TriviaQuestion q46 = new TriviaQuestion("Quel précipités observe-t-on quand on mélange du nitrate d’argent avec du chlore?",
                "Du quartz", "Un précipité bleu ciel", "Un précipité blanc qui se noircit","Rien",
                "Un précipité blanc qui se noircit",Constants.SCIENCE);
        addQuestions(q46);
        TriviaQuestion q47 = new TriviaQuestion("Comment appelle-t-on un groupe de pandas ?",
                "embarras", "confusion", "trouble","désordre",
                "embarras", Constants.SCIENCE);
        addQuestions(q47);
        TriviaQuestion q48 = new TriviaQuestion("Quelle pièce faut-il absolument protéger dans un jeu d'échecs ?",
                "Le roi", "La reine", "Le fou","La tour",
                "Le roi", Constants.DIVERTISSEMENT);
        addQuestions(q48);
        TriviaQuestion q49 = new TriviaQuestion("Qui anime Secret Story?",
                "Jack Peralte", "Paul Ducompte", "Robert Martin","Benjamin Castaldi",
                "Benjamin Castaldi",Constants.DIVERTISSEMENT);
        addQuestions(q49);
        TriviaQuestion q50 = new TriviaQuestion("Combien y a-t-il de signes astrologiques chinois ?",
                "12", "10", "9","8",
                "12",Constants.DIVERTISSEMENT);
        addQuestions(q50);
        TriviaQuestion q51 = new TriviaQuestion("Qui est le réalisateur du film « camping » ?",
                "S. Spielberg", "R.Antonniente", "Kubrik","M. Wallberg",
                "R.Antonniente", Constants.DIVERTISSEMENT);
        addQuestions(q51);
        TriviaQuestion q52 = new TriviaQuestion("Qui a écrit les misérables ?",
                "Honoré de Balzac", "Agatha Christie", "Victor Hugo","Emile Zola",
                "Victor Hugo",Constants.DIVERTISSEMENT);
        addQuestions(q52);
        TriviaQuestion q53 = new TriviaQuestion("Qui a écrit le père Goriot ?",
                "Honoré de Balzac", "Gustave", "Victor","Agatha",
                "Honoré de Balzac",Constants.DIVERTISSEMENT);
        addQuestions(q53);
        TriviaQuestion q54 = new TriviaQuestion("Quel acteur célèbre a participé à un concours de sosies de lui-même et a fini 3eme ?",
                "Rowan Atkinson (Mr.Bean)", "Keanu Reeves", "Jackie Chan","Charlie Chaplin",
                "Charlie Chaplin", Constants.DIVERTISSEMENT);
        addQuestions(q54);
        TriviaQuestion q55 = new TriviaQuestion("Qui a chanté « Hotel California » ?",
                "The Beatles", "Pheasant", "Eagles","The Beavers",
                "Eagles", Constants.DIVERTISSEMENT);
        addQuestions(q55);
        TriviaQuestion q56 = new TriviaQuestion("La saga Rush Hour où ont joué Jackie Chan et Chris Tucker compte combien de films ?",
                "1", "3", "2","4",
                "3",Constants.DIVERTISSEMENT);
        addQuestions(q56);
        TriviaQuestion q57 = new TriviaQuestion("Lequel est un groupe de rock ? ",
                "Red Hot Chili Peppers", "Red Sweet Chili Peppers", "Green Hot Chili Peppers","Green Sweet Chili Peppers",
                "Red Hot Chili Peppers",Constants.DIVERTISSEMENT);
        addQuestions(q57);
        TriviaQuestion q58 = new TriviaQuestion(" Le père adoptif de Po: le panda dans Kung Fu Panda est ",
                "cigne", "canard", "pelican","oie",
                "oie", Constants.DIVERTISSEMENT);
        addQuestions(q58);
        TriviaQuestion q59 = new TriviaQuestion("Pendant les oscars 2017 l’oscar du meilleur film a été attribué à un autre par erreur. Quel était le vrai film gagnant ?",
                "Lion", "La La Land", "Moonlight","Manchester by the sea",
                "Moonlight",Constants.DIVERTISSEMENT);
        addQuestions(q59);
        TriviaQuestion q60 = new TriviaQuestion("No-face est un personnage de quel film? ",
                "moving castle", "Away", "Rosso","Neighbor Toboro",
                "Away", Constants.DIVERTISSEMENT);
        addQuestions(q60);
        TriviaQuestion q61 = new TriviaQuestion("Quel film n'est pas réalisé par Christopher Nolan ?",
                "Interstellar", "Arrival", "Inception","Memento",
                "Arrival", Constants.DIVERTISSEMENT);
        addQuestions(q61);
        TriviaQuestion q62 = new TriviaQuestion("Lequel n'est pas un héros Marvel ?",
                "Green Lantern", "Ant-Man", "Daredevil","Deadpool",
                "Green Lantern",Constants.DIVERTISSEMENT);
        addQuestions(q62);
    }

    //Méthode qui permet d'insérer une question (objet TriviaQuestion)
    private void addQuestions(TriviaQuestion question){

        /*
        La classe ContentValues est utilisée pour stocker un ensemble de valeurs.
        La méthode put permet d'ajouter le contenu de "question"
        dans les colonnes correspondantes de la table QuestionsTable

        On insère ensuite les lignes de cv dans notre db  avec la méthode insert()
         */
        ContentValues cv = new ContentValues();
        cv.put(QuestionTable.COLUMN_QUESTION,question.getQuestion());
        cv.put(QuestionTable.COLUMN_OPTION1,question.getOption1());
        cv.put(QuestionTable.COLUMN_OPTION2,question.getOption2());
        cv.put(QuestionTable.COLUMN_OPTION3,question.getOption3());
        cv.put(QuestionTable.COLUMN_OPTION4,question.getOption4());
        cv.put(QuestionTable.COLUMN_ANSWER_NR,question.getAnswerNr());
        cv.put(QuestionTable.COLUMN_CATEGORY,question.getCategory());
        db.insert(QuestionTable.TABLE_NAME,null,cv);

    }

    @SuppressLint("Range")
    public ArrayList<TriviaQuestion> getAllQuestions() {

        //On crée une liste où on stocke les objets TriviaQuestion
        ArrayList<TriviaQuestion> questionList = new ArrayList<>();

        //On appelle la base de données
        db = getReadableDatabase();

        String Projection[] = {

                QuestionTable._ID,
                QuestionTable.COLUMN_QUESTION,
                QuestionTable.COLUMN_OPTION1,
                QuestionTable.COLUMN_OPTION2,
                QuestionTable.COLUMN_OPTION3,
                QuestionTable.COLUMN_OPTION4,
                QuestionTable.COLUMN_ANSWER_NR
        };

        /*
        Cursor est un pointeur sur un tableau en 2 dimensions de n'importe quelle base de données.
        Lorsque on essaye de récupérer des données à l'aide de l'instruction SELECT,
        la base de données crée d'abord un objet Cursor et vous renvoie sa référence

        La méthode query exécute les requêtes SQL sur la table passée en paramètre
        */
        Cursor c = db.query(QuestionTable.TABLE_NAME,
                Projection,
                null,
                null,
                null,
                null,
                null);

        //La méthode moveToFirst nous permet de tester si la requête SQL a renvoyé un ensemble vide
        if (c.moveToFirst()) {
            do {
                //On récupére les questions et réponses de la table QuestionsTable
                TriviaQuestion question = new TriviaQuestion();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getString(c.getColumnIndex(QuestionTable.COLUMN_ANSWER_NR)));

                questionList.add(question);

            } while (c.moveToNext());

        }
        c.close();
        return questionList;
    }

    /*
    Cette méthode fonctionne de la même manière que getAllQuestions() sauf
    qu'elle va nous permettre de récupérer en plus les catégories
    pour que l'on puisse afficher les questions
    par catégorie dans activity_quiz.xml/QuizActivity.java
     */

    @SuppressLint("Range")
    public ArrayList<TriviaQuestion> getQuestionsWithCategory(String Category) {

        ArrayList<TriviaQuestion> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String Projection[] = {

                QuestionTable._ID,
                QuestionTable.COLUMN_QUESTION,
                QuestionTable.COLUMN_OPTION1,
                QuestionTable.COLUMN_OPTION2,
                QuestionTable.COLUMN_OPTION3,
                QuestionTable.COLUMN_OPTION4,
                QuestionTable.COLUMN_ANSWER_NR,
                QuestionTable.COLUMN_CATEGORY
        };

        String selection = QuestionTable.COLUMN_CATEGORY + " = ? ";
        String selectionArgs[] = {Category};


        Cursor c = db.query(QuestionTable.TABLE_NAME,
                Projection,
                selection,
                selectionArgs,
                null,
                null,
                null);


        if (c.moveToFirst()) {
            do {

                TriviaQuestion question = new TriviaQuestion();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getString(c.getColumnIndex(QuestionTable.COLUMN_ANSWER_NR)));
                question.setCategory(c.getString(c.getColumnIndex(QuestionTable.COLUMN_CATEGORY)));

                questionList.add(question);

            } while (c.moveToNext());

        }
        c.close();
        return questionList;
    }

}


