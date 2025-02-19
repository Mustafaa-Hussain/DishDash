package com.mustafa.dishdash.main.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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
import com.google.android.material.snackbar.Snackbar;
import com.mustafa.dishdash.R;
import com.mustafa.dishdash.main.home.data_layer.MealsRepository;
import com.mustafa.dishdash.main.home.data_layer.network.MealsRemoteDatasource;
import com.mustafa.dishdash.main.home.data_layer.shared_prefs.TodayMealLocalDatasource;
import com.mustafa.dishdash.main.home.presenter.HomePresenter;
import com.mustafa.dishdash.main.home.data_layer.network.models.meals_short_details.MealsList;
import com.mustafa.dishdash.main.home.data_layer.network.models.random_meal.MealsItem;
import com.mustafa.dishdash.main.home.view.adabters.MealClickListener;
import com.mustafa.dishdash.main.home.view.adabters.MealsAdapter;


public class HomeFragment extends Fragment implements HomeView, MealClickListener {

    private CardView mealCard;
    private TextView username;
    private ImageView addToCalender;
    private ImageView addToFavorites;
    private ImageView randomMealImage;
    private TextView randomMealTitle;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mealsRecyclerView;
    private MealsAdapter adapter;

    private String randomMealId;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupUI(view);
        setupClickListener();


        HomePresenter presenter = new HomePresenter(this,
                MealsRepository.getInstance(new MealsRemoteDatasource(getContext()),
                        new TodayMealLocalDatasource(getContext())));

        swipeRefreshLayout.setRefreshing(true);

        presenter.getRandomMeal();
        presenter.getAllMeals();

        adapter = new MealsAdapter(getContext(), this);
        mealsRecyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.getRandomMeal();
            presenter.getAllMeals();
        });
    }

    private void setupUI(View view) {

        mealCard = view.findViewById(R.id.random_meal_card);
        username = view.findViewById(R.id.username);
        addToCalender = view.findViewById(R.id.add_to_calender);
        addToFavorites = view.findViewById(R.id.add_to_favorite);
        randomMealImage = view.findViewById(R.id.random_meal_image);
        randomMealTitle = view.findViewById(R.id.random_meal_title);
        swipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        mealsRecyclerView = view.findViewById(R.id.meals_recycler_view);
    }

    private void setupClickListener() {
        addToCalender.setOnClickListener(v -> {
            onClickAddToCalender("id");
        });

        addToFavorites.setOnClickListener(v -> {
            onClickAddToFavorites("id");
        });

        //go to details screen
        mealCard.setOnClickListener(v -> {
            if (randomMealId != null && !randomMealId.isEmpty()) {
                gotoDetailsPage(randomMealId);
            }
        });
    }

    private void gotoDetailsPage(String id) {
        HomeFragmentDirections.ActionHomeFragmentToRecipeDetailsFragment action = HomeFragmentDirections
                .actionHomeFragmentToRecipeDetailsFragment(id);
        Navigation.findNavController(this.getView()).navigate(action);
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


    @Override
    public void randomMealResultSuccess(MealsItem meal) {
        displayRandomMeal(meal);
        randomMealId = meal.getIdMeal();
    }

    @Override
    public void randomMealResultFail(String errorMessage) {
        Snackbar.make(mealCard, errorMessage, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void allMealsResultSuccess(MealsList meal) {
        adapter.setMealsList(meal);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void allMealsResultFail(String errorMessage) {
        Snackbar.make(mealCard, errorMessage, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void mealByIdResultSuccess(MealsItem meal) {
        displayRandomMeal(meal);
        randomMealId = meal.getIdMeal();
    }

    @Override
    public void mealByIdResultFail(String errorMessage) {
        Snackbar.make(mealCard, errorMessage, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(String id) {
        gotoDetailsPage(id);
    }

    @Override
    public void onClickAddToFavorites(String id) {
        Toast.makeText(getContext(), "add to favorites", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickAddToCalender(String id) {
        Toast.makeText(getContext(), "add to calender", Toast.LENGTH_SHORT).show();
    }
}