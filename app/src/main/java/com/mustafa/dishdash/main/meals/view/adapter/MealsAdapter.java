package com.mustafa.dishdash.main.meals.view.adapter;

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
import com.mustafa.dishdash.main.meals.data_layer.model.MealsItem;

import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder> {

    private List<MealsItem> mealsItems;
    private Context context;
    private ItemClickListener clickListener;

    public MealsAdapter(Context context, ItemClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    public void mealsItems(List<MealsItem> mealsItemList) {
        this.mealsItems = mealsItemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.meal_card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemTitle.setText(mealsItems.get(position).getStrMeal());
        Glide.with(context)
                .load(mealsItems.get(position).getStrMealThumb())
                .error(R.drawable.broken_image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.itemImage);

        holder.mealItem.setOnClickListener(view ->
                clickListener.onItemClickListener(mealsItems.get(position).getIdMeal(),
                        mealsItems.get(position).getStrMealThumb()));
    }

    @Override
    public int getItemCount() {
        if (mealsItems == null) return 0;
        return mealsItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView mealItem;
        ImageView itemImage;
        TextView itemTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealItem = itemView.findViewById(R.id.meal_card);
            itemImage = itemView.findViewById(R.id.meal_image);
            itemTitle = itemView.findViewById(R.id.meal_title);
        }
    }
}
