package com.mustafa.dishdash.main.recipe_details.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_SHORT;
import static com.mustafa.dishdash.utils.Constants.ingredientImageUrl;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.mustafa.dishdash.R;
import com.mustafa.dishdash.auth.AuthenticationActivity;
import com.mustafa.dishdash.auth.data_layer.AuthRepository;
import com.mustafa.dishdash.auth.data_layer.firebase.UserRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.MealsRepository;
import com.mustafa.dishdash.main.data_layer.db.FavoritesMealsLocalDatasource;
import com.mustafa.dishdash.main.data_layer.db.entities.FavoriteMeal;
import com.mustafa.dishdash.main.data_layer.network.MealsRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;
import com.mustafa.dishdash.main.data_layer.shared_prefs.TodayMealLocalDatasource;
import com.mustafa.dishdash.main.recipe_details.presenter.RecipeDetailsPresenter;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class RecipeDetailsFragment extends Fragment implements RecipeDetailsView {

    private TextView txtMealName, instructions;
    private ChipGroup mealCategories;
    private ImageView addToCalender, togleFavorite, mealImage;

    private LinearLayout linearLayout;
    private YouTubePlayerView youTubePlayerView;
    private RecipeDetailsPresenter presenter;
    private CardView notLoggedInBanner;
    private CardView mealCard;
    private TextView dismiss, login;
    private Animation openBanner, closeBanner, moveUp;
    private @NonNull String mealId;
    private boolean displayed = false;
    private boolean isFavorite = false;

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupUI(view);

        getLifecycle().addObserver(youTubePlayerView);

        mealId = RecipeDetailsFragmentArgs.fromBundle(getArguments()).getId();

        presenter = new RecipeDetailsPresenter(this,
                MealsRepository.getInstance(new MealsRemoteDatasource(),
                        new TodayMealLocalDatasource(getContext()),
                        new FavoritesMealsLocalDatasource(getContext())),
                AuthRepository.getInstance(new UserRemoteDatasource(getActivity())));


        presenter.getMealById(mealId);

        presenter.getFavoriteMealById(mealId);
    }

    private void setupUI(View view) {
        txtMealName = view.findViewById(R.id.txtMealName);
        instructions = view.findViewById(R.id.meal_instructions);
        mealCategories = view.findViewById(R.id.meal_tags);
        addToCalender = view.findViewById(R.id.add_to_calender);
        togleFavorite = view.findViewById(R.id.add_to_favorite);
        mealImage = view.findViewById(R.id.instructions_image);
        linearLayout = view.findViewById(R.id.ingredients_container);
        youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        mealCard = view.findViewById(R.id.meal_card);

        //not logged in banner
        notLoggedInBanner = view.findViewById(R.id.not_logged_in_banner);
        dismiss = view.findViewById(R.id.dismiss);
        login = view.findViewById(R.id.login);
        setBannerClickListeners();
        setupBannerAnimation();
    }

    private void setupBannerAnimation() {
        openBanner = AnimationUtils.loadAnimation(getContext(), R.anim.banner_open);
        closeBanner = AnimationUtils.loadAnimation(getContext(), R.anim.banner_close);
        moveUp = AnimationUtils.loadAnimation(getContext(), R.anim.under_banner_move_up);
    }

    private void setBannerClickListeners() {

        dismiss.setOnClickListener(view -> {
            notLoggedInBanner.setAnimation(closeBanner);
            mealCard.setAnimation(moveUp);
            notLoggedInBanner.setVisibility(GONE);

        });
        login.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), AuthenticationActivity.class));

            notLoggedInBanner.setVisibility(GONE);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().findViewById(R.id.bottomNavigationView).setVisibility(GONE);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().findViewById(R.id.bottomNavigationView).setVisibility(VISIBLE);
    }

    @Override
    public void recipeDetailsResponseSuccess(MealsItem mealsItem) {
        displayMealDetails(mealsItem);
    }

    @Override
    public void recipeDetailsResponseFail(String errorMessage) {
        displayed = false;
        Snackbar.make(this.getView(), getString(R.string.you_are_not_connected), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void addedToFavorites() {
        Toast.makeText(getContext(), getString(R.string.added_to_favorites), LENGTH_SHORT).show();
    }

    @Override
    public void userNotLoggedIn() {
        //banner
        notLoggedInBanner.setAnimation(openBanner);
        notLoggedInBanner.setVisibility(VISIBLE);
    }

    @Override
    public void favoriteMeal(LiveData<FavoriteMeal> favoriteMealById) {
        if (getActivity() != null) {
            favoriteMealById.observe(getActivity(), new Observer<FavoriteMeal>() {
                @Override
                public void onChanged(FavoriteMeal favoriteMeal) {
                    if (favoriteMeal != null) {
                        togleFavorite.setImageResource(R.drawable.remove_from_favorite);
                        isFavorite = true;
                        if (!displayed) {
                            displayMealDetails(favoriteMeal);
                        }
                    } else {
                        togleFavorite.setImageResource(R.drawable.add_to_favorite);
                        isFavorite = false;
                    }
                }
            });
        }
    }


    private void displayMealDetails(MealsItem mealItem) {
        if (getContext() != null) {
            displayed = true;
            txtMealName.setText(mealItem.getStrMeal());
            instructions.setText(mealItem.getStrInstructions());

            mealCategories.removeAllViews();
            addChipToGroup(mealItem.getStrCategory());
            addChipToGroup(mealItem.getStrArea());

            loadAndPlaceImage(mealItem.getStrMealThumb(), mealImage);

            addToCalender.setOnClickListener(view -> {
                Toast.makeText(getContext(), "add to calender", LENGTH_SHORT).show();
            });
            togleFavorite.setOnClickListener(view -> {
                if (!isFavorite) {
                    addMealToFavorites(mealItem);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.are_sure_you_want_to_remove_this_meal_from_favorites)
                            .setPositiveButton(R.string.remove, (dialogInterface, i) -> {
                                removeFavoriteMeal(mealItem);
                            })
                            .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
                                dialogInterface.dismiss();
                            });
                    builder.create().show();
                }
            });

            mealItem.getingredients().forEach((item) -> {
                addIngredient(item.first, item.second);
            });


            if (!mealItem.getStrYoutube().isEmpty()) {
                youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        youTubePlayer.cueVideo(presenter.getYoutubeVideo(mealItem.getStrYoutube()), 0);
                    }
                });
            } else {
                youTubePlayerView.setVisibility(GONE);
            }
        }
    }

    private void addMealToFavorites(MealsItem mealItem) {
        presenter.addMealToFavorites(mealItem);
    }

    private void removeFavoriteMeal(MealsItem mealsItem) {
        presenter.removeFavoriteMeal(mealsItem);
    }

    private void addIngredient(String ingredientTitle, String ingredientMeasurement) {
        if (getContext() != null && ingredientTitle != null && !ingredientTitle.trim().isEmpty()) {
            View child = getLayoutInflater().inflate(R.layout.ingredients_card, null);
            ImageView ingImage = child.findViewById(R.id.ingredient_image);
            TextView ingTitle = child.findViewById(R.id.ingredient_title);
            TextView ingMeasurement = child.findViewById(R.id.ingredient_measurement);

            ingTitle.setText(ingredientTitle);
            ingMeasurement.setText(ingredientMeasurement);

            loadAndPlaceImage(ingredientImageUrl + ingredientTitle + "-Small.png", ingImage);

            linearLayout.addView(child);
        }
    }

    private void loadAndPlaceImage(String url, ImageView view) {
        if (getContext() != null) {
            Glide.with(getContext())
                    .load(url)
                    .placeholder(R.drawable.broken_image)
                    .into(view);
        }
    }

    private void addChipToGroup(String text) {
        Chip chip = new Chip(getContext());
        chip.setText(text);
        mealCategories.addView(chip);
    }

}