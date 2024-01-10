package org.rmj.guanzon.guanzonsaleskit.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import org.rmj.g3appdriver.GCircle.room.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.guanzon.guanzonsaleskit.R;
import org.rmj.guanzon.guanzonsaleskit.ViewModel.VMHome;
import org.rmj.guanzon.guanzonsaleskit.databinding.ActivityHomeBinding;
import org.rmj.guanzongroup.agent.Activities.Activity_AgentEnroll;
import org.rmj.guanzongroup.agent.Activities.Activity_AgentList;
import org.rmj.guanzongroup.authlibrary.Activity.Activity_Settings;
import org.rmj.guanzongroup.ganado.Activities.Activity_Inquiries;

public class Activity_Home extends AppCompatActivity {
    private VMHome mviewModel;
    private Boolean isCompleteAccount;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    private NavigationView navigationView;
    private MessageBox poMessage;

    public Boolean IsCompleteAccount(){
        return isCompleteAccount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*VERIFY FIRST USER IF COMPLETED ITS ACCOUNT*/
        mviewModel = new ViewModelProvider(this).get(VMHome.class);
        mviewModel.GetPoEmpInfo().observe(Activity_Home.this, new Observer<EEmployeeInfo>() {
            @Override
            public void onChanged(EEmployeeInfo eEmployeeInfo) {
                String sClientID = eEmployeeInfo.getClientID();
                if (sClientID.isEmpty()){
                    isCompleteAccount = false;
                }else {
                    isCompleteAccount = true;
                }
            }
        });

        poMessage = new MessageBox(this);
        poMessage.initDialog();
        poMessage.setTitle("Guanzon Sales Kit");
        poMessage.setPositiveButton("Close", new MessageBox.DialogButton() {
            @Override
            public void OnButtonClick(View view, AlertDialog dialog) {
                dialog.dismiss();
            }
        });

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarActivityHome.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_inquiry, R.id.nav_agent_enroll, R.id.nav_agent_list,R.id.nav_profile)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_activity_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity__home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent loIntent;
        if(item.getItemId() == android.R.id.home){

        } else if (item.getItemId() == R.id.nav_agent_list) {
            if (isCompleteAccount){
                loIntent = new Intent(Activity_Home.this, Activity_AgentList.class);
                startActivity(loIntent);
                return false;
            }
        }else if (item.getItemId() == R.id.nav_agent_enroll) {
            if (isCompleteAccount){
                loIntent = new Intent(Activity_Home.this, Activity_AgentEnroll.class);
                startActivity(loIntent);
                return true;
            }
        }else if (item.getItemId() == R.id.nav_profile) {
            if (isCompleteAccount){
                loIntent = new Intent(Activity_Home.this, Activity_Settings.class);
                startActivity(loIntent);
                return true;
            }
        }
        else if (item.getItemId() == R.id.nav_inquiry) {
            if (isCompleteAccount){
                loIntent = new Intent(Activity_Home.this, Activity_Inquiries.class);
                startActivity(loIntent);
                return true;
            }
        }

        poMessage.setMessage(String.valueOf(isCompleteAccount));
        poMessage.show();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_activity_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}