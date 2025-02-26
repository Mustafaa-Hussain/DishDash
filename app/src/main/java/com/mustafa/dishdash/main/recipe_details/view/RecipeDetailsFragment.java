package com.mustafa.dishdash.main.recipe_details.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_SHORT;
import static com.mustafa.dishdash.utils.Constants.ingredientImageUrl;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.mustafa.dishdash.R;
import com.mustafa.dishdash.auth.AuthenticationActivity;
import com.mustafa.dishdash.auth.data_layer.AuthRepository;
import com.mustafa.dishdash.auth.data_layer.firebase.UserRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.FavoriteMealsRepository;
import com.mustafa.dishdash.main.data_layer.FuturePlanesRepository;
import com.mustafa.dishdash.main.data_layer.MealsRepository;
import com.mustafa.dishdash.main.data_layer.db.favorites.FavoritesMealsLocalDatasource;
import com.mustafa.dishdash.main.data_layer.db.future_planes.FuturePlanesLocalDatasource;
import com.mustafa.dishdash.main.data_layer.firebase.favorite_meals.FavoritesRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.firebase.future_plane.FuturePlanesRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.network.MealsRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;
import com.mustafa.dishdash.main.data_layer.shared_prefs.TodayMealLocalDatasource;
import com.mustafa.dishdash.main.recipe_details.presenter.RecipeDetailsPresenter;
import com.mustafa.dishdash.utils.Constants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.Calendar;

public class RecipeDetailsFragment extends Fragment implements RecipeDetailsView {

    private TextView txtMealName, instructions;
    private ChipGroup mealCategories;
    private ImageView addToCalender, toggleFavorite, mealImage;

    private LinearLayout linearLayout;
    private YouTubePlayerView youTubePlayerView;
    private RecipeDetailsPresenter presenter;
    private CardView notLoggedInBanner;
    private CardView mealCard;
    private TextView dismiss, login;
    private Animation openBanner, closeBanner, moveUp;
    private boolean isFavorite = false;
    private final long WEEK = 7 * 24 * 60 * 60 * 1000;

    public RecipeDetailsFragment() {
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

        String mealId = RecipeDetailsFragmentArgs.fromBundle(getArguments()).getId();

        presenter = new RecipeDetailsPresenter(this,
                MealsRepository.getInstance(new MealsRemoteDatasource(),
                        new TodayMealLocalDatasource(getContext()),
                        new FavoritesMealsLocalDatasource(getContext())),
                FavoriteMealsRepository.getInstance(new FavoritesMealsLocalDatasource(getContext())
                        , new FavoritesRemoteDatasource()),
                FuturePlanesRepository.getInstance(new FuturePlanesLocalDatasource(getContext()),
                        new FuturePlanesRemoteDatasource()),
                AuthRepository.getInstance(new UserRemoteDatasource(getActivity())));


        presenter.getMealById(mealId);
    }

    private void setupUI(View view) {
        txtMealName = view.findViewById(R.id.txtMealName);
        instructions = view.findViewById(R.id.meal_instructions);
        mealCategories = view.findViewById(R.id.meal_tags);
        addToCalender = view.findViewById(R.id.add_to_calender);
        toggleFavorite = view.findViewById(R.id.add_to_favorite);
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
        presenter.close();
        getActivity().findViewById(R.id.bottomNavigationView).setVisibility(VISIBLE);
        super.onStop();
    }

    @Override
    public void onGetMealDetailsSuccess(MealsItem mealsItem, Boolean isFavorite) {
        displayMealDetails(mealsItem, isFavorite);
    }

    @Override
    public void onGetMealDetailsFail(String errorMessage) {
        if (getContext() != null)
            Toast.makeText(getContext(), getString(R.string.you_are_not_connected), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddedToFavoritesSuccess() {
        toggleFavorite.setImageResource(R.drawable.remove_from_favorite);
        isFavorite = true;
    }

    @Override
    public void onAddedToFavoritesFail() {
        if (getContext() != null)
            Toast.makeText(getContext(), getString(R.string.adding_to_favorite_error), LENGTH_SHORT).show();
    }

    @Override
    public void onRemoveFavoriteSuccess() {
        toggleFavorite.setImageResource(R.drawable.add_to_favorite);
        isFavorite = false;
    }

    @Override
    public void onRemoveFavoriteFail(String errorMsg) {
        if (getContext() != null)
            Toast.makeText(getContext(), getString(R.string.failed_to_remove_favorite_meal), LENGTH_SHORT).show();
    }

    @Override
    public void onSyncSuccess() {
        if (getContext() != null) {
            Toast.makeText(getContext(), R.string.data_synced, LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSyncDataFailed() {
        if (getContext() != null) {
            Toast.makeText(getContext(), R.string.failed_to_sync_data, LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAddedToFuturePlanesSuccess() {
        if (getContext() != null)
            Toast.makeText(getContext(), R.string.added_to_future_planes, LENGTH_SHORT).show();
    }

    @Override
    public void onAddedToFuturePlanesFail() {
        if (getContext() != null)
            Toast.makeText(getContext(), R.string.already_exists_in_this_day, LENGTH_SHORT).show();
    }


    @Override
    public void userNotLoggedIn() {
        //banner
        notLoggedInBanner.setAnimation(openBanner);
        notLoggedInBanner.setVisibility(VISIBLE);
        Toast.makeText(getContext(), getString(R.string.you_are_not_logged_in), LENGTH_SHORT).show();
    }


    private void displayMealDetails(MealsItem mealItem, Boolean isFavorite) {
        this.isFavorite = isFavorite;

        txtMealName.setText(mealItem.getStrMeal());
        instructions.setText(mealItem.getStrInstructions());

        toggleFavorite.setImageResource(isFavorite ? R.drawable.remove_from_favorite : R.drawable.add_to_favorite);

        mealCategories.removeAllViews();
        addChipToGroup(mealItem.getStrCategory());
        addChipToGroup(mealItem.getStrArea());

        loadAndPlaceImage(mealItem.getStrMealThumb(), mealImage);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);

        addToCalender.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    R.style.DialogTheme,
                    (datePicker, year1, month_index, day1) ->
                            presenter.addMealToFuturePlane(mealItem, day1, month_index + 1, year1),
                    year, month, day);
            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
            datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis() + WEEK);

            datePickerDialog.show();
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(getContext(), R.color.primary));
        });

        toggleFavorite.setOnClickListener(view -> {
            if (!this.isFavorite) {
                presenter.addMealToFavorites(mealItem);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.are_sure_you_want_to_remove_this_meal_from_favorites)
                        .setPositiveButton(R.string.remove, (dialogInterface, i) -> {
                            presenter.removeFavoriteMeal(mealItem);
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

    private void addIngredient(String ingredientTitle, String ingredientMeasurement) {
        if (ingredientTitle != null && !ingredientTitle.trim().isEmpty()) {
            View child = getLayoutInflater().inflate(R.layout.ingredients_card, null);
            ImageView ingImage = child.findViewById(R.id.ingredient_image);
            TextView ingTitle = child.findViewById(R.id.ingredient_title);
            TextView ingMeasurement = child.findViewById(R.id.ingredient_measurement);

            ingTitle.setText(ingredientTitle);
            ingMeasurement.setText(ingredientMeasurement);

            loadAndPlaceImage(ingredientImageUrl + ingredientTitle + Constants.SMALL_IMAGE_EXTENSION, ingImage);

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


