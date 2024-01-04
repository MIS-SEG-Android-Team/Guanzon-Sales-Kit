package org.rmj.guanzongroup.agent.Activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import org.rmj.g3appdriver.etc.LoadDialog;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.guanzongroup.agent.Adapter.AgentListAdapter;
import org.rmj.guanzongroup.agent.R;
import org.rmj.guanzongroup.agent.ViewModel.VMAgentList;

import java.util.Objects;

public class Activity_AgentEnroll extends AppCompatActivity {
    private VMAgentList mViewModel;
    private RecyclerView rvAgentList;
    private AgentListAdapter adapter;
    private LoadDialog poLoading;
    private MessageBox poMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(Activity_AgentEnroll.this).get(VMAgentList.class);
        setContentView(R.layout.activity_agent_enroll);
        initView();

        mViewModel.ImportKPOPAgent(new VMAgentList.OnTaskExecute() {
            @Override
            public void OnExecute() {
                poLoading = new LoadDialog(Activity_AgentEnroll.this);
                poLoading.initDialog("Agent Enroll", "Please wait for a while.", false);
                poLoading.show();


            }

            @Override
            public void OnSuccess() {
                poLoading.dismiss();
            }

            @Override
            public void OnFailed(String message) {
                poLoading.dismiss();

                poMessage = new MessageBox(Activity_AgentEnroll.this);
                poMessage.initDialog();
                poMessage.setMessage(message);
                poMessage.setPositiveButton("Okay", new MessageBox.DialogButton() {
                    @Override
                    public void OnButtonClick(View view, AlertDialog dialog) {
                        dialog.dismiss();
                    }
                });
                poMessage.show();
            }
        });
        mViewModel.GetKPOPAgentRole().observe(Activity_AgentEnroll.this, ekpopAgentRoles -> {
            if (ekpopAgentRoles.size() > 0){
                adapter = new AgentListAdapter(ekpopAgentRoles, new AgentListAdapter.OnAgentClickListener() {
                    @Override
                    public void OnClick(String sUserIDxx) {

                    }

                });
                rvAgentList.setAdapter(adapter);

            }
        });

    }
    private void initView() {
        rvAgentList = findViewById(R.id.rvAgentList);
//        searchView = findViewById(R.id.searchview);

        MaterialToolbar toolbar = findViewById(R.id.toolbar_agent);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(org.rmj.g3appdriver.R.anim.anim_intent_slide_in_left, org.rmj.g3appdriver.R.anim.anim_intent_slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}