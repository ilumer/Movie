package com.example.root.movie.model;

import java.util.List;

/**
 * Created by root on 16-6-7.
 */
public class Trailers {

    /**
     * id : 550
     * results : [{"id":"533ec654c3a36854480003eb","iso_639_1":"en","key":"SUXWAEX2jlg","name":"Trailer 1","site":"YouTube","size":720,"type":"Trailer"}]
     */

    private int id;
    private List<Trailer> results;

    public void setId(int id) {
        this.id = id;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public List<Trailer> getResults() {
        return results;
    }

    public class Trailer {
        /**
         * id : 533ec654c3a36854480003eb
         * iso_639_1 : en
         * key : SUXWAEX2jlg
         * name : Trailer 1
         * site : YouTube
         * size : 720
         * type : Trailer
         */

        private String id;
        private String key;
        private String name;
        private String site;
        private int size;
        private String type;

        public void setId(String id) {
            this.id = id;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public String getKey() {
            return key;
        }

        public String getName() {
            return name;
        }

        public String getSite() {
            return site;
        }

        public int getSize() {
            return size;
        }

        public String getType() {
            return type;
        }
    }
}
