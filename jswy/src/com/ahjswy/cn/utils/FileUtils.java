package com.ahjswy.cn.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.app.MyApplication;

public class FileUtils {
	public String filePath = MyApplication.getInstance().getFilesDir().getAbsolutePath() + "/";

	public <T> void saveStorageList(List<T> tArrayList, String fileName) {
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			File file = new File(filePath, fileName);
			fileOutputStream = new FileOutputStream(file.toString()); // 新建一个内容为空的文件
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(tArrayList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (objectOutputStream != null) {
			try {
				objectOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (fileOutputStream != null) {
			try {
				fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public <T> void saveStorageObject(Object obj, String fileName) {
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			File file = new File(filePath, fileName);
			if (file.exists()) {
				file.delete();
			}else{
//				file.mkdir();
			}
			file.createNewFile();
			fileOutputStream = new FileOutputStream(file.getAbsoluteFile()); // 新建一个内容为空的文件
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(obj);
			objectOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (objectOutputStream != null) {
			try {
				objectOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (fileOutputStream != null) {
			try {
				fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Object getStorageEntitiesObject(String fileName) {

		ObjectInputStream objectInputStream = null;
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(filePath + fileName);
			objectInputStream = new ObjectInputStream(fileInputStream);
			return objectInputStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (objectInputStream != null) {
					objectInputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> getStorageEntitiesList(String fileName) {
		ObjectInputStream objectInputStream = null;
		FileInputStream fileInputStream = null;
		ArrayList<T> savedArrayList = new ArrayList<>();
		try {
			fileInputStream = new FileInputStream(filePath);
			objectInputStream = new ObjectInputStream(fileInputStream);
			savedArrayList = (ArrayList<T>) objectInputStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (objectInputStream != null) {
					objectInputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return savedArrayList;
	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean deleteFile(String fileName) {
		File file = new File(filePath + fileName);
		if (file.exists()) {
			return file.delete();
		}
		return false;
	}
}
