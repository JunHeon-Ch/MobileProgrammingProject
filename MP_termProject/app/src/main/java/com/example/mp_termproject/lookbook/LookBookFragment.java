package com.example.mp_termproject.lookbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mp_termproject.R;


public class LookBookFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("LOOKBOOK");
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_lookbook,
                container,
                false);

//        db에 저장된 룩북 읽어와서 뿌려주는거 구현


        return rootView;
    }
}
