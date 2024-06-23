package com.example.gpa_calculator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gpa_calculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private Spinner universitySpinner;
    private String selectedUniversity;

    // Arrays to hold the IDs of EditText fields
    int[] courseTitleIds = {R.id.eTCourseTitle, R.id.eTCourseTitle2, R.id.eTCourseTitle3, R.id.eTCourseTitle4, R.id.eTCourseTitle5};
    int[] gradePtIds = {R.id.eTGradePt, R.id.eTGradePt2, R.id.eTGradePt3, R.id.eTGradePt4, R.id.eTGradePt5};
    int[] crHrIds = {R.id.eTCrHr, R.id.eTCrHr2, R.id.eTCrHr3, R.id.eTCrHr4, R.id.eTCrHr5};

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Inflate the layout using View Binding
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Spinner setup
        universitySpinner = findViewById(R.id.sPUnivs);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.ethiopian_universities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        universitySpinner.setAdapter(adapter);
        universitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedUniversity = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedUniversity = null;
            }
        });

//        set click listener on calculate gpa btn
        binding.calcBtn.setOnClickListener(v->{
//            calculate gpa and display it using dialogue
            // Calculate the total grade points and total credit hours
          calculateGPA();
        });



        }

        public void calculateGPA(){

            // Arrays to hold the values
            String[] courseTitles = new String[courseTitleIds.length];
            double[] gradePts = new double[gradePtIds.length];
            double[] crHrs = new double[crHrIds.length];

            double totalGradePoints = 0;
            double totalCreditHours = 0;

            // Loop through each array to get the values from the EditText fields
            for (int i = 0; i < courseTitleIds.length; i++) {
                courseTitles[i] = ((EditText) findViewById(courseTitleIds[i])).getText().toString();
                gradePts[i] = Double.parseDouble(((EditText) findViewById(gradePtIds[i])).getText().toString());
                crHrs[i] = Double.parseDouble(((EditText) findViewById(crHrIds[i])).getText().toString());

                // Calculate the total grade points and total credit hours
                totalGradePoints += gradePts[i] * crHrs[i];
                totalCreditHours += crHrs[i];
            }

            // Calculate GPA
            double gpa = totalGradePoints / totalCreditHours;

            // Display the GPA (Assuming you have a TextView to display the result)
            new AlertDialog.Builder(this)
                    .setTitle("Your GPA ")
                    .setMessage(String.format("Your GPA is: %.2f", gpa))
                    .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Clear the form
                            for (int i = 0; i < courseTitleIds.length; i++) {
                                ((EditText) findViewById(courseTitleIds[i])).setText("");
                                ((EditText) findViewById(gradePtIds[i])).setText("");
                                ((EditText) findViewById(crHrIds[i])).setText("");
                            }
                            universitySpinner.setSelection(0); // Reset the spinner to the first item
                        }
                    })
                    .show();

        }

}