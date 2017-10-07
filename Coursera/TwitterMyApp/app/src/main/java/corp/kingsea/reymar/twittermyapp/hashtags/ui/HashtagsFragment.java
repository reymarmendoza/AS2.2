package corp.kingsea.reymar.twittermyapp.hashtags.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import corp.kingsea.reymar.twittermyapp.R;
import corp.kingsea.reymar.twittermyapp.TwitterClientApp;
import corp.kingsea.reymar.twittermyapp.entities.Hashtag;
import corp.kingsea.reymar.twittermyapp.hashtags.HashtagsPresenter;
import corp.kingsea.reymar.twittermyapp.hashtags.di.HashtagsComponent;
import corp.kingsea.reymar.twittermyapp.hashtags.ui.adapters.HashtagsAdapter;
import corp.kingsea.reymar.twittermyapp.hashtags.ui.adapters.OnItemClickListener;

/**
 * Created by reyma on 4/07/2016.
 */
public class HashtagsFragment extends Fragment implements HashtagsView, OnItemClickListener {

    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.container)
    FrameLayout container;

    @Inject
    HashtagsAdapter adapter;
    @Inject
    HashtagsPresenter presenter;

    public HashtagsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        ButterKnife.bind(this, view);
        setupInjection();
        setupRecyclerView();
        presenter.getHashtagsTweets();
        return view;
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setupInjection() {
        TwitterClientApp app = (TwitterClientApp)getActivity().getApplication();
        HashtagsComponent hashtagsComponent = app.getHashtagsComponent(this, this);
        //presenter = ImagesComponent.getPresenter();
        hashtagsComponent.inject(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showImages() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideImages() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(String error) {
        Snackbar.make(container, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setContent(List<Hashtag> items) {
        adapter.setItems(items);
    }

    @Override
    public void onItemClick(Hashtag hashtags) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(hashtags.getTweetUrl()));
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
