package com.example.androideatitv2_shipperapp.CallBack;

import com.example.androideatitv2_shipperapp.Model.ShippingOrderModel;

import java.util.List;

public interface IShippingOrderCallBackListener {

    void onShippingOrderLoadSuccess(List<ShippingOrderModel> shippingOrderModelList);
    void onShippingOrderLoadFailed(String message);
}
