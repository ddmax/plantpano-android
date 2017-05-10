package net.ddmax.plantpano.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.ddmax.plantpano.Constants;
import net.ddmax.plantpano.R;
import net.ddmax.plantpano.entity.Comment;
import net.ddmax.plantpano.entity.Image;
import net.ddmax.plantpano.ui.activity.ResultActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author ddMax
 * @since 2017-03-26 04:56 PM.
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    private Context mContext;
    private List<Image> data;

    public ImageListAdapter(Context context, List<Image> data) {
        this.mContext = context;
        this.data = data;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.card_view) CardView mCardView;
        @BindView(R.id.item_image) ImageView mImageView;
        @BindView(R.id.item_title) TextView mTitle;
        @BindView(R.id.item_review) TextView mReview;
        @BindView(R.id.item_comment) TextView mComment;
        @BindView(R.id.item_likeit) TextView mLikeit;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Image imageObj = data.get(position);
        Picasso.with(mContext)
                .load(Constants.URL.BASE + imageObj.getImageLink().replaceFirst("/", ""))
                .into(holder.mImageView);
        holder.mTitle.setText(imageObj.getName());
        holder.mReview.setText(String.valueOf(imageObj.getReview()));
        holder.mLikeit.setText(String.valueOf(imageObj.getLikeit()));
        List<Comment> comments = imageObj.getComments();
        holder.mComment.setText(String.valueOf(comments == null ? 0 : comments.size()));
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ResultActivity.class);
                intent.putExtra("image_obj", imageObj);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
