package com.example.asus.activititytest;

import android.content.Context;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class List_View extends AppCompatActivity {

    public class LanguageAdapter extends ArrayAdapter<List_View.Language> {
        private int resourceId;
        public LanguageAdapter(Context context, int textViewResourceId, List<Language> objects) {
            super (context,textViewResourceId,objects);
            resourceId = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Language language = getItem(position);
            View view;
            ViewHolder viewHolder;
            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
                viewHolder = new ViewHolder();
                viewHolder.languageImage = (ImageView) view.findViewById(R.id.language_iamge);
                viewHolder.languageName = (TextView) view.findViewById(R.id.language_name);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.languageImage.setImageResource(language.getImageId());
            viewHolder.languageName.setText(language.getName());
            /* ImageView languageImage = (ImageView) view.findViewById(R.id.language_iamge);
            TextView languageName = (TextView) view.findViewById(R.id.language_name);
            languageImage.setImageResource(language.getImageId());
            languageName.setText(language.getName()); */
            return view;
        }

        class ViewHolder{
            ImageView languageImage;
            TextView languageName;
        }
    }

    private String[] data = {"Java","python","JS","PHP","C++",
     "html5","CSS","C","C#","swift" };
    private List<Language> languageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        initLanguage();
        LanguageAdapter adapter = new LanguageAdapter(List_View.this,R.layout.language_item, languageList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (List_View.this, android.R.layout.simple_list_item_1,data);*/
         listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Language language = languageList.get(position);
                Toast.makeText(List_View.this,language.getName(),Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(List_View.this, "你长按的是第" + (position + 1) + "条数据", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

private void initLanguage() {
    for (int i=0;i<2;i++) {
        Language Java = new Language("Java",R.drawable.java);
        languageList.add(Java);
        Language python = new Language("python",R.drawable.python);
        languageList.add(python);
        Language C_language = new Language("C",R.drawable.clanguage);
        languageList.add(C_language);
        Language CPP = new Language("C++",R.drawable.cpp);
        languageList.add(CPP);
        Language Swift = new Language("Swift",R.drawable.sw);
        languageList.add(Swift);
        Language html5 = new Language("html5",R.drawable.html5);
        languageList.add(html5);
        Language PHP = new Language("PHP",R.drawable.php);
        languageList.add(PHP);
        Language JavaScript = new Language("JS",R.drawable.js);
        languageList.add(JavaScript);
        Language CSharp = new Language("C#",R.drawable.csharp);
        languageList.add(CSharp);
        Language CSS = new Language("CSS",R.drawable.css);
        languageList.add(CSS);
    }
}

    public class Language {
        private String name;
        private int imageId;
        public Language(String name, int imageId) {
            this.name = name;
            this.imageId = imageId;
        }
        public String getName() {
            return name;
        }
        public int getImageId() {
            return imageId;
        }
    }
}