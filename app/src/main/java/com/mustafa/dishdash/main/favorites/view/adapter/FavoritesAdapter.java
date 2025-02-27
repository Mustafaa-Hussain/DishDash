package com.mustafa.dishdash.main.favorites.view.adapter;

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
import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private List<MealsItem> mealsList;
    private Context context;
    private FavoriteItemClickListener clickListener;

    public FavoritesAdapter(Context context, FavoriteItemClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    public void setMealsList(List<MealsItem> mealsList) {
        this.mealsList = mealsList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favorite_meal_raw, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mealTitle.setText(mealsList.get(position).getStrMeal());
        holder.categoryChip.setText(mealsList.get(position).getStrCategory());
        holder.countryChip.setText(mealsList.get(position).getStrArea());

        Glide.with(context)
                .load(mealsList.get(position).getStrMealThumb())
                .placeholder(R.drawable.broken_image)
                .into(holder.mealImage);

        holder.toggleFavorite.
                setOnClickListener(v -> clickListener.toggleFavoriteClickListener(mealsList.get(position)));

        holder.cardView.setOnClickListener(v -> clickListener.itemClickListener(mealsList.get(position)));
    }

    @Override
    public int getItemCount() {
        if (mealsList == null) return 0;
        return mealsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mealImage, toggleFavorite;
        TextView mealTitle;
        Chip categoryChip, countryChip;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.favorite_meal_image);
            toggleFavorite = itemView.findViewById(R.id.favorite_toggle);
            mealTitle = itemView.findViewById(R.id.favorite_meal_title);
            categoryChip = itemView.findViewById(R.id.favorite_category_chip);
            countryChip = itemView.findViewById(R.id.favorite_country_chip);
            cardView = itemView.findViewById(R.id.favorite_card_view);
        }
    }
}
