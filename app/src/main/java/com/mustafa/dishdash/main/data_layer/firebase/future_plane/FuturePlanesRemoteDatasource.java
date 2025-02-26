package com.mustafa.dishdash.main.data_layer.firebase.future_plane;


import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mustafa.dishdash.main.data_layer.firebase.future_plane.entities.FuturePlaneEntity;
import com.mustafa.dishdash.main.data_layer.firebase.future_plane.entities.FuturePlaneList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuturePlanesRemoteDatasource {
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    public static final String USER_NOT_LOGGED_IN = "user_not_logged_in";
    private final String FUTURE_PLANES = "future_planes";
    private final String FUTURE_PLANES_COLLECTION_NAME = "future_planes";

    public FuturePlanesRemoteDatasource() {
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }


    public void getFuturePlanes(GetRemoteFuturePlanesCallBack callBack) {
        if (auth.getCurrentUser() != null) {
            String userId = auth.getCurrentUser().getUid();
            DocumentReference documentReference = firestore
                    .collection(FUTURE_PLANES_COLLECTION_NAME)
                    .document(userId);

            documentReference.get()
                    .addOnSuccessListener(
                            documentSnapshot -> {
                                callBack.getAllFuturePlanesRemoteOnSuccess(
                                        convirteResponseToFuturePlaneList(
                                                (List<Map<String, Object>>) documentSnapshot
                                                        .get(FUTURE_PLANES)));
                            })
                    .addOnFailureListener(e -> callBack.
                            getAllFuturePlanesRemoteOnFail(e.getMessage()));
        } else {
            callBack.getAllFuturePlanesRemoteOnFail(USER_NOT_LOGGED_IN);
        }
    }

    private List<FuturePlaneEntity> convirteResponseToFuturePlaneList(List<Map<String, Object>> future_plane) {
        List<FuturePlaneEntity> futurePlaneEntityList = new ArrayList<>();
        if (future_plane != null) {
            for (Map<String, Object> plane :
                    future_plane) {
                futurePlaneEntityList.add(new FuturePlaneEntity((String) plane.get("mealId"),
                        (long) plane.get("day"),
                        (long) plane.get("month"),
                        (long) plane.get("year")));
            }
        }
        return futurePlaneEntityList;
    }


    public void uploadFuturePlanes(UploadFuturePlanesCallBack callBack,
                                   List<FuturePlaneEntity> futurePlanes) {
        if (auth.getCurrentUser() != null) {
            String userId = auth.getCurrentUser().getUid();

            Map<String, List<FuturePlaneEntity>> future_planes = new HashMap<>();

            future_planes.put(FUTURE_PLANES, futurePlanes);

            DocumentReference documentReference = firestore
                    .collection(FUTURE_PLANES_COLLECTION_NAME)
                    .document(userId);

            documentReference.set(future_planes)
                    .addOnSuccessListener(unused -> callBack.onUploadFuturePlanesRemoteOnSuccess())
                    .addOnFailureListener(e -> callBack.onUploadFuturePlanesRemoteOnFail(e.getMessage()));

        } else {
            callBack.onUploadFuturePlanesRemoteOnFail(USER_NOT_LOGGED_IN);
        }
    }
}
