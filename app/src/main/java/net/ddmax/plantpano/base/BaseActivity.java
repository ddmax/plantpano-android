package net.ddmax.plantpano.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author ddMax
 * @since 2017-02-28 08:35 PM
 * 说明：Activity基础类
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder bind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置布局内容
        setContentView(getLayoutId());
        // 绑定组件
        bind = ButterKnife.bind(this);
        // 初始化控件
        initViews(savedInstanceState);
        // 初始化ToolBar
        initToolBar();
    }


    public abstract int getLayoutId();

    public abstract void initViews(Bundle savedInstanceState);

    public abstract void initToolBar();


    public void loadData() {}


    public void showProgressBar() {}


    public void hideProgressBar() {}


    public void initRecyclerView() {}


    public void initRefreshLayout() {}


    public void finishTask() {}


    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
