package com.websocketgateway.jsonbuilder;

import org.json.JSONObject;

public class SendCoordinatesBuilder implements JSONBuilderable {
    @Override
    public JSONObject buildJson(String[] params) {
        JSONObject object = new JSONObject();
        object.put("task", "addCoordinate");
        object.put("prevX", Integer.parseInt(params[0]));
        object.put("prevY", Integer.parseInt(params[1]));
        object.put("currX", Integer.parseInt(params[2]));
        object.put("currY", Integer.parseInt(params[3]));
        object.put("strokeStyle", params[4]);
        object.put("lineWidth", Integer.parseInt(params[5]));
        return object;
    }
}
