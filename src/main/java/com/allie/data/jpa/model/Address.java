package com.allie.data.jpa.model;

/**
 * Created by jacob.headlee on 10/20/2016.
 */
public class Address {

    private String address1;
    private String city;
    private String state;
    private String postalCode;

    public Address() {};

    @Override
    public String toString() {
        return "Address{" +
                "address1='" + address1 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }

    public String getAddress1() {return this.address1;}
    public void setAddress1(String address1) {this.address1 = address1;}

    public String getCity() {return  this.city;}
    public void setCity(String city) {this.city = city;}

    public String getState() {return this.state;}
    public void setState(String state) {this.state = state;}

    public String getPostalCode() {return this.postalCode;}
    public void  setPostalCode(String postalCode) {this.postalCode = postalCode;}

}
