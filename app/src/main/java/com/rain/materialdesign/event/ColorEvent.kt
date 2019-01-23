package com.rain.materialdesign.event

import com.rain.materialdesign.widget.SettingUtil


class ColorEvent(var isRefresh: Boolean, var color: Int = SettingUtil.getColor())