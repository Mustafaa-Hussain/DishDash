package com.mustafa.dishdash.main.home.view.adabters;

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
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.mustafa.dishdash.R;
import com.mustafa.dishdash.main.data_layer.pojo.meals_short_details.MealShortDetails;
import com.mustafa.dishdash.main.data_layer.pojo.meals_short_details.MealsList;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder> {
    private MealsList mealsList;
    private Context context;
    private MealClickListener mealClickListener;

    public MealsAdapter(Context context, MealClickListener mealClickListener) {
        this.context = context;
        this.mealClickListener = mealClickListener;
    }

    public void setMealsList(MealsList mealsList) {
        this.mealsList = mealsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.meal_card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealsAdapter.ViewHolder holder, int position) {
        MealShortDetails meal = mealsList.getMeals().get(position);
        holder.mealTitle.setText(meal.getStrMeal());

        Glide.with(context)
                .load(meal.getStrMealThumb())
                .placeholder(R.drawable.ic_launcher_foreground)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.mealImage);

        holder.mealCard
                .setOnClickListener(view ->
                        mealClickListener.onItemClick(meal.getIdMeal()));
    }

    @Override
    public int getItemCount() {
        if (mealsList == null) return 0;
        return mealsList.getMeals().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView mealCard;
        ImageView mealImage;
        TextView mealTitle;

        public ViewHolder(View view) {
            super(view);
            mealCard = view.findViewById(R.id.meal_card);
            mealImage = view.findViewById(R.id.meal_image);
            mealTitle = view.findViewById(R.id.meal_title);
        }
    }
}
