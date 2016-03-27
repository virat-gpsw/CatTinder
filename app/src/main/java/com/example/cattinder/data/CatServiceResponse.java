package com.example.cattinder.data;

import com.example.cattinder.api.CatService;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Object returned from the {@link CatService}.
 *
 * Note: Gson is used by Retrofit and parses the response to
 * an object of this class.
 */
public class CatServiceResponse {
  private List<CatObj> items = new ArrayList<>();

  public List<CatObj> getCats() {
    return items;
  }

  public static class CatObj{
    private String link;
    private String snippet;

    public CatObj() {
      link = "";
      snippet = "";
    }

    public CatObj(String link, String snippet) {
      this.link = link;
      this.snippet = snippet;
    }

    public Uri getImageUri() {
      return Uri.parse(link);
    }

    public String getDescription() {
      return snippet;
    }

    @Override public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      CatObj cat = (CatObj) o;
      return link.equals(cat.link) && snippet.equals(cat.snippet);

    }

    @Override public int hashCode() {
      int result = link.hashCode();
      result = 31 * result + snippet.hashCode();
      return result;
    }
  }
}
