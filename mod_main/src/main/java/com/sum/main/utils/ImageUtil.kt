package com.sum.main.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import com.sum.common.manager.FileManager
import com.sum.framework.helper.SumAppHelper
import com.sum.framework.log.LogUtil
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.URLConnection

/**
 * @author mingyan.su
 * @date   2023/4/26 08:08
 * @desc   图片工具类
 */
object ImageUtil {

    const val TAG = "ImageUtil"

    /**
     * 获取View的bitmap对象
     */
    fun viewToBitmap(view: View, config: Bitmap.Config = Bitmap.Config.ARGB_8888): Bitmap {
        val screenshot: Bitmap =
            Bitmap.createBitmap(view.width, view.height, config)
        val canvas = Canvas(screenshot)
        view.draw(canvas)
        return screenshot
    }

    /**
     * 判断bitmap对象是否为空
     *
     * @param src 源图片
     * @return `true`: 是<br></br>`false`: 否
     */
    private fun isEmptyBitmap(src: Bitmap?): Boolean {
        return src == null || src.width == 0 || src.height == 0
    }

    /**
     * 保存图片
     *
     * @param src     源图片
     * @param file    要保存到的文件
     * @param format  格式
     * @param recycle 是否回收
     * @return `true`: 成功<br></br>`false`: 失败
     */
    fun save(src: Bitmap, file: File?, format: CompressFormat?, vararg recycle: Boolean): Boolean {
        if (isEmptyBitmap(src) || !FileManager.createOrExistsFile(file)) return false
        println(src.width.toString() + ", " + src.height)
        var os: OutputStream? = null
        var ret = false
        var isInsertSystemPictures = true //是否添加到系统相册
        if (recycle.size > 1) {
            isInsertSystemPictures = recycle[1]
        }
        try {
            os = BufferedOutputStream(FileOutputStream(file))
            ret = src.compress(format, 90, os)
            if (recycle[0] && !src.isRecycled) src.recycle()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                os?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (isInsertSystemPictures) {
            val out: FileOutputStream
            try {
                out = FileOutputStream(file)
                // 格式为 JPEG，照相机拍出的图片为JPEG格式的，PNG格式的不能显示在相册中
                if (src.compress(CompressFormat.JPEG, 90, out)) {
                    out.flush()
                    out.close()
                    // 插入图库
                    insertSystemImage(file)
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                LogUtil.i("FileNotFoundException", tag = TAG)
            } catch (e: IOException) {
                e.printStackTrace()
                LogUtil.i("IOException", tag = TAG)
            }
        }
        return ret
    }

    /**
     * 插入到系统相册图库
     *
     * @param file
     */
    fun insertSystemImage(file: File?): String? {
        if (file == null) {
            LogUtil.i("file==null", tag = TAG)
            return ""
        }
        val bitName = file.name
        LogUtil.i("bitName===$bitName | file.getAbsolutePath:${file.absolutePath} | path== ${file.path}", tag = TAG)
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val mimeType: String? = getMimeType(file)
                val fileName = file.name
                val values = ContentValues()
                values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
                val contentResolver: ContentResolver =
                    SumAppHelper.getApplication().applicationContext.contentResolver
                val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                if (uri == null) {
                    LogUtil.i("图片保存失败", tag = TAG)
                    return ""
                }
                try {
                    val out = contentResolver.openOutputStream(uri)
                    val fis = FileInputStream(file)
                    copyStreamFile(fis, out)
                    fis.close()
                    out?.close()
                    val resultPath =
                        Environment.getExternalStorageDirectory().absolutePath + "/" + Environment.DIRECTORY_DCIM + "/" + fileName
                    LogUtil.i("图片保存成功:$resultPath", tag = TAG)
                    resultPath
                } catch (e: IOException) {
                    LogUtil.i("图片保存失败", tag = TAG)
                    e.printStackTrace()
                    ""
                }
            } else {
                // 其次把文件插入到系统图库
                //MediaStore.Images.Media.insertImage(mContext.getContentResolver(), file.getAbsolutePath(), fileName, null);
                val values = ContentValues()
                values.put(MediaStore.Images.Media.DATA, file.absolutePath)
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                val uri: Uri? = SumAppHelper.getApplication().applicationContext.contentResolver
                        .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                // 最后通知图库更新
                SumAppHelper.getApplication().applicationContext.sendBroadcast(
                    Intent(
                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        Uri.parse("file://" + file.absolutePath)
                    )
                )
                file.absolutePath
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    /**
     * 获取文件mimeType
     *
     * @param file
     * @return
     */
    fun getMimeType(file: File): String? {
        val fileNameMap = URLConnection.getFileNameMap()
        return fileNameMap.getContentTypeFor(file.name)
    }

    /**
     * 文件拷贝到指定的stream
     *
     * @param fis
     * @param out
     */
    fun copyStreamFile(fis: InputStream, out: OutputStream?) {
        try {
            val buffer = ByteArray(1024)
            var byteRead: Int
            while (-1 != fis.read(buffer).also { byteRead = it }) {
                out?.write(buffer, 0, byteRead)
            }
            fis.close()
            out?.flush()
            out?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}