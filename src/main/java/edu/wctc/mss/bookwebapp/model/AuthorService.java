/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mss.bookwebapp.model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Spike
 */
public class AuthorService {
    public ArrayList findAllAuthors() {
        ArrayList al = new ArrayList();
        al.add(new Author(1, "Mike", new Date()));
        al.add(new Author(2, "Dave", new Date()));
        al.add(new Author(3, "Steve", new Date()));
        return al;
    }
}
