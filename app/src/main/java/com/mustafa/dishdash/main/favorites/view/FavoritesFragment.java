package com.mustafa.dishdash.main.favorites.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mustafa.dishdash.R;
import com.mustafa.dishdash.auth.AuthenticationActivity;
import com.mustafa.dishdash.main.data_layer.MealsRepository;
import com.mustafa.dishdash.main.data_layer.db.favorites.FavoritesMealsLocalDatasource;
import com.mustafa.dishdash.main.data_layer.db.future_planes.FuturePlanesLocalDatasource;
import com.mustafa.dishdash.main.data_layer.firebase.favorite_meals.FavoritesRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.firebase.future_plane.FuturePlanesRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.network.MealsRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.pojo.random_meal.MealsItem;
import com.mustafa.dishdash.main.data_layer.shared_prefs.TodayMealLocalDatasource;
import com.mustafa.dishdash.main.favorites.presenter.FavoritesPresenter;
import com.mustafa.dishdash.main.favorites.view.adapter.FavoriteItemClickListener;
import com.mustafa.dishdash.main.favorites.view.adapter.FavoritesAdapter;

import java.util.List;


public class FavoritesFragment extends Fragment implements FavoriteItemClickListener, FavoritesView {
    private RecyclerView favoritesRecyclerView;
    private View notLoggedInGroup;
    private TextView login;
    private FavoritesAdapter adapter;
    private FavoritesPresenter presenter;

    public FavoritesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favoritesRecyclerView = view.findViewById(R.id.favorite_meals_recycler_view);
        notLoggedInGroup = view.findViewById(R.id.not_logged_in_group);
        login = view.findViewById(R.id.login);

        login.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), AuthenticationActivity.class));
        });

        presenter = new FavoritesPresenter(this,
                MealsRepository.getInstance(
                        new MealsRemoteDatasource(),
                        new TodayMealLocalDatasource(getContext()),
                        new FavoritesMealsLocalDatasource(getContext()),
                        new FavoritesRemoteDatasource(),
                        new FuturePlanesRemoteDatasource(),
                        new FuturePlanesLocalDatasource(getContext())
                ));

        adapter = new FavoritesAdapter(getContext(), this);
        favoritesRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        presenter.getAllFavoriteMeals();
        super.onResume();
    }

    @Override
    public void onStop() {
        presenter.close();
        super.onStop();
    }

    @Override
    public void allFavoriteMeals(List<MealsItem> mealsList) {
        notLoggedInGroup.setVisibility(View.GONE);
        adapter.setMealsList(mealsList);
    }

    @Override
    public void noFavoriteMeals() {
        if (getContext() != null)
            Toast.makeText(getContext(), R.string.there_are_no_favorite_meals, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRemovedSuccess() {
        if (getContext() != null)
            Toast.makeText(getContext(), R.string.favorite_meal_removed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRemovedFail() {
        if (getContext() != null)
            Toast.makeText(getContext(), R.string.failed_to_remove_favorite_meal, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void userNotLoggedIn() {
        notLoggedInGroup.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSyncDataFailed() {
        if (getContext() != null) {
            Toast.makeText(getContext(), R.string.data_synced, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSyncDataSuccess() {
        if (getContext() != null) {
            Toast.makeText(getContext(), R.string.failed_to_sync_data, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void toggleFavoriteClickListener(MealsItem favoriteMeal) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.are_sure_you_want_to_remove_this_meal_from_favorites)
                .setPositiveButton(R.string.remove, (dialogInterface, i) -> {
                    presenter.removeFavoriteMeal(favoriteMeal);
                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });
        builder.create().show();
    }

    @Override
    public void itemClickListener(MealsItem favoriteMeal) {
        if (this.getView() != null) {
            FavoritesFragmentDirections.ActionFavoritesFragmentToRecipeDetailsFragment action = FavoritesFragmentDirections
                    .actionFavoritesFragmentToRecipeDetailsFragment(favoriteMeal.getIdMeal());
            Navigation.findNavController(this.getView()).navigate(action);
        }
    }
}