package com.google.firebase.udacity.friendlychat;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.udacity.friendlychat.DetailObjectFragment.ObjectDemoFragment;
import com.google.firebase.udacity.friendlychat.cards.SliderAdapter;
import com.google.gson.Gson;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Object.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Object#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Object extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Object() {
        // Required empty public constructor
    }

    private static final String logDebug = "Logger : ";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Object.
     */
    // TODO: Rename and change types and number of parameters
    public static Object newInstance(String param1, String param2) {
        Object fragment = new Object();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    private CardSliderLayoutManager layoutManger;
    private RecyclerView recyclerView;

    private TextSwitcher placeSwitcher;

    private TextView country1TextView;
    private TextView country2TextView;
    private int countryOffset1;
    private int countryOffset2;
    private long countryAnimDuration;
    private int currentPosition;
    SliderAdapter sliderAdapter;
    private LinearLayout mPositionObjectLinarLayout;
    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        Log.d(logDebug, "Object.onAttach");
    }

    public static final String fragmentTag = "Object";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if (savedInstanceState != null) {
            Log.d(logDebug, "Object.onCreateSavedInstanceState" + savedInstanceState.getString("hello"));
        }
        Log.d(logDebug, "Object.onCreate");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden == true)
            ((HomeActivity) context).tabStateManager.ObjectState = fragmentTag;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            Log.d(logDebug, "Object.onActivityCreatedSavedInstanceState" + savedInstanceState.getString("hello"));
        }
        Log.d(logDebug, "Object.onActivityCreated");
    }

    View rootView;
    FrameLayout container;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_object, container, false);
        mPositionObjectLinarLayout = (LinearLayout) rootView.findViewById(R.id.object_position_linearlayout);
        placeSwitcher = (TextSwitcher) rootView.findViewById(R.id.ts_place);
        placeSwitcher.setFactory(new TextViewFactory(R.style.PlaceTextView, false));

        if (Objects.isEmpty()) {
            getObjectinfo();
        }

        mPositionObjectLinarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id2 = ((HomeActivity) getActivity()).getPosition();
                Log.d(logDebug, id2);
                final int newPos = getPositionById2(id2);
                layoutManger.scrollToPosition(newPos);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layoutManger.scrollToPosition(newPos);
                    }
                }, 40);
                onActiveCardChange();
            }
        });

        return rootView;
    }

    public int getPositionById2(String id2) {
        for (int i = 0; i < Objects.size(); i++) {
            if (id2.equals(Objects.get(i).beaconId2)) {
                return i;
            }
        }
        return -1;
    }


    private static final String ObjectInfoUrl = "https://us-central1-friendlychat-10420.cloudfunctions.net/sendAllArts";

    private RequestQueue requestQueue;

    private void getObjectinfo() {
        requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ObjectInfoUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray ja = response.getJSONArray("items");
                    for (int i = 0; i < ja.length(); i++) {
                        String foo = ja.getString(i);
                        Gson gson = new Gson();
                        ObjectInfo object = gson.fromJson(foo, ObjectInfo.class);
                        object.beaconId2 = getObjectId2(i);
                        Objects.put(i, object);
                    }
                } catch (JSONException e) {
                    Log.d("JSONException", e.toString());
                }
                initCountryText();
                sliderAdapter = new SliderAdapter(Objects, Objects.size(), new OnCardClickListener(), Object.this);
                initRecyclerView();
                Log.d("sliderAdapter  ", sliderAdapter.toString());
                onActiveCardChange(1);
                onActiveCardChange(0);
                mPositionObjectLinarLayout.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("jsonObjectResponse", error.toString());
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    public String getObjectId2(int pos) {
        if (pos == 0)
            return "56197";
        if (pos == 1)
            return "10136";
        if (pos == 2)
            return "15498";
        if (pos == 3)
            return "7407";
        if (pos == 4)
            return "35830";
        else
            return null;
    }


    HashMap<Integer, ObjectInfo> Objects = new HashMap<>();


    @Override
    public void onResume() {
        super.onResume();
        Log.d(logDebug, "Object.onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(logDebug, "Object.onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(logDebug, "Object.onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(logDebug, "Object.onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Log.d(logDebug, "Object.onDetach");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(logDebug, "Object.onDestroyView");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.g
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(sliderAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    onActiveCardChange();
                }
            }
        });

        layoutManger = (CardSliderLayoutManager) recyclerView.getLayoutManager();
        new CardSnapHelper().attachToRecyclerView(recyclerView);
    }

    private void initCountryText() {
        countryAnimDuration = getResources().getInteger(R.integer.labels_animation_duration);
        countryOffset1 = getResources().getDimensionPixelSize(R.dimen.left_offset4ArtName);
        countryOffset2 = getResources().getDimensionPixelSize(R.dimen.aniStart4ArtNane);
        country1TextView = (TextView) rootView.findViewById(R.id.tv_country_1);
        country2TextView = (TextView) rootView.findViewById(R.id.tv_country_2);

        country1TextView.setX(countryOffset1);
        country2TextView.setX(countryOffset2);
        country1TextView.setText(Objects.get(0).artName);
        titleTextId = country1TextView.getId();
        country2TextView.setAlpha(0f);

        country1TextView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "open-sans-extrabold.ttf"));
        country2TextView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "open-sans-extrabold.ttf"));
    }

    int titleTextId;

    private void setCountryText(String text, boolean left2right) {
        final TextView invisibleText;
        final TextView visibleText;
        if (country1TextView.getAlpha() > country2TextView.getAlpha()) {
            visibleText = country1TextView;
            invisibleText = country2TextView;
        } else {
            visibleText = country2TextView;
            invisibleText = country1TextView;
        }

        final int vOffset;
        if (left2right) {
            invisibleText.setX(0);
            vOffset = countryOffset2;
        } else {
            invisibleText.setX(countryOffset2);
            vOffset = 0;
        }

        if (text.length() > 8) {
            invisibleText.setTextSize(24);
        } else {
            invisibleText.setTextSize(28);
        }
        invisibleText.setText(text);
        titleTextId = invisibleText.getId();

        final ObjectAnimator iAlpha = ObjectAnimator.ofFloat(invisibleText, "alpha", 1f);
        final ObjectAnimator vAlpha = ObjectAnimator.ofFloat(visibleText, "alpha", 0f);
        final ObjectAnimator iX = ObjectAnimator.ofFloat(invisibleText, "x", countryOffset1);
        final ObjectAnimator vX = ObjectAnimator.ofFloat(visibleText, "x", vOffset);

        final AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(iAlpha, vAlpha, iX, vX);
        animSet.setDuration(countryAnimDuration);
        animSet.start();
    }

    private void onActiveCardChange() {

        final int pos = layoutManger.getActiveCardPosition();
        Log.d("oACCPOS", pos + "");
        if (pos == RecyclerView.NO_POSITION || pos == currentPosition) {
            return;
        }
        onActiveCardChange(pos);
    }

    private void onActiveCardChange(int pos) {

        //right2left
        int animH[] = new int[]{R.anim.slide_in_right, R.anim.slide_out_left};
        int animV[] = new int[]{R.anim.slide_in_top, R.anim.slide_out_bottom};

        final boolean left2right = pos < currentPosition;
        if (left2right) {
            animH[0] = R.anim.slide_in_left;
            animH[1] = R.anim.slide_out_right;

            animV[0] = R.anim.slide_in_bottom;
            animV[1] = R.anim.slide_out_top;
        }


        setCountryText(Objects.get(pos).artName, left2right);

        placeSwitcher.setInAnimation(getActivity(), animV[0]);
        placeSwitcher.setOutAnimation(getActivity(), animV[1]);
        placeSwitcher.setText(Objects.get(pos).authorName);

        currentPosition = pos;
    }


    private class TextViewFactory implements ViewSwitcher.ViewFactory {

        @StyleRes
        final int styleId;
        final boolean center;

        TextViewFactory(@StyleRes int styleId, boolean center) {
            this.styleId = styleId;
            this.center = center;
        }

        @SuppressWarnings("deprecation")
        @Override
        public View makeView() {
            final TextView textView = new TextView(getActivity());

            if (center) {
                textView.setGravity(Gravity.CENTER);
            }

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                textView.setTextAppearance(getActivity(), styleId);
            } else {
                textView.setTextAppearance(styleId);
            }

            return textView;
        }

    }

    private class OnCardClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            final CardSliderLayoutManager lm = (CardSliderLayoutManager) recyclerView.getLayoutManager();

            if (lm.isSmoothScrolling()) {
                return;
            }

            final int activeCardPosition = lm.getActiveCardPosition();
            if (activeCardPosition == RecyclerView.NO_POSITION) {
                return;
            }

            final int clickedPosition = recyclerView.getChildAdapterPosition(view);
            if (clickedPosition == activeCardPosition) {

                Bundle bundle = new Bundle();

                bundle.putString("imageUrl", Objects.get(activeCardPosition).photo);
                bundle.putString("audioUrl", Objects.get(activeCardPosition).audio);
                bundle.putString("artName", Objects.get(activeCardPosition).artName);
                bundle.putString("artDesc", Objects.get(activeCardPosition).artDes);
                bundle.putString("authorName", Objects.get(activeCardPosition).authorName);
                bundle.putString("authorDesc", Objects.get(activeCardPosition).authorIntro);



                ObjectDemoFragment demoFragment = new ObjectDemoFragment();
                demoFragment.setArguments(bundle);
                ((HomeActivity) context).changeFragment(ObjectDemoFragment.fragmentTag, demoFragment);

            } else if (clickedPosition > activeCardPosition) {
                recyclerView.smoothScrollToPosition(clickedPosition);
                onActiveCardChange(clickedPosition);
            }
        }
    }
}
