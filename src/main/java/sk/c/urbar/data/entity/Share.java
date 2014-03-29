package sk.c.urbar.data.entity;

/**
 * Created by coon on 12/25/13.
 */
public class Share {

    Integer part;
    Integer votes;
    Boolean custom;

    public Integer getPart() {
        return part;
    }

    public void setPart(Integer part) {
        this.part = part;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }


    public boolean getCustom() {
        return custom != null ? custom.booleanValue() : false;
    }

    public void setCustom(Boolean custom) {
        this.custom = custom;
    }


}
