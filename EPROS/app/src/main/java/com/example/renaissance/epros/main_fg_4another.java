package com.example.renaissance.epros;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by Renaissance on 1/12/2017.
 */

public class main_fg_4another extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fg_4another, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        ImageButton btn_attachment = (ImageButton) getActivity().findViewById(R.id.btn_attachment);
        btn_attachment.setOnClickListener(new View.OnClickListener(){
                                              public void onClick(View v){
                                                  Intent myIntent = new Intent(main_fg_4another.this.getActivity(), attachment.class);
                                                  main_fg_4another.this.startActivity(myIntent);
                                              }
                                          }
        );
        ImageButton btn_profile = (ImageButton) getActivity().findViewById(R.id.btn_profile);
        btn_profile.setOnClickListener(new View.OnClickListener(){
                                           public void onClick(View v){
                                               Intent myIntent = new Intent(main_fg_4another.this.getActivity(), profil.class);
                                               main_fg_4another.this.startActivity(myIntent);
                                           }
                                       }
        );
        ImageButton btn_maps = (ImageButton) getActivity().findViewById(R.id.btn_maps);
        btn_maps.setOnClickListener(new View.OnClickListener(){
                                        public void onClick(View v){
                                            Intent myIntent = new Intent(main_fg_4another.this.getActivity(), maps.class);
                                            main_fg_4another.this.startActivity(myIntent);
                                        }
                                    }
        );
        ImageButton btn_setting = (ImageButton) getActivity().findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(new View.OnClickListener(){
                                           public void onClick(View v){
                                               Intent myIntent = new Intent(main_fg_4another.this.getActivity(), setting.class);
                                               main_fg_4another.this.startActivity(myIntent);
                                           }
                                       }
        );
        ImageButton btn_tipsandtrik = (ImageButton) getActivity().findViewById(R.id.btn_tipsandtrik);
        btn_tipsandtrik.setOnClickListener(new View.OnClickListener(){
                                               public void onClick(View v){
                                                   Intent myIntent = new Intent(main_fg_4another.this.getActivity(), tipsandtrik.class);
                                                   main_fg_4another.this.startActivity(myIntent);
                                               }
                                           }
        );
    }
}
