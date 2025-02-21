package com.resource.deadliner.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
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

    public static Week ParseWeek() {
        try {
            Week week = new Week();
            
            Document document = Jsoup.connect(getUrl("6204-090301D")).get();
            Elements weekNumber = document.select(".week-nav-current_week");
            int currentWeekNumber = parseWeekNumber(weekNumber.text());
            week.setWeekNumber(currentWeekNumber);

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
                week.addDay(day);
            } 

            int lessonCount = 0;

            for (Element item : scheduleElements) {
                for (Element lessonItem : item.select(".schedule__lesson")) {
                    Lesson lesson = new Lesson();
                    if (lessonItem.select(".schedule__discipline").text() != "") {
                        lesson.setType(LessonType.fromDisplayName(lessonItem.select(".lesson-type-2__bg").text()));
                        lesson.setTeacher(lessonItem.select(".schedule__teacher").text());
                        lesson.setClassroom(lessonItem.select(".schedule__place").text());
                    }
                    lesson.setOrderNumber((lessonCount - 1) / 6);
                    week.getDay((lessonCount - 1) % 6).addLesson(lesson);
                }
                lessonCount++;
            }

            return week;

        } catch (Exception e) {
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
                    .setHeader("referer", "https://ssau.ru/rasp?groupId=799359533")
                    .setHeader("sec-ch-ua", "\"Not(A:Brand\";v=\"99\", \"Google Chrome\";v=\"133\", \"Chromium\";v=\"133\"")
                    .setHeader("sec-ch-ua-mobile", "?0")
                    .setHeader("sec-ch-ua-platform", "\"Linux\"")
                    .setHeader("sec-fetch-dest", "empty")
                    .setHeader("sec-fetch-mode", "cors")
                    .setHeader("sec-fetch-site", "same-origin")
                    .setHeader("sec-gpc", "1")
                    .setHeader("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36")
                    .setHeader("x-csrf-token", "cD2x9MpmB5py8119iUVmy8LfmZGSTPv8OLmnU1Qt")
                    .setHeader("x-requested-with", "XMLHttpRequest")
                    .setHeader("cookie", "hpvbg=0; laravel_session=l45K3G7ZXndas5eNT8cU7MGS6cTzL5OfbEHOLMys; XSRF-TOKEN=eyJpdiI6Ik1HNEJWWWsrMjYzV2JZNmpcL3lVbGtnPT0iLCJ2YWx1ZSI6IldTUWlIa1wvUkF6djBOaWE5ZU5rejc3OFBxb1hhVDlRWlp0VDhDZ3YwTTN3TEpoNUd4eGJROWxvbHhnNjFNXC9wdyIsIm1hYyI6IjNjY2QwZjc3NjU0NTEzMDI1NjQ1MmQxM2JiNDFlMDY1YmZlZTI4OGIzZmFjN2QxNDc4YWE4ZTAyNWYyODc0NTMifQ%3D%3D")
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
