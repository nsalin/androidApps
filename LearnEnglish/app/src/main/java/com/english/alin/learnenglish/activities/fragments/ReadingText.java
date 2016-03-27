package com.english.alin.learnenglish.activities.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.english.alin.learnenglish.R;
import com.english.alin.learnenglish.persistance.database.DatabaseManager;

/**
 * Created by Alin on 3/27/2016.
 */
public class ReadingText extends Fragment {
    public static ReadingText newInstance(){
        return new ReadingText();
    }

    public ReadingText(){

    }
//    @Override

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reading_text, container, false);
        TextView textView = (TextView) view.findViewById(R.id.tvTitle);
        DatabaseManager databaseManager = new DatabaseManager(getActivity().getApplicationContext());
        textView.setText(databaseManager.getLastReadingText());
        return view;
    }
}
