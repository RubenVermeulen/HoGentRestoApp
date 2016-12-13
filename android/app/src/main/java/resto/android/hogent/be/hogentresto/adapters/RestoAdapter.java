package resto.android.hogent.be.hogentresto.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import resto.android.hogent.be.hogentresto.R;
import resto.android.hogent.be.hogentresto.RestaurantActivity;
import resto.android.hogent.be.hogentresto.helpers.Traffic;
import resto.android.hogent.be.hogentresto.models.Restaurant;

/**
 * Created by jonas on 12/12/2016.
 */

public class RestoAdapter extends ArrayAdapter<Restaurant> {

    private Context context;
    private List<Restaurant> restos;
    private int ilayout;

    public RestoAdapter(Context context, int layout, List<Restaurant> restos) {
        super(context, layout, restos);
        this.restos = restos;
        this.ilayout = layout;
        this.context = context;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            // assign the view we are converting to a local variable
            View v = convertView;

            // first check to see if the view is null. if so, we have to inflate it.
            // to inflate it basically means to render, or show, the view.
            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.restaurant_list_item, null);
            }

            Restaurant r = restos.get(position);

            if (r != null) {

                // This is how you obtain a reference to the TextViews.
                // These TextViews are created in the XML files we defined.

                CardView cardView = (CardView) v.findViewById(R.id.cardView);
                ImageView thumbnail = (ImageView) v.findViewById(R.id.thumbnail);
                TextView name = (TextView) v.findViewById(R.id.name);
                TextView openingHours = (TextView) v.findViewById(R.id.openingHours);
                TextView trafficGrade = (TextView) v.findViewById(R.id.trafficGrade);
                ImageView trafficIndicator = (ImageView) v.findViewById(R.id.trafficIndicator);

                name.setText(r.getName());
                openingHours.setText(r.getOpeningHours());
                Picasso.with(context).load(r.getUrlImage()).into(thumbnail);

                Traffic.setTraffic(r.getOccupation(), trafficGrade, trafficIndicator);

            }

            // the view must be returned to our activity
            return v;

        }

    }
