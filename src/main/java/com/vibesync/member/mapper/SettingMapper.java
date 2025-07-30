package com.vibesync.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vibesync.member.domain.SettingVO;

public interface SettingMapper {

	/* setting 테이블 */
	// 기본 설정 추가
	int insertDefaultSetting(SettingVO setting);
	
	/* notification_settings 테이블 */
	// 기본 알림 설정들을 한번에 추가
	int insertDefaultNotificationSettings(
            @Param("settingIdx") int settingIdx, 
            @Param("notificationTypes") List<String> notificationTypes);
	
}
