package com.inet.code.entity.cipher.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 管理用户的密码
 * </p>
 *
 * @author HCY
 * @since 2020-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@TableName("tbl_cipher")
public class Cipher implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 密码实体类的uuid
     */
    @TableId(value = "cipher_uuid",type = IdType.UUID)
    private String cipherUuid;

    /**
     * 用户账号(邮箱)
     */
    @TableField(value = "cipher_email")
    private String cipherEmail;

    /**
     * 用户密码(MD5加密)
     */
    @TableField(value = "cipher_password")
    private String cipherPassword;

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

    public Cipher(String cipherEmail, String cipherPassword) {
        this.cipherEmail = cipherEmail;
        this.cipherPassword = cipherPassword;
    }
}
