/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 9/16/21, 10:47 AM
 * project file last modified : 9/16/21, 10:47 AM
 */

package org.rmj.guanzongroup.agent.Adapter;

import android.annotation.SuppressLint;
import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.SalesKit.Entities.EKPOPAgentRole;
import org.rmj.g3appdriver.lib.Ganado.Obj.ProductInquiry;
import org.rmj.guanzongroup.agent.R;

import java.util.List;

public class AgentListAdapter extends RecyclerView.Adapter<AgentListAdapter.ApplicationViewHolder> {

    private List<EKPOPAgentRole> poModel;
    private ProductInquiry poApp;
    private OnAgentClickListener listener;
    private boolean forViewing = false;

    private Application mContex;
    public interface OnAgentClickListener{
        void OnClick(String sUserIDxx);
    }

    public AgentListAdapter(List<EKPOPAgentRole> poModel, OnAgentClickListener listener) {
        this.poModel = poModel;
        this.mContex = mContex;
        this.listener = listener;
        this.poApp = new ProductInquiry(mContex);
    }


    @NonNull
    @Override
    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.agent_item, parent, false);
        return new ApplicationViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ApplicationViewHolder holder, int position) {
        try {
            EKPOPAgentRole loModel = poModel.get(position);
            holder.itemName01.setText(loModel.getsUserName());
            holder.itemView.setOnClickListener(v -> {
                if(listener != null){
                    listener.OnClick(loModel.getUserIDxx());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return poModel.size();
    }

    public static class ApplicationViewHolder extends RecyclerView.ViewHolder{

        MaterialTextView itemName01, itemName02, itemName03, itemName04,itemName05,itemName06;

        LinearLayout lnStatus;

        ShapeableImageView imgType;

        public ApplicationViewHolder(@NonNull View view) {
            super(view);
            itemName01 = view.findViewById(R.id.lbl_agentName);
        }
    }
}
