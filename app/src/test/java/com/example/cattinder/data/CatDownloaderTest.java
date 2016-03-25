package com.example.cattinder.data;

import com.example.cattinder.api.CatService;
import com.example.cattinder.api.TestSchedulerFactory;
import com.example.cattinder.data.CatServiceResponse.Cat;
import com.example.test.RobolectricTest;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import retrofit.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;

public class CatDownloaderTest extends RobolectricTest {
    private static final String MOCK_CAT_LINK = "www.kittykat.com";
    private static final String MOCK_CAT_SNIPPET_ONE = "Hey, my name is Garfield.";
    private static final String MOCK_CAT_SNIPPET_TWO = "Meow, I am Tom :).";
    private static final String MOCK_CAT_SNIPPET_THREE = "Purr, I am Figaro ;).";

    private CatDownloader catDownloader;

    @Before
    public void setup() {
        catDownloader = new CatDownloader(new TestCatService(), new CatPaginator());
    }

    @Test
    public void getCats_shouldReturn_anObservable_ofListOfCats() throws Exception {
        // GIVEN
        TestSubscriber<List<Cat>> testSubscriber = new TestSubscriber<>();

        // WHEN
        Observable<List<Cat>> catObservable = catDownloader.getCats();
        catObservable.subscribe(testSubscriber);

        // THEN
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        List<Cat> cats = testSubscriber.getOnNextEvents().get(0); // Get the first onNext event
        assertThat(cats).isNotEmpty();
    }

    /**
     * Returns a mock CatServiceResponse Observable.
     */
    private static class TestCatService implements CatService{

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
        public List<Cat> getCats() {
            return new ArrayList<Cat>() {
                {
                    add(new Cat(MOCK_CAT_LINK, MOCK_CAT_SNIPPET_ONE));
                    add(new Cat(MOCK_CAT_LINK, MOCK_CAT_SNIPPET_TWO));
                    add(new Cat(MOCK_CAT_LINK, MOCK_CAT_SNIPPET_THREE));
                }
            };
        }
    }
}