package com.mustafa.dishdash.main.Planes.view;

import android.app.AlertDialog;
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
import com.mustafa.dishdash.main.Planes.presenter.PlanesPresenter;
import com.mustafa.dishdash.main.data_layer.FuturePlanesRepository;
import com.mustafa.dishdash.main.data_layer.db.future_planes.FuturePlanesLocalDatasource;
import com.mustafa.dishdash.main.data_layer.db.future_planes.entites.FuturePlane;
import com.mustafa.dishdash.main.data_layer.firebase.future_plane.FuturePlanesRemoteDatasource;

import java.util.List;

public class PlanesFragment extends Fragment implements PlanesView, FuturePlaneClickListener {
    private PlanesAdapter adapter;
    private PlanesPresenter presenter;

    public PlanesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_planes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.future_planes_recycler_view);
        adapter = new PlanesAdapter(getContext(), this);
        recyclerView.setAdapter(adapter);

        presenter = new PlanesPresenter(
                FuturePlanesRepository
                        .getInstance(new FuturePlanesLocalDatasource(getContext()),
                                new FuturePlanesRemoteDatasource()),
                this);

        presenter.getAllFuturePlanes();
    }

    @Override
    public void onStop() {
        presenter.close();
        super.onStop();
    }

    @Override
    public void onGetAllFuturePlanesSuccess(List<FuturePlane> futurePlanes) {
        adapter.setFuturePlanes(futurePlanes);
    }

    @Override
    public void onGetAllFuturePlanesFail(String errorMsg) {
        if (getContext() != null)
            Toast.makeText(getContext(), R.string.failed_to_load_future_planes, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteFuturePlaneSuccess() {
        if (getContext() != null)
            Toast.makeText(getContext(), R.string.deleted, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteFuturePlaneFail(String errorMsg) {
        if (getContext() != null)
            Toast.makeText(getContext(), "Failed to delete Plane", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserNotLoggedIn() {
        if (getContext() != null) {
            Toast.makeText(getContext(), R.string.you_are_not_logged_in, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSyncSuccess() {
        if (getContext() != null) {
            Toast.makeText(getContext(), R.string.data_synced, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void removePlaneClickListener(FuturePlane futurePlane) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.are_sure_you_want_to_remove_this_meal_from_planes)
                .setPositiveButton(R.string.remove, (dialogInterface, i) -> {
                    presenter.deleteFuturePlanes(futurePlane);
                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });
        builder.create().show();
    }

    @Override
    public void itemClickListener(String mealId) {
        if (this.getView() != null) {
            PlanesFragmentDirections.ActionPlanesFragmentToRecipeDetailsFragment action =
                    PlanesFragmentDirections.actionPlanesFragmentToRecipeDetailsFragment(mealId);
            Navigation.findNavController(this.getView()).navigate(action);
        }
    }
}