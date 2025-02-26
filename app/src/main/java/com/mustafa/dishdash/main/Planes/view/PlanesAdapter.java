package com.mustafa.dishdash.main.Planes.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.mustafa.dishdash.R;
import com.mustafa.dishdash.main.data_layer.db.future_planes.entites.FuturePlane;

import java.util.List;

public class PlanesAdapter extends RecyclerView.Adapter<PlanesAdapter.ViewHolder> {
    private List<FuturePlane> futurePlanes;
    private Context context;
    private FuturePlaneClickListener clickListener;

    public PlanesAdapter(Context context, FuturePlaneClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    public void setFuturePlanes(List<FuturePlane> futurePlanes) {
        this.futurePlanes = futurePlanes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(context)
                .inflate(R.layout.future_plane_raw, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FuturePlane futurePlane = futurePlanes.get(position);
        String date = futurePlane.getDay() + "/" + futurePlane.getMonth() + "/" + futurePlane.getYear();
        holder.txtDate.setText(date);
        holder.mealTitle.setText(futurePlane.getStrMeal());
        holder.categoryChip.setText(futurePlane.getStrCategory());
        holder.countryChip.setText(futurePlane.getStrArea());

        Glide.with(context)
                .load(futurePlane.getStrMealThumb())
                .placeholder(R.drawable.broken_image)
                .into(holder.mealImage);

        holder.removeFromPlanes.
                setOnClickListener(v -> clickListener.removePlaneClickListener(futurePlane));

        holder.cardView.setOnClickListener(v -> clickListener.itemClickListener(futurePlane.getIdMeal()));
    }

    @Override
    public int getItemCount() {
        if (futurePlanes == null) return 0;
        return futurePlanes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDate;
        ImageView mealImage, removeFromPlanes;
        TextView mealTitle;
        Chip categoryChip, countryChip;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.future_plane_date);
            mealImage = itemView.findViewById(R.id.future_plane_meal_image);
            removeFromPlanes = itemView.findViewById(R.id.future_plane_delete);
            mealTitle = itemView.findViewById(R.id.future_plane_meal_title);
            categoryChip = itemView.findViewById(R.id.future_plane_category_chip);
            countryChip = itemView.findViewById(R.id.future_plane_country_chip);
            cardView = itemView.findViewById(R.id.future_plane_card_view);
        }
    }
}
