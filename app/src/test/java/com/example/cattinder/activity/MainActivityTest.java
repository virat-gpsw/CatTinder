package com.example.cattinder.activity;

import com.example.cattinder.api.CatService;
import com.example.cattinder.data.CatServiceResponse;
import com.example.test.ActivityTestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import retrofit.http.Query;
import rx.Observable;
import rx.Subscriber;

import static com.example.cattinder.data.CatServiceResponse.*;

/**
 * If you'd like to try a Robolectric style test of your Activity
 * then implement JUnit tests here.
 *
 * See: {@link ActivityTestCase}
 */
public class MainActivityTest extends ActivityTestCase<MainActivity>{
  static final String CAT_LINK_ONE = "link_one";
  static final String CAT_LINK_TWO = "link_two";

  static final String CAT_SNIPPET_ONE = "snippet_one";
  static final String CAT_SNIPPET_TWO = "snippet_two";

  static final CatObj CAT_ONE = new CatObj(CAT_LINK_ONE, CAT_SNIPPET_ONE);
  static final CatObj CAT_TWO = new CatObj(CAT_LINK_TWO, CAT_SNIPPET_TWO);

  public MainActivityTest() {
    super(MainActivity.class);
  }

  @Before
  public void setup() {

  }

  @Test
  public void getCats_shouldReturnNonEmpty_listOfCats() throws Exception {

  }

  private static class TestMainActivity extends MainActivity {

  }

  private static class TestCatService implements CatService{

    @Override
    public Observable<CatServiceResponse> getCats(@Query("start") int startIndex) {
      return Observable.create(new Observable.OnSubscribe<CatServiceResponse>(){
        @Override
        public void call(Subscriber<? super CatServiceResponse> subscriber) {
          subscriber.onNext(new TestCatServiceResponse(getMockCats()));
          subscriber.onCompleted();
        }
      });
    }

    List<CatObj> getMockCats() {
      return new ArrayList<CatObj>(){
        {
          add(CAT_ONE);
          add(CAT_TWO);
        }
      };
    }
  }

  private static class TestCatServiceResponse extends CatServiceResponse {
    private List<CatObj> mCats;

    public TestCatServiceResponse(List<CatObj> cats) {
      mCats = cats;
    }

    @Override
    public List<CatObj> getCats() {
      return mCats;
    }
  }
}
