package xyz.cybersapien.newsdroid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ogcybersapien on 6/10/16.
 * Custom Adapter for the Stories extending RecyclerView.Adapter
 */

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {

    private List<Story> storyList;
    private Context context;

    public StoryAdapter(Context context, List<Story> storyList) {
        this.storyList = storyList;
        this.context = context;
    }

    /**
     * Easy access to the context of this Adapter
     * @return Adapter context
     */
    private Context getContext(){
        return context;
    }

    @Override
    public StoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View storyView = layoutInflater.inflate(R.layout.story_item, parent,false);

        return new ViewHolder(storyView);
    }

    @Override
    public void onBindViewHolder(StoryAdapter.ViewHolder holder, int position) {

        /*get Current Story*/
        Story currentStory = storyList.get(position);

        holder.titleView.setText(currentStory.getTitle());
        Picasso.with(getContext()).load(currentStory.getImgURL())
                .placeholder(R.drawable.placeholder).error(R.drawable.placeholder).resize(250,150).into(holder.thumbnailImageView);
        holder.dateTextView.setText(currentStory.getPublicationDate());
        holder.byLineTextView.setText(currentStory.getByLine());
        holder.trailingTextView.setText(currentStory.getTrailingText());

    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    /**
     * Custom ViewHolder class for the custom View in our recyclerView
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView titleView;
        public TextView trailingTextView;
        public ImageView thumbnailImageView;
        public TextView byLineTextView;
        public TextView dateTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleView = (TextView) itemView.findViewById(R.id.story_item_title);
            trailingTextView = (TextView) itemView.findViewById(R.id.story_item_trailing_text);
            thumbnailImageView = (ImageView) itemView.findViewById(R.id.story_item_imageview);
            byLineTextView = (TextView) itemView.findViewById(R.id.story_item_byline);
            dateTextView = (TextView) itemView.findViewById(R.id.story_item_date);
        }
    }
}
