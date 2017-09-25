package com.a700apps.techmart.data.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by samir salah on 9/13/2017.
 */

public class Result {


    private List<BoardMemebe> boardMemebes = null;
    private List<OtheMemebes> otheMemebes = null;
    private Integer iD;
    private String name;
    private String icon;
    private String creationDate;
    private Integer memberCount;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<BoardMemebe> getBoardMemebes() {
        return boardMemebes;
    }

    public void setBoardMemebes(List<BoardMemebe> boardMemebes) {
        this.boardMemebes = boardMemebes;
    }

    public List<OtheMemebes> getOtheMemebes() {
        return otheMemebes;
    }

    public void setOtheMemebes(List<OtheMemebes> otheMemebes) {
        this.otheMemebes = otheMemebes;
    }

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
