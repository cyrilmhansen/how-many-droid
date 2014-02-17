package fr.softwaresemantics.howmanydroid;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;

import fr.softwaresemantics.howmanydroid.db.Category;
import fr.softwaresemantics.howmanydroid.db.DBHelper;
import fr.softwaresemantics.howmanydroid.db.Locale;
import fr.softwaresemantics.howmanydroid.ui.CategoryListAdapter;

/**
 * Created by chris on 16/02/14.
 */
public class CategoryListActivity extends ListActivity {
    ListView catList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.category_list);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
        Bundle bundle = getIntent().getExtras();



                //(ListView) findViewById(R.id.category_list_view);
        final DBHelper dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);
        final int parent_id = bundle.getInt("parent_id");
        final String lang_id = bundle.getString("lang");
        //Locale lang = null;
        try {
            ArrayList<Category> categories;

            Locale lang = dbHelper.getLocaleDao().queryForId(lang_id);
            if (lang ==null)
                lang = dbHelper.getLocaleDao().queryForId("en_GB");
            if(parent_id==-1)
            {
                Where where = dbHelper.getCategoryDao().queryBuilder().where().isNull("parent_id");
                categories =  new ArrayList<Category>(dbHelper.getCategoryDao().query(where.prepare()));
            }
            else
            {
                Category parent = dbHelper.getCategoryDao().queryForId(bundle.getInt("parent_id"));
                categories = new ArrayList<Category>(parent.getChildren());
            }
            setListAdapter(new CategoryListAdapter(this,categories,lang));
            catList = getListView();

            catList.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int selectedCategory = ((Category)parent.getItemAtPosition(position)).getCategoryID();
                    //getItemIdAtPosition() // why U no work, hum was it my fault?

                    //Category Parent = dbHelper.getCategoryDao().queryForId((int) selectedCategory);//hum
                    //ArrayList<Category> categories =  new ArrayList<Category>(Parent.getChildren());
                    //need to distinguish sub menu from leaf here
                    //long foo = (long)view.getTag();
                    Intent i = new Intent(getApplicationContext(), CategoryListActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("parent_id",(int) selectedCategory);
                    b.putString("lang",lang_id);
                    i.putExtras(b);

                    startActivity(i);
                }
            });
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }
}
