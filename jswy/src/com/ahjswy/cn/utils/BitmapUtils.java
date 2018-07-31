package com.ahjswy.cn.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Base64;

public class BitmapUtils {
	public BitmapUtils() {
		File localFile = new File(getPicDir());
		if (!localFile.exists())
			localFile.mkdirs();
	}

	// ERROR //
	public static Bitmap readBitmapAutoSize(String paramString, int paramInt1, int paramInt2) {
		return null;
		// Byte code:
		// 0: aconst_null
		// 1: astore_3

	}

	public static BitmapFactory.Options setBitmapOption(String paramString, int paramInt1, int paramInt2) {
		BitmapFactory.Options localOptions = new BitmapFactory.Options();
		localOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(paramString, localOptions);
		int i = localOptions.outWidth;
		int j = localOptions.outHeight;
		localOptions.inDither = false;
		localOptions.inPreferredConfig = Bitmap.Config.RGB_565;
		localOptions.inSampleSize = 1;
		if ((i != 0) && (j != 0) && (paramInt1 != 0) && (paramInt2 != 0))
			localOptions.inSampleSize = ((i / paramInt1 + j / paramInt2) / 2);
		localOptions.inJustDecodeBounds = false;
		return localOptions;
	}

	public byte[] Bitmap2Bytes(Bitmap paramBitmap) {
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, localByteArrayOutputStream);
		return localByteArrayOutputStream.toByteArray();
	}

	public Bitmap Bytes2Bimap(byte[] paramArrayOfByte) {
		if (paramArrayOfByte.length != 0)
			return BitmapFactory.decodeByteArray(paramArrayOfByte, 0, paramArrayOfByte.length);
		return null;
	}

	public String bitmaptoString(Bitmap paramBitmap) {
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, localByteArrayOutputStream);
		return Base64.encodeToString(localByteArrayOutputStream.toByteArray(), 0);
	}

	public String getOrCreateDir() {
		String localString = getPicDir();
		File localFile = new File(localString);
		if (!localFile.exists()) {
			localFile.mkdirs();
		}
		return localString;
	}

	public String getPicDir() {
		try {
			String sd = Environment.getExternalStorageDirectory().getAbsolutePath();
			return sd + "/" + "swy/swyxs" + "/goodspic";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Bitmap getPictureBitmap(String paramString) {
		return BitmapFactory.decodeFile(getPicDir() + "/" + paramString);
	}

	public boolean isExist(String paramString) {
		return new File(getPicDir() + "/" + paramString).exists();
	}

	public Bitmap reverseBitmap(Bitmap paramBitmap) {
		float[] arrayOfFloat = { -1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F };
		Matrix localMatrix = new Matrix();
		localMatrix.setValues(arrayOfFloat);
		Bitmap localBitmap = Bitmap.createBitmap(paramBitmap, 0, 0, paramBitmap.getWidth(), paramBitmap.getHeight(),
				localMatrix, true);
		paramBitmap.recycle();
		return localBitmap;
	}

	public Bitmap rotate(Bitmap paramBitmap, int paramInt) {
		Matrix localMatrix = null;
		if ((paramInt != 0) && (paramBitmap != null)) {
			localMatrix = new Matrix();
			localMatrix.setRotate(paramInt, paramBitmap.getWidth() / 2.0F, paramBitmap.getHeight() / 2.0F);
		}
		try {
			int i = paramBitmap.getWidth();
			int j = paramBitmap.getHeight();
			Bitmap localBitmap = Bitmap.createBitmap(paramBitmap, 0, 0, i, j, localMatrix, true);
			if (paramBitmap != localBitmap) {
				paramBitmap.recycle();
				paramBitmap = localBitmap;
			}
			return paramBitmap;
		} catch (OutOfMemoryError localOutOfMemoryError) {
		}
		return paramBitmap;
	}

	public boolean savePicture(Bitmap bitmap, String name) {
		File localFile = new File(getPicDir(), name);
		try {
			localFile.createNewFile();
			FileOutputStream os = new FileOutputStream(localFile);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, os);
			os.flush();
			os.close();
			bitmap.recycle();
			System.gc();
			return true;
		} catch (Exception localException) {
		}
		return false;
	}

	/**
	 * 保存图片
	 * 
	 * @param paramString1
	 * @param paramString2
	 * @return
	 */
	public boolean savePicture(String strImage, String picName) {
		FileOutputStream os = null;
		boolean v5 = false;
		if (TextUtils.isEmptyS(strImage)) {
			v5 = true;
		}
		File file = new File(this.getOrCreateDir(), picName);
		try {
			os = new FileOutputStream(file);
			file.createNewFile();
			os.write(Base64.decode(strImage, 0));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.flush();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return v5;
	}

	public Bitmap stringtoBitmap(String paramString) {
		try {
			byte[] arrayOfByte = Base64.decode(paramString, 0);
			Bitmap localBitmap = BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length);
			return localBitmap;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return null;
	}
}
