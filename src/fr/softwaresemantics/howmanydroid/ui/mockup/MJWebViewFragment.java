package fr.softwaresemantics.howmanydroid.ui.mockup;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewFragment;

import fr.softwaresemantics.howmanydroid.model.formula.FormulaSyntax;
import fr.softwaresemantics.howmanydroid.util.MJView;

/**
 * Created by cmh on 08/02/14.
 */
public class MJWebViewFragment extends Fragment {

    MJView mjHelpher;

    private WebView mWebView;
    private boolean mIsWebViewAvailable;

    public MJWebViewFragment() {
    }


    private boolean already = false;

    // TODO manage here nb line, formats, height

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       //  View myView = super.onCreateView(inflater, container, savedInstanceState);

        if (mWebView != null) {
            mWebView.destroy();
        }
        mWebView = new WebView(getActivity());
        mIsWebViewAvailable = true;

        if (!already) {

            setRetainInstance(true);

        // Init MJ
        mjHelpher = new MJView(getActivity(), mWebView, 1);

        // Sample data
        mjHelpher.displayMath(FormulaSyntax.ASCII_MATHML, "pi.R^2");

        already= true;
        }

        return mWebView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        Log.e("bla onHiddenChanged", String.valueOf(hidden));
    }

    @Override
    public void onPause() {
        // On tente de bloquer la purge de la webview lié au détachement du layout
        // Rendre conditionnel...
       super.onPause();

        // Pause de la webview ?
       Log.e("bla"," onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        // Pause de la webview ?
        Log.e("bla"," onResume");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("bla"," onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("bla"," onDestroy");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.e("bla"," onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("bla"," onDetach");
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.e("bla"," onStart");
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.e("bla"," onStop");

    }
}
