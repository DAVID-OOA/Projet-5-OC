package com.oconte.david.mynews.di;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.idling.CountingIdlingResource;

import com.oconte.david.mynews.calls.NYTCallsMostPopular;
import com.oconte.david.mynews.calls.NYTCallsSearch;
import com.oconte.david.mynews.calls.NYTCallsSports;
import com.oconte.david.mynews.calls.NYTCallsTopStories;
import com.oconte.david.mynews.NYTFactory;
import com.oconte.david.mynews.NYTService;

public class Injection {

    public static final CountingIdlingResource resource = new CountingIdlingResource("NewYorkTimeIdling");

    // For Calls TopStories
    public static NYTCallsTopStories getTopStories(NYTService service, CountingIdlingResource resource) {
        NYTCallsTopStories topStories = new NYTCallsTopStories(service, resource);
        return topStories;
    }

    public static  NYTService getService(){
        return NYTFactory.getRetrofit().create(NYTService.class);
    }

    // For Calls Sports
    public static NYTCallsSports getSports(NYTService service, CountingIdlingResource resource) {
        NYTCallsSports sports = new NYTCallsSports(service, resource);
        return sports;
    }

    // For Calls MostPopular
    public static NYTCallsMostPopular getMostPopular(NYTService service, CountingIdlingResource resource) {
        NYTCallsMostPopular mostPopular = new NYTCallsMostPopular(service, resource);
        return mostPopular;
    }

    // For Calls Search
    public static NYTCallsSearch getSearch(NYTService service, CountingIdlingResource resource) {
        return new NYTCallsSearch(service, resource);
    }

}
