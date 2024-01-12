package ma.inpt.quiztrivia.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import ma.inpt.quiztrivia.constants.Constants;

/*
Cette classe permet à l'utilisateur de régler les paramètres de son choix
 */
public class SettingsPreferences {

    //Cette variable contient l'état actuel de la musique (ON ou OFF)
    private static final String SHOW_MUSIC_ONOFF =  "show_music_enable_disable";

    /*
    Dans cette méthode, la classe SharedPreferences permet d'enregistrer et stocker
    les paramètres (préférences) entre les sessions utilisateur, même si l'application est arrêtée
     et redémarrée ou si l'appareil est redémarré
     */
    public static void setMusicEnableDisable(Context context,Boolean result){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        /*
        La méthode edit() est utilisée pour créer un nouvel éditeur pour nos préférences,
        grâce auquel on peut modifier les données et valider de manière atomique ces modifications
        dans l'objet prefs.
        */
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putBoolean(SHOW_MUSIC_ONOFF,result);
        prefsEditor.commit();

    }

    public static boolean getMusicEnableDisable(Context context){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(SHOW_MUSIC_ONOFF,
                Constants.DEFAULT_MUSIC_SETTING);

    }
}
