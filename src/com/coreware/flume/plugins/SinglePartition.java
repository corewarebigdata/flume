package com.coreware.flume.plugins;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

public class SinglePartition implements Partitioner {

    public SinglePartition(VerifiableProperties props) {
    }


	public int partition(Object arg0, int arg1)
	{
		return 0;
	}

}
