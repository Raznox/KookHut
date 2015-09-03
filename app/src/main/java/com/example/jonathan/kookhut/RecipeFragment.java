package com.example.jonathan.kookhut;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jonathan.kookhut.db.ImageLoader;
import com.example.jonathan.kookhut.db.Models.Recipe;
import com.example.jonathan.kookhut.db.RecipeLoader;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeFragment extends Fragment {

    private static final String ARG_RECIPEID = "recipeID";
    private static final String ARG_IMGURL = "imgUrl";
    private Integer RecipeID;
    private String imgUrl;

    private OnFragmentInteractionListener mListener;


    public static RecipeFragment newInstance(Integer RecipeID, String imgUrl) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_RECIPEID, RecipeID);
        args.putString(ARG_IMGURL, imgUrl);
        fragment.setArguments(args);
        return fragment;
    }

    public RecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            RecipeID = getArguments().getInt(ARG_RECIPEID);
            imgUrl = getArguments().getString(ARG_IMGURL);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        new RecipeLoader(getActivity(), RecipeID).execute();
        Activity activity = getActivity();
        ImageView imageView = (ImageView)activity.findViewById(R.id.imageViewRecipe);
        new ImageLoader(imageView).execute(imgUrl);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        public void onFragmentInteraction(Uri uri);
    }

}
