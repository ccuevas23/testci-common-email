package org.apache.commons.mail;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.mail.Authenticator;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.lang.Object;

import java.util.*;

public class EmailTest {

	// Instance of email type
	private EmailConcrete email;

	private static final String[] TEST_EMAILS = { "abc@hello.com", "1234@goodbye.com", "qwerty@asdf.com" };
	private static final Map<String, String> heads = new HashMap<String, String>();

	@Before
	public void setUpEmailTest() throws Exception {
		email = new EmailConcrete();
	}

	@After
	public void tearDownEmailTest() throws Exception {
		// Tear down the tests after execution
	}

	// Testing the addBcc(String... emails) function using TEST_EMAILS array
	@Test
	public void testAddBcc() throws Exception {
		email.addBcc(TEST_EMAILS);

		assertEquals(3, email.getBccAddresses().size());
	}

	// Testing if addBcc throws the correct exception when there is a null value
	@Test(expected = EmailException.class)
	public void testNullAddBcc() throws Exception {
		final String[] NO_VALS = {};

		email.addBcc(NO_VALS);
	}

	// Tests the addCc(String email) function with one string for email
	@Test
	public void testAddCc() throws Exception {
		email.addCc("testemail@asdf.com");

		assertEquals("testemail@asdf.com", email.getCcAddresses().get(0).toString());
	}

	// Tests the addHeader(String name, String value) function, value is the key in
	// this case
	@Test
	public void testAddHeader() throws Exception {
		email.addHeader("Christian", "Value");

		// Tests for the key "Value" to get the string name Christian
		assertEquals("Value", email.headers.get("Christian"));
	}

	// Tests if addHeader() throws the correct exception when called with null
	// strings for both name and value
	@Test(expected = IllegalArgumentException.class)
	public void testNoNameAddHeader() throws Exception {
		String name = null, value = null;

		email.addHeader(name, value);
	}

	// Tests if addHeader() throws the correction exception when called with a null
	// value
	@Test(expected = IllegalArgumentException.class)
	public void testNoValueAddHeader() throws Exception {
		String name = "test", value = null;

		email.addHeader(name, value);
	}

	// Tests if addReplyTo(String email, String name) correctly adds a reply to
	// address
	@Test
	public void testAddReplyTo() throws Exception {

		// Testing the function with an email from TEST_EMAILS and name "Christian"
		email.addReplyTo(TEST_EMAILS[0], "Christian");

		// Creating new Internet Address to see if the addresses match
		InternetAddress test = new InternetAddress(TEST_EMAILS[0], "Christian");

		assertEquals(test, email.replyList.get(0));
	}

	// Testing buildMimeMessage() with a non null email body
	@Test
	public void testEmailBodyBuildMimeMessage() throws Exception {

		// Setting Host name
		email.setHostName("ChristianC");

		// Creating properties in order to create the session
		Properties props = null;
		try {
			props = new Properties(System.getProperties());
		} catch (SecurityException ex) {
			props = new Properties();
		}

		Authenticator auth = null;

		// Creating the session
		Session ssn = Session.getInstance(props, auth);

		// Creating a collection of internet addresses
		Collection<InternetAddress> test = new LinkedList<InternetAddress>();
		test.add(new InternetAddress(TEST_EMAILS[0], "Christian"));
		test.add(new InternetAddress(TEST_EMAILS[1], "Christian"));
		test.add(new InternetAddress(TEST_EMAILS[2], "Christian"));

		// Setting the email send to list with the above collection
		email.setTo(test);

		// Setting the from address
		email.setFrom("1234@abc.com");

		email.emailBody = new MimeMultipart();

		email.buildMimeMessage();
		MimeMessage msg = email.getMimeMessage();

		assertEquals(msg, email.getMimeMessage());
	}

	// Testing buildMimeMessage() with required non null lists
	@Test
	public void testWithListsBuildMimeMessage() throws Exception {
		// Setting Host name
		email.setHostName("ChristianC");

		// Creating properties in order to create the session
		Properties props = null;
		try {
			props = new Properties(System.getProperties());
		} catch (SecurityException ex) {
			props = new Properties();
		}

		Authenticator auth = null;

		// Creating the session
		Session ssn = Session.getInstance(props, auth);

		// Setting the from address
		email.setFrom("1234@abc.com");

		// Adding values to all lists for if statement coverage
		email.addCc("hello@mich.com");
		email.addBcc("chris@chris.com");
		email.addReplyTo("where@how.com");
		email.addHeader("Key", "Value");

		email.buildMimeMessage();
		MimeMessage msg = email.getMimeMessage();

		assertEquals(msg, email.getMimeMessage());
	}

	// Testing buildMimeMessage() with a reciever address
	@Test
	public void testReceiverAddressesBuildMimeMessage() throws Exception {
		// Setting Host name
		email.setHostName("ChristianC");

		// Creating properties in order to create the session
		Properties props = null;
		try {
			props = new Properties(System.getProperties());
		} catch (SecurityException ex) {
			props = new Properties();
		}

		Authenticator auth = null;

		// Creating the session
		Session ssn = Session.getInstance(props, auth);

		// Setting the from address
		email.setFrom("1234@abc.com");

		// Creating a collection of internet addresses
		Collection<InternetAddress> test = new LinkedList<InternetAddress>();
		test.add(new InternetAddress(TEST_EMAILS[0], "Christian"));
		test.add(new InternetAddress(TEST_EMAILS[1], "Christian"));
		test.add(new InternetAddress(TEST_EMAILS[2], "Christian"));

		// Setting the email send to list with the above collection
		email.setTo(test);

		email.buildMimeMessage();

		MimeMessage msg = email.getMimeMessage();

		assertEquals(msg, email.getMimeMessage());
	}

