package com.didiincubator.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.didiincubator.R;

import io.rong.imkit.RongIM;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatFragment factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {
    //ConversationActivity conversationActivity;
    public static ChatFragment instance = null;

    public static ChatFragment getInstance() {
        if (instance == null) {
            instance = new ChatFragment();
        }
        return instance;
    }

    private View view;
    private Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat, null);
        button = (Button) view.findViewById(R.id.friend);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RongIM.getInstance() != null) {
                    RongIM.getInstance().startPrivateChat(getActivity(),"362970502",button.getText().toString());
                }
            }
        });
        return view;
    }
}

