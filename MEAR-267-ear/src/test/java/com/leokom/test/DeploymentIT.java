package com.leokom.test;

import static org.junit.Assert.assertNotNull;

import com.leokom.controller.MemberRegistration;
import com.leokom.model.Member;
import java.io.File;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class DeploymentIT {

  @Deployment
  public static Archive<?> createTestArchive() {
    final JavaArchive testJar = ShrinkWrap.createFromZipFile(JavaArchive.class,
        new File(System.getProperty("arquillian.testJar")));
    final EnterpriseArchive testEar = ShrinkWrap.createFromZipFile(EnterpriseArchive.class,
        new File(System.getProperty("arquillian.testEar")));
    return testEar.addAsLibraries(testJar);
  }

  @Inject
  private MemberRegistration memberRegistration;

  @Inject
  private Logger log;

  @Test
  public void testRegister() throws Exception {
    Member newMember = memberRegistration.getNewMember();
    newMember.setName("Jane Doe");
    newMember.setEmail("jane@mailinator.com");
    newMember.setPhoneNumber("2125551234");
    memberRegistration.register();
    assertNotNull(newMember.getId());
    log.info(newMember.getName() + " was persisted with id " + newMember.getId());
  }

}