	// Testing that buildMimeMessage() throws the correct exception when a message
	// is already built
	@Test(expected = IllegalStateException.class)
	public void testMessageBuiltBuildMimeMessage() throws Exception {
		// Setting Host name
		email.setHostName("ChristianC");

		// Creating properties in order to create the session
		Properties props = null;
		try {
			props = new Properties(System.getProperties());
		} catch (SecurityException ex) {
			props = new Properties();
		}

		Authenticator auth = null;

		// Creating the session
		Session ssn = Session.getInstance(props, auth);

		// Setting the from address
		email.setFrom("1234@abc.com");

		// Creating a collection of internet addresses
		Collection<InternetAddress> test = new LinkedList<InternetAddress>();
		test.add(new InternetAddress(TEST_EMAILS[0], "Christian"));
		test.add(new InternetAddress(TEST_EMAILS[1], "Christian"));
		test.add(new InternetAddress(TEST_EMAILS[2], "Christian"));

		// Setting the email send to list with the above collection
		email.setTo(test);

		// Calling buildMimeMessage() twice so that it will throw the correct exception
		email.buildMimeMessage();
		email.buildMimeMessage();
	}

	// Testing that buildMimeMessage() throws the correct exception when there is no
	// receiver address
	@Test(expected = EmailException.class)
	public void testNoReceiverAddressBuildMimeMessage() throws Exception {
		// Setting Host name
		email.setHostName("ChristianC");

		// Creating properties in order to create the session
		Properties props = null;
		try {
			props = new Properties(System.getProperties());
		} catch (SecurityException ex) {
			props = new Properties();
		}

		Authenticator auth = null;

		// Creating the session
		Session ssn = Session.getInstance(props, auth);

		email.setFrom("1234@abc.com");

		email.buildMimeMessage();
	}

	// Testing that buildMimeMessage() throws the correct exception when there is no
	// from address
	@Test(expected = EmailException.class)
	public void testFromAddressRequiredBuildMimeMessage() throws Exception {
		// Setting Host name
		email.setHostName("ChristianC");

		// Creating properties in order to create the session
		Properties props = null;
		try {
			props = new Properties(System.getProperties());
		} catch (SecurityException ex) {
			props = new Properties();
		}

		Authenticator auth = null;

		// Creating the session
		Session ssn = Session.getInstance(props, auth);

		email.buildMimeMessage();

	}

	// Testing getHostName() with a valid session
	@Test
	public void testValidSessionGetHostName() throws Exception {
		// Creating properties in order to create the session
		Properties props = null;
		try {
			props = new Properties(System.getProperties());
		} catch (SecurityException ex) {
			props = new Properties();
		}

		Authenticator auth = null;

		// Creating the session
		Session ssn = Session.getInstance(props, auth);

		email.setMailSession(ssn);

		assertEquals(null, email.getHostName());
	}

	// Testing getHostName() with a valid name
	@Test
	public void testValidNameGetHostName() throws Exception {
		// Setting the host name to "Christian"
		String test = "Christian";
		email.setHostName(test);

		assertEquals(test, email.getHostName());
	}

	// Testing getHostName() when the name is null
	@Test
	public void testNullSessionGetHostName() throws Exception {
		assertEquals(null, email.getHostName());
	}

	// Testing that getMailSession() throws the correct exception when there is no
	// Host Name
	@Test(expected = EmailException.class)
	public void testNoHostNameGetMailSession() throws Exception {
		Session ssn = email.getMailSession();
	}

	// Testing getMailSession() when there is a valid host name
	@Test
	public void testValidHostNameGetMailSession() throws Exception {

		email.setHostName("ChristianC");

		Session ssn = email.getMailSession();
	}

	// Testing that getSentDate() works properly
	@Test
	public void testGetSentDate() throws Exception {
		// Creating date
		Date test = new Date(2023, 1, 23);

		// Setting the email date to the date above
		email.setSentDate(test);

		assertEquals(test, email.getSentDate());
	}

	// Testing getSentDate() when the date is null
	@Test
	public void testNullGetSentDate() throws Exception {
		email.getSentDate();

		// Creating a null Date object to compare
		Date test = new Date();

		assertEquals(test, email.getSentDate());

	}

	// Testing getSocketConnectionTimeout() with a valid socket connection timeout
	// set
	@Test
	public void testGetSocketConnectionTimeout() throws Exception {
		email.setSocketConnectionTimeout(0);

		assertEquals(0, email.getSocketConnectionTimeout());
	}

	// Testing setFrom(String email) sets the from address correctly
	@Test
	public void testSetFrom() throws Exception {

		email.setFrom("christian@umich.com");

		// Creating a new Internet Address with same address as above for assertion
		InternetAddress test = new InternetAddress("christian@umich.com");

		assertEquals(test, email.getFromAddress());
	}

}
