package com.jessy.foodmap.member.paging

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.jessy.foodmap.R

class CollectPagingAdapter(private val context: Context,
                           private val departments: List<String>,
                           private val classes: List<List<String>>
): BaseExpandableListAdapter() {

    override fun getGroup(groupPosition: Int): Any {
        return departments[groupPosition]
    }
    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return classes[groupPosition][childPosition]
    }

    override fun getGroupCount(): Int {
        return departments.size
    }
    override fun getChildrenCount(groupPosition: Int): Int {
        return classes[groupPosition].size
    }
    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }
    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return (groupPosition * 100 + childPosition).toLong()
    }
    override fun hasStableIds(): Boolean {
        return true
    }
    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_member_collect, null)
        val textView = view.findViewById<TextView>(R.id.item_collect_tv_Name)
        textView.text = departments[groupPosition]

        return view
    }
    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_member_collect_class, null)
        val textView = view.findViewById<TextView>(R.id.item_collect_tv_className)
        textView.text = classes[groupPosition][childPosition]

        return view
    }
}