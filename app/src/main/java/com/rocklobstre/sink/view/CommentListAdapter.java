package com.rocklobstre.sink.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.rocklobstre.sink.R;
import com.rocklobstre.sink.model.Comment;
import com.rocklobstre.sink.util.animation.SupportAnimator;

import java.util.List;
import java.util.Random;

import timber.log.Timber;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {

    private final List<Comment> comments;
    private static final long[] delayList = {400, 500, 600, 700, 800, 900};
    private int lastPosition = -1;
    private Animator mAnimator;


    public CommentListAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Comment comment = comments.get(position);
        if (comment.isSyncPending()) {
            holder.commentText.setTextColor(Color.LTGRAY);
        } else {
            holder.commentText.setTextColor(Color.BLACK);
        }
        holder.commentText.setText(comment.getCommentText());

        holder.chartlet.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(final View v) {

                final View myView = holder.card;
                int cx = (myView.getLeft() + myView.getRight()) / 2;
                int cy = (myView.getTop() + myView.getBottom()) / 2;
                float finalRadius = hypo(myView.getWidth(), myView.getHeight());
                mAnimator = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
                mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                mAnimator.setDuration(1000);
                mAnimator.start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return comments == null ? 0 : comments.size();
    }

    public void updateCommentList(List<Comment> newComments) {
        Timber.d("Got new comments " + newComments.size());
        this.comments.clear();
        this.comments.addAll(newComments);
        notifyDataSetChanged();
    }

    /**
     * View holder for shopping list items of this adapter
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView card;
        private ImageView chartlet;
        private TextView commentText;

        public ViewHolder(final View view) {
            super(view);
            card = (CardView) view.findViewById(R.id.card);
            chartlet = (ImageView) view.findViewById(R.id.chartlet_review);
            commentText = (TextView) view.findViewById(R.id.comment);
        }
    }

    @Override
    public void onViewAttachedToWindow(final ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        final long delayTime = delayList[new Random().nextInt(5)];
        holder.card.setVisibility(View.INVISIBLE);

        if (holder.getPosition() > lastPosition) {
            holder.card.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    holder.card.setVisibility(View.VISIBLE);
                    ObjectAnimator alpha = ObjectAnimator.ofFloat(holder.card, "alpha", 0f, 1f);
                    ObjectAnimator scaleY = ObjectAnimator.ofFloat(holder.card, "scaleY", 0f, 1f);
                    ObjectAnimator scaleX = ObjectAnimator.ofFloat(holder.card, "scaleX", 0f, 1f);
                    AnimatorSet animSet = new AnimatorSet();
                    animSet.play(alpha).with(scaleY).with(scaleX);
                    animSet.setInterpolator(new OvershootInterpolator());
                    animSet.setDuration(400);
                    animSet.start();

                }
            }, delayTime);

            lastPosition = holder.getPosition();
        } else {
            holder.card.setVisibility(View.VISIBLE);
        }
    }

    static float hypo(int a, int b) {
        return (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

}
