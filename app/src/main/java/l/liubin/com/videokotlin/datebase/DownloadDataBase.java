package l.liubin.com.videokotlin.datebase;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by steam_lb on 2018/5/20/020.
 */

@Database(name = DownloadDataBase.NAME, version = DownloadDataBase.VERSION)
public class DownloadDataBase {
    //数据库名称
    public static final String NAME = "DownloadDataBase";
    //数据库版本
    public static final int VERSION = 1;
}

