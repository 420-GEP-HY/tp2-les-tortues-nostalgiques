package ca.qc.nostalgiques.tortues.lecteurrssmedia;

import org.junit.Test;
import org.junit.Assert;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FluxRSSTest {

        @Test
        public void TestAjoutRSS()
        {
            List<FluxRSS> mesFlux = new ArrayList<FluxRSS>();
            try
            {
                mesFlux.add(new FluxRSS("https://ici.radio-canada.ca/rss"));
                mesFlux.add(new FluxRSS("http://www.lapresse.ca/rss.php"));
                mesFlux.add(new FluxRSS("http://rss.slashdot.org/Slashdot/slashdotMain"));
                mesFlux.add(new FluxRSS("https://www.commentcamarche.net/rss/"));
                mesFlux.add(new FluxRSS("https://www.developpez.com/index/rss"));
                mesFlux.add(new FluxRSS("http://feeds.twit.tv/sn.xml"));
                mesFlux.add(new FluxRSS("http://feeds.twit.tv/sn_video_hd.xml"));
                mesFlux.add(new FluxRSS("http://feeds.podtrac.com/9dPm65vdpLL1"));
                mesFlux.add(new FluxRSS("http://visualstudiotalkshow.libsyn.com/rss"));
            }
            catch (Exception e)
            {
                Assert.fail("Les flux n'ont pas pu être tous ajoutés");
            }
        }

        @Test
        public void TestURLInvalide()
        {
            try
            {
                FluxRSSActivity activity = new FluxRSSActivity();
                activity.LireFlux(new URL("TEST"));
                Assert.fail("Ceci doit donner une erreur");
            }
            catch (Exception e) {}
        }

}
