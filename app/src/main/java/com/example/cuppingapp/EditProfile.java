package com.example.cuppingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class EditProfile extends Fragment {

    private EditText editUsername, editEmail, editCurrentPassword, editNewPassword, editConfirmPassword;
    private UserDao userDao;
    private String currentUserName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize input fields and UserDao
        editUsername = view.findViewById(R.id.Username);
        editEmail = view.findViewById(R.id.Email);
        editCurrentPassword = view.findViewById(R.id.editCurrentPassword);
        editNewPassword = view.findViewById(R.id.editNewPassword);
        editConfirmPassword = view.findViewById(R.id.editConfirmPassword);
        Button buttonSave = view.findViewById(R.id.buttonEditAccount);
        Button buttonCancel = view.findViewById(R.id.buttonBack);

        userDao = new UserDao(getContext());

        // Get the current user's username from SharedPreferences
        currentUserName = getCurrentUserName();
        if (currentUserName != null) {
            loadUserData(currentUserName);  // Load user data into fields
        } else {
            Toast.makeText(getContext(), "Failed to load user information", Toast.LENGTH_SHORT).show();
        }

        // Cancel button to close the fragment
        buttonCancel.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.popBackStack();  // Close fragment
        });

        // Save button listener
        // Inside the Save button listener
        buttonSave.setOnClickListener(v -> {
            String newUsername = editUsername.getText().toString();
            String newEmail = editEmail.getText().toString();
            String currentPassword = editCurrentPassword.getText().toString();
            String newPassword = editNewPassword.getText().toString();
            String confirmPassword = editConfirmPassword.getText().toString();

            if (newUsername.isEmpty() || newEmail.isEmpty() || currentPassword.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Verify the current password
            boolean isCurrentPasswordCorrect = userDao.checkUser(currentUserName, currentPassword);
            if (!isCurrentPasswordCorrect) {
                Toast.makeText(getContext(), "Current password is incorrect.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if new password matches confirm password
            if (!newPassword.isEmpty() && !newPassword.equals(confirmPassword)) {
                Toast.makeText(getContext(), "New passwords do not match.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Update user information and password
            boolean isUpdated = userDao.updateUser(newUsername, newEmail, newPassword);
            if (isUpdated) {
                Toast.makeText(getContext(), "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                getParentFragmentManager().popBackStack();
            } else {
                Toast.makeText(getContext(), "Failed to update profile.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Load user data and set in EditTexts
    private void loadUserData(String username) {
        User user = userDao.getUserByUsername(username);
        if (user != null) {
            editUsername.setText(user.getUsername());
            editEmail.setText(user.getEmail());
        } else {
            Toast.makeText(getContext(), "Failed to load user information.", Toast.LENGTH_SHORT).show();
        }
    }

    // Fetch the current logged-in user's username from SharedPreferences
    private String getCurrentUserName() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("current_user_name", null);
    }
}
