package com.xb.haikou.config.param;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AliPublicKeyEntity {
    /**
     * publicKey : 02DF95E6C7491E0F90A2322075BD973FBCB2D163B92623BBE153F65814583F17C9
     * keyId : 22
     */

    private String public_key;
    @Unique
    private int key_id;
    @Generated(hash = 880778898)
    public AliPublicKeyEntity(String public_key, int key_id) {
        this.public_key = public_key;
        this.key_id = key_id;
    }
    @Generated(hash = 928156976)
    public AliPublicKeyEntity() {
    }
    public String getPublic_key() {
        return this.public_key;
    }
    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }
    public int getKey_id() {
        return this.key_id;
    }
    public void setKey_id(int key_id) {
        this.key_id = key_id;
    }


}
