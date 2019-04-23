package com.xb.visitor.moudle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.media.FaceRecognizer;
import android.os.*;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.hao.lib.base.MI2Activity;
import com.xb.visitor.FaceOverlayView;
import com.xb.visitor.FaceUtil.FaceUtils;
import com.xb.visitor.R;
import com.xb.visitor.base.App;
import com.xb.visitor.db.manage.DBManager;
import com.xb.visitor.entity.FaceInfo;
import com.xb.visitor.entity.Feature;

import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class MainActivity extends MI2Activity implements SurfaceHolder.Callback, Camera.FaceDetectionListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int DEFAULT_CAMERA_ID = 0;

    private static final int MSG_CLEAR_FACE_RECT = 1;

    public Camera mCamera;
    private Toast mToast;
    private FaceOverlayView mFaceOverlay;
    private ImageView mDetectingImage;
    private ImageView mDetectedImage;
    public ImageView in_image;
    private TextView mDetectedImageInfo;
    private View mLoadingView;
    private Handler mHandler;
    private byte[] mPreviewFrameBuffer;
    private byte[] mByteCache;

    // 根据已知人物图像生成对应的特征数据.
    // 由于生成特征数据是一个耗时的过程,实际使用中需要提前生成好,然后持久化保存.
    // 下次启动直接加载,对于新的人物做增量更新即可.
    public FaceRecognizer fr = new FaceRecognizer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_show);
        App.getInstance().addActivity(this);
        initView();
        initData();

        findViewById(R.id.add_face).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                try {
                    tag++;
                    if (App.getInstance().getmBinder() != null)
                        App.getInstance().getmBinder().transact(tag % 2, data, reply, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.add_face1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Feature> features = DBManager.checkAllFeature();
                List<FaceRecognizer.Feature> usefeatures = new ArrayList<>();
                List<String> names = new ArrayList<>();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                for (int i = 0; i < features.size(); ++i) {
                    usefeatures.add(FaceUtils.toFaceRecognizerFeature(features.get(i)));
                    names.add("图片" + i);
                    Log.i("添加特征", "图片" + i);
                }
                Utils.setFeatures(mCamera, usefeatures, names);
            }
        });

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                FaceRecognizer fr = new FaceRecognizer();
//                List<String> picPaths = FileUtils.getAssetPicPath(App.getInstance());
//                Log.i("解析人脸", "开始解析 图片数量=" + picPaths.size());
//                for (int i = 0; i < picPaths.size(); i++) {
//                    try {
//                        Bitmap bitmap = FileUtils.getAssetsBitmap(App.getInstance(), picPaths.get(i));
//                        Log.i("解析人脸", "开始解析 图片" + i);
//                        FaceRecognizer.Feature feature = fr.extractFeature(bitmap);
//                        Feature locFeature = FaceUtils.getLocFeature(feature);
//                        locFeature.setOpenid(String.valueOf(i));
//                        DBManager.checkFeature(locFeature);
//                        Log.i("解析人脸", "解析完成 图片" + i);
//                    } catch (Exception e) {
//                        Log.i("问题数据", "图片" + i);
//                    }
//                }
//            }
//        }).start();
    }

    @Override
    protected void initDrawView(View view) {

    }

    int tag = 1;

    private void initView() {
        mDetectingImage = findViewById(R.id.detecting_image);
        mDetectedImage = findViewById(R.id.detected_image);
        mDetectedImageInfo = findViewById(R.id.detected_image_info);
        mFaceOverlay = findViewById(R.id.face_overlay);
        mLoadingView = findViewById(R.id.loading_view);
        in_image = findViewById(R.id.in_image);

        SurfaceView surfaceView = findViewById(R.id.surface_view);

        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(this);
        holder.setFormat(ImageFormat.NV21);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initData() {
        mHandler = new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_CLEAR_FACE_RECT:
                        mFaceOverlay.update(null);
                        break;
                }
                return true;
            }
        });
        mByteCache = new byte[400 * 400 * 2];

        // 特征数据加载完成,开启摄像头预览,然后将数据设置到Camera
        new GenFeatureTask(this).execute();
    }

    private void openCamera(SurfaceHolder holder) {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int cameraId = DEFAULT_CAMERA_ID;
        try {
            mCamera = Camera.open();
            mCamera.startPreview();
            mLoadingView.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
            showToast("Open camera  failed:" + e.getMessage());
            return;
        }

        mCamera.setFaceDetectionListener(this);
        Camera.getCameraInfo(cameraId, cameraInfo);
        try {
            mCamera.setPreviewDisplay(holder);
        } catch (Exception e) {
            Log.e(TAG, "set preview display failed:" + e.getMessage());
        }

        configureFaceView();
    }

    private void closeCamera() {
        if (mCamera != null) {
            mCamera.stopFaceDetection();
            mCamera.stopPreview();
            mCamera.setPreviewCallbackWithBuffer(null);
            mCamera.setErrorCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private void configureFaceView() {
        Camera.Parameters parameters = mCamera.getParameters();
        Camera.Size previewSize = parameters.getPreviewSize();
        int previewWidth, previewHeight;
        if (previewSize != null) {
            previewWidth = previewSize.width;
            previewHeight = previewSize.height;
        } else {
            Log.w(TAG, "preview size is null, assume it is 720p");
            previewWidth = 1080;
            previewHeight = 720;
        }

        Log.d(TAG, "camera preview size:("
                + previewWidth + "x" + previewHeight + ")("
                + parameters.getPreviewFormat() + ")");

        mFaceOverlay.setPreviewSize(previewWidth, previewHeight);
        mPreviewFrameBuffer = new byte[previewWidth * previewHeight * 3];
    }

    private void startPreview() {
        mCamera.startPreview();
        mCamera.startFaceDetection();

        mCamera.addCallbackBuffer(mPreviewFrameBuffer);
        mCamera.setPreviewCallbackWithBuffer(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(final byte[] data, Camera camera) {
                // 解析人脸识别结果
                // 人脸识别结果以二进制形式保存在data中
                // 内存布局如下(括号中为字段占用字节数):
                // | 结果个数(1) | 第1个结果 | ... | 第n个结果 |
                // 每个结果的内存布局如下:
                // | id(4) | score(4) | width(4) | height(4) | time(4) | image data(width*height*2) |
                // id: 调用setFeatures时设置的id数组
                // score: 识别结果确信度, 0-1000
                // width: 图像长(单位为像素)
                // height: 图像宽(单位为像素)
                // time: 识别过程耗费时间(单位为ms)
                // image data: 图像数据(RGB565,每个像素占用2字节)

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        int numberOfFaces = data[0];
                        if (numberOfFaces <= 0) {
                            Log.w(TAG, "Recognized nothing, skip this frame!");
                            return;
                        }
                        parseFRResult(numberOfFaces, data);
                    }
                });
                mCamera.addCallbackBuffer(mPreviewFrameBuffer);
            }
        });
    }

    private void parseFRResult(int count, byte[] data) {
        int offset = 1;
        for (int i = 0; i < count; i++) {
            int id = Utils.byte2int(data, offset);
            int score = Utils.byte2int(data, offset + 4);
            int width = Utils.byte2int(data, offset + 8);
            int height = Utils.byte2int(data, offset + 12);
            int time = Utils.byte2int(data, offset + 16);
            offset += 20;

            if (width <= 0 || height <= 0) {
                Log.e(TAG, "invalid fr result(" + width + "x" + height + ")");
                return;
            }

            int length = width * height * 2;
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            System.arraycopy(data, offset, mByteCache, 0, length);
            ByteBuffer buffer = ByteBuffer.wrap(mByteCache, 0, length);
            bitmap.copyPixelsFromBuffer(buffer);

            offset += length;

            if (id < 0) {
                // 当id为-1时,表示正在识别的人脸
                // 可以根据需要更新
                mDetectingImage.setImageBitmap(bitmap);
            } else {
                // 当id大于0时,表示识别完成的人脸
                mDetectedImage.setImageBitmap(bitmap);
                if (Utils.getName(id).length() > 10) {
                    FaceInfo faceInfo = DBManager.checkFaceInfo(Utils.getName(id));
                    mDetectedImageInfo.setText(faceInfo.getName());
                } else {
                    mDetectedImageInfo.setText(Utils.getName(id));
                }
            }
        }
    }

    private void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            mToast.show();
        } else {
            mToast.cancel();
            mToast.setText(msg);
            mToast.show();
        }
    }

    // ============================================================
    // SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i("流程", "打开相机");
        openCamera(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        closeCamera();
    }

    // ============================================================
    // Camera.FaceDetectionListener
    @Override
    public void onFaceDetection(final Camera.Face[] faces, Camera camera) {
        mHandler.removeMessages(MSG_CLEAR_FACE_RECT);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                // 根据检测结果更新检测框
                mFaceOverlay.update(faces);
            }
        });
        mHandler.sendEmptyMessageDelayed(MSG_CLEAR_FACE_RECT, 500);
    }

    private class GenFeatureTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<MainActivity> mActivity;
        private List<String> mNames;
        private List<FaceRecognizer.Feature> mFeatures;

        GenFeatureTask(MainActivity activity) {
            mActivity = new WeakReference<>(activity);
            mFeatures = new ArrayList<>();
            mNames = new ArrayList<>();
        }

        @Override
        protected void onPreExecute() {
            if (mActivity.get() != null) {
                mActivity.get().mLoadingView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {

            int[] resIds = new int[]{R.drawable.wangyadong2, R.drawable.liuke2, R.drawable.zhanghao2, R.drawable.huangcuilan2, R.drawable.mahuateng2,
                    R.drawable.wangyadong, R.drawable.liuke1, R.drawable.zhanghao1, R.drawable.huangcuilan1, R.drawable.mahuateng,
                    R.drawable.huangmu1, R.drawable.huangmu2, R.drawable.huangmu3, R.drawable.huangmu4, R.drawable.huangmu5,
                    R.drawable.huangmu6};
            String[] names = new String[]{"王亚东", "刘科", "张浩", "黄翠兰", "马化腾", "王亚东2", "刘科2", "张浩2", "黄翠兰2", "马化腾2", "黄睦1", "黄睦2", "黄睦3", "黄睦4", "黄睦5", "黄睦6"};


            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            for (int i = 0; i < resIds.length; ++i) {
                if (mActivity.get() == null) return null;
                Bitmap bitmap = BitmapFactory.decodeResource(mActivity.get().getResources(), resIds[i], options);
                Log.i("解析人脸", "开始解析 图片" + i);
                FaceRecognizer.Feature feature = fr.extractFeature(bitmap);
                Log.i("解析人脸", "解析完成 图片" + i);
                if (feature != null) {
                    mFeatures.add(feature);
                    mNames.add(names[i]);
                } else {
                    Log.w(TAG, "gen feature failed for:" + names[i]);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (mActivity.get() != null) {
                mActivity.get().mLoadingView.setVisibility(View.INVISIBLE);
                // 特征数据加载完成,开启摄像头预览,然后将数据设置到Camera
                mActivity.get().startPreview();
                Log.i("解析人脸", "添加特征");
                Utils.setFeatures(mActivity.get().mCamera, mFeatures, mNames);
                Log.i("解析人脸", "特征添加完成");
            }
        }
    }
}
