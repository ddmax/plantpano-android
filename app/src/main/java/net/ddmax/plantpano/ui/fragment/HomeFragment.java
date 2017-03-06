package net.ddmax.plantpano.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import net.ddmax.plantpano.R;
import net.ddmax.plantpano.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ddMax
 * @since 2017-03-01 12:24 PM
 * 说明：首页Fragment
 */
public class HomeFragment extends BaseFragment {

    public static final int REQUEST_IMAGE_CAPTURE = 1;

    @BindView(R.id.fab_menu) FloatingActionMenu mFam;
    @BindView(R.id.fab_btn_from_camera) FloatingActionButton mFabCamera;
    @BindView(R.id.fab_btn_from_album) FloatingActionButton mFabAlbum;

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
     * Take the photo
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
                    Toast.makeText(getActivity(), "Taking photo successfully", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

}
