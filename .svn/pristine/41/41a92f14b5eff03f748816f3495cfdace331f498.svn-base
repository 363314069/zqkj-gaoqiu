package com.zqkj.service;

import com.zqkj.entity.MembersverifyEntity;
import com.zqkj.utils.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * 会员验证表
 * @author zqkj
 * @email root@qq.com
 * @date 2020-10-12 15:32:13
 */
public interface MembersverifyService extends BaseService<MembersverifyEntity> {

    public Integer importData(MultipartFile file);

    /**
     * 重写插入，需要验证是否有重复
     *
     * @param record
     * @return
     */
    public int backSave(MembersverifyEntity record);


    /**
     * 重写修改，需要验证是否有重复
     *
     * @param record
     * @return
     */
    public int backUpdate(MembersverifyEntity record);

    /**
     * 重写插入，需要验证是否有重复
     *
     * @param entity
     * @return
     */
    public R verifyBin(MembersverifyEntity entity);
}
