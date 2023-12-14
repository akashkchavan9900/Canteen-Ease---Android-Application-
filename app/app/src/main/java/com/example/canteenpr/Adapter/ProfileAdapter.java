package com.example.canteenpr.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canteenpr.R;
import com.example.canteenpr.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
    private Context mContext;
    List<UserModel> userModels = new ArrayList<UserModel>();



    public ProfileAdapter(Context c, List<UserModel> userModels) {
          mContext = c;
        this.userModels = userModels;
    }

    @NonNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profile_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProfileAdapter.ViewHolder viewHolder, final int i) {
        UserModel userModel=userModels.get(i);
        viewHolder.tv_name.setText(userModel.getName());
        viewHolder.gender.setText(userModel.getGender());
        viewHolder.m_number.setText( userModel.getMnumber());
        viewHolder.dob.setText(userModel.getBdate());
        viewHolder.address.setText(userModel.getAddress());
        viewHolder.collage_id.setText(userModel.getColg_id());
        viewHolder.department.setText(userModel.getDepartment());
        viewHolder.acedemic_year.setText(userModel.getAcadamic_year());
        viewHolder.tvn_wallet.setText("Wallet Amount :-"+userModel.getWallet());
    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
          TextView tvn_wallet,tv_name,gender,m_number,dob,address,department,acedemic_year,collage_id;

        public ViewHolder(@NonNull View view) {
            super(view);
            tvn_wallet=view.findViewById(R.id.tvn_wallet);
            tv_name=view.findViewById(R.id.tvn_sname);
            gender=view.findViewById(R.id.tvn_sgender);
            m_number=view.findViewById(R.id.tvn_smnumber);
            dob=view.findViewById(R.id.tvn_sbirthdate);
            address=view.findViewById(R.id.tvn_saddress);
            collage_id=view.findViewById(R.id.tvn_scollageid);
            department=view.findViewById(R.id.tvn_sdepartment);
            acedemic_year=view.findViewById(R.id.tvn_sacadamic_year);
        }
    }
}
