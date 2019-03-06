package com.example.pmprogect.fragment.home.multithreading;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pmprogect.BuildConfig;
import com.example.pmprogect.R;
import com.example.pmprogect.fragment.home.multithreading.download.DownloadHelper;
import com.example.pmprogect.fragment.home.multithreading.download.DownloadStatus;
import com.example.pmprogect.fragment.home.multithreading.download.bean.FileInfo;
import com.example.pmprogect.fragment.home.multithreading.download.config.InnerConstant;
import com.example.pmprogect.fragment.home.multithreading.download.utils.DebugUtils;
import com.example.pmprogect.fragment.home.multithreading.download.utils.Utils_Parse;
import com.example.pmprogect.util.PermissionUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 多线程下载Apk
 */
public class MultithreadingActivity extends AppCompatActivity {


    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.btn)
    Button mBtn;
    @BindView(R.id.deleteBtn)
    Button mDeleteBtn;

    private static final String TAG = "MultithreadingActivity";
    //淘宝 app 下载地址
    private static final String url = "http://ucan.25pp.com/Wandoujia_web_seo_baidu_homepage.apk";
//    private static final String url = "http://www.meituan.com/mobile/download/meituan/android/meituan?from=new";
//    private static final String url = "http://palm.zq12369.com/Common/android/apk/zzgd.apk";
    private File mFile;
    private static final String BC_ACTION = "download_helper_first_action";
    //    private String appName = "豌豆荚.apk";
    private String appName = "zzgd.apk";

    private static final String START = "开始";
    private static final String PAUST = "暂停";

    private DownloadHelper mDownloadHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multithreading);
        ButterKnife.bind(this);
        initData();
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != intent) {
                switch (intent.getAction()) {
                    case BC_ACTION: {
                        /**
                         * 我们接收到的FileInfo对象，包含了下载文件的各种信息。
                         * 然后我们就可以做我们想做的事情了。
                         * 比如更新进度条，改变状态等。
                         */
                        FileInfo fileInfo = (FileInfo) intent.getSerializableExtra(InnerConstant.EXTRA_INTENT_DOWNLOAD);

                        float pro = (float) (fileInfo.getDownloadLocation() * 1.0 / fileInfo.getSize());
                        int progress = (int) (pro * 100);
                        float downSize = fileInfo.getDownloadLocation() / 1024.0f / 1024;
                        float totalSize = fileInfo.getSize() / 1024.0f / 1024;

                        StringBuilder sb = new StringBuilder();
                        sb.append(appName);
                        sb.append(" 当前状态: " + DebugUtils.getStatusDesc(fileInfo.getDownloadStatus()) + " \n ");
                        sb.append(Utils_Parse.getTwoDecimalsStr(downSize) + "M/" + Utils_Parse.getTwoDecimalsStr(totalSize) + "M\n" + "( " + progress + "% )");
                        mTitle.setText(sb.toString());
                        mProgressBar.setProgress(progress);
                        if (fileInfo.getDownloadStatus() == DownloadStatus.COMPLETE) {
                            update();
                        }
                    }
                    break;
                }
            }
        }
    };

    private void checkPermission() {
        // 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，还要使用如下代码动态获取
        if (Build.VERSION.SDK_INT >= 23) {
            if (PermissionUtil.checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                checkIsAndroidO();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
            }
        } else {
            excutDownLoad();
        }
    }


    //注册
    private void initData() {
        mBtn.setText(START);
        mFile = new File(Environment.getExternalStorageDirectory(), appName);
        mDownloadHelper = DownloadHelper.getInstance();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BC_ACTION);
        registerReceiver(receiver, filter);
    }


    @OnClick({R.id.btn, R.id.deleteBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn:
                Log.i(TAG, "开始下载");
                checkPermission();
                break;
            case R.id.deleteBtn:
                if (mFile.exists()) {
                    boolean result = mFile.delete();
                    String resultStr = result ? "成功" : "失败";
                    Toast.makeText(this, "删除 mFile  " + resultStr, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "不存在 mFile ", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void excutDownLoad() {
        String content = mBtn.getText().toString().trim();
        if (TextUtils.equals(content, START)) {
            mBtn.setText(PAUST);
            mDownloadHelper.addTask(url, mFile, BC_ACTION).submit(MultithreadingActivity.this);
        } else if (TextUtils.equals(content, PAUST)) {
            mBtn.setText(START);
            mDownloadHelper.pauseTask(url, mFile, BC_ACTION).submit(MultithreadingActivity.this);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 107:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    excutDownLoad();
                } else {
                    Toast.makeText(this, "请打开未知应用安装权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        checkIsAndroidO();
                    } else {
                        excutDownLoad();
                    }
                } else {
                    Toast.makeText(this, "请打开读写权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    // 安装文件，一般固定写法
    private void update() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本  7.0以上版本适配
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", new File(Environment.getExternalStorageDirectory(), "zzgd.apk"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "zzgd.apk")), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivity(intent);
    }

    /**
     * 判断是否是8.0系统,是的话需要获取此权限，判断开没开，没开的话处理未知应用来源权限问题,否则直接安装
     */
    private void checkIsAndroidO() {
        if (Build.VERSION.SDK_INT >= 26) {
            //先获取是否有安装未知来源应用的权限
            if (getPackageManager().canRequestPackageInstalls()) {
                excutDownLoad();//安装应用的逻辑(写自己的就可以)
            } else {
                new AlertDialog.Builder(this).setMessage("下载应用需要打开未知来源权限，请去设置中开启权限")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    Uri packageURI = Uri.parse("package:" + getPackageName());
                                    //注意这个是8.0新API
                                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                                    startActivityForResult(intent, 107);
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MultithreadingActivity.this, "请打开未知应用安装权限", Toast.LENGTH_SHORT).show();
                            }
                        }).create().show();
            }
        } else {
            excutDownLoad();
        }
    }


}
