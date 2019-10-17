package com.informatics.research.quiznav.database;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class QuizesDb {
    private DatabaseReference dbQuizes;

    public void getDataByKey(final MyCallback myCallback, HashMap<String, String> DataToSend) {
        final String SubjectCode, MaterialCode, QuizesCode, Key;

        SubjectCode = DataToSend.get("Subject Code");
        MaterialCode = DataToSend.get("Material Code");
        QuizesCode = DataToSend.get("Quiz Code");
        Key = DataToSend.get("Key");

        dbQuizes = FirebaseDatabase.getInstance().getReference("quizes").child(SubjectCode).child(MaterialCode).child(QuizesCode);

        dbQuizes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.child(Key).getValue().toString();
                myCallback.onCallback(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
