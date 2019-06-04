package com.xb.haikou.config;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class BlackList {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String card;
    private String type;

    @Generated(hash = 1598883430)
    public BlackList(Long id, String card, String type) {
        this.id = id;
        this.card = card;
        this.type = type;
    }

    @Generated(hash = 1200343381)
    public BlackList() {
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
