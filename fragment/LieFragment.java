package com.example.kao.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.kao.Application.application;
import com.example.kao.MyStringRequest;
import com.example.kao.R;
import com.example.kao.Student;
import com.example.kao.StudentDao;
import com.example.kao.adap.LieAdap;
import com.example.kao.base.DataDemo;
import com.google.gson.Gson;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LieFragment extends Fragment {
    private String url = "http://172.16.54.21:8080/Data/data.json";
    private RecyclerView ReView;
    private StudentDao studentDao;
    private LieAdap lieAdap;


    public LieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_lie, container, false);
        studentDao = application.application.getdaoSession().getStudentDao();
        initView(inflate);
        initData();
        return inflate;
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                MyStringRequest request=new MyStringRequest(url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        DataDemo dataDemo = gson.fromJson(response, DataDemo.class);
                        final List<DataDemo.StudentsBean.StudentBean> student = dataDemo.getStudents().getStudent();
                        lieAdap = new LieAdap(student,getActivity());
                        ReView.setAdapter(lieAdap);
                            lieAdap.setOnClick(new LieAdap.OnClickItem() {
                                @Override
                                public void setClick(View v, int position) {
                                    Student student1=new Student();
                                    student1.setName(student.get(position).getName());
                                    student1.setContext(student.get(position).getContent());
                                    student1.setImage(student.get(position).getImg());
                                    studentDao.insert(student1);
                                }
                            });
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                requestQueue.add(request);
            }
        }).start();

    }



    private void initView(View inflate) {
        ReView = (RecyclerView) inflate.findViewById(R.id.ReView);
        RecyclerView.LayoutManager lay=new LinearLayoutManager(getActivity());
        ReView.setLayoutManager(lay);
    }
}
