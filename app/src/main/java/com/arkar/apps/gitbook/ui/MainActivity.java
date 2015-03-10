package com.arkar.apps.gitbook.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arkar.apps.gitbook.Config;
import com.arkar.apps.gitbook.R;
import com.arkar.apps.gitbook.adapter.RepoListAdapter;
import com.arkar.apps.gitbook.model.Repo;
import com.arkar.apps.gitbook.network.RepoApi;
import com.arkar.apps.gitbook.util.PrefUtilis;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.arkar.apps.gitbook.util.NetworkUtils.checkConnection;
import static com.arkar.apps.gitbook.util.NetworkUtils.getRestAdapter;


public class MainActivity extends ActionBarActivity {

    private RecyclerView mRepoList;
    private ProgressBar mProgressbar;
    private TextView mRetry;
    private RepoListAdapter mRepoListAdapter;
    private Subscription mRepoSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (PrefUtilis.checkAuthenticated(this) == 0) {
            goToLogin();
        }

        mRepoList = (RecyclerView) findViewById(R.id.repo_list);
        mProgressbar = (ProgressBar) findViewById(R.id.progressbar);
        mRetry = (TextView) findViewById(R.id.retry);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRepoList.setLayoutManager(layoutManager);
        mRepoListAdapter = new RepoListAdapter();
        mRepoList.setAdapter(mRepoListAdapter);

        mRetry.setOnClickListener(v -> {
            mRetry.setVisibility(View.GONE);
            mProgressbar.setVisibility(View.VISIBLE);
            getRepoList();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRepoSubscription = getRepoList();
    }

    private Subscription getRepoList() {
        if (! checkConnection(this)) {
            Toast.makeText(this, getResources().getString(R.string.message_no_connection),
                    Toast.LENGTH_SHORT).show();
            mRetry.setVisibility(View.VISIBLE);
            mProgressbar.setVisibility(View.GONE);
            return null;
        }

        final String oAuthToken = "token " + PrefUtilis.getOAuthToken(this);
        return getRestAdapter(Config.BASE_URL).create(RepoApi.class)
                .getRepositories(oAuthToken)
                .flatMap(repoList -> Observable.from(repoList))
                .flatMap(repo -> getRepo(oAuthToken, repo.getOwner().getName(), repo.getRepoName()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(repo -> {
                    mRepoListAdapter.add(repo);
                    mRepoListAdapter.notifyDataSetChanged();
                    mProgressbar.setVisibility(View.GONE);
                    mRetry.setVisibility(View.GONE);
                    mRepoList.setVisibility(View.VISIBLE);
                }, throwable -> {
                    Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    if (mRepoList.getVisibility() != View.VISIBLE) {
                        mRetry.setVisibility(View.VISIBLE);
                    }
                    mProgressbar.setVisibility(View.GONE);
                });
    }

    private Observable<Repo> getRepo(String oAuthToken, String owner, String repoName) {
        return getRestAdapter(Config.BASE_URL).create(RepoApi.class)
                .getRepo(oAuthToken, owner, repoName);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //mRepoSubscription.unsubscribe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            PrefUtilis.setAuthenticated(this, 0);
            PrefUtilis.setOAuthToken(this, "");
            goToLogin();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
