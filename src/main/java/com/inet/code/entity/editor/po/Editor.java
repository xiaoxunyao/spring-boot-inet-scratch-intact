package com.inet.code.entity.editor.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 管理一个文件的标签
 * </p>
 *
 * @author HCY
 * @since 2020-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tbl_editor")
public class Editor implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标签管理器的uuid
     */
    @TableId(value = "editor_uuid",type = IdType.UUID)
    private String editorUuid;

    /**
     * 文件的uuid
     */
    @TableField(value = "editor_production_uuid")
    private String editorProductionUuid;

    /**
     * 标签的uuid
     */
    @TableField(value = "editor_label_uuid")
    private String editorLabelUuid;

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
