package com.strive.xhttp.data

/**
 *
 * @description 功能描述
 * @date 2020-01-03
 * @author xingcc
 *
 */
class BaseEntity<T> {
    private var code = 0
    private var message: String? = null
    private var data: T? = null
}