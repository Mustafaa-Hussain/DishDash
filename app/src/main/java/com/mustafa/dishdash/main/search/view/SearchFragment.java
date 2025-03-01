package com.mustafa.dishdash.main.search.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.chip.ChipGroup;
import com.mustafa.dishdash.R;
import com.mustafa.dishdash.main.search.data_layer.SearchRemoteDataSource;
import com.mustafa.dishdash.main.search.data_layer.SearchRepository;
import com.mustafa.dishdash.main.search.presenter.SearchPresenter;
import com.mustafa.dishdash.main.search.view.adapter.ItemClickListener;
import com.mustafa.dishdash.main.search.view.adapter.SearchAdapter;
import com.mustafa.dishdash.main.search.view.adapter.SearchItem;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class SearchFragment extends Fragment implements ItemClickListener, SearchFragmentView {

    private View noInternetConnectionMessage;
    private SearchView searchBar;
    private TextView retry;
    private ChipGroup chipFilterGroup;
    private RecyclerView searchRecyclerView;
    private SearchAdapter adapter;
    private List<SearchItem> searchItemList;
    private Observable<String> searchObservable;
    private SearchPresenter presenter;

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI(view);
        setOnClickListener();

        presenter = new SearchPresenter(this,
                SearchRepository.getInstance(new SearchRemoteDataSource()));

        adapter = new SearchAdapter(getContext(), this);
        searchRecyclerView.setAdapter(adapter);

        chipFilterGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            adapter.setSearchItemList(null);
            searchBar.clearFocus();
            searchBar.setQuery("", false);
            switch (group.getCheckedChipId()) {
                case R.id.category_chip:
                    presenter.getCategories();
                    break;
                case R.id.country_chip:
                    presenter.getCountries();
                    break;
                case R.id.ingredients_chip:
                    presenter.getIngredients();
                    break;
                case R.id.name_chip:
                    searchItemList = null;
            }
        });

        searchObservable = Observable.create(emitter ->
                searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        emitter.onNext(s.trim());
                        return false;
                    }
                }));

        subscribeForChanges();
    }

    private void setOnClickListener() {
        retry.setOnClickListener(v -> {
            subscribeForChanges();
        });
    }

    @SuppressLint("CheckResult")
    private void subscribeForChanges() {
        searchObservable
                .subscribeOn(Schedulers.io())
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .flatMap(query -> {
                            if (searchItemList != null) {
                                return Observable.fromIterable(searchItemList)
                                        .filter(searchItem ->
                                                searchItem.getTitle()
                                                        .toLowerCase()
                                                        .startsWith(query.toLowerCase()))
                                        .toList()
                                        .toObservable();
                            } else {
                                return presenter.filterByMealName(query);
                            }
                        }
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchResult ->
                                adapter.setSearchItemList(searchResult)
                        , error -> showNoItemsWithThisName());

        hideNoInternetConnection();
    }

    private void showNoItemsWithThisName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.no_meal_with_this_name)
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                    subscribeForChanges();
                    searchBar.setQuery("", false);
                })
                .setOnCancelListener(dialogInterface -> {
                    subscribeForChanges();
                    searchBar.setQuery("", false);
                });
        builder.create().show();
    }

    private void showNoInternetConnection() {
        noInternetConnectionMessage.setVisibility(View.VISIBLE);
        searchBar.setVisibility(View.GONE);
        chipFilterGroup.setVisibility(View.GONE);
        searchRecyclerView.setVisibility(View.GONE);
    }

    private void hideNoInternetConnection() {
        noInternetConnectionMessage.setVisibility(View.GONE);
        searchBar.setVisibility(View.VISIBLE);
        chipFilterGroup.setVisibility(View.VISIBLE);
        searchRecyclerView.setVisibility(View.VISIBLE);
    }

    private void setupUI(View view) {
        noInternetConnectionMessage = view.findViewById(R.id.no_internet_connection_group);
        retry = view.findViewById(R.id.retry);
        searchBar = view.findViewById(R.id.search_bar);
        chipFilterGroup = view.findViewById(R.id.chip_filter_group);
        searchRecyclerView = view.findViewById(R.id.search_recycler_view);
    }

    @Override
    public void onGetCategoriesSuccess(List<SearchItem> searchItemList) {
        adapter.setSearchItemList(searchItemList);
        this.searchItemList = searchItemList;
    }

    @Override
    public void onGetCategoriesFail(String errorMsg) {
        showNoInternetConnection();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onItemClickListener(String id, String title) {
        switch (chipFilterGroup.getCheckedChipId()) {
            case R.id.category_chip:
                Navigation.findNavController(this.getView()).navigate(
                        SearchFragmentDirections.
                                actionSearchFragmentToMealsFragment("c", title)
                );
                break;
            case R.id.country_chip:
                Navigation.findNavController(this.getView()).navigate(
                        SearchFragmentDirections.
                                actionSearchFragmentToMealsFragment("a", title)
                );
                break;
            case R.id.ingredients_chip:
                Navigation.findNavController(this.getView()).navigate(
                        SearchFragmentDirections.
                                actionSearchFragmentToMealsFragment("i", title)
                );
                break;
            default:
                Navigation.findNavController(this.getView()).navigate(
                        SearchFragmentDirections.
                                actionSearchFragmentToRecipeDetailsFragment(id));

        }
    }
}