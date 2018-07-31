package com.ahjswy.cn.utils;

import java.util.Iterator;
import java.util.List;

import com.ahjswy.cn.request.ReqSynUpdateInfo;

public class SwyUtils {
	public long getPagesFromUpdateInfo(List<ReqSynUpdateInfo> paramList, String paramString) {

		for (int i = 0; i < paramList.size(); i++) {
			if (paramList.get(i).getTableName().equals(paramString)) {
				return paramList.get(i).getPages();
			}
		}
		return 0;
	}

	// 获取 list 中 需要更新 的 pages
	public int getSumPagesFromUpdateInfo(List<ReqSynUpdateInfo> paramList) {
		int i = 0;
		Iterator<ReqSynUpdateInfo> localIterator = paramList.iterator();
		while (localIterator.hasNext()) {
			ReqSynUpdateInfo localReqSynUpdateInfo = (ReqSynUpdateInfo) localIterator.next();
			if ((!localReqSynUpdateInfo.getTableName().equals("rversion"))
					&& (!localReqSynUpdateInfo.getTableName().equals("pagesize"))) {
				i = (int) (i + localReqSynUpdateInfo.getPages());
			}

		}
		return i;
	}
}
