package live.Abhinav.ecommerce.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import live.Abhinav.ecommerce.app.R;
import live.Abhinav.ecommerce.pojo.Information;

import java.util.Collections;
import java.util.List;

/**
 * Created by Abhinav on 6/12/2015.
 */
public class NavbarListAdapter extends RecyclerView.Adapter<NavbarListAdapter.MyViewHolder> {
    int oldPosition = -1;
    private LayoutInflater inflater;
    List<Information> data = Collections.emptyList();
    Context context;
    private ClickListener clickListener;

    public NavbarListAdapter(Context context, List<Information> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_nav_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Information current = data.get(position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconId);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            if (oldPosition != position) {
                title.setTextColor(Color.parseColor("#FF556F90"));
                oldPosition = position;
            }
//            context.startActivity(new Intent(context, SubActivity.class));
            if (clickListener != null) {
                clickListener.itemClicked(v, position);
            }
//            Toast.makeText(context, "Item clicked at: "+getLayoutPosition(),Toast.LENGTH_SHORT).show();
//            moveToBottom(getLayoutPosition());
        }
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        public void itemClicked(View view, int position);
    }


//    public void moveToBottom(int position) {
//        data.add(data.remove(position));
//        notifyItemMoved(position,data.size()-1);
//    }
}