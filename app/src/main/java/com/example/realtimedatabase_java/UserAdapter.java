package com.example.realtimedatabase_java;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private ArrayList<Modul> userList;


    public UserAdapter(ArrayList<Modul> listUser) {
        this.userList = listUser;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView tvName;
        private final TextView tvNim;
        private final AppCompatImageButton btnEdit;
        private final AppCompatImageButton btnDelete;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvNim = itemView.findViewById(R.id.tvNim);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);


        }

        public AppCompatImageButton getBtnEdit() {
            return btnEdit;
        }

        public AppCompatImageButton getBtnDelete() {
            return btnDelete;
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(userList.get(position).getName());
        holder.tvNim.setText(userList.get(position).getNim());

        AppCompatImageButton btnEdit = holder.getBtnEdit();
        AppCompatImageButton btnDelete = holder.getBtnDelete();

        Modul user = userList.get(position);

        btnDelete.setOnClickListener(view -> {
           RealtimeDatabase.getInstance().getReference("user").child(user.getId()).removeValue().addOnSuccessListener(runnable -> {
               System.out.println("data berhasil dihapus");
           }).addOnFailureListener(runnable -> {

           });
        });

        btnEdit.setOnClickListener(view -> {
            Utillity.showEditDialog(MainActivity.context,user,FirebaseDatabase.getInstance());
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


}
