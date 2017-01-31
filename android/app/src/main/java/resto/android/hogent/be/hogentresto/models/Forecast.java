package resto.android.hogent.be.hogentresto.models;

import java.io.Serializable;
import java.util.List;

public class Forecast implements Serializable {

    private long recommendedHour;
    private List<OccupancyUnit> times;

    public Forecast(long recommendedHour, List<OccupancyUnit> times) {
        this.recommendedHour = recommendedHour;
        this.times = times;
    }

    public double getRecommendedHour() {
        return recommendedHour;
    }

    public void setRecommendedHour(long recommendedHour) {
        this.recommendedHour = recommendedHour;
    }

    public List<OccupancyUnit> getTimes() {
        return times;
    }

    public void setTimes(List<OccupancyUnit> times) {
        this.times = times;
    }
}
