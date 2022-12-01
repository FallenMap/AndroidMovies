package com.davilav.wigilabstest.utils

interface Queue {
    fun enqueue(key:Any)
    fun dequeue():Any?
    fun isEmpty():Boolean
    fun size():Int
}