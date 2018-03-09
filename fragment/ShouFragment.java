package com.example.kao.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kao.Application.application;
import com.example.kao.Main2Activity;
import com.example.kao.R;
import com.example.kao.Student;
import com.example.kao.StudentDao;
import com.example.kao.adap.ShouAdap;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShouFragment extends Fragment {


    private RecyclerView ReView1;
    private StudentDao studentDao;
    private ShouAdap shouAdap;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_shou, container, false);
        studentDao = application.application.getdaoSession().getStudentDao();
        initView(inflate);
        initData();
        return inflate;
    }

    private void initData() {
        final List<Student> students = studentDao.loadAll();
        shouAdap = new ShouAdap(students,getActivity());
        ReView1.setAdapter(shouAdap);
        shouAdap.setOnClick(new ShouAdap.OnClickItem() {
            @Override
            public void setClick(View v, int position) {
                Intent intent=new Intent(getActivity(), Main2Activity.class);
                intent.putExtra("name",students.get(position).getName());
                intent.putExtra("context",students.get(position).getContext());
                intent.putExtra("image",students.get(position).getImage());
                startActivity(intent);
            }
        });
        shouAdap.setOnItemLongClickListener(new ShouAdap.ClickListener() {
            @Override
            public void setOnItemLongClickListener(View v, int position) {
                studentDao.delete(students.get(position));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        List<Student> students1 = studentDao.loadAll();
        shouAdap.Liedap(students1);
        shouAdap.notifyDataSetChanged();
    }

    private void initView(View inflate) {
        ReView1 = (RecyclerView) inflate.findViewById(R.id.ReView1);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        ReView1.setLayoutManager(layoutManager);
    }
}
