package resto.android.hogent.be.hogentresto.services;

import java.util.List;

import resto.android.hogent.be.hogentresto.models.Menu;
import resto.android.hogent.be.hogentresto.models.OccupancyUnit;
import resto.android.hogent.be.hogentresto.models.Restaurant;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HoGentRestoService {
    @GET("restaurants")
    Call<List<Restaurant>> restaurants();

    @GET("restaurants/{id}/menus/week")
    Call<List<Menu>> menus(@Path("id") String id);

    @GET("restaurants/{id}/forecast")
    Call<List<OccupancyUnit>> forecast(@Path("id") String id);
}
