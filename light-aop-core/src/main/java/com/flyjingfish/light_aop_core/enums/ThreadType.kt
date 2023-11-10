package com.flyjingfish.light_aop_core.enums

enum class ThreadType {
    /**
     * 单线程池
     */
    SingleIO,

    /**
     * 多线程池
     */
    MultipleIO,

    /**
     * 磁盘读写线程池(本质上是 {@link ThreadType#SingleIO} ）
     */
    DiskIO,

    /**
     * 网络请求线程池(本质上是 {@link ThreadType#MultipleIO}）
     */
    NetworkIO
}