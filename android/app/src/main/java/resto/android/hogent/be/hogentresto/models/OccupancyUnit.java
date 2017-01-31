package resto.android.hogent.be.hogentresto.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class OccupancyUnit implements Serializable {

    @SerializedName("_id")
    private String id;

    private long time;
    private double occupancy;

    public OccupancyUnit(long time, double occupancy) {
        this.time = time;
        this.occupancy = occupancy;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(double occupancy) {
        this.occupancy = occupancy;
    }

    public long getMilliseconds() {
        return time * 1000;
    }
}
