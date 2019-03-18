package com.cloudsoft.common.util;

import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author ZJR
 * @data 2019/2/18 下午 6:09
 */
public class BankUtils {

    public static String getCardCodeAndName(final String cardNo){
        //银行代码请求接口 url
        String url = "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardNo="+cardNo+"&cardBinCheck=true";
        //发送请求，得到 josn 类型的字符串
        String result = HttpUtil.get(url);
        // 转为 Json 对象
        JSONObject json = new JSONObject(result);
        //获取到 bank 代码
        String bank = String.valueOf(json.get("bank"));
        if(StringUtils.isEmpty(bank)){
            return null;
        }
        //爬取支付宝银行合作商页面
        String listContent =  HttpUtil.get("https://ab.alipay.com/i/yinhang.htm");
        //过滤得到需要的银行名称
        List<String> titles = ReUtil.findAll("<span title=\"(.*?)\" class=\"icon "+bank+"\">(.*?)</span>", listContent, 2);
        if(titles.size()>0){
            return bank+","+titles.get(0);
        }else {
            return null;
        }


    }
}
