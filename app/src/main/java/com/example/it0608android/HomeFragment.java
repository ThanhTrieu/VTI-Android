package com.example.it0608android;

import static android.Manifest.permission.CALL_PHONE;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.it0608android.adapter.CourseRVAdapter;
import com.example.it0608android.database.CourseDB;
import com.example.it0608android.model.CourseModal;
import com.example.it0608android.test.BottomSheetDialog;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<CourseModal> courseModalArrayList;
    private CourseDB dbHandler;
    private CourseRVAdapter courseRVAdapter;
    private RecyclerView coursesRV;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button btnCallPhone = view.findViewById(R.id.btnCallPhone);
        EditText edtPhone = view.findViewById(R.id.edtPhone);
        Button btnURL = view.findViewById(R.id.btnURLWebsite);
        EditText edtURL = view.findViewById(R.id.edtURL);
        Button OpenBottomSheet = view.findViewById(R.id.open_bottom_sheet);

        OpenBottomSheet.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BottomSheetDialog bottomSheet = new BottomSheetDialog();
                        bottomSheet.show(getActivity().getSupportFragmentManager(),"ModalBottomSheet");
                    }
                });

        btnURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = edtURL.getText().toString().trim();
                if (TextUtils.isEmpty(url)){
                    edtURL.setError("URL can be not empty");
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        btnCallPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edtPhone.getText().toString().trim();
                //Toast.makeText(getActivity(), phone, Toast.LENGTH_SHORT).show();
                if (TextUtils.isEmpty(phone)){
                    edtPhone.setError("Phone number can not be empty");
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+phone));
                if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                    startActivity(intent);
                } else {
                    requestPermissions(new String[] { CALL_PHONE }, 1);
                }
            }
        });

        courseModalArrayList = new ArrayList<>();
        dbHandler = new CourseDB(getActivity());

        // getting our course array
        // list from db handler class.
        courseModalArrayList = dbHandler.readCourses();

        // on below line passing our array list to our adapter class.
        courseRVAdapter = new CourseRVAdapter(courseModalArrayList, getActivity());
        coursesRV = view.findViewById(R.id.idRVCourses);

        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        coursesRV.setLayoutManager(linearLayoutManager);

        // setting our adapter to recycler view.
        coursesRV.setAdapter(courseRVAdapter);

        return view;
    }
}