package com.mustafa.dishdash.main.search.view.adapter;

import android.content.Context;
import android.text.Layout;
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

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<SearchItem> searchItemList;
    private Context context;
    private ItemClickListener clickListener;

    public SearchAdapter(Context context, ItemClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    public void setSearchItemList(List<SearchItem> searchItemList) {
        this.searchItemList = searchItemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemTitle.setText(searchItemList.get(position).getTitle());
        Glide.with(context)
                .load(searchItemList.get(position).getThumbnail())
                .error(R.drawable.broken_image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.itemImage);

        holder.searchItem.setOnClickListener(view ->
                clickListener.onItemClickListener(searchItemList.get(position).getId(),
                        searchItemList.get(position).getTitle()));
    }

    @Override
    public int getItemCount() {
        if (searchItemList == null) return 0;
        return searchItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView searchItem;
        ImageView itemImage;
        TextView itemTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            searchItem = itemView.findViewById(R.id.search_item);
            itemImage = itemView.findViewById(R.id.search_item_image);
            itemTitle = itemView.findViewById(R.id.search_item_title);
        }
    }
}
