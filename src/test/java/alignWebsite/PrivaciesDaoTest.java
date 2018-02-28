package alignWebsite;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mehaexample.asdDemo.dao.PrivaciesDao;
import org.mehaexample.asdDemo.model.Privacies;

import static org.junit.Assert.*;

public class PrivaciesDaoTest {
    private static PrivaciesDao privaciesDao;

    @BeforeClass
    public static void init() {
        privaciesDao = new PrivaciesDao();
    }

    @Test
    public void addPrivacyRecordTest() {
        Privacies privacy = new Privacies("111234544", true, true, true,
                true, true, true, true, true,
                true, true, true, true, true);

        privaciesDao.addPrivacyRecord(privacy);
        Assert.assertTrue(privaciesDao.ifNuidExists("111234544"));
        privaciesDao.deletePrivacyRecord(privacy);
    }

    @Test
    public void getPrivacyRecordByNeuIdTest() {
        Privacies privacy = new Privacies("111234544", true, true, true,
                true, true, true, true, true,
                true, true, true, true, true);

        privaciesDao.addPrivacyRecord(privacy);
        Assert.assertTrue(privaciesDao.getPrivacyRecordByNeuId("111234544").isZipShown());
        privaciesDao.deletePrivacyRecord(privacy);
    }

    @Test
    public void updatePrivacyRecordTest() {
        Privacies privacy = new Privacies("111234544", true, true, true,
                true, true, true, true, true,
                true, true, true, true, true);

        privaciesDao.addPrivacyRecord(privacy);

        privacy.setAddressShown(false);
        privaciesDao.updatePrivacyRecord(privacy);

        Assert.assertTrue(!privaciesDao.getPrivacyRecordByNeuId("111234544").isAddressShown());
        privaciesDao.deletePrivacyRecord(privacy);
    }

    @Test
    public void deletePrivacyRecord() {
        Privacies privacy = new Privacies("111234544", true, true, true,
                true, true, true, true, true,
                true, true, true, true, true);

        privaciesDao.addPrivacyRecord(privacy);
        Assert.assertTrue(privaciesDao.ifNuidExists("111234544"));
        privaciesDao.deletePrivacyRecord(privacy);
        Assert.assertTrue(!privaciesDao.ifNuidExists("111234544"));
    }

    @Test
    public void ifNuidExists() {
        Privacies privacy = new Privacies("111234544", true, true, true,
                true, true, true, true, true,
                true, true, true, true, true);

        privaciesDao.addPrivacyRecord(privacy);
        Assert.assertTrue(privaciesDao.ifNuidExists("111234544"));
        privaciesDao.deletePrivacyRecord(privacy);
        Assert.assertTrue(!privaciesDao.ifNuidExists("111234544"));
    }
}