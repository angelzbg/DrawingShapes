package angelzani.drawingshapes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import angelzani.drawingshapes.database.DatabaseHelper;

public class ProjectsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        LinearLayout projects_LL = findViewById(R.id.projects_LL);

        ArrayList<String> projects = db.getAllProjectNames();
        for(int i=0; i<projects.size(); i++) {
            final TextView TV = new TextView(getApplicationContext());
            TV.setId(View.generateViewId());
            projects_LL.addView(TV);
            TV.setText(projects.get(i));
            TV.setTextColor(Color.BLACK);
            TV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            TV.setGravity(Gravity.CENTER);
            TV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("projectName", TV.getText().toString());
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            });
        }

    }
}
