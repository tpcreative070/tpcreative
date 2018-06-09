package co.tpcreative.media.manager.listener;

import java.io.File;

import co.tpcreative.media.utils.Size;

/**
 * Created by memfis on 8/14/16.
 */
public interface CameraVideoListener {
    void onVideoRecordStarted(Size videoSize);

    void onVideoRecordStopped(File videoFile);

    void onVideoRecordError();
}
