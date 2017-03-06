package net.ddmax.plantpano.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author ddMax
 * @since 17-3-6
 * 说明： Fragment基础类
 */
public abstract class BaseFragment extends Fragment {

    private View parentView;

    private FragmentActivity activity;

    private Unbinder bind;

    // Flag to set whether the fragment is visible
    protected boolean isVisible;

    public abstract @LayoutRes int getLayoutResId();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(getLayoutResId(), container, false);
        activity = getSupportActivity();
        return parentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = ButterKnife.bind(this, view);
        finishCreateView(savedInstanceState);
    }

    protected abstract void finishCreateView(Bundle savedInstanceState);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    private FragmentActivity getSupportActivity() {
        return super.getActivity();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {}

    protected void onInvisible() {}
}
