package com.didiincubator.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.didiincubator.R;
import com.didiincubator.fragment.ChatFragment;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

public class MessageActivity extends FragmentActivity {
    private ViewPager viewPager;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private Fragment conversationlist;
    private Fragment conversationFragment = null;
    private List<Fragment> fragment = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        conversationlist = initConversationList();
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //fragment.add(HomeFragment.getInstance());
        fragment.add(conversationlist);
        fragment.add(ChatFragment.getInstance());
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragment.get(position);
            }

            @Override
            public int getCount() {
                return fragment.size();
            }
        };
        viewPager.setAdapter(fragmentPagerAdapter);
    }

    private Fragment initConversationList() {
        if (conversationFragment == null) {
            ConversationListFragment listFragment = ConversationListFragment.getInstance();
            Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationList")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")
                    .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")
                    .build();
            listFragment.setUri(uri);
            return listFragment;
        } else {
            return conversationFragment;
        }
    }
}
