package com.jlysh.analysis.view;

import com.alibaba.fastjson.JSON;
import com.jlysh.analysis.pojo.beans.Topic;
import com.jlysh.analysis.service.ReportService;
import com.jlysh.analysis.util.JiebaSplit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 江冷易水寒
 * @data 2021/4/22 14:52
 */

@RestController
@Slf4j
public class SynthesisReportView {

    @Autowired
    ReportService reportService;
    @Autowired
    JiebaSplit jiebaSplit;

    //综合舆情分析
    @RequestMapping(value = "/perReportData")
    public String perReport() throws IOException {
//        Thread.sleep(5000);
//        ReportService reportService= new ReportService();

        List<Topic> ss = reportService.synthesisReportService();

        //积极/消极比例
        ArrayList<HashMap<String,Object>> emoRationList = new ArrayList<>();

        //地区比例
        ArrayList<HashMap<String,Object>> areaRatioList = new ArrayList<>();
        HashMap<String,Integer> geographicSegment = new HashMap<>(); //<地区,次数>

        //男女比例
        ArrayList<HashMap<String,Object>> sexRatioList = new ArrayList<>();
        HashMap<String,Integer> sexSegment = new HashMap<>(); //<性别,人数>


        //词云图
        ArrayList<ArrayList<Object>> wordCloudList = new ArrayList<>();
        HashMap<String,Integer> wordCloud = jiebaSplit.getJiebaMap(ss);

        //言论分布-地图
        ArrayList<HashMap<String,Object>> mapRatioList = new ArrayList<>();
        HashMap<String,Integer> mapSegment = new HashMap<>(); //<性别,人数>

        //表格信息
        List<Topic> renderTableList = ss;

        //一、    积极、消极 人数
        int negCount = 0;
        int posCount = 0;
        //二、    地区总人数
        int totalArea = ss.size();
        for(Topic s : ss) {
            // 1. 积极/消极 比例
//            System.out.println("emotion " + s.getEmotion());
            if(Float.parseFloat(s.getEmotion()) == 1) {
                posCount+=1;
            }else {
                negCount+=1;
            }

            //2 地区分布 比例
            //如果包含key，则value++;
            if(geographicSegment.containsKey(s.getArea())){
                geographicSegment.put(s.getArea(),geographicSegment.get(s.getArea())+1);
            }else { //如果不包含则将key存入到map,并初始化value为1
                geographicSegment.put(s.getArea(),1);
            }

            //3 性别 比例
            if(sexSegment.containsKey(s.getSex())) {
                sexSegment.put(s.getSex(),sexSegment.get(s.getSex()) + 1);
            }else {
                sexSegment.put(s.getSex(),1);
            }



        }
        //4 言论分布 比例
        mapSegment = geographicSegment;


        // 一、积极/消极 比例-饼状图
        final int finalNegCount = negCount;
        final int finalPosCount = posCount;
        emoRationList.add(new HashMap<String,Object>(){{
            put("name","积极");
            put("y", (float)(finalPosCount) / (float)(totalArea));
        }});
        emoRationList.add(new HashMap<String,Object>(){{
            put("name","消极");
            put("y", (float)(finalNegCount) / (float)(totalArea));
        }});

        // 二、地区比例-饼状图
        for(Map.Entry<String, Integer> entry:geographicSegment.entrySet()){
            areaRatioList.add(new HashMap<String,Object>(){{
                put("name",entry.getKey());
                put("y",(float)(entry.getValue())/(float)(totalArea));
            }});
        }

        // 三、性别比例-饼状图
        for(Map.Entry<String, Integer> entry:sexSegment.entrySet()){
            sexRatioList.add(new HashMap<String,Object>(){{
                put("name",entry.getKey());
                put("y",(float)(entry.getValue())/(float)(totalArea));
            }});
        }




        //  四、词云图
        for(Map.Entry<String, Integer> entry:wordCloud.entrySet()){
            wordCloudList.add(new ArrayList<Object>(){{
                add(entry.getKey());
                add(entry.getValue());
            }
            });
        }

        // 五、言论分布-地图
        for(Map.Entry<String, Integer> entry:mapSegment.entrySet()){
            mapRatioList.add(new HashMap<String,Object>(){{
//                System.out.println("entry.getKey()===" + entry.getKey());
//                System.out.println("entry.getKey()===" + entry.getValue());
                put("name",entry.getKey());
                put("value",entry.getValue());
            }});
        }



        Map<String,Object> perlist = new HashMap<>();

        perlist.put("emoRationList",emoRationList);
        perlist.put("areaRatioList",areaRatioList);
        perlist.put("sexRatioList",sexRatioList);
        perlist.put("mapRatioList",mapRatioList);
        perlist.put("wordCloudList",wordCloudList);
        perlist.put("renderTableList",renderTableList);
        return JSON.toJSONString(perlist);
    }

}
