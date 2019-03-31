package com.iwxyi.Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SqlUtil {

    private static MyOpenHelper myOpenHelper;
    private static SQLiteDatabase db;
    private static boolean inited = false;
    private static Context _context = null;
    private final static String[] fields = new String[] {
            "bills",
            "cards",
            "kinds_spending",
            "kinds_income",
            "kinds_borrowing"
    };

    /**
     * 数据库工具类初始化
     * @param context 上下文
     */
    public static void init(Context context) {
        _context = context;
        inited = true;

        myOpenHelper = new MyOpenHelper(context);
        db = myOpenHelper.getWritableDatabase();
    }

    /**
     * 备份所有数据到数据库
     */
    public static void backupToSql() {
        for (int i = 0; i < fields.length; i++) {
            backupToSql(fields[i]);
        }
    }

    /**
     * 备份某一项名字的文件到数据库
     * @param field 文件名（不包括后缀名）（后缀名是txt）
     */
    public static void backupToSql(String field) {
        if (!inited) return ;
        String s = FileUtil.readTextVals(field + ".txt");
        Log.i("==== backup to sql:" + field, s);
        s = java.net.URLEncoder.encode(s);
        db.execSQL("UPDATE poorrow set content = '" + s + "' where field = '" + field + "'");
    }

    /**
     * 从数据库中恢复数据
     */
    public static void restoreFromSql() {
        if (!inited) return ;
        Cursor cursor = db.rawQuery("SELECT * from poorrow", null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String field = cursor.getString(1);
                String content = cursor.getString(2);
                content = java.net.URLDecoder.decode(content);
                if (!"".equals(content))
                {
                    if (FileUtil.exist(field + ".txt")) {
                        FileUtil.writeTextVals(field+".txt", content);
                    }
                }
                Log.i("==== restore from sql:" + field, content);
            }
        }
    }
}
