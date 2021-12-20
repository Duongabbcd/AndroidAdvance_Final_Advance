// IMyServer.aidl
package com.example.lequangdao;

// Declare any non-default types here with import statements

interface IMyServer {

    /**
    * Count from 1 to 1000
    */
    int operation();

    /**
    * Delay 5s
    */
    void process();

    /**
    * Return current user location
    */
    String ocateMe();
}