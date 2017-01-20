package com.example.renaissance.epros;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Renaissance on 1/13/2017.
 */

public class attachment_fg_2terima extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.attachment_fg_2terima, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
                                   public void onClick(View v){
                                       Intent myIntent = new Intent(attachment_fg_2terima.this.getActivity(), attachment_layout.class);
                                       attachment_fg_2terima.this.startActivity(myIntent);
                                   }
                               }
        );
    }

}
