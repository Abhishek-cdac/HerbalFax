package com.herbal.herbalfax.customer.interfaces

import androidx.fragment.app.Fragment

interface OnInnerFragmentClicks {
    fun ReplaceFrag(fragment: Fragment?)
    fun ReplaceFragWithStack(fragment: Fragment?)
}