package sk.c.urbar.data.entity;

/**
 * share DTO
 *
 * @author coon
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
