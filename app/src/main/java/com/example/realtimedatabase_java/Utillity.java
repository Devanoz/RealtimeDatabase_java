package com.example.realtimedatabase_java;

import android.app.Dialog;
import android.content.Context;

import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Utillity {

    public static void showEditDialog(Context context, Modul user, FirebaseDatabase db){
        Dialog dialog = new Dialog(context);

        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_edit_user);
        dialog.getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        EditText etName = dialog.findViewById(R.id.etEditName);
        EditText etNim = dialog.findViewById(R.id.etEditNim);

        etName.setText(user.getName());
        etNim.setText(user.getNim());

        Button editButton = dialog.findViewById(R.id.btnEdit);
        Button cancelButton = dialog.findViewById(R.id.btnCancel);

        editButton.setOnClickListener(view -> {
            if(!etName.getText().toString().isEmpty() && !etNim.getText().toString().isEmpty()){
                Map<String,Object> updatedData = new HashMap<>();

                updatedData.put("name",etName.getText().toString().trim());
                updatedData.put("nim",etNim.getText().toString());

                db.getReference("user").child(user.getId()).updateChildren(updatedData).addOnSuccessListener(runnable -> {
                    dialog.dismiss();

                }).addOnFailureListener(runnable -> {
                    Toast.makeText(MainActivity.context,"Data gagal diupdate",Toast.LENGTH_SHORT);
                });
            }
        });

        cancelButton.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog.show();
    }

}
