package com.example.posts.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.posts.R;
import com.example.posts.data.adapter.PostAdapter;
import com.example.posts.data.model.PostModel;
import com.example.posts.data.network.OnClickListener;
import com.example.posts.data.network.PostService;
import com.example.posts.databinding.FragmentPostBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostFragment extends Fragment implements OnClickListener {


    FragmentPostBinding binding;
    public ArrayList<PostModel> postModels;
    PostAdapter postAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
        getPost();
    }

    public void getPost() {
        PostService.getInstance().getPosts().enqueue(new Callback<ArrayList<PostModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PostModel>> call, Response<ArrayList<PostModel>> response) {
                Log.d("TAG", "onResponse: Success!" +response.body().size());
                postAdapter.setList(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<PostModel>> call, Throwable t) {
                Log.d("TAG", "onFailure: Fail!" + t.getLocalizedMessage());
            }
        });
    }

    private void init() {
        postModels = new ArrayList<>();
        postAdapter = new PostAdapter(postModels,this);
        binding.recyclerView.setAdapter(postAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPost();
    }

    @Override
    public void openEdit(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", postAdapter.getElement(position));
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_postFragment_to_detailsFragment, bundle);
    }
}