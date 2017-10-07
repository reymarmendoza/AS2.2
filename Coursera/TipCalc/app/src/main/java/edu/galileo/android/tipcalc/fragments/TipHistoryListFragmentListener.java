package edu.galileo.android.tipcalc.fragments;

import edu.galileo.android.tipcalc.models.TipRecord;

/**
 * Created by reyma on 6/06/2016.
 */
public interface TipHistoryListFragmentListener {
    void addToList(TipRecord record);
    void clearList();
}
