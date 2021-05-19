package com.example.andproject.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.andproject.models.Recipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment {


    private RecyclerView recyclerView;


    private FirebaseAuth mAuth;
    private String currentUserID;

    private FloatingActionButton fab;
    private EditText editTextAddRecipe;

    private List<Recipe> recipeArrayList;


    private RecipeAdapter recipeAdapter;

    FirebaseDatabase database;
    DatabaseReference reference;



    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        editTextAddRecipe = view.findViewById(R.id.editTextTextAddRecipe);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.hasFixedSize();
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Recipes").child(currentUserID);

        recipeArrayList = new ArrayList<>();

//        emptyID = reference.push().getKey();
//        Recipe emptyRecipe = new Recipe(emptyID, null);
//        reference.child(emptyID).setValue(emptyRecipe);



        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String recipeName = editTextAddRecipe.getText().toString().trim();

                    String id = reference.push().getKey();
                    Recipe recipe = new Recipe(id, recipeName);
                    reference.child(id).setValue(recipe);
                    editTextAddRecipe.getText().clear();
                    InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getView().getWindowToken(),0);

                recipeArrayList.add(recipe);
                recyclerView.setAdapter(recipeAdapter);







            }

        });



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.exists()){
                        recipeArrayList = new ArrayList<>();

                        for (DataSnapshot npsnapshot : snapshot.getChildren()) {

                            Recipe zero = npsnapshot.getValue(Recipe.class);

                            recipeArrayList.add(zero);
                        }

                        recipeAdapter = new RecipeAdapter(recipeArrayList);
                        recyclerView.setAdapter(recipeAdapter);



                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };

            reference.addListenerForSingleValueEvent(eventListener);
    }


}