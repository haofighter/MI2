package com.xb.haikou.config.param;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 作者: Tangren on 2017/9/1
 * 包名：szxb.com.commonbus.entity
 * 邮箱：996489865@qq.com
 * TODO:mac密钥表
 */
@Entity
public class TencentMacKeyEntity {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String key_id;
    @Unique
    private String mac_key;
    private String time;
    @Generated(hash = 761742130)
    public TencentMacKeyEntity(Long id, String key_id, String mac_key,
            String time) {
        this.id = id;
        this.key_id = key_id;
        this.mac_key = mac_key;
        this.time = time;
    }
    @Generated(hash = 846221505)
    public TencentMacKeyEntity() {
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
    public String getMac_key() {
        return this.mac_key;
    }
    public void setMac_key(String mac_key) {
        this.mac_key = mac_key;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }




}
