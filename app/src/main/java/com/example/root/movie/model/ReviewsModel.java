package com.example.root.movie.model;

import java.util.List;

public class ReviewsModel {

    /**
     * id : 49026
     * page : 1
     * results : [{"id":"5010553819c2952d1b000451","author":"Travis Bell","content":"I felt like this was a tremendous end to Nolan's Batman trilogy. The Dark Knight Rises may very well have been the weakest of all 3 films but when you're talking about a scale of this magnitude, it still makes this one of the best movies I've seen in the past few years.\r\n\r\nI expected a little more _Batman_ than we got (especially with a runtime of 2:45) but while the story around the fall of Bruce Wayne and Gotham City was good I didn't find it amazing. This might be in fact, one of my only criticisms\u2014it was a long movie but still, maybe too short for the story I felt was really being told. I feel confident in saying this big of a story could have been split into two movies.\r\n\r\nThe acting, editing, pacing, soundtrack and overall theme were the same 'as-close-to-perfect' as ever with any of Christopher Nolan's other films. Man does this guy know how to make a movie!\r\n\r\nYou don't have to be a Batman fan to enjoy these movies and I hope any of you who feel this way re-consider. These 3 movies are without a doubt in my mind, the finest display of comic mythology ever told on the big screen. They are damn near perfect.","url":"http://j.mp/QSjAK2"},{"id":"5013bc76760ee372cb00253e","author":"Chris","content":"I personally thought this film is on par if not better than the Dark Knight.\r\n\r\nWhilst some think the film is too long for the story I didn't find this. The length of the film is longer than some (but doesn't feel it), I liked that the film took it's time rather than shoving more elements in it - I think this contributed to the dramatic ending (much like a classical piece of music will be relaxed and calm before the final crescendo).\r\n\r\nAt the end of the day whether you like this film will boil down to if you like films Christopher Nolan has directed and/or you like the Christopher Nolan Batman series so far.\r\n\r\nStupendously good film in my opinion.","url":"http://j.mp/P18dg1"},{"id":"51429a7519c29552e10eba16","author":"GeekMasher","content":"The Dark Knight Rises is one of the best movies to come out in 2012. The story compels you to watch it time and time again. It also has I of, I my opinion, the best bad guys in any movie, Bane! Batman was well played as all ways and the cast where well selected. I think this movie is the best batman to see the light of day or the darkest nights (pun intended).","url":"http://j.mp/19nyIWg"}]
     * total_pages : 1
     * total_results : 3
     */

    private int id;
    private int page;
    private int total_pages;
    private int total_results;
    /**
     * id : 5010553819c2952d1b000451
     * author : Travis Bell
     * content : I felt like this was a tremendous end to Nolan's Batman trilogy. The Dark Knight Rises may very well have been the weakest of all 3 films but when you're talking about a scale of this magnitude, it still makes this one of the best movies I've seen in the past few years.

     I expected a little more _Batman_ than we got (especially with a runtime of 2:45) but while the story around the fall of Bruce Wayne and Gotham City was good I didn't find it amazing. This might be in fact, one of my only criticisms—it was a long movie but still, maybe too short for the story I felt was really being told. I feel confident in saying this big of a story could have been split into two movies.

     The acting, editing, pacing, soundtrack and overall theme were the same 'as-close-to-perfect' as ever with any of Christopher Nolan's other films. Man does this guy know how to make a movie!

     You don't have to be a Batman fan to enjoy these movies and I hope any of you who feel this way re-consider. These 3 movies are without a doubt in my mind, the finest display of comic mythology ever told on the big screen. They are damn near perfect.
     * url : http://j.mp/QSjAK2
     */

    private List<ResultsBean> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        private String id;
        private String author;
        private String content;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
