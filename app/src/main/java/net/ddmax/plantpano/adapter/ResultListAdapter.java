package net.ddmax.plantpano.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.ddmax.plantpano.R;
import net.ddmax.plantpano.entity.Result;
import net.ddmax.plantpano.utils.NumberUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author ddMax
 * @since 2017-03-11 02:55 PM.
 */

public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.ViewHolder> {

    private Context mContext;
    private List<Result.ScoreBean> data;

    public ResultListAdapter(Context mContext, List<Result.ScoreBean> data) {
        this.mContext = mContext;
        this.data = data;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_class_label) TextView mTextLabel;
        @BindView(R.id.tv_class_score) TextView mTextScore;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_classify_result, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Result.ScoreBean result = data.get(position);
        holder.mTextLabel.setText(result.getName());
        holder.mTextScore.setText(
                NumberUtils.convertToPercent(result.getScore())
        );
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
