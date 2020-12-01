package com.zqkj.entity;

import com.zqkj.utils.annotation.SqlFieldIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 
 * 
 * @author zqkj
 * @email root@qq.com
 * @date 2020-01-14 11:14:20
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="teatimetab")
@SqlFieldIgnore("content")
public class TeatimeGroupByEntity extends BaseEntity implements Serializable {

	private String guid;

	private List<TeatimeEntity> teatimeEntityList;
}
