package com.prince.sdcapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActivityRegistration extends AppCompatActivity {
    private ViewPager viewPager;

    private TextView nextText;
    private TextView textViewPerson;
    private TextView textViewAcademic;
    private TextView textViewSecurity;
    private LinearLayout buttonNext;

    private LinearLayout buttonPrevious;
    private LinearLayout line1;
    private LinearLayout line2;
    private LinearLayout line3;

    private ImageView img1;
    private ImageView img3;

    private ImageView img2;

    private ProgressBar progressBar;

    RegistrationPagerAdapter pagerAdapter;
    private int currentPosition = 0;


    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

   private fragment_basic_info personalInfoFragment;
   private fragment_academic_details academicInfoFragment;
   private security securityFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);





        // Initialize views
        viewPager = findViewById(R.id.viewPager);
        textViewPerson = findViewById(R.id.textViewPerson);
        textViewAcademic = findViewById(R.id.textViewAcademic);
        textViewSecurity = findViewById(R.id.textViewSecurity);
        buttonNext = findViewById(R.id.buttonNext);
        buttonPrevious = findViewById(R.id.buttonPrevious);

        line1=findViewById(R.id.line1);
        line2=findViewById(R.id.line2);
        line3=findViewById(R.id.line3);

        img1=findViewById(R.id.img1);
        img2=findViewById(R.id.img2);
        img3=findViewById(R.id.img3);

        progressBar=findViewById(R.id.progress_circular);


        nextText=findViewById(R.id.nextText);
        // Set initial indicator colors
        updateIndicatorColors();

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser!=null)
        {
            startActivity(new Intent(ActivityRegistration.this, MainActivity.class));
            finish();
        }


        // Initialize ViewPager and set adapter
        pagerAdapter = new RegistrationPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);


        // Initialize  fragments
       personalInfoFragment = new fragment_basic_info();
       academicInfoFragment =new fragment_academic_details();
       securityFragment =new  security() ;

        pagerAdapter = new RegistrationPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(personalInfoFragment);
        pagerAdapter.addFragment(academicInfoFragment);
        pagerAdapter.addFragment(securityFragment);
        viewPager.setAdapter(pagerAdapter);
        // ViewPager listener to update current position
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                buttonPrevious.setVisibility(position == 0 ? android.view.View.INVISIBLE : android.view.View.VISIBLE);
                nextText.setText(position == pagerAdapter.getCount() - 1 ? "Finish" : "Next");
                updateIndicatorColors();
            }
        });



       //next Button
        buttonNext.setOnClickListener(v -> {
            if (currentPosition < pagerAdapter.getCount() - 1) {
                if (validateCurrentFragment()) {
                    viewPager.setCurrentItem(currentPosition + 1);
                } else {
                    Toast.makeText(this, "Please fill out all required fields.", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (validateCurrentFragment()) {
                    createUser();
                } else {
                    Toast.makeText(this, "Please fill out all required fields.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        buttonPrevious.setVisibility(currentPosition == 0 ? android.view.View.INVISIBLE : android.view.View.VISIBLE);
        buttonPrevious.setOnClickListener(v -> {
            if (currentPosition > 0) {
                currentPosition--;
                viewPager.setCurrentItem(currentPosition);
                updateIndicatorColors();
            }
        });

        // Click listeners for step indicators
        textViewPerson.setOnClickListener(v -> {
            if (validateCurrentFragment()) {
                if (currentPosition != 0) {
                    currentPosition = 0;
                    viewPager.setCurrentItem(currentPosition);
                    updateIndicatorColors();
                }
            } else {
                Toast.makeText(this, "Please fill out all required fields.", Toast.LENGTH_SHORT).show();
            }

        });

        textViewAcademic.setOnClickListener(v -> {
            if (validateCurrentFragment()) {
                if (currentPosition != 1) {
                    currentPosition = 1;
                    viewPager.setCurrentItem(currentPosition);
                    updateIndicatorColors();
                }
            } else {
                Toast.makeText(this, "Please fill out all required fields.", Toast.LENGTH_SHORT).show();
            }
        });

        textViewSecurity.setOnClickListener(v -> {
            if (validateCurrentFragment()) {
                if (currentPosition != 2) {
                    currentPosition = 2;
                    viewPager.setCurrentItem(currentPosition);
                    updateIndicatorColors();
                }
            }
            else {
                Toast.makeText(this, "Please fill out all required fields.", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private boolean validateCurrentFragment() {
        Fragment currentFragment = pagerAdapter.getItem(currentPosition);
        if (currentFragment instanceof fragment_basic_info) {
            fragment_basic_info basicInfoFragment = (fragment_basic_info) currentFragment;
            return basicInfoFragment != null && basicInfoFragment.isFieldsValid();
        } else if (currentFragment instanceof fragment_academic_details) {
            fragment_academic_details academicDetailsFragment = (fragment_academic_details) currentFragment;
            return academicDetailsFragment != null && academicDetailsFragment.isFieldsValid();
        } else if (currentFragment instanceof security) {
            security securityFragment = (security) currentFragment;
            return securityFragment != null && securityFragment.isFieldsValid();
        }
        return true;
    }


    private void createUser() {
        progressBar.setVisibility(View.VISIBLE);

        String firstName = personalInfoFragment.getFirstName();
        String lastName = personalInfoFragment.getLastName();
        String dob = personalInfoFragment.getDOB();
        String gender = personalInfoFragment.getGender();
        String age = personalInfoFragment.getAge();


        String rollNumber = academicInfoFragment.getRollNumber();
        String branch = academicInfoFragment.getBranch();
        String year = academicInfoFragment.getYear();
        String selectedPdfPath = academicInfoFragment.getSelectedPdfPath(); // Get selected PDF file path

        String email = securityFragment.getEmail();
        String phoneNumber = securityFragment.getPhoneNumber();
        String password = securityFragment.getPassword();

        // Create user authentication with email and password
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    // User authentication successful
                    String userId = Objects.requireNonNull(authResult.getUser()).getUid();
                   uplodeUser(selectedPdfPath, userId, firstName, lastName, rollNumber, branch, email, phoneNumber,dob,gender,age,year);
                    // Upload PDF file to Firebase Storage
                   // uploadPdf(selectedPdfPath, userId, firstName, lastName, rollNumber, branch, email, phoneNumber,dob,gender,age,year);
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    // User authentication failed
                    Toast.makeText(ActivityRegistration.this, "Authentication failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }

    private  void uplodeUser(String pdfFilePath, String userId, String firstName, String lastName,
                           String rollNumber, String branch, String email, String phoneNumber,String dob,String gender,String age,String year){
               // Create a User object or map to store in Firestore
                    Map<String, Object> user = new HashMap<>();
                    user.put("firstName", firstName);
                    user.put("lastName", lastName);
                    user.put("dob", dob);
                    user.put("gender", gender);
                    user.put("age", age);

                    user.put("branch", branch);
                    user.put("rollnumber", rollNumber);
                    user.put("year", year);
                   // user.put("registrationSlipUrl", pdfDownloadUrl);

                    user.put("email", email);
                    user.put("phoneNumber", phoneNumber);



                    // Add user data to Firestore
                    firestore.collection("users").document(userId)
                            .set(user)
                            .addOnSuccessListener(aVoid -> {
                                progressBar.setVisibility(View.GONE);
                                // User data stored successfully
                                Toast.makeText(ActivityRegistration.this, "User registered successfully!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ActivityRegistration.this, MainActivity.class));
                                finish();
                            })
                            .addOnFailureListener(e -> {
                                progressBar.setVisibility(View.GONE);
                                // Error storing user data in Firestore
                                Toast.makeText(ActivityRegistration.this, "Failed to register user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });



                         }

//    private void uploadPdf(String pdfFilePath, String userId, String firstName, String lastName,
//                           String rollNumber, String branch, String email, String phoneNumber,String dob,String gender,String age,String year) {
//        if (pdfFilePath != null) {
//            FirebaseStorage storage = FirebaseStorage.getInstance();
//            StorageReference storageRef = storage.getReference();
//
//            // Create a reference to store PDF file in Firebase Storage
//            StorageReference pdfRef = storageRef.child("registration_slips/" + userId + ".pdf");
//
//            // Upload PDF file to Firebase Storage
//            UploadTask uploadTask = pdfRef.putFile(Uri.fromFile(new File(pdfFilePath)));
//
//            uploadTask.addOnSuccessListener(taskSnapshot -> {
//                // PDF file uploaded successfully, now store user data in Firestore
//                pdfRef.getDownloadUrl().addOnSuccessListener(downloadUrl -> {
//                    String pdfDownloadUrl = downloadUrl.toString();
//
//                    // Create a User object or map to store in Firestore
//                    Map<String, Object> user = new HashMap<>();
//                    user.put("firstName", firstName);
//                    user.put("lastName", lastName);
//                    user.put("dob", dob);
//                    user.put("gender", gender);
//                    user.put("age", age);
//
//                    user.put("branch", branch);
//                    user.put("rollnumber", rollNumber);
//                    user.put("year", year);
//                    user.put("registrationSlipUrl", pdfDownloadUrl);
//
//                    user.put("email", email);
//                    user.put("phoneNumber", phoneNumber);
//
//
//
//                    // Add user data to Firestore
//                    firestore.collection("users").document(userId)
//                            .set(user)
//                            .addOnSuccessListener(aVoid -> {
//                                // User data stored successfully
//                                Toast.makeText(ActivityRegistration.this, "User registered successfully!", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(ActivityRegistration.this, MainActivity.class));
//                                finish();
//                            })
//                            .addOnFailureListener(e -> {
//                                // Error storing user data in Firestore
//                                Toast.makeText(ActivityRegistration.this, "Failed to register user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                            });
//                });
//            }).addOnFailureListener(e -> {
//                // PDF file upload failed
//                Toast.makeText(ActivityRegistration.this, "Failed to upload PDF file: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            });
//        } else {
//            // No PDF file selected
//            Toast.makeText(ActivityRegistration.this, "Please select a PDF file", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//

    // Update indicator colors based on current position
    private void updateIndicatorColors() {
        textViewPerson.setTextColor(getResources().getColor(currentPosition == 0 ? R.color.black : R.color.gray));
        line1.setVisibility(currentPosition==0?View.VISIBLE:View.INVISIBLE);
        line2.setVisibility(currentPosition==1?View.VISIBLE:View.INVISIBLE);
        line3.setVisibility(currentPosition==2?View.VISIBLE:View.INVISIBLE);
        img1.setVisibility(currentPosition==0?View.VISIBLE:View.GONE);
        img2.setVisibility(currentPosition==1?View.VISIBLE:View.GONE);
        img3.setVisibility(currentPosition==2?View.VISIBLE:View.GONE);
        textViewAcademic.setTextColor(getResources().getColor(currentPosition == 1 ? R.color.black : R.color.gray));
        textViewSecurity.setTextColor(getResources().getColor(currentPosition == 2 ? R.color.black : R.color.gray));
    }
}

