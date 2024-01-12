package ma.inpt.quiztrivia.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import ma.inpt.quiztrivia.R;

/*
La classe AppControler permet de contrôler différents
paramètres de l'application (musique, état,...)
 */

public class AppController extends Application {

    private static Context mContext;
    public static MediaPlayer player;
    public static Activity currentActivity;


    @Override
    public void onCreate() {
        super.onCreate();
        setmContext(getApplicationContext());

        player = new MediaPlayer();

        mediaPlayerIntilizer();

    }

    //Getter et Setter de la méthode Context qui précise l'état actuel de l'application
    public static Context getmContext() {
        return mContext;
    }

    public static void setmContext(Context context) {
        mContext = context;
    }

    public static void mediaPlayerIntilizer(){

        try {

            player = MediaPlayer.create(getmContext(), R.raw.background);
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setLooping(true);
            player.setVolume(1f,1f);

        }catch (IllegalStateException e){
            e.printStackTrace();
        }
    }
    /*
    Cette méthode permet de lancer la musique SI l'utilisateur l'a permis dans les patamètres
    et que l'utilisateur n'est pas entrain de jouer
     */
    public static void playMusic(){

        try {
            if (SettingsPreferences.getMusicEnableDisable(mContext)&&!player.isPlaying()){
                player.start();
            }

        }catch (IllegalStateException e){}

    }

    //Cette méthode permet d'arrêter la musique si l'utilisateur est entrain de jouer

    public static void StopSound(){

        if (player.isPlaying()){
            player.pause();
        }
    }

}