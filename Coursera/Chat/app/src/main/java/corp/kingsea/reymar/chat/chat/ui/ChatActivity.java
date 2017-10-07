package corp.kingsea.reymar.chat.chat.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import corp.kingsea.reymar.chat.R;
import corp.kingsea.reymar.chat.chat.adapters.ChatAdapter;
import corp.kingsea.reymar.chat.chat.ChatPresenter;
import corp.kingsea.reymar.chat.chat.ChatPresenterImpl;
import corp.kingsea.reymar.chat.domain.AvatarHelper;
import corp.kingsea.reymar.chat.entities.ChatMessage;
import corp.kingsea.reymar.chat.lib.GlideImageLoader;
import corp.kingsea.reymar.chat.lib.ImageLoader;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity implements ChatView {

    @Bind(R.id.imgAvatar)
    CircleImageView imgAvatar;
    @Bind(R.id.txtUser)
    TextView txtUser;
    @Bind(R.id.txtStatus)
    TextView txtStatus;
    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.messageRecyclerView)
    RecyclerView messageRecyclerView;
    @Bind(R.id.editTxtMessage)
    EditText editTxtMessage;
    @Bind(R.id.btnSendMessage)
    ImageButton btnSendMessage;

    private ChatPresenter presenter;
    private ChatAdapter adapter;

    public final static String EMAIL_KEY = "email";
    public final static String ONLINE_KEY = "online";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        setupAdapter();
        setupRecyclerView();
        setupTolbar(getIntent());

        presenter = new ChatPresenterImpl(this);
    }

    private void setupAdapter() {
        adapter = new ChatAdapter(getApplication(), new ArrayList<ChatMessage>());
    }

    private void setupRecyclerView() {
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageRecyclerView.setAdapter(adapter);
    }

    private void setupTolbar(Intent i) {
        String recipient = i.getStringExtra(EMAIL_KEY);
        presenter.setChatRecipient(recipient);

        boolean online = i.getBooleanExtra(ONLINE_KEY, false);
        String status = online ? "online" : "offline";
        int color =online ? Color.GREEN : Color.RED;

        txtUser.setText(recipient);
        txtStatus.setText(status);
        txtStatus.setTextColor(color);

        ImageLoader imageLoader = new GlideImageLoader(getApplicationContext());
        imageLoader.load(imgAvatar, AvatarHelper.getAvatarUrl(recipient));

        setSupportActionBar(toolBar);
    }


    @Override
    protected void onResume() {
        presenter.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        presenter.onPaused();
        super.onPause();
    }

    @Override
    public void onMessageReceived(ChatMessage msg) {
        adapter.add(msg);
        messageRecyclerView.scrollToPosition(adapter.getItemCount()-1);
    }

    @OnClick(R.id.btnSendMessage)
    public void sendMessage(){
        presenter.sendMessage(editTxtMessage.getText().toString());
        editTxtMessage.setText("");
    }
}
