package com.example.cattinder.api;

import com.example.cattinder.data.CatServiceResponse;
import com.example.cattinder.data.CatServiceResponse.Cat;
import com.example.test.RobolectricTest;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import retrofit.http.Query;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;

public class CatAdapterTest extends RobolectricTest {
    static final String CAT_LINK_ONE = "link_one";
    static final String CAT_LINK_TWO = "link_two";

    static final String CAT_SNIPPET_ONE = "snippet_one";
    static final String CAT_SNIPPET_TWO = "snippet_two";

    static final Cat CAT_ONE = new Cat(CAT_LINK_ONE, CAT_SNIPPET_ONE);
    static final Cat CAT_TWO = new Cat(CAT_LINK_TWO, CAT_SNIPPET_TWO);

    private CatAdapter mCatAdapter;

    @Before
    public void setup() {
        mCatAdapter = new CatAdapter(new TestCatService(getMockCats()), new TestSchedulerFactory());
    }

    @Test
    public void getCats_shouldReturnObservable_ofListOfCats() throws Exception {
        // Given
        int startIndex = 1;

        // When
        Observable<List<Cat>> catObservable = mCatAdapter.getCats(startIndex);
        TestSubscriber<List<Cat>> subscriber = new TestSubscriber<>();
        catObservable.subscribe(subscriber);
        List<Cat> cats = subscriber.getOnNextEvents().get(0);

        // Then
        assertThat(cats.get(0)).isEqualTo(CAT_ONE);
    }

    private static class TestCatService implements CatService {

        private List<Cat> mCats;

        public TestCatService(List<Cat> cats) {
            mCats = cats;
        }

        @Override
        public CatServiceResponse getCats(@Query("start") int startIndex) {
            return new TestCatServiceResponse(mCats);
        }
    }

    private static class TestCatServiceResponse extends CatServiceResponse {
        private List<Cat> mCats;

        public TestCatServiceResponse(List<Cat> cats) {
            mCats = cats;
        }

        @Override
        public List<Cat> getCats() {
            return mCats;
        }
    }

    List<Cat> getMockCats() {
        return new ArrayList<Cat>() {
            {
                add(CAT_ONE);
                add(CAT_TWO);
            }
        };
    }

}