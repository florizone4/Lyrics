package com.example.lyrics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProjectsListActivity extends AppCompatActivity {
    ArrayList<String> ProjectTitles = new ArrayList<>();
    ArrayList<String>ProjectDescriptions = new ArrayList<>();
    RecyclerView recyclerView;

    String ProjectsURL = "https://studev.groept.be/api/a22pt108/selectProjectsFromUser/";
    String DescriptionsURL = "https://studev.groept.be/api/a22pt108/selectDescriptionsFromUser/";
    int UserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects_list);
        //get the userID from the loginActivity
        Intent loginIntent = new Intent(this, LoginActivity.class);
        int UserID = getIntent().getIntExtra("UserID", -1);
        setUserID(UserID);
        requestProjects();
        requestDescriptions();



        recyclerView = findViewById(R.id.ProjectListView);

        ProjectsListAdapter projectsListAdapter = new ProjectsListAdapter(this, ProjectTitles, ProjectDescriptions);
        recyclerView.setAdapter(projectsListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void setUserID(int UserID){this.UserID = UserID;}

    private void requestProjects() {
        String MODIFIED_Projects_URL = ProjectsURL + UserID;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest queueRequest = new JsonArrayRequest(
                Request.Method.GET,
                MODIFIED_Projects_URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i=0;i<response.length();i++) {

                            JSONObject jsonobject = null;
                            try {
                                jsonobject = response.getJSONObject(i);
                            }
                            catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                            String name;
                            try {
                                name = jsonobject.getString("projectName");
                            }
                            catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                            ProjectTitles.add(name);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(queueRequest);
    }

    private void requestDescriptions() {
        String MODIFIED_Projects_URL = DescriptionsURL + UserID;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest queueRequest = new JsonArrayRequest(
                Request.Method.GET,
                MODIFIED_Projects_URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i=0;i<response.length();i++){
                            JSONObject jsonobject = null;
                            try {
                                jsonobject = response.getJSONObject(i);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                            String description;
                            try {
                                description = jsonobject.getString("description");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }


                            ProjectDescriptions.add(description);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(queueRequest);
    }
}