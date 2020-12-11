package com.example.posts.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.posts.R;
import com.example.posts.data.model.PostModel;
import com.example.posts.data.network.PostService;
import com.example.posts.databinding.FragmentDetailsBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailsFragment extends Fragment {

    FragmentDetailsBinding binding;
    Integer group, id, user;
    boolean knopka = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getPost();
        onClick();
    }

    @SuppressLint("SetTextI18n")
    private void getPost() {
        if (getArguments() != null) {
            knopka = true;
            assert getArguments() != null;
            PostModel model = (PostModel) getArguments().getSerializable("model");
            assert model != null;
            binding.id.setText(model.getId().toString());
            binding.user.setText(model.getUser().toString());
            binding.group.setText(model.getGroup().toString());
            binding.content.setText(model.getContent());
            binding.title.setText(model.getTitle());
        }
    }

    public void onClick() {
        binding.btn.setOnClickListener(view1 -> {

            id = Integer.valueOf(binding.id.getText().toString());
            user = Integer.valueOf(binding.user.getText().toString());
            group = Integer.valueOf(binding.group.getText().toString());

            Bundle bundle = new Bundle();
            PostModel model = new PostModel(id,
                    binding.title.getText().toString().trim(),
                    binding.content.getText().toString().trim(),
                    user,
                    group);
            bundle.putSerializable("model", model);
            setArguments(bundle);

            if (!knopka)
                PostService.getInstance().sendPost(model).enqueue(new Callback<PostModel>() {
                    @Override
                    public void onResponse(Call<PostModel> call, Response<PostModel> response) {

                    }

                    @Override
                    public void onFailure(Call<PostModel> call, Throwable t) {

                    }
                });
            else
                PostService.getInstance().updatePost(model.getId().toString(), model).enqueue(new Callback<PostModel>() {
                    @Override
                    public void onResponse(Call<PostModel> call, Response<PostModel> response) {

                    }

                    @Override
                    public void onFailure(Call<PostModel> call, Throwable t) {

                    }
                });

            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            navController.navigateUp();
        });
    }

}