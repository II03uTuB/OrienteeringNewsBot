package ru.medvedev.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HTMLParser {
    public static void main(String[] args) throws IOException {
        Document document = Jsoup.connect("http://moscompass.ru/news/").get();
        Elements startNames = document.select("table > tbody > tr > td.nh > p.nyh");
        Elements startData = document.select( "table > tbody > tr > td.nh > p.nd");
        ArrayList arrayList = new ArrayList<Element>(startNames);
        for ( Element i : startNames){
            System.out.println(i.text());
        }


    }
}
