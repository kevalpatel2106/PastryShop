package com.kevalpatel2106.pastryshop.repository.network

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by Keval on 05/06/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
@RunWith(JUnit4::class)
class ContactItemTest {
    private val testPhone = "3726583269"
    private val testEmail = "test@example.com"
    private val testTwitter = "http://twitter.com/kevalpatel2106"

    @Test
    fun checkInitialize() {
        val contact = ContactItem(
                phone = testPhone,
                email = testEmail,
                twitter = testTwitter
        )

        assertEquals(testPhone, contact.phone)
        assertEquals(testEmail, contact.email)
        assertEquals(testTwitter, contact.twitter)
    }

    @Test
    fun checkToContact() {
        val contactItem = ContactItem(
                phone = testPhone,
                email = testEmail,
                twitter = testTwitter
        )

        val contact = contactItem.toContact()

        assertEquals(testPhone, contact.phone)
        assertEquals(testEmail, contact.email)
        assertEquals(testTwitter, contact.twitter)
    }

    @Test
    fun checkEquals() {
        val contact = ContactItem(phone = testPhone, email = testEmail, twitter = testTwitter)
        val contact1 = ContactItem(phone = testPhone, email = testEmail, twitter = testTwitter)

        assertEquals(contact1, contact)
    }

    @Test
    fun checkNotEquals() {
        val contact = ContactItem(phone = testPhone, email = testEmail, twitter = testTwitter)
        val contact1 = ContactItem(phone = testPhone, email = testEmail, twitter = testTwitter)
        val contact2 = ContactItem(phone = "${testPhone}1 ", email = testEmail, twitter = testTwitter)

        assertEquals(contact1, contact)
        assertNotEquals(contact, contact2)
        assertNotEquals(contact1, contact2)
    }

    @Test
    fun checkHashCode() {
        val contact = ContactItem(phone = testPhone, email = testEmail, twitter = testTwitter)
        val contact1 = ContactItem(phone = testPhone, email = testEmail, twitter = testTwitter)
        val contact2 = ContactItem(phone = "${testPhone}1 ", email = testEmail, twitter = testTwitter)

        assertEquals(contact1.hashCode(), contact.hashCode())
        assertNotEquals(contact.hashCode(), contact2.hashCode())
        assertNotEquals(contact1.hashCode(), contact2.hashCode())
    }
}