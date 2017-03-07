package com.example.root.movie;

/**
 * Created by ilumer on 17-3-6.
 */
public class PopMovieFragmentPresenterTest {
/*

    @Test
    public void shouldPassMovieToView(){
        // given
        MoviesFragmentView view = new MockView();
        MoviesRepository repository = new MockMoviesRepository(true);

        // when
        PopMovieFragmentPresenter presenter = new PopMovieFragmentPresenter(view,repository);
        presenter.loadNetMovies();

        // then
        Assert.assertEquals(true,((MockView)view).displayMoviesWithMoviesCalled);

    }

    @Test
    public void shouldPassNoMovieToView(){
        MoviesFragmentView view = new MockView();
        MoviesRepository repository = new MockMoviesRepository(false);

        PopMovieFragmentPresenter presenter = new PopMovieFragmentPresenter(view,repository);
        presenter.loadLocalMovies();

        Assert.assertEquals(true,((MockView) view).displayNoMoviesWithMoviesCalled);
    }


    private class MockView implements MoviesFragmentView{

        boolean displayMoviesWithMoviesCalled;
        boolean displayNoMoviesWithMoviesCalled;

        @Override
        public void displayMovies(List<MovieContract> movies) {
            displayMoviesWithMoviesCalled = true;
        }

        @Override
        public void displayNoMovies() {
            displayNoMoviesWithMoviesCalled = true;
        }
    }

    private class MockMoviesRepository implements MoviesRepository {

        private boolean returnSomeMovies;

        public MockMoviesRepository(boolean returnSomeMovies) {
            this.returnSomeMovies = returnSomeMovies;
        }

        @Override
        public List<MovieContract> getPopMoviesFromLocal() {
            if (returnSomeMovies)
                return Arrays.asList(new MovieContract(),new MovieContract(),new MovieContract());
            else
                return Collections.emptyList();
        }

        @Override
        public List<MovieContract> getPopMoviesFromNet() {
            if (returnSomeMovies)
                return Arrays.asList(new MovieContract(),new MovieContract(),new MovieContract());
            else
                return Collections.emptyList();
        }
    }
}*/
}
