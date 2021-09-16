package com.example.a_gregos_capstone;

import java.util.ArrayList;
import java.util.Arrays;

public class HelperUtility {
    public static final int ADD_PERSON_REQUEST = 1;
    public static final int EDIT_PERSON_REQUEST = 2;
    public static final int ADD_DATE_REQUEST = 3;
    public static final int EDIT_DATE_REQUEST = 4;
    public static final int ADD_CAREER_REQUEST = 5;
    public static final int EDIT_CAREER_REQUEST = 6;
    public static final int ADD_NOTE_REQUEST = 7;
    public static final int EDIT_NOTE_REQUEST = 8;
    public static final int ADD_PAST_TIME_REQUEST = 9;
    public static final int EDIT_PAST_TIME_REQUEST = 10;
    public static int activeCategory;
    public static int activePerson;
    public static int activeDate;
    public static int activePastTime;
    public static int activeNote;
    public static int activeCareer;


    public static ArrayList<String> careerCategories() {
        ArrayList<String> careers = new ArrayList<String>();
        careers.add(0, "Administrative");
        careers.add(1, "Arts & Design");
        careers.add(2, "Business");
        careers.add(3, "Customer Service");
        careers.add(4, "Education");
        careers.add(5, "Engineering");
        careers.add(6, "Finance & Accounting");
        careers.add(7, "Health Care");
        careers.add(8, "Human Resources");
        careers.add(9, "Information Technology");
        careers.add(10, "Law Enforcement");
        careers.add(11, "Marketing");
        careers.add(12, "Military");
        careers.add(13, "Operations");
        careers.add(14, "Project Management");
        careers.add(15, "Research & Science");
        careers.add(16, "Retail");
        careers.add(17, "Sales");
        careers.add(18, "Skilled Labor");
        careers.add(19, "Transportation");
        return careers;
    }

    public static ArrayList<String> getDateTypes() {
        ArrayList<String> categories = new ArrayList<String>();
        categories.add(0, "Birthday");
        categories.add(1, "Marriage anniversary");
        categories.add(2, "Daughter's birthday");
        categories.add(3, "Son's birthday");
        categories.add(4, "Spouse's birthday");
        categories.add(5, "Parent's birthday");
        categories.add(6, "First meet");
        return categories;
    }

    public static String addElement(String original, int elementID) {
        String newString;
        if (filterString(original).isEmpty()) {
            newString = String.valueOf(elementID);
        } else {
            ArrayList<Integer> array = stringToArray(original);
            array.add(elementID);
            newString = array.toString();
        }
        return newString;
    }

    public static String removeElement(String original, int elementID) {
        String newString;
        ArrayList<Integer> array = stringToArray(original);
        array.remove(array.indexOf(elementID));

        if (array.size() == 0) {
            newString = "";
        } else {
            newString = array.toString();
        }
        return newString;
    }

    public static ArrayList<Integer> stringToArray(String toConvert) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        String replacement = filterString(toConvert);
        ArrayList<String> strings = new ArrayList<String>(Arrays.asList(replacement.split(",")));
        for (int i = 0; i < strings.size(); i++) {
            arrayList.add(i, Integer.parseInt(strings.get(i)));
        }
        return arrayList;
    }

    public static String filterString(String toFilter) {
        String filteredString = toFilter.replace("[", "");
        filteredString = filteredString.replace("]", "");
        filteredString = filteredString.replace("(", "");
        filteredString = filteredString.replace(")", "");
        filteredString = filteredString.replace("-", "");
        filteredString = filteredString.replace(" ", "");
        return filteredString;
    }

    public static int getActiveCategory() {
        return activeCategory;
    }

    public static void setActiveCategory(int activeCategory) {
        HelperUtility.activeCategory = activeCategory;
    }

    public static int getActivePerson() {
        return activePerson;
    }

    public static void setActivePerson(int activePerson) {
        HelperUtility.activePerson = activePerson;
    }

    public static int getActiveDate() {
        return activeDate;
    }

    public static void setActiveDate(int activeDate) {
        HelperUtility.activeDate = activeDate;
    }

    public static int getActivePastTime() {
        return activePastTime;
    }

    public static void setActivePastTime(int activePastTime) {
        HelperUtility.activePastTime = activePastTime;
    }

    public static int getActiveNote() {
        return activeNote;
    }

    public static void setActiveNote(int activeNote) {
        HelperUtility.activeNote = activeNote;
    }

    public static int getActiveCareer() {
        return activeCareer;
    }

    public static void setActiveCareer(int careerID) {
        HelperUtility.activeCareer = careerID;
    }

}
