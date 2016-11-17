package resto.android.hogent.be.hogentresto.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import resto.android.hogent.be.hogentresto.R;
import resto.android.hogent.be.hogentresto.RestaurantActivity;
import resto.android.hogent.be.hogentresto.helpers.Traffic;
import resto.android.hogent.be.hogentresto.models.Restaurant;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder> {
    private List<Restaurant> dataset;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    protected static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CardView cardView;
        ImageView thumbnail;
        TextView name;
        TextView openingHours;
        TextView trafficGrade;
        ImageView trafficIndicator;

        ViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardView);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            name = (TextView) itemView.findViewById(R.id.name);
            openingHours = (TextView) itemView.findViewById(R.id.openingHours);
            trafficGrade = (TextView) itemView.findViewById(R.id.trafficGrade);
            trafficIndicator = (ImageView) itemView.findViewById(R.id.trafficIndicator);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RestaurantsAdapter(Context context, List<Restaurant> dataset) {
        this.context = context;
        this.dataset = dataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RestaurantsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_list_item, parent, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Restaurant restaurant = dataset.get(position);

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.name.setText(restaurant.getName());
        holder.openingHours.setText(restaurant.getOpeningHours());
        Picasso.with(context).load(restaurant.getUrlImage()).into(holder.thumbnail);

        Traffic.setTraffic(restaurant.getOccupation(), holder.trafficGrade, holder.trafficIndicator);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RestaurantActivity.class);
                intent.putExtra("restaurant", restaurant);

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
