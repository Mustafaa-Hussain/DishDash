package com.mustafa.dishdash.adabters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.mustafa.dishdash.R;
import com.mustafa.dishdash.retrofit.models.meals_short_details.MealShortDetails;
import com.mustafa.dishdash.retrofit.models.meals_short_details.MealsList;

import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder> {
    MealsList mealsList;
    Context context;
    public MealsAdapter(Context context, MealsList mealsList) {
        this.context = context;
        this.mealsList = mealsList;
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

        holder.addToCalender.setOnClickListener(view -> {
            Toast.makeText(context, "add to calender", Toast.LENGTH_SHORT).show();
        });

        holder.addToFavorite.setOnClickListener(view -> {
            Toast.makeText(context, "add to favorite", Toast.LENGTH_SHORT).show();
        });

        holder.mealCard.setOnClickListener(view -> {
            Toast.makeText(context, "go to details screen", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return mealsList.getMeals().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView mealCard;
        ImageView addToCalender;
        ImageView addToFavorite;
        ImageView mealImage;
        TextView mealTitle;

        public ViewHolder(View view) {
            super(view);
            mealCard = view.findViewById(R.id.meal_card);
            addToCalender = view.findViewById(R.id.add_to_calender);
            addToFavorite = view.findViewById(R.id.add_to_favorite);
            mealImage = view.findViewById(R.id.meal_image);
            mealTitle = view.findViewById(R.id.meal_title);
        }
    }
}
