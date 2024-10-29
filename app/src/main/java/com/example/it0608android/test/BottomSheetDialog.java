package com.example.it0608android.test;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.example.it0608android.MenuActivity;
import com.example.it0608android.R;
import com.example.it0608android.database.CourseDB;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    CourseDB courseDB;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.bottom_sheet_layout,
                container, false);

        EditText courseNameEdt = v.findViewById(R.id.idEdtCourseName);
        EditText courseTracksEdt = v.findViewById(R.id.idEdtCourseTracks);
        EditText courseDurationEdt = v.findViewById(R.id.idEdtCourseDuration);
        EditText courseDescriptionEdt = v.findViewById(R.id.idEdtCourseDescription);
        Button addCourses = v.findViewById(R.id.idBtnAddCourse);
        Button viewAllCourses = v.findViewById(R.id.idBtnReadCourse);
        courseDB = new CourseDB(getActivity());

        // below line is to add on click listener for our add course button.
        addCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // below line is to get data from all edit text fields.
                String courseName = courseNameEdt.getText().toString();
                String courseTracks = courseTracksEdt.getText().toString();
                String courseDuration = courseDurationEdt.getText().toString();
                String courseDescription = courseDescriptionEdt.getText().toString();
                // validating if the text fields are empty or not.
                if (courseName.isEmpty() || courseTracks.isEmpty() || courseDuration.isEmpty() || courseDescription.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    return;
                }
                // on below line we are calling a method to add new
                // course to sqlite data and pass all our values to it.
                courseDB.addNewCourse(courseName, courseDuration, courseDescription, courseTracks);
                // after adding the data we are displaying a toast message.
                Toast.makeText(getActivity(), "Course has been added.", Toast.LENGTH_SHORT).show();
                courseNameEdt.setText("");
                courseDurationEdt.setText("");
                courseTracksEdt.setText("");
                courseDescriptionEdt.setText("");
                dismiss();
                Intent intent = new Intent(getActivity(), MenuActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }
}
