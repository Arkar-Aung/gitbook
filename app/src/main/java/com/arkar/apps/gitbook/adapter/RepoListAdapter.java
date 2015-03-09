package com.arkar.apps.gitbook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arkar.apps.gitbook.R;
import com.arkar.apps.gitbook.model.Repo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arkar on 3/9/15.
 */
public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.ViewHolder> {

    private Context context;
    private List<Repo> mRepoList = new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatar;
        public TextView repoName;
        public TextView starCount;
        public TextView description;
        public ViewHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            repoName = (TextView) itemView.findViewById(R.id.repo_name);
            starCount = (TextView) itemView.findViewById(R.id.star_count);
            description = (TextView) itemView.findViewById(R.id.description);
        }
    }

    @Override
    public RepoListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.repo_item_view,
                viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RepoListAdapter.ViewHolder viewHolder, int i) {
        Repo repo = this.mRepoList.get(i);

        Picasso.with(context).load(repo.getOwner().getAvatar()).into(viewHolder.avatar);
        viewHolder.repoName.setText(repo.getRepoName());
        viewHolder.starCount.setText(repo.getStarCount());
        viewHolder.description.setText(repo.getDescription());
    }

    @Override
    public int getItemCount() {
        return this.mRepoList.size();
    }

    public void replaceWith(List<Repo> repoList) {
        this.mRepoList = repoList;
    }

    public void add(Repo repo) {
        this.mRepoList.add(repo);
    }
}
