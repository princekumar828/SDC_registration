package com.prince.sdcapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_basic_info#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_basic_info extends Fragment {


    private EditText editTextFirstName;
    private EditText editTextLastName;

    private EditText editTextDOB;

    private EditText editTextGender;

    private EditText editTextAge;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_basic_info() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_basic_info.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_basic_info newInstance(String param1, String param2) {
        fragment_basic_info fragment = new fragment_basic_info();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic_info, container, false);

        editTextFirstName = view.findViewById(R.id.fnameEdit);
        editTextLastName = view.findViewById(R.id.lnameEdit);

        editTextDOB = view.findViewById(R.id.dobEdit);
        editTextGender = view.findViewById(R.id.genderEdit);
        editTextAge = view.findViewById(R.id.ageEdit);


        return view;
    }

    public boolean isFieldsValid() {


        // Check if any of the required fields are empty
        if ( TextUtils.isEmpty(editTextFirstName.getText().toString().trim())) {
            editTextFirstName.setError("Please enter first name");
            return false;
        }

        if ( TextUtils.isEmpty(editTextLastName.getText().toString().trim())) {
            editTextLastName.setError("Please enter last name");
            return false;
        }

        if (TextUtils.isEmpty(editTextDOB.getText().toString().trim())) {
            editTextDOB.setError("Please enter date of birth");
            return false;
        }

        if (TextUtils.isEmpty(editTextGender.getText().toString().trim())) {
            editTextGender.setError("Please enter gender");
            return false;
        }

        if (TextUtils.isEmpty(editTextAge.getText().toString().trim())) {
            editTextAge.setError("Please enter age");
            return false;
        }

        // All fields are valid
        return true;
    }

    public String getFirstName() {
        return editTextFirstName.getText().toString();
    }

    public String getLastName() {
        return editTextLastName.getText().toString().trim();
    }

    public String getDOB() {
        return editTextDOB.getText().toString();
    }

    public String getGender() {
        return editTextGender.getText().toString();
    }

    public String getAge() {
        return editTextAge.getText().toString();
    }

     void initializeEditTextFields(View view) {
        editTextFirstName = view.findViewById(R.id.fnameEdit);
        editTextLastName = view.findViewById(R.id.lnameEdit);
        editTextDOB = view.findViewById(R.id.dobEdit);
        editTextGender = view.findViewById(R.id.genderEdit);
        editTextAge = view.findViewById(R.id.ageEdit);
    }




}