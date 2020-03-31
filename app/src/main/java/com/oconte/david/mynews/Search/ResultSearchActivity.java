package com.oconte.david.mynews.Search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.oconte.david.mynews.Calls.NYTCallsSearch;
import com.oconte.david.mynews.Models.SearchResult;
import com.oconte.david.mynews.R;
import com.oconte.david.mynews.RecyclerView.NYTResultSearchAdapter;
import com.oconte.david.mynews.WebView.ItemClickSupport;
import com.oconte.david.mynews.WebView.WebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("Registered")
public class ResultSearchActivity extends AppCompatActivity implements NYTCallsSearch.Callbacks {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.test_view_search)
    TextView textView;

    // FOR DESIGN
    @BindView(R.id.result_search_view) RecyclerView recyclerView;

    // FOR DATA
    private NYTResultSearchAdapter adapter;

    private static final String KEY_POSITION = "position";
    private int position;

    Context context;

    SearchResult result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);

        this.configureRecyclerView();

        this.executeHttpRequestWithRetrofit();

        this.configureOnClickRecyclerView();

        this.configureToolbar();

        this.getSearchQuery();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected void configureToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My News");


        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // -----------------
    // GET DATA FROM INTENT
    // -----------------
    String query = "";
    String beginDate = "";
    String endDate = "";
    String art;
    String business;
    String entrepreneurs;
    String politics;
    String sports;
    String travel;

    private void getSearchQuery() {

        Bundle searchString = getIntent().getExtras();
        assert searchString != null;

        query = searchString.getString("extra_query");
        beginDate = searchString.getString("extra_beginDate");
        endDate = searchString.getString("extra_endDate");
        art = searchString.getString("extra_art");
        business = searchString.getString("extra_business");
        entrepreneurs = searchString.getString("extra_entrepreneurs");
        politics = searchString.getString("extra_politics");
        sports = searchString.getString("extra_sports");
        travel = searchString.getString("extra_travel");

        if (query != null) {
            Log.d("searchString", "extra_query" + query);
            Toast.makeText(this, query, Toast.LENGTH_LONG).show();

        }

        if (beginDate != null) {
            Log.d("searchString", "extra_beginDate " + beginDate);
            Toast.makeText(this, beginDate, Toast.LENGTH_LONG).show();

        }

        if (endDate != null) {
            Log.d("searchActivity", "endDate " + endDate);
            Toast.makeText(this, endDate, Toast.LENGTH_LONG).show();
        }


        if (art != null) {
            Log.d("searchActivity", "art " + art);
            Toast.makeText(this, art, Toast.LENGTH_LONG).show();
        }

        if (business != null) {
            Log.d("searchActivity", "business " + business);
            Toast.makeText(this, business, Toast.LENGTH_LONG).show();
        }

        if (entrepreneurs != null) {
            Log.d("searchActivity", "entrepreneurs " + entrepreneurs);
            Toast.makeText(this, entrepreneurs, Toast.LENGTH_LONG).show();
        }

        if (politics != null) {
            Log.d("searchActivity", "politics " + politics);
            Toast.makeText(this, politics, Toast.LENGTH_LONG).show();
        }

        if (sports != null) {
            Log.d("searchActivity", "sports " + sports);
            Toast.makeText(this, sports, Toast.LENGTH_LONG).show();
        }

        if (travel != null) {
            Log.d("searchActivity", "travel " + travel);
            Toast.makeText(this, travel, Toast.LENGTH_LONG).show();
        }

        textView.setText(query + " - " + beginDate + " - " + endDate + " - " + art + " - " + business + " - " + entrepreneurs + " - " + politics + " - " + sports + " - " + travel);

        //intent.putExtras(searchString);

    }

    // Configure RecyclerView, Adapter, LayoutManager
    private void configureRecyclerView() {

        // Create adapter passing the list of articles
        this.adapter = new NYTResultSearchAdapter();

        // Attach the adapter to the recyclerview to populate items
        this.recyclerView.setAdapter(this.adapter);

        // Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // -----------------
    // ACTION
    // -----------------

    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerView, R.layout.activity_web_view)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        result.getResponse().getDocs().get(position);

                        Intent intent = new Intent(getBaseContext(), WebViewActivity.class);
                        intent.putExtra("url", result.getResponse().getDocs().get(position).getUrl());

                        startActivity(intent);
                    }
                });
    }

    private void executeHttpRequestWithRetrofit() {
        // Ca marche =)
        //NYTCallsSearch.getSearchSection(this, "20200301", "20200314", "politics", "trump", 10);
        NYTCallsSearch.getSearchSection(this, "20200101", "20200314", "sports", "kobe", 10);
        //NYTCallsSearch.getSearchSection(this, "20200301", "20200314", "technology", "3D", 10);

    }

    @Override
    public void onResponse(@Nullable SearchResult response) {
        this.result = response;
        this.adapter.updateCallRetrofitNews(response);
    }

    @Override
    public void onFailure() {
        // When getting error, we update UI
        Log.d("failure", "onFailure");
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
