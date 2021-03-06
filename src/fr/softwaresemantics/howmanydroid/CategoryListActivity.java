package fr.softwaresemantics.howmanydroid;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import fr.softwaresemantics.howmanydroid.db.Category;
import fr.softwaresemantics.howmanydroid.db.DBHelper;
import fr.softwaresemantics.howmanydroid.db.Locale;
import fr.softwaresemantics.howmanydroid.ui.CategoryListAdapter;
import android.app.ActionBar;
/**
 * Created by christophe Goessen on 16/02/14.
 */
public class CategoryListActivity extends ListActivity
{
    ListView catList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.category_list);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
        Bundle bundle = getIntent().getExtras();
        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);


                //(ListView) findViewById(R.id.category_list_view);
        final DBHelper dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);
        final int parent_id = bundle.getInt("parent_id");
        final String lang_id = bundle.getString("lang");
        //Locale lang = null;
        try {
            ArrayList<Category> categories;

            Locale lang = dbHelper.getLocaleDao().queryForId(lang_id);
            final Locale flang = lang;
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

            Collections.sort(categories, new Comparator<Category>() {

                @Override
                public int compare(Category lhs, Category rhs) {
                    return lhs.getName().getValue(flang).compareTo(rhs.getName().getValue(flang));
                }
            });
            //OpenHelperManager.releaseHelper();
            setListAdapter(new CategoryListAdapter(this, categories, lang));
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
    //this will go in interface
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.header, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                intent = new Intent(this, CalculatorActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.header_menu_list:
                intent = new Intent(this, CategoryListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Bundle b = new Bundle();
                b.putInt("parent_id",-1);
                b.putString("lang","en_GB");
                intent.putExtras(b);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
