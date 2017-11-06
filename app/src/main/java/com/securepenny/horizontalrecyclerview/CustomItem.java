package com.securepenny.horizontalrecyclerview;

/**
 * Created by R041708040 on 11/6/2017.
 */

public class CustomItem {
    private Long id;
    private String name;
    private int imageId;

    public CustomItem(Long id, String name,int imageId)
    {
        this.id=id;
        this.name=name;
        this.imageId=imageId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
