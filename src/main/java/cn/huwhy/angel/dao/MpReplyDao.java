package cn.huwhy.angel.dao;

import org.apache.ibatis.annotations.Param;

import cn.huwhy.angel.ibatis.BaseDao;
import cn.huwhy.angel.po.MpReply;

public interface MpReplyDao extends BaseDao<MpReply, Integer> {

    MpReply getByKeyword(@Param("keyword") String keyword);
}
