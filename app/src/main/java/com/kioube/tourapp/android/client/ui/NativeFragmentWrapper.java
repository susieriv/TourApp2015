package com.kioube.tourapp.android.client.ui;


import android.content.Intent;
import android.app.Fragment;

/**
 * Created by pierrelouis on 17/04/2016.
 */
public class NativeFragmentWrapper extends android.support.v4.app.Fragment {
    private final Fragment nativeFragment;

    public NativeFragmentWrapper(FragmentBase fragment) {
        this.nativeFragment = fragment;
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        nativeFragment.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        nativeFragment.onActivityResult(requestCode, resultCode, data);
    }

}
