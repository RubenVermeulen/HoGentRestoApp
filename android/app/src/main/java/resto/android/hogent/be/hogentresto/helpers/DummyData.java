package resto.android.hogent.be.hogentresto.helpers;

import com.jjoe64.graphview.series.DataPoint;

public class DummyData {

    public static DataPoint[] getCurrentPosition() {
        return new DataPoint[] {
            new DataPoint(1480937460000L, 0),
            new DataPoint(1480937460000L, 1)
        };
    }
}
