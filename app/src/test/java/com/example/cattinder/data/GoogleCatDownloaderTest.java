package com.example.cattinder.data;

import com.example.cattinder.api.CatService;
import com.example.test.RobolectricTest;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import retrofit.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;

public class GoogleCatDownloaderTest extends RobolectricTest {
    private static final String MOCK_CAT_LINK = "www.kittykat.com";
    private static final String MOCK_CAT_SNIPPET_ONE = "Hello, my name is Garfield.";
    private static final String MOCK_CAT_SNIPPET_TWO = "Meow, I am Tom 8-).";
    private static final String MOCK_CAT_SNIPPET_THREE = "Purr, I am Figaro ;-).";

    private GoogleCatDownloader mGoogleCatDownloader;

    @Before
    public void setup() {
        mGoogleCatDownloader = new GoogleCatDownloader(new TestCatService(), new GoogleSearchPaginator());
    }

    @Test
    public void getCats_shouldReturn_anObservable_ofListOfCats() throws Exception {
        // GIVEN
        TestSubscriber<List<Cat>> testSubscriber = new TestSubscriber<>();

        // WHEN
        Observable<List<Cat>> catObservable = mGoogleCatDownloader.getCats();
        catObservable.subscribe(testSubscriber);

        // THEN
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        List<Cat> cats = testSubscriber.getOnNextEvents().get(0); // Get the first onNext event
        assertThat(cats).isNotEmpty();
    }

    /**
     * Returns a "mock" CatServiceResponse Observable.
     */
    private static class TestCatService implements CatService {

        @Override
        public Observable<CatServiceResponse> getCats(@Query("start") int startIndex) {
            return Observable.create(new Observable.OnSubscribe<CatServiceResponse>(){
                @Override
                public void call(Subscriber<? super CatServiceResponse> subscriber) {
                    subscriber.onNext(new TestCatServiceResponse());
                    subscriber.onCompleted();
                }
            });
        }
    }

    /**
     * Returns a list of mock Cat objects.
     */
    private static class TestCatServiceResponse extends CatServiceResponse {

        @Override
        public List<CatObj> getCats() {
            return new ArrayList<CatObj>() {
                {
                    add(new CatObj(MOCK_CAT_LINK, MOCK_CAT_SNIPPET_ONE));
                    add(new CatObj(MOCK_CAT_LINK, MOCK_CAT_SNIPPET_TWO));
                    add(new CatObj(MOCK_CAT_LINK, MOCK_CAT_SNIPPET_THREE));
                }
            };
        }
    }
}