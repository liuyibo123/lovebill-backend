package com.liuyibo.lovebill.common;

/**
 * 返回结果枚举
 */
public enum  BillResultConstrant {
    //成功
    success("000","成功"),
    //失败
    failed("100","调用失败"),
    //用户不存在
    user_not_exist("101","用户不存在"),
    //用户已存在
    user_already_exist("201","用户已存在"),
    //用户未设置账单库
    bill_id_not_set("102","未设置账单库"),
    //获取billTotal失败
    get_bill_total_fail("103","获取账单库失败");
    private  String code;
    private  String msg;
    BillResultConstrant(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
