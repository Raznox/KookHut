package com.example.jonathan.kookhut;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.jonathan.kookhut.db.CategoryLoader;
import com.example.jonathan.kookhut.db.ImageLoader;

import java.util.concurrent.ExecutionException;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class CategoryFragment extends Fragment implements AbsListView.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private OnFragmentInteractionListener mListener;
    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private CursorAdapter mAdapter;

    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CategoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] columns = new String[] { Contract.CategoryColumns._ID, Contract.CategoryColumns.name, Contract.CategoryColumns.imgUrl};
        int[] viewIds = new int[] { R.id.textViewCategory, R.id.imageViewCategory};


        mAdapter = new CategoryAdapter(getActivity(), R.layout.row_category, null, columns, viewIds, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CategoryLoader(getActivity());
        //return new CategorieenLoader(getActivity(), Contract.Categorieen.CONTENT_URI, new String[] {Contract.Categorieen._ID, Contract.Categorieen.Naam}, null, null, null);
        //return new CursorLoader(getActivity(), Contract.Categorieen.CONTENT_URI, new String[] {Contract.Categorieen._ID, Contract.Categorieen.Naam}, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor c = (Cursor)mAdapter.getItem(position);
        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, RecipeListFragment.newInstance(c.getInt(c.getColumnIndex(Contract.CategoryColumns._ID))))
                .commit();
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }


    class CategoryAdapter extends SimpleCursorAdapter {

        class ViewHolder {

            public ImageView image;
            public TextView CategoryName;

            public ViewHolder(View v) {
                image = (ImageView) v.findViewById(R.id.imageViewCategory);
                CategoryName = (TextView) v.findViewById(R.id.textViewCategory);
            }
        }

        private Context context;
        private int layout;

        public CategoryAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
            this.context = context;
            this.layout = layout;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = super.newView(context, cursor, parent);
            view.setTag(new ViewHolder(view));
            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            final ViewHolder viewholder = (ViewHolder) view.getTag();
            viewholder.CategoryName.setText(cursor.getString(cursor.getColumnIndex(Contract.CategoryColumns.name)));

            new ImageLoader(viewholder.image).execute(cursor.getString(cursor.getColumnIndex(Contract.CategoryColumns.imgUrl)));
        }

    }

}
