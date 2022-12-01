package com.davilav.wigilabstest.utils

class QueueImpl(var head:Node? = null,var tail:Node? = null):Queue {
    override fun enqueue(key: Any) {
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

    override fun dequeue(): Any? {
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

    override fun isEmpty(): Boolean {
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

}