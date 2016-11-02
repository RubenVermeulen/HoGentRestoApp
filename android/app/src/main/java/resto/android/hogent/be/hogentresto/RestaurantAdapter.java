package resto.android.hogent.be.hogentresto;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by alexa on 2/11/2016.
 */

public class RestaurantAdapter extends BaseAdapter {

    private Context ctx;
    private LayoutInflater inflater;
    private List<Restaurant> objects;

    public RestaurantAdapter(Context ctx, List<Restaurant> objects) {
        this.ctx = ctx;
        this.objects = objects;
        inflater = LayoutInflater.from(ctx);
    }

    private class ViewHolder {
        TextView tvOne;
        TextView tvTwo;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.restaurant_list_item, null);

            holder.tvOne = (TextView) convertView.findViewById(R.id.name);

            ImageView trafficKitchen = (ImageView) convertView.findViewById(R.id.trafficKitchen);
            ImageView trafficEatingArea = (ImageView) convertView.findViewById(R.id.trafficEatingArea);

            changeColor(convertView, trafficKitchen, R.color.colorTrafficFull);
            changeColor(convertView, trafficEatingArea, R.color.colorTrafficCalm);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        int resourceKey = 0;

        holder.tvOne.setText(objects.get(position).getName());

        return convertView;
    }

    private void changeColor(View convertView, ImageView imageView, int color) {
        GradientDrawable drawableTwo = (GradientDrawable) imageView.getDrawable();
        drawableTwo.setColor(convertView.getResources().getColor(color));
    }
}
