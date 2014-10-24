package fr.julienheissat.ui;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;

public class MainTabListener<T extends Fragment> implements ActionBar.TabListener {
    private Fragment mFragment;
    private final Activity mActivity;
    private final String mTag;
    private final Class<T> mClass;

    /** Constructor used each time a new tab is created.
     * @param activity  The host Activity, used to instantiate the fragment
     * @param tag  The identifier tag for the fragment
     * @param clz  The fragment's Class, used to instantiate the fragment
     */
    public MainTabListener(Activity activity, String tag, Class<T> clz) {
        mActivity = activity;
        mTag = tag;
        mClass = clz;
    }

    /* The following are each of the ActionBar.TabListener callbacks */

    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // Check if the fragment is already initialized
  //      if (mFragment == null) {
            // If not, instantiate and add it to the activity
            mFragment = Fragment.instantiate(mActivity, mClass.getName());
            ft.add(android.R.id.content, mFragment, mTag);
//        } else {
//            // If it exists, simply attach it in order to show it
//            ft.attach(mFragment);
//        }
    }

    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (mFragment != null) {
            // Detach the fragment, because another one is being attached
            ft.detach(mFragment);
        }

    }

    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        // User selected the already selected tab. Usually do nothing.
    }
}

//import android.app.Activity;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.app.ActionBar;
//
//public class MainTabListener<T extends Fragment> implements ActionBar.TabListener
//{
//    private Fragment mFragment;
//    private final Activity mActivity;
//    private final String mTag;
//    private final Class<T> mClass;
//    private final Bundle mArgs;
//    private FragmentTransaction fft;
//
//    /** Constructor used each time a new tab is created.
//     * @param activity  The host Activity, used to instantiate the fragment
//     * @param tag  The identifier tag for the fragment
//     * @param clz  The fragment's Class, used to instantiate the fragment
//     */
//    public MainTabListener(Activity activity, String tag, Class<T> clz) {
//        mActivity = activity;
//        mTag = tag;
//        mClass = clz;
//    }
//
//    /* The following are each of the ActionBar.TabListener callbacks */
//
//    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
//        android.support.v4.app.FragmentTransaction fft = mActivity.getSupportFragmentManager().beginTransaction();
//        // Check if the fragment is already initialized
//        if (mFragment == null) {
//            // If not, instantiate and add it to the activity
//            mFragment = Fragment.instantiate(mActivity, mClass.getName());
//            ft.add(android.R.id.content, mFragment, mTag);
//        } else {
//            // If it exists, simply attach it in order to show it
//            ft.attach(mFragment);
//        }
//    }
//
//    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
//        if (mFragment != null) {
//            // Detach the fragment, because another one is being attached
//            ft.remove(mFragment);
//        }
//
//
//    }
//
//    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
//        // User selected the already selected tab. Usually do nothing.
//    }
//}
