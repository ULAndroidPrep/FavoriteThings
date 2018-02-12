package edu.rosehulman.favoritethings;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private TextView mColorTextView;
  private TextView mNumberTextView;
  private long mNumber;
  private static final String TAG = "FAVES";

  private static final String COLOR_KEY = "color";
  private static final String NUMBER_KEY = "number";

  // Access a Cloud Firestore instance from your Activity
  FirebaseFirestore db = FirebaseFirestore.getInstance();
  //    private DocumentReference mDocRef = db.collection("favoriteThings").document("Oo0D9GDQ2mUmA7Bbcb9a")
  private DocumentReference mDocRef = db.document("favoriteThings/Oo0D9GDQ2mUmA7Bbcb9a");


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mColorTextView = (TextView) findViewById(R.id.color_text_view);
    mNumberTextView = (TextView) findViewById(R.id.number_text_view);
    mNumber = 17;

    findViewById(R.id.red_button).setOnClickListener(this);
    findViewById(R.id.white_button).setOnClickListener(this);
    findViewById(R.id.blue_button).setOnClickListener(this);
    findViewById(R.id.update_color_button).setOnClickListener(this);
    findViewById(R.id.increment_number_button).setOnClickListener(this);
    findViewById(R.id.decrement_number_button).setOnClickListener(this);

    mDocRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
      @Override
      public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
        if (documentSnapshot.exists()) {
          mNumber = documentSnapshot.getLong("number");
          mNumberTextView.setText("" + mNumber);
          String color = documentSnapshot.getString("color");
          mColorTextView.setText(color);
        }

      }
    });
  }

  @Override
  public void onClick(View view) {
    Map<String, Object> dataToSave = new HashMap<>();
    switch (view.getId()) {
      case R.id.red_button:
//                mColorTextView.setText(R.string.red);
        dataToSave.put(COLOR_KEY, "red");
        break;
      case R.id.white_button:
//                mColorTextView.setText(R.string.white);
        dataToSave.put(COLOR_KEY, "white");
        break;
      case R.id.blue_button:
//                mColorTextView.setText(R.string.blue);
        dataToSave.put(COLOR_KEY, "blue");
        break;
      case R.id.update_color_button:
        Log.d(TAG, "Updating from Firebase");


//              mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//
//                @Override
//                public void onSuccess(DocumentSnapshot documentSnapshot) {
//
//                  if (documentSnapshot.exists()) {
//                    mNumber = documentSnapshot.getLong("number");
//                    mNumberTextView.setText("" + mNumber);
//                    String color = documentSnapshot.getString("color");
//                    mColorTextView.setText(color);
//                  }
//
//                }
//              });



        break;
      case R.id.increment_number_button:
        mNumber++;
//                mNumberTextView.setText("" + mNumber);
        dataToSave.put(NUMBER_KEY, mNumber);
        break;
      case R.id.decrement_number_button:
        mNumber--;
//                mNumberTextView.setText("" + mNumber);
        dataToSave.put(NUMBER_KEY, mNumber);

        break;

    }
    mDocRef.update(dataToSave);
  }
}
