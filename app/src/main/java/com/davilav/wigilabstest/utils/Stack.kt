package com.davilav.wigilabstest.utils

interface Stack {
    fun push(key:Any)
    fun top():Any?
    fun pop():Any?
    fun isEmpty():Boolean
    fun size():Int
}