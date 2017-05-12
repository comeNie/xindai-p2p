package com.qfw.platform.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

import com.qfw.common.bizservice.ICodeDictBS;
import com.qfw.common.model.SysCodeDict;
import com.qfw.platform.model.bo.CmsFriendLink;
import com.qfw.platform.model.bo.CmsNavigation;
import com.qfw.platform.service.ICmsFriendlinkService;
import com.qfw.platform.service.ICmsNavigationService;

/**
 * 缓存管理器
 * 
 * @author Administrator
 * 
 */
public class CacheManager {

	private ICodeDictBS codeDictBS;

	private ICmsFriendlinkService cmsFriendlinkService;
	
	private ICmsNavigationService cmsNavigationService;
	

	/**
	 * 数据字典Map
	 */
	private static Map<String, List<SysCodeDict>> sysCodeDictMap = new ConcurrentHashMap<String, List<SysCodeDict>>();

	/**
	 * 友情链接
	 */
	private List<CmsFriendLink> friendLinkList = null;
	
	/**
	 * 前台轮播图片
	 */
	private List<CmsFriendLink> carouselList = null;
	
	private List<CmsNavigation> topNavigationList = null;
	
	private List<CmsNavigation> bottomNavigationList = null;
	

	public CacheManager() {
	}

	public void setCodeDictBS(ICodeDictBS codeDictBS) {
		this.codeDictBS = codeDictBS;
		refreshCodeDict();
	}

	public void setCmsFriendlinkService(
			ICmsFriendlinkService cmsFriendlinkService) {
		this.cmsFriendlinkService = cmsFriendlinkService;
		refreshFriendlink();
	}
	
	public void setCmsNavigationService(
			ICmsNavigationService cmsNavigationService) {
		this.cmsNavigationService = cmsNavigationService;
		refreshNavigationList();
	}

	/**
	 * 刷新数据字典
	 */
	public void refreshCodeDict() {
		List<SysCodeDict> sysCodeDictList = codeDictBS.findAllSysCodeDicts();
		Map<String, List<SysCodeDict>> tmpSysCodeDictMap = new HashMap<String, List<SysCodeDict>>();
		if (null != sysCodeDictList && !sysCodeDictList.isEmpty()) {
			for (SysCodeDict sysCodeDict : sysCodeDictList) {
				if (null != sysCodeDict) {
					String codeType = sysCodeDict.getCodeType();
					List<SysCodeDict> tmpSysCodeDictList = tmpSysCodeDictMap
							.get(codeType);
					if (null == tmpSysCodeDictList) {
						tmpSysCodeDictList = new ArrayList<SysCodeDict>();
						tmpSysCodeDictMap.put(codeType, tmpSysCodeDictList);
					}
					tmpSysCodeDictList.add(sysCodeDict);
				}
			}
		}

		if (null != tmpSysCodeDictMap && !tmpSysCodeDictMap.isEmpty()) {
			sysCodeDictMap = tmpSysCodeDictMap;
		}
	}

	/**
	 * 刷新友情链接
	 * 首页轮播图片
	 */
	public void refreshFriendlink() {
		friendLinkList = cmsFriendlinkService.getFriendLinkList();
		carouselList =cmsFriendlinkService.getCarouselList();
	}

	
	/**
	 * 刷新导航栏
	 */
	public void refreshNavigationList() {
		topNavigationList = cmsNavigationService.getTopNavigationList();
		bottomNavigationList = cmsNavigationService.getBottomNavigationList();
	}

	public String getDisPlayNameByCodeTypeAndVal(String codeType,
			String codeValue) {
		if (StringUtils.isEmpty(codeType) || StringUtils.isEmpty(codeValue))
			return null;
		List<SysCodeDict> tmpSysCodeDictList = sysCodeDictMap.get(codeType);
		if (null != tmpSysCodeDictList && !tmpSysCodeDictList.isEmpty()) {
			for (SysCodeDict sysCodeDict : tmpSysCodeDictList) {
				String tmpCodeVal = sysCodeDict.getCodeValue();
				if (codeValue.equals(tmpCodeVal)) {
					return sysCodeDict.getDisplayValue();
				}
			}
		}
		return null;
	}
	
	public List<SysCodeDict> getCodeList(String codeType) {
		if (StringUtils.isEmpty(codeType))
			return null;
		List<SysCodeDict> tmpSysCodeDictList = sysCodeDictMap.get(codeType);
		return tmpSysCodeDictList;
	}
	
	/**
	 * 将Cd值替换成文字显示
	 * @param list
	 * @param changeColMap
	 * @return
	 */
	public List<Map<String,Object>> changeListDisplayName(List<Map<String,Object>> list,Map<String,String> changeColMap){
		String colVal = "";
		List<Map<String,Object>> newList = new ArrayList<Map<String,Object>>();
		
		for(Map<String,Object> map : list){
			Iterator iter = changeColMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String key = entry.getKey().toString();
				String val = entry.getValue().toString();
				
				colVal = map.get(key)==null?"":map.get(key).toString();
				if( !"".equals(colVal) ){
					map.remove(key);
					map.put(key, getDisPlayNameByCodeTypeAndVal(val,
							colVal));
				}
			}
			newList.add(map);
		}
		return newList;
	}
	
	/**
	 * 获取友情链接
	 * @return
	 */
	public List<CmsFriendLink> getFriendLinkList() {
		return friendLinkList;
	}

	public List<CmsNavigation> getTopNavigationList() {
		return topNavigationList;
	}

	public List<CmsNavigation> getBottomNavigationList() {
		return bottomNavigationList;
	}

	public List<CmsFriendLink> getCarouselList() {
		return carouselList;
	}

	public void setCarouselList(List<CmsFriendLink> carouselList) {
		this.carouselList = carouselList;
	}
}
