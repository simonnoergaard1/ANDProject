package com.example.andproject.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.andproject.R;
import com.example.andproject.adapters.RecipeAdapter;
import com.example.andproject.adapters.UserAdapter;
import com.example.andproject.models.Recipe;
import com.example.andproject.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FriendsFragment extends Fragment {

    private RecyclerView recyclerView;


    private FirebaseAuth mAuth;
    private String currentUserID;


    private List<User> userArrayList;


    private UserAdapter userAdapter;

    FirebaseDatabase database;
    DatabaseReference reference;



    public FriendsFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.friends_fragment, container, false);


        recyclerView = view.findViewById(R.id.recyclerView2);
        recyclerView.hasFixedSize();
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Users")  ;


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    userArrayList = new ArrayList<>();

                    for (DataSnapshot npsnapshot : snapshot.getChildren()) {

                        User zero = npsnapshot.getValue(User.class);

                        userArrayList.add(zero);
                    }

                    userAdapter = new UserAdapter(userArrayList);
                    recyclerView.setAdapter(userAdapter);



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        reference.addListenerForSingleValueEvent(eventListener);
    }


}