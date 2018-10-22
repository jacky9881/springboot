package com.xxx.storm;

import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Values;


public class HelloSpout extends BaseRichSpout {

	private static final long serialVersionUID = -1790954436228054097L;
	private SpoutOutputCollector collector;
	private int count;

	@Override
	public void nextTuple() {
		if(count <= 2){
            System.out.println("第"+count+"次开始发送数据...");
            this.collector.emit(new Values("aaaa"));
        }
        count++;
		
	}

	@Override
	public void open(Map map, TopologyContext arg1, SpoutOutputCollector collector) {
		System.out.println("open:" + map.get("test"));
        this.collector = collector;
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// TODO Auto-generated method stub
		
	}

}
