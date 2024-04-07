package com.prince.sdcapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link security#newInstance} factory method to
 * create an instance of this fragment.
 */
public class security extends Fragment {

    private EditText editTextEmail;
    private EditText editTextPhoneNumber;
    private EditText editTextPassword;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public security() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment security.
     */
    // TODO: Rename and change types and number of parameters
    public static security newInstance(String param1, String param2) {
        security fragment = new security();
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
        View view = inflater.inflate(R.layout.fragment_security, container, false);

        editTextEmail = view.findViewById(R.id.emailEdit);
        editTextPhoneNumber = view.findViewById(R.id.phoneEdit);
        editTextPassword = view.findViewById(R.id.passwordedit);

        return view;
    }
    public boolean isFieldsValid() {
        if (!isValidEmail(editTextEmail.getText().toString().trim())) {
            editTextEmail.setError("Please enter a valid email (example@student.nitw.ac.in)");
            return false;
        }

        if (!isValidPhoneNumber(editTextPhoneNumber.getText().toString().trim())) {
            editTextPhoneNumber.setError("Please enter a valid phone number (10 digits)");
            return false;
        }

        if (!isValidPassword(editTextPassword.getText().toString().trim())) {
            editTextPassword.setError("Please enter a valid password (at least 6 characters)");
            return false;
        }

        // All fields are valid
        return true;
    }

    // Validate email format using regex pattern
    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9+_.-]+@student\\.nitw\\.ac\\.in$");
        Pattern pattern2 = Pattern.compile("^[a-zA-Z0-9+_.-]+@\\.nitw\\.ac\\.in$");
        return pattern.matcher(email).matches() ||pattern2.matcher(email).matches();
    }

    // Validate phone number format (10 digits)
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.length() == 10 && phoneNumber.matches("[0-9]+");
    }

    // Validate password format (at least 6 characters)
    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

    public void initializeEditTextFields(View view) {
        editTextEmail = view.findViewById(R.id.emailEdit);
        editTextPhoneNumber = view.findViewById(R.id.phoneEdit);
        editTextPassword = view.findViewById(R.id.passwordedit);
    }

    public String getEmail() {
        return editTextEmail.getText().toString();
    }

    public String getPhoneNumber() {
        return editTextPhoneNumber.getText().toString();
    }

    public String getPassword() {
        return editTextPassword.getText().toString();
    }
}