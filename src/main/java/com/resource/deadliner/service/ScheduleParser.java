package com.resource.deadliner.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.resource.deadliner.model.Day;
import com.resource.deadliner.model.DayOfWeek;
import com.resource.deadliner.model.Lesson;
import com.resource.deadliner.model.LessonType;
import com.resource.deadliner.model.Week;


public class ScheduleParser {

    public static Week ParseWeek(String groupName, String weekNumber) {
        try {
            Week week = new Week();
            
            Document document = Jsoup.connect(getUrl(groupName) + "&selectedWeek=" + weekNumber).get();
            week.setWeekNumber(Integer.parseInt(weekNumber));

            Elements dateElements = document.select(".schedule__head-date");
            ArrayList<String> dateList = new ArrayList<String>();
            for (Element date : dateElements) {
                dateList.add(date.text());
            }

            Elements scheduleElements = document.select(".schedule__item");
            for (int i = 0; i < 6; i++) {
                Day day = new Day();
                day.setDayOfWeek(DayOfWeek.fromIndex(i));
                day.setWeek(week);
                day.setDate(LocalDate.parse(dateList.get(i), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                week.addDay(day);
            } 

            int lessonCount = 0;

            for (Element item : scheduleElements) {
                for (Element lessonItem : item.select(".schedule__lesson")) {
                    Lesson lesson = new Lesson();
                    if (lessonItem.select(".schedule__discipline").text() != "") {
                        lesson.setType(LessonType.fromDisplayName(lessonItem.select(".schedule__lesson-type-chip").text()));
                        lesson.setTeacher(lessonItem.select(".schedule__teacher").text());
                        lesson.setClassroom(lessonItem.select(".schedule__place").text());
                    }
                    lesson.setOrderNumber((lessonCount - 1) / 6);
                    week.getDay((lessonCount - 1) % 6).addLesson(lesson);
                }
                lessonCount++;
            }
            System.out.println(week);
            return week;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int parseWeekNumber(String week) {
        String numberOnly = week.replaceAll("[^0-9]", "");
        return Integer.parseInt(numberOnly);
    }

    private static String getUrl(String groupName) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://ssau.ru/rasp/search"))
                .POST(BodyPublishers.ofString("text=" + groupName))
                .setHeader("accept", "application/json")
                .setHeader("accept-language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
                .setHeader("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                .setHeader("dnt", "1")
                .setHeader("origin", "https://ssau.ru")
                .setHeader("priority", "u=1, i")
                .setHeader("referer", "https://ssau.ru/rasp")
                .setHeader("sec-ch-ua", "\"Not(A:Brand\";v=\"99\", \"Google Chrome\";v=\"133\", \"Chromium\";v=\"133\"")
                .setHeader("sec-ch-ua-mobile", "?0")
                .setHeader("sec-ch-ua-platform", "\"Linux\"")
                .setHeader("sec-fetch-dest", "empty")
                .setHeader("sec-fetch-mode", "cors")
                .setHeader("sec-fetch-site", "same-origin")
                .setHeader("sec-gpc", "1")
                .setHeader("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36")
                .setHeader("x-csrf-token", "tV8HtNpV21NdlhxirtCW07NA1B3iEmT95G0NEBwn")
                .setHeader("x-requested-with", "XMLHttpRequest")
                .setHeader("cookie", "hpvbg=0; laravel_session=to4xiDqiWS51rwNRrTtnpfx1N4cCrWeyB5HHZpLC; XSRF-TOKEN=eyJpdiI6IjZZZ0U0U0t4YnpWRjhPdFcrWkRueEE9PSIsInZhbHVlIjoiNnlOK00rVE9lRHFoSnQwamIrazBsXC9HbXdJM05KdUkxQmUweWFuTHp0RDl1Y2g5bDRpclNoRGdNdFlPQ3ZLQUIiLCJtYWMiOiI4MzY1NzVhNTc2NjgzY2RlM2MyYzY0YmM3M2VjM2JkMGFlMWMyNjM2ZDliNzkzNzI2NGZhMTkzYTU4MjViZWY2In0%3D")
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            JSONArray jsonArray = new JSONArray(responseBody);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String url = jsonObject.getString("url");

            String result = url.substring(url.indexOf("rasp?groupId="));
            return "https://ssau.ru/" + result;
        } catch (Exception e) {
            return "";
        }
        
    }
}
