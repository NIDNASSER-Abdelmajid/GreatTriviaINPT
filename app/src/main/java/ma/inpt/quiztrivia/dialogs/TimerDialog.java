package ma.inpt.quiztrivia.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;

import ma.inpt.quiztrivia.R;
import ma.inpt.quiztrivia.activities.HomeScreen;

public class TimerDialog {

    private Context mContext;
    private Dialog TimerDialog;

    public TimerDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void timerDialog(){

        TimerDialog = new Dialog(mContext);
        TimerDialog.setContentView(R.layout.timer_dialog);

        final Button btTimer = (Button) TimerDialog.findViewById(R.id.bt_timer);

        btTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimerDialog.dismiss();
                Intent intent = new Intent(mContext, HomeScreen.class);
                mContext.startActivity(intent);
            }
        });

        TimerDialog.show();
        TimerDialog.setCancelable(false);
        TimerDialog.setCanceledOnTouchOutside(false);
        TimerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }
}
