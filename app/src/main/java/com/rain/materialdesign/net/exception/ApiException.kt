package com.rain.materialdesign.net.exception


class ApiException(errorCode: Int, msg: String) : RuntimeException(msg)
