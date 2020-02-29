package com.example.fireo;

import android.os.Bundle;

import com.example.fireo.model.Device;
import com.google.firebase.firestore.FieldValue;

public class DummyDataAdd extends BaseApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_data_add);

//        for (int x = 0; x < 20; x++) {
//            Device device = new Device("dw2sgH0mVOqD5MEdfwia", true, "test_Building", x % 4, FieldValue.serverTimestamp(), "wuhan", "0", "low efficiency", 0, "timestampt2222222", true, x * 2, 0, "timeStamp2222", 21 * x, "timeStamp2222", "timeStamp2222", "22.3", 2.1f, 3.2f);
//            getFireStore().collection("building").document("dw2sgH0mVOqD5MEdfwia").collection("floor0").document().set(device);
//
//        }
//        for (int x = 0; x < 20; x++) {
//            Device device = new Device("dw2sgH0mVOqD5MEdfwia", true, "test_Building", x % 4, FieldValue.serverTimestamp(), "wuhan", "1", "low efficiency", 1, "timestampt2222222", true, x * 2, 0, "timeStamp2222", 21 * x, "timeStamp2222", "timeStamp2222", "22.3", 2.1f, 3.2f);
//            getFireStore().collection("building").document("dw2sgH0mVOqD5MEdfwia").collection("floor1").document().set(device);
//        }
//        for (int x = 0; x < 20; x++) {
//            Device device = new Device("dw2sgH0mVOqD5MEdfwia", true, "test_Building", x % 4, FieldValue.serverTimestamp(), "wuhan", "1", "low efficiency", 2, "timestampt2222222", true, x * 2, 0, "timeStamp2222", 21 * x, "timeStamp2222", "timeStamp2222", "22.3", 2.1f, 3.2f);
//            getFireStore().collection("building").document("dw2sgH0mVOqD5MEdfwia").collection("floor2").document().set(device);
//        }
//
//
//        for (int x = 0; x < 20; x++) {
//            Device device = new Device("dw2sgH0mVOqD5MEdfwia", true, "test_Building", x % 4, FieldValue.serverTimestamp(), "wuhan", "0", "low efficiency", 3, "timestampt2222222", true, x * 2, 0, "timeStamp2222", 21 * x, "timeStamp2222", "timeStamp2222", "22.3", 2.1f, 3.2f);
//            getFireStore().collection("building").document("dw2sgH0mVOqD5MEdfwia").collection("floor3").document().set(device);
//
//        }
//        for (int x = 0; x < 20; x++) {
//            Device device = new Device("dw2sgH0mVOqD5MEdfwia", true, "test_Building", x % 4, FieldValue.serverTimestamp(), "wuhan", "1", "low efficiency", 4, "timestampt2222222", true, x * 2, 0, "timeStamp2222", 21 * x, "timeStamp2222", "timeStamp2222", "22.3", 2.1f, 3.2f);
//            getFireStore().collection("building").document("dw2sgH0mVOqD5MEdfwia").collection("floor4").document().set(device);
//        }
//        for (int x = 0; x < 20; x++) {
//            Device device = new Device("dw2sgH0mVOqD5MEdfwia", true, "test_Building", x % 4, FieldValue.serverTimestamp(), "wuhan", "1", "low efficiency", 5, "timestampt2222222", true, x * 2, 0, "timeStamp2222", 21 * x, "timeStamp2222", "timeStamp2222", "22.3", 2.1f, 3.2f);
//            getFireStore().collection("building").document("dw2sgH0mVOqD5MEdfwia").collection("floor5").document().set(device);
//        }
//
//        for (int x = 0; x < 20; x++) {
//            Device device = new Device("dw2sgH0mVOqD5MEdfwia", true, "test_Building", x % 4, FieldValue.serverTimestamp(), "wuhan", "1", "low efficiency", 6, "timestampt2222222", true, x * 2, 0, "timeStamp2222", 21 * x, "timeStamp2222", "timeStamp2222", "22.3", 2.1f, 3.2f);
//            getFireStore().collection("building").document("dw2sgH0mVOqD5MEdfwia").collection("floor6").document().set(device);
//        }
    }
}
