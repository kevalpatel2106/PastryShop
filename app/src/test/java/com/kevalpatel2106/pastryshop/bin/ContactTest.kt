/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.pastryshop.bin

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by Keval on 02/06/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
@RunWith(JUnit4::class)
class ContactTest {
    private val testPhone = "23676349234"
    private val testEmail = "test@example.com"
    private val testTwitter = "@example"

    @Test
    fun checkInitialize() {

        val contact = Contact(phone = testPhone, email = testEmail, twitter = testTwitter)

        assertEquals(testPhone, contact.phone)
        assertEquals(testEmail, contact.email)
        assertEquals(testTwitter, contact.twitter)
    }

    @Test
    fun checkEquals() {
        val contact = Contact(phone = testPhone, email = testEmail, twitter = testTwitter)
        val contact1 = Contact(phone = testPhone, email = testEmail, twitter = testTwitter)

        assertEquals(contact1, contact)
    }

    @Test
    fun checkNotEquals() {
        val contact = Contact(phone = testPhone, email = testEmail, twitter = testTwitter)
        val contact1 = Contact(phone = testPhone, email = testEmail, twitter = testTwitter)
        val contact2 = Contact(phone = "${testPhone}1 ", email = testEmail, twitter = testTwitter)

        assertEquals(contact1, contact)
        assertNotEquals(contact, contact2)
        assertNotEquals(contact1, contact2)
    }

    @Test
    fun checkHashCode() {
        val contact = Contact(phone = testPhone, email = testEmail, twitter = testTwitter)
        val contact1 = Contact(phone = testPhone, email = testEmail, twitter = testTwitter)
        val contact2 = Contact(phone = "${testPhone}1 ", email = testEmail, twitter = testTwitter)

        assertEquals(contact1.hashCode(), contact.hashCode())
        assertNotEquals(contact.hashCode(), contact2.hashCode())
        assertNotEquals(contact1.hashCode(), contact2.hashCode())
    }

}