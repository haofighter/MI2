package com.xb.haikou.config.param;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 作者: Tangren on 2017-09-08
 * 包名：szxb.com.commonbus.entity
 * 邮箱：996489865@qq.com
 * TODO:公钥表
 */


@Entity
public class TencentPublicKeyEntity {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String key_id;
    @Unique
    private String pub_key;
    private String remark_1;
    private String remark_2;
    @Generated(hash = 1367251237)
    public TencentPublicKeyEntity(Long id, String key_id, String pub_key,
            String remark_1, String remark_2) {
        this.id = id;
        this.key_id = key_id;
        this.pub_key = pub_key;
        this.remark_1 = remark_1;
        this.remark_2 = remark_2;
    }
    @Generated(hash = 1625044209)
    public TencentPublicKeyEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getKey_id() {
        return this.key_id;
    }
    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }
    public String getPub_key() {
        return this.pub_key;
    }
    public void setPub_key(String pub_key) {
        this.pub_key = pub_key;
    }
    public String getRemark_1() {
        return this.remark_1;
    }
    public void setRemark_1(String remark_1) {
        this.remark_1 = remark_1;
    }
    public String getRemark_2() {
        return this.remark_2;
    }
    public void setRemark_2(String remark_2) {
        this.remark_2 = remark_2;
    }

}
