package com.gameball.gameball.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ahmed Abdelmoneam Abdelfattah on 8/23/2018.
 */
public class PlayerRegisterRequest {
    @SerializedName("playerCategoryId")
    @Expose
    private Integer playerCategoryID;
    @SerializedName("playerUniqueId")
    @Expose
    private String playerUniqueID;
    @SerializedName("deviceToken")
    @Expose
    private String deviceToken;
    @SerializedName("osType")
    @Expose
    private String oSType = "Android";
    @SerializedName("playerInfo")
    @Expose
    private PlayerInfoBody playerInfoBody;

    public Integer getPlayerCategoryID()
    {
        return playerCategoryID;
    }

    public void setPlayerCategoryID(Integer playerCategoryID)
    {
        this.playerCategoryID = playerCategoryID;
    }

    public String getPlayerUniqueID()
    {
        return playerUniqueID;
    }

    public void setPlayerUniqueID(String playerUniqueID)
    {
        this.playerUniqueID = playerUniqueID;
    }

    public String getDeviceToken()
    {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken)
    {
        this.deviceToken = deviceToken;
    }

    public PlayerInfoBody getPLayerInfo()
    {
        return playerInfoBody;
    }

    public void setPlayerInfoBody(PlayerInfoBody playerInfoBody)
    {
        this.playerInfoBody = playerInfoBody;
    }
}
