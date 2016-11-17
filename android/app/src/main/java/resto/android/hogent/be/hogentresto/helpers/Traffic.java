package resto.android.hogent.be.hogentresto.helpers;

import android.widget.ImageView;
import android.widget.TextView;

import resto.android.hogent.be.hogentresto.R;

public class Traffic {
    public static void setTraffic(double occupation, TextView trafficGrade, ImageView trafficIndicator) {
        if (occupation < 0.5) {
            trafficGrade.setText(R.string.calm);
            trafficIndicator.setImageResource(R.drawable.circle_calm);
        }
        else if (occupation < 0.8) {
            trafficGrade.setText(R.string.doable);
            trafficIndicator.setImageResource(R.drawable.circle_doable);
        }
        else {
            trafficGrade.setText(R.string.full);
            trafficIndicator.setImageResource(R.drawable.circle_full);
        }
    }
}
