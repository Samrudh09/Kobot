package com.example.sidnotebook.summary;


import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.widget.Button;
import android.widget.RadioButton;
import org.w3c.dom.Text;

import java.util.ArrayList;


public class Main_Select extends AppCompatActivity{
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_main);

        final ArrayList<String> books =new  ArrayList<String>() ;
        books.add("Sherlock Holmes Study In Scarlet");
        books.add("Lord Of The Rings");
        books.add("Lord Of the Flies");
        books.add("Harry Potter: Chamber of secrets");
        books.add("1984");
        books.add("To Kill A Mocking Bird");
        ArrayList<Drawable> bookImages = createDrawables(books);

        CustomList adapter = new CustomList(this, books, bookImages);
        ListAdapter bookAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,books);
        //ArrayList<Drawable> layers = new ArrayList<>();
        //LayerDrawable layerDrawable = new LayerDrawable((Drawable[]) layers.toArray());
        //((ImageView) findViewById(R.id.imageView)).setImageDrawable(layerDrawable);
        ((ListView)findViewById(R.id.ListView)).setAdapter(adapter);
        //text.setText(listString);
        ((ListView)findViewById(R.id.ListView)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                // Toast.makeText(MainActivity.this, "You Clicked at " +books.get(position), Toast.LENGTH_SHORT).show();
                Intent book = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(book);
            }
        });
    }

    public ArrayList<Drawable> createDrawables(ArrayList<String> books){
        ArrayList<Drawable> list =new ArrayList<Drawable>();
        list.add(this.getResources().getDrawable(R.drawable.sherlock_holmes_study_in_scarlet));
        list.add(this.getResources().getDrawable(R.drawable.lord_of_the_rings));
        list.add(this.getResources().getDrawable(R.drawable.a2));
        list.add(this.getResources().getDrawable(R.drawable.a3));
        list.add(this.getResources().getDrawable(R.drawable.a4));
        list.add(this.getResources().getDrawable(R.drawable.a5));
        return list;
    }


}

class CustomList extends ArrayAdapter<String>{
    private final Activity context;
    private final ArrayList<String> web;
    private final ArrayList<Drawable> image;
    public CustomList(Activity context,ArrayList<String> web, ArrayList<Drawable> image) {
        super(context, R.layout.single_list, web);
        this.context = context;
        this.web = web;
        this.image = image;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.single_list, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(web.get(position));

        imageView.setImageDrawable(image.get(position));
        return rowView;
    }
}


