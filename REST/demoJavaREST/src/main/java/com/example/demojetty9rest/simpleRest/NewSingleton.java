/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demojetty9rest.simpleRest;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class NewSingleton {
    
    private List<String> list = new ArrayList<String>();
    
    public List<String> getList() {
        return list;
    }
    
    private NewSingleton() {
        
            list.add("a");
            list.add("b");
            list.add("c");
            list.add("d");
            list.add("e");
        
    }
    
    public static NewSingleton getInstance() {
        return NewSingletonHolder.INSTANCE;
    }
    
    private static class NewSingletonHolder {

        private static final NewSingleton INSTANCE = new NewSingleton();
    }
}
