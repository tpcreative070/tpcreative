package co.tpcreative.media.manager;

import android.content.Context;

import java.io.File;

import co.tpcreative.media.configuration.AnncaConfiguration;
import co.tpcreative.media.configuration.ConfigurationProvider;
import co.tpcreative.media.manager.impl.CameraHandler;
import co.tpcreative.media.manager.impl.ParametersHandler;
import co.tpcreative.media.manager.listener.CameraCloseListener;
import co.tpcreative.media.manager.listener.CameraOpenListener;
import co.tpcreative.media.manager.listener.CameraPhotoListener;
import co.tpcreative.media.manager.listener.CameraVideoListener;
import co.tpcreative.media.utils.Size;

/**
 * Created by memfis on 8/14/16.
 */
public interface CameraManager<CameraId, SurfaceListener, CameraParameters, Camera> {

    void initializeCameraManager(ConfigurationProvider configurationProvider, Context context);

    void openCamera(CameraId cameraId, CameraOpenListener<CameraId, SurfaceListener> cameraOpenListener);

    void closeCamera(CameraCloseListener<CameraId> cameraCloseListener);

    void setFlashMode(@AnncaConfiguration.FlashMode int flashMode);

    void takePhoto(File photoFile, CameraPhotoListener cameraPhotoListener);

    void startVideoRecord(File videoFile, CameraVideoListener cameraVideoListener);

    Size getPhotoSizeForQuality(@AnncaConfiguration.MediaQuality int mediaQuality);

    void stopVideoRecord();

    void releaseCameraManager();

    CameraId getCurrentCameraId();

    CameraId getFaceFrontCameraId();

    CameraId getFaceBackCameraId();

    int getNumberOfCameras();

    int getFaceFrontCameraOrientation();

    int getFaceBackCameraOrientation();

    boolean isVideoRecording();

    boolean handleParameters(ParametersHandler<CameraParameters> parameters);

    void handleCamera(CameraHandler<Camera> cameraHandler);
}
