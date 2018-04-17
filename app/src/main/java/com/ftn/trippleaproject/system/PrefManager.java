package com.ftn.trippleaproject.system;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(SharedPref.Scope.UNIQUE)
public interface PrefManager {

    @DefaultBoolean(false)
    boolean startWithEvents();
}