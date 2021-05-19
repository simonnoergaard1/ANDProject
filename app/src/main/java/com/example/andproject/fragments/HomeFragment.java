package com.example.andproject.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.andproject.R;
import com.example.andproject.adapters.RecipeAdapter;
import com.example.andproject.models.Recipe;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private Random randomGenerator;
    private List<String> generatedList;

    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference userRef;
    private FirebaseAuth mAuth;
    private String currentUserID;
    private TextView randomRecipe;
    private TextView username, email;
    private Button generateButton;
    private View headerView;
    private NavigationView navigationView;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment, container, false);

        randomGenerator = new Random();
        randomRecipe = view.findViewById(R.id.randomRecipe);
        generatedList = new ArrayList<>();

        navigationView = getActivity().findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        username = headerView.findViewById(R.id.usernameText);
        email = headerView.findViewById(R.id.emailText);


        generateButton = view.findViewById(R.id.generateButton);
        generateButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Recipes").child(currentUserID);
        userRef = database.getReference("Users").child(currentUserID);

        loadRandomRecipe();
        setUsernameAndEmail();

        return view;
    }

    private void setUsernameAndEmail() {

        ValueEventListener eventListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    String nameUser = snapshot.child("name").getValue(String.class);
                    String userEmail = snapshot.child("email").getValue(String.class);

                    username.setText(nameUser);
                    email.setText(userEmail);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        userRef.addListenerForSingleValueEvent(eventListener2);

    }

    private void loadRandomRecipe() {
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    generatedList = new ArrayList<>();

                    for (DataSnapshot npsnapshot : snapshot.getChildren()) {

                        String recipe = npsnapshot.child("recipe").getValue(String.class);
                        generatedList.add(recipe);
                    }





                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        reference.addListenerForSingleValueEvent(eventListener);
    }


    @Override
    public void onClick(View v) {

        int index = randomGenerator.nextInt(generatedList.size());
        String genRecipe = generatedList.get(index);

        randomRecipe.setText(genRecipe);

    }
}