package com.inet.code.entity.paraise.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 管理用户的点赞
 * </p>
 *
 * @author HCY
 * @since 2020-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tbl_paraise")
public class Paraise implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 赞uuid
     */
    @TableId(value = "praise_uuid",type = IdType.UUID)
    private String praiseUuid;

    /**
     * 用户邮箱
     */
    @TableField(value = "praise_email")
    private String praiseEmail;

    /**
     * 点赞的用户邮箱
     */
    @TableField(value = "praise_economy")
    private String praiseEconomy;

    /**
     * 创建时间
     */
    @TableField(value = "gmt_create",fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "gmt_modified",fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;


}
