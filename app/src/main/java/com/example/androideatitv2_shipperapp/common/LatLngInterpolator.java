package com.example.androideatitv2_shipperapp.common;

import com.google.android.gms.maps.model.LatLng;

public interface LatLngInterpolator {
     LatLng interpolate(float fraction , LatLng a,LatLng b);

     class Linear implements LatLngInterpolator{

         @Override
         public LatLng interpolate(float fraction, LatLng a, LatLng b) {

             return null;
         }
     }
}
