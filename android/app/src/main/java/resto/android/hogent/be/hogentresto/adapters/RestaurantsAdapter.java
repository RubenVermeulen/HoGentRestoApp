package resto.android.hogent.be.hogentresto.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import resto.android.hogent.be.hogentresto.R;
import resto.android.hogent.be.hogentresto.models.Restaurant;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder> {
    private List<Restaurant> dataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    protected static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView textView;
        TextView trafficGrade;
        ImageView trafficIndicator;

        ViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.name);
            trafficGrade = (TextView) itemView.findViewById(R.id.trafficGrade);
            trafficIndicator = (ImageView) itemView.findViewById(R.id.trafficIndicator);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RestaurantsAdapter(List<Restaurant> dataset) {
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(dataset.get(position).getName());

        switch (position % 3) {
            case 0:
                holder.trafficGrade.setText(R.string.calm);
                holder.trafficIndicator.setImageResource(R.drawable.circle_calm);
                break;
            case 1:
                holder.trafficGrade.setText(R.string.doable);
                holder.trafficIndicator.setImageResource(R.drawable.circle_doable);
                break;
            case 2:
                holder.trafficGrade.setText(R.string.full);
                holder.trafficIndicator.setImageResource(R.drawable.circle_full);
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
