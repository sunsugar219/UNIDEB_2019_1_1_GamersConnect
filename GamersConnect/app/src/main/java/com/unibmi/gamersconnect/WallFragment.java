package com.unibmi.gamersconnect;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *  interface
 * to handle interaction events.
 * Use the factory method to
 * create an instance of this fragment.
 */
public class WallFragment extends Fragment /*implements OnFragmentInteractionListener*/ {

    /*String[] strings = {getContext().getResources().getString(R.string.testText1),
            getContext().getResources().getString(R.string.testText2),
            /*getActivity().getString(R.string.testText3),
            getActivity().getString(R.string.testText4),
            getActivity().getString(R.string.testText5),
            getActivity().getString(R.string.testText6),
            getActivity().getString(R.string.testText7),
            getActivity().getString(R.string.testText8),
            getActivity().getString(R.string.testText9),
            getActivity().getString(R.string.testText10),
            getActivity().getString(R.string.testText11),
            getActivity().getString(R.string.testText12),
            getActivity().getString(R.string.testText13),
            getActivity().getString(R.string.testText14),
            getActivity().getString(R.string.testText15),*/
           /* getContext().getResources().getString(R.string.testText16)};*/

    public WallFragment() {}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String[] strings = getResources().getStringArray(R.array.testTexts);
        Context cx = getActivity().getApplicationContext();
        RecyclerView rv = new RecyclerView(getContext());
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new SimpleRVAdapter(strings, cx));
        return rv;
    }

    /**
     * A Simple Adapter for the RecyclerView
     */
    public class SimpleRVAdapter extends RecyclerView.Adapter<SimpleViewHolder> {
        private String[] dataSource;
        public SimpleRVAdapter(String[] dataArgs, Context cx){

            dataSource = cx.getResources().getStringArray(R.array.testTexts);
        }

        @Override
        public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = new TextView(parent.getContext());
            view.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            SimpleViewHolder viewHolder = new SimpleViewHolder(view);
            viewHolder.textView.setTextColor(getResources().getColor(R.color.colorAccent));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(SimpleViewHolder holder, int position) {
            holder.textView.setText(dataSource[position]);
        }

        @Override
        public int getItemCount() {
            return dataSource.length;
        }
    }

    /**
     * A Simple ViewHolder for the RecyclerView
     */
    public static class SimpleViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public SimpleViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}
    /*RecyclerView mRecyclerView;
    int[] mTextList;

    private OnFragmentInteractionListener mListener;

    public WallFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WallFragment.
     */
    // TODO: Rename and change types and number of parameters
    /*public static WallFragment newInstance(String param1, String param2) {
        WallFragment fragment = new WallFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wall, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

   /* @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 1);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mTextList = new int[]{R.string.testText1,
                R.string.testText2,
                R.string.testText3,
                R.string.testText4,
                R.string.testText5,
                R.string.testText6,
                R.string.testText7,
                R.string.testText8,
                R.string.testText9,
                R.string.testText10,
                R.string.testText11,
                R.string.testText12,
                R.string.testText13,
                R.string.testText14,
                R.string.testText15,
                R.string.testText16};

        MyAdapter myAdapter = new MyAdapter(getActivity(), mTextList);
        mRecyclerView.setAdapter(myAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/

