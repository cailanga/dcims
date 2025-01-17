package cn.pzhu.dcims.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义返回状态信息
 * @author cailang
 * @create 2021-03-02-23:00
 */
public class R extends HashMap<String, Object> implements Serializable {
    private static final long serialVersionUID = 1L;

    public R() {
        put("code", 200);
        put("msg", "操作成功");
    }

    public static R error() {
        return error(1, "操作失败");
    }

    public static R error(String msg) {
        return error(500, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
