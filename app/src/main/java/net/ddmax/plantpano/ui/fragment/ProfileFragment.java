package net.ddmax.plantpano.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.ddmax.plantpano.R;
import net.ddmax.plantpano.base.BaseFragment;
import net.ddmax.plantpano.ui.activity.LoginActivity;
import net.ddmax.plantpano.ui.activity.NotificationActivity;
import net.ddmax.plantpano.ui.custom.SettingItem;

import butterknife.BindView;
import butterknife.OnClick;
import studios.codelight.smartloginlibrary.SmartLoginCallbacks;
import studios.codelight.smartloginlibrary.users.SmartUser;
import studios.codelight.smartloginlibrary.util.SmartLoginException;

import static android.app.Activity.RESULT_OK;

/**
 * @author ddMax
 * @since 17-3-2
 * 说明：
 */
public class ProfileFragment extends BaseFragment{
    public static final String TAG = ProfileFragment.class.getSimpleName();
    public static final int REQUEST_LOGIN = 0;

    @BindView(R.id.head_view) LinearLayout mHeadView;
    @BindView(R.id.image_head) ImageView mHeadImageView;
    @BindView(R.id.tv_head) TextView mHeadTextView;
    @BindView(R.id.item_notify) SettingItem mSettingNotify;
    @BindView(R.id.item_history) SettingItem mSettingHistory;
    @BindView(R.id.item_favorite) SettingItem mSettingFavorite;
    @BindView(R.id.item_settings) SettingItem mSettings;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void finishCreateView(Bundle savedInstanceState) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_LOGIN) {
            if (resultCode == RESULT_OK) {
                mHeadTextView.setText(data.getStringExtra("username"));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.head_view)
    public void startLogin() {
        startActivityForResult(new Intent(getActivity(), LoginActivity.class), REQUEST_LOGIN);
    }

    @OnClick(R.id.item_notify)
    public void startNotification() {
        startActivity(new Intent(getActivity(), NotificationActivity.class));
    }

    @OnClick(R.id.item_history)
    public void startHistory() {

    }

    @OnClick(R.id.item_favorite)
    public void startFavorite() {

    }

}
