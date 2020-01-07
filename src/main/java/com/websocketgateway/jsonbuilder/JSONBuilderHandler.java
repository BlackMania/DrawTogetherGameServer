package com.websocketgateway.jsonbuilder;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JSONBuilderHandler {
    private static Map<BuildType, JSONBuilderable> builders = new HashMap<BuildType, JSONBuilderable>();


    static {
        builders.put(BuildType.CREATELOBBY, new CreateLobbyBuilder());
        builders.put(BuildType.ERRORJSON, new ErrorJsonBuilder());
        builders.put(BuildType.GETDRAWWORDS, new GetDrawWordBuilder());
        builders.put(BuildType.GETGAMES, new GetGamesBuilder());
        builders.put(BuildType.GUESSDRAWING, new GuessDrawingBuilder());
        builders.put(BuildType.JOINLOBBY, new JoinLobbyBuilder());
        builders.put(BuildType.LEAVELOBBY, new LeaveLobbyBuilder());
        builders.put(BuildType.SENDCOORDINATES, new SendCoordinatesBuilder());
        builders.put(BuildType.SETWORD, new SetWordBuilder());
        builders.put(BuildType.STARTGAME, new StartGameBuilder());
    }

    public static JSONObject buildJson(String[] params, BuildType type)
    {
        return builders.get(type).buildJson(params);
    }
}
