package com.davilav.wigilabstest.utils

class treeNode(var data1: movie?) {
        var value: movie? = data1
        var left : treeNode? = null
        var right: treeNode? = null
    }

class movie(
    var adult:              Boolean,
    var backdrop_path:      String,
    var genre_ids:          IntArray,
    var id:                 Int,
    var original_language:  String,
    var original_title:     String,
    var overview:           String,
    var popularity:         Int,
    var poster_path:        String,
    var release_date:       String,
    var title:              String,
    var video:              Boolean,
    var vote_average:       Float,
    var vote_count:         Int
    )

class AVLtree(){
    var head: treeNode? = null

    fun findDepth(root: treeNode?):Int
	{
		if (root == null) return 0
		var right = findDepth(root.right)
		var left = findDepth(root.left)
		if (left > right) return left + 1
		return right + 1
	}

    fun findDepth():Int
	{
		if (head == null) return 0
		return findDepth(head)
	}

    fun  getBalanceFactor(root: treeNode?): Int
    {
        if (root == null) return -1
		return findDepth(root.left) - findDepth(root.right)
    }

    fun  rotateRight(root: treeNode?): treeNode?
    {
        var newHead:treeNode? = root?.left
		var subTree:treeNode? = newHead?.right
		newHead?.right = root
		root?.left = subTree
		return newHead
    }

    fun  rotateLeft(root: treeNode?): treeNode?
    {
        var newHead:treeNode? = root?.right
		var subTree:treeNode? = newHead?.left
		newHead?.left = root
		root?.right = subTree
		return newHead
    }

    fun insertAVL(root: treeNode?, newNode: treeNode?): treeNode?
	{
        if (root == null) return newNode

        if (newNode?.value!!.id < root.value!!.id)
        {
			root.left = insertAVL(root.left, newNode)
        }
		else if (newNode.value!!.id > root.value!!.id)
        {
			root.right = insertAVL(root.right, newNode)
        }
		else {
			println("No duplicate values allowed in BST")
        }

        var balanceFactor = getBalanceFactor(root)

        if (balanceFactor > 1 && newNode.value!!.id < root.left?.value!!.id) return rotateRight(root)

        if (balanceFactor < -1 && newNode.value!!.id > root.right?.value!!.id) return rotateLeft(root)

        if (balanceFactor > 1 && newNode.value!!.id > root.left?.value!!.id)
		{
			root.left = rotateLeft(root.left)
			return rotateRight(root)
		}
        if (balanceFactor < -1 && newNode.value!!.id < root.right?.value!!.id)
		{
			root.right = rotateRight(root.right)
			return rotateLeft(root)
		}
        return root
    }

    fun insertAVL(newMovie : movie)
	{
		var newNode:treeNode = treeNode(newMovie)
		head = insertAVL(head, newNode)
	}

    fun findMin(root: treeNode?): treeNode?
	{
        var root1: treeNode? = root
		while (root1?.left != null){
			root1 = root1.left
        }
		return root1
	}

    fun deleteAVL(root1: treeNode?, movie1:movie?): treeNode?
    {
		var root:treeNode? = root1
        if (root == null) return null
		else if (movie1!!.id > root.value!!.id) root.right = deleteAVL(root.right, movie1)
		else if (movie1.id < root.value!!.id) root.left = deleteAVL(root.left, movie1)
        else 
		{
            if (root.left == null && root.right == null)
			{
				root = null
			}
			else if (root.left == null)
			{
				root = root.right
			}
			else if (root.right == null)
			{
				root = root.left
			}
			else 
			{
				var tmp:treeNode? = findMin(root.right)
				root.value = tmp?.value
				root.right = deleteAVL(root.right, tmp?.value)
			}
        }
		var balanceFactor:Int = getBalanceFactor(root)
		if (balanceFactor == 2 && getBalanceFactor(root?.left) >= 0) return rotateRight(root)
		else if (balanceFactor == -2 && getBalanceFactor(root?.right) <= 0) return rotateLeft(root)
		else if (balanceFactor == -2 && getBalanceFactor(root?.right) == 1)
		{
			root?.right = rotateRight(root?.right)
			return rotateLeft(root)
		}
		else if (balanceFactor == 2 && getBalanceFactor(root?.left) == -1)
		{
			root?.left = rotateLeft(root?.left)
			return rotateRight(root)
		}
		return root
    }

    fun deleteAVL(movie1:movie?)
	{
		if (head == null) return
		head = deleteAVL(head, movie1);
	}

	fun Find(root1: treeNode?, movie1:movie?):treeNode?
	{
		var root:treeNode? = root1
		if (root == null) return null 
		if (movie1!!.id > root.value!!.id) return Find(root.right, movie1)
		else if (movie1.id < root.value!!.id) return Find(root.left, movie1)
		else return root
	}

    fun preorder(root1: treeNode?){
        if (root1 != null){
			println(root1.value!!.id)
            preorder(root1.left)
            preorder(root1.right)
		}
	}
}