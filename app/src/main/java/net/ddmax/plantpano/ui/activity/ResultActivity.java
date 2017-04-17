package net.ddmax.plantpano.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jph.takephoto.model.TImage;
import com.squareup.picasso.Picasso;

import net.ddmax.plantpano.R;
import net.ddmax.plantpano.base.BaseActivity;
import net.ddmax.plantpano.entity.Image;
import net.ddmax.plantpano.network.ApiService;
import net.ddmax.plantpano.network.RetrofitGenerator;
import net.ddmax.plantpano.ui.custom.viewpagercard.CardItem;
import net.ddmax.plantpano.ui.custom.viewpagercard.CardPagerAdapter;
import net.ddmax.plantpano.ui.custom.viewpagercard.ShadowTransformer;
import net.ddmax.plantpano.utils.BitmapUtils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivity extends BaseActivity {
    public static final String TAG = ResultActivity.class.getSimpleName();

    @BindView(R.id.app_bar) AppBarLayout mAppBar;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.progress) LinearLayout mProgressView;
    @BindView(R.id.progress_bar) ContentLoadingProgressBar mProgressBar;
    @BindView(R.id.image) ImageView mUploadImageView;
    @BindView(R.id.view_pager_result) ViewPager mViewPager;

    private Image resultData;
    private File imageFile;
    private Call<Image> call;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;

    @Override
    public void initViews(Bundle savedInstanceState) {
        TImage image = (TImage) getIntent().getSerializableExtra("image");
        int from = getIntent().getIntExtra("image_from", 0);

        processImage(image, from);
        uploadImage(image);
    }

    /**
     * Compress image if takes from album
     */
    private void processImage(TImage image, int from) {
        if (from == 1) {
            imageFile = BitmapUtils.convertFileToJpeg(image.getCompressPath());
        }
    }

    /**
     * Upload image
     */
    private void uploadImage(TImage image) {
        // Start progress view
        setUploadProgressShown(true);

        // TODO: User id/Filename
        // Create RequestBody instance from file
        final RequestBody requestImage = RequestBody.create(
                MediaType.parse("image/jpeg"),
                imageFile
        );

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part imageBody = MultipartBody.Part.createFormData(
                "image",
                UUID.randomUUID().toString(),
                requestImage);

        // Finally, call the request
        call = RetrofitGenerator.createService(ApiService.class)
                .upload(RequestBody.create(MultipartBody.FORM, UUID.randomUUID().toString()),
                        RequestBody.create(MultipartBody.FORM, "true"),
                        imageBody);
        call.enqueue(uploadCallback);
    }

    Callback<Image> uploadCallback = new Callback<Image>() {
        @Override
        public void onResponse(Call<Image> call, Response<Image> response) {
            if (response.errorBody() != null) {
                //TODO: Deal with error body
                try {
                    Log.e(TAG, response.errorBody().string());
                    setUpErrorView();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                resultData = response.body();

                if (resultData != null) {
                    if (response.code() == 201) {
                        // Setup ImageView
                        Picasso.with(ResultActivity.this).load(imageFile).into(mUploadImageView);
                        setUpCardAdapter();
                    }
                }
            }

        }

        @Override
        public void onFailure(Call<Image> call, Throwable t) {
            t.printStackTrace();
        }

    };

    private void setUpCardAdapter() {
        setUploadProgressShown(false);

        mCardAdapter = new CardPagerAdapter();
        if (resultData != null) {
            for (Image.Result result : resultData.getResult()) {
                mCardAdapter.addCardItem(new CardItem(result.getName(), result.getScore()));
            }
        }
        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mCardShadowTransformer.enableScaling(true);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
    }

    private void setUploadProgressShown(boolean isStart) {
        mAppBar.setVisibility(isStart ? View.GONE : View.VISIBLE);
        mProgressView.setVisibility(isStart ? View.VISIBLE : View.GONE);
        mViewPager.setVisibility(isStart ? View.GONE : View.VISIBLE);
    }

    private void setUpErrorView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_result;
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        call.cancel();
        super.onDestroy();
    }
}
