package xyz.cybersapien.newsdroid.story.Guardian

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import xyz.cybersapien.newsdroid.R

/**
 * Created by cybersapien on 27/5/17.
 * Custom Adapter for the Stories extending RecyclerView.Adapter
 */
class GuardianStoryAdapter (
        val context: Context,
        val guardianStoryList: ArrayList<GuardianStory>?
) : RecyclerView.Adapter<GuardianStoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val context = parent?.context

        val storyView = LayoutInflater
                .from(context)
                .inflate(R.layout.story_item, parent, false)
        return ViewHolder(storyView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentStory = guardianStoryList!![position]
        val webAddressUri = Uri.parse(currentStory.webLink)
        val clickIntent = Intent(Intent.ACTION_VIEW, webAddressUri)
        holder.rootView.setOnClickListener {
            context.startActivity(clickIntent)
        }

        holder.titleView?.text = currentStory.title
        Picasso.with(context)
                .load(currentStory.imgURL)
                .error(R.drawable.placeholder)
                .into(holder.thumbImageView)

        holder.dateTextView?.text = currentStory.publicationDate

        if (currentStory.byLine.isNullOrEmpty()){
            holder.byLineText?.visibility = View.INVISIBLE
        } else {
            holder.byLineText?.visibility = View.VISIBLE
            holder.byLineText?.text = currentStory.byLine
        }

        holder.trailingTextView?.text = currentStory.trailingText
    }

    override fun getItemCount(): Int {
        return guardianStoryList?.size as Int
    }

    fun clearData() {
        val size = itemCount
        if (size > 0) {
            guardianStoryList?.clear()
            notifyItemRangeRemoved(0, size)
        }
    }


    class ViewHolder(val rootView: View): RecyclerView.ViewHolder(rootView) {

        var titleView: TextView? = null
        var trailingTextView: TextView? = null
        var thumbImageView: ImageView? = null
        var byLineText: TextView? = null
        var dateTextView: TextView? = null

        init {
            titleView = rootView.findViewById(R.id.story_item_title) as TextView
            trailingTextView = rootView.findViewById(R.id.story_item_trailing_text) as TextView
            thumbImageView = rootView.findViewById(R.id.story_item_imageview) as ImageView
            byLineText = rootView.findViewById(R.id.story_item_byline) as TextView
            dateTextView = rootView.findViewById(R.id.story_item_date) as TextView
        }
    }

}