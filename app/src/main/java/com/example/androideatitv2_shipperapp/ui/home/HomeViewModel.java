package com.example.androideatitv2_shipperapp.ui.home;

import com.example.androideatitv2_shipperapp.CallBack.IShippingOrderCallBackListener;
import com.example.androideatitv2_shipperapp.Model.ShipperUserModel;
import com.example.androideatitv2_shipperapp.Model.ShippingOrderModel;
import com.example.androideatitv2_shipperapp.common.common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel implements IShippingOrderCallBackListener {

   private MutableLiveData<List<ShippingOrderModel>> shippingOrderMutableData;
   private MutableLiveData<String> messageError;

   private IShippingOrderCallBackListener listener;

    public HomeViewModel() {

        shippingOrderMutableData = new MutableLiveData<>();
        messageError = new MutableLiveData<>();
        listener = this;
    }

    public MutableLiveData<String> getMessageError() {
        return messageError;
    }

    public MutableLiveData<List<ShippingOrderModel>> getShippingOrderMutableData(String shipperPhone) {
        loadOrderByShipper(shipperPhone);
        return shippingOrderMutableData;
    }

    private void loadOrderByShipper(String shipperPhone) {
        List<ShippingOrderModel> tempList = new ArrayList<>();
        Query orderRef = FirebaseDatabase.getInstance().getReference(common.SHIPPER_ORDER_REF)
                .orderByChild("shipperPhone")
                .equalTo(common.currentShipperUser.getPhone());

        orderRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot orderSnapshot : dataSnapshot.getChildren())
                {
                    ShippingOrderModel shippingOrderModel = orderSnapshot.getValue(ShippingOrderModel.class);
                    tempList.add(shippingOrderModel);
                }
                listener.onShippingOrderLoadSuccess(tempList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onShippingOrderLoadFailed(databaseError.getMessage());
            }
        });
    }

    @Override
    public void onShippingOrderLoadSuccess(List<ShippingOrderModel> shippingOrderModelList) {
        shippingOrderMutableData.setValue(shippingOrderModelList);
    }

    @Override
    public void onShippingOrderLoadFailed(String message) {
        messageError.setValue(message);
    }
}