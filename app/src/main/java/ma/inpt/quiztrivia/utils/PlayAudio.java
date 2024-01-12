package ma.inpt.quiztrivia.utils;

import android.content.Context;
import android.media.MediaPlayer;

import ma.inpt.quiztrivia.R;

//La classe PlayAudio permet de lancer les audios lors de l'utilisation de l'app
public class PlayAudio {

    /*
    La classe MediaPlayer est utilisée pour contrôler la lecture de fichiers et de flux audio/vidéo.
    On l'utilise ici pour lancer les audios lors de l'usage de l'application
     */
    private Context mContext;
    private MediaPlayer mediaPlayer;

    public PlayAudio(Context context){
        this.mContext = context;
    }

    public void setAudioforEvent(int FLAG) {  //  1 2 3
    //On utilise un switch pour lancer un audio selon la réponse de l'utilisateur
        switch (FLAG){
            case 1:
                /*
                Dans le premier cas, si l'utilisateur répond correctement, l'audio "correctAudio"
                est lancé pour indiquer à l'utilisateur qu'il a bien répondu.
                 */
                int correctAudio = R.raw.correct;
                playMusic(correctAudio);
                break;

            case 2:
                /*
                Dans le deuxième cas, si l'utilisateur donne une réponse incorrecte, l'audio "wrongAudio"
                est lancé pour indiquer à l'utilisateur qu'il a mal répondu.
                 */
                int wrongAudio = R.raw.wrong;
                playMusic(wrongAudio);
                break;

            case 3:
                /*
                Dans le troisième cas, si l'utilisateur dépasse le délai donné, l'audio "timerAudio"
                est lancé pour indiquer à l'utilisateur que le délai est passé.
                 */
                int timerAudio = R.raw.times_up_sound;
                playMusic(timerAudio);
                break;

        }

    }

    private void playMusic(int audioFile){

        mediaPlayer = MediaPlayer.create(mContext,audioFile);

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();

            }
        });


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });


    }


}
