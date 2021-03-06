package com.example.ali.myapplication.Activities.ModelClasses;

import java.lang.ref.SoftReference;

/**
 * Created by Sami Khan on 10/22/2017.
 */

public class Form_Token {

   private String token_id;
    private String Form_id;
    private String ApplicantName;
    private String CNIC_no;
    private long form_date;
    private String appointment_date;
    private String appointment_time;
    private Object token_date;
    private String form_status;



    public Form_Token() {
    }

    public Form_Token(String token_id, String form_id, String applicantName, String CNIC_no, long form_date, String appointment_date, String appointment_time, Object token_date,String form_status) {
        this.token_id = token_id;
        Form_id = form_id;
        ApplicantName = applicantName;
        this.CNIC_no = CNIC_no;
        this.form_date = form_date;
        this.appointment_date = appointment_date;
        this.appointment_time = appointment_time;
        this.token_date = token_date;
        this.form_status=form_status;
    }

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }

    public String getForm_id() {
        return Form_id;
    }

    public void setForm_id(String form_id) {
        Form_id = form_id;
    }

    public String getApplicantName() {
        return ApplicantName;
    }

    public void setApplicantName(String applicantName) {
        ApplicantName = applicantName;
    }

    public String getCNIC_no() {
        return CNIC_no;
    }

    public void setCNIC_no(String CNIC_no) {
        this.CNIC_no = CNIC_no;
    }

    public long getForm_date() {
        return form_date;
    }

    public void setForm_date(long form_date) {
        this.form_date = form_date;
    }

    public String getAppointment_date() {
        return appointment_date;
    }

    public void setAppointment_date(String appointment_date) {
        this.appointment_date = appointment_date;
    }

    public String getAppointment_time() {
        return appointment_time;
    }

    public void setAppointment_time(String appointment_time) {
        this.appointment_time = appointment_time;
    }

    public String getForm_status() {
        return form_status;
    }

    public void setForm_status(String form_status) {
        this.form_status = form_status;
    }

    public Object getToken_date() {
        return token_date;
    }

    public void setToken_date(Object token_date) {
        this.token_date = token_date;
    }
}
