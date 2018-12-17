package com.hao.mi2.help;

import android.view.View;

public interface DrawerHelper {
    void openDrawer();

    void closeDrawer();

    void setDrawerContent(View view);

    void setDrawerLockMode(int i);
}
