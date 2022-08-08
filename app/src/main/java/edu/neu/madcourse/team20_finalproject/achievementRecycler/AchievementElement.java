package edu.neu.madcourse.team20_finalproject.achievementRecycler;

import edu.neu.madcourse.team20_finalproject.R;

public class AchievementElement {

    private int imgId;
    private String description;
    private boolean unlocked;

    public AchievementElement(String description) {
        this.imgId = R.drawable.ic_lock;
        this.description = description;
        this.unlocked = false;
    }

    public AchievementElement(String description, boolean unlocked) {
        this.description = description;
        this.unlocked = unlocked;
        if (unlocked) {
            this.imgId = R.drawable.trophy;
        } else {
            this.imgId = R.drawable.ic_lock;
        }
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
        if (unlocked) {
            this.imgId = R.drawable.trophy;
        } else {
            this.imgId = R.drawable.ic_lock;
        }
    }
}
