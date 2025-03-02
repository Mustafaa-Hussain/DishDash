package com.mustafa.dishdash.main.meals.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mustafa.dishdash.R;
import com.mustafa.dishdash.main.data_layer.MealsRepository;
import com.mustafa.dishdash.main.data_layer.db.favorites.FavoritesMealsLocalDatasource;
import com.mustafa.dishdash.main.data_layer.db.future_planes.FuturePlanesLocalDatasource;
import com.mustafa.dishdash.main.data_layer.firebase.favorite_meals.FavoritesRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.firebase.future_plane.FuturePlanesRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.network.MealsRemoteDatasource;
import com.mustafa.dishdash.main.data_layer.pojo.search.MealsItem;
import com.mustafa.dishdash.main.data_layer.shared_prefs.TodayMealLocalDatasource;
import com.mustafa.dishdash.main.meals.presenter.MealsPresenter;
import com.mustafa.dishdash.main.meals.view.adapter.ItemClickListener;
import com.mustafa.dishdash.main.meals.view.adapter.MealsAdapter;

import java.util.List;

public class MealsFragment extends Fragment implements ItemClickListener, MealsView {

    private MealsAdapter adapter;

    public MealsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String filterType = MealsFragmentArgs.fromBundle(getArguments()).getFilterType();
        String query = MealsFragmentArgs.fromBundle(getArguments()).getQuery();

        adapter = new MealsAdapter(getContext(), this);
        RecyclerView mealsRecyclerView = view.findViewById(R.id.meals_recycler_view);
        mealsRecyclerView.setAdapter(adapter);

        MealsPresenter presenter = new MealsPresenter(
                MealsRepository.getInstance(new MealsRemoteDatasource(),
                        new TodayMealLocalDatasource(getContext()),
                        new FavoritesMealsLocalDatasource(getContext()),
                        new FavoritesRemoteDatasource(),
                        new FuturePlanesRemoteDatasource(),
                        new FuturePlanesLocalDatasource(getContext())),
                this);

        presenter.getData(filterType, query);

    }

    @Override
    public void onItemClickListener(String id, String title) {
        Navigation.findNavController(this.getView()).navigate(
                MealsFragmentDirections.
                        actionMealsFragmentToRecipeDetailsFragment(id));

    }

    @Override
    public void onMealResponseSuccess(List<MealsItem> mealsItemList) {
        adapter.mealsItems(mealsItemList);
    }

    @Override
    public void onMealResponseFail(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}