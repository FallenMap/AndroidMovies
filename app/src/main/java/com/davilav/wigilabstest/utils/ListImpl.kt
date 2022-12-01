package com.davilav.wigilabstest.utils

class ListImpl(var head:Node? = null,var tail:Node? = null):List{

    override fun pushFront(key:Any){
        val node = Node(key)
        node.next = head
        head = node
        if (tail == null){
            tail = head
        }
    }
    override fun topFront():Any?{
        return head?.key
    }
    override fun popFront():Any?{
        if (head == null){
            throw Exception("ERROR:The list is empty")
        }
        else{
            val a = head?.key
            head = head?.next
            if(head == null){
                tail = null
            }
            return a
        }
    }
    override fun append(key:Any?){
        val node = Node(key)
        node.next = null
        if (tail == null){
            tail = node
            head = tail
        }
        else{
            tail?.next = node
            tail = node
        }
    }
    override fun topBack():Any?{
        return tail?.key
    }
    override fun popBack():Any?{
        if (head == null){
            throw Exception("ERROR:The list is empty")
        }
        if (head == tail){
            val a = tail?.key
            tail = null
            head = tail
            return a
        }
        else{
            var p = head
            while (p?.next?.next != null){
                p = p.next
            }
            val a = p?.next
            p?.next = null
            tail = p
            return a?.key
        }
    }
    override fun find(key:Any?):Boolean{
        var a = head
        while (true){
            if (a?.key == key){
                return true
            }
            else{
                if(a?.next == null){
                    return false
                }
                else{
                    a = a.next
                }
            }
        }
    }

    override fun toString(): String {
        var b = "null"
        if (head != null){
            b = "[" + head?.key
            var a = head
            while (a?.next != null){
                a = a.next
                b =b + "," + a?.key
            }
            b += "]"
        }
        return b
    }

    override fun isEmpty():Boolean{
        return tail == null && tail == head
    }

    override fun size(): Int {
        var i = 0
        var a = head
        while (a != null){
            a = a.next
            i += 1
        }
        return i
    }
}