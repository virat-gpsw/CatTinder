package com.example.cattinder.activity;

import com.example.cattinder.R;
import com.example.cattinder.view.CatFragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

public class MainActivity extends Activity{

    private static final String TAG_CAT_FRAGMENT = "CatFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: your code here
        showFragment(new CatFragment(), TAG_CAT_FRAGMENT);
    }

    /**
     * A helper method to swap the container view and a given Fragment
     *
     * @param fragment - the Fragment to show
     * @param name     - the Fragment's name on the back stack state
     */
    private void showFragment(Fragment fragment, String name) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(name)
                .commit();
    }


}
