package resto.android.hogent.be.hogentresto.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import resto.android.hogent.be.hogentresto.R;
import resto.android.hogent.be.hogentresto.models.Menu;

/**
 * Created by alexa on 2/11/2016.
 */

public class MenusAdapter extends BaseAdapter {
    private Context ctx;
    private LayoutInflater inflater;
    private List<Menu> objects;

    public MenusAdapter(Context ctx, List<Menu> objects) {
        this.ctx = ctx;
        this.objects = objects;
        inflater = LayoutInflater.from(ctx);
    }

    private class ViewHolder {
        TextView tvOne;
        TextView tvTwo;
        TextView tvThree;
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
        MenusAdapter.ViewHolder holder = null;

        if (convertView == null) {
            holder = new MenusAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.menus_list_item, null);

            holder.tvOne = (TextView) convertView.findViewById(R.id.title);
            holder.tvTwo = (TextView) convertView.findViewById(R.id.description);
            holder.tvThree = (TextView) convertView.findViewById(R.id.price);

            convertView.setTag(holder);
        }
        else {
            holder = (MenusAdapter.ViewHolder) convertView.getTag();
        }

        int resourceKey = 0;

        holder.tvOne.setText(objects.get(position).getTitle());
        holder.tvTwo.setText(objects.get(position).getDescription());
        holder.tvThree.setText(Double.toString(objects.get(position).getPrice()));

        return convertView;
    }
}
