package com.mustafa.dishdash.main.home.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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
import com.google.firebase.auth.FirebaseAuth;
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview;
import com.mustafa.dishdash.R;
import com.mustafa.dishdash.auth.AuthenticationActivity;
import com.mustafa.dishdash.auth.data_layer.AuthRepository;
import com.mustafa.dishdash.auth.data_layer.firebase.UserRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.MealsRepository;
import com.mustafa.dishdash.main.data_layer.db.favorites.FavoritesMealsLocalDatasource;
import com.mustafa.dishdash.main.data_layer.network.MealsRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.shared_prefs.TodayMealLocalDatasource;
import com.mustafa.dishdash.main.home.presenter.HomePresenter;
import com.mustafa.dishdash.main.data_layer.pojo.meals_short_details.MealsList;
import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;
import com.mustafa.dishdash.main.home.view.adabters.MealClickListener;
import com.mustafa.dishdash.main.home.view.adabters.MealsAdapter;


public class HomeFragment extends Fragment implements HomeView, MealClickListener {

    private CardView mealCard;
    private TextView username;
    private TextView login;
    private ImageView randomMealImage;
    private TextView randomMealTitle;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CarouselRecyclerview mealsRecyclerView;
    private MealsAdapter adapter;

    private String randomMealId;
    private HomePresenter presenter;
    private ConstraintLayout homeView;
    private View noInternetConnectionGroup;

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

        presenter = new HomePresenter(this,
                MealsRepository.getInstance(new MealsRemoteDatasource(),
                        new TodayMealLocalDatasource(getContext()),
                        new FavoritesMealsLocalDatasource(getContext())),
                AuthRepository.getInstance(new UserRemoteDatasource(getActivity())));

        swipeRefreshLayout.setRefreshing(true);
        presenter.getRandomMeal();
        presenter.getAllMeals();

        adapter = new MealsAdapter(getContext(), this);
        mealsRecyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.getRandomMeal();
            presenter.getAllMeals();
            presenter.getRegisteredUser();
        });
    }

    @Override
    public void onResume() {
        presenter.getRegisteredUser();
        super.onResume();
    }

    @Override
    public void onStop() {
        presenter.close();
        super.onStop();
    }

    private void setupUI(View view) {
        mealCard = view.findViewById(R.id.random_meal_card);
        username = view.findViewById(R.id.username);
        login = view.findViewById(R.id.login);
        randomMealImage = view.findViewById(R.id.random_meal_image);
        randomMealTitle = view.findViewById(R.id.random_meal_title);
        swipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        mealsRecyclerView = view.findViewById(R.id.meals_recycler_view);
        homeView = view.findViewById(R.id.home_view);
        noInternetConnectionGroup = view.findViewById(R.id.no_internet_connection_group);
    }

    private void setupClickListener() {
        //go to details screen
        mealCard.setOnClickListener(v -> {
            if (randomMealId != null && !randomMealId.isEmpty()) {
                gotoDetailsPage(randomMealId);
            }
        });

        login.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), AuthenticationActivity.class));
        });
    }

    private void gotoDetailsPage(String id) {
        if (this.getView() != null) {
            HomeFragmentDirections.ActionHomeFragmentToRecipeDetailsFragment action = HomeFragmentDirections
                    .actionHomeFragmentToRecipeDetailsFragment(id);
            Navigation.findNavController(this.getView()).navigate(action);
        }
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
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void randomMealResultFail(String errorMessage) {
        Snackbar.make(mealCard, R.string.you_are_not_connected, Snackbar.LENGTH_SHORT).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void registeredUser(String username) {
        this.username.setText(username);
        this.login.setVisibility(GONE);
    }

    @Override
    public void userNotLoggedIn() {
        //user not logged in
    }

    @Override
    public void allMealsResultSuccess(MealsList meal) {
        hideNoInternetConnectionMessage();
        swipeRefreshLayout.setRefreshing(false);

        adapter.setMealsList(meal);
        mealsRecyclerView.set3DItem(true);
        mealsRecyclerView.setAlpha(true);
        mealsRecyclerView.setInfinite(true);
    }

    @Override
    public void allMealsResultFail(String errorMessage) {
        swipeRefreshLayout.setRefreshing(false);
        showNoInternetConnectionMessage();
    }

    private void showNoInternetConnectionMessage() {
        homeView.setVisibility(GONE);
        noInternetConnectionGroup.setVisibility(VISIBLE);
    }

    private void hideNoInternetConnectionMessage() {
        homeView.setVisibility(VISIBLE);
        noInternetConnectionGroup.setVisibility(GONE);
    }

    @Override
    public void mealByIdResultSuccess(MealsItem meal) {
        displayRandomMeal(meal);
        randomMealId = meal.getIdMeal();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void mealByIdResultFail(String errorMessage) {
        Snackbar.make(mealCard, R.string.you_are_not_connected, Snackbar.LENGTH_SHORT).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(String id) {
        gotoDetailsPage(id);
    }
}