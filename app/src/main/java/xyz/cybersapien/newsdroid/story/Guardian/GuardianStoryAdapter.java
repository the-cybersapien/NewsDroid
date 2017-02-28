package xyz.cybersapien.newsdroid.story.Guardian;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

import xyz.cybersapien.newsdroid.R;

/**
 * Created by ogcybersapien on 6/10/16.
 * Custom Adapter for the Stories extending RecyclerView.Adapter
 */

public class GuardianStoryAdapter extends RecyclerView.Adapter<GuardianStoryAdapter.ViewHolder> {

    private List<GuardianStory> guardianStoryList;
    private Context context;

    public GuardianStoryAdapter(Context context, List<GuardianStory> guardianStoryList) {
        this.guardianStoryList = guardianStoryList;
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
    public GuardianStoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View storyView = layoutInflater.inflate(R.layout.story_item, parent,false);

        return new ViewHolder(storyView);
    }

    @Override
    public void onBindViewHolder(final GuardianStoryAdapter.ViewHolder holder, int position) {

        /*get Current GuardianStory*/
        GuardianStory currentGuardianStory = guardianStoryList.get(position);
        /*Create a Uri to the story*/
        Uri webAddressUri = Uri.parse(currentGuardianStory.getWebLink());
        /*Create a webIntent to call if the Image or the headline is clicked*/
        final Intent webIntent = new Intent(Intent.ACTION_VIEW, webAddressUri);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(webIntent);
            }
        });

        holder.titleView.setText(currentGuardianStory.getTitle());
        Picasso.with(getContext()).load(currentGuardianStory.getImgURL())
                .error(R.drawable.placeholder).into(holder.thumbnailImageView);
        holder.dateTextView.setText(currentGuardianStory.getPublicationDate());
        holder.byLineTextView.setText(!currentGuardianStory.getByLine().equals("By "+ GuardianStory.byLineDefault)? currentGuardianStory.getByLine():"");
        holder.trailingTextView.setText(currentGuardianStory.getTrailingText());


    }

    @Override
    public int getItemCount() {
        return guardianStoryList.size();
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
        protected View rootView;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            titleView = (TextView) itemView.findViewById(R.id.story_item_title);
            trailingTextView = (TextView) itemView.findViewById(R.id.story_item_trailing_text);
            thumbnailImageView = (ImageView) itemView.findViewById(R.id.story_item_imageview);
            byLineTextView = (TextView) itemView.findViewById(R.id.story_item_byline);
            dateTextView = (TextView) itemView.findViewById(R.id.story_item_date);
        }
    }

    public void clearData(){
        int size = this.getItemCount();
        if (size > 0){
            for (int i=0;i<size;i++){
                this.guardianStoryList.remove(0);
            }
            this.notifyItemChanged(0, size);
        }
    }
}
