package com.example.cuppingapp;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import android.Manifest;
import android.os.Build;

public class CuppingDetailFragment extends Fragment {

    private Cupping cupping;
    private CuppingDao cuppingDao;
    private EditText editTextDate, editTextNotes;
    private RatingBar ratingBarAcidity, ratingBarFlavour, ratingBarSweetness, ratingBarBitterness, ratingBarTactile, ratingBarBalance;
    private Button buttonUpdate, buttonCancel, buttonDelete;
    private Button buttonExportPDF;
    private Object calendar;

    public CuppingDetailFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cupping_detail2, container, false);

        // Initialize views
        editTextDate = view.findViewById(R.id.editTextDate);
        editTextNotes = view.findViewById(R.id.editTextNotes);
        ratingBarAcidity = view.findViewById(R.id.ratingBarAcidity);
        ratingBarFlavour = view.findViewById(R.id.ratingBarFlavour);
        ratingBarSweetness = view.findViewById(R.id.ratingBarSweetness);
        ratingBarBitterness = view.findViewById(R.id.ratingBarBitterness);
        ratingBarTactile = view.findViewById(R.id.ratingBarTactile);
        ratingBarBalance = view.findViewById(R.id.ratingBarBalance);
        buttonUpdate = view.findViewById(R.id.buttonUpdate);
        buttonCancel = view.findViewById(R.id.buttonCancel);
        buttonDelete = view.findViewById(R.id.buttonDelete);
        editTextDate = view.findViewById(R.id.editTextDate);
        buttonExportPDF = view.findViewById(R.id.buttonExportPDF);

        cuppingDao = new CuppingDao(requireContext());

        // Get the selected cupping from the arguments
        if (getArguments() != null) {
            int cuppingID = getArguments().getInt("cuppingID");

            // Retrieve the full cupping object from the database
            cupping = cuppingDao.getCuppingById(cuppingID);

            // Ensure cupping data is loaded immediately
            if (cupping != null) {
                loadCuppingDetails(view);  // Load details right after retrieving the cupping object
            } else {
                Toast.makeText(getContext(), "Failed to load the cupping details.", Toast.LENGTH_SHORT).show();
            }
        }

        // Handle update button click
        buttonUpdate.setOnClickListener(v -> {
            if (cupping != null) {
                updateCupping();
            }
        });

        // Handle cancel button click
        buttonCancel.setOnClickListener(v -> {
            // Go back to the previous fragment
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        // Handle delete button click
        buttonDelete.setOnClickListener(v -> {
            if (cupping != null) {
                deleteCupping();
            }
        });

        // Set a click listener to show DatePickerDialog
        editTextDate.setOnClickListener(v -> showDatePicker());

        // Handle Export to PDF button click with permission check
        buttonExportPDF.setOnClickListener(v -> {
            requestStoragePermission();  // Ensure permission is requested for Android 9 and below
            exportToPDF();               // Then execute the PDF export
        });

        return view;
    }

    private void loadCuppingDetails(View view) {
        // Check if the cupping object has valid data
        if (cupping != null) {
            editTextDate.setText(cupping.getDate());
            editTextNotes.setText(cupping.getNotes());
            ratingBarAcidity.setRating(cupping.getAcidity());
            ratingBarFlavour.setRating(cupping.getFlavour());
            ratingBarSweetness.setRating(cupping.getSweetness());
            ratingBarBitterness.setRating(cupping.getBitterness());
            ratingBarTactile.setRating(cupping.getTactile());
            ratingBarBalance.setRating(cupping.getBalance());
            Log.d("CuppingDetailFragment", "Cupping Data: " + cupping.getDate() + ", " + cupping.getAcidity());
        } else {
            Log.e("CuppingDetailFragment", "Cupping object is null");
        }
    }

    private void updateCupping() {
        String newDate = editTextDate.getText().toString();
        String newNotes = editTextNotes.getText().toString();
        int newAcidity = (int) ratingBarAcidity.getRating();
        int newFlavour = (int) ratingBarFlavour.getRating();
        int newSweetness = (int) ratingBarSweetness.getRating();
        int newBitterness = (int) ratingBarBitterness.getRating();
        int newTactile = (int) ratingBarTactile.getRating();
        int newBalance = (int) ratingBarBalance.getRating();

        if (TextUtils.isEmpty(newDate)) {
            Toast.makeText(getContext(), "Please enter a valid date.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update the cupping object
        cupping.setDate(newDate);
        cupping.setNotes(newNotes);
        cupping.setAcidity(newAcidity);
        cupping.setFlavour(newFlavour);
        cupping.setSweetness(newSweetness);
        cupping.setBitterness(newBitterness);
        cupping.setTactile(newTactile);
        cupping.setBalance(newBalance);

        // Calculate the new total score based on the updated attributes
        //Todo: totalScore is not calculated properly?
        float totalScore = (newAcidity + newFlavour + newSweetness + newBitterness + newTactile + newBalance) / 6.0f;
        cupping.setTotalScore(totalScore);

        // Update the cupping in the database
        boolean isUpdated = cuppingDao.updateCupping(cupping);

        if (isUpdated) {
            Toast.makeText(getContext(), "Cupping updated successfully!", Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack(); // Go back to the previous fragment
        } else {
            Toast.makeText(getContext(), "Failed to update cupping.", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteCupping() {
        // Call delete method in CuppingDao
        boolean isDeleted = cuppingDao.deleteCupping(cupping.getCuppingID());

        if (isDeleted) {
            Toast.makeText(getContext(), "Cupping deleted successfully!", Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack();  // Go back after deletion
        } else {
            Toast.makeText(getContext(), "Failed to delete cupping.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDatePicker() {
        // Get current date to display in the DatePicker
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Set the selected date to the EditText
                    String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    editTextDate.setText(date);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            // For Android 9 and below, request WRITE_EXTERNAL_STORAGE permission
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Storage permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Storage permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void exportToPDF() {
        if (cupping == null) {
            Toast.makeText(getContext(), "No cupping data available.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new PdfDocument
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();

        // Start a page
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create(); // A4 size: 595x842 points
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        // Safely load the logo from drawable
        try {
            Log.d("CuppingDetailFragment", "Attempting to load logo...");
            Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.logo);  // Use your custom logo here
            if (logo != null) {
                Bitmap scaledLogo = Bitmap.createScaledBitmap(logo, 100, 100, false);
                canvas.drawBitmap(scaledLogo, 50, 50, paint);  // Draw logo at top of page
                Log.d("CuppingDetailFragment", "Logo loaded successfully.");
            } else {
                Log.e("CuppingDetailFragment", "Logo could not be loaded.");
            }
        } catch (Exception e) {
            Log.e("CuppingDetailFragment", "Error loading logo: " + e.getMessage(), e);
        }

        // Write content to the PDF (replace with your actual cupping details)
        paint.setTextSize(16);
        canvas.drawText("Cupping Report", 50, 200, paint);  // Set the text below the logo
        paint.setTextSize(14);

        // Display all attributes (cupping details)
        canvas.drawText("Date: " + editTextDate.getText().toString(), 50, 240, paint);
        canvas.drawText("Notes: " + editTextNotes.getText().toString(), 50, 280, paint);
        canvas.drawText("Acidity: " + cupping.getAcidity(), 50, 320, paint);
        canvas.drawText("Flavour: " + cupping.getFlavour(), 50, 360, paint);
        canvas.drawText("Sweetness: " + cupping.getSweetness(), 50, 400, paint);
        canvas.drawText("Bitterness: " + cupping.getBitterness(), 50, 440, paint);
        canvas.drawText("Tactile: " + cupping.getTactile(), 50, 480, paint);
        canvas.drawText("Balance: " + cupping.getBalance(), 50, 520, paint);
        canvas.drawText("Coffee: " + cupping.getCoffeeName(), 50, 560, paint);  // Assuming the coffee name is in the cupping object

        // Finish the page
        pdfDocument.finishPage(page);

        // Get the public Documents directory for saving
        File publicDocDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        if (!publicDocDir.exists()) {
            publicDocDir.mkdirs();  // Create the directory if it doesn't exist
        }

        // Custom file name (cuppingID + coffeeName.pdf)
        String fileName = "CuppingReport_" + cupping.getCuppingID() + "_" + cupping.getCoffeeName() + ".pdf";
        File file = new File(publicDocDir, fileName);

        try {
            // Write the PDF to the file
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(getContext(), "PDF exported successfully to Documents!", Toast.LENGTH_SHORT).show();
            Log.d("CuppingDetailFragment", "PDF saved to: " + file.getAbsolutePath());

            // Show a notification to open the PDF
            showNotification(file);

        } catch (IOException e) {
            Log.e("CuppingDetailFragment", "Error writing PDF: " + e.getMessage(), e);
            Toast.makeText(getContext(), "Failed to export PDF.", Toast.LENGTH_SHORT).show();
        }

        // Close the document
        pdfDocument.close();
    }

    private void showNotification(File pdfFile) {
        NotificationManager notificationManager = (NotificationManager) requireContext().getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "pdf_export_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "PDF Export", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        // Create an intent to open the PDF file
        Intent openPdfIntent = new Intent(Intent.ACTION_VIEW);
        Uri pdfUri = FileProvider.getUriForFile(requireContext(), "com.example.cuppingapp.fileprovider", pdfFile);
        openPdfIntent.setDataAndType(pdfUri, "application/pdf");
        openPdfIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // Create PendingIntent to open the PDF when the notification is clicked
        PendingIntent pendingIntent = PendingIntent.getActivity(requireContext(), 0, openPdfIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), channelId)
                .setSmallIcon(R.drawable.logo_white_sml)  // Ensure this icon exists
                .setContentTitle("PDF Export Successful")
                .setContentText("Tap to open the PDF.")
                .setContentIntent(pendingIntent)  // Set the PendingIntent to open the PDF
                .setAutoCancel(true);  // Remove the notification after it's clicked

        // Show the notification
        notificationManager.notify(1, builder.build());
    }

}
