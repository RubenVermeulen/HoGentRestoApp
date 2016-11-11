package resto.android.hogent.be.hogentresto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HoGentRestoService {
    @GET("restaurants")
    Call<List<Restaurant>> restaurants();
}
