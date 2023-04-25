package com.sum.user.manager

import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import com.sum.framework.helper.SumAppHelper
import com.sum.framework.log.LogUtil
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * @author mingyan.su
 * @date   2023/4/24 18:08
 * @desc   文件管理类
 */
object FileManager {
    // 媒体模块根目录
    private val SAVE_MEDIA_ROOT_DIR = Environment.DIRECTORY_DCIM

    // 媒体模块存储路径
    private val SAVE_MEDIA_DIR: String = SAVE_MEDIA_ROOT_DIR + "/SumTea"
    private val AVATAR_DIR = "/avatar"

    // JPG后缀
    val JPG_SUFFIX = ".jpg"

    // PNG后缀
    val PNG_SUFFIX = ".png"

    // MP4后缀
    val MP4_SUFFIX = ".mp4"

    // YUV后缀
    val YUV_SUFFIX = ".yuv"

    // h264后缀
    val H264_SUFFIX = ".h264"

    /**
     * 获取jpg图片输出路径
     *
     * @return 输出路径
     */
    fun getHeadJpgFile(): Any? {
        val timeStamp = SimpleDateFormat("yyyy-MM-dd_HH:mm", Locale.CHINA).format(Date())
        val fileName: String = "${timeStamp}$JPG_SUFFIX"
        val headPath: String = getAvatarPath(fileName)
        val imgFile: File
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            imgFile = File(headPath)
            // 通过 MediaStore API 插入file 为了拿到系统裁剪要保存到的uri（因为App没有权限不能访问公共存储空间，需要通过 MediaStore API来操作）
            val values = ContentValues()
            values.put(MediaStore.Images.Media.DATA, imgFile.absolutePath)
            values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            return SumAppHelper.getApplication().contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            )
        } else {
            imgFile = File(headPath)
        }
        return imgFile
    }

    /**
     * 通过媒体文件Uri获取文件-Android 11兼容
     *
     * @param fileUri 文件Uri
     */
    fun getMediaUri2File(fileUri: Uri): File? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = SumAppHelper.getApplication().contentResolver.query(
            fileUri, projection,
            null, null, null
        )
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                val path = cursor.getString(columnIndex)
                cursor.close()
                return File(path)
            }
        }
        return null
    }

    /**
     * 头像地址
     */
    fun getAvatarPath(fileName: String): String {
        val path: String? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getFolderDirPath(SAVE_MEDIA_DIR + AVATAR_DIR)
        } else {
            getSaveDir(AVATAR_DIR)
        }
        return path + File.separator + fileName
    }

    fun getFolderDirPath(dstDirPathToCreate: String): String? {
        val dstFileDir = File(Environment.getExternalStorageDirectory(), dstDirPathToCreate)
        if (!dstFileDir.exists() && !dstFileDir.mkdirs()) {
            return null
        }
        return dstFileDir.absolutePath
    }

    /**
     * 获取具体模块存储目录
     */
    fun getSaveDir(directory: String): String {
        var path = ""
        path = if (TextUtils.isEmpty(directory) || "/" == directory) {
            ""
        } else if (directory.startsWith("/")) {
            directory
        } else {
            "/$directory"
        }
        path = getAppRootDir() + path
        val file = if (isSpace(path)) null else File(path)
        createOrExistsDir(file)
        return path
    }

    /**
     * 获取App存储根目录
     */
    fun getAppRootDir(): String {
        val path: String = getStorageRootDir()
        val file = if (isSpace(path)) null else File(path)
        LogUtil.i("getAppRootDir:$path")
        createOrExistsDir(file)
        return path
    }

    fun createOrExistsDir(file: File?): Boolean {
        return file != null && if (file.exists()) file.isDirectory else file.mkdirs()
    }

    private fun isSpace(s: String?): Boolean {
        if (s == null) return true
        var i = 0
        val len = s.length
        while (i < len) {
            if (!Character.isWhitespace(s[i])) {
                return false
            }
            ++i
        }
        return true
    }

    /**
     * 获取文件存储根目录
     */
    fun getStorageRootDir(): String {
        val filePath: File? = SumAppHelper.getApplication().getExternalFilesDir("")
        val path: String = if (filePath != null) {
            filePath.absolutePath
        } else {
            SumAppHelper.getApplication().filesDir.absolutePath
        }
        LogUtil.i("getStorageRootDir:$path")
        return path
    }
}