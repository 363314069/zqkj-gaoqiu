package com.zqkj.service.impl;

import com.zqkj.utils.DateUtils;
import org.springframework.stereotype.Service;

import com.zqkj.dao.mapper.ReservationdateDao;
import com.zqkj.entity.ReservationdateEntity;
import com.zqkj.service.ReservationdateService;

import java.text.ParseException;
import java.util.*;


/**
 * 
 * 
 * @author zqkj
 * @email root@qq.com
 * @date 2020-01-14 11:09:36
 */
@Service("reservationdateService")
public class ReservationdateServiceImpl extends BaseServiceImpl<ReservationdateDao, ReservationdateEntity> implements ReservationdateService {

    @Override
    public List<ReservationdateEntity> selectReservationdateList(ReservationdateEntity reservationdateEntity) {
        return dao.selectReservationdateList(reservationdateEntity);
    }

    @Override
    public List<ReservationdateEntity> updateReservationdateList(List<ReservationdateEntity> reservationdateList) {
        List<ReservationdateEntity> list = new ArrayList<>();
        for(ReservationdateEntity reservationdate : reservationdateList){
            if(reservationdate.getId() != null){
                int updateInt = updateByPrimaryKeySelective(reservationdate);
                if(updateInt <= 0){
                    list.add(reservationdate);
                }
            }else{
                int insertInt = insertSelective(reservationdate);
                if(insertInt <= 0){
                    list.add(reservationdate);
                }
            }
        }
        return list;
    }

    @Override
    public List<ReservationdateEntity> updateListWeekend(ReservationdateEntity reservationdateEntity) {
        List<ReservationdateEntity> dateList = new ArrayList<ReservationdateEntity>();
        try {
            Calendar c_begin = new GregorianCalendar();
            Calendar c_end = new GregorianCalendar();
            Date stateTime = DateUtils.parseDatetime(reservationdateEntity.getStartTime());//起始时间
            Date endTime = DateUtils.parseDatetime(reservationdateEntity.getEndTime());//结束时间
            c_begin.setTime(stateTime);
            c_end.setTime(endTime);
            //c_begin.set(2017, 3, 29); //Calendar的月从0-11，所以4月是3.
            //c_end.set(2017, 4, 12); //Calendar的月从0-11，所以5月是4.
            c_end.add(Calendar.DAY_OF_YEAR, 1);  //结束日期下滚一天是为了包含最后一天

            while(c_begin.before(c_end)){
                if(c_begin.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || c_begin.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){//匹配周六和周日的日期
                    ReservationdateEntity newEntity = new ReservationdateEntity();
                    newEntity.setDate(new java.sql.Date(c_begin.getTime().getTime()).toString());
                    newEntity.setType(reservationdateEntity.getType());
                    newEntity.setState(reservationdateEntity.getState());
                    newEntity.setIntroductionGuid(reservationdateEntity.getIntroductionGuid());
                    newEntity.setTimeJson(reservationdateEntity.getTimeJson());
                    dateList.add(newEntity);
                }
                c_begin.add(Calendar.DAY_OF_YEAR, 1);
            }
            System.out.println(dateList);

            //查询这些日期在数据库中是否有记录，有记录进行修改，没有进行插入
            for(ReservationdateEntity entity : dateList){
                ReservationdateEntity selectEntity = new ReservationdateEntity();
                selectEntity.setDate(entity.getDate());//日期
                selectEntity.setIntroductionGuid(entity.getIntroductionGuid());//产品GUID
                List<ReservationdateEntity> list = select(selectEntity);
                if(list != null && list.size() > 0){
                    //有记录进行修改
                    for(ReservationdateEntity selectUpdate : list){
                        selectUpdate.setTimeJson(reservationdateEntity.getTimeJson());
                        updateByPrimaryKeySelective(selectUpdate);
                    }
                }else{
                    //没有记录进行插入
                    insertSelective(entity);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateList;
    }
}
