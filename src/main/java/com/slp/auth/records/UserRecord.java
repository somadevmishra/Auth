package com.slp.auth.records;

public record UserRecord(String name,
                         String userName,
                         String password,
                         String status,
                         Boolean active,
                         Boolean expired,
                         String emailId,
                         String phoneNumber,
                         String salt){

    public UserRecord (String name,
                       String userName,
                       String password,
                       String status,
                       Boolean active,
                       Boolean expired,
                       String emailId,
                       String phoneNumber){
        this( name, userName, password, status, active,
                expired, emailId, phoneNumber, null);
    }

}
