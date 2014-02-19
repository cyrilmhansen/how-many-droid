package fr.softwaresemantics.howmanydroid.ui;

import android.app.Activity;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import fr.softwaresemantics.howmanydroid.R;
import fr.softwaresemantics.howmanydroid.db.Category;
import fr.softwaresemantics.howmanydroid.db.Locale;

/**
 * Created by christophe Goessen on 16/02/14.
 */
public class CategoryListAdapter implements ListAdapter {

    private final ArrayList<Category> categories;
    private final Locale lang; //just for test
    public LayoutInflater inflater;
    public Activity activity;

    public CategoryListAdapter(Activity act, ArrayList<Category> categories, Locale lang) {
        this.categories = categories;
        activity=act;
        this.lang = lang;
        inflater = act.getLayoutInflater();

    }



    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categories.get(position).getCategoryID();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, null);
        }
        Category cat = categories.get(position);

        //((TextView) convertView.findViewById(R.id.list_item_title);
        if (convertView != null) {
            ((TextView)(convertView.findViewById(R.id.list_item_title))).setText(cat.getName().getValue(lang));
        }
        if (convertView != null) {
            ((TextView)(convertView.findViewById(R.id.list_item_description))).setText(cat.getDescription().getValue(lang));
        }
        //convertView.setTag(cat.getCategoryID());// not needed anymore
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;//maybe change that here for mixed category/calculus list
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }
}
