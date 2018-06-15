package xyz.yikai.kky.service;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import xyz.yikai.kky.model.UserItem;
import xyz.yikai.kky.web.ActionType;

/**
 * @Author: Jason
 * @Time: 2018/4/26 21:29
 * @Description:Json转Model
 * @Example BaseItem item = new Gson().fromJson(data, JsonModelRegistory.getInstance().getJsonType(tag[0]));
 */
public class JsonModelRegistory {

    private static JsonModelRegistory instance = new JsonModelRegistory();

    private JsonModelRegistory() {}

    public static JsonModelRegistory getInstance() {
        return instance;
    }

    static Map<ActionType, Type> actionTypeMap = new HashMap<>();

    public Type getJsonType(ActionType actionType) {
        return actionTypeMap.get(actionType);
    }

    static {
        registActionWithModel();
    }

    private static void registActionWithModel() {

        actionTypeMap.put(ActionType.Action_Login, new TypeToken<UserItem>() {
        }.getType());
    }
}
