package net.ddmax.plantpano.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.ddmax.plantpano.R;
import net.ddmax.plantpano.adapter.ResultListAdapter;
import net.ddmax.plantpano.base.BaseActivity;
import net.ddmax.plantpano.entity.Result;
import net.ddmax.plantpano.network.ApiService;
import net.ddmax.plantpano.network.ApiServiceGenerator;
import net.ddmax.plantpano.utils.BitmapUtils;

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

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.progress) LinearLayout mProgressView;
    @BindView(R.id.progress_bar) ContentLoadingProgressBar mProgressBar;
    @BindView(R.id.image) ImageView mUploadImageView;
    @BindView(R.id.result_list) RecyclerView mResultList;

    private Result resultData;
    private Bitmap imageBitmap;

    @Override
    public void initViews(Bundle savedInstanceState) {
        // Get bundles
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            imageBitmap = (Bitmap) bundle.get("data");
            uploadImage(imageBitmap);
        }
    }

    /**
     * Upload image
     */
    private void uploadImage(Bitmap bitmap) {
        // Start progress view
        setUploadProgressShown(true);

        ApiService apiService = ApiServiceGenerator.createService(ApiService.class);

        // Create RequestBody instance from bitmap
        final RequestBody requestImage = RequestBody.create(
                MediaType.parse("image/jpeg"),
                BitmapUtils.bitmapToByte(bitmap)
        );
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData(
                "file",
                UUID.randomUUID().toString() + ".jpg",
                requestImage);
        // Add another part within the multipart request
        String descriptionString = "upload for classification";
        RequestBody description = RequestBody.create(
                MultipartBody.FORM, descriptionString
        );

        // Finally, call the request
        Call<Result> call = apiService.upload(description, body);
        call.enqueue(uploadCallback);
    }

    Callback<Result> uploadCallback = new Callback<Result>() {
        @Override
        public void onResponse(Call<Result> call, Response<Result> response) {
            Log.i(TAG, "Upload successfully!");
            resultData = response.body();

            if (resultData != null) {
                switch (resultData.getCode()) {
                    case 0:
                        // Setup ImageView
                        mUploadImageView.setImageBitmap(imageBitmap);
                        setUpRecyclerView();
                        break;
                    default:
                        break;
                }
            } else {
                Log.e(TAG, "Backend error!");
                Toast.makeText(ResultActivity.this, getString(R.string.upload_toast_backend_error), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<Result> call, Throwable t) {
            t.printStackTrace();
        }
    };

    private void setUpRecyclerView() {
        setUploadProgressShown(false);
        ResultListAdapter adapter = new ResultListAdapter(this, resultData.getResult());
        mResultList.setLayoutManager(new LinearLayoutManager(this));
        mResultList.setAdapter(adapter);
    }

    private void setUploadProgressShown(boolean isStart) {
        mProgressView.setVisibility(isStart ? View.VISIBLE : View.GONE);
        mResultList.setVisibility(isStart ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_classify_result;
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle(getString(R.string.title_result));
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
}
