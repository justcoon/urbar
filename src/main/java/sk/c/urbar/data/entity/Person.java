package sk.c.urbar.data.entity;

import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: coon
 * Date: 11/25/13
 * Time: 5:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class Person {

    String firstName;
    String surName;
    Date birthDate;
    String address;
    String bankAccount;
    String email;
    Date registeredFrom;
    Date registeredTo;
    String notice;
    String document;
    Boolean active = Boolean.TRUE;


    String identification;
    List<Share> shares = new Vector<Share>();

    public Person(String firstName, String surName, Date birthDate, String address, String bankAccount, String email, Date registeredFrom) {
        this.firstName = firstName;
        this.surName = surName;
        this.birthDate = birthDate;
        this.address = address;
        this.bankAccount = bankAccount;
        this.email = email;
        this.registeredFrom = registeredFrom;
    }

    public Person() {

    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegisteredFrom() {
        return registeredFrom;
    }

    public void setRegisteredFrom(Date registeredFrom) {
        this.registeredFrom = registeredFrom;
    }

    public Date getRegisteredTo() {
        return registeredTo;
    }

    public void setRegisteredTo(Date registeredTo) {
        this.registeredTo = registeredTo;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public synchronized List<Share> getShares() {

        if (shares == null) {
            shares = new Vector<Share>();
        }
        return shares;
    }

    public boolean getActive() {
        return active != null ? active.booleanValue() : true;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
