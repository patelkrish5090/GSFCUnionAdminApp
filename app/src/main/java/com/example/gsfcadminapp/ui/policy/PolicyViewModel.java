package com.example.gsfcadminapp.ui.policy;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.gsfcadminapp.ui.policy.Policy;

import java.util.List;

public class PolicyViewModel extends ViewModel {
    private MutableLiveData<List<Policy>> policies = new MutableLiveData<>();

    public void setPolicies(List<Policy> policyList) {
        policies.setValue(policyList);
    }

    public MutableLiveData<List<Policy>> getPolicies() {
        return policies;
    }
}
