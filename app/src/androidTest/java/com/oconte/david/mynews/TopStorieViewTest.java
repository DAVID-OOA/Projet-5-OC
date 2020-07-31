package com.oconte.david.mynews;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.oconte.david.mynews.Models.Result;

import org.jetbrains.annotations.NotNull;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.CountDownLatch;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class TopStorieViewTest {

    private Result results;
    private String baseUrl = "http://127.0.0.1:9900";

    private int resId = R.id.fragment_main_recycler_view;

    /* the Activity of the Target application*/
    private MainActivity mActivity;

    /**
     * the {@link RecyclerView}
     */
    private RecyclerView mRecyclerView;

    /**
     * and it's item count
     */
    private int itemCount = 0;

    /**
     * such a {@link ActivityTestRule} can be used eg. for Intent.putExtra(),
     * alike one would pass command-line arguments to regular run configurations.
     */
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<MainActivity>(MainActivity.class, false, false);

    // Method allowing to outsource the creation of the mockwebserver object by passing the correct http code (200, 400 or other) and the correct json response.
    private MockWebServer setupServer(int code, String response) {
        MockWebServer server = new MockWebServer();
        server.setDispatcher(new Dispatcher() {
            @NotNull
            @Override
            public MockResponse dispatch(@NotNull RecordedRequest recordedRequest) throws InterruptedException {
                return new MockResponse().setResponseCode(code).setBody(response);
            }
        });
        return server;
    }

    @Test
    public void testCallsTopStorie() throws IOException, InterruptedException {

        MockWebServer server = setupServer(HttpURLConnection.HTTP_OK, AssetReader.getAsset(InstrumentationRegistry.getInstrumentation().getContext(), "topstories_response.json"));

        final CountDownLatch latch = new CountDownLatch(1);

        // Start the server.
        server.start(9900);

        //Start the MainActivity
        mActivityRule.launchActivity(null);

        //Test recyclerview is good.
        onView(withId(R.id.fragment_main_recycler_view)).check(matches(isDisplayed()));

        //Test all elements are good in the recyclerview.
        //onView(withId(R.id.fragment_main_date)).check(matches(withText("published_date")));
        onView(withId(R.id.fragment_main_date)).check(matches(isDisplayed()));

    }
}
