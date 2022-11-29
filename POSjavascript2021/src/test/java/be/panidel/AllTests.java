package be.panidel;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import be.panidel.dao.DataFacadeTest;
import be.panidel.dao.ItemDaoTest;
import be.panidel.dao.SaleDaoTest;

@RunWith(Suite.class)
@SuiteClasses({ ItemDaoTest.class, SaleDaoTest.class, DataFacadeTest.class })
public class AllTests {

}
