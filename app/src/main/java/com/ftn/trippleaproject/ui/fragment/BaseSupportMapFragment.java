package com.ftn.trippleaproject.ui.fragment;

public class BaseSupportMapFragment extends BaseFragment {

    @Override
    protected boolean onPermissionGranted() {
        return false;
    }

    @Override
    protected void onPermissionDenied() {

    }
}
