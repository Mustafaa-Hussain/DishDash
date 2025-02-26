package com.mustafa.dishdash.main.Planes.view;

import com.mustafa.dishdash.main.data_layer.db.future_planes.entites.FuturePlane;

public interface FuturePlaneClickListener {
    void removePlaneClickListener(FuturePlane futurePlane);

    void itemClickListener(String mealId);
}
