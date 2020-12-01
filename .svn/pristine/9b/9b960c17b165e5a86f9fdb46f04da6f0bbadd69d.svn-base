package com.zqkj.service.impl;

import com.zqkj.dao.mapper.InviteDao;
import com.zqkj.entity.InviteEntity;
import com.zqkj.entity.UserEntity;
import com.zqkj.service.InviteService;
import com.zqkj.service.UserService;
import com.zqkj.utils.BaseContentHandler;
import com.zqkj.utils.R;
import com.zqkj.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 邀请表
 */
@Service("inviteService")
public class InviteServiceImpl extends BaseServiceImpl<InviteDao, InviteEntity> implements InviteService {

    @Autowired
    private UserService userService;

    @Override
    public R binding(String invitedGuid) {
        //判断邀请人是否是当前用户
        if(invitedGuid.equals(BaseContentHandler.getUserGuid())){
            return R.ok().putData(1);
        }
        //如果邀请人不是当前用户，则判断用户是否已经被绑定   如果已经被绑定前端直接跳转列表页面不进行注册操作
        InviteEntity inviteEntity = new InviteEntity();
        inviteEntity.setBeInvitedGuid(BaseContentHandler.getUserGuid());
        int count = this.selectCount(inviteEntity);
        if(count > 0){
            //用户已经被绑定
            return R.ok().putData(count);
        }else{
            //用户未被绑定，判断用户是否已经注册
            UserEntity userEntity = userService.selectOneByGuid(BaseContentHandler.getUserGuid());
            if(StringUtil.isEmpty(userEntity.getPhone())){
                //用户未注册
                return R.ok().putData(0);
            }else{
                //用户已经注册，直接进行绑定
                /*//邀请表添加记录
                //修改邀请人用户邀请数量
                UserEntity userInviter = userService.selectOneByGuid(invitedGuid);
                if(userInviter.getInviteSum() != null){
                    if(userInviter.getInviteSum() == -1){
                        //-1代表可无限邀请
                        InviteEntity inviteSave = new InviteEntity();
                        inviteSave.setInvitedGuid(invitedGuid);//邀请人
                        inviteSave.setBeInvitedGuid(BaseContentHandler.getUserGuid());//被邀请人
                        inviteSave.setType(0);
                        inviteSave.setState(0);
                        this.insertSelective(inviteSave);
                    }else if(count >= userInviter.getInviteSum()){
                        //如果不是-1，则判断邀请人数限制是否大于，当前已邀请人数，如果大于这继续记录邀请记录，类型为1
                        InviteEntity inviteSave = new InviteEntity();
                        inviteSave.setInvitedGuid(invitedGuid);//邀请人
                        inviteSave.setBeInvitedGuid(BaseContentHandler.getUserGuid());//被邀请人
                        inviteSave.setType(1);
                        inviteSave.setState(0);
                        this.insertSelective(inviteSave);
                    }else {
                        //以上条件都不满足则为正常邀请流程
                        InviteEntity inviteSave = new InviteEntity();
                        inviteSave.setInvitedGuid(invitedGuid);//邀请人
                        inviteSave.setBeInvitedGuid(BaseContentHandler.getUserGuid());//被邀请人
                        inviteSave.setType(0);
                        inviteSave.setState(0);
                        this.insertSelective(inviteSave);
                    }
                }else{
                    InviteEntity inviteSave = new InviteEntity();
                    inviteSave.setInvitedGuid(invitedGuid);//邀请人
                    inviteSave.setBeInvitedGuid(BaseContentHandler.getUserGuid());//被邀请人
                    inviteSave.setType(0);
                    inviteSave.setState(0);
                    this.insertSelective(inviteSave);
                    userInviter.setInviteSum(-1);
                    userService.updateByPrimaryKeySelective(userInviter);
                }*/
                return R.ok().putData(1);
            }
        }
    }
}
