package com.example.android.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends ArrayAdapter<News> {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.date)
    TextView publishedDate;
    @BindView(R.id.sectionName)
    TextView sectionName;
    @BindView(R.id.card_view)
    CardView cardView;

    public NewsAdapter(@NonNull Context context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem = convertView;
        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
        }
        ButterKnife.bind(this, listViewItem);
        final News currentNews = getItem(position);

        title.setText(currentNews.getWebTitle().toString());
        publishedDate.setText(currentNews.getWebPublicationDate().toString());
        sectionName.setText(currentNews.getSection_name().toString());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = currentNews.getWebUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                getContext().startActivity(intent);
            }
        });
        return listViewItem;
    }
}
