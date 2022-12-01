package com.davilav.wigilabstest.utils

interface List{
    fun pushFront(key:Any)
    fun topFront():Any?
    fun popFront():Any?
    fun append(key:Any?)
    fun topBack():Any?
    fun popBack():Any?
    fun find(key:Any?):Boolean
    fun isEmpty():Boolean
    fun size():Int
}