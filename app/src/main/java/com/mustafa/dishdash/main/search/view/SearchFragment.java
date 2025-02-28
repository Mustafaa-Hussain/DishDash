package com.mustafa.dishdash.main.search.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private EditText searchBar;
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

    @SuppressLint("CheckResult")
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
                default:
                    adapter.setSearchItemList(null);
                    searchItemList = null;
            }
            searchBar.setText("");
        });


        searchObservable = Observable.create(emitter ->
                searchBar.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        emitter.onNext(charSequence.toString().trim());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                })
        );
        subscribeForChanges();
    }

    private void setOnClickListener() {
        retry.setOnClickListener(v -> {
            subscribeForChanges();
        });
    }

    @SuppressLint("CheckResult")
    private void subscribeForChanges() {
        hideNoInternetConnection();
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
                        , error -> showNoInternetConnection());
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

    @Override
    public void onItemClickListener(String id, String title) {
        Toast.makeText(getContext(), title + ", id: " + id, Toast.LENGTH_SHORT).show();
    }
}