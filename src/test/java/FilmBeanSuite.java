import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import ru.cybern.kinoserver.mobileapi.controllers.IFilmBean;
import ru.cybern.kinoserver.mobileapi.db.IFilmDAO;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmEntity;

import javax.inject.Inject;

import static org.junit.Assert.assertTrue;


@RunWith(Arquillian.class)
public class FilmBeanSuite {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, IFilmBean.class.getPackage(), FilmEntity.class.getPackage(), IFilmDAO.class.getPackage())
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    IFilmBean filmBean;

    @Test
    public void saveFilmTest() {
        int before = filmBean.getFilms().size();
        FilmEntity film = new FilmEntity();
        film.setName("Die Hard");
        film.setRating(5.0);
        film.setYear(1993);

        filmBean.saveFilm(film);
        int after = filmBean.getFilms().size();

        assertTrue("Film count should increase after adding new film", after>before);

    }


}