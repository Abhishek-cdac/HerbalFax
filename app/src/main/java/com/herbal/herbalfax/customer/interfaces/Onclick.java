package com.herbal.herbalfax.customer.interfaces;

import android.view.View;

public interface Onclick {
    void onItemClicks(View view, int position, int i, String blogId, String s);

    void onItemClicks(View view, int position, int i, String blogId);
}
