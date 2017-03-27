package net.ddmax.plantpano.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import net.ddmax.plantpano.R;
import net.ddmax.plantpano.adapter.ImageListAdapter;
import net.ddmax.plantpano.base.BaseFragment;
import net.ddmax.plantpano.entity.Image;
import net.ddmax.plantpano.entity.ImageList;
import net.ddmax.plantpano.network.ApiService;
import net.ddmax.plantpano.network.RetrofitGenerator;
import net.ddmax.plantpano.ui.activity.ResultActivity;
import net.ddmax.plantpano.ui.common.GridSpacingItemDecoration;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author ddMax
 * @since 2017-03-01 12:24 PM
 * 说明：首页Fragment
 */
public class HomeFragment extends BaseFragment {

    public static final String TAG = HomeFragment.class.getSimpleName();
    public static final int REQUEST_IMAGE_CAPTURE = 1;

    @BindView(R.id.image_list) RecyclerView mImageRecyclerView;
    @BindView(R.id.fab_menu) FloatingActionMenu mFam;
    @BindView(R.id.fab_btn_from_camera) FloatingActionButton mFabCamera;
    @BindView(R.id.fab_btn_from_album) FloatingActionButton mFabAlbum;
    @BindView(R.id.loading_view) LinearLayout mLoadingView;
    @BindView(R.id.error_view) LinearLayout mErrorView;

    private List<Image> imageListData;
    Call<ImageList> callImageList;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void finishCreateView(Bundle savedInstanceState) {
        loadData();
    }

    /**
     * Fetch all public image that needs further classification
     */
    @Override
    protected void loadData() {
        setUpLoadingViewVisibility(true);
        callImageList = RetrofitGenerator.createService(ApiService.class)
                .getAllPubImage();
        callImageList.enqueue(callbackImageList);
    }

    Callback<ImageList> callbackImageList = new Callback<ImageList>() {
        @Override
        public void onResponse(Call<ImageList> call, Response<ImageList> response) {
            if (response.errorBody() != null) {
                // Show error view
                try {
                    Log.e(TAG, response.errorBody().string());
                    setUpErrorViewVisibility(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                setUpLoadingViewVisibility(false);
                imageListData = response.body().getItems();
                initRecycler();
            }
        }

        @Override
        public void onFailure(Call<ImageList> call, Throwable t) {
            Toast.makeText(getActivity(), getString(R.string.msg_network_error), Toast.LENGTH_SHORT).show();
            setUpLoadingViewVisibility(false);
            setUpErrorViewVisibility(true);
        }
    };

    private void initRecycler() {
        if (imageListData.size() == 0) {
            setUpErrorViewVisibility(true, getString(R.string.msg_empty_image_list));
        } else {
            mImageRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            mImageRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 0, false));
            mImageRecyclerView.setAdapter(new ImageListAdapter(getActivity(), imageListData));
        }
    }

    private void setUpLoadingViewVisibility(boolean isVisible) {
        setUpErrorViewVisibility(false);
        mLoadingView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void setUpErrorViewVisibility(boolean isVisible) {
        this.setUpErrorViewVisibility(isVisible, null);
    }

    private void setUpErrorViewVisibility(boolean isVisible, String msg) {
        mErrorView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        if (msg != null) {
            ((TextView) mErrorView.findViewById(R.id.tv_error_msg)).setText(msg);
        }
    }

    @OnClick(R.id.error_view)
    public void errorViewClick() {
        this.loadData();
    }

    @OnClick(R.id.fab_btn_from_camera)
    public void onFabCameraClick() {
        mFam.close(true);
        startCamera();
    }

    @OnClick(R.id.fab_btn_from_album)
    public void onFabAlbumClick() {
        Toast.makeText(getActivity(), "To select from album", Toast.LENGTH_SHORT).show();
        mFam.close(true);
    }

    /**
     * Take the image
     */
    private void startCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Intent intent = new Intent(getActivity(), ResultActivity.class);
                        intent.putExtras(extras);
                        startActivity(intent);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        callImageList.cancel();
        super.onDestroy();
    }
}
