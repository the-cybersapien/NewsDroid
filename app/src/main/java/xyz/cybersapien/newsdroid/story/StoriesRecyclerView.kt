package xyz.cybersapien.newsdroid.story

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View

import xyz.cybersapien.newsdroid.story.Guardian.GuardianStoryAdapter

/**
 * Created by ogcybersapien on 7/10/16.
 * Simple RecyclerView subclass that supports providing an empty view
 */

class StoriesRecyclerView : RecyclerView {

    private var emptyView: View? = null

    private val dataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            updateEmptyView()
        }
    }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    fun setEmptyView(emptyView: View) {
        this.emptyView = emptyView
    }

    fun setAdapter(adapter: GuardianStoryAdapter?) {
        if (getAdapter() != null) {
            getAdapter().unregisterAdapterDataObserver(dataObserver)
        }
        adapter?.registerAdapterDataObserver(dataObserver)
        super.setAdapter(adapter)
        updateEmptyView()
    }

    private fun updateEmptyView() {
        if (emptyView != null && adapter != null) {
            val showEmptyView = adapter.itemCount == 0
            emptyView!!.visibility = if (showEmptyView) View.VISIBLE else View.GONE
            visibility = if (showEmptyView) View.GONE else View.VISIBLE
        }
    }
}
