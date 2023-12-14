package com.example.canteenpr.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canteenpr.R;
import com.example.canteenpr.model.MenuModel;
import com.example.canteenpr.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private Context mContext;
    List<MenuModel> menuModels = new ArrayList<MenuModel>();


    public MenuAdapter(Context mContext, List<MenuModel> menuModels) {
        this.mContext = mContext;
        this.menuModels = menuModels;
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menucard_list_item, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MenuAdapter.ViewHolder viewHolder, final int i) {
        MenuModel menuModel=menuModels.get(i);
        viewHolder.tv_name.setText(menuModel.getName());
        viewHolder.tv_quantity.setText(menuModel.getQuantity());
        viewHolder.tv_price.setText(menuModel.getPrize());

    }

    @Override
    public int getItemCount() {
        return menuModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_quantity, tv_price;
        public ViewHolder( View view) {
            super(view);
            tv_name= (TextView) view.findViewById(R.id.name);
            tv_quantity= (TextView)view.findViewById(R.id.quantity);
            tv_price= (TextView) view.findViewById(R.id.price);
        }
    }
}
