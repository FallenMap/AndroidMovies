package com.davilav.wigilabstest.utils

class Node(val key : Any?,var next:Node? = null){
    override fun toString(): String {
        return key.toString()
    }
}