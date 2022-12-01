package com.davilav.wigilabstest.utils

class ArrayImpl(var data: ArrayList<Any?> = ArrayList<Any?>()):List{


    override fun pushFront(key:Any){
        data.add(0,key)
    }
    override fun topFront():Any?{
        return data[0]
    }

    fun get(i:Int): Any? {
        return data[i]
    }

    fun clear(){
        data = ArrayList<Any?>()
    }

    fun toArray(list:MutableList<Any>): ArrayImpl {
        for (i in list){
            data.add(i)
        }
        return this
    }

    override fun popFront():Any?{
        if (isEmpty()){
            throw Exception("ERROR:The list is empty")
        }
        else{
            val a = data[0]
            data.removeAt(0)
            return a
        }
    }
    override fun append(key:Any?){
        data.add(key)
    }
    override fun topBack():Any?{
        return data[data.size-1]
    }
    override fun popBack():Any?{
        if (isEmpty()){
            throw Exception("ERROR:The list is empty")
        }
        val a = topBack()
        data.removeAt(data.size-1)
        return a
    }
    override fun find(key:Any?):Boolean{
        var i = 0
        while (i < data.size && data.get(i) != key){
            i++
        }
        return i < data.size
    }

    override fun toString(): String {
        var b = "["
        if (!isEmpty()){
            b += data.get(0)
            var i = 1
            while (i < data.size){
                b += "," + data.get(i)
                i++
            }
        }
        b += "]"
        return b
    }

    override fun isEmpty():Boolean{
        return data.size == 0
    }

    override fun size(): Int {
        return data.size
    }

    fun delDuplicates() {
        var workingList = ArrayImpl()
        for(i in 0 until data.size){
            var a = data[i]
            if (!workingList.find(a)){
                workingList.append(a)
            }
        }
        data = workingList.data
    }
}