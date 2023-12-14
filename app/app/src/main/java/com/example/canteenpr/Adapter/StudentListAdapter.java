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
import com.example.canteenpr.admin.AdminRecharegeActivity;
import com.example.canteenpr.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder> {
    private Context mContext;
    List<UserModel> userModels = new ArrayList<UserModel>();


    public StudentListAdapter(Context c, List<UserModel> userModels) {
        mContext = c;
        this.userModels = userModels;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StudentListAdapter.ViewHolder viewHolder, int i) {
        UserModel userModel=userModels.get(i);
        viewHolder.s_name.setText("Name :\t"+userModel.getName());
        viewHolder.s_mobile.setText("Mobile No :\t"+userModel.getMnumber());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone=userModel.getMnumber();
                String pk_id=userModel.getPkid();
                Intent intent= new Intent(mContext, AdminRecharegeActivity.class);
                intent.putExtra("Phone",phone);
                intent.putExtra("pk_id",pk_id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView s_name,s_mobile;
        public ViewHolder(View view) {
            super(view);
            s_name=view.findViewById(R.id.s_name);
            s_mobile=view.findViewById(R.id.s_mobile);
        }
    }
}