package resto.android.hogent.be.hogentresto.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class OccupancyUnit implements Serializable {

    @SerializedName("_id")
    String id;
    Date time;
    double occupancy;

    public OccupancyUnit(Date time, double occupancy) {
        this.time = time;
        this.occupancy = occupancy;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public double getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(double occupancy) {
        this.occupancy = occupancy;
    }
}
