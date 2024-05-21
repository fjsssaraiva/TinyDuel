package com.fsaraiva.tinyduel.engine.video;

import java.util.*;

public class SceneObjectSorter implements Comparator<SceneObject>{

    @Override
    public int compare(SceneObject a, SceneObject b) {
        if (a.hitBox.y < b.hitBox.y) {
            return 1;
        }
        if (a.hitBox.y > b.hitBox.y) {
            return -1;
        }
        if (a.hitBox.y == b.hitBox.y) {
            if (a.hitBox.x < b.hitBox.x) {
                return -1;
            }
            if (a.hitBox.x > b.hitBox.x) {
                return 1;
            }
            if (a.hitBox.x == b.hitBox.x) {
                return 0;
            }
        }
        return 0;
    }
}