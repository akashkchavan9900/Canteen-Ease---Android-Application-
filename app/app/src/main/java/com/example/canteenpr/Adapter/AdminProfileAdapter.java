package com.example.canteenpr.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canteenpr.R;
import com.example.canteenpr.admin.PkIdActivity;
import com.example.canteenpr.model.AdminModel;
import com.example.canteenpr.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class AdminProfileAdapter extends RecyclerView.Adapter<AdminProfileAdapter.ViewHolder> {
    private Context mContext;
    List<AdminModel> adminModels = new ArrayList<AdminModel>();


    public AdminProfileAdapter(Context c, List<AdminModel> adminModels) {
        mContext = c;
        this.adminModels = adminModels;
    }

    @NonNull
    @Override
    public AdminProfileAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.admin_profile_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdminProfileAdapter.ViewHolder viewHolder, final int i) {
        AdminModel adminModel=adminModels.get(i);

        viewHolder.tv_name.setText(adminModel.getName());
        viewHolder.tvn_pkid.setText(adminModel.getPkid());
        viewHolder.tv_phone.setText(adminModel.getPhone());
        viewHolder.tv_wallet.setText(adminModel.getWallet());
        viewHolder.tv_password.setText(adminModel.getPassword());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pk_id=adminModel.getPkid();
                Intent intent=new Intent(mContext, PkIdActivity.class);
                intent.putExtra("pk_id",pk_id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

            }
        });
    }
    @Override
    public int getItemCount() {
        return adminModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name,tv_phone,tv_password,tv_wallet,tvn_pkid;

        public ViewHolder(View view) {
            super(view);
            tv_name=view.findViewById(R.id.tvn_name);
            tvn_pkid=view.findViewById(R.id.tvn_pkid);
            tv_phone=view.findViewById(R.id.tvn_mnumber);
            tv_wallet=view.findViewById(R.id.tvn_wallet);
            tv_password=view.findViewById(R.id.tvn_password);
        }
    }

}
