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
    final Logger log = Logger.getLogger(DeploymentIT.class.getName());
    final String testJarName = System.getProperty("arquillian.testJar");
    log.info("Loading test JAR from " + testJarName);
    final JavaArchive testJar = ShrinkWrap.createFromZipFile(JavaArchive.class,
        new File(testJarName));
    final String testEarName = System.getProperty("arquillian.testEar");
    log.info("Loading test EAR from " + testEarName);
    final EnterpriseArchive testEar = ShrinkWrap.createFromZipFile(EnterpriseArchive.class,
        new File(testEarName));
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
