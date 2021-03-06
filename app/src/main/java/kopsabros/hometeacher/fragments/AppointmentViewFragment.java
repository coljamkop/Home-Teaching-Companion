package kopsabros.hometeacher.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kopsabros.hometeacher.R;
import kopsabros.hometeacher.adapters.MyAppointmentRecyclerViewAdapter;

import static kopsabros.hometeacher.content.FamilyContent.*;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnAppointmentListFragmentInteractionListener}
 * interface.
 */
public class AppointmentViewFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnAppointmentListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AppointmentViewFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AppointmentViewFragment newInstance(int columnCount) {
        AppointmentViewFragment fragment = new AppointmentViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment_list, container, false);
        // set the FAB
        final FloatingActionButton addFamilyFAB = (FloatingActionButton)view.findViewById(R.id.appointment_fab);
        addFamilyFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onAppointmentAddFamily();
            }
        });

        // Set the adapter
        if (view.findViewById(R.id.appointmentlist) instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.appointmentlist);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                    if (dy > 0 && addFamilyFAB.isShown())
                        addFamilyFAB.hide();
                    else
                        addFamilyFAB.show();
                }
            });
            recyclerView.setAdapter(new MyAppointmentRecyclerViewAdapter(FAMILIES, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAppointmentListFragmentInteractionListener) {
            mListener = (OnAppointmentListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAppointmentListFragmentInteractionListener");
        }
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
    public interface OnAppointmentListFragmentInteractionListener {
        void onAppointmentListFragmentInteraction(Family family);

        void onAppointmentListCheckBoxInteraction(Appointment appointment);

        void onAppointmentTimeClick(Family family);

        void onAppointmentDateClick(Family family);

        void onRemindButtonPress(Family family);

        void onListButtonPress(Family family);

        void onAppointmentAddFamily();

        void onFamilyNameInteraction(Family family);
    }
}
