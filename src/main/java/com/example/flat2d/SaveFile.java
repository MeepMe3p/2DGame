package com.example.flat2d;

import java.io.Serializable;

public class SaveFile implements Serializable {
    Object saveFile;
    SaveFile(Object saveFile){
        this.saveFile = saveFile;
    }
}
