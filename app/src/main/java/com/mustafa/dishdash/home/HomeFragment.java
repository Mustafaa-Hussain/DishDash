package com.mustafa.dishdash.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.mustafa.dishdash.R;
import com.mustafa.dishdash.adabters.MealsAdapter;
import com.mustafa.dishdash.retrofit.ApiService;
import com.mustafa.dishdash.retrofit.models.meals_short_details.MealsList;
import com.mustafa.dishdash.retrofit.models.random_meal.MealsItem;
import com.mustafa.dishdash.retrofit.models.random_meal.RandomMeal;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private static final String DAY_RANDOM_MEAL = "random_meal_of_the_day";
    private static final String MEAL_ID = "meal_id";
    private static final String DAY = "day";
    private CardView mealCard;
    private TextView username;
    private ImageView addToCalender;
    private ImageView addToFavorites;
    private ImageView randomMealImage;
    private TextView randomMealTitle;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mealsRecyclerView;

    private ApiService apiService;
    private String api_url;
    private String randomMealId;

    public HomeFragment() {
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

        mealCard = view.findViewById(R.id.random_meal_card);
        username = view.findViewById(R.id.username);
        addToCalender = view.findViewById(R.id.add_to_calender);
        addToFavorites = view.findViewById(R.id.add_to_favorite);
        randomMealImage = view.findViewById(R.id.random_meal_image);
        randomMealTitle = view.findViewById(R.id.random_meal_title);
        swipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        mealsRecyclerView = view.findViewById(R.id.meals_recycler_view);


        if (api_url.isEmpty()) {
            Toast.makeText(getContext(), "Wrong api url!", Toast.LENGTH_SHORT).show();
            return;
        }


        addToCalender.setOnClickListener(v -> {
            Toast.makeText(getContext(), "add to calender", Toast.LENGTH_SHORT).show();
        });

        addToFavorites.setOnClickListener(v -> {
            Toast.makeText(getContext(), "add to favorite", Toast.LENGTH_SHORT).show();
        });

        mealCard.setOnClickListener(v -> {
            if (randomMealId != null && !randomMealId.isEmpty()) {
                HomeFragmentDirections.ActionHomeFragmentToRecipeDetailsFragment action = HomeFragmentDirections
                        .actionHomeFragmentToRecipeDetailsFragment(randomMealId);
                Navigation.findNavController(v).navigate(action);

                Toast.makeText(getContext(), "go to details screen", Toast.LENGTH_SHORT).show();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        SharedPreferences savedRandomMeal = getContext().getSharedPreferences(DAY_RANDOM_MEAL, MODE_PRIVATE);
        int day = savedRandomMeal.getInt(DAY, -1);

        if (savedRandomMeal.getInt(DAY, -1) == -1 || day != Calendar.getInstance().get(Calendar.DATE)) {
            getRandomMeal(apiService);
        } else {
            getMealById(savedRandomMeal.getString(MEAL_ID, ""), apiService);
        }
        getAllMeals(apiService);


        swipeRefreshLayout.setOnRefreshListener(() -> {
            int d = savedRandomMeal.getInt(DAY, -1);
            if (d == -1 || d != Calendar.getInstance().get(Calendar.DATE)) {
                getRandomMeal(apiService);
            } else {
                getMealById(savedRandomMeal.getString(MEAL_ID, ""), apiService);
            }
            getAllMeals(apiService);
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void getMealById(String id, ApiService apiService) {
        Call<RandomMeal> mealCall = apiService.getMealById(id);

        mealCall.enqueue(new Callback<RandomMeal>() {
            @Override
            public void onResponse(Call<RandomMeal> call, Response<RandomMeal> response) {
                if (response.isSuccessful()) {
                    displayRandomMeal(response.body().getMeals().get(0));
                }
            }

            @Override
            public void onFailure(Call<RandomMeal> call, Throwable throwable) {
                Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllMeals(ApiService apiService) {
        Call<MealsList> callMeals = apiService.getAllMealsByIngredientName("");
        callMeals.enqueue(new Callback<MealsList>() {
            @Override
            public void onResponse(Call<MealsList> call, Response<MealsList> response) {
                if (response.isSuccessful()) {
                    MealsAdapter adapter = new MealsAdapter(getContext(), response.body());
                    mealsRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<MealsList> call, Throwable throwable) {
                Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getRandomMeal(ApiService apiService) {
        Call<RandomMeal> callRandomMeal = apiService.getRandomMeal();

        callRandomMeal.enqueue(new Callback<RandomMeal>() {
            @Override
            public void onResponse(Call<RandomMeal> call, Response<RandomMeal> response) {
                if (response.isSuccessful() && !response.body().getMeals().isEmpty()) {
                    displayRandomMeal(response.body().getMeals().get(0));

                    randomMealId = response.body().getMeals().get(0).getIdMeal();

                    SharedPreferences todayMeal = getContext().getSharedPreferences(DAY_RANDOM_MEAL, MODE_PRIVATE);
                    SharedPreferences.Editor editor = todayMeal.edit();
                    editor.putString(MEAL_ID, randomMealId);
                    editor.putInt(DAY, Calendar.getInstance().get(Calendar.DATE));
                    editor.commit();
                }
            }

            @Override
            public void onFailure(Call<RandomMeal> call, Throwable throwable) {
                Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayRandomMeal(MealsItem meal) {
        randomMealTitle.setText(meal.getStrMeal());
        if (getContext() != null) {
            Glide.with(getContext())
                    .load(meal.getStrMealThumb())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(randomMealImage);
        }
    }
}