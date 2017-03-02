package xyz.cybersapien.newsdroid.sources;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import xyz.cybersapien.newsdroid.*;
import xyz.cybersapien.newsdroid.database.NewsContract;

/**
 * Created by ogcybersapien on 3/1/2017.
 * Adapter for the Source List screen
 */

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.ViewHolder> {

    private List<Source> sourceList;
    private Context context;

    public SourceAdapter(Context context, Cursor cursor) {
        sourceList = new ArrayList<>();
        cursorToList(cursor);
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View sourceView = inflater.inflate(R.layout.source_item, parent, false);

        return new ViewHolder(sourceView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Source current = sourceList.get(position);
        holder.sourceNameTextView.setText(current.getName());
        holder.sourceDescTextView.setText(current.getDescription());
        holder.sourceLangTextView.setText(current.getLanguage());
        holder.sourceCatTextView.setText(current.getCategory());
        holder.sourceCountryTextView.setText(current.getCountry());
        Picasso.with(context).load(current.getUrlToImage()).into(holder.sourceImgImageView);
    }

    @Override
    public int getItemCount() {
        return sourceList.size();
    }

    public void swapCursor(Cursor c){
        sourceList.clear();
        cursorToList(c);
        notifyDataSetChanged();
    }

    /**
     * Adds cursor data to the sourceList
     * @param cursor with the database entries
     */
    private void cursorToList(Cursor cursor){
        if (cursor == null){
            sourceList.clear();
            return;
        }
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            Source s = new Source();
            s.setId(cursor.getString(cursor.getColumnIndex(NewsContract.SourceEntry.COLUMN_SOURCE_SID)));
            s.setName(cursor.getString(cursor.getColumnIndex(NewsContract.SourceEntry.COLUMN_SOURCE_NAME)));
            s.setDescription(cursor.getString(cursor.getColumnIndex(NewsContract.SourceEntry.COLUMN_SOURCE_DESC)));
            s.setLanguage(cursor.getString(cursor.getColumnIndex(NewsContract.SourceEntry.COLUMN_SOURCE_LANGUAGE)));
            s.setCategory(cursor.getString(cursor.getColumnIndex(NewsContract.SourceEntry.COLUMN_SOURCE_CATEGORY)));
            s.setCountry(cursor.getString(cursor.getColumnIndex(NewsContract.SourceEntry.COLUMN_SOURCE_COUNTRY)));
            s.setUrlToImage(cursor.getString(cursor.getColumnIndex(NewsContract.SourceEntry.COLUMN_SOURCE_IMAGEURL)));
            if (!exists(s)){
                sourceList.add(s);
            }
        }
    }

    private boolean exists(Source c){
        for (Source s : sourceList) {
            if (s.getId() == c.getId())
                return true;
        }
        return false;
    }

    /**
     * ViewHolder Class for SourceItem in Recycler View
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView sourceNameTextView;
        public TextView sourceDescTextView;
        public TextView sourceLangTextView;
        public TextView sourceCatTextView;
        public TextView sourceCountryTextView;
        public ImageView sourceImgImageView;
        protected View rootView;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            sourceNameTextView = (TextView) rootView.findViewById(R.id.source_item_name);
            sourceDescTextView = (TextView) rootView.findViewById(R.id.source_item_desc);
            sourceLangTextView = (TextView) rootView.findViewById(R.id.source_item_lang);
            sourceCatTextView = (TextView) rootView.findViewById(R.id.source_item_category);
            sourceCountryTextView = (TextView) rootView.findViewById(R.id.source_item_country);
            sourceImgImageView = (ImageView) rootView.findViewById(R.id.source_item_img);
        }
    }

}
