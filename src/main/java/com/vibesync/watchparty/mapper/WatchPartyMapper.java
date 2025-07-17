package com.vibesync.watchparty.mapper;


import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.vibesync.watchparty.domain.WatchPartyVO;

public interface WatchPartyMapper {
    List<WatchPartyVO> selectAll();

    List<WatchPartyVO> selectByHost(@Param("hostIdx") int hostIdx);

    int insert(WatchPartyVO wp);

    WatchPartyVO selectOne(@Param("watchPartyIdx") int watchPartyIdx);

    WatchPartyVO selectLatestByUniqueFields(@Param("title") String title,
                                             @Param("videoId") String videoId,
                                             @Param("host") int host);

    int checkExit(@Param("hostIdx") int hostIdx);

    int deleteByHost(@Param("hostIdx") int hostIdx);
}
