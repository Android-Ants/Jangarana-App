package com.woc.jangarana.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.woc.jangarana.R;
import com.woc.jangarana.databinding.FragmentAddFirstBinding;
import com.woc.jangarana.familyhead.DashboardActivity;
import com.woc.jangarana.models.Person;
import com.woc.jangarana.viewmodels.PersonDetailViewModel;


public class AddFirstFragment extends Fragment {


    Context context;
    PersonDetailViewModel personDetailViewModel;

    SharedPreferences sharedPreferences;
    Person person;

    FragmentAddFirstBinding binding;


    public AddFirstFragment(Context context, PersonDetailViewModel personDetailViewModel) {
        this.context = context;
        this.personDetailViewModel = personDetailViewModel;
    }


    public AddFirstFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddFirstBinding.inflate(inflater, container,false);
        binding.familyHeadCensusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sharedPreferences = getActivity().getSharedPreferences("Head SignUp", MODE_PRIVATE);
                person = new Person();
                person.setId(sharedPreferences.getString("family_head_token" , ""));
                personDetailViewModel.personDetails.postValue(person);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flFragment,
                                new UserDetail1Fragment(context, personDetailViewModel))
                        .commit();
            }
        });

        binding.addMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sharedPreferences = getActivity().getSharedPreferences("Head SignUp", MODE_PRIVATE);
                person = new Person();
                person.setId(sharedPreferences.getString("family_head_token" , "")+Math.random()*1000000000);
                personDetailViewModel.personDetails.postValue(person);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flFragment,
                                new UserDetail1Fragment(context, personDetailViewModel))
                        .commit();
            }
        });

        return binding.getRoot();
    }
}