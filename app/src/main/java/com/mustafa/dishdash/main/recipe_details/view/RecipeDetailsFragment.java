package com.mustafa.dishdash.main.recipe_details.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_SHORT;
import static com.mustafa.dishdash.utils.Constant.API_URL;
import static com.mustafa.dishdash.utils.Constant.ingredientImageUrl;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.mustafa.dishdash.main.data_layer.MealsRepository;
import com.mustafa.dishdash.main.data_layer.network.ApiHomeService;
import com.mustafa.dishdash.main.data_layer.network.MealsRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.network.pojo.random_meal.Meal;
import com.mustafa.dishdash.main.data_layer.network.pojo.random_meal.MealsItem;
import com.mustafa.dishdash.main.data_layer.shared_prefs.TodayMealLocalDatasource;
import com.mustafa.dishdash.main.recipe_details.presenter.RecipeDetailsPresenter;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeDetailsFragment extends Fragment implements RecipeDetailsView {

    private TextView txtMealName, instructions;
    private ChipGroup mealCategories;
    private ImageView addToCalender, addToFavorites, mealImage;

    private LinearLayout linearLayout;
    private YouTubePlayerView youTubePlayerView;


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

        txtMealName = view.findViewById(R.id.txtMealName);
        instructions = view.findViewById(R.id.meal_instructions);
        mealCategories = view.findViewById(R.id.meal_tags);
        addToCalender = view.findViewById(R.id.add_to_calender);
        addToFavorites = view.findViewById(R.id.add_to_favorite);
        mealImage = view.findViewById(R.id.instructions_image);
        linearLayout = view.findViewById(R.id.ingredients_container);
        youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        String id = RecipeDetailsFragmentArgs.fromBundle(getArguments()).getId();

        RecipeDetailsPresenter presenter = new RecipeDetailsPresenter(this,
                MealsRepository.getInstance(new MealsRemoteDatasource(), new TodayMealLocalDatasource(getContext())));

        presenter.getMealById(id);

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

    private void displayMealDetails(MealsItem mealItem) {
        if (getContext() != null) {
            txtMealName.setText(mealItem.getStrMeal());
            instructions.setText(mealItem.getStrInstructions());

            mealCategories.removeAllViews();
            addChipToGroup(mealItem.getStrCategory());
            addChipToGroup(mealItem.getStrArea());

            Glide.with(getContext())
                    .load(mealItem.getStrMealThumb())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(R.drawable.broken_image)
                    .into(mealImage);

            addToCalender.setOnClickListener(view -> {
                Toast.makeText(getContext(), "add to calender", LENGTH_SHORT).show();
            });
            addToFavorites.setOnClickListener(view -> {
                Toast.makeText(getContext(), "add to favorite", LENGTH_SHORT).show();
            });

            displayIngredients(mealItem);


            if (!mealItem.getStrYoutube().isEmpty()) {
                youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        String videoUrl = mealItem.getStrYoutube();

                        if (videoUrl.indexOf('&') != -1) {
                            youTubePlayer.cueVideo(videoUrl.substring(videoUrl.indexOf('=') + 1, videoUrl.indexOf('&')), 0);
                        } else {
                            youTubePlayer.cueVideo(videoUrl.substring(videoUrl.indexOf('=') + 1), 0);
                        }
                    }
                });
            } else {
                youTubePlayerView.setVisibility(GONE);
            }
        }
    }

    private void displayIngredients(MealsItem mealItem) {
        linearLayout.removeAllViews();

        addIngredient(mealItem.getStrIngredient1(), mealItem.getStrMeasure1());
        addIngredient(mealItem.getStrIngredient2(), mealItem.getStrMeasure2());
        addIngredient(mealItem.getStrIngredient3(), mealItem.getStrMeasure3());
        addIngredient(mealItem.getStrIngredient4(), mealItem.getStrMeasure4());
        addIngredient(mealItem.getStrIngredient5(), mealItem.getStrMeasure5());
        addIngredient(mealItem.getStrIngredient6(), mealItem.getStrMeasure6());
        addIngredient(mealItem.getStrIngredient7(), mealItem.getStrMeasure7());
        addIngredient(mealItem.getStrIngredient8(), mealItem.getStrMeasure8());
        addIngredient(mealItem.getStrIngredient9(), mealItem.getStrMeasure9());
        addIngredient(mealItem.getStrIngredient10(), mealItem.getStrMeasure10());

        addIngredient(mealItem.getStrIngredient11(), mealItem.getStrMeasure12());
        addIngredient(mealItem.getStrIngredient12(), mealItem.getStrMeasure12());
        addIngredient(mealItem.getStrIngredient13(), mealItem.getStrMeasure13());
        addIngredient(mealItem.getStrIngredient14(), mealItem.getStrMeasure14());
        addIngredient(mealItem.getStrIngredient15(), mealItem.getStrMeasure15());
        addIngredient(mealItem.getStrIngredient16(), mealItem.getStrMeasure16());
        addIngredient(mealItem.getStrIngredient17(), mealItem.getStrMeasure17());
        addIngredient(mealItem.getStrIngredient18(), mealItem.getStrMeasure18());
        addIngredient(mealItem.getStrIngredient19(), mealItem.getStrMeasure19());
        addIngredient(mealItem.getStrIngredient20(), mealItem.getStrMeasure20());
    }

    private void addIngredient(String ingredientTitle, String ingredientMeasurement) {
        if (ingredientTitle != null && !ingredientTitle.trim().isEmpty()) {
            View child = getLayoutInflater().inflate(R.layout.ingredients_card, null);
            ImageView ingImage = child.findViewById(R.id.ingredient_image);
            TextView ingTitle = child.findViewById(R.id.ingredient_title);
            TextView ingMeasurement = child.findViewById(R.id.ingredient_measurement);

            ingTitle.setText(ingredientTitle);
            ingMeasurement.setText(ingredientMeasurement);

            Glide.with(getContext())
                    .load(ingredientImageUrl + ingredientTitle + "-Small.png")
                    .placeholder(R.drawable.broken_image)
                    .into(ingImage);

            linearLayout.addView(child);
        }
    }


    private void addChipToGroup(String text) {
        Chip chip = new Chip(getContext());
        chip.setText(text);
        mealCategories.addView(chip);
    }

    @Override
    public void recipeDetailsResponseSuccess(MealsItem mealsItem) {
        displayMealDetails(mealsItem);
    }

    @Override
    public void recipeDetailsResponseFail(String errorMessage) {
        Snackbar.make(this.getView(), errorMessage, Snackbar.LENGTH_SHORT).show();
    }
}