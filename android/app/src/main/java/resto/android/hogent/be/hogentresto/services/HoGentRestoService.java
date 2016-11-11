package resto.android.hogent.be.hogentresto.services;

import java.util.List;

import resto.android.hogent.be.hogentresto.models.Restaurant;
import retrofit2.Call;
import retrofit2.http.GET;

public interface HoGentRestoService {
    @GET("restaurants")
    Call<List<Restaurant>> restaurants();
}
