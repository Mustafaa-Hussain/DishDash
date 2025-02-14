package com.mustafa.dishdash;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.mustafa.dishdash.retrofit.ApiService;
import com.mustafa.dishdash.retrofit.models.random_meal.MealsItem;
import com.mustafa.dishdash.retrofit.models.random_meal.RandomMeal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends Fragment {

    private TextView username;
    private ImageView addToCalender;
    private ImageView addToFavorites;
    private ImageView randomMealImage;
    private TextView randomMealTitle;

    private String api_url;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api_url = getContext().getSharedPreferences("api_url", getContext().MODE_PRIVATE).getString("url", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        username = view.findViewById(R.id.username);
        addToCalender = view.findViewById(R.id.add_to_calender);
        addToFavorites = view.findViewById(R.id.add_to_favorite);
        randomMealImage = view.findViewById(R.id.random_meal_image);
        randomMealTitle = view.findViewById(R.id.random_meal_title);

        if (api_url.isEmpty()) {
            Toast.makeText(getContext(), "Wrong api url!", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<RandomMeal> callRandomMeal = apiService.getRandomMeal();

        callRandomMeal.enqueue(new Callback<RandomMeal>() {
            @Override
            public void onResponse(Call<RandomMeal> call, Response<RandomMeal> response) {
                if (response.isSuccessful() && !response.body().getMeals().isEmpty()) {
                    MealsItem meal = response.body().getMeals().getFirst();
                    randomMealTitle.setText(meal.getStrMeal());

                    Glide.with(view)
                            .load(meal.getStrMealThumb())
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .into(randomMealImage);
                }
            }

            @Override
            public void onFailure(Call<RandomMeal> call, Throwable throwable) {
                Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}