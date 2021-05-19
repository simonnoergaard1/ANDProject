package com.example.andproject.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andproject.R;
import com.example.andproject.models.Recipe;
import com.example.andproject.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.RecyclerViewHolder> {

    private List<User> mUsers;
    private LayoutInflater layoutInflater;


    public UserAdapter(List<User> mUsers) {
        this.mUsers = mUsers;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.friendlist_textview, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        User user = mUsers.get(position);
        holder.ageText.setText(user.getAge());
        holder.emailText.setText(user.getEmail());
        holder.nameText.setText(user.getName());

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView ageText, emailText, nameText;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            ageText = itemView.findViewById(R.id.ageText);
            emailText = itemView.findViewById(R.id.emailText);
            nameText = itemView.findViewById(R.id.nameText1);

        }

    }

}
