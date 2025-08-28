package com.mega.entity;

import java.util.ArrayList;

import lombok.Data;

@Data
public class pjtMenu {
    private int id;
    private String menuid;
    private String menuname;
    private String path;
    private String parent_id;
    @SuppressWarnings("rawtypes")
    private ArrayList children;
    private int sort_order;
}