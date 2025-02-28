package com.mustafa.dishdash.main.search.view;

import com.mustafa.dishdash.main.search.view.adapter.SearchItem;

import java.util.List;

public interface SearchFragmentView {
    void onGetCategoriesSuccess(List<SearchItem> searchItemList);
    void onGetCategoriesFail(String errorMsg);
}
