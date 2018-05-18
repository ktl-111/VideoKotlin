package l.liubin.com.videokotlin.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import l.liubin.com.videokotlin.download.DownloadState;

/**
 * Created by l on 2018/5/18.
 */
@Entity
public class DownloadBean {
    @Id(autoincrement = true)
    private Long id;
    private String title;
    private String video_id;
    private String downloadUrl;
    private String savePath;
    private Long currByte;
    private int state = DownloadState.INSTANCE.getSTATE_PAUSE();

    @Generated(hash = 1640663391)
    public DownloadBean(Long id, String title, String video_id, String downloadUrl,
            String savePath, Long currByte, int state) {
        this.id = id;
        this.title = title;
        this.video_id = video_id;
        this.downloadUrl = downloadUrl;
        this.savePath = savePath;
        this.currByte = currByte;
        this.state = state;
    }

    @Generated(hash = 2040406903)
    public DownloadBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo_id() {
        return this.video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getDownloadUrl() {
        return this.downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getSavePath() {
        return this.savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Long getCurrByte() {
        return this.currByte;
    }

    public void setCurrByte(Long currByte) {
        this.currByte = currByte;
    }
}
