package com.mustafa.dishdash.main.data_layer.firebase.future_plane.entities;

import java.util.List;

public class FuturePlaneList {
    private List<FuturePlaneEntity> futurePlaneEntityList;

    public void setFuturePlaneEntityList(List<FuturePlaneEntity> futurePlaneEntityList) {
        this.futurePlaneEntityList = futurePlaneEntityList;
    }

    public List<FuturePlaneEntity> getFuturePlaneEntityList() {
        return futurePlaneEntityList;
    }

    @Override
    public String toString() {
        return "FuturePlaneList{" +
                "futurePlaneEntityList=" + futurePlaneEntityList +
                '}';
    }
}
