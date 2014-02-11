package fr.softwaresemantics.howmanydroid.ui.mockup;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.webkit.WebView;
import android.webkit.WebViewFragment;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import fr.softwaresemantics.howmanydroid.HMDApplication;
import fr.softwaresemantics.howmanydroid.R;
import fr.softwaresemantics.howmanydroid.model.formula.FormulaSyntax;
import fr.softwaresemantics.howmanydroid.ui.mjview.MJViewCache;
import fr.softwaresemantics.howmanydroid.util.MJView;
import fr.softwaresemantics.howmanydroid.util.MsgDialog;

public class WebViewReuseActivity extends Activity {

    private FrameLayout frameLayout;
    private static FrameLayout frameLayout1;
    private MJWebViewFragment fragment;
    private LinearLayout ll;
    private MJView mjv6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_reuse);

        ll = (LinearLayout) findViewById(R.id.FragWvReuseLay1);

        MJView mjv = MJViewCache.getInstance().getView();
        // WebView wv = new WebView(HMDApplication.getContext());
        //ll.addView(wv);
        mjv.displayMath(FormulaSyntax.ASCII_MATHML, "");
        mjv.getWebView().setVisibility(View.VISIBLE);

        MJView mjv2 = MJViewCache.getInstance().getView();


        ll.addView(mjv2.getWebView(), 1);
        mjv2.getWebView().setVisibility(View.VISIBLE);

        mjv2.displayMath(FormulaSyntax.ASCII_MATHML, "f(x)=\\sum_{n=0}^\\infty\\frac{f^{(n)}(a)}{n!}(x-a)^n");

        MJView mjv3 = MJViewCache.getInstance().getView();
        ll.addView(mjv3.getWebView(), 1);
        mjv3.getWebView().setVisibility(View.VISIBLE);
        mjv3.displayMath(FormulaSyntax.ASCII_MATHML, "f(x)=sum_(n=0)^oo(f^((n))(a))/(n!)(x-a)^n");

        MJView mjv4 = MJViewCache.getInstance().getView();
        ll.addView(mjv4.getWebView(), 1);
        mjv4.getWebView().setVisibility(View.VISIBLE);

        mjv4.displayMath(FormulaSyntax.ASCII_MATHML, "x/x={(1,if x!=0),(text{undefined},if x=0):}");

        MJView mjv5 = MJViewCache.getInstance().getView();
        ll.addView(mjv5.getWebView(), 1);
        mjv5.getWebView().setVisibility(View.VISIBLE);

        mjv5.displayMath(FormulaSyntax.ASCII_MATHML, "(a,b]={x in RR | a < x <= b}");

        mjv6 = MJViewCache.getInstance().getView();
        ll.addView(mjv6.getWebView(), 1);
        mjv6.getWebView().setVisibility(View.VISIBLE);

        mjv6.displayMath(FormulaSyntax.ASCII_MATHML, "d/dxf(x)=lim_(h->0)(f(x+h)-f(x))/h");

//        MJView mjView = new MJView(this, wv, 1;
//        mjView.displayMath(FormulaSyntax.ASCII_MATHML, "pi.2.R^2");

//
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        fragment = new MJWebViewFragment();
//        ft.replace(R.id.placeholder, fragment);
//        ft.setCustomAnimations(android.R.animator.fade_in,
//                android.R.animator.fade_out);
//        ft.commit();
//
//
//
      addShowHideListener(R.id.FragWvReuseBtn1,  mjv.getWebView());


        // Delayed analysis of perf stats

        Thread thread = new Thread() {

            @Override
            public void run() {

                // Block this thread for 2 seconds.
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                }

                // After sleep finished blocking, create a Runnable to run on the UI Thread.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       perfAnalysis();
                    }
                });

            }

        };

// Don't forget to start the thread.
        thread.start();


//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        }
//
//        WebView webView = (WebView) findViewById(R.id.webViewFormulaReuse);
//         MJView mjView = new MJView(this, webView, 1)
    }

    public void perfAnalysis() {
        MsgDialog.showDialog(this, "Perfs stats", "Call back 3s " + mjv6.getLastTypeSetDelayMs());
    }

    void addShowHideListener(int buttonId, final WebView webview) {
        final Button button = (Button) findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               // ll.removeView(webview);
                ll.addView(webview, 1);
                webview.setVisibility(View.VISIBLE);
            }
        });
}
//    void addShowHideListener(int buttonId, final Fragment fragment) {
//        final Button button = (Button)findViewById(buttonId);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//
//
//                if (fragment.isHidden()) {
//                   // ft.show(fragment);
//                    getFragmentManager().popBackStackImmediate(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
//                    // On tente de déplacer le fragment
//                    // Avant de le reaffichaer
//
//                    getFragmentManager().beginTransaction().hide(fragment).remove(fragment).commit();
//                    getFragmentManager().executePendingTransactions();
//                    getFragmentManager().beginTransaction()
//                            .replace(R.id.placeholder2, fragment)
//                            .show(fragment)
//                            .setCustomAnimations(android.R.animator.fade_in,
//                                    android.R.animator.fade_out)
//                            .commit();
//                    getFragmentManager().executePendingTransactions();
//
//                    button.setText("Hide");
//                } else {
//                    FragmentTransaction ft = getFragmentManager().beginTransaction();
//                    ft.setCustomAnimations(android.R.animator.fade_in,
//                            android.R.animator.fade_out);
//                    ft.hide(fragment);
//                    button.setText("Show");
//                    ft.commit();
//                }
//
//            }
//        });
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.web_view_reuse, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
//
//    /**
//     * A placeholder fragment containing a simple view.
//     */
//    public static class PlaceholderFragment extends Fragment {
//
//        public PlaceholderFragment() {
//        }
//
////        @Override
////        public View onCreateView(LayoutInflater inflater, ViewGroup container,
////                Bundle savedInstanceState) {
////
////            View viewFrag1 = inflater.inflate(R.layout.fragment_web_view_reuse, container, false);
////
////            frameLayout1 = new FrameLayout(getActivity());
////            frameLayout1.addView(viewFrag1);
////
////            View webViewFrag = inflater.inflate(R.layout.fragment_web_view_reuse2, container, false);
////          // Référence sur la webview
////
////            frameLayout1.addView(webViewFrag);
////
////            return frameLayout1;
////        }
//    }

}
