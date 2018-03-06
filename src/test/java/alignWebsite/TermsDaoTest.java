package alignWebsite;

import java.util.List;


import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mehaexample.asdDemo.dao.TermsDao;
import org.mehaexample.asdDemo.enums.Term;
import org.mehaexample.asdDemo.enums.TermType;
import org.mehaexample.asdDemo.model.Terms;

public class TermsDaoTest {

	private static TermsDao termsDao;

	@BeforeClass
	public static void init() {
		termsDao = new TermsDao();
	}
	
	@Test
	public void addTermTest() {
		Terms newTerm = new Terms(Term.FALL, 2021, TermType.QUARTER);
		Terms term = termsDao.addTerm(newTerm);
        Assert.assertTrue(term.toString().equals(newTerm.toString()));
        termsDao.deleteTerm(term.getTermId()); 
	}
	
	@Test
	public void addNullTermTest() {
		Terms term = termsDao.addTerm(null);
		Assert.assertNull(term);
	}
	 
	@Test
	public void getAllTerms() {
		List<Terms> terms = termsDao.getAllTerms();
		Terms term = new Terms(Term.SUMMER, 2029, TermType.QUARTER);
		termsDao.addTerm(term);
		List<Terms> newTerms = termsDao.getAllTerms();
        Assert.assertTrue(newTerms.size() == terms.size() + 1);
        termsDao.deleteTerm(term.getTermId());
	}
	
	@Test
    public void deleteTermRecordTest() {
		List<Terms> terms = termsDao.getAllTerms();
		int oldTermSize = terms.size();		
		Terms newTerm = new Terms(Term.SUMMER, 2029, TermType.QUARTER);
		termsDao.addTerm(newTerm);
        termsDao.deleteTerm(newTerm.getTermId());
		int newTermSize = terms.size();	
		Assert.assertEquals(oldTermSize, newTermSize); 
    }
	
	@Test
	public void deleteNullTermTest() {
		boolean result = termsDao.deleteTerm(-1);
		Assert.assertFalse(result);
	}
}
