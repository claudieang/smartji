/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;

/**
 *
 * @author Claudie
 */
public interface EmailVerification {

    public void sendNotification(String to, int base, int currentValue, int recommendedTime);
}
