package eu.cec.sanco.beans;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.primefaces.component.log.Log;

public class ComplaintSet {
  private String creation_date;
  private String modification_date;
  private boolean new_complaint;
  private String locked;
  private String contact_name;
  private String contact_email;
  private String contact_tel;
  private String contact_fax;
  private String contact_address;
  private String contact_town;
  private String contact_postcode;
  private String consumer_country;
  private String user_notes;
  private String reference;
  private String complaint_date;
  private String distinction;
  private String organisation_id;
  private String trader_name;
  private String trader_country;
  private String selling_method;
  private String advertising_method;
  private String sector;
  private String market;
  private List<Complaint> complaints = new LinkedList<Complaint>();

  private static final transient Logger LOG = Logger.getLogger(ComplaintSet.class);

  public ComplaintSet() {

  }

  public Date getCreation_date() {
    if (creation_date == null) {
      return null;
    }
    return new Date(new Long(creation_date));
  }

  public void setCreation_date(Date creation_date) {
    this.creation_date = String.valueOf(creation_date.getTime());
  }

  public Date getModification_date() {
    if (modification_date == null) {
      return null;
    }

    return new Date(new Long(modification_date));
  }

  public void setModification_date(Date modification_date) {
    this.modification_date = String.valueOf(modification_date.getTime());
  }

  public boolean isNew_complaint() {
    return new_complaint;
  }

  public void setNew_complaint(boolean new_complaint) {
    this.new_complaint = new_complaint;
  }

  public String getLocked() {
    return locked;
  }

  public void setLocked(String locked) {
    this.locked = locked;
  }

  public String getContact_name() {
    return contact_name;
  }

  public void setContact_name(String contact_name) {
    this.contact_name = contact_name;
  }

  public String getContact_email() {
    return contact_email;
  }

  public void setContact_email(String contact_email) {
    this.contact_email = contact_email;
  }

  public String getContact_tel() {
    return contact_tel;
  }

  public void setContact_tel(String contact_tel) {
    this.contact_tel = contact_tel;
  }

  public String getContact_fax() {
    return contact_fax;
  }

  public void setContact_fax(String contact_fax) {
    this.contact_fax = contact_fax;
  }

  public String getContact_address() {
    return contact_address;
  }

  public void setContact_address(String contact_address) {
    this.contact_address = contact_address;
  }

  public String getContact_town() {
    return contact_town;
  }

  public void setContact_town(String contact_town) {
    this.contact_town = contact_town;
  }

  public String getContact_postcode() {
    return contact_postcode;
  }

  public void setContact_postcode(String contact_postcode) {
    this.contact_postcode = contact_postcode;
  }

  public String getConsumer_country() {
    return consumer_country;
  }

  public void setConsumer_country(String consumer_country) {
    this.consumer_country = consumer_country;
  }

  public String getUser_notes() {
    return user_notes;
  }

  public void setUser_notes(String user_notes) {
    this.user_notes = user_notes;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public String getComplaint_date() {
    return complaint_date;
  }

  public void setComplaint_date(String complaint_date) {
    this.complaint_date = complaint_date;
  }

  public String getDistinction() {
    return distinction;
  }

  public void setDistinction(String distinction) {
    this.distinction = distinction;
  }

  public String getOrganisation_id() {
    return organisation_id;
  }

  public void setOrganisation_id(String organisation_id) {
    this.organisation_id = organisation_id;
  }

  public String getTrader_name() {
    return trader_name;
  }

  public void setTrader_name(String trader_name) {
    this.trader_name = trader_name;
  }

  public String getTrader_country() {
    return trader_country;
  }

  public void setTrader_country(String trader_country) {
    this.trader_country = trader_country;
  }

  public String getSelling_method() {
    return selling_method;
  }

  public void setSelling_method(String selling_method) {
    this.selling_method = selling_method;
  }

  public String getAdvertising_method() {
    return advertising_method;
  }

  public void setAdvertising_method(String advertising_method) {
    this.advertising_method = advertising_method;
  }

  public String getSector() {
    return sector;
  }

  public void setSector(String sector) {
    this.sector = sector;
  }

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  public List<Complaint> getComplaints() {
    LOG.info("Getting :" + complaints.size());

    return complaints;
  }

  public void setComplaints(List<Complaint> complaints) {
    this.complaints = complaints;
  }

  @Override
  public String toString() {
    return String
        .format(
            "ComplaintSet [creation_date=%s, modification_date=%s, new_complaint=%s, locked=%s, contact_name=%s, contact_email=%s, contact_tel=%s, contact_fax=%s, contact_address=%s, contact_town=%s, contact_postcode=%s, consumer_country=%s, user_notes=%s, reference=%s, complaint_date=%s, distinction=%s, organisation_id=%s, trader_name=%s, trader_country=%s, selling_method=%s, advertising_method=%s, sector=%s, market=%s, complaints=%s]",
            creation_date, modification_date, new_complaint, locked, contact_name, contact_email, contact_tel,
            contact_fax, contact_address, contact_town, contact_postcode, consumer_country, user_notes, reference,
            complaint_date, distinction, organisation_id, trader_name, trader_country, selling_method,
            advertising_method, sector, market, complaints);
  }

}
