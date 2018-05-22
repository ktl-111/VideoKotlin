package l.liubin.com.videokotlin.datebase;

import android.os.Parcel;
import android.os.Parcelable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import l.liubin.com.videokotlin.download.DownloadState;

/**
 * Created by steam_lb on 2018/5/20/020.
 */

@Table(database = DownloadDataBase.class) //上面自己创建的类（定义表的名称 版本）
public class DownloadModel extends BaseModel implements Parcelable {

    @PrimaryKey(autoincrement = true) //主键  //autoincrement 开启自增
    public int id;

    @Column               //表示一栏 一列
    public String title; //自己需要存储的字段

    @Column
    public String download_url;

    @Column
    public String img_url;

    @Column
    public String savepath;

    @Column
    public long currlength = 0;

    @Column
    public long totallength = 0;

    @Column
    public int state = DownloadState.INSTANCE.getSTATE_WAIT();

    public DownloadModel() {

    }

    protected DownloadModel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        download_url = in.readString();
        img_url = in.readString();
        savepath = in.readString();
        currlength = in.readLong();
        totallength = in.readLong();
        state = in.readInt();
    }

    public static final Creator<DownloadModel> CREATOR = new Creator<DownloadModel>() {
        @Override
        public DownloadModel createFromParcel(Parcel in) {
            return new DownloadModel(in);
        }

        @Override
        public DownloadModel[] newArray(int size) {
            return new DownloadModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(download_url);
        dest.writeString(img_url);
        dest.writeString(savepath);
        dest.writeLong(currlength);
        dest.writeLong(totallength);
        dest.writeInt(state);
    }
}
