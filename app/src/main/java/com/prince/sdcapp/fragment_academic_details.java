package com.prince.sdcapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_academic_details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_academic_details extends Fragment {

    private static final int REQUEST_CODE_PICK_PDF = 1;


    private EditText editTextRollNumber;
    private EditText editTextBranch;

    private EditText editTextYear;

    private String selectedPdfPath; // Store selected PDF file path

    private LinearLayout uplodeButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_academic_details() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_academic_details.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_academic_details newInstance(String param1, String param2) {
        fragment_academic_details fragment = new fragment_academic_details();
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
        View view = inflater.inflate(R.layout.fragment_academic_details, container, false);

        editTextRollNumber = view.findViewById(R.id.rollEdit);
        editTextBranch = view.findViewById(R.id.branchEdit);
        editTextYear = view.findViewById(R.id.yearEdit);
        uplodeButton=view.findViewById(R.id.buttonUplode);

        uplodeButton.setOnClickListener(v-> openPdfPicker());
        // Initialize other EditText fields

        return view;
    }

    private void openPdfPicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, REQUEST_CODE_PICK_PDF);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_PDF && resultCode == Activity.RESULT_OK && data != null) {
            Uri pdfUri = data.getData();
            if (pdfUri != null) {
                selectedPdfPath = getPathFromUri(pdfUri);
                if (selectedPdfPath != null) {
                    // Successfully obtained the PDF file path
                    Toast.makeText(requireContext(), "PDF selected: " + selectedPdfPath, Toast.LENGTH_SHORT).show();
                } else {
                    // Error: Failed to retrieve PDF file path
                    Toast.makeText(requireContext(), "Failed to retrieve PDF file path", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Error: PDF URI is null
                Toast.makeText(requireContext(), "PDF URI is null", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private String getPathFromUri(Uri uri) {
        String filePath = null;
        String scheme = uri.getScheme();

        if (scheme != null && scheme.equals("content")) {
            Cursor cursor = null;
            try {
                cursor = requireActivity().getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
                    filePath = cursor.getString(columnIndex);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        } else if (scheme != null && scheme.equals("file")) {
            filePath = uri.getPath();
        }

        return filePath;
    }



    public boolean isFieldsValid() {
        if (TextUtils.isEmpty(editTextRollNumber.getText().toString().trim())) {
            editTextRollNumber.setError("Please enter roll number");
            return false;
        }

        if (TextUtils.isEmpty(editTextBranch.getText().toString().trim())) {
            editTextBranch.setError("Please enter branch");
            return false;
        }

        if (TextUtils.isEmpty(editTextYear.getText().toString().trim())) {
            editTextYear.setError("Please enter year");
            return false;
        }

        // Check if PDF file is selected
        if (TextUtils.isEmpty(selectedPdfPath)) {
            // Show error message for PDF selection
            Toast.makeText(requireContext(), "Please select a PDF file", Toast.LENGTH_SHORT).show();
          //  return false;
        }

        // All fields are valid
        return true;
    }

    // Other methods to get and set academic information
    public String getRollNumber() {
        return editTextRollNumber.getText().toString();
    }

    public String getBranch() {
        return editTextBranch.getText().toString();
    }

    public String getYear() {
        return editTextYear.getText().toString();
    }
    public String getSelectedPdfPath() {
        return selectedPdfPath;
    }



}